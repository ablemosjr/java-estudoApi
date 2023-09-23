package estudo.apirest.spring_railway.service.impl;

import java.util.List;
import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estudo.apirest.spring_railway.domain.model.User;
import estudo.apirest.spring_railway.domain.repository.UserRepository;
import estudo.apirest.spring_railway.service.UserService;
import estudo.apirest.spring_railway.service.exception.BusinessException;
import estudo.apirest.spring_railway.service.exception.NotFoundException;


@Service
public class UserServiceImpl implements UserService {

  private static final Long UNCHANGEABLE_USER_ID = 1L;

  private final UserRepository userRepository;
  
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  @Transactional(readOnly = true)
  public User findById(Long id) {
    return userRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  @Transactional
  public User create(User userToCreate) {
    ofNullable(userToCreate).orElseThrow(() -> new BusinessException("User to create must not be null."));
    ofNullable(userToCreate.getAccount()).orElseThrow(() -> new BusinessException("User account must not be null."));
    ofNullable(userToCreate.getCard()).orElseThrow(() -> new BusinessException("User card must not be null."));

    this.validateChangeableId(userToCreate.getId(), "created");

    if(userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
      throw new BusinessException("This Account Number already exists.");
    }
    if(userRepository.existsByCardNumber(userToCreate.getCard().getNumber())) {
      throw new BusinessException("This Card Number already exists.");
    }

    return this.userRepository.save(userToCreate);
  }
  
  @Transactional
  public User update(Long id, User userToUpdate) {
    this.validateChangeableId(id, "update");
    User dbUser = this.findById(id);

    if(!dbUser.getId().equals(userToUpdate.getId())) {
      throw new BusinessException("Update IDs must be the same.");
    }

    dbUser.setName(userToUpdate.getName());  
    dbUser.setAccount(userToUpdate.getAccount());
    dbUser.setCard(userToUpdate.getCard());
    dbUser.setFeatures(userToUpdate.getFeatures());
    dbUser.setNews(userToUpdate.getNews());
    
    return this.userRepository.save(dbUser);
  }

  @Transactional
  public void delete(Long id) {
    this.validateChangeableId(id, "deleted");
    User dbUser = this.findById(id);
    this.userRepository.delete(dbUser);
  }

  private void validateChangeableId(Long id, String operation) {
    if(UNCHANGEABLE_USER_ID.equals(id)) {
      throw new BusinessException("User with ID %d can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
    }
  }
  
}
