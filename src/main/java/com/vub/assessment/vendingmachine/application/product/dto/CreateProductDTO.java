package com.vub.assessment.vendingmachine.application.product.dto;


import java.util.UUID;

import com.vub.assessment.vendingmachine.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateProductDTO {
	private String name;
	private long price;
	private int availableAmount;
	private String sellerId;

	public CreateProductDTO(String name, long price) {
		this.name = name;
		this.price = price;
	}

	public static Product from(CreateProductDTO createProductDTO) {
		return new Product(
				UUID.randomUUID().toString(),
				createProductDTO.getName(),
				createProductDTO.getPrice(),
				createProductDTO.getAvailableAmount(),
				createProductDTO.sellerId
		);
	}
}
