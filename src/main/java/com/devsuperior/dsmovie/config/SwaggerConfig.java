package com.devsuperior.dsmovie.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	private List<SecurityContext> securityContext() {
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReference()).build());
	}

	private List<SecurityReference> securityReference() {
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scope }));
	}

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.securityContexts(securityContext())
        		.securitySchemes(Arrays.asList(apiKeys()))
        		.select()
        		.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        		.paths(PathSelectors.any())
        		.build();
    }
}
