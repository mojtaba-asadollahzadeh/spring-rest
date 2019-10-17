package com.app.restApplication.services;

import com.app.restApplication.models.User;
import java.util.List;

public interface UserService {

    User fetchById(Long id);
    List<User> fetchAll();
    User save(User user);
    User update(User user);
    void delete(Long id);
}
