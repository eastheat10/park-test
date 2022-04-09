package com.nhnacademy.parking;

import java.util.ArrayList;
import java.util.List;

public class PaycoServer {

    private List<User> userList;

    public PaycoServer() {
        this.userList = new ArrayList<>();
    }

    public User join(User user) {
        userList.add(user);
        return user;
    }

    public User findById(Long id) {
        return null;
    }

    public boolean isExist(Long id) {
        for (User user : userList) {
            if (id.equals(user.getId())) {
                return true;
            }
        }
        return false;
    }
}
