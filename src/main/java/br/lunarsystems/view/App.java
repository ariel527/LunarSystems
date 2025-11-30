package br.lunarsystems.view;

import br.lunarsystems.model.*;
import br.lunarsystems.repository.*;
import br.lunarsystems.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Paths dos arquivos binários e banco nitrite
        BinaryRepository<Astronauta> binAstr = new BinaryRepository<>("data/astronautas.bin");
        BinaryRepository<Nave> binNaves = new BinaryRepository<>("data/naves.bin");
        BinaryRepository<Missao> binMissoes = new BinaryRepository<>("data/missoes.bin");

        NitriteService nitrite = new NitriteService("lunarsystems.db");


        AstronautaService astronautaService = new AstronautaService(binAstr, nitrite);
        NaveService naveService = new NaveService(binNaves, nitrite);
        MissaoService missaoService = new MissaoService(binMissoes, nitrite);

        loop:
        while (true) {
            System.out.println("\n--- Lunar Systems ---");
            System.out.println("1. Cadastrar Astronauta");
            System.out.println("2. Listar Astronautas");
            System.out.println("3. Remover Astronauta");
            System.out.println("4. Cadastrar Nave");
            System.out.println("5. Listar Naves");
            System.out.println("6. Remover Nave");
            System.out.println("7. Cadastrar Missão");
            System.out.println("8. Listar Missões");
            System.out.println("9. Remover Missão");
            System.out.println("10. Adicionar Astronauta a Missão");
            System.out.println("11. Registrar Resultado da Missão");
            System.out.println("0. Sair");
            System.out.print("> ");
            String opc = sc.nextLine();
            try {
                switch (opc) {
                    case "1": cadastrarAstronauta(astronautaService); break;
                    case "2": listarAstronautas(astronautaService); break;
                    case "3": removerAstronauta(astronautaService); break;
                    case "4": cadastrarNave(naveService); break;
                    case "5": listarNaves(naveService); break;
                    case "6": removerNave(naveService); break;
                    case "7": cadastrarMissao(missaoService, naveService); break;
                    case "8": listarMissoes(missaoService); break;
                    case "9": removerMissao(missaoService); break;
                    case "10": adicionarAstronautaAMissao(missaoService, astronautaService); break;
                    case "11": registrarResultado(missaoService); break;
                    case "0":
                        nitrite.close();
                        break loop;
                    default: System.out.println("Opção inválida");
                }
            } catch (Exception ex) {
                System.err.println("Erro: " + ex.getMessage());
            }
        }

        System.out.println("Encerrando...");
    }

    private static void cadastrarAstronauta(AstronautaService s) {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("Idade: "); int idade = Integer.parseInt(sc.nextLine());
        System.out.print("Especialidade: "); String esp = sc.nextLine();
        System.out.print("Horas de voo: "); int horas = Integer.parseInt(sc.nextLine());
        Astronauta a = new Astronauta(nome, idade, esp, horas);
        s.cadastrar(a);
        System.out.println("Astronauta cadastrado.");
    }

    private static void listarAstronautas(AstronautaService s) {
        List<Astronauta> list = s.listar();
        if (list.isEmpty()) System.out.println("Nenhum astronauta cadastrado.");
        list.forEach(System.out::println);
    }

    private static void removerAstronauta(AstronautaService s) {
        System.out.print("Nome do astronauta a remover: "); String nome = sc.nextLine();
        boolean ok = s.removerPorNome(nome);
        System.out.println(ok ? "Removido." : "Não encontrado.");
    }

    private static void cadastrarNave(NaveService s) {
        System.out.print("Tipo (1-Tripulada, 2-Cargueira): "); String t = sc.nextLine();
        System.out.print("Nome da nave: "); String nome = sc.nextLine();
        System.out.print("Capacidade tripulantes: "); int cap = Integer.parseInt(sc.nextLine());
        if ("1".equals(t)) {
            NaveTripulada n = new NaveTripulada(nome, cap);
            s.cadastrar(n);
        } else {
            System.out.print("Capacidade carga (kg): "); double carga = Double.parseDouble(sc.nextLine());
            NaveCargueira n = new NaveCargueira(nome, cap, carga);
            s.cadastrar(n);
        }
        System.out.println("Nave cadastrada.");
    }

    private static void listarNaves(NaveService s) {
        List<Nave> list = s.listar();
        if (list.isEmpty()) System.out.println("Nenhuma nave cadastrada.");
        list.forEach(System.out::println);
    }

    private static void removerNave(NaveService s) {
        System.out.print("Nome da nave a remover: "); String nome = sc.nextLine();
        boolean ok = s.removerPorNome(nome);
        System.out.println(ok ? "Removida." : "Não encontrada.");
    }

    private static void cadastrarMissao(MissaoService s, NaveService ns) {
        System.out.print("Código da missão: "); String codigo = sc.nextLine();
        System.out.print("Nome da missão: "); String nome = sc.nextLine();
        System.out.print("Data lançamento (YYYY-MM-DD): "); LocalDate lanc = LocalDate.parse(sc.nextLine());
        System.out.print("Data retorno (YYYY-MM-DD): "); LocalDate ret = LocalDate.parse(sc.nextLine());
        System.out.print("Destino: "); String dest = sc.nextLine();
        System.out.print("Objetivo: "); String obj = sc.nextLine();
        System.out.print("Nome da nave a usar: "); String nomeNave = sc.nextLine();
        Optional<Nave> n = ns.buscarPorNome(nomeNave);
        if (n.isEmpty()) { System.out.println("Nave não encontrada."); return; }
        Missao m = new Missao(codigo, nome, lanc, ret, dest, obj, n.get());
        s.cadastrar(m);
        System.out.println("Missão cadastrada.");
    }

    private static void listarMissoes(MissaoService s) {
        List<Missao> list = s.listar();
        if (list.isEmpty()) System.out.println("Nenhuma missão cadastrada.");
        list.forEach(System.out::println);
    }

    private static void removerMissao(MissaoService s) {
        System.out.print("Código da missão a remover: "); String codigo = sc.nextLine();
        boolean ok = s.removerPorCodigo(codigo);
        System.out.println(ok ? "Removida." : "Não encontrada.");
    }

    private static void adicionarAstronautaAMissao(MissaoService ms, AstronautaService as) {
        System.out.print("Código da missão: "); String codigo = sc.nextLine();
        System.out.print("Nome do astronauta (deve estar cadastrado): "); String nome = sc.nextLine();
        Optional<Astronauta> a = as.buscarPorNome(nome);
        if (a.isEmpty()) { System.out.println("Astronauta não encontrado."); return; }
        ms.adicionarAstronauta(codigo, a.get());
        System.out.println("Astronauta adicionado à missão.");
    }

    private static void registrarResultado(MissaoService s) {
        System.out.print("Código da missão: "); String codigo = sc.nextLine();
        System.out.print("Resultado: "); String res = sc.nextLine();
        s.registrarResultado(codigo, res);
        System.out.println("Resultado registrado.");
    }
}
