package com.loansytemapi.LoanSystem_Api.service;

import com.loansytemapi.LoanSystem_Api.exception.InvalidUsernameException;
import com.loansytemapi.LoanSystem_Api.exception.NotFoundException;
import com.loansytemapi.LoanSystem_Api.model.User;
import com.loansytemapi.LoanSystem_Api.repository.IUserRepository;
import com.loansytemapi.LoanSystem_Api.service.imp.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) throws InvalidUsernameException {
        if (user.getUsername() == null || user.getUsername().isEmpty() || user.getUsername().length() > 20) {
            throw new InvalidUsernameException("El nombre de usuario no puede estar vacío o tener más de 20 caracteres");
        }

        // Encriptar contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void removeUser(int id) throws NotFoundException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) throws NotFoundException {
        if (!userRepository.existsById(user.getId())) {
            throw new NotFoundException("Usuario no encontrado");
        }

        // Si se actualiza la contraseña, encriptarla
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User loadUser(String username, String password) throws NotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new NotFoundException("Usuario no encontrado");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Contraseña incorrecta");
        }

        return user;
    }
}