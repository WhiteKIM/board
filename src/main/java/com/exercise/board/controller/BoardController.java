package com.exercise.board.controller;

import com.exercise.board.entity.Account;
import com.exercise.board.entity.Board;
import com.exercise.board.entity.Message;
import com.exercise.board.service.AccountService;
import com.exercise.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/")//위의 경로에서 보일 내용
    public String main()
    {
        return "boardlogin";
    }


    @PostMapping("/login")
    public ModelAndView boardLogin(Account account, ModelAndView model)
    {
     Boolean istrue = accountService.Login(account);
     System.out.println(istrue.toString());

     if(istrue)
     {
         model.addObject("data", new Message("로그인 성공", "/board/list"));
         model.setViewName("Message");
         return model;
     }
     else
     {
         model.addObject("data", new Message("로그인 실패", "/"));
         model.setViewName("Message");
         return model;
     }
    }

    @GetMapping("/register")
    public String boardRegister()
    {
        return "boardregister";
    }

    @PostMapping("/registerdo")
    public ModelAndView boardRegisterdo(Account account, ModelAndView model)
    {
        accountService.Register(account);
        model.addObject("data", new Message("회원가입이 완료되었습니다", "/"));
        model.setViewName("Message");

        return model;
    }

    @GetMapping("/board/write")//localhost:8090/board/write에 나타남
    public String boardWriteForm()
    {
        return "boardwrite";
    }

    @PostMapping("/board/writedo")
    public String boardWritedo(Board board, Model model, MultipartFile file) throws Exception
    {
        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다");
        model.addAttribute("model", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public  String boardlist(Model model,
                             @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             String searchKeyword)
    {
        Page<Board> list = null;
        if(searchKeyword ==null)
        {
            list = boardService.boardList(pageable);
        }else{
            list = boardService.boardSearch(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() +1;
        int startPage = Math.max(1,nowPage-4);
        int endPage = Math.min(list.getTotalPages(), nowPage+5);

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardlist";
    }

    @GetMapping("/board/view")  //localhost:8090/board/view?id=1
    public String boardView(Model model, Integer id)
    {
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id)
    {
        boardService.boardDelete(id);

        return "redirect:/board/list";
    }
    @GetMapping("/board/modify/{id}")
    public  String boardModify(@PathVariable("id") Integer id, Model model)
    {
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }
    @GetMapping("/board/update/{id}")
    public  String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception
    {
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }
}
