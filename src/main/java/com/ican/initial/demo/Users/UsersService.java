package com.ican.initial.demo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Iterable<Users> getAllUsers() {
        return usersRepository.findAll();

    }

    public Users Save(Users users) {
        return usersRepository.save(users);
    }

}
