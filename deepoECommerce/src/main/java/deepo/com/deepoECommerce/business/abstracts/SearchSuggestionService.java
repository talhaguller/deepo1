package deepo.com.deepoECommerce.business.abstracts;

import deepo.com.deepoECommerce.dataAccess.concretes.SearchSuggestionKeywordInfo;

import java.util.List;

public interface SearchSuggestionService {

    void loadSearchSuggestionToMap();

    List<SearchSuggestionKeywordInfo> searchKeywordFromMap(String q);

    List<SearchSuggestionKeywordInfo> getDefaultSearchSuggestions();
}
