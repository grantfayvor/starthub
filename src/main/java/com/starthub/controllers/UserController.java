package com.starthub.controllers;

import com.starthub.models.User;
import com.starthub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Harrison on 03/03/2018.
 */

@RestController
@RequestMapping("api/user")
public class UserController extends CRUDController<User, Long> {

    @Autowired
    private UserService service;

    public UserController(UserService service) {
        super(service);
        this.service = service;
    }

    @RequestMapping("/register")
    @Override
    public boolean save(@RequestBody User user) {
        return super.save(user);
    }
}
