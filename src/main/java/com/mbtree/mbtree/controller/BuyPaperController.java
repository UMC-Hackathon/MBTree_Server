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

import static com.mbtree.mbtree.config.BaseResponseStatus.PAPER_ALREADY_BUY;
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

        try {
            BuyPaper buyPaper = new BuyPaper();
            User user = userRepository.findById(userId);

            if (user == null) {
                System.out.println("편지지 구매자 USERS_EMPTY_USER_ID");
                throw new BaseException(USERS_EMPTY_USER_ID);
            }

            //이미 구매한 편지지인 경우 예외처리
            List<Integer> userPaperList = buyPaperRepository.findByUserId(userId);
            if (userPaperList.contains(paperStyle)) {
                System.out.println("이미 구매한 편지지입니다.");
                throw new BaseException(PAPER_ALREADY_BUY);
            }

            buyPaper.setUserId(user);
            buyPaper.setPaperStyle(paperStyle);
            buyPaper.setCreateDate(LocalDateTime.now());

            buyPaperRepository.save(buyPaper);
            return new BaseResponse<>(buyPaper);
        }
        catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    //편지지 목록(구매내역)
    @GetMapping("/buy")
    public BaseResponse<List<Integer>> getBuyPaper(@RequestParam int userId) throws BaseException {

        //유저가 구매한 편지지 목록
        try {
            User user = userRepository.findById(userId);
            if (user == null) {
                System.out.println("편지지 구매자 USERS_EMPTY_USER_ID");
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
            List<Integer> userPaperList = buyPaperRepository.findByUserId(userId);
            return new BaseResponse<>(userPaperList);
        }
        catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

}
