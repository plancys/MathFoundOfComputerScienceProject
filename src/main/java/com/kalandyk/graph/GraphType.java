package com.kalandyk.graph;

public enum GraphType {
    SOURCE,
    SOLVED;

    public String getImageFileName() {
        return name() + ".png";
    }

}
