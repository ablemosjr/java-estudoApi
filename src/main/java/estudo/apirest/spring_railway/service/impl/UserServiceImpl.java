package estudo.apirest.spring_railway.service.impl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import estudo.apirest.spring_railway.domain.model.User;
import estudo.apirest.spring_railway.domain.repository.UserRepository;
import estudo.apirest.spring_railway.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findById(Long id) {
    return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  @Override
  public User create(User userToCreate) {
    if(userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
      throw new IllegalArgumentException("This Account Number already exists.");
    }

    return userRepository.save(userToCreate);
  }
  
}
