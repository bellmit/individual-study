package cn.gyw.frameworks.easypoi;

import cn.gyw.frameworks.easypoi.config.ConfigReader;
import cn.gyw.frameworks.easypoi.model.Record;
import cn.gyw.frameworks.easypoi.util.EasyPoiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 计算服务
 */
public class CalcService {

    private static final Logger log = LoggerFactory.getLogger(CalcService.class);

    private Map<String, Integer> ruleMap = ConfigReader.getInstance().getRules();

    public void calc() {
        List<Record> recordList = readData();
        recordList.forEach(this::handleItem);
        writeData(recordList);
    }

    private void handleItem(Record record) {
        StringBuilder builder = new StringBuilder();
        String[] optionList = record.getItem().replaceAll("，", ",").split(",");
        String tmpOption;
        int sum = 0;
        for (int i = 0, len = optionList.length; i < len; i++) {
            tmpOption = optionList[i];
            Integer subTotal = handleOption(tmpOption);
            builder.append(tmpOption);
            builder.append(subTotal == 0 ? "" : String.valueOf(subTotal));
            builder.append("，");
            sum += subTotal;
        }
        record.setItem(builder.substring(0, builder.length() - 1));
        record.setTotal(String.valueOf(sum));
    }

    private Integer handleOption(String tmpOption) {
        String[] two = tmpOption.replaceAll("=", "")
                .replaceAll("\\s", "").split("\\*");
        if (ruleMap.containsKey(two[0])) {
            Integer goal = ruleMap.get(two[0]);
            Integer num = Integer.parseInt(two[1]);
            return goal * num;
        }
        return 0;
    }

    private List<Record> readData() {
        String excelPath = ConfigReader.getInstance().getInputFilePath();
        return EasyPoiUtil.importExcel(excelPath, Record.class);
    }

    private void writeData(List<Record> data) {
        String outPath = ConfigReader.getInstance().getOutFilePath();
        EasyPoiUtil.exportExcel(data, null, "二季度", Record.class, outPath, true);
    }
}
