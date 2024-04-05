package com.clone;

public class Zoo extends DeepClone {
    private static final long serialVersionUID = -1812884732710635495L;

    private int number;
    private String name;
    private Animal[] animals;

    public Zoo() {

    }

    public Zoo(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public Zoo(int number, String name, Animal[] animals) {
        this.number = number;
        this.name = name;
        this.animals = animals;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Animal[] getAnimals() {
        return animals;
    }

    public void setAnimals(Animal[] animals) {
        this.animals = animals;
    }

    @Override
    public String toString() {
        String description = number + " " + name;
        if (this.animals != null) {
            for (int i = 0; i < this.animals.length; i++) {
                description += " " + this.animals[i].getName();
            }
        }

        return description;
    }
}