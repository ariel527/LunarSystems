package br.lunarsystems.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.dizitart.no2.objects.Id;

public class Missao implements Serializable {
    @Id
    private String codigo;
    private String nome;
    private LocalDate lancamento;
    private LocalDate retorno;
    private String destino;
    private String objetivo;
    private String resultado;
    private Nave nave;
    private List<Astronauta> astronautas = new ArrayList<>();

    public Missao() {}

    public Missao(String codigo, String nome, LocalDate lancamento, LocalDate retorno,
                  String destino, String objetivo, Nave nave) {
        if (lancamento.isAfter(retorno)) throw new IllegalArgumentException("Lançamento deve ser antes do retorno.");
        this.codigo = codigo;
        this.nome = nome;
        this.lancamento = lancamento;
        this.retorno = retorno;
        this.destino = destino;
        this.objetivo = objetivo;
        this.nave = nave;
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public LocalDate getLancamento() { return lancamento; }
    public LocalDate getRetorno() { return retorno; }
    public String getObjetivo() { return objetivo; }
    public Nave getNave() { return nave; }
    public List<Astronauta> getAstronautas() { return astronautas; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public long getDuracaoDias() {
        return ChronoUnit.DAYS.between(lancamento, retorno);
    }

    public void adicionarAstronauta(Astronauta a) {
        if (astronautas.size() >= nave.getCapacidadeTripulantes()) {
            throw new IllegalStateException("A nave não suporta mais tripulantes.");
        }
        astronautas.add(a);
    }

    public void removerAstronauta(String nome) {
        astronautas.removeIf(a -> a.getNome().equals(nome));
    }

    @Override
    public String toString() {
        return String.format("Missão %s (%s) - Nave: %s - Astronautas: %d - Duração: %d dias - Resultado: %s",
                nome, codigo, nave.getNome(), astronautas.size(), getDuracaoDias(),
                resultado == null ? "Pendente" : resultado);
    }
}
