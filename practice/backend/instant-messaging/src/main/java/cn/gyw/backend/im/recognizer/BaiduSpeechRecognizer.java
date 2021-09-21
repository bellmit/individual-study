package cn.gyw.backend.im.recognizer;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.gyw.backend.im.recognizer.upload.AbstractUploader;
import cn.gyw.backend.im.recognizer.upload.RealTimeUploader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * 百度实时语音识别
 */
@Component
@Scope("prototype")
public class BaiduSpeechRecognizer {
	
	private Logger logger = LoggerFactory.getLogger(BaiduSpeechRecognizer.class);
	
	private WebSocket webSocket;
	
	/* 可以改为wss:// */
    private String URI = "wss://vop.baidu.com/realtime_asr";
    
    private AbstractUploader uploader;
	
	public BaiduSpeechRecognizer() {
		logger.debug("Constructor for BaiduSpeechRecognizer");
	}
	
	public void startRecognize(InputStream inputStream) {
		uploader = new RealTimeUploader(inputStream, new Stat());
		WsListener listener = new WsListener(uploader);
		createWebSocket(listener);
	}
	
	private void createWebSocket(WsListener listener) {
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(2000, TimeUnit.MILLISECONDS).build();
		String url = URI + "?sn=" + UUID.randomUUID().toString();
		Request request = new Request.Builder().url(url).build();
		logger.debug("Connect baidu recognize :{}", url);
		this.webSocket = client.newWebSocket(request, listener); // WListener 为回调类
	}
	
	public void sendAudioFrames() {
		uploader.sendAudioFrames(this.webSocket);
	}
	
	public void sendFinishFrames() {
		uploader.sendFinishFrame(this.webSocket);
	}
}
