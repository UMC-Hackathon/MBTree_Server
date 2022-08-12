package com.mbtree.mbtree.controller;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.config.BaseResponse;
import com.mbtree.mbtree.domain.buyPaper.BuyPaper;
import com.mbtree.mbtree.domain.buyPaper.BuyPaperRepository;
import com.mbtree.mbtree.domain.message.Message;
import com.mbtree.mbtree.domain.user.User;
import com.mbtree.mbtree.domain.user.UserRepository;
import com.mbtree.mbtree.dto.BuyPaperResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mbtree.mbtree.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

@RestController
public class BuyPaperController {

    @Autowired
    private BuyPaperRepository buyPaperRepository;

    @Autowired
    private UserRepository userRepository;

    //편지지 구매
    @PostMapping("/buy")
    public BaseResponse<BuyPaper> postBuyPaper(@RequestParam int userId, @RequestParam int paperStyle) throws BaseException {

        BuyPaper buyPaper = new BuyPaper();
        User user = userRepository.findById(userId);
        if (user == null) {
            System.out.println("편지지 구매자 USERS_EMPTY_USER_ID");
            throw new BaseException(USERS_EMPTY_USER_ID);
        }

        buyPaper.setUserId(user);
        buyPaper.setPaperStyle(paperStyle);
        buyPaper.setCreateDate(LocalDateTime.now());

        buyPaperRepository.save(buyPaper);
        return new BaseResponse<>(buyPaper);
    }

    //편지지 목록
    @GetMapping("/buy")
    public BaseResponse<List<Integer>> getBuyPaper(@RequestParam int userId) throws BaseException {

        User user = userRepository.findById(userId);
        if (user == null) {
            System.out.println("편지지 구매자 USERS_EMPTY_USER_ID");
            throw new BaseException(USERS_EMPTY_USER_ID);
        }

        //유저가 가진 편지지만 출력 -> 둘다 나오게 하면서 구분은 어떻게 구현..?
        List<Integer> userPaperList = buyPaperRepository.findByUserId(userId);
        return new BaseResponse<>(userPaperList);

    }

}
