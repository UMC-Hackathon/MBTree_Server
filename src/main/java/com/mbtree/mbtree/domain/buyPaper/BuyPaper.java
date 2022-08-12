package com.mbtree.mbtree.domain.buyPaper;

import com.mbtree.mbtree.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BuyPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buyPaperId; //pk

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

    private int paperId; //구입한 편지지

    private LocalDateTime createDate; //생성시간

}
