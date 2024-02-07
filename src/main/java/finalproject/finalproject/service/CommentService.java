package finalproject.finalproject.service;

import finalproject.finalproject.Entity.operation.Comment;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.service.dto.request.CommentDtoRequest;
import org.springframework.stereotype.Service;

@Service
public interface CommentService extends BaseService<Comment, Integer> {
    Comment addCommentForCustomerId(CustomerOrder customerOrder, CommentDtoRequest dto);
}
