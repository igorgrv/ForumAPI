package com.forum.forum.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.forum.forum.model.User;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.forum.forum"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(User.class);
//                .globalOperationParameters(
//                        Arrays.asList(
//                                	new ParameterBuilder()
//                                    .name("Authorization")
//                                    .description("Token JWT Header")
//                                    .modelRef(new ModelRef("string"))
//                                    .parameterType("header")
//                                    .required(false)
//                                    .build() 
//                                    )
//                 );
    }
}
