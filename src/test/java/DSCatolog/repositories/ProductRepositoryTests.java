package DSCatolog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import DSCatolog.entities.Product;
import DSCatolog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProduct;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1l;
		nonExistingId = 100l;
		countTotalProduct = 25l;
		
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {		
		repository.deleteById(existingId);
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);		
		product = repository.save(product);		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals( countTotalProduct + 1,product.getId());	
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
		
		Optional<Product> result = repository.findById(nonExistingId);
		Assertions.assertFalse(result.isPresent());
		
	}
	
	
	@Test
	public void findByIdShouldReturnProduct() {
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
		
	}
	
}


