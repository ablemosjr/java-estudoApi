package estudo.apirest.spring_railway.service;

import estudo.apirest.spring_railway.domain.model.User;

public interface UserService {
  
  User findById(Long id);

  User create(User userToCreate);


}
