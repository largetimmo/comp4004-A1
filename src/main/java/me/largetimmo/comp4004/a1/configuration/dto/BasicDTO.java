package me.largetimmo.comp4004.a1.configuration.dto;


import lombok.Data;

@Data
public class BasicDTO<T> {

    private T data;
    private String type;
    private String action;

}
