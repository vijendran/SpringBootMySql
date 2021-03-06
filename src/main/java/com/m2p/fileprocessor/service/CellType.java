package com.m2p.fileprocessor.service;

public enum CellType {

    _NONE(-1),


    NUMERIC(0),

    STRING(1),


    FORMULA(2),


    BLANK(3),


    BOOLEAN(4),


    ERROR(5);


    private final int code;


    private CellType(int code) {
        this.code = code;
    }


}
