package com.nhnacademy.parking.user;

import java.util.ArrayList;
import java.util.List;

public class PaycoServer {

    private final List<User> userList;

    public PaycoServer() {
        this.userList = new ArrayList<>();
    }

    public User join(User user) {
        userList.add(user);
        return user;
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
