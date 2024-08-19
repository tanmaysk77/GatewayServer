package com.example.cards.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

//@ConfigurationProperties(prefix = "cards")           // prefix in yml fil
//public record CardsContactInfoDto(
//        String message,
//        Map<String,String> contactDetails,
//        List<String> onCallSupport
//        ) {
//        // It is a special class in java17
//    // When you use record classes you can initialize the data only once
//    // and when the object is created it is going to be final
//    // It creates a constructor and getters by default no setters
//}

@Getter
@Setter
@ConfigurationProperties(prefix = "cards")
public class CardsContactInfoDto {
        String message;
        Map<String,String> contactDetails;
        List<String> onCallSupport;
}
// Record class fields are final so once object is created it cannot be updated so we cannot add properties at runtime thats why we changed from record to a nortmal class to update peroperties at runtime