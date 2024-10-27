package com.yourcompanyname.persistance.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@NamedQueries({ @NamedQuery(name = "Product.findByProductNames", query = "select p from Product p where p.name in ?1"),
		@NamedQuery(name = "Product.findProductsByCategory", query = "select p from Product p where p.name like  concat( '%',:category)") })
@NamedNativeQueries(
{ @NamedNativeQuery(name = "Product.findProductsInDescOrderOfCreation", query = "select p.* from Product p order by p.created_on desc", resultClass = Product.class) })

@Table(name = "product", uniqueConstraints = { @UniqueConstraint(name = "sku_unique", columnNames = "sku") })
public class Product {

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productIdSequence")
	@SequenceGenerator(name = "productIdSequence", initialValue = 1000, allocationSize = 1)
	private Long id;
	@Column(nullable = false)
	private String sku;
	@Column(nullable = false)
	private String name;
	private String description;
	private String imageUrl;
	private BigDecimal price;
	private boolean active;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updatedOn;

}
