package com.study.phonebook.service;

import com.study.phonebook.domain.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class MainService {
    @Value("${upload.path}") //Spring ищет переменную в properties и вставляет её в указанную переменную
    private String uploadPath;

    public void saveAvatar(Contact contact, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            contact.setFilename(resultFilename);
        }
    }
}
