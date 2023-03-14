package com.example.firstapp.inClass06;

public class Source {
    String id;
    String name;

    public Source() {}
    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String toString() {
        return "Sources{" +
                "id=" + id +
                "name=" + name +
                "}";
    }
}
