package com.sepideh.notebook.module.user.repository;

import com.sepideh.notebook.module.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String  username);

    @Query(value = "SELECT user from User user where user.username = :username")
    User userByUsername(@Param("username") String username);

}
