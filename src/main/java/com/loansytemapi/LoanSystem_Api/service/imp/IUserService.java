package com.loansytemapi.LoanSystem_Api.service.imp;

import com.loansytemapi.LoanSystem_Api.dto.UserDTO;
import com.loansytemapi.LoanSystem_Api.dto.UserResponseDTO;
import com.loansytemapi.LoanSystem_Api.exception.InvalidUsernameException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.User;

import java.util.List;

public interface IUserService {
    UserResponseDTO saveUser(UserDTO user) throws InvalidUsernameException;
    void removeUser(int id)throws NotFoundException;
    UserResponseDTO updateUser(int id, UserDTO user) throws NotFoundException, InvalidUsernameException;
    List<UserResponseDTO> getUsers();
    UserResponseDTO loadUser(String username, String password) throws NotFoundException;
}
