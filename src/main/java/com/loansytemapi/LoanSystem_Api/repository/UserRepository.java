package com.loansytemapi.LoanSystem_Api.repository;

import com.loansytemapi.LoanSystem_Api.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    // Simulamos una base de datos con un Map
    private final Map<String, User> baseDeDatos = new HashMap<>();

    // Guardar un usuario
    public User save(User usuario) {
        baseDeDatos.put(usuario.getId(), usuario);
        return usuario;
    }

    // Encontrar usuario por ID
    public User findById(String id) {
        return baseDeDatos.get(id);
    }

    // Listar todos los usuarios
    public List<User> findAll() {
        return new ArrayList<>(baseDeDatos.values());
    }

    // Eliminar un usuario
    public void deleteById(String id) {
        baseDeDatos.remove(id);
    }

    // Actualizar un usuario
    public User update(User usuario) {
        if (baseDeDatos.containsKey(usuario.getId())) {
            baseDeDatos.put(usuario.getId(), usuario);
            return usuario;
        }
        return null;
    }

    public User loadUser(String username, String password) {
        for (User user : baseDeDatos.values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
