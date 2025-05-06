package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.InvalidUsernameException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.User;
import com.loansytemapi.LoanSystem_Api.repository.imp.IUserRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) throws InvalidUsernameException {
        if (user.getUsername() == null || user.getUsername().isEmpty() || user.getUsername().length() > 20) {
            throw new InvalidUsernameException("El nombre de usuario no puede estar vacío o tener más de 20 caracteres");
        }
        return userRepository.save(user);
    }

    @Override
    public void removeUser(Integer id) throws NotFoundException {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new NotFoundException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) throws NotFoundException {
        Optional<User> userOpt = userRepository.findById(user.getId());
        if (userOpt.isEmpty()) {
            throw new NotFoundException("Usuario no encontrado");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User loadUser(String username, String password) throws NotFoundException {
        Optional<User> userOpt = userRepository.loadUser(username, password);
        return userOpt.orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }
}