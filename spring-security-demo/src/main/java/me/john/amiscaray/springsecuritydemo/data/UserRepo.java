package me.john.amiscaray.springsecuritydemo.data;

import me.john.amiscaray.springsecuritydemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

}
