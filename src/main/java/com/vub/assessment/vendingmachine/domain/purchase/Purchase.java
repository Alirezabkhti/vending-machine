package com.vub.assessment.vendingmachine.domain.purchase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vub.assessment.vendingmachine.domain.product.Product;
import com.vub.assessment.vendingmachine.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "purchases")
@AllArgsConstructor
@Getter
public class Purchase {

	@Id
	private String id;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User buyer;

	@OneToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@Column
	private int amount;

	Purchase() {
	}
}
