package com.eazybytes.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "ResponseData",
        description = "Schema for Response"
)
@Data
@AllArgsConstructor
public class ResponseDTO {
    @Schema(
            description = "Status Code of error"
    )
    private String statusCode;
    @Schema(
            description = "Status Message of errors"
    )
    private String statusMessage;
}
