package DSCatolog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import DSCatolog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
