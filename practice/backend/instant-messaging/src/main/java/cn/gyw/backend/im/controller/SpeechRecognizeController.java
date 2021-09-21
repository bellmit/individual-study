package cn.gyw.backend.im.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.gyw.backend.im.recognizer.BaiduSpeechRecognizer;

@RestController
@RequestMapping("/speech")
public class SpeechRecognizeController {
	
	private Logger logger = LoggerFactory.getLogger(SpeechRecognizeController.class);
	
	private String filePath = "D:\\Temp\\audio\\16k-0.pcm";
	
	@Autowired
	private ApplicationContext applicationContext;

	@GetMapping("/start")
	public int startRecognize() {
		BaiduSpeechRecognizer baiduRecognizer = createBaiduRecognizer();
		File file = new File(filePath);
		InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            logger.info("file total size: " + inputStream.available());
            baiduRecognizer.startRecognize(inputStream);
            
            TimeUnit.SECONDS.sleep(1L);
            
            baiduRecognizer.sendAudioFrames();
            
            TimeUnit.SECONDS.sleep(1L);
            
            baiduRecognizer.sendFinishFrames();
            
        } catch (Exception e) {
			e.printStackTrace();
		}
        return 0;
	}
	
	private BaiduSpeechRecognizer createBaiduRecognizer() {
		return applicationContext.getBean(BaiduSpeechRecognizer.class);
	}
}
