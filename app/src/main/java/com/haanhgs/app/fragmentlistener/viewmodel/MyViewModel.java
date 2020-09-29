package com.haanhgs.app.fragmentlistener.viewmodel;

import com.haanhgs.app.fragmentlistener.model.Model;
import com.haanhgs.app.fragmentlistener.model.Repo;
import com.haanhgs.app.fragmentlistener.model.Status;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

     private Repo repo = new Repo();

     public LiveData<Model> getLiveData(){
         return repo.getLiveData();
     }

     public void setStatus(Status status){
         repo.setStatus(status);
     }

     public void toggle(){
         repo.toggle();
     }

}
