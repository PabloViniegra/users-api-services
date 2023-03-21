package com.users.usersapiservices.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@Document("users")
public class Users {
    @Id
    private String id;
    @TextIndexed(weight = 2)
    private String name;
    @TextIndexed(weight = 1)
    private String lastname;
    @TextIndexed(weight = 5)
    private String username;
    private String password;
    @TextIndexed(weight =3)
    private Date created;
}
