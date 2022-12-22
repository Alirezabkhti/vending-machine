package com.vub.assessment.vendingmachine.presentation;

import com.vub.assessment.vendingmachine.application.user.UserService;
import com.vub.assessment.vendingmachine.application.user.dto.SignInDTO;
import com.vub.assessment.vendingmachine.application.user.dto.SignInSuccessDTO;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "authentication")
@AllArgsConstructor
public class AuthenticationResource {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<SignInSuccessDTO> signIn(@RequestBody SignInDTO signInDTO) {
		return ResponseEntity.ok(userService.signIn(signInDTO));
	}
}
