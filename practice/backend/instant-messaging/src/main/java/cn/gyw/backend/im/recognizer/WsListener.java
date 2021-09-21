package cn.gyw.backend.im.recognizer;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyw.backend.im.recognizer.upload.AbstractUploader;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WsListener extends WebSocketListener {

	private Logger logger = LoggerFactory.getLogger(WsListener.class);

	private AtomicBoolean isClosed;

	private Stat stat;

	private AbstractUploader uploader;

	public WsListener(AbstractUploader uploader) {
		isClosed = new AtomicBoolean(false);
		stat = uploader.getStat();

		this.uploader = uploader;
		stat.updateBeforeConnectTime();
	}

	@Override
	public void onOpen(WebSocket webSocket, Response response) {
		super.onOpen(webSocket, response);
		new Thread(() -> {
			try {
				uploader.execute(webSocket);
			} catch (JSONException e) {
				logger.error("upload " + e.getClass().getSimpleName(), e);
				throw new RuntimeException(e);
			}
		}).start();
	}

	@Override
	public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
		super.onMessage(webSocket, text);
		// 这里千万别阻塞，包括这个类其它回调
		Result result;
		try {
			// 将json解析为Result类
			result = new Result(text);
		} catch (JSONException e) {
			logger.info("receive json parse error: " + e.getMessage() + ":" + text, e);
			e.printStackTrace();
			return;
		}
		if (result.isHeartBeat()) {
			logger.info("receive heartbeat: " + text.trim());
		} else {
			logger.info("receive text: " + text.trim());
		}
		if (result.isFin()) {
			stat.addResult(result);
		}
	}

	@Override
	public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
		super.onClosed(webSocket, code, reason);
		// 这里千万别阻塞，包括这个类其它回调
		logger.info("websocket closed: " + code + " | " + reason);
		logger.info(stat.toReportString());
		setClosed();
	}

	@Override
	public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
		super.onClosing(webSocket, code, reason);
		// 这里千万别阻塞，包括这个类其它回调
		logger.info("websocket event closing :" + code + " | " + reason);
		webSocket.close(1000, "");
	}

	@Override
	public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
		super.onFailure(webSocket, t, response);
		// 这里千万别阻塞，包括这个类其它回调
		logger.info("websocket failure :" + t.getMessage(), t);
		setClosed();
	}

	private void setClosed() {
		isClosed.set(true);
	}

	@Override
	public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
		super.onMessage(webSocket, bytes);
		logger.info("receive binary unexpected: " + bytes.size());
		// never happen
	}

	public boolean isClosed() {
		return isClosed.get();
	}
}
