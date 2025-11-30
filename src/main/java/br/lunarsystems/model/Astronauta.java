package br.lunarsystems.model;

import java.io.Serializable;
import org.dizitart.no2.objects.Id;

public class Astronauta implements Serializable {
    @Id
    private String nome;
    private int idade;
    private String especialidade;
    private int horasVoo;

    public Astronauta() {}

    public Astronauta(String nome, int idade, String especialidade, int horasVoo) {
        if (idade < 21) throw new IllegalArgumentException("Astronauta deve ter pelo menos 21 anos.");
        this.nome = nome;
        this.idade = idade;
        this.especialidade = especialidade;
        this.horasVoo = horasVoo;
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getEspecialidade() { return especialidade; }
    public int getHorasVoo() { return horasVoo; }

    @Override
    public String toString() {
        return String.format("%s (Idade: %d, Esp: %s, HorasVoo: %d)", nome, idade, especialidade, horasVoo);
    }
}
