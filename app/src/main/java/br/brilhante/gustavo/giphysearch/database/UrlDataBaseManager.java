package br.brilhante.gustavo.giphysearch.database;

import java.util.ArrayList;
import java.util.List;

import br.brilhante.gustavo.giphysearch.model.DataRealm;
import br.brilhante.gustavo.giphysearch.model.data;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * Created by Gustavo on 22/06/18.
 */

public class UrlDataBaseManager {

    Realm realm;

    List<DataRealm> dataRealmList;

    ArrayList<String> urlList;


    public UrlDataBaseManager() {
        this.realm = Realm.getDefaultInstance();

        dataRealmList = realm.where(DataRealm.class).findAll();

        urlList = new ArrayList<>();

        for (DataRealm dataRealm:
             dataRealmList) {
            urlList.add(dataRealm.getUrl());
        }

    }

    Observable<DataRealm> getObservableDataRealmFromData(List<data> dataList){

        return Observable
                .fromIterable(dataList)
                .map(new Function<data, DataRealm>() {
                    @Override
                    public DataRealm apply(data data) throws Exception {
                        DataRealm dataRealm = new DataRealm(data.getImages().getDownsizedMedium().getUrl());
                        return dataRealm;
                    }
                });

    }

    ArrayList<String> getStringList(List<data> dataList){
        ArrayList<String> list = new ArrayList<>();
        for (data item : dataList) {
            list.add(item.getImages().getDownsizedMedium().getUrl());
        }
        return list;
    }

    public List<String> getPartList(int limit, int offset){
        if(offset< urlList.size()) {
            if(limit< urlList.size()) {
                return new ArrayList<String>(urlList.subList(offset, offset + limit));
            }else{
                return new ArrayList<String>(urlList.subList(offset, urlList.size()-1));
            }
        }else{
            return null;
        }
    }

    public List<DataRealm> getDataRealmList() {
        return dataRealmList;
    }

    public void storeData(List<data> dataList){
        final ArrayList<String> stringList = getStringList(dataList);
        Observable<DataRealm> dataListObservable = getObservableDataRealmFromData(dataList);

        dataListObservable.filter(new Predicate<DataRealm>() {
            @Override
            public boolean test(DataRealm dataRealm) throws Exception {
                if (stringList.contains(dataRealm.getUrl())) {
                    return true;
                }else{
                    return false;
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<DataRealm>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(DataRealm dataRealm) {
                realm.beginTransaction();
                realm.copyToRealm(dataRealm);
                realm.commitTransaction();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


}
