package com.mbtree.mbtree.repository;

import com.mbtree.mbtree.dto.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostDTO, Long> {

    PostDTO findById(int id);
    // select * frow post where id = ?

    List<PostDTO> findByTreeID(int id);
}
