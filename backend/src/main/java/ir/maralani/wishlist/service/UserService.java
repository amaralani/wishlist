package ir.maralani.wishlist.service;

import ir.maralani.wishlist.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByUsername(String username);
}
