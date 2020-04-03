package com.haanhgs.app.fragmentlistener.viewmodel;

import android.app.Application;
import com.haanhgs.app.fragmentlistener.model.Model;
import com.haanhgs.app.fragmentlistener.model.Repo;
import com.haanhgs.app.fragmentlistener.model.Status;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MyViewModel extends AndroidViewModel {

    private Repo repo = new Repo();

    public MyViewModel(@NonNull Application application) {
        super(application);
        repo.setLiveData();
    }

    public LiveData<Model> getData(){
        return repo.getLiveData();
    }

    public void setStatus(Status status){
        repo.setStatus(status);
    }

    public void toggleFragment(){
        repo.toggleFragment();
    }

}
