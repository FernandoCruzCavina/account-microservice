package com.bank.account_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "User Authentication",
                contact = @Contact(
                        name = "Fernando Cruz Cavina",
                        url = "https://www.github.com/fernandocruzcavina",
                        email = "fernando.cruz.cavina@gmail.com"
                ),
                version = "1.0"
        )
        
)

@Configuration
public class OpenApiConfig {
}
