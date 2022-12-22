package com.vub.assessment.vendingmachine.application.purchase;


import com.vub.assessment.vendingmachine.application.product.ProductService;
import com.vub.assessment.vendingmachine.application.product.dto.ProductDTO;
import com.vub.assessment.vendingmachine.application.purchase.dto.PurchaseDTO;
import com.vub.assessment.vendingmachine.application.purchase.dto.PurchaseInfoDTO;
import com.vub.assessment.vendingmachine.application.user.UserService;
import com.vub.assessment.vendingmachine.application.user.dto.UserDTO;
import com.vub.assessment.vendingmachine.domain.user.UserRole;
import com.vub.assessment.vendingmachine.infrastructure.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class PurchaseServiceTest {
	private static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJidXllciIsImlhdCI6MTY3MTIyNDcyMywiZXhwIjoxNjcxNjU2NzIzfQ.3FrdQq5sIUIrWbYdNzLidZtXR8sAr6YIkTfuqO6Kxq8";

	private static final String PRODUCT_ID = "product_id";

	private static final String SELLER_ID = "seller_id";

	private static final int AMOUNT = 4;

	private static final long PRICE = 15;

	private PurchaseDTO purchaseDTO = new PurchaseDTO(PRODUCT_ID, AMOUNT);

	private PurchaseService purchaseService;

	private ProductService productService;

	private UserService userService;

	private JwtUtil jwtUtil;


	@BeforeEach
	void setUp() {
		productService = Mockito.mock(ProductService.class);
		userService = Mockito.mock(UserService.class);
		jwtUtil = Mockito.mock(JwtUtil.class);

		purchaseService = new PurchaseDefaultService(productService, userService, jwtUtil);
	}

	@Test
	public void purchase_failedToFindProductWithGivenProductId_throwIllegalStateException() {
		Mockito.when(productService.findById(PRODUCT_ID)).thenThrow(IllegalStateException.class);

		Assertions.assertThrows(
				IllegalStateException.class, () -> purchaseService.purchase(purchaseDTO, JWT)
		);
	}

	@Test
	public void purchase_productAvailabilityAmountIsLessThanNeededAmount_throwIllegalStateException() {
		Mockito.when(productService.findById(PRODUCT_ID)).thenReturn(giveMeProductDTO(AMOUNT - 1));

		Assertions.assertThrows(
				IllegalStateException.class, () -> purchaseService.purchase(purchaseDTO, JWT)
		);
	}

	@Test
	public void purchase_failedToFindUser_throwIllegalStateException() {
		Mockito.when(productService.findById(PRODUCT_ID)).thenReturn(giveMeProductDTO(AMOUNT));
		Mockito.when(userService.findByUsername(any())).thenThrow(IllegalStateException.class);

		Assertions.assertThrows(
				IllegalStateException.class, () -> purchaseService.purchase(purchaseDTO, JWT)
		);
	}

	@Test
	public void purchase_userDepositIsLessThanBuyerTotalPurchasesCost_throwIllegalStateException() {
		Mockito.when(productService.findById(PRODUCT_ID)).thenReturn(giveMeProductDTO(AMOUNT));
		Mockito.when(userService.findByUsername(any())).thenReturn(giveMeUserDTO(10));

		Assertions.assertThrows(
				IllegalStateException.class, () -> purchaseService.purchase(purchaseDTO, JWT)
		);
	}

	@Test
	public void purchase_userHaveAcceptableRequest_beDoneAndReturnPurchaseInfoDTO() {
		Mockito.when(productService.findById(PRODUCT_ID)).thenReturn(giveMeProductDTO(AMOUNT));
		Mockito.when(userService.findByUsername(any())).thenReturn(giveMeUserDTO(200));
		Mockito.doNothing().when(productService).updateProductAmount(PRODUCT_ID,AMOUNT);
		Mockito.doNothing().when(userService).updateDeposit(SELLER_ID,140L);

		PurchaseInfoDTO actual = purchaseService.purchase(purchaseDTO,JWT);

		Assertions.assertEquals(actual.getProduct().getId(),PRODUCT_ID);
		Assertions.assertEquals(actual.getTotalAmount(),60L);
	}

	private ProductDTO giveMeProductDTO(int amount){
		return new ProductDTO(PRODUCT_ID, "P1",PRICE,amount,SELLER_ID);
	}

	private UserDTO giveMeUserDTO(long deposit){
		return new UserDTO(
				SELLER_ID,
				"username",
				UserRole.ROLE_SELLER.getTitle(),
				deposit
		);
	}
}
