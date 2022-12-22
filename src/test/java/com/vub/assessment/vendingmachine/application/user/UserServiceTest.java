package com.vub.assessment.vendingmachine.application.user;

import com.vub.assessment.vendingmachine.application.user.dto.DepositDTO;
import com.vub.assessment.vendingmachine.domain.user.UserJpaRepository;
import com.vub.assessment.vendingmachine.infrastructure.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

	private static final String SELLER_ID = "seller_id";

	private UserJpaRepository userJpaRepository;

	private PasswordEncoder passwordEncoder;

	private JwtUtil jwtUtil;

	private AuthenticationManager authenticationManager;

	private UserService userService;

	@BeforeEach
	void setUp() {
		userJpaRepository = Mockito.mock(UserJpaRepository.class);
		passwordEncoder = Mockito.mock(PasswordEncoder.class);
		jwtUtil = Mockito.mock(JwtUtil.class);
		authenticationManager = Mockito.mock(AuthenticationManager.class);

		userService = new UserDefaultService(userJpaRepository, passwordEncoder, jwtUtil, authenticationManager);
	}

	@Test
	public void deposit_receivedValueRatherThanExpectedValue_throwIllegalStateException() {
		Assertions.assertThrows(
				IllegalStateException.class, () ->userService.deposit(giveMeDepositDTO(8L))
		);
	}


	@Test
	public void deposit_receivedProperValue_updateDepositAndReturnUserId() {
		Mockito.doNothing().when(userJpaRepository).updateDeposit(5L,SELLER_ID);

		String actual = userService.deposit(giveMeDepositDTO(5L));

		Assertions.assertEquals(actual,SELLER_ID);

	}

	private DepositDTO giveMeDepositDTO(long amount) {
		return new DepositDTO(SELLER_ID, amount);
	}
}
