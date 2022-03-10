package com.letscode.starwar.doc;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.letscode.starwar"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Star Wars Resistence")
                .version("1.0.0")
                .description(buildDescription().toString())
                .license("Apache License Version 2.0")
                .build();
    }

    private StringBuilder buildDescription() {
        StringBuilder text = new StringBuilder();
        text.append("API de cadastro de Rebeldes");
        return text;
    }

}
