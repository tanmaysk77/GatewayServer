package com.eazybytes.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "Error Response Schema",
        description = "This is Json format of Error Response"
)
@Data
@AllArgsConstructor
public class ErrorResponseDTO {

   @Schema(
           description = "Api path of Error",
           example = "/create"
   )
   private String apiPath;

   @Schema(
           description = "Error Code which is error specific",
           example = "500"
   )
   private HttpStatus errorCode;

   @Schema(
           description = "Error message",
           example = "Check the request body"
   )
   private String errorMessage;

   @Schema(
           description = "Error Time",
           example = "2024-08-11T15:56:38.1385739"
   )
   private  LocalDateTime errorTime;

}
