package com.sepideh.notebook.repository;

import com.sepideh.notebook.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String  username);

    @Query(value = "SELECT user from User user where user.username = :username")
    User userByUsername(@Param("username") String username);

    Page<User> findAll(Pageable pageable);

}
