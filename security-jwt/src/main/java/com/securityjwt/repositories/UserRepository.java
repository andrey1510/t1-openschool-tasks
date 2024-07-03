package com.securityjwt.repositories;

import com.securityjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> { //ToDo

    Optional<User> findByUsername(String username);

}