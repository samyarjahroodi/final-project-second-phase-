package finalproject.finalproject.service;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity, ID extends Serializable> {
    <S extends T> S save(S entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    List<T> findAllById(Iterable<ID> ids);

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    void deleteById(ID id);

    void delete(T entity);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();

    T getReferenceById(ID id);
}
