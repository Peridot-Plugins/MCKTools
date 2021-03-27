package me.mckoxu.mcktools.enums;

public enum ErrorType {

    //MYSQL ERRORS
    MYSQL_CANTCONNECT("[MYSQL] Can't connect to mysql database!"),
    MYSQL_CANTCREATETABLE("[MYSQL] Can't create a table!"),
    MYSQL_CANTCREATEROW("[MYSQL] Can't create a row in database!"),
    MYSQL_CANTREMOVE("[MYSQL] Can't remove data from database!"),
    MYSQL_CANTSAVE("[MYSQL] Can't save data in database!"),
    MYSQL_CANTGET("[MYSQL] Can't get data from database!"),

    //DATA ERRORS
    DATA_CANTSAVE("[DATA] Can't save data!"),

    //FILE ERRORS
    FILE_CANTCREATE("[FILE] Can't create file!"),

    //UPDATE CHECK ERRORS
    UPDATE_CANTCHECKUPDATE("[UPDATE CHECK] Can't check update!"),

    //HEAD CREATE ERRORS
    HEAD_CANTCREATEHEAD("[HEAD] Can't create a head!");

    String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
