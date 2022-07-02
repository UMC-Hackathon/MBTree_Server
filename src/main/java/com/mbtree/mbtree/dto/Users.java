package com.mbtree.mbtree.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Users {
    private String name;
    private String userToken;
    private String email;
    private String myers;
    private String location;
}
