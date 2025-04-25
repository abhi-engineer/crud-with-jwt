package com.crud.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {
    private String errorMsg;
    private int errorCode;
    private LocalDateTime timeStamp;
}
