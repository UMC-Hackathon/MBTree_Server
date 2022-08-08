package com.mbtree.mbtree.domain.buyPaper;

import com.mbtree.mbtree.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BuyPaper {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buyPaperId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

}
