package com.binar.binarfud.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui/index.html");
    }

//    @Bean
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.binar.binarfud.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo(){
//        ApiInfo apiInfo = new ApiInfo(
//                "Binarfud API",
//                "ini adalah api binarfud dengan spring boot 2.7.x",
//                "v1.7.8",
//                "Term of Service",
//                new Contact("Fazarhdyt", "www.fazarhdyt.com", "m.fajar184@gmail.com"),
//                "Apache License",
//                "www.apache.com",
//                Collections.emptyList()
//        );
//        return apiInfo;
//    }
}
