package net.gupt.community.controller;

import lombok.extern.slf4j.Slf4j;
import net.gupt.community.annotation.AuthToken;
import net.gupt.community.entity.CodeMsg;
import net.gupt.community.entity.Likes;
import net.gupt.community.entity.Result;
import net.gupt.community.entity.Student;
import net.gupt.community.service.LikesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <h3>gupt-community</h3>
 * <p>点赞web控制层</p>
 *
 * @author : Cui
 * @date : 2019-07-30 19:17
 **/
@Slf4j
@AuthToken
@RestController
@RequestMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LikesController {

    private final LikesService likesService;


    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    /**
     * 获取Student对象
     *
     * @param request <br/>
     * @return <br/>
     */
    private Student getStudent(HttpServletRequest request) {
        String studentObject = "Student";
        return (Student) request.getAttribute(studentObject);
    }

    /**
     * 发表点赞
     *
     * @param likes Likes实体对象
     * @return Result
     */
    @PostMapping(value = "/postLikeOrView", produces = "application/json")
    public Result postLikes(@RequestBody Likes likes, HttpServletRequest request) {
        Student student = getStudent(request);
        likes.setUid(student.getUid());
        int executeResult = likesService.postLikes(likes);
        if (executeResult > 0) {
            return Result.success(CodeMsg.SUCCESS);
        } else {
            return Result.error(CodeMsg.FAILED);
        }
    }

    /**
     * 查询是否点过赞或浏览过
     *
     * @param articleId   <br/>
     * @param articleType <br/>
     * @param request     <br/>
     * @return Result <br/>
     */
    @GetMapping(value = "/isLikesOrView")
    public Result isLikes(@RequestParam(value = "articleId") Integer articleId,
                          @RequestParam(value = "articleType") Byte articleType, HttpServletRequest request) {
        Student student = getStudent(request);
        Integer uid = student.getUid();
        String info = request.getParameter("info");
        Likes likes;
        if (info == null) {
            likes = likesService.findIsLikes(articleId, articleType, uid);
            return responseResult(likes);
        } else {
            likes = likesService.findIsLikes(articleId, articleType, uid, info);
            return responseResult(likes);
        }
    }

    /**
     * 删除点赞
     *
     * @param articleType 文章类型
     * @param articleId   文章唯一标识
     * @return Result
     */
    @RequestMapping(value = "/deleteLikes", method = RequestMethod.GET)
    public Result deleteLikes(@RequestParam(value = "articleId") Integer articleId,
                              @RequestParam(value = "articleType") Byte articleType, HttpServletRequest request) {
        Student student = getStudent(request);
        Integer uid = student.getUid();
        int executeResult = likesService.deleteLikes(articleId, articleType, uid);
        if (executeResult > 0) {
            return Result.success(CodeMsg.SUCCESS);
        } else {
            return Result.error(CodeMsg.MISSING_RECORD, false);
        }
    }

    /**
     * 获取点赞数量
     *
     * @param articleId   <br/>
     * @param articleType <br/>
     * @return Result
     */
    @GetMapping(value = "/getLikesOrViews")
    public Result getLikes(@RequestParam(value = "articleId") Integer articleId,
                           @RequestParam(value = "articleType") Byte articleType,
                           @RequestParam(value = "info", required = false) String info) {
        //判断参数是否存在info，如果不存在调用获取点赞数量，反之则调用获取浏览量
        if (info == null) {
            Likes likes = likesService.getLikes(articleId, articleType);
            if (likes.getLoveNum() > 0) {
                return Result.success(CodeMsg.SUCCESS, likes.getLoveNum());
            } else {
                return Result.error(CodeMsg.MISSING_RECORD, likes.getLoveNum());
            }
        } else {
            Likes likes = likesService.getLikes(articleId, articleType, info);
            if (likes.getViewNum() > 0) {
                return Result.success(CodeMsg.SUCCESS, likes.getViewNum());
            } else {
                return Result.error(CodeMsg.MISSING_RECORD, likes.getViewNum());
            }
        }
    }

    /**
     * 结果输出函数
     *
     * @param result <br/>
     * @return Result
     */
    private Result responseResult(Likes result) {
        if (result != null) {
            return Result.success(CodeMsg.SUCCESS, true);
        } else {
            return Result.error(CodeMsg.MISSING_RECORD, false);
        }

    }

}
