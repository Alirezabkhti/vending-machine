package com.vub.assessment.vendingmachine.application.purchase;


import com.vub.assessment.vendingmachine.application.purchase.dto.PurchaseDTO;
import com.vub.assessment.vendingmachine.application.purchase.dto.PurchaseInfoDTO;

import org.springframework.stereotype.Service;

@Service
public interface PurchaseService {
	PurchaseInfoDTO purchase(PurchaseDTO purchaseDTO, String jwt);
}
