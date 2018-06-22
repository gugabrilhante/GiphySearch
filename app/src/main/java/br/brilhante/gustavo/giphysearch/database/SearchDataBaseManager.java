package br.brilhante.gustavo.giphysearch.database;

import java.util.ArrayList;
import java.util.List;

import br.brilhante.gustavo.giphysearch.model.SearchRealm;
import br.brilhante.gustavo.giphysearch.util.DateUtils;
import io.realm.Realm;

/**
 * Created by Gustavo on 22/06/18.
 */

public class SearchDataBaseManager {

    Realm realm;

    List<SearchRealm> searchList;

    public SearchDataBaseManager() {
        realm = Realm.getDefaultInstance();

        searchList = realm.where(SearchRealm.class).findAll();

    }

    public void updatedList(){
        searchList = realm.where(SearchRealm.class).findAll();
    }

    public ArrayList<SearchRealm> getFilteredList(final String filterStr){
        final ArrayList<SearchRealm> list = new ArrayList<>();

        for (SearchRealm searchRealm:
             searchList) {
            if(searchRealm.getSearchTerm().contains(filterStr)){
                list.add(searchRealm);
            }
        }
        return list;
    }

    private void saveSearch(SearchRealm searchRealm){
        realm.beginTransaction();
        realm.copyToRealm(searchRealm);
        realm.commitTransaction();
    }

    public void saveSearchTerm(String search){
        SearchRealm searchRealm = new SearchRealm();
        searchRealm.setSearchTerm(search);
        searchRealm.setTime(DateUtils.getCurrentDateTime());
        saveSearch(searchRealm);
    }

}
