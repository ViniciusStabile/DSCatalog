package DSCatolog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import DSCatolog.DTO.ProductDTO;
import DSCatolog.repositories.ProductRepository;
import DSCatolog.services.exception.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductRepository repository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProduct;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1l;
		nonExistingId = 1000l;
		countTotalProduct = 25l;
	}

	@Test
	public void deleteShouldThrowResourceNotFoundWhenIdDoesNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

	}

	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		service.delete(existingId);
		Assertions.assertEquals(countTotalProduct - 1, repository.count());

	}

	@Test
	public void findAllPagedShouldReturnPage() {

		PageRequest page = PageRequest.of(0, 10);

		Page<ProductDTO> result = service.findAll(page);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalProduct, result.getTotalElements());

	}

	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {

		PageRequest page = PageRequest.of(50, 10);

		Page<ProductDTO> result = service.findAll(page);

		Assertions.assertTrue(result.isEmpty());
		Assertions.assertEquals(50, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalProduct, result.getTotalElements());

	}

	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortByName() {

		PageRequest page = PageRequest.of(0, 10, Sort.by("name"));

		Page<ProductDTO> result = service.findAll(page);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalProduct, result.getTotalElements());
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());

	}

}
