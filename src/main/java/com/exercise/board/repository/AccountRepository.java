package com.exercise.board.repository;

import com.exercise.board.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Boolean existsByIdAndPassword(@Param("id")String id, @Param("password")String password);
    //계정 증복 확인
    Boolean existsById(@Param("id") String id);
    //닉네임 중복 확인
    Boolean existsByNickname(@Param("nickname") String nickname);
}
