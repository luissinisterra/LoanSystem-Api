package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.InvalidUsernameException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.User;
import com.loansytemapi.LoanSystem_Api.repository.UserRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) throws InvalidUsernameException {
        this.userRepository = userRepository;
        initSampleData();
    }

    private void initSampleData() throws InvalidUsernameException {
        saveUser(new User("Luis Eduardo", "Sinisterra", "luchoporst@gmail.com", "holamundo", "luchoporst", "M"));
        saveUser(new User("Alejandro", "Ballesteros", "alej0nt666@gmail.com", "holamundo", "alej0nt", "M"));
    }
    @Override
    public User saveUser(User user) throws InvalidUsernameException {
        if (user.getUsername() == null || user.getUsername().isEmpty() || user.getUsername().length() > 20) {
            throw new InvalidUsernameException("El nombre de usuario no puede estar vacio o ser de más de 10 caracteres");
        }
        return userRepository.save(user);
    }

    @Override
    public void removeUser(String id) throws NotFoundException {
        User u = userRepository.findById(id);
        if (u == null) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) throws NotFoundException {
        User u = userRepository.findById(user.getId());
        if (u == null) {
            throw new NotFoundException("User not found");
        }
        user.setId(u.getId());
        return userRepository.update(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
