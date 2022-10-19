package com.ll.exam.ebook.app.base.initData;

import com.ll.exam.ebook.app.article.service.ArticleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestInitData implements InitDataBefore {
    @Bean
    CommandLineRunner initData(
            ArticleService articleService) {
        return args -> {
            before(articleService);
        };
    }
}