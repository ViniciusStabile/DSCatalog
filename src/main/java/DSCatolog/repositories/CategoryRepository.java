package DSCatolog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import DSCatolog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
