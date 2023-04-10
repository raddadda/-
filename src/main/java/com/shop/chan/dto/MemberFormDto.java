package com.shop.chan.dto;


import com.shop.chan.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class MemberFormDto {

    @NotEmpty(message="닉네임은 필수 입력값입니다.")
    private String username;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message="이메일은 필수 입력값입니다.")
    private String email;

    @NotEmpty(message="비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotEmpty(message="주소는 필수 입력값입니다.")
    private String address;

    private String role;

    public Member toEntity() {

        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .address(address)
                .role(role)
                .build();
    }
}
