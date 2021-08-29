package com.hiyj.blog.config;

import com.hiyj.blog.interceptor.CodeInterceptor;
import com.hiyj.blog.interceptor.CrosInterceptor;
import com.hiyj.blog.interceptor.MappingInterceptor;
import com.hiyj.blog.interceptor.PermissionsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 *
 * @author windSnowLi
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private CrosInterceptor cros;

    @Autowired
    public void setCros(CrosInterceptor cros) {
        this.cros = cros;
    }

    private MappingInterceptor mappingInterceptor;

    @Autowired
    public void setMappingInterceptor(MappingInterceptor mappingInterceptor) {
        this.mappingInterceptor = mappingInterceptor;
    }

    private PermissionsInterceptor permissionsInterceptor;

    @Autowired
    public void setPassTokenInterceptor(PermissionsInterceptor permissionsInterceptor) {
        this.permissionsInterceptor = permissionsInterceptor;
    }


    private CodeInterceptor codeInterceptor;

    @Autowired
    public void setCodeInterceptor(CodeInterceptor codeInterceptor) {
        this.codeInterceptor = codeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(codeInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger**/**", "/webjars/**", "/v3/**", "/doc.html");
        registry.addInterceptor(cros)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger**/**", "/webjars/**", "/v3/**", "/doc.html");
        registry.addInterceptor(mappingInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger**/**", "/webjars/**", "/v3/**", "/doc.html");
        registry.addInterceptor(permissionsInterceptor)
                // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger**/**", "/webjars/**", "/v3/**", "/doc.html");
    }
}
