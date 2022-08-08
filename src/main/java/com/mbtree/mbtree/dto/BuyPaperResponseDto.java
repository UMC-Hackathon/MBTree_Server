package com.mbtree.mbtree.dto;

import com.mbtree.mbtree.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuyPaperResponseDto {
    private int userId;
}
