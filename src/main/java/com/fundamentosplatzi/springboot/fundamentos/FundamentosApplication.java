package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.Users;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class FundamentosApplication implements CommandLineRunner {
private ComponentDependency componentDependency;
private MyBean myBean;
private MyBeanWithDependency myBeanWithDependency;
private MyBeanWithProperties myBeanWithProperties;
private UserPojo userPojo;
private UserRepository userRepository;
private UserService userService;

public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties,UserPojo userPojo,UserRepository userRepository, UserService userService){
	this.componentDependency = componentDependency;
	this.myBean = myBean;
	this.myBeanWithDependency = myBeanWithDependency;
	this.myBeanWithProperties = myBeanWithProperties;
	this.userPojo = userPojo;
	this.userRepository = userRepository;
	this.userService = userService;


}
	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//	ejemplosAnteriore();
	saveUsersInDataBase();
	log.info("salio");
	getInformationJpglFromUser();
	saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		Users test1 = new Users("TestTransactional1", "TestTransactional1@domain.com", LocalDate.now());
		Users test2 = new Users("TestTransactional2", "TestTransactional2@domain.com", LocalDate.now());
		Users test3 = new Users("TestTransactional3", "TestTransactional3@domain.com", LocalDate.now());
		Users test4 = new Users("TestTransactional4", "TestTransactional4@domain.com", LocalDate.now());
	List<Users> users = Arrays.asList(test1,test2,test3,test4);
	try {
		userService.saveTransaccional(users);
	}catch (Exception e){
		log.error("Esta es una exception dentro del metodo transaccional"+e);
	}

	userService.getAllUsers()
			.stream().
			forEach(users1 -> log.info("Este es el usuario del metodo transactinal"+users1));
}

	private void getInformationJpglFromUser(){
		/*log.info("Usuario con el metodo de findBY " + userRepository.findByEmail("john@domain.com")
				.orElseThrow(()-> new RuntimeException("No se encontro el usuario")));

		userRepository.findAndShort("Marco", Sort.by("id").descending())
				.stream()
				.forEach(users -> log.info("User con metodo sort"+users));

		userRepository.findByEmail("John").stream().forEach(users -> log.info("Usuario con query method" + users));
		log.info("usuario con query method"+userRepository.findByEmailAndName("karen@domain.com","Karen").orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

		userRepository.findByNameLike("%J%")
				.stream()
				.forEach(users -> log.info("Usuario findByNameLike" + users));
		userRepository.findByNameOrEmail(null,"marco@domain.com")
				.stream()
				.forEach(users -> log.info("Usuario findBynameOrEmail"+users));*/

	userRepository
			.findByBirthDateBetween(LocalDate.of(2021,3,1),LocalDate.of(2021,04,2))
			.stream().
			forEach(users -> log.info("Usuario con intervalo de fechas: "+ users));

	userRepository
			.findByNameLikeOrderByIdDesc("%Marco%")
			.stream()
			.forEach(users -> log.info("Usuarios encontrado con like y ordenado"+ users));

	userRepository
			.findByNameContainingOrderByIdDesc("Marco")
			.stream()
			.forEach(users -> log.info("Usuario encontrado con container" + users));

	log.info("El usuario a partir del named parameter es: "
			+ userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021, 3, 13),"john@domain.com")
			.orElseThrow(() -> new RuntimeException("No se encontro el usuario a partir del named parameter")));
	}

	private void saveUsersInDataBase(){
		Users users1 = new Users("John", "john@domain.com", LocalDate.of(2021, 3, 13));
		Users users2 = new Users("Marco", "marco@domain.com", LocalDate.of(2021, 12, 8));
		Users users3 = new Users("Daniela", "daniela@domain.com", LocalDate.of(2021, 9, 8));
		Users users4 = new Users("Marisol", "marisol@domain.com", LocalDate.of(2021, 6, 18));
		Users users5 = new Users("Karen", "karen@domain.com", LocalDate.of(2021, 1, 1));
		Users users6 = new Users("Carlos", "carlos@domain.com", LocalDate.of(2021, 7, 7));
		Users users7 = new Users("Enrique", "enrique@domain.com", LocalDate.of(2021, 11, 12));
		Users users8 = new Users("Marco", "luis@domain.com", LocalDate.of(2021, 2, 27));
		Users users9 = new Users("Paola", "paola@domain.com", LocalDate.of(2021, 4, 10));
		List<Users> usersList = Arrays.asList(users1, users2, users3, users4, users5, users6, users7, users8, users9);
		usersList.stream().forEach(userRepository::save);
	}

	public void ejemplosAnteriore(){
		componentDependency.doSaludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + " - "+ userPojo.getPassword());
	}
}
