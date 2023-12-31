package com.ssafy.chartflow.board.service;

import com.ssafy.chartflow.board.dto.response.ArticleResponseDto;
import com.ssafy.chartflow.board.entity.Article;
import com.ssafy.chartflow.board.entity.Likes;
import com.ssafy.chartflow.board.repository.ArticleRepository;
import com.ssafy.chartflow.board.repository.CustomLikeRepository;
import com.ssafy.chartflow.board.repository.LikeRepository;
import com.ssafy.chartflow.exception.LikeDuplicateException;
import com.ssafy.chartflow.exception.NoSuchLikeException;
import com.ssafy.chartflow.user.entity.User;
import com.ssafy.chartflow.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor

public class ArticleServiceImp implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    @Override
    public ArticleResponseDto writeArticle(String email,String title, String content) {
        User user = userRepository.findByEmail(email);
        Article article = new Article();
        article.setUser(user);
        article.setRegisterTime(LocalDateTime.now());
        article.setTitle(title);

        userRepository.save(user);
        articleRepository.save(article);

        ArticleResponseDto articleResponseDto = new ArticleResponseDto();
        articleResponseDto.setArticleNo(article.getArticleId());
        articleResponseDto.setContent(article.getContent());
        articleResponseDto.setNickName(article.getUser().getNickname());

        return articleResponseDto;
    }

    @Override
    public ArticleResponseDto modifyArticle(long articleId, long userId,String title,String content) {
        User user = userRepository.findUserByUserId(userId);
        Article article = articleRepository.findArticleByArticleId(articleId);
        article.setContent(content);
        article.setTitle(title);
        articleRepository.save(article);

        ArticleResponseDto articleResponseDto = new ArticleResponseDto();
        articleResponseDto.setNickName(user.getNickname());
        articleResponseDto.setArticleNo(articleId);
        articleResponseDto.setContent(content);

        return articleResponseDto;
    }

    @Override
    public void deleteArticle(long articleId) {
        Article article = articleRepository.findArticleByArticleId(articleId);
        article.setDeleted(true);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void likeArticle(long userId, long articleId){

        if (likeRepository.findLikesByUserIdAndArticleId(userId,articleId) != null){
            throw new LikeDuplicateException();
        }

        User user = userRepository.findUserByUserId(userId);
        Article article = articleRepository.findArticleByArticleId(articleId);

        Likes likes = new Likes();
        likes.setArticle(article);
        likes.setUser(user);
        likeRepository.save(likes);
    }

    @Override
    public void withdrawLike(long likeId) {
        likeRepository.deleteById(likeId);
    }

}
