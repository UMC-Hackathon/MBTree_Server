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

import static com.mbtree.mbtree.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

@RestController
public class BuyPaperController {

    @Autowired
    private BuyPaperRepository buyPaperRepository;

    @Autowired
    private UserRepository userRepository;

    //편지지 구매
    @PostMapping("/buy")
    public BaseResponse<BuyPaper> postBuyPaper(@RequestBody BuyPaperResponseDto buyPaperResponseDto) throws BaseException {

        BuyPaper buyPaper = new BuyPaper();
        User user = userRepository.findById(buyPaperResponseDto.getUserId());
        if(user == null ){System.out.println("편지지 구매자 USERS_EMPTY_USER_ID" ); throw new BaseException(USERS_EMPTY_USER_ID);}
        buyPaper.setUserId(user);
        int paperId = buyPaperResponseDto.getPaperId();
        buyPaper.setPaperId(paperId);
        buyPaperRepository.save(buyPaper);
        return new BaseResponse<>(buyPaper);
    }

    //편지지 구매 내역
//    @GetMapping("/buy")
//    public String postBuyPaper(){
//
//    }
}
