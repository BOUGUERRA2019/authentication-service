package com.mdeaf;

import com.mdeaf.Entities.AppRole;

import com.mdeaf.Service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	CommandLineRunner start(AccountService accountService){
//		return args->{
//
//			accountService.saveRole(new AppRole("USER"));
//			accountService.saveRole(new AppRole("ADMIN"));
//
//			Stream.of("user1","user2","user3","user4","admin").forEach(user->{
//				accountService.saveUser(user,"123", "123");
//				System.out.println(accountService.findUserByUsername(user).getPassword());
//
//			});
//
//			accountService.addRoleToUser("admin", "ADMIN");
//
//		};
//	}

	@Bean
	BCryptPasswordEncoder getBCPE() {

		return new BCryptPasswordEncoder();
	}

}
