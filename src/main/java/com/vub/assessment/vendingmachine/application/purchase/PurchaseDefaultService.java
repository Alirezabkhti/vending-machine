package com.vub.assessment.vendingmachine.application.purchase;

import javax.transaction.Transactional;

import com.vub.assessment.vendingmachine.application.product.ProductService;
import com.vub.assessment.vendingmachine.application.product.dto.ProductDTO;
import com.vub.assessment.vendingmachine.application.purchase.dto.PurchaseDTO;
import com.vub.assessment.vendingmachine.application.purchase.dto.PurchaseInfoDTO;
import com.vub.assessment.vendingmachine.application.user.UserService;
import com.vub.assessment.vendingmachine.application.user.dto.UserDTO;
import com.vub.assessment.vendingmachine.infrastructure.JwtUtil;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PurchaseDefaultService implements PurchaseService{

	private final ProductService productService;
	private final UserService userService;
	private final JwtUtil jwtUtil;

	@Override
	@Transactional
	public PurchaseInfoDTO purchase(PurchaseDTO purchaseDTO, String jwt) {
		ProductDTO product = productService.findById(purchaseDTO.getProductId());
		checkProductAvailableAmount(product.getAvailableAmount(),purchaseDTO.getAmount());
		UserDTO user = userService.findByUsername(jwtUtil.extractUsername(jwt.substring(7)));
		checkUserDeposit(product.getPrice(),purchaseDTO.getAmount(),user.getDeposit());
		productService.updateProductAmount(product.getId(),purchaseDTO.getAmount());
		userService.updateDeposit(user.getId(), calculateRemainingDepositAmount(user.getDeposit(),purchaseDTO.getAmount(),product.getPrice()));
		return PurchaseInfoDTO.from(product,purchaseDTO.getAmount());
	}

	private void checkProductAvailableAmount(int availableAmount, int neededAmount) {
		if(availableAmount < neededAmount)
			throw new IllegalStateException("insufficient amount.");
	}

	private void checkUserDeposit(long price, int amount, long deposit) {
		if(price * amount > deposit)
			throw new IllegalStateException("user does not have enough deposit.");
	}

	private long calculateRemainingDepositAmount(long deposit, int amount, long price) {
		return  deposit - (amount * price);
	}

}
