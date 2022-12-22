package com.vub.assessment.vendingmachine.application.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInDTO {
	private String username;
	private String password;
}
