package com.study.phonebook.controller;

import com.study.phonebook.domain.Contact;
import com.study.phonebook.domain.User;
import com.study.phonebook.repos.ContactRepo;
import com.study.phonebook.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired //Позволяет Spring работать с указанной базой данных в реализованном классе
    private ContactRepo contactRepo;

    @Autowired
    private MainService mainService;

    @Value("${upload.path}") //Spring ищет переменную в properties и вставляет её в указанную переменную
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
            contacts = contactRepo.findByAuthorAndSurname(user, filter);
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
            @Valid Contact contact, //Аннотация для запуска валидации
            //BindingResult должна идти обязательно ДО Model, иначе все ошибки будут идти во View
            BindingResult bindingResult, //Список сообщений ошибок валидации
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {
        contact.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("contact", contact);
        } else {
            mainService.saveAvatar(contact, file);

            //Если валидация прошла упешно, необходимо удалить выводимое сообщение
            model.addAttribute("contact", null);

            contactRepo.save(contact);
        }
        Iterable<Contact> contacts = contactRepo.findByAuthor(user);
        model.addAttribute("contacts", contacts);
        return "main";
    }

    @GetMapping("/contact/{id}")
    public String contact (@AuthenticationPrincipal User user, @PathVariable Integer id, Model model) {
        Iterable<Contact> viewContact = contactRepo.findByAuthorAndId(user, id);
        model.addAttribute("viewContact", viewContact);
        return "contact";
    }

    @PostMapping("/contact/{id}")
    public String edit(
            @AuthenticationPrincipal User user,
            @Valid Contact contact,
            BindingResult bindingResult,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String phone,
            @RequestParam("file") MultipartFile file,
            @PathVariable Integer id,
            Model model
    ) throws IOException {
        contact.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("contact", contact);
            Iterable<Contact> viewContact = contactRepo.findByAuthorAndId(user, id);
            model.addAttribute("viewContact", viewContact);
            return "contact";
        } else {
            Contact myContact = (contactRepo.findByAuthorAndId(user, id).get(0));
            myContact.setSurname(surname);
            myContact.setName(name);
            myContact.setPhone(phone);
            mainService.saveAvatar(myContact, file);
            contactRepo.save(myContact);
            return "redirect:/main";
        }
    }

    @GetMapping("delete/{id}")
    public String deleteContact(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        Contact contact = (contactRepo.findByAuthorAndId(user, id).get(0));
        File file = new File(uploadPath + "/" + contact.getFilename());
        if (file.delete()) {
            contactRepo.deleteByAuthorAndId(user, id);
        }
        return "redirect:/main";
    }

    @GetMapping("deleteIMG/{id}")
    public String deleteIMG(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        Contact contact = (contactRepo.findByAuthorAndId(user, id).get(0));
        File file = new File(uploadPath + "/" + contact.getFilename());
        if (file.delete()) {
            contact.setFilename(null);
            contactRepo.save(contact);
        }
        return "redirect:/contact/{id}";
    }
}