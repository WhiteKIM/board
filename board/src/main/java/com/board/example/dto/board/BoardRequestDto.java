/*
 * @ 게시물 등록, 수정, 상세 조회에 필요한 정보 정의
 */

package com.board.example.dto.board;

import com.board.example.entity.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
	private Long id;
	private String title;
	private String content;
	private String registerId;
	
	public Board toEntity()
	{
		return Board.builder().title(title).content(content).registerId(registerId).build();
	}
}
