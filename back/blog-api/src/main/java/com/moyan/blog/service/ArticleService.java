package com.moyan.blog.service;

import com.moyan.blog.vo.Result;
import com.moyan.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);
}
