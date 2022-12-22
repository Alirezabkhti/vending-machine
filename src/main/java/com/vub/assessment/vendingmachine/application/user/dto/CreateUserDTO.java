package com.vub.assessment.vendingmachine.application.user.dto;

import java.util.UUID;

import com.vub.assessment.vendingmachine.domain.user.User;
import com.vub.assessment.vendingmachine.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserDTO {

	protected String username;
	protected String password;
	protected String userRole;
	protected long deposit;

	public CreateUserDTO(String username, String userRole, int deposit) {
		this.username = username;
		this.userRole = userRole;
		this.deposit = deposit;
	}

	public static User from(CreateUserDTO createUserDTO, PasswordEncoder passwordEncoder) {
		return new User(
				UUID.randomUUID().toString(),
				createUserDTO.getUsername(),
				passwordEncoder.encode(createUserDTO.getPassword()),
				UserRole.getByUserRoleTitle(createUserDTO.getUserRole()),
				createUserDTO.getDeposit()
		);
	}
}
