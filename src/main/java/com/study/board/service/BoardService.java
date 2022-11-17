package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files"; //저장경로

        UUID uuid = UUID.randomUUID(); //식별자, 랜덤이름생성

        String fileName = uuid + "_" + file.getOriginalFilename(); //파일이름을 랜덤이름_파일오리지널네임

        File saveFile = new File(projectPath, fileName); //파일 저장

        file.transferTo(saveFile); //파일 저장

        board.setFilename(fileName);
        board.setFilepath("/files/"+fileName);

        boardRepository.save(board);

    }
    // 게시글 리스트 처리
    public List<Board> boardList() {

        return boardRepository.findAll();
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer boardno) {

        return boardRepository.findById(boardno).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer boardno) {

        boardRepository.deleteById(boardno);
    }
}
