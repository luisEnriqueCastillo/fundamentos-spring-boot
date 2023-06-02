package com.fundamentosplatzi.springboot.fundamentos.controller;

import com.fundamentosplatzi.springboot.fundamentos.caseuse.CreateUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.DeleteUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.GetUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.UpdateUser;
import com.fundamentosplatzi.springboot.fundamentos.entity.Users;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserRestController {

    private GetUser getUser;
    private CreateUser createUser;
    private UpdateUser updateUser;
    private DeleteUser deleteUser;

    public UserRestController(GetUser getUser, CreateUser createUser, UpdateUser updateUser, DeleteUser deleteUser) {
        this.getUser = getUser;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.deleteUser = deleteUser;
    }


    @GetMapping("/")
    List<Users> get(){
        return  getUser.getAll();
    }

    @PostMapping("/")
    ResponseEntity<Users> newUser (@RequestBody Users newUser){
        return new ResponseEntity<>(createUser.save(newUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteUser(@PathVariable Long id){
        deleteUser.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    ResponseEntity<Users> replaceUser(@RequestBody Users newUser , @PathVariable Long id){
        return new ResponseEntity<Users>(updateUser.update(newUser, id), HttpStatus.OK);
    }

    /*@GetMapping("/pageable")
    List<Users> getUserPageable(@RequestParam int page, @RequestParam Long id){
        Page<Users> list = userService.findAll(pageable);
        return list;
    }*/
}
