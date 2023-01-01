package com.ican.initial.demo.Users;

import com.github.dozermapper.core.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/users") // This means URL's start with /demo (after Application path)
public class UsersController {

    private final UsersService usersService;
    private final Mapper mapper;

    @Autowired
    public UsersController(UsersService usersService, Mapper mapper) {
        this.usersService = usersService;
        this.mapper = mapper;
    }

    @Operation(summary = "Adds a new user to the database", description = "All the fields are required.")
    @PostMapping(path = "/add2") // Map ONLY POST Requests
    public ResponseEntity<Users> addNewUserWithRbUserClass(@RequestBody RBUsers rbUsers) {
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