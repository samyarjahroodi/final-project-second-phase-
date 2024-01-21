package finalproject.finalproject.repository;


import finalproject.finalproject.model.operation.Comment;
import finalproject.finalproject.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Customer getId(String username, String password);
}
