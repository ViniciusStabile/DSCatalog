package DSCatolog.tests;

import java.time.Instant;

import DSCatolog.DTO.ProductDTO;
import DSCatolog.entities.Category;
import DSCatolog.entities.Product;

public class Factory {

	public static Product createProduct() {
		Product product = new Product(1l,"phone","Good phone",800.0,"https",Instant.parse("2020-12-12T03:00:00Z"));
		product.getCategories().add(new Category(2l,"Eletronics"));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product,product.getCategories());
		
	}
}
