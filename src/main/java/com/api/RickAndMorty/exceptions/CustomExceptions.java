package com.api.RickAndMorty.exceptions;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author erdmcagri
 */
@ControllerAdvice
public class CustomExceptions  extends RuntimeException {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public String NumberFormatException() {
        Date date = new Date();
        JSONObject jo = new JSONObject();
        jo.put("timestamp", sdf.format(date));
        jo.put("status", "" + HttpStatus.NOT_ACCEPTABLE);
        jo.put("defaultMessage", "This input not allowed");

        return jo.toString();

    }

    public String RecordNotFoundException() {
        Date date = new Date();
        JSONObject jo = new JSONObject();
        jo.put("timestamp", sdf.format(date));
        jo.put("status", "" + HttpStatus.NO_CONTENT);
        jo.put("defaultMessage", "Data not found");

        return jo.toString();

    }



}
