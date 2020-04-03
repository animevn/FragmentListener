package com.haanhgs.app.fragmentlistener.model;

import androidx.lifecycle.MutableLiveData;

public class Repo {

    private Model model = new Model();
    private MutableLiveData<Model> liveData = new MutableLiveData<>();

    public MutableLiveData<Model> getLiveData() {
        return liveData;
    }

    public void setLiveData() {
        liveData.setValue(model);
    }

    public void setStatus(Status status){
        model.setStatus(status);
        liveData.setValue(model);
    }

    public void setOpen(boolean open){
        model.setOpen(open);
        liveData.setValue(model);
    }

}
