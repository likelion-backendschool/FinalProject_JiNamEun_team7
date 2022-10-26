package main.java.com.ll.exam.ebook.app.productKeyword.repository;


import com.ll.exam.ebook.app.productKeyword.entity.ProductKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductKeywordRepository extends JpaRepository<ProductKeyword, Long> {
    Optional<ProductKeyword> findByContent(String keywordContent);
}
