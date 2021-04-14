package com.viewnext.suma.repository;

import org.springframework.data.repository.CrudRepository;

import com.viewnext.suma.domain.User;

import java.util.Optional;

/**
 * This interface allows us to save and retrieve Users
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAlias(final String alias);

}
