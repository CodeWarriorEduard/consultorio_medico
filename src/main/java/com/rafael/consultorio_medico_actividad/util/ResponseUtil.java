package com.rafael.consultorio_medico_actividad.util;


import java.util.List;

public class ResponseUtil {

    public static <T> ApiResponse<T> errorResponse(int errorCode, String errorMessage, List<String> errors) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setErrorCode(errorCode);
        apiResponse.setMessage(errorMessage);
        apiResponse.setData(null);
        apiResponse.setErrors(errors);
        apiResponse.setTimestamp(System.currentTimeMillis());
        return apiResponse;
    }
}
