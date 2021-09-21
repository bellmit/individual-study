package cn.gyw.thirdpart.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FtpTools {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private FTPClient ftp;
    private String hostName;
    private Integer port = 21;
    private String username;
    private String password;
    private String controlEncoding = "GBK";
    private Boolean localActive = true;

	public FtpTools(String ftpHost, int port, String user, String pass){
		this.hostName = ftpHost;
		this.port = port;
		this.username = user;
		this.password = pass;
	}
    public FtpTools(String ftpHost, String user, String pass){
        this.hostName = ftpHost;
        this.username = user;
        this.password = pass;
    }

    public void login() throws IOException {
	    logger.info("登陆FTP:"+username+":"+password+":"+hostName+":"+port);
	    ftp = new FTPClient();
        ftp.connect(hostName);
        ftp.login(username, password);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        if (controlEncoding != null){
            ftp.setControlEncoding(controlEncoding);
        }

        if (localActive) {
            ftp.enterLocalActiveMode();
        } else {
            ftp.enterLocalPassiveMode();
        }
	    logger.info("登陆完成");
    }

	public List<String> listDir(String path){
		if(ftp == null){
			try {
				login();
			} catch (IOException e) {
				logger.error("ftp连接失败",e);
				return new LinkedList<String>();
			}
		}
		try {
			FTPFile ftpFiles[] = ftp.listDirectories(path);
			List<String> list = new LinkedList<String>();
			for(FTPFile ftpFile : ftpFiles){
				list.add(ftpFile.getName());
			}
			logger.info("获取文件夹列表:"+list);
			return list;
		} catch (IOException e) {
			logger.error("",e);
			return new LinkedList<String>();
		}
	}
	public boolean createDir(String name){
		if(ftp == null){
			try {
				login();
			} catch (IOException e) {
				logger.error("ftp连接失败",e);
				return false;
			}
		}
		boolean b = false;
		try {
			b = ftp.makeDirectory(name);
			logger.info(name+"文件夹建立成功");
		} catch (IOException e) {
			logger.error("",e);
		}
		return b;
	}
	public boolean update(byte[] bytes,String remotePath,String remoteFileName) {
		if(ftp == null){
			try {
				login();
			} catch (IOException e) {
				logger.error("ftp连接失败",e);
				return false;
			}
		}
		logger.info("开始上传文件:"+remoteFileName+"，到:"+remotePath + remoteFileName);
		boolean b = false;
		BufferedInputStream bufferedInputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(bytes));
			if (ftp.isConnected() && ftp.isAvailable()) {
				b = ftp.storeFile(remotePath + remoteFileName, bufferedInputStream);
			} else {
				logger.error("ftp没有连接");
			}
		} catch (IOException e) {
			logger.error("",e);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				bufferedInputStream.close();
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
		if(b){
			logger.info("上传成功:"+remotePath+remoteFileName);
		} else {
			logger.info("上传失败:"+remotePath+remoteFileName);
		}
		return b;
	}

    public boolean update(String _file,String remotePath,String remoteFileName) {
	    if(ftp == null){
		    try {
			    login();
		    } catch (IOException e) {
			    logger.error("ftp连接失败",e);
			    return false;
		    }
	    }
        logger.info("开始上传文件:"+_file+"，到:"+remotePath + remoteFileName);
        File file = new File(_file);
	    boolean b = false;
	    BufferedInputStream bufferedInputStream = null;
	    try {
		    bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
		    if (ftp.isConnected() && ftp.isAvailable()) {
			    b = ftp.storeFile(remotePath + remoteFileName, bufferedInputStream);
		    } else {
			    logger.error("ftp没有连接");
			    throw new Exception("ftp没有连接成功");
		    }
	    } catch (FileNotFoundException e) {
		    logger.error("", e);
	    } catch (IOException e) {
		    logger.error("", e);
	    } catch (Exception e) {
		    logger.error("", e);
	    } finally {
		    try {
			    bufferedInputStream.close();
		    } catch (IOException e) {
			    logger.error(e.getLocalizedMessage());
		    }
	    }
	    if(b){
		    logger.info("上传成功:"+remotePath+remoteFileName);
	    }
	    return b;
    }

    public boolean downLoad(String remote,String local){
        if(ftp == null){
            try {
                login();
            } catch (IOException e) {
                logger.error("ftp连接失败",e);
                return false;
            }
        }
        logger.info("开始下载文件:"+remote+"，到:"+local);
        boolean b = false;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(local));
            b = ftp.retrieveFile(remote,bufferedOutputStream);
            if(b){
                logger.info("下载成功："+local);
            } else {
                logger.info("下载失败："+local);
            }
        } catch (FileNotFoundException e) {
            logger.error("",e);
        } catch (IOException e) {
            logger.error("",e);
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                logger.error("",e);
            }
        }
        return b;
    }
    public void logout(){
        try {
            if(ftp != null && ftp.isConnected()){
                ftp.logout();
            }
        } catch (IOException e) {
            logger.warn("ftp登出失败！");
        }
    }
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
