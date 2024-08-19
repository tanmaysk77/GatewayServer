package com.eazybytes;

import com.eazybytes.accounts.dtos.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Note if you have creates some component classes outside of the base  package that has your main class "AccountsApplication"
// (class having @springboot Application annotation) then you need to follow this steps
/*
@ComponentScans(
{ @ComponentScan("com.eazybytes.accounts.controller","com.eazybytes.accounts.service") })
@EnableJpaRepositories("com.eazybytes.accounts.repository")
@EntityScan("com.eazybytes.accounts.model")
*/
// Note if you hva eto exclude some components from the base package you can follow this approach
/*
@ComponentScan(
		basePackages = {"com.eazybytes.accounts"},  // Specify the base package for scanning
		excludeFilters = {
				@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ExcludedClass1.class, ExcludedClass2.class}),
				@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.eazybytes.accounts.exclude.*")
		}
)
*/
@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})// Used to enable  configuration class properties
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice documentation",
				description = "Eazy Bank accounts microservice documentation ",
				version = "v1",
				contact = @Contact(
						name = "Tanmay Sk",
						email = "tsk@yahoo.com",
						url = "somerandomurl.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.eazybytes.com"
				)
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
