package com.vub.assessment.vendingmachine.presentation;

import java.util.List;

import com.vub.assessment.vendingmachine.application.user.UserService;
import com.vub.assessment.vendingmachine.application.user.dto.CreateUserDTO;
import com.vub.assessment.vendingmachine.application.user.dto.DepositDTO;
import com.vub.assessment.vendingmachine.application.user.dto.UpdateUserDTO;
import com.vub.assessment.vendingmachine.application.user.dto.UserDTO;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
@AllArgsConstructor
public class UserResource {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody CreateUserDTO createUserDTO){
		return ResponseEntity.ok(userService.create(createUserDTO));
	}

	@PutMapping
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_ADMIN + "')")
	public ResponseEntity<UserDTO> update(@RequestBody UpdateUserDTO userDTO){
		return ResponseEntity.ok(userService.update(userDTO));
	}

	@GetMapping(path = "{id}")
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_ADMIN + "')")
	public ResponseEntity<UserDTO> findById(@PathVariable String id){
		return ResponseEntity.ok(userService.findById(id));
	}

	@GetMapping
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_ADMIN + "')")
	public ResponseEntity<List<UserDTO>> findAll(){
		return ResponseEntity.ok(userService.findAll());
	}

	@DeleteMapping(path = "{id}")
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_ADMIN + "')")
	public ResponseEntity<String> delete(@PathVariable String id){
		return ResponseEntity.ok(userService.delete(id));
	}

	@PatchMapping(path = "{id}/resetDeposit")
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_BUYER + "')")
	public ResponseEntity<String> resetDeposit(@PathVariable String id){
		return ResponseEntity.ok(userService.resetDeposit(id));
	}

	@PatchMapping(path = "deposit")
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_BUYER + "')")
	public ResponseEntity<String> deposit(@RequestBody DepositDTO depositDTO){
		return ResponseEntity.ok(userService.deposit(depositDTO));
	}
}
