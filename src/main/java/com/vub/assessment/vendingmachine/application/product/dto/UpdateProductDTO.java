package com.vub.assessment.vendingmachine.application.product.dto;

import com.vub.assessment.vendingmachine.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateProductDTO {

	private String id;

	private String name;

	private long price;

	private int availableAmount;

	private String sellerId;

	public static Product from(UpdateProductDTO updateProductDTO) {
		return new Product(
				updateProductDTO.getId(),
				updateProductDTO.getName(),
				updateProductDTO.getPrice(),
				updateProductDTO.getAvailableAmount(),
				updateProductDTO.sellerId
		);
	}
}
