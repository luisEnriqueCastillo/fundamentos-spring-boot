package com.fundamentosplatzi.springboot.fundamentos.repository;

import com.fundamentosplatzi.springboot.fundamentos.dto.UserDto;
import com.fundamentosplatzi.springboot.fundamentos.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    @Query("Select u From Users u Where u.email=?1")
    Optional<Users> findByEmail(String email);

    @Query("Select u From Users u Where u.name like ?1%")
    List<Users> findAndShort(String name, Sort sort);

    List<Users>findByName(String name);
    Optional<Users>findByEmailAndName(String email, String name);
    List<Users> findByNameLike(String name);
    List<Users>findByNameOrEmail(String name, String email);
    List<Users>findByBirthDateBetween(LocalDate begin, LocalDate end);
    List<Users> findByNameLikeOrderByIdDesc(String name);
    List<Users> findByNameContainingOrderByIdDesc(String name);
    @Query("SELECT new com.fundamentosplatzi.springboot.fundamentos.dto.UserDto(u.id,u.name,u.birthDate) FROM Users u WHERE u.birthDate=:parametroFecha AND u.email=:parametroEmail")
    Optional<UserDto>getAllByBirthDateAndEmail(@Param("parametroFecha") LocalDate date,@Param("parametroEmail") String email);

    //List<Users> findAll();
}
