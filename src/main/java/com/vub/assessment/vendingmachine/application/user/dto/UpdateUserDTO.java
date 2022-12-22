package com.vub.assessment.vendingmachine.application.user.dto;

import com.vub.assessment.vendingmachine.domain.user.User;
import com.vub.assessment.vendingmachine.domain.user.UserRole;
import lombok.Data;

import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class UpdateUserDTO {
	private String id;
	protected String username;
	protected String password;
	protected String userRole;
	protected int deposit;

	public static User from(UpdateUserDTO updateUserDTO, PasswordEncoder passwordEncoder) {
		return new User(
				updateUserDTO.getId(),
				updateUserDTO.getUsername(),
				passwordEncoder.encode(updateUserDTO.getPassword()),
				UserRole.getByUserRoleTitle(updateUserDTO.getUserRole()),
				updateUserDTO.getDeposit()
		);
	}

}
