package com.vub.assessment.vendingmachine.application.product.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.vub.assessment.vendingmachine.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDTO extends CreateProductDTO{
	private String id;

	public ProductDTO(String id,String name, long price, int availableAmount, String sellerId) {
		super(name, price, availableAmount, sellerId);
		this.id = id;
	}

	public ProductDTO(String name, long price, String id) {
		super(name, price);
		this.id = id;
	}

	public static ProductDTO from(Product product) {
		return new ProductDTO(
				product.getId(),
				product.getName(),
				product.getPrice(),
				product.getAvailableAmount(),
				product.getSellerId()
		);
	}

	public static List<ProductDTO> from(List<Product> products) {
		return products.stream().map(ProductDTO::from).collect(Collectors.toList());
	}
}
