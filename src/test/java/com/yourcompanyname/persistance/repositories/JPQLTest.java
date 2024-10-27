package com.yourcompanyname.persistance.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yourcompanyname.persistance.entities.Product;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class JPQLTest {

	@Autowired
	ProductRepository productRepository;

	@Test
	void findBySku() {
		Product product = productRepository.findProductBySku("56811825");
		log.info(product.toString());
	}

	@Test
	void getExtractProductNameAndPriceProjection() {
		List<Object[]> productNameAndPrice = productRepository.findProductNameAndPrice();
		for (Object[] x : productNameAndPrice) {
			log.info("Name: " + (String) x[0] + "\tPrice: " + String.valueOf((BigDecimal) x[1]));
		}
	}

	@Test
	void getProductsPriceHigherThanAverge() {
		List<Product> productsGreaterPriceThanAvergaePrice = productRepository
				.findProductsGreaterPriceThanAvergaePrice();
		for (Product p : productsGreaterPriceThanAvergaePrice)
			log.info("Product Name: " + p.getName() + "Product Price: " + p.getPrice());

	}

	@Test
	void getNumberOfProductByCategory() {
		String category = "Clock";
		int count = productRepository.findNumberOfProductsByCategory(category);
		log.info("Category: " + category + "\tProduct Count :" + String.valueOf(count));
	}

	@Test
	void MaxAndMinPriceByCategory() {
		List<Object[]> output = productRepository.findMinAndMaxPriceByCatgeory("SHoes");
		Object[] objects = output.get(0);

		log.info("\nCategory\t" + objects[0].toString() + "\n" + "Min_Price\t" + objects[1].toString() + "\n"
				+ "Max_Price\t" + objects[2].toString() + "\n");
	}
	
	@Test
	void findProductByProductNames()
	{
		List<String> productNames= List.of("Fantastic Copper Clock","Heavy Duty Wool Clock");
		List<Product> productOutputs= productRepository.findByProductNames(productNames);
		for(Product product: productOutputs)
			log.info(product.toString());
	}
	
	@Test
	void findProductsByCategory()
	{
		List<Product> productsByCategory = productRepository.findProductsByCategory("Hat");
		for(Product p : productsByCategory)
			log.info(p.toString());
	}
	
	@Test
	void findProductsInDescOrderOfCreation()
	{
		List<Product> productOutput = productRepository.findProductsInDescOrderOfCreation();
		for(Product p : productOutput)
			log.info(p.toString());
	}

}
