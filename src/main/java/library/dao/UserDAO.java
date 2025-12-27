package library.dao;

import library.model.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByStudentId(String studentId);
}

