package com.yourcompanyname.persistance.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.yourcompanyname.persistance.entities.Product;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PaginationAndSortingTest {

	@Autowired
	ProductRepository productRepository;

	@Test
	void pagingTest() {

		Pageable pageable = PageRequest.of(0, 10);

		Page<Product> zeroproductPage = productRepository.findAll(pageable);

		int totalPages = zeroproductPage.getTotalPages();
		int pageSize = zeroproductPage.getSize();

		for (int pageNo = 0; pageNo <= totalPages; pageNo++) {
			Pageable newPageable = PageRequest.of(pageNo, pageSize);
			Page<Product> productPage = productRepository.findAll(newPageable);
			List<Product> pageContents = productPage.getContent();
			if (productPage.isFirst())
				System.out.println("PRODUCT LIST IN PAGES\n");
			log.info("Page no: " + pageNo + "No of Products in Page : " + productPage.getNumberOfElements());
			for (Product p : pageContents) {
				log.info(p.toString() + "\n");
			}
			if (productPage.isLast()) {
				System.out.println("End of Products\n");
				break;
			}

		}

	}

	@Test
	void SortingTest() {

		String sortBy1 = "name";
		String sortOrder1 = "asc";
		String sortyBy2 = "price";
		String sortOrder2 = "desc";

		Sort sort1 = sortOrder1.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy1).descending()
				: Sort.by(sortBy1).ascending();

		Sort sort2 = sortOrder2.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortyBy2).descending()
				: Sort.by(sortyBy2).ascending();

		List<Product> sortedByName = productRepository.findAll(sort1);
		System.out.println("\tSORTED BY NAME");
		for (Product p : sortedByName)
			System.out.println("Name : " + p.getName() + "\t" + "Price : " + p.getPrice());

		System.out.println("\tSORTED BY Price");
		List<Product> sortedByPrice = productRepository.findAll(sort2);
		for (Product p : sortedByPrice)
			System.out.println("Name : " + p.getName() + "\t" + "Price : " + p.getPrice());

		Sort combinedSort = sort1.and(sort2);

		System.out.println("\tSORTED BY Name and Price");
		List<Product> sortedByNameAndPrice = productRepository.findAll(combinedSort);
		for (Product p : sortedByNameAndPrice)
			System.out.println("Name : " + p.getName() + "\t" + "Price : " + p.getPrice());

	}

	@Test
	void PagingAndSortingTest() {
		String sortBy = "price";
		String sortDirection = "asc";
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		int pageNo=0;
		while (true) {
			Pageable pageable = PageRequest.of(pageNo, 5, sort);
			Page<Product> productsPage = productRepository.findAll(pageable);
			System.out.println("Showing " + productsPage.getNumber() + "of " + productsPage.getTotalPages() + " pages"
					+ "No of Elements in Page" + productsPage.getNumberOfElements());
			for (Product p : productsPage.getContent())
				System.out.println(p.toString());
			if (productsPage.isLast())
				break;
			else
				pageNo++;
		}
	}

}
