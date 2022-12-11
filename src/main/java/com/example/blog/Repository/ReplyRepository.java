package com.example.blog.Repository;

import com.example.blog.Dto.ReplySaveRequestDto;
import com.example.blog.Model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value = "INSERT INTO reply(userId, boardId, createDate, content) VALUES(?1,?2,?3,now())", nativeQuery = true)
    public void mSave(ReplySaveRequestDto replySaveRequestDto);
}
