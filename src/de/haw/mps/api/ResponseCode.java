package de.haw.mps.api;

public enum ResponseCode {

    OK(200),
    ALREADYDONE(208),
    NOTFOUND(404),
    FORBIDDEN(403),
    ERROR(500);

    private int num;

    ResponseCode(int num) {
        this.num = num;
    }
}
