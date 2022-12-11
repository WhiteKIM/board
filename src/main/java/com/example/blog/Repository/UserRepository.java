package com.example.blog.Repository;

import com.example.blog.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //JPA 네이밍 전략
    //User findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
    /*
    @Query(value="SELECT * FROM user WHERE username=?1 AND password=?2", nativeQuery = true)
    User login(String username, String password);
    */
}
