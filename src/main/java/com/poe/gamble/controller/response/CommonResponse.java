package com.poe.gamble.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public class CommonResponse<T> {
    private int status;
    private List<String> message;
    private T data;

    public static <T> CommonResponse<T> success(T data, CommonCode code) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(code.getStatus());
        response.setMessage(Arrays.asList("SUCCESS", code.getMessage()));
        response.setData(data);
        return response;
    }


    public static <T> CommonResponse<T> fail(CommonCode code) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(code.getStatus());
        response.setMessage(Arrays.asList("FAIL", code.getMessage()));
        response.setData(null);
        return response;
    }

    public static <T> CommonResponse<T> error(CommonCode code) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(code.getStatus());
        response.setMessage(Arrays.asList("ERROR", code.getMessage()));
        response.setData(null);
        return response;
    }
}
