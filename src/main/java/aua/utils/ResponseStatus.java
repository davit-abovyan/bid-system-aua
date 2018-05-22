package aua.utils;

import java.io.Serializable;

public class ResponseStatus implements Serializable {
    private int status;
    private String message;

    public ResponseStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
