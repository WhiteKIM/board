package com.example.blog.Service;

import com.example.blog.Model.Board;
import com.example.blog.Model.Reply;
import com.example.blog.Model.User;
import com.example.blog.Repository.BoardRepository;
import com.example.blog.Repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void write(Board board, User user)
    {
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> boardList(Pageable pageable)
    {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board boardDetail(int id)
    {
        return boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 상세보기 실패");
        });
    }

    @Transactional
    public void delete(int id)
    {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Board requestBoard)
    {
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패");
        });
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        //해당 함수 종료시(서비스가 종료) 트랜잭션이 종료, 이 때에 더티체크 발생 - 자동 업데이트로 db에 플러시가 이루어짐
    }

    @Transactional
    public void replyWrite(User user, int boardId, Reply reply)
    {
        Board board = boardRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("댓글 작성 실패 : 존재하지 않는 게시글입니다.");
        });

        reply.setUser(user);
        reply.setBoard(board);

        replyRepository.save(reply);
    }
    @Transactional
    public void replyDelete(int replyId)
    {
        replyRepository.deleteById(replyId);
    }
}
