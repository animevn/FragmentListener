package com.haanhgs.app.fragmentlistener.model;

import androidx.lifecycle.MutableLiveData;

public class Repo {

    private MutableLiveData<Model> liveData = new MutableLiveData<>();
    private Model model = new Model();

    public Repo() {
        liveData.setValue(model);
    }

    public MutableLiveData<Model> getLiveData(){
        return liveData;
    }

    public void setStatus(Status status){
        model.setStatus(status);
        liveData.setValue(model);
    }

    public void toggle(){
        model.setOpen(!model.isOpen());
        liveData.setValue(model);
    }

}
