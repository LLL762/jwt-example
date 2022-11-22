package com.delacasa.jwt.repo;

import com.delacasa.jwt.entity.ApiUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiUserRepo extends JpaRepository<ApiUser, Long> {

    @EntityGraph(attributePaths = {"role"})
    Optional<ApiUser> findByUsername(String username);
}
