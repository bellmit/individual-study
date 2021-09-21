package cn.gyw.backend.fileserver.enums;

import cn.gyw.components.web.exceptions.BusinessExceptionAssert;
import org.springframework.web.multipart.MultipartFile;

public enum FileServerAssert implements BusinessExceptionAssert {

    FILE_NOT_EMPTY(60001, "upload file must not empty"),
    FILE_ID_NOT_EMPTY(60002, "file id must not empty"),
    UPLOAD_SUCCESS(60003, "upload failed")
    ;
    
    private int code;
    private String message;

    FileServerAssert(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void assertNotEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw newException(file);
        }
    }
    
    public void assertNotEmpty(String id) {
        if (id.isEmpty()) {
            throw newException(id);
        }
    }
}
