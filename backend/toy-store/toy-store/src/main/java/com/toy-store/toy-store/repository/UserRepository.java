package com.petshop.petshop.repository;

import com.petshop.petshop.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"roles"})
    Optional<User> findByUsername(String username);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"roles"})
    List<User> findAll();


}
