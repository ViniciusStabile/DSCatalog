package DSCatolog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import DSCatolog.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
