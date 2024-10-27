package com.yourcompanyname.persistance.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yourcompanyname.persistance.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public List<Product> findByNameLike(String name);

	public List<Product> findByNameOrDescription(String name, String description);

	public List<Product> findDistinctByName(String name);

	public List<Product> findByPriceGreaterThan(BigDecimal price);

	public List<Product> findByDescriptionContaining(String description);

	public List<Product> findByPriceBetween(BigDecimal low, BigDecimal up);

	public List<Product> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate);

	public List<Product> findByNameIn(List<String> productNames);

	public List<Product> findTop3ByOrderByPriceDesc();

	// JPQL
	@Query("select p from Product p where p.sku = ?1")
	public Product findProductBySku(String sku);

	@Query("select p.name , p.price from Product p order by p.price desc")
	public List<Object[]> findProductNameAndPrice();

	@Query("select p from Product p where p.price>(select AVG(pt.price) from Product pt)")
	public List<Product> findProductsGreaterPriceThanAvergaePrice();

	// @Query("select count(*) from Product p where p.name like %:category")
	@Query("select count(*) from Product p where lower(p.name) like lower( concat( '%',:category))")
	public int findNumberOfProductsByCategory(@Param("category") String category);

	@Query(value = "select category,x.min_price,x.max_price from (\r\n"
			+ "select min(temp.price) min_price,max(temp.price) max_price,temp.category category from\r\n"
			+ "(select p.price price, substr(p.name,INSTR(p.name,' ',-1)+1) category from product p) temp group by temp.category\r\n"
			+ ") x where lower(x.category) =lower(?1)", nativeQuery = true)
	public List<Object[]> findMinAndMaxPriceByCatgeory(String category);

	// defined named query
	public List<Product> findByProductNames(List<String> productNames);

	public List<Product> findProductsByCategory(String category);

	public List<Product> findProductsInDescOrderOfCreation();
}
