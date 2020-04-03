package com.haanhgs.app.fragmentlistener.model;

public class Model {

    private boolean open = false;
    private Status status = Status.None;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
