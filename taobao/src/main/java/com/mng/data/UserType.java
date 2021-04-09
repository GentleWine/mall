package com.mng.data;

public enum UserType {
    NORMAL(0),
    ADMIN(1);

    private final int id;

    UserType(int number) {
        this.id = number;
    }

    public int getId() {
        return id;
    }

    public static UserType getFromId(int id) {
        switch (id) {
            case 0:
                return NORMAL;
            case 1:
                return ADMIN;
            default:
                return null;
        }
    }

    public static boolean isValidUserType(int id) {
        return getFromId(id) != null;
    }
}
