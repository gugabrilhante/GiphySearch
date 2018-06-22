package br.brilhante.gustavo.giphysearch.model;

import io.realm.RealmObject;

/**
 * Created by Gustavo on 22/06/18.
 */

public class DataRealm extends RealmObject{
    String url;

    public DataRealm() {
    }

    public DataRealm(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
