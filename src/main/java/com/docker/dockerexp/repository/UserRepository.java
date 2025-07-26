package com.docker.dockerexp.repository;

import com.docker.dockerexp.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
