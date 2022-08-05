package com.mbtree.mbtree.repository;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(int userId) throws BaseException;

}
