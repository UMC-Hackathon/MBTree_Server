package com.mbtree.mbtree.repository;

import com.mbtree.mbtree.dto.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostDTO, Long> {

    PostDTO findById(int id);
}
