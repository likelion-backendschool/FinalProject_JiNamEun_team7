package main.java.com.ll.exam.ebook.app.postTag.service;

import com.ll.exam.ebook.app.member.entity.Member;
import com.ll.exam.ebook.app.post.entity.Post;
import com.ll.exam.ebook.app.postTag.entity.PostTag;
import main.java.com.ll.exam.ebook.app.postTag.repository.PostTagRepository;
import main.java.com.ll.exam.ebook.app.postkeyword.entity.PostKeyword;
import main.java.com.ll.exam.ebook.app.postkeyword.service.PostKeywordService;
import main.java.com.ll.exam.ebook.app.productTag.entity.ProductTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostTagService {
    private final PostKeywordService postKeywordService;
    private final PostTagRepository postTagRepository;

    public void applyPostTags(Post post, String postTagContents) {
        List<PostTag> oldPostTags = getPostTags(post);

        List<String> postKeywordContents = Arrays.stream(postTagContents.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        List<PostTag> needToDelete = new ArrayList<>();

        for (PostTag oldPostTag : oldPostTags) {
            boolean contains = postKeywordContents.stream().anyMatch(s -> s.equals(oldPostTag.getPostKeyword().getContent()));

            if (contains == false) {
                needToDelete.add(oldPostTag);
            }
        }

        needToDelete.forEach(postTag -> postTagRepository.delete(postTag));

        postKeywordContents.forEach(postKeywordContent -> {
            savePostTag(post, postKeywordContent);
        });
    }

    private PostTag savePostTag(Post post, String postKeywordContent) {
        PostKeyword postKeyword = postKeywordService.save(postKeywordContent);

        Optional<PostTag> opPostTag = postTagRepository.findByPostIdAndPostKeywordId(post.getId(), postKeyword.getId());

        if (opPostTag.isPresent()) {
            return opPostTag.get();
        }

        PostTag postTag = PostTag.builder()
                .post(post)
                .member(post.getAuthor())
                .postKeyword(postKeyword)
                .build();

        postTagRepository.save(postTag);

        return postTag;
    }

    public List<PostTag> getPostTags(Post post) {
        return postTagRepository.findAllByPostId(post.getId());
    }

    public List<PostTag> getPostTagsByPostIdIn(long[] ids) {
        return postTagRepository.findAllByPostIdIn(ids);
    }

    public List<PostTag> getPostTags(Member member, String postKeywordContent) {
        return postTagRepository.findAllByMemberIdAndPostKeyword_contentOrderByPost_idDesc(member.getId(), postKeywordContent);
    }

    public List<PostTag> getPostTags(long authorId, long postKeywordId) {
        return postTagRepository.findAllByMemberIdAndPostKeywordIdOrderByPost_idDesc(authorId, postKeywordId);
    }

    public List<ProductTag> getPostTags(String productTagContent) {
        return postTagRepository.findAllByPostKeyword_contentOrderByPost_idDesc(productTagContent);
    }
}
