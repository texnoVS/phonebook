package com.study.phonebook.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    //Поле статическое, тк не в одном другом package оно не нужно
    static Map<String, String> getErrors(BindingResult bindingResult) {
        //Преобразование fieldError в ходовой вариант
        //К полученному имени ошибки, добавляю строку Error для удобного использования во View
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }
}