package com.study.phonebook.repos;

import com.study.phonebook.domain.Contact;
import com.study.phonebook.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepo extends CrudRepository<Contact, Integer> {
    List<Contact> findBySurnameAndAuthor(User user, String Surname);
    List<Contact> findByAuthor(User user);
    List<Contact> findByAuthorAndId(User user, Integer Id);
//    void deleteFilenameById(Integer Id);
}
