package cn.gyw.thirdpart.urlconnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

/**
 * JDK的java.net包中已经提供了访问HTTP协议的基本功能的类
 * HttpURLConnection
 * 该工具用来
 * Created by guanyw on 2018/7/6.
 */
public class UrlConnectionUtils {

	private static Logger logger = LoggerFactory.getLogger(UrlConnectionUtils.class);

	private static Config config;

	public static String getUrl(String url) {
		return execute(url, createConfig(false, true, true), "GET");
	}

	public static String postUrl(String url) {
		return execute(url, createConfig(true, true, false), "POST");
	}

	public static String execute(String url, Config conf, String method) {

		URL targetUrl = null;
		HttpURLConnection connection = null;
		BufferedReader reader = null;

		try {
			// 1. 得到访问地址的URL
			targetUrl = new URL(url);
			// 2. 得到网络访问对象java.net.HttpURLConnection
			connection = (HttpURLConnection) targetUrl.openConnection();
			// 3. 设置请求参数
			configConnection(connection, conf, method);
			// 4.连接
			connection.connect();

			if ("post".equalsIgnoreCase(method)) {
				String params = "username=test&password=123456";
				OutputStream out = connection.getOutputStream();
				out.write(params.getBytes());
				out.flush();
				out.close();
			}

			// 5.得到相应状态码的返回值
			int code = connection.getResponseCode();
			// 6.如果返回值正常，输在在网络中是以流的形式得到服务端返回的数据
			String msg = "";
			// 正常响应处理
			if (code == 200) {
				// 从流中读取响应信息
				reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				// 循环从流中读取
				while ((line = reader.readLine()) != null) {
					msg += line;
				}
			}

			// 返回响应数据
			return msg;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			release(reader, connection);
		}

		return "failed";
	}

	private static void configConnection(HttpURLConnection connection, Config conf, String method) throws ProtocolException {
		logger.info("开始配置连接...");
		// 设置是否想HttpURLConnection 输出
		connection.setDoOutput(conf.isOutput());
		// 设置是否从httpUrlConnection读入
		connection.setDoInput(conf.isInput());
		// 设置请求方式
		connection.setRequestMethod(method);
		// 设置是否使用缓存
		connection.setUseCaches(conf.isCache());
		// 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
		connection.setInstanceFollowRedirects(true);
		// 设置超时时间
		connection.setConnectTimeout(3000);
		if ("post".equalsIgnoreCase(method)) {
			// 设置使用标准编码格式编码参数的名-值对
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		}
	}

	private static void release(BufferedReader reader, HttpURLConnection connection) {
		logger.info("开始释放资源...");
		try {
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		} catch (IOException e) {
			logger.error("资源释放异常,异常原因：{}", e.getMessage());
		}

	}

	private static Config createConfig(boolean isOutput, boolean isInput, boolean isCache) {
		config.setOutput(isOutput);
		config.setInput(isInput);
		config.setCache(isCache);
		return config;
	}

	private class Config {
		boolean isOutput;
		boolean isInput;
		boolean isCache;

		public boolean isOutput() {
			return isOutput;
		}

		public void setOutput(boolean output) {
			isOutput = output;
		}

		public boolean isInput() {
			return isInput;
		}

		public void setInput(boolean input) {
			isInput = input;
		}

		public boolean isCache() {
			return isCache;
		}

		public void setCache(boolean cache) {
			isCache = cache;
		}
	}
}
