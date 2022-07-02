package com.mbtree.mbtree.repository;

import com.mbtree.mbtree.dto.TreeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<TreeDTO, Long> {

    TreeDTO findById(int id);

    TreeDTO findByUserId(int id);
}
