package com.vub.assessment.vendingmachine.application.purchase.dto;

import com.vub.assessment.vendingmachine.application.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseInfoDTO {
	ProductDTO product;

	long totalAmount;

	public static PurchaseInfoDTO from(ProductDTO product, int amount) {
		return new PurchaseInfoDTO(
				new ProductDTO(
						product.getName(),
						product.getPrice(),
						product.getId()
				),
				product.getPrice() * amount
		);
	}
}
