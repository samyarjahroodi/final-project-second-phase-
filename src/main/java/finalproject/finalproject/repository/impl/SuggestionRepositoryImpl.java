package finalproject.finalproject.repository.impl;

import base.reposiotry.Impl.BaseEntityRepositoryImpl;
import entity.operation.Suggestion;
import repository.SuggestionRepository;

import javax.persistence.EntityManager;

public class SuggestionRepositoryImpl
        extends BaseEntityRepositoryImpl<Suggestion, Integer>
        implements SuggestionRepository {


    public SuggestionRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Suggestion> getEntityClass() {
        return Suggestion.class;
    }
}
