package finalproject.finalproject.service.impl;




public class CommentServiceImpl
        extends BaseEntityServiceImpl<Comment, Integer, CommentRepository>
        implements CommentService {


    public CommentServiceImpl(CommentRepository repository) {
        super(repository);
    }
}