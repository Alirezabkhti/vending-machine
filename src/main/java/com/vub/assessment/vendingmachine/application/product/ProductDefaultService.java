package com.vub.assessment.vendingmachine.application.product;

import java.util.List;

import javax.transaction.Transactional;

import com.vub.assessment.vendingmachine.application.product.dto.CreateProductDTO;
import com.vub.assessment.vendingmachine.application.product.dto.ProductDTO;
import com.vub.assessment.vendingmachine.application.product.dto.UpdateProductDTO;
import com.vub.assessment.vendingmachine.application.user.UserService;
import com.vub.assessment.vendingmachine.application.user.dto.UserDTO;
import com.vub.assessment.vendingmachine.domain.product.Product;
import com.vub.assessment.vendingmachine.domain.product.ProductJpaRepository;
import com.vub.assessment.vendingmachine.infrastructure.JwtUtil;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductDefaultService implements ProductService {

	private final ProductJpaRepository productJpaRepository;

	private final UserService userService;

	private final JwtUtil jwtUtil;

	@Override
	public ProductDTO create(CreateProductDTO createProductDTO) {
		Product product = productJpaRepository.save(CreateProductDTO.from(createProductDTO));
		return ProductDTO.from(product);
	}

	@Override
	public ProductDTO update(UpdateProductDTO updateProductDTO, String jwt) {
		UserDTO user = userService.findByUsername(jwtUtil.extractUsername(jwt.substring(7)));
		Product product = productJpaRepository.findById(updateProductDTO.getId()).orElseThrow(
				() -> new IllegalStateException("Can not find product with demanded Id")
		);
		checkSeller(product.getSellerId(), user.getId());
		productJpaRepository.save(UpdateProductDTO.from(updateProductDTO));
		return ProductDTO.from(product);
	}

	@Override
	public ProductDTO findById(String id) {
		Product product = productJpaRepository.findById(id).orElseThrow(
				() -> new IllegalStateException("Can not find product with demanded Id")
		);
		return ProductDTO.from(product);
	}

	@Override
	public List<ProductDTO> findAll() {
		return ProductDTO.from(productJpaRepository.findAll());
	}

	@Override
	public String delete(String id, String jwt) {
		UserDTO user = userService.findByUsername(jwtUtil.extractUsername(jwt.substring(7)));
		Product product = productJpaRepository.findById(id).orElseThrow(
				() -> new IllegalStateException("Can not find product with demanded Id")
		);
		checkSeller(product.getSellerId(), user.getId());
		productJpaRepository.deleteById(id);
		return id;
	}

	@Override
	@Transactional
	public void updateProductAmount(String id, int amount) {
		productJpaRepository.updateAmount(id, amount);
	}

	private void checkSeller(String sellerId, String id) {
		if (!sellerId.equals(id)) {
			throw new IllegalStateException("your are not the seller");
		}
	}
}
