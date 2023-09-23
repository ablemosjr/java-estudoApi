package estudo.apirest.spring_railway.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import estudo.apirest.spring_railway.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  boolean existsByAccountNumber(String accountNumber);

  boolean existsByCardNumber(String number);
}
