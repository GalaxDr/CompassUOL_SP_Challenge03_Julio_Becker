package br.com.compassuol.sp.challenge.apigateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
