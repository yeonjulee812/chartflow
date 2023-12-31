package com.ssafy.chartflow.board.repository;

import com.ssafy.chartflow.board.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    public Comments findCommentByCommentId(long commentId);

    //FIXME: N+1 문제 발생 및 JpaRepository가 인식 못함
    //public List<Comments> findAllByArticleId(long articleId);


}
