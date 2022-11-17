package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardCotroller {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm() {

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model) {

        boardService.write(board);

        model.addAttribute("message", "글 작성이 완료되었습니다.");

        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {

        model.addAttribute("list", boardService.boardList());
        return "boardlist";
    }

    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    public String boardList(Model model, Integer boardno) {

        model.addAttribute("board", boardService.boardView(boardno));
        return "boardview";
    }

    @GetMapping("/board/modify/{boardno}")
    public String boardModify(@PathVariable("boardno") Integer boardno, Model model) {

        model.addAttribute("board", boardService.boardView(boardno));
        return "boardmodify";
    }

    @PostMapping("/board/update/{boardno}")
    public String boardUpdate(@PathVariable("boardno") Integer boardno, Board board, Model model) {

        Board boardTemp = boardService.boardView(boardno);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getTitle());
        boardTemp.setWriter(board.getWriter());

        boardService.write(boardTemp);

        model.addAttribute("message", "글 수정이 완료되었습니다.");

        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer boardno) {

        boardService.boardDelete(boardno);

        return "redirect:/board/list";
    }
}