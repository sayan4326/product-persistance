package com.yourcompanyname.persistance.repositories;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yourcompanyname.persistance.entities.Product;

import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

@SpringBootTest
@Slf4j
public class ProductRepositoryRepoMethoedTest {

	@Autowired
	ProductRepository productRepository;

	@Test
	void saveProduct() {

		Product product = createDummyProduct();
		productRepository.save(product);
		Optional<Product> productOutput = productRepository.findById(product.getId());
		if (productOutput.isPresent())
			log.info(productOutput.get().toString());

	}

	@Test
	void updateProduct() {
		Faker faker = new Faker();
		Product product = productRepository.findAll().get(0);
		BigDecimal originalPrice = product.getPrice();
		product.setPrice(new BigDecimal(faker.commerce().price().toString()));
		productRepository.save(product);
		log.info("original price " + String.valueOf(originalPrice));
		log.info("updated price " + String.valueOf(product.getPrice()));
	}

	@Test
	void saveAllProduct() {
		List<Product> productsToSave = new ArrayList<>();
		List<Long> idList = new ArrayList<>();
		for(int i=0;i<100;i++)
		{
			productsToSave.add(createDummyProduct());
			
		}
		productRepository.saveAll(productsToSave);

		for (Product product : productsToSave) {
			if (product.getId() != null)
				idList.add(product.getId());
			else
				fail();
		}
		List<Product> productListbyId = productRepository.findAllById(idList);
		for (Product product : productListbyId) {
			System.out.println(product.toString());
		}
	}

	@Test
	void findAllProducts() {
		List<Product> allProducts = productRepository.findAll();
		for (Product product : allProducts) {
			System.out.println(product.toString());
		}
	}

	@Test
	void deleteByProductId() {
		List<Product> allProducts = productRepository.findAll();
		if (allProducts != null && !allProducts.isEmpty()) {
			Product productToDelete = allProducts.get(0);
			productRepository.deleteById(productToDelete.getId());
			boolean existsById = productRepository.existsById(productToDelete.getId());
			if (existsById)
				fail();
		}
	}

	@Test
	void deleteProduct() {
		List<Product> allProducts = productRepository.findAll();
		if (allProducts != null && !allProducts.isEmpty()) {
			Product productToDelete = allProducts.get(0);
			productRepository.delete(productToDelete);
			Optional<Product> productDeleted = productRepository.findById(productToDelete.getId());
			if (productDeleted.isPresent())
				fail();
		}
	}

	@Test
	void deleteAllProduct() {
		List<Product> allProducts = productRepository.findAll();
		List<Product> productToDelete = allProducts.subList(0, 2);
		productRepository.deleteAll(productToDelete);
		List<Long> deletedIds = new ArrayList<>();
		for (Product p : productToDelete) {
			deletedIds.add(p.getId());
		}
		List<Product> deletedProducts = productRepository.findAllById(deletedIds);

		for (Product p : deletedProducts) {
			if (p != null && p.getId() != null)
				fail();
		}
	}

	@Test
	void emptyProductTable() {
		productRepository.deleteAll();
		long count = productRepository.count();
		if (count > 0)
			fail();
	}

	Product createDummyProduct() {
		Product product = new Product();
		Faker faker = new Faker();
		product.setName(faker.commerce().productName());
		String sku = faker.number().digits(8);
		String imageUrl = "https://product-persistance.com/" + sku + "/" + faker.number().numberBetween(300, 800) + "/"
				+ faker.number().numberBetween(300, 800);
		product.setSku(sku);
		product.setDescription(faker.lorem().sentence());
		product.setPrice(new BigDecimal(faker.commerce().price().toString()));
		product.setImageUrl(imageUrl);
		product.setActive(true);
		return product;

	}
}
