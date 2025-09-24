package com.jpdev.dscatalog.resources.exceptions;

import java.time.Instant;

public class StandardError {

    private Instant timestamp;
    private Integer stattus;
    private String error;
    private String message;
    private String path;

    public StandardError() {
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStattus() {
        return stattus;
    }

    public void setStattus(Integer stattus) {
        this.stattus = stattus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
