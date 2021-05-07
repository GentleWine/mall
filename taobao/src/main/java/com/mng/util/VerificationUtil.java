package com.mng.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
@SuppressWarnings("unused")
public final class VerificationUtil {

    public static boolean anyIsNull(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean anyIsEmpty(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                return true;
            }
            if (arg instanceof CharSequence) {
                if (((CharSequence) arg).isEmpty()) {
                    return true;
                }
            }
            if (arg instanceof Collection<?>) {
                if (((Collection<?>) arg).isEmpty()) {
                    return true;
                }
            }
            if (arg instanceof Iterable<?>) {
                if (!((Iterable<?>) arg).iterator().hasNext()) {
                    return true;
                }
            }
            if (arg instanceof Object[]) {
                if (((Object[]) arg).length == 0) {
                    return true;
                }
            }
            if (arg instanceof int[]) {
                if (((int[]) arg).length == 0) {
                    return true;
                }
            }
            if (arg instanceof long[]) {
                if (((long[]) arg).length == 0) {
                    return true;
                }
            }
            if (arg instanceof float[]) {
                if (((float[]) arg).length == 0) {
                    return true;
                }
            }
            if (arg instanceof double[]) {
                if (((double[]) arg).length == 0) {
                    return true;
                }
            }
            if (arg instanceof byte[]) {
                if (((byte[]) arg).length == 0) {
                    return true;
                }
            }
            if (arg instanceof char[]) {
                if (((char[]) arg).length == 0) {
                    return true;
                }
            }
            if (arg instanceof boolean[]) {
                if (((boolean[]) arg).length == 0) {
                    return true;
                }
            }
            if (arg instanceof short[]) {
                if (((short[]) arg).length == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
