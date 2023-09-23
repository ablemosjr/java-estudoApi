package estudo.apirest.spring_railway.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import estudo.apirest.spring_railway.controller.dto.UserDTO;
import estudo.apirest.spring_railway.domain.model.User;
import estudo.apirest.spring_railway.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Tag(name = "Users Controller", description = "RESTFul API for managing users.")
public record UserController(UserService userService) {

  @GetMapping
  @Operation(summary = "Get all users", description = "Retrieve a list od all registred users.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operation successful")})
  public ResponseEntity<List<UserDTO>> findAll() {
    var users = userService.findAll();
    var usersDTO = users.stream().map(UserDTO::new).toList();
    return ResponseEntity.ok(usersDTO);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a user by ID", description = "Retrieve a specific user based on its ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Operation successful"),
    @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
    var user = userService.findById(id);
    return ResponseEntity.ok(new UserDTO(user));
  }

  @PostMapping
  @Operation(summary = "Create a new user", description = "Create a new usder and return the created user's data")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "User created successfully"),
    @ApiResponse(responseCode = "422", description = "Invalid user data provided")
  })
  public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
    var user = userService.create(userDTO.toModel());
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(user.getId())
      .toUri();
    return ResponseEntity.created(location).body(new UserDTO(user));
  }

  @PutMapping
  @Operation(summary = "Update a user", description = "Update the data of an existing user based on its ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User update successfully"),
    @ApiResponse(responseCode = "404", description = "User not found"),
    @ApiResponse(responseCode = "422", description = "Invalid user data provided")
  })
  public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
    var user = userService.update(id, userDTO.toModel());
    return ResponseEntity.ok(new UserDTO(user));
  }

  @Operation(summary = "Delete a user", description = "Delete an existing user based on its ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
    @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
