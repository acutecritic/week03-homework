package com.sparta.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data//게터랑 셋터를 한번에 쓸수있게 만들어놓은것
public class PasswordCheckReqDto {

    private String password;
}
