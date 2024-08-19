package com.eazybytes.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts Schema",
        description = "This is Json format of Accounts"
)
@Data                                                           // This is a combination of multiple annotation liken getter setter AllArgConstructor etc
public class AccountsDto {
    // We can do data filtering inside DTO class as we can send only required data from the dto classes

    @Schema(
            description = "Account number of user"
    )
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private  Long accountNumber;

    @Schema(
            description = "Account type of users",
            example = "Savings"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema(
            description = "Address of branch"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}
