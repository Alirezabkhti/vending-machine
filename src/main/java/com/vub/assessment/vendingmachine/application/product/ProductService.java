package com.vub.assessment.vendingmachine.application.product;

import java.util.List;

import com.vub.assessment.vendingmachine.application.product.dto.CreateProductDTO;
import com.vub.assessment.vendingmachine.application.product.dto.ProductDTO;
import com.vub.assessment.vendingmachine.application.product.dto.UpdateProductDTO;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {
	ProductDTO create(CreateProductDTO createProductDTO);

	ProductDTO update(UpdateProductDTO userDTO, String jwt);

	ProductDTO findById(String id);

	List<ProductDTO> findAll();

	String delete(String id, String jwt);

	void updateProductAmount(String id, int amount);
}
