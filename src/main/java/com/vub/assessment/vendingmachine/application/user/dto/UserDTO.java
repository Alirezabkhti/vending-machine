package com.vub.assessment.vendingmachine.application.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.vub.assessment.vendingmachine.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDTO{
	private String id;
	protected String username;
	protected String userRole;
	protected long deposit;

	public static UserDTO from(User user) {
		return new UserDTO(
				user.getId(),
				user.getUsername(),
				user.getUserRole().getTitle(),
				user.getDeposit()
		);
	}

	public static List<UserDTO> from(List<User> users) {
		return users.stream().map(UserDTO::from).collect(Collectors.toList());
	}
}
