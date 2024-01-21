package finalproject.finalproject.base.service.Impl;

import finalproject.finalproject.base.entity.BaseEntity;
import finalproject.finalproject.base.reposiotry.BaseEntityRepository;
import finalproject.finalproject.base.service.BaseEntityService;

import java.io.Serializable;

@SuppressWarnings("unused")
public class BaseEntityServiceImpl
        <T extends BaseEntity<ID>, ID extends Serializable, R extends BaseEntityRepository<T, ID>>
        implements BaseEntityService<T, ID> {
    protected final R repository;

    public BaseEntityServiceImpl(R repository) {
        this.repository = repository;
    }

}
