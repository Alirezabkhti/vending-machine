package com.vub.assessment.vendingmachine.application.user;

import java.util.List;

import javax.transaction.Transactional;

import com.vub.assessment.vendingmachine.application.user.dto.CreateUserDTO;
import com.vub.assessment.vendingmachine.application.user.dto.DepositDTO;
import com.vub.assessment.vendingmachine.application.user.dto.SignInDTO;
import com.vub.assessment.vendingmachine.application.user.dto.SignInSuccessDTO;
import com.vub.assessment.vendingmachine.application.user.dto.UpdateUserDTO;
import com.vub.assessment.vendingmachine.application.user.dto.UserDTO;
import com.vub.assessment.vendingmachine.domain.user.User;
import com.vub.assessment.vendingmachine.domain.user.UserJpaRepository;
import com.vub.assessment.vendingmachine.infrastructure.JwtUtil;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDefaultService implements UserService{

	private final UserJpaRepository userJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final AuthenticationManager authenticationManager;

	@Override
	public UserDTO create(CreateUserDTO createUserDTO) {
		User user = userJpaRepository.save(CreateUserDTO.from(createUserDTO,passwordEncoder));
		return UserDTO.from(user);
	}

	@Override
	public UserDTO update(UpdateUserDTO updateUserDTO) {
		User user = userJpaRepository.save(UpdateUserDTO.from(updateUserDTO,passwordEncoder));
		return UserDTO.from(user);
	}

	@Override
	public UserDTO findById(String id) {
		User user = userJpaRepository.findById(id).orElseThrow(
				() -> new IllegalStateException("Can not find user with demanded Id")
		);
		return UserDTO.from(user);
	}

	@Override
	public List<UserDTO> findAll() {
		return UserDTO.from(userJpaRepository.findAll());
	}

	@Override
	public String delete(String id) {
		userJpaRepository.deleteById(id);
		return id;
	}

	@Override
	public SignInSuccessDTO signIn(SignInDTO signInDTO) {
		User user = userJpaRepository.findByUsername(signInDTO.getUsername()).orElseThrow(
				() -> new IllegalStateException("user could not be found")
		);
		authenticateUserCredential(signInDTO);
		return SignInSuccessDTO.from(user, jwtUtil.generateToken(user));
	}

	@Override
	public UserDTO findByUsername(String username) {
		User user = userJpaRepository.findByUsername(username).orElseThrow(
				() -> new IllegalStateException("Can not find user with demanded username")
		);
		return UserDTO.from(user);
	}

	@Override
	@Transactional
	public String resetDeposit(String id) {
		userJpaRepository.updateDeposit(0,id);
		return id;
	}

	@Override
	@Transactional
	public String deposit(DepositDTO depositDTO) {
		checkDepositAmount(depositDTO.getDepositAmount());
		userJpaRepository.updateDeposit(depositDTO.getDepositAmount(),depositDTO.getUserId());
		return depositDTO.getUserId();
	}

	@Override
	@Transactional
	public void updateDeposit(String id, long amount) {
		userJpaRepository.updateDeposit(amount,id);
	}

	private void checkDepositAmount(long depositAmount) {
		if(!List.of(5L,10L,20L,50L,100L).contains(depositAmount))
			throw new IllegalStateException("you should deposit -> 5,10,20,50,100");
	}

	private void authenticateUserCredential(SignInDTO signInDTO) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							signInDTO.getUsername(),
							signInDTO.getPassword()
					)
			);
		} catch (BadCredentialsException ex) {
			throw ex;
		}
	}
}
