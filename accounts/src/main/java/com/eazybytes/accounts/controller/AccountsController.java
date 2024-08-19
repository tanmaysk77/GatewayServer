package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dtos.AccountsContactInfoDto;
import com.eazybytes.accounts.dtos.CustomerDto;
import com.eazybytes.accounts.dtos.ErrorResponseDTO;
import com.eazybytes.accounts.dtos.ResponseDTO;
import com.eazybytes.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD REST APIS FOR ACCOUNT IN EAZYBANK",
        description = "CRUD Apis to perform Create, Update, Delete, Get operations"
) // Annotation used for Description for entire controller
@RestController // I am instructing application i am going to write http methods // Accordingly expose those methods as resp apis
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated// this tells springboot to apply validations on all of my methods
public class AccountsController {

    private final IAccountService iAccountService;
    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @Autowired
    Environment environment;                                                            // Used to access  the environemnt variables

    @Autowired
    AccountsContactInfoDto accountsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;                                                        //As we rely on spring dependency injection here  It is not able to find bean of String so we removed all args constructor
    // In case of DTO classes with spring type fields it is not dependent on dependency injection so it works there when AllArgConsrucotr is used


    // We cannot autowire this in constructor as springboot is not handling dependency injection of these class as we are getting the objects using some configuration


    AccountsController( IAccountService iAccountService){
        this.iAccountService = iAccountService;                                 // No need to use @Autowired on top of constructor as we have single constructor
    }                                                                                               // We removed  @AllArgsConstructor as it is not able to find bean of  configuration management classes like Environment , @ConfigurationProperties,, @Value


    @Operation(
            summary = "Create account REST API ",
            description = "Rest Api to create Account"
    )   // Annotation specific to methods
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Http Status Created"
        ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(                                                                                                 // Define this to see the ErrorResponseDTO is visble in swagger as it is used in global exception it was not visible it must be in controller
                            schema = @Schema(implementation =  ErrorResponseDTO.class)
                    )
            )
    })
@PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(
            @RequestHeader("eazybank-correlation-id") String correlationId,
            @Valid @RequestBody CustomerDto customerDTO
    )       // @Valid wll tell to validate all the validation in customerDTO
{                                                                                                                                                                                   // But how wil these errors be handled so we will extend out Global Excpetion class and overide one method to handle these exceptions
    logger.debug("eazy-bank-correaltionIdFound ",correlationId);
    iAccountService.createAccountService(customerDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)                                                                                                             // Sending Meta Data information
            .body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_201));
}






    @Operation(
            summary = "Fetch account REST API ",
            description = "Rest Api to fetch Account Details"
    )   // Annotation specific to methods
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status Ok"
            ), // /api/fetch?mobileNumber=John&key=78
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(                                                                                                 // Define this to see the ErrorResponseDTO is visble in swagger as it is used in global exception it was not visible it must be in controller
                            schema = @Schema(implementation =  ErrorResponseDTO.class)
                    )
            )
    })
@GetMapping("/fetch")
public ResponseEntity<CustomerDto> fetchAccountDetails(
        @RequestParam
       @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
       String mobileNumber
    )   // This name and name in request of postman should match
{
CustomerDto filteredCustomerData = iAccountService.fetchAccount(mobileNumber);

return ResponseEntity
        .status(HttpStatus.OK)
        .body(filteredCustomerData);
}




    @Operation(
            summary = "Update account REST API ",
            description = "Rest Api to update Account"
    )   // Annotation specific to methods
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Http Status Created"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Exception Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(                                                                                                 // Define this to see the ErrorResponseDTO is visble in swagger as it is used in global exception it was not visible it must be in controller
                            schema = @Schema(implementation =  ErrorResponseDTO.class)
                    )
            )
    }
    )
@PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccount(
            @Valid @RequestBody CustomerDto customerDto
    )       // @Valid This tells to validate all validation in DTO
{
   boolean isUpdated =  iAccountService.updateAccount(customerDto);
    if(isUpdated)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
    }
    else {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_500));
    }
}





    @Operation(
            summary = "Delete account REST API ",
            description = "Rest Api to delete Account"
    )   // Annotation specific to methods
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Http Status Created"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Exception Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(                                                                                                 // Define this to see the ErrorResponseDTO is visble in swagger as it is used in global exception it was not visible it must be in controller
                    schema = @Schema(implementation =  ErrorResponseDTO.class)
            )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccountDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber
) {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "BuildVersion account REST API ",
            description = "Rest Api to get the build version "
    )   // Annotation specific to methods
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status Ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(                                                                                                 // Define this to see the ErrorResponseDTO is visble in swagger as it is used in global exception it was not visible it must be in controller
                            schema = @Schema(implementation =  ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "JAVA Version REST API ",
            description = "Rest Api to get the Java version "
    )   // Annotation specific to methods
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status Ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(                                                                                                 // Define this to see the ErrorResponseDTO is visble in swagger as it is used in global exception it was not visible it must be in controller
                            schema = @Schema(implementation =  ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getMavenVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }


    @Operation(
            summary = "Get Contact Info REST API ",
            description = "Rest Api to get the Contact Information "
    )   // Annotation specific to methods
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status Ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(                                                                                                 // Define this to see the ErrorResponseDTO is visble in swagger as it is used in global exception it was not visible it must be in controller
                            schema = @Schema(implementation =  ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }
}
