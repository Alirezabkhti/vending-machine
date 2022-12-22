package com.vub.assessment.vendingmachine.application.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseDTO {
	private String productId;
	private int amount;
}
