package com.example.blog.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.blog.Service.BoardService;
import com.example.blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    //@AuthenticationPrincipal PrincipalDetail principal
    @GetMapping({"","/"})
    public String index(Model model, @PageableDefault(size=3, sort = "id", direction = Sort.Direction.DESC) Pageable pagealbe)
    {
        model.addAttribute("boards", boardService.boardList(pagealbe));

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm()
    {
        return "board/saveForm";
    }
    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model)
    {
        model.addAttribute("board", boardService.boardDetail(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model)
    {
        model.addAttribute("board", boardService.boardDetail(id));
        return "board/updateForm";
    }
}
