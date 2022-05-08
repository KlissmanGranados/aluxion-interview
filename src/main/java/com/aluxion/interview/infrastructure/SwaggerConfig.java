package com.aluxion.interview.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

/**
 * @author Klissman Granados
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.aluxion.interview"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Aluxion API",
                "API documentation for Aluxion interview",
                "1.0",
                "",
                new Contact("KlissmanGranados",
                        "https://github.com/KlissmanGranados/",
                        "granadosklissman@gmail.com"),
                "GNU",
                "https://raw.githubusercontent.com/KlissmanGranados/licenses/master/GNU",
                Collections.emptyList()
        );
    }
}
