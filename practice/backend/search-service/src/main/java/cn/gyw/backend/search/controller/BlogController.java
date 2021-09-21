package cn.gyw.backend.search.controller;

import cn.gyw.backend.search.domain.EsBlog;
import cn.gyw.backend.search.repository.EsBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private EsBlogRepository esBlogRepository;

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping
    public List<EsBlog> findByPage(@RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "summary", required = false) String summary,
                                   @RequestParam(value = "content", required = false) String content,
                                   @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title,
                summary, content, pageIndex, pageSize);
    }
}
