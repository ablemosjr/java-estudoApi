package estudo.apirest.spring_railway.controller.dto;

import java.util.List;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import estudo.apirest.spring_railway.domain.model.User;

public record UserDTO(Long id, String name, AccountDTO account, CardDTO card, List<FeatureDTO> features, List<NewsDTO> news) {

  public UserDTO(User model) {
    this(
      model.getId(),
      model.getName(),
      ofNullable(model.getAccount()).map(AccountDTO::new).orElse(null),
      ofNullable(model.getCard()).map(CardDTO::new).orElse(null),
      ofNullable(model.getFeatures()).orElse(emptyList()).stream().map(FeatureDTO::new).toList(),
      ofNullable(model.getNews()).orElse(emptyList()).stream().map(NewsDTO::new).toList()
    );
  }

  public User toModel() {
    User model = new User();

    model.setId(this.id);
    model.setName(this.name);
    model.setAccount(ofNullable(this.account()).map(AccountDTO::toModel).orElse(null));
    model.setCard(ofNullable(this.card()).map(CardDTO::toModel).orElse(null));
    model.setFeatures(ofNullable(this.features()).orElse(emptyList()).stream().map(FeatureDTO::toModel).toList());
    model.setNews(ofNullable(this.news()).orElse(emptyList()).stream().map(NewsDTO::toModel).toList());

    return model;
  }
  
}
