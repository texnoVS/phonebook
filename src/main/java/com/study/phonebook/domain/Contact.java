package com.study.phonebook.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Please fill the surname")
    @Length(max = 255, message = "Surname too long (more then 255)")
    private String surname;
    @Length(max = 255, message = "Name too long (more then 255)")
    private String name;
    @Length(max = 255, message = "Number too long (more then 255)")
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Contact() {
    }

    public Contact(
            @NotBlank(message = "Please fill the surname") @Length(max = 255, message = "Surname too long (more then 255)") String surname,
            @Length(max = 255, message = "Name too long (more then 255)") String name,
            @Length(max = 255, message = "Number too long (more then 255)") String phone,
            User user
    ) {
        this.surname = surname;
        this.name = name;
        this.phone = phone;
        this.author = user;
    }

    //    public Contact(
//            String surname,
//            String name,
//            User user
//    ) {
//        this.surname = surname;
//        this.name = name;
//        this.author = user;
//    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
