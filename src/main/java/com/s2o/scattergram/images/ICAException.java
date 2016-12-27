package com.s2o.scattergram.images;
/**
 * Created by blancof1 on 07/12/2016.
 */
public class ICAException extends Exception {

    public ICAException(String msg) {
        super(msg);
    }

    public ICAException(String msg, Exception exception) {
        super(msg, exception);
    }
}
