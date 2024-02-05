package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.operation.Comment;
import finalproject.finalproject.repository.CommentRepository;
import finalproject.finalproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(Integer integer) {
        return commentRepository.findById(integer);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findAllById(Iterable<Integer> integers) {
        return commentRepository.findAllById(integers);
    }

    @Override
    public List<Comment> findAll(Sort sort) {
        return commentRepository.findAll(sort);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteById(Integer integer) {
        commentRepository.deleteById(integer);
    }

    @Override
    @Transactional
    public void delete(Comment entity) {
        commentRepository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends Comment> entities) {
        commentRepository.deleteAll(entities);
    }

    @Override
    @Transactional
    public void deleteAll() {
        commentRepository.deleteAll();
    }

    @Override
    public Comment getReferenceById(Integer integer) {
        return commentRepository.getReferenceById(integer);
    }
}
