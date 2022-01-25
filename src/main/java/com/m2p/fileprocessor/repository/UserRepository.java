package com.m2p.fileprocessor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.m2p.fileprocessor.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
