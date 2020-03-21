package ir.maralani.wishlist.service;

import ir.maralani.wishlist.domain.User;
import ir.maralani.wishlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(userRepository.getByUsername(username));
    }
}
