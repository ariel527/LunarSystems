package br.lunarsystems.model;

public class NaveCargueira extends Nave {
    private double capacidadeCargaKg;

    public NaveCargueira() {}
    public NaveCargueira(String nome, int capacidadeTripulantes, double capacidadeCargaKg) {
        super(nome, capacidadeTripulantes);
        this.capacidadeCargaKg = capacidadeCargaKg;
    }

    public double getCapacidadeCargaKg() { return capacidadeCargaKg; }

    @Override
    public String toString() {
        return super.toString() + String.format(" - carga: %.1f kg", capacidadeCargaKg);
    }
}
