package net.gupt.community.service;

import com.github.pagehelper.PageInfo;
import net.gupt.community.entity.Comment;

/**
 * <h3>gupt-community</h3>
 * <p>评论业务层接口类</p>
 *
 * @author : Cui
 * @date : 2019-07-30 18:34
 **/
public interface CommentService {
    /**
     * 获取评论列表
     *
     * @param articleId 文章ID
     * @param pageNum   文章页码
     * @param pageSize  文章页面大小
     * @param type      文章类型
     * @return PageInfo
     */
    PageInfo<Comment> getComments(Byte type, Integer articleId, Integer pageNum, Integer pageSize);

    /**
     * 发表评论
     *
     * @param comment 评论实体对象
     * @return int
     */
    int postComment(Comment comment);

    /**
     * 删除评论
     *
     * @param articleId   文章ID
     * @param articleType 文章类型
     * @return int
     */
    int deleteComment(Integer articleId, Integer articleType);

    /**
     * 通过注解删除
     *
     * @param id 文章ID
     * @return int
     */
    int deleteByPrimaryId(Integer id);
}
