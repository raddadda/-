package com.shop.chan.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="member")
public class Member {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true) // 닉네임 중복 방지
    private String username;
    @Column(unique = true) // 이메일 중복 방지
    private String email;
    private String name;
    private String password;
    private String address;
    private String role;

    // 회원의 장바구니
    @OneToOne(mappedBy = "member")
    private Basket basket;
    // 회원의 주문
    @OneToMany(mappedBy = "member")
    private List<Order> memberOrder = new ArrayList<>();
    // 회원의 주문상품들
    @OneToMany(mappedBy = "member")
    private List<OrderItem> memberOrderItem = new ArrayList<>();


    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDate.now();
    }



    //   private List<Item> items = new ArrayList<>();

/*    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setUsername(memberFormDto.getUsername());
        // 스프링 시큐리티 설정을 클래스에 등록한 BCryptPassword Bean을 파라미터로 넘겨서 비밀번호를 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole("ADMIN");

        return member;
    }*/
/*    private Member(String email, String password,String address){
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public static Member createMember(String email, String password,String address){
        return new Member(email,password,address);
    }*/
/*
    @Builder
    public Member(String email, String password,String address, Role role){
        this.email = email;
        this.address = address;
        this.password = password;
        this.role = role;
    }*/
}
