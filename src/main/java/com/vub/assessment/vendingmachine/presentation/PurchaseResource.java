package com.vub.assessment.vendingmachine.presentation;

import com.vub.assessment.vendingmachine.application.purchase.PurchaseService;
import com.vub.assessment.vendingmachine.application.purchase.dto.PurchaseDTO;
import com.vub.assessment.vendingmachine.application.purchase.dto.PurchaseInfoDTO;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "purchases")
@AllArgsConstructor
public class PurchaseResource {
	private final PurchaseService purchaseService;

	@PostMapping
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_BUYER + "')")
	public ResponseEntity<PurchaseInfoDTO> purchase(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
			@RequestBody PurchaseDTO purchaseDTO
	){
		return ResponseEntity.ok(purchaseService.purchase(purchaseDTO,jwt));
	}
}
