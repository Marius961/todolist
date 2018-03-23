package ua.product.manager.services.interfaces;

import ua.product.manager.models.User;
import ua.product.manager.models.UserData;

public interface UserService {

    boolean registerUser(User user);
    void autologin();
    boolean checkUser(User user);
    User checkAndGetUser(UserData user);
    User getFullData(User user);
    boolean checkUserTelNumber(String telnumber);
}