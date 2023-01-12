package recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import recipes.model.User;

@Service
public interface UserService {

    User save(User user);

    boolean emailExists(String email);

    User getUserByEmail(String email);
}
