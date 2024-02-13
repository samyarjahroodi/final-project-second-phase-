package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.operation.Comment;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.exception.StatusException;
import finalproject.finalproject.repository.CommentRepository;
import finalproject.finalproject.service.CommentService;
import finalproject.finalproject.service.dto.request.CommentDtoRequest;
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
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment addCommentForCustomerId(CustomerOrder customerOrder, CommentDtoRequest dto) {
        if (customerOrder.getStatus().equals(Status.BEEN_PAID)) {
            Comment comment = Comment.builder()
                    .comment(dto.getComment())
                    .star(dto.getStar())
                    .customerOrder(customerOrder)
                    .build();
            commentRepository.save(comment);
            return comment;
        } else {
            throw new StatusException("status is not being paid");
        }
    }

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
        return commentRepository.findById(integer).orElseThrow(() -> new NullInputException("null"));
    }


}
