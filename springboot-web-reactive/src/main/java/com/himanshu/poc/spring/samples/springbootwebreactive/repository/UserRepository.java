package com.himanshu.poc.spring.samples.springbootwebreactive.repository;

import com.himanshu.poc.spring.samples.springbootwebreactive.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
