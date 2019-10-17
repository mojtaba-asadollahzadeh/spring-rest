package com.app.restApplication.controllers;

import com.app.restApplication.dtos.requests.UserReqDTO;
import com.app.restApplication.mappers.requests.UserRequestMapper;
import com.app.restApplication.mappers.responses.UserResponseMapper;
import com.app.restApplication.models.User;
import com.app.restApplication.services.UserService;
import com.app.restApplication.utils.ApiError;
import com.app.restApplication.utils.ResponseHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.mapstruct.factory.Mappers;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserRequestMapper userRequestMapper = Mappers.getMapper(UserRequestMapper.class);
    private final UserResponseMapper userResponseMapper = Mappers.getMapper(UserResponseMapper.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method to return list of all Users
     * @return ResponseHandler
     */
    @GetMapping
    public ResponseEntity<Object> fetchAll() {

        return ResponseHandler.send(
                HttpStatus.OK,
                true,
                "users fetched suucessfully !",
                userResponseMapper.toUsers( userService.fetchAll() )
        );
    }

    /**
     * Method to create a new User
     * @return ResponseHandler
     */
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UserReqDTO userReqDTO, Errors errors) {
        // check if request have any error
        if (errors.hasErrors()) {
            return ResponseHandler.send(
                    HttpStatus.BAD_REQUEST,
                    false,
                    "you have errors in your request!",
                    new ApiError(errors).getAllErrors()
            );
        }
        // create the user
        User user = userService.save( userRequestMapper.toUserDTO( userReqDTO ) );
        return ResponseHandler.send(
                HttpStatus.CREATED,
                true,
                "user created suucessfully!",
                userResponseMapper.toUserResDTO(user)
        );
    }

    /**
     * Method to update a user by ID field
     * @return ResponseHandler
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody @Valid UserReqDTO userReqDTO,Errors errors,@PathVariable Long id) {
        // check if request have any error
        if (errors.hasErrors()) {
            return ResponseHandler.send(
                    HttpStatus.BAD_REQUEST,
                    false,
                    "you have errors in your request!",
                    new ApiError(errors).getAllErrors()
            );
        }
        // fetch the user
        User user = userService.fetchById(id);

        // update user properties
        user.setName( userReqDTO.getName() );
        user.setEmail( userReqDTO.getEmail() );
        user.setAddress( userReqDTO.getAddress() );
        user.setBirthDate( userReqDTO.getBirthDate() );

        // save the user
        User updated = userService.save( user );
        return ResponseHandler.send(
                HttpStatus.CREATED,
                true,
                "user updated suucessfully!",
                userResponseMapper.toUserResDTO(updated)
        );
    }

    /**
     * Method to return user by ID field
     * @return ResponseHandler
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchOne(@PathVariable Long id) {

        // fetch the user
        User user = userService.fetchById(id);
        // return it by response DTO
        return ResponseHandler.send(
                HttpStatus.OK,
                true,
                "user fetched successfully !",
                userResponseMapper.toUserResDTO(user)
        );
    }

    /**
     * Method to delete a user by ID field
     * @return ResponseHandler
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable Long id) {

        // fetch the user
        userService.delete(id);
        // return it by response DTO
        return ResponseHandler.send(
                HttpStatus.OK,
                true,
                "user deleted successfully !",
                null
        );
    }



    /**
     * Exception Handlers
     * @return ResponseHandler
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        Map<String, String> error = new HashMap<String, String>();
        error.put("birthDate","birthDate should be in yyyy-MM-dd format");
        return ResponseHandler.send(
                HttpStatus.BAD_REQUEST,
                false,
                "you have errors in your request!",
                error
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> error = new HashMap<String, String>();
        error.put("user","user can not be found!");
        return ResponseHandler.send(
                HttpStatus.NOT_FOUND,
                false,
                "you have errors in your request!",
                error
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<String, String>();
        error.put("body","request body is missing!");
        return ResponseHandler.send(
                HttpStatus.BAD_REQUEST,
                false,
                "you have errors in your request!",
                error
        );
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        Map<String, String> error = new HashMap<String, String>();
        error.put("user","user can not be found!");
        return ResponseHandler.send(
                HttpStatus.NOT_FOUND,
                false,
                "you have errors in your request!",
                error
        );
    }
}
