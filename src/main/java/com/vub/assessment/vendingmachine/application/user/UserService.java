package com.vub.assessment.vendingmachine.application.user;

import java.util.List;

import com.vub.assessment.vendingmachine.application.user.dto.CreateUserDTO;
import com.vub.assessment.vendingmachine.application.user.dto.DepositDTO;
import com.vub.assessment.vendingmachine.application.user.dto.SignInDTO;
import com.vub.assessment.vendingmachine.application.user.dto.SignInSuccessDTO;
import com.vub.assessment.vendingmachine.application.user.dto.UpdateUserDTO;
import com.vub.assessment.vendingmachine.application.user.dto.UserDTO;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
	UserDTO create(CreateUserDTO createUserDTO);

	UserDTO update(UpdateUserDTO userDTO);

	UserDTO findById(String id);

	List<UserDTO> findAll();

	String delete(String id);

	SignInSuccessDTO signIn(SignInDTO signInDTO);

	UserDTO findByUsername(String username);

	String resetDeposit(String id);

	String deposit(DepositDTO depositDTO);

	void updateDeposit(String id, long amount);
}
