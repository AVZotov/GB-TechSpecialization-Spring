package ru.gb.repository;

import ru.gb.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> selectAllUsers();
    Optional<User> selectUserById(Integer id);
    void insertUser(User user);
    void deleteUserById(Integer id);
    void updateUser(User user);
}
