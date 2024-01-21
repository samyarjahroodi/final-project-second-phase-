package finalproject.finalproject.service.impl;

import finalproject.finalproject.repository.CommentRepository;
import finalproject.finalproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl
        implements CommentService {
    private final CommentRepository commentRepository;

}