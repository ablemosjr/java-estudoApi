package estudo.apirest.spring_railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import estudo.apirest.spring_railway.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
}
