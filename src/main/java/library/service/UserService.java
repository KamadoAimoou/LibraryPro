package library.service;

import library.dao.UserDAO;
import library.dao.impl.UserDAOImpl;
import library.model.User;
import library.utils.HashUtil;

import java.util.Optional;

public class UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    public Optional<User> authenticate(String studentId, String password) {
        Optional<User> userOpt = userDAO.findByStudentId(studentId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String hash = HashUtil.sha256(password);
            if (hash.equals(user.getPasswordHash())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}

