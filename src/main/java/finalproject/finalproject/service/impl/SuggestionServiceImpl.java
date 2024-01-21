package finalproject.finalproject.service.impl;


import base.service.Impl.BaseEntityServiceImpl;
import entity.operation.Suggestion;
import repository.SuggestionRepository;
import service.SuggestionService;

public class SuggestionServiceImpl
        extends BaseEntityServiceImpl<Suggestion, Integer, SuggestionRepository>
        implements SuggestionService {

    public SuggestionServiceImpl(SuggestionRepository repository) {
        super(repository);
    }
}