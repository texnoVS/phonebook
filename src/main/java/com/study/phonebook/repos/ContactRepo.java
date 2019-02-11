package com.study.phonebook.repos;

import com.study.phonebook.domain.Contact;
import com.study.phonebook.domain.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

//Позволяет работать с репозиторием, с помощью инструментов Spring
public interface ContactRepo extends CrudRepository<Contact, Integer> {
    List<Contact> findByAuthorAndSurname(User user, String Surname);
    List<Contact> findByAuthor(User user);
    List<Contact> findByAuthorAndId(User user, Integer Id);

    @Transactional
    Long deleteByAuthorAndId(User user, Integer Id);
}
