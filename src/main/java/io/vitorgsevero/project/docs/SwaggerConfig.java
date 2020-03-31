package io.vitorgsevero.project.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                        .apis(RequestHandlerSelectors.basePackage("io.vitorgsevero.project.endpoint"))
                        .paths(regex("/v1.*"))
                        .build()
                    .globalOperationParameters(Collections.singletonList(new ParameterBuilder()
                        .name("Authorization")
                        .description("Bearer token")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(true)
                        .build()))
                .apiInfo(metaData());
    }
    
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Todos by Vítor Severo")
                .description("To do list API")
                .version("1.0")
                .contact(new Contact("Vítor Severo", "https://vitorgsevero.github.io/myPortfolio/", "vitorgsevero@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.apache.org/license/LICENSE-2.0")
                .build();
    }

}
