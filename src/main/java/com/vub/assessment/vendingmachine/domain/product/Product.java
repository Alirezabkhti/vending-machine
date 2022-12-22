package com.vub.assessment.vendingmachine.domain.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "products")
@AllArgsConstructor
@Getter
public class Product {

	@Id
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private long price;

	@Column(name = "availableAmount", nullable = false)
	private int availableAmount;

	@Column(name = "sellerId", nullable = false)
	private String sellerId;

	Product() {
	}

}
