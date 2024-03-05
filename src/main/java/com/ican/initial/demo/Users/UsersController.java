package com.ican.initial.demo.Users;

import com.github.dozermapper.core.Mapper;
import com.ican.initial.demo.AppMain.InitService;
import com.ican.initial.demo.DatabaseLayer.AuxMethods;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;

/*
 * APP URL : http://localhost:8080/users/all
 * Validation : https://www.baeldung.com/spring-boot-bean-validation
 */

@Controller // This means that this class is a Controller
@RequestMapping(path = "/users") // This means URL's start with /demo (after Application path)
@Validated
public class UsersController {

    private final UsersService usersService;
    private final InitService initService;
    private final Mapper mapper;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersService usersService, Mapper mapper, InitService initService) {
        this.usersService = usersService;
        this.mapper = mapper;
        this.initService = initService;
    }

    @Operation(summary = "Adds a new user to the database", description = "All the fields are required.")
    @PostMapping(path = "/add2") // Map ONLY POST Requests
    public ResponseEntity<Users> addNewUserWithRbUserClass(@Valid @RequestBody RBUsers rbUsers) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        // rbUsers.setemail("email");
        Users n = new Users();
        mapper.map(rbUsers, n);

        return ResponseEntity.status(HttpStatus.OK).body(usersService.Save(n));
        // return ;

        // return "Saved";
    }

    @Operation(summary = "Adds a new user to the database", description = "All the fields are required.")
    @PostMapping(path = "/add3") // Map ONLY POST Requests
    public Users addNewUserWithPureUserClass(@Valid @RequestBody Users users) throws Exception {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        // rbUsers.setemail("email");

        // return ResponseEntity.status(HttpStatus.OK).body(usersService.Save(users));

        List<Map<String, Object>> metaData = initService.getMetaData();

        AuxMethods.doValidityCheck(metaData, "Employee_updated", null);

        return usersRepository.save(users);
        // return ;

        // return "Saved";
    }

    @Operation(summary = "Adds a new user to the database", description = "All the fields are required.")
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Users n = new Users();
        n.setname(name);
        n.setemail(email);
        // usersRepository.save(n);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        // This returns a JSON or XML with the users
        return usersService.getAllUsers();
    }
}