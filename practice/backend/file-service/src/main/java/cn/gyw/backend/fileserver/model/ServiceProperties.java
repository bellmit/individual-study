package cn.gyw.backend.fileserver.model;

public class ServiceProperties {
    // 存储目录
    private String storageDir;
    // Markdown 文件访问 url正则表达式
    private String mdUrlPattern;
    // Markdown 文件本地磁盘路径
    private String mdDiskDir;

    public String getStorageDir() {
        return storageDir;
    }

    public void setStorageDir(String storageDir) {
        this.storageDir = storageDir;
    }

    public String getMdUrlPattern() {
        return mdUrlPattern;
    }

    public void setMdUrlPattern(String mdUrlPattern) {
        this.mdUrlPattern = mdUrlPattern;
    }

    public String getMdDiskDir() {
        return mdDiskDir;
    }

    public void setMdDiskDir(String mdDiskDir) {
        this.mdDiskDir = mdDiskDir;
    }
}
