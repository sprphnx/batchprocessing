package com.sprphnx.batchprocessing.repository;

import org.springframework.data.repository.CrudRepository;

import com.sprphnx.batchprocessing.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
