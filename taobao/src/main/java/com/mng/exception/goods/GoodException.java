package com.mng.exception.goods;

/**
 * Commodity related exceptions
 */
public abstract class GoodException extends Exception {
    public GoodException(String msg) {
        super(msg);
    }
}
