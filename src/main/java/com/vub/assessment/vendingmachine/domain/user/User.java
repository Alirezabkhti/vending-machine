package com.vub.assessment.vendingmachine.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Entity
@Table(name = "users")
@AllArgsConstructor
@Getter
public class User {

	@Id
	private String id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(value = EnumType.STRING)
	private UserRole userRole;

	@Column
	private long deposit;
	User() {
	}
}
