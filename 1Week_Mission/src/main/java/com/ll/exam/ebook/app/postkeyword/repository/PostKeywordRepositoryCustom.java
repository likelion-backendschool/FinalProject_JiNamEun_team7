package main.java.com.ll.exam.ebook.app.postkeyword.repository;

import com.ll.exam.ebook.app.postkeyword.entity.PostKeyword;

import java.util.List;

public interface PostKeywordRepositoryCustom {
    List<PostKeyword> getQslAllByAuthorId(Long authorId);
}
