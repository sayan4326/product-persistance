package com.yourcompanyname.persistance.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yourcompanyname.persistance.entities.Product;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ProductRepositoryDerivedMethoedTest {
	
	@Autowired
	ProductRepository productRepository;

	@Test
	void testFindByProductNameLike()
	{
		List<Product> productOutputList = productRepository.findByNameLike("%Marble%");
		System.out.println(productOutputList.toString());
	}
	
	@Test
	void findByProductDescContaining()
	{
		List<Product> productOutputList = productRepository.findByDescriptionContaining("ti");
		System.out.println(productOutputList.toString());
	}
	
	@Test
	void findByNameOrDesc()
	{
		List<Product> productOutputList = productRepository.findByNameOrDescription("Enormous Marble Coat","Quam doloremque totam ut cumque dicta et occaecati.");
		log.info(productOutputList.toString());

	}
	
	@Test
	void findDistinctByName()
	{
		List<Product> productOutputList = productRepository.findDistinctByName("Enormous Marble Coat");
		log.info(productOutputList.toString());

	}
	
	@Test
	void findByPriceGreaterThan()
	{
		List<Product> productOutputList = productRepository.findByPriceGreaterThan(BigDecimal.valueOf(10.00) );
		log.info(String.valueOf(productOutputList.size()));

	}
	@Test
	void findBetweenPrice()
	{
		List<Product> productOutputList = productRepository.findByPriceBetween(BigDecimal.valueOf(0.00),BigDecimal.valueOf(10000.00)  );
		log.info(String.valueOf(productOutputList.size()));

	}
	
	@Test
	void findBetweenDate()
	{
		List<Product> productOutputList = productRepository.findByCreatedOnBetween(LocalDateTime.of(2024, 1, 1, 1, 1),LocalDateTime.now() );
		log.info(String.valueOf(productOutputList.size()));

	}
	
	@Test
	void findByNameIn()
	{	
		List<String> productNames= new ArrayList<>();
		productNames.add("Lightweight Marble Hat");
		productNames.add("Enormous Marble Coat");
		List<Product> productOutputList = productRepository.findByNameIn(productNames);
		log.info(String.valueOf(productOutputList.size()));
	}
	
	@Test
	void findTop3OrderByPricDesc()
	{	
		List<Product> productOutputList = productRepository.findTop3ByOrderByPriceDesc();
		log.info(productOutputList.toString());
	}
	
}
