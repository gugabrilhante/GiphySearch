package br.brilhante.gustavo.giphysearch.model;

import io.realm.RealmObject;

/**
 * Created by Gustavo on 22/06/18.
 */

public class SearchRealm extends RealmObject{

    public SearchRealm() {
    }

    String searchTerm;
    String time;

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
