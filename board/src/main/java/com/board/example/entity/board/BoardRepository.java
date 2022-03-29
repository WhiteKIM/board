/*
 * @ JpaRepository를 상속받아 CRUD 기능을 담당하는 인터페이스 생성
 * @ Query를 사용한 JPQL 방식의 updateBoard 메소드 구현
 * @ 이 방식으로 쿼리를 직접 작성하여 사용가능
 */

package com.board.example.entity.board;

import com.board.example.dto.board.BoardRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	String UPDATE_BOARD = "update board "
			+ "set title = :#{#boardRequestDto.title}, "
			+ "content = :#{#boardRequestDto.content}, "
			+ "update_time = NOW() "
			+ "where id = :#{#boardRequestDto.id}";
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_BOARD, nativeQuery = true)
	public int updateBoard(@Param("boardRequestDto") BoardRequestDto boardRequestDto);
}
