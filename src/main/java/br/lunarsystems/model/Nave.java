package br.lunarsystems.model;

import java.io.Serializable;
import org.dizitart.no2.objects.Id;

public abstract class Nave implements Serializable {
    @Id
    protected String nome;
    protected int capacidadeTripulantes;

    public Nave() {}

    public Nave(String nome, int capacidadeTripulantes) {
        this.nome = nome;
        this.capacidadeTripulantes = capacidadeTripulantes;
    }

    public String getNome() { return nome; }
    public int getCapacidadeTripulantes() { return capacidadeTripulantes; }

    @Override
    public String toString() {
        return String.format("%s (capacidade: %d)", nome, capacidadeTripulantes);
    }
}
