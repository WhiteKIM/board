package com.example.blog.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

/*
@ 카카오로 받을 정보
@ 프로필(필수), 이메일(선택)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert  //insert시 null은 제외
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트의 넘버링 전략 따라감
    private int id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 100)//해시 암호화
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //DB는 RoleType이 존재하지 않음
    @Enumerated(EnumType.STRING)
    private RoleType role;//Enum을 쓰는것이 좋음

    private String oauth;   //kakao, google

    @CreationTimestamp  // 시간이 자등으로 입력
    private Timestamp createDate;
}
