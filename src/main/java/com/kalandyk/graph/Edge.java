package com.kalandyk.graph;

public class Edge {

    private int v;
    private int u;
    private int number;
    private boolean isHamiltonian;

    private Edge(int v, int u, int number) {
        this.v = v;
        this.u = u;
        this.number = number;
        this.isHamiltonian = true;
    }

    private Edge(int v, int u) {
        this.v = v;
        this.u = u;
        this.isHamiltonian = false;
    }

    public static Edge createHamiltonianEdge(int v, int u, int number) {
        return new Edge(v, u, number);
    }

    public static Edge createEdge(int v, int u) {
        return new Edge(v, u);
    }


    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isHamiltonian() {
        return isHamiltonian;
    }

    public void setIsHamiltonian(boolean isHamiltonian) {
        this.isHamiltonian = isHamiltonian;
    }
}
