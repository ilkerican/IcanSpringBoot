package com.ican.initial.demo.Users;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
// Consider changing to JpaRepository

public interface UsersRepository extends CrudRepository<Users, Integer> {

}