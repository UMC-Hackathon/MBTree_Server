package com.mbtree.mbtree.domain.buyPaper;

import com.mbtree.mbtree.config.BaseException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface BuyPaperRepository extends JpaRepository<BuyPaper, Long> {

    //User가 가진 편지지(paper_id) 목록 반환
    @Query(value = "select paper_style from buy_paper where user_id=?;", nativeQuery = true)
    List<Integer> findByUserId(int userId) throws BaseException;
}
