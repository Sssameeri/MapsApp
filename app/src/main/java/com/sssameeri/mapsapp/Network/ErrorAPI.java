package com.sssameeri.mapsapp.Network;

public class ErrorAPI {
    private int statusCode;
    private String message;

    public ErrorAPI()
    {
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
