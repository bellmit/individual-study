package cn.gyw.spring.web.controller;

import cn.gyw.spring.model.UserSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * 桩数据控制器
 */
@RestController
@RequestMapping("/data")
public class StubDataController {

    private Logger log = LoggerFactory.getLogger(StubDataController.class);

    @RequestMapping("")
    public String one() {

        return "one";
    }

    /**
     * @param name
     * @param age
     * @return
     * @RequestParam：请求参数映射 示例：/r?name=xxx&age=20
     */
    @RequestMapping(value = "/r", method = RequestMethod.GET)
    public String requestParamMapping1(@RequestParam String name,
                                       @RequestParam(required = false, defaultValue = "28") Integer age) {
        log.info("name:[{}], age:[{}]", name, age);

        return "success";
    }

    /**
     * @return
     * @RequestParam：请求参数映射 示例：/r?name=xxx&age=20
     */
    @RequestMapping(value = "/r2", method = RequestMethod.GET)
    public String requestParamMapping2(@RequestParam Map<String, String> params) {
        params.forEach((key, val) -> {
            log.info("key:[{}]-value:[{}]", key, val);
        });

        return "success";
    }

    /**
     * 表单提交 和 表单验证
     *
     * @return
     */
    @RequestMapping(value = "/p1", method = RequestMethod.POST)
    public String formSubmit(@ModelAttribute("userForm") @Valid UserSpring user, BindingResult bindingResult) {
        log.info("User :[{}]", user);
        if (bindingResult.hasErrors()) {
            return "表单验证错误";
        } else {

        }

        return "验证成功";
    }

    /**
     * 文件上传
     *
     * 基于 Apache Commons Fileupload
     * @return
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST,
            produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(@RequestParam("Filedata") MultipartFile file, HttpServletRequest request) {

        // 判断文件是否为空，空则返回失败页面
        if (file.isEmpty()) {
            return "failed";
        }
        // 获取文件存储路径（绝对路径）
        // 获取原文件名
        String fileName = file.getOriginalFilename();
        // 创建文件实例
        // File filePath = new File(path, fileName);
        // // 如果文件目录不存在，创建目录
        // if (!filePath.getParentFile().exists()) {
        //     filePath.getParentFile().mkdirs();
        //     System.out.println("创建目录" + filePath);
        // }
        // // 写入文件
        // file.transferTo(filePath);

        System.out.println(request.getContextPath());
        System.out.println(request.getServletPath());

        return "success";
    }
}
