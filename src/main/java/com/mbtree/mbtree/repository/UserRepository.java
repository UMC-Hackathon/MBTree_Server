package com.mbtree.mbtree.repository;

import com.mbtree.mbtree.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDTO, Long> {

    UserDTO findById(int id);
}
