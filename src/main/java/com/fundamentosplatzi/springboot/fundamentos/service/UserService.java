package com.fundamentosplatzi.springboot.fundamentos.service;

import com.fundamentosplatzi.springboot.fundamentos.entity.Users;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void  saveTransaccional(List<Users> users){
        users.stream()
                .peek(users1 -> log.info("Usuario Insertado"+ users1))
                .forEach(userRepository::save);
        //.forEach(users1 -> userRepository.save(users1))
    }
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users save(Users newUser) {
        return userRepository.save(newUser);
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public Users update(Users newUser, Long id) {
       return userRepository.findById(id)
                .map(users -> {
                    users.setEmail(newUser.getEmail());
                    users.setName(newUser.getName());
                    users.setBirthDate(newUser.getBirthDate());
                    return userRepository.save(users);
                }).orElseThrow(() -> new RuntimeException("No se encontro usuario a modificar"));

    }
}
