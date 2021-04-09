package com.mng.data;

public enum UserType {
    CUSTOMER(0),
    SELLER(1);

    private final int id;

    UserType(int number) {
        this.id = number;
    }

    public static UserType getFromId(int id) {
        switch (id) {
            case 0:
                return CUSTOMER;
            case 1:
                return SELLER;
            default:
                return null;
        }
    }

    public static boolean isValidUserType(int id) {
        return getFromId(id) != null;
    }

    public int getId() {
        return id;
    }
}
