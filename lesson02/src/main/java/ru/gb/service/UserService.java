package ru.gb.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.gb.entity.User;
import ru.gb.repository.UserDao;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(@Qualifier("jdbc") UserDao userDao) {
        this.userDao = userDao;
    }


    public List<User> selectAllUsers(){
        return userDao.selectAllUsers();
    }

    public void insertUser(User user){
        userDao.insertUser(user);
    }

    public void deleteUserById(Integer id){
        userDao.deleteUserById(id);
    }

    public User selectUserById(Integer id) {
        return userDao.selectUserById(id)
                .orElseThrow(() -> new RuntimeException(
                        "customer with ID: %d not found".formatted(id)
                ));
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
