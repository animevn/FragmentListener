package com.haanhgs.app.fragmentlistener;

public enum Status {

    Yes(1), No(2), None(3);

    public final int state;

    Status(int state){
        this.state = state;
    }
}
