package cn.gyw.platform.tools.logging.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.stream.Stream;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import cn.gyw.platform.tools.logging.model.RemoteConfig;

public class GanymedRemoteService implements IRemoteService {

	private static final String DEFAULT_CHARTSET = "UTF-8";

	private Connection connection;
	
	private volatile boolean isLogin;

	@Override
	public boolean login(RemoteConfig config) {
		connection = new Connection(config.getIp());
		try {
			connection.connect();
			isLogin = true;
			return connection.authenticateWithPassword(config.getUsername(), config.getPassword());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public String execute(String cmd) {
		if (!isLogin) {
			return "Not login.";
		}
		String result = "";
		try {
			Session session = connection.openSession();
			session.execCommand(cmd);
			result = processStdout(session.getStdout(), DEFAULT_CHARTSET);
			if (result.equals("")) {
				result = processStdout(session.getStderr(), DEFAULT_CHARTSET);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String processStdout(InputStream in, String charset) {
		InputStream stdout = new StreamGobbler(in);
		StringBuilder builder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	@Override
	public boolean upload(String sourceFile, String remoteDir) {
		return upload(sourceFile, remoteDir, "0600");
	}
	
	@Override
	public boolean upload(String sourceFile, String remoteDir, String mode) {
		if (!isLogin) {
			return false;
		}
		try {
			SCPClient scpClient = connection.createSCPClient();
			scpClient.put(sourceFile, remoteDir);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean download(String remoteFile, String localDir) {
		if (!isLogin) {
			return false;
		}
		try {
			SCPClient scpClient = connection.createSCPClient();
			scpClient.get(remoteFile, localDir);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean download(String[] remoteFiles, String localDir) {
		Stream<String> stream = Arrays.stream(remoteFiles);
		stream.forEach(path -> {
			download(path, localDir);
		});
		return true;
	}
}
