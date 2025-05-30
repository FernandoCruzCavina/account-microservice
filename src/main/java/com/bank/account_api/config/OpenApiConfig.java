package com.bank.account_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Account API",
                description = "API for managing bank accounts. Develompment by: \n-Fernando Cruz Cavina; \n -Samuel do Carmo Carvalho.",
                contact = @Contact(
                        name = "GitHub",
                        url = "https://www.github.com/fernandocruzcavina/account-microservice"
                ),
                version = "1.1"
        )
        
)

@Configuration
public class OpenApiConfig {
}
