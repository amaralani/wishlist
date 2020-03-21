package ir.maralani.wishlist.repository;


import ir.maralani.wishlist.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User getByUsername(String username);
}
