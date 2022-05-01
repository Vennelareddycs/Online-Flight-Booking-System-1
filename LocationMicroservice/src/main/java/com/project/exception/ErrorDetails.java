package com.project.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
	private String errormsg;
	private String errorcode;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy hh.mm.ss")
	private LocalDateTime timestamp;

}
