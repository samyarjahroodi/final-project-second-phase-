package finalproject.finalproject.base.reposiotry.Impl;

import finalproject.finalproject.base.entity.BaseEntity;
import finalproject.finalproject.base.reposiotry.BaseEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public abstract class BaseEntityRepositoryImpl
        <T extends BaseEntity<ID>, ID extends Serializable>
        implements BaseEntityRepository<T, ID> {
    protected final EntityManager entityManager;

    protected abstract Class<T> getEntityClass();
}
