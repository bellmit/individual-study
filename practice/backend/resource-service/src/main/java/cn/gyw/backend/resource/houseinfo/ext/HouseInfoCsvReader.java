package cn.gyw.backend.resource.houseinfo.ext;

import cn.gyw.backend.resource.enums.OriginTypeEnum;
import cn.gyw.backend.resource.houseinfo.dao.mapper.HouseInfoMapper;
import cn.gyw.backend.resource.houseinfo.dao.po.HouseInfo;
import cn.gyw.backend.resource.houseinfo.dto.HouseInfoDto;
import cn.gyw.components.web.utils.DateUtil;
import com.opencsv.CSVReader;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 房屋信息CSV数据文件读取入库
 *
 * @date 2021/11/9 17:59
 */
@Component
public class HouseInfoCsvReader {

    private static final Logger log = LoggerFactory.getLogger(HouseInfoCsvReader.class);

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    // CSV 数据文件存储目录
    @Value("${crawler.csv.fileDir}")
    private String csvStorageDir;

    private HouseInfoMapper houseInfoMapper;

    public boolean readAndSaveDB() {
        // 1. 获取前一天的数据文件
        List<File> fileList = findYesterdayCsvFile(csvStorageDir);
        if (CollectionUtils.isEmpty(fileList)) {
            log.error("数据文件不存在...");
            return false;
        }
        boolean isSuccess = false;
        for (File file : fileList) {
            // 2. 读取前一天的数据(不读当天的)
            List<HouseInfoDto> dataList = readCsvData(file);
            if (CollectionUtils.isEmpty(dataList)) {
                log.error("文件数据不存在或读取失败！");
                return false;
            }
            // 3. 存储到关系型数据库
            isSuccess = saveDB(dataList);
            log.info("存储文件[{}],结果：{}", file.getName(), isSuccess);
        }
        return isSuccess;
    }

    private boolean saveDB(List<HouseInfoDto> dataList) {
        List<HouseInfo> houseInfoList = dataList.stream().map(houseInfoDto -> {
            HouseInfo houseInfo = new HouseInfo();
            BeanUtils.copyProperties(houseInfoDto, houseInfo);
            // 爬取日期
            if (StringUtils.isNotEmpty(houseInfoDto.getCrawlDate())) {
                houseInfo.setCrawlDate(LocalDate.parse(houseInfoDto.getCrawlDate(), dateFormatter));
            }
            // 数据来源
            houseInfo.setOriginType(OriginTypeEnum.getCode(houseInfoDto.getOriginType()));
            houseInfo.setHouseType(1);
            houseInfo.setUsage(1);
            return houseInfo;
        }).collect(Collectors.toList());
        houseInfoMapper.batchInsert(houseInfoList);
        return true;
    }

    private List<HouseInfoDto> readCsvData(File file) {
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String[] cnHeaders = csvReader.readNext();
            String[] enHeaders = csvReader.readNext();
            if (Objects.isNull(cnHeaders) || Objects.isNull(enHeaders)) {
                log.error("Csv file headers is null, check data content!");
                return Collections.emptyList();
            }
            List<HouseInfoDto> houseInfoDtoList = new ArrayList<>();
            String[] data;
            while ((data = csvReader.readNext()) != null) {
                houseInfoDtoList.add(buildHouseInfoDto(enHeaders, data));
            }
            return houseInfoDtoList;
        } catch (Exception e) {
            log.error("error :", e);
        } finally {
            try {
                if (Objects.nonNull(csvReader)) {
                    csvReader.close();
                }
            } catch (IOException ex) {
                log.error("close csvReader error :", ex);
            }
        }
        return Collections.emptyList();
    }

    private HouseInfoDto buildHouseInfoDto(String[] enHeaders, String[] data) {
        HouseInfoDto houseInfoDto = new HouseInfoDto();
        // 文件数据
        for (int i = 0, len = enHeaders.length; i < len; i++) {
            String prop = enHeaders[i];
            try {
                PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(houseInfoDto, prop);
                pd.getWriteMethod().invoke(houseInfoDto, data[i]);
            } catch (Exception e) {
                log.error("write filed error [" + prop + "] , error :", e);
            }
        }
        return houseInfoDto;
    }


    private List<File> findYesterdayCsvFile(String csvStorageDir) {
        String dayOfYesterday = DateUtil.getDateOfYesterday();
        Path storageDir = Paths.get(Paths.get(csvStorageDir).toUri());
        try {
            return Files.walk(storageDir).peek(path -> log.debug("访问文件path :{}", path.toString()))
                    .filter(path -> {
                        String fileName = path.getFileName().toString();
                        return fileName.endsWith(".csv") && fileName.contains(dayOfYesterday);
                    }).map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Autowired
    public void setHouseInfoMapper(HouseInfoMapper houseInfoMapper) {
        this.houseInfoMapper = houseInfoMapper;
    }
}
