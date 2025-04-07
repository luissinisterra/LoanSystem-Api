package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.exception.InvalidUsernameException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.User;

import java.util.List;

public interface IUserService {

    User saveUser(User user) throws InvalidUsernameException;
    void removeUser(String id)throws NotFoundException;
    User updateUser(User user) throws NotFoundException;
    List<User> getUsers();
}
