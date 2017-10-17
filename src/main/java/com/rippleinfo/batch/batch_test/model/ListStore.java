package com.rippleinfo.batch.batch_test.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListStore {

    private List<String> store;

    public ListStore(){
        this.store = new ArrayList<>();
        this.store.add("a");
        this.store.add("b");
        this.store.add("c");
        this.store.add("e");
        this.store.add("f");
        this.store.add("g");
        this.store.add("h");
        this.store.add("i");
        this.store.add("j");
    }

    public List<String> getStore(){
        return  this.store;
    }

    public void addElement(String element){
        this.store.add(element);
    }


}
