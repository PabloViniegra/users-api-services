package com.users.usersapiservices.repositories;

import com.users.usersapiservices.entities.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends MongoRepository<Users, String> {

    Optional<Users> findUsersByUsername(String username);



}
