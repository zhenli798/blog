package com.moyan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyan.blog.dao.mapper.ArticleMapper;
import com.moyan.blog.dao.pojo.Article;
import com.moyan.blog.service.ArticleService;
import com.moyan.blog.vo.ArticleVo;
import com.moyan.blog.vo.Result;
import com.moyan.blog.vo.params.PageParams;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired(required = false)
    private ArticleMapper articleMapper;
    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 1. 分页查询 article 数据库表
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 是否置顶排
        // queryWrapper.orderByDesc(Article::getWeight); // 是否置顶进行排序
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate); // 按最新时间排序
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        // 不能直接返回，需要去掉一些不用去展示的内容
        List<ArticleVo> articleVoList = copyList(records);
        return Result.success(articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records){
            articleVoList.add(copy(record));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }
}
