package com.vub.assessment.vendingmachine.application.user.dto;

import com.vub.assessment.vendingmachine.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SignInSuccessDTO {

	private String id;

	public String jwt;

	protected String username;

	protected String userRole;

	public static SignInSuccessDTO from(User user, String jwt) {
		return new SignInSuccessDTO(
				user.getId(),
				jwt,
				user.getUsername(),
				user.getUserRole().getTitle()
		);
	}
}
