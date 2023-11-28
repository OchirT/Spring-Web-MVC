package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@org.springframework.stereotype.Repository
public class PostRepositoryStubImpl implements PostRepository {
    private final Map<Long, Post> postMap = new ConcurrentHashMap<>();
    private final AtomicLong count = new AtomicLong(0);

    @Override
    public List<Post> all() {
        return postMap.values().stream().toList();
    }

    @Override
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postMap.get(id));
    }

    @Override
    public Post save(Post post) {
        long counter = count.incrementAndGet();
        if (post.getId() > postMap.size()) {
            throw new NotFoundException("Not found message");
        }
        if (post.getId() != 0) {
            postMap.put(post.getId(), post);
        } else {
            post.setId(counter);
            postMap.put(post.getId(), post);
        }

        return post;
    }

    @Override
    public void removeById(long id) {
        postMap.remove(id);
    }
}
