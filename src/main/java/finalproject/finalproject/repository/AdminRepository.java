package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository
        extends JpaRepository<Admin, Integer> {

}
