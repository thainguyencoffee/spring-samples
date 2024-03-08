package com.boot.ch04.exception;

public class UrlNotAccessibleException extends RuntimeException{
    private String url;

    public UrlNotAccessibleException(String url) {
        super(url, null);
    }

    public UrlNotAccessibleException(Throwable cause, String url) {
        super("URL " + url + " is not accessible", cause);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
