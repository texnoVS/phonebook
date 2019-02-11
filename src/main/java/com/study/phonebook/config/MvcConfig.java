package com.study.phonebook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Конфигурация веб слоя, система авторизации от Spring
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    //
    public void addViewControllers(ViewControllerRegistry registry) {
        //Первый параметр (url), второй (имя шаблона)
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**") //Все файли по пути img будут перенаправляться
                .addResourceLocations("file://" + uploadPath + "/"); //в текущую директорию
        registry.addResourceHandler("/static/**") //При обращении к этому пути
                .addResourceLocations("classpath:/static/"); //будет происходить поиск от начала дерева каталогов
    }
}