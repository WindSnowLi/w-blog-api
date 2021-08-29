package com.hiyj.blog.doc;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.hiyj.blog.object.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {

    private OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public void setOpenApiExtensionResolver(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean
    public Docket createRestApi() {
        List<Response> responseMessageList = new ArrayList<>();
        responseMessageList.add(
                new ResponseBuilder().code(String.valueOf(Msg.CODE_SUCCESS)).build()
        );

        responseMessageList.add(
                new ResponseBuilder().code(String.valueOf(Msg.CODE_FAIL)).build()
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.POST, responseMessageList)
                .globalResponses(HttpMethod.GET, responseMessageList)
                .globalResponses(HttpMethod.PUT, responseMessageList)
                .globalResponses(HttpMethod.DELETE, responseMessageList)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().host("http://127.0.0.1:8888")
                .apiInfo(new ApiInfoBuilder()
                        .title("w-blog-api接口")
                        .description("w-blog-api接口")
                        .contact(new Contact("WindSnowLi", "https://www.blog.firstmeet.xyz/", "windsnowli@163.com"))
                        .license("The MIT License")
                        .licenseUrl("https://github.com/WindSnowLi/w-blog-api/blob/master/LICENSE")
                        .version("0.0.1")
                        .build())
                .extensions(openApiExtensionResolver.buildExtensions("md"));
    }
}