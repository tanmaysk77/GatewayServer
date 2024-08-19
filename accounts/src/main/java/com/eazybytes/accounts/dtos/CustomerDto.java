package com.eazybytes.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "Customer Schema",
        description = "This is Json format of Customer"
)
@Data                                                           // This is a combination of multiple annotation liken getter setter AllArgConstructor etc
public class CustomerDto {
    // We can do data filtering inside DTO class as we can send TO THE USER only required data from the dto classes
    // ALSO WE WILL ACCEPT ONLY LIMITED DATA WE WILL NOT ACCEPT THE CUSTOMER_ID

    @Schema(
            description = "Name of Customer",
            minLength = 5,
            maxLength = 30,
            example = "Ashok"
    )
    @NotEmpty(message = "Name should not be empty or null value")
    @Size(min = 3, max=30, message = "Length of name should be between 5-30 character")
    private String name;

    @Schema(
            description = "Email of Customer",
            example = "abc@gmail.com"
    )
    @Email(message = "Please check the email format")
    private String email;

    @Schema(
            description = "Mobile Number of Customer",
            pattern = "^$|[0-9]{10})",
            example = "9632587414"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Accounts Detail of customer"
    )
    private AccountsDto accountsDTO;
}
