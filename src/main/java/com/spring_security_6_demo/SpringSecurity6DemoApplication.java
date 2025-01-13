package com.spring_security_6_demo;

import com.spring_security_6_demo.entities.Role;
import com.spring_security_6_demo.entities.User;
import com.spring_security_6_demo.services.AccountServiceImpl;
import com.spring_security_6_demo.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.spring_security_6_demo")
public class SpringSecurity6DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurity6DemoApplication.class, args);
	}
	@Bean
	CommandLineRunner start(UserService userService){
			return args -> {
				userService.addNewUser(new User(null,"John", "Doe",null));
				userService.addNewUser(new User(null,"amal", "amal",null));
				userService.addNewUser(new User(null,"khaoula", "ouifaya",null));

				userService.addNewRole(new Role(null,"USER","Utilisateur",null));
				userService.addNewRole(new Role(null,"ADMIN","Administrateur",null));

				userService.addRoleToUser("John","USER");
				userService.addRoleToUser("khaoula","USER");
				userService.addRoleToUser("amal","ADMIN");
			};
	}
}
