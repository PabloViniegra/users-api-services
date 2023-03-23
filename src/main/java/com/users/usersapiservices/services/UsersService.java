package com.users.usersapiservices.services;

import com.users.usersapiservices.entities.Users;
import com.users.usersapiservices.repositories.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {


    private UsersRepository usersRepository;

    private MongoTemplate template;
    @Autowired
    public UsersService(UsersRepository usersRepository, MongoTemplate template) {
        this.usersRepository = usersRepository;
        this.template = template;
    }
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUser(String id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> getUserByUsername(String username) {
        return usersRepository.findUsersByUsername(username);
    }

    public void createUsers(Users user) {
        user.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
        usersRepository.save(user);
    }

    public void updateUser(Users user) {
        usersRepository.save(user);
    }

    public List<Users> getUsersSearched(String value) {

        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("name", 2F)
                .onField("lastname", 1F)
                .onField("username", 5F)
                .onField("created", 3F)
                .build();
        TextQuery textQuery = TextQuery.queryText(new TextCriteria().matchingAny(value)).sortByScore();
        return template.find(textQuery, Users.class, "users");
    }

    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }
}
