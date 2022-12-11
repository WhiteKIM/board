package com.example.blog.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob    //대용량 데이터
    private String content; //섬머노트 라이브러리 사용<html>태그가 사용됨

    @ColumnDefault("0")
    private int count;//조회수

    @ManyToOne  //Many = Board, User = one
    @JoinColumn(name="userId")
    private User user;  //DB는 오브젝트 저장 불가능

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)//mappedby는 연관관계의 주인이 아니다(Fk X), 디비에 컬럼 생성X
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;
}
