package com.capg.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
     private String error;
     private String msg;
     @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-mm-yyyy hh:mm:ss")
     private LocalDateTime timestamp;
     
}
