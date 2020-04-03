package com.haanhgs.app.fragmentlistener.model;

public enum Status {
    Yes(1), No(2), None(0);

    public final int state;

    Status(int state) {
        this.state = state;
    }
}
