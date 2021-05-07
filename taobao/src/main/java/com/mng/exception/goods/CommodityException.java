package com.mng.exception.goods;

/**
 * Commodity related exceptions
 */
public abstract class CommodityException extends Exception {
    public CommodityException(String msg) {
        super(msg);
    }
}
