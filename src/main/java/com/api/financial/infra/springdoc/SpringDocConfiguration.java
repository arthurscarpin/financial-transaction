package com.api.financial.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Financial API")
                        .description("API REST de cadastro, exclusão e geração de estatísticas de transações financeiras.")
                        .contact(new Contact()
                                .name("Arthur Scarpin")
                                .email("scarpinarthur.dev@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://financial/api/licenca")));
    }
}