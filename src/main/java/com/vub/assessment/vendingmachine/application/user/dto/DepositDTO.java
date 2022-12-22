package com.vub.assessment.vendingmachine.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DepositDTO {
	private String userId;
	private long depositAmount;
}
