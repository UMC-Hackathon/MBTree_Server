package com.mbtree.mbtree.domain.user;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(int userId) throws BaseException;

}