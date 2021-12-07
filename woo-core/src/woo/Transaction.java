package woo;

import java.io.Serializable;

public abstract class Transaction implements Serializable{

    private int _key;

    public Transaction (int key) {
        _key = key;
    }

    public int getKey(){
        return _key;
    }

    public abstract String accept(Visitor v);
}