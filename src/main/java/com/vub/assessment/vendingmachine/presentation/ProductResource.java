package com.vub.assessment.vendingmachine.presentation;

import java.util.List;

import com.vub.assessment.vendingmachine.application.product.ProductService;
import com.vub.assessment.vendingmachine.application.product.dto.CreateProductDTO;
import com.vub.assessment.vendingmachine.application.product.dto.ProductDTO;
import com.vub.assessment.vendingmachine.application.product.dto.UpdateProductDTO;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "products")
@AllArgsConstructor
public class ProductResource {
	private final ProductService productService;

	@PostMapping
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_SELLER + "')")
	public ResponseEntity<ProductDTO> create(@RequestBody CreateProductDTO createProductDTO){
		return ResponseEntity.ok(productService.create(createProductDTO));
	}

	@PutMapping
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_SELLER + "')")
	public ResponseEntity<ProductDTO> update(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
			@RequestBody UpdateProductDTO userDTO
	){
		return ResponseEntity.ok(productService.update(userDTO,jwt));
	}

	@GetMapping(path = "{id}")
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_SELLER + "')")
	public ResponseEntity<ProductDTO> findById(@PathVariable String id){
		return ResponseEntity.ok(productService.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll(){
		return ResponseEntity.ok(productService.findAll());
	}

	@DeleteMapping(path = "{id}")
	@PreAuthorize("hasRole('"+ AuthorityConstant.ROLE_SELLER + "')")
	public ResponseEntity<String> delete(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
			@PathVariable String id
	){
		return ResponseEntity.ok(productService.delete(id, jwt));
	}
}
