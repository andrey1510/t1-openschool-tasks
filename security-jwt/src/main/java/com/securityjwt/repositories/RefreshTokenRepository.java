package com.securityjwt.repositories;

import com.securityjwt.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT rt " +
        "FROM RefreshToken rt " +
        "JOIN rt.user u " +
        "WHERE u.username = :username " +
        "ORDER BY rt.expirationTime DESC " +
        "LIMIT 1")
    Optional<List<RefreshToken>> findLatestRefreshToken(@Param("username") String username);

}
