package com.study.phonebook.controller;

import com.study.phonebook.domain.Contact;
import com.study.phonebook.domain.User;
import com.study.phonebook.repos.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private ContactRepo contactRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting (Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main (
            @AuthenticationPrincipal User user,
            @RequestParam (required = false, defaultValue = "") String filter,
            Model model
    ) {
        Iterable<Contact> contacts = contactRepo.findByAuthor(user);
        if (filter != null && !filter.isEmpty()) {
            contacts = contactRepo.findBySurnameAndAuthor(user, filter);
        } else {
            contacts = contactRepo.findByAuthor(user);
        }
        model.addAttribute("contacts", contacts);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add (
            @AuthenticationPrincipal User user,
            @RequestParam String surname,
            @RequestParam String name,
            @RequestParam("file") MultipartFile file,
            Map<String, Object> model
    ) throws IOException {
        Contact contact = new Contact(surname, name, user);

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

        contactRepo.save(contact);
        Iterable<Contact> contacts = contactRepo.findByAuthor(user);
        model.put("contacts", contacts);
        return "main";
    }

    @GetMapping("/profile/{contact}")
    public String profile(
            @AuthenticationPrincipal User user,
            @PathVariable Integer contact,
            Model model
    ) {
        Iterable<Contact> viewContact = contactRepo.findByAuthorAndId(user, contact);
        model.addAttribute("viewContact", viewContact);
        return "profile";
    }

    @PostMapping("/profile/{id}")
    public String edit(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam("file") MultipartFile file,
            @PathVariable Integer id
    ) throws IOException {
        Contact contact = (contactRepo.findByAuthorAndId(user, id).get(0));
        contact.setName(name);
        contact.setSurname(surname);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            contact.setFilename(resultFilename);
        }
        contactRepo.save(contact);
        return "redirect:/profile/{id}";
    }

    @GetMapping("delete/{id}")
    public String deleteContact(
            @AuthenticationPrincipal User user,
            @PathVariable Integer id
    ) {
        contactRepo.deleteByAuthorAndId(user, id);
        return "redirect:/main";
    }

    @GetMapping("deleteIMG/{id}")
    public String deleteIMG(
            @AuthenticationPrincipal User user,
            @PathVariable Integer id
    ) {
        Contact contact = (contactRepo.findByAuthorAndId(user, id).get(0));
        contact.setFilename(null);
        contactRepo.save(contact);
        return "redirect:/profile/{id}";
    }
}