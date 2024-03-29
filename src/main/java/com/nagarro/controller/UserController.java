package com.nagarro.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nagarro.entity.User;
import com.nagarro.exception.CustomException;
import com.nagarro.service.UserService;
import com.nagarro.model.UsersResponse;
import com.nagarro.validator.CustomValidator;
import com.nagarro.validator.FactoryValidator;

@RestController
public class UserController {
	
	private UserService service;
	private final FactoryValidator factoryValidator;
	List<Integer> news= Arrays.asList(5, -10, 7, -18, 23); 

    @Autowired
    public UserController(FactoryValidator factoryValidator, UserService service) {
        this.factoryValidator = factoryValidator;
        this.service = service;
    }


	
	@PostMapping("users")
	public List<User> addUser(@RequestParam(name="size",defaultValue="1")Integer size) throws CustomException{
		List<User> users=service.getUsersFromApi(size);
		news.stream().sorted(Comparator.naturalOrder()).forEach(System.out::println);
		return service.addUsers(users);
	}
	
	@GetMapping("users")
	public List<User> getAllUsers() {
//		throw new Exception("not authorize");
		return service.getUsersFromDatabase();
	}

	@GetMapping("listUser")
    public UsersResponse getUsers(

    		
    		
    		 @RequestParam String sortType,
             @RequestParam String sortOrder,
             @RequestParam String limit,
             @RequestParam String offset) {

        validateInput("Numeric", limit);
        validateInput("Numeric", offset);
        validateInput("Alphabets", sortType);
        validateInput("Alphabets", sortOrder);
        
      
        return service.getUsers(sortType, sortOrder, Integer.parseInt(limit), Integer.parseInt(offset));
    }

    public void validateInput(String parameterType, String value) {
        CustomValidator validator = factoryValidator.getValidator(parameterType);
        if (!validator.validate(value)) {
            throw new IllegalArgumentException("Invalid input for " + parameterType + ": " + value);
        }
    }
}
