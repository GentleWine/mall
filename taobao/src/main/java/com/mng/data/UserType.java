package com.mng.data;

public enum UserType {
    CUSTOMER,
    SELLER,
    ADMIN;

    public static UserType getFromId(int id) throws IllegalArgumentException {
        int size = UserType.values().length;
        if (id >= size || id < 0) {
            throw new IllegalArgumentException("Invalid Usertype ID!");
        }
        return UserType.values()[id];
    }

    public static UserType getFromIdOrNull(int id) {
        try {
            return getFromId(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean isValidUserType(int id) {
        return getFromId(id) != null;
    }

    public int getId() {
        return ordinal();
    }
}
