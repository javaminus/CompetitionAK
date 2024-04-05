package com.clone;

public class Animal extends DeepClone {
    private static final long serialVersionUID = -293932665050190715L;

    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}