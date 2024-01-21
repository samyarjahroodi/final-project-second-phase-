package finalproject.finalproject.service.impl;


import finalproject.finalproject.repository.SuggestionRepository;
import finalproject.finalproject.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl
        implements SuggestionService {
    private final SuggestionRepository suggestionRepository;

}