package br.lunarsystems.service;

import br.lunarsystems.model.Astronauta;
import br.lunarsystems.model.Missao;
import br.lunarsystems.model.Nave;
import br.lunarsystems.repository.BinaryRepository;
import br.lunarsystems.repository.NitriteService;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.List;
import java.util.Optional;

public class MissaoService {
    private final BinaryRepository<Missao> binaryRepo;
    private final ObjectRepository<Missao> nitriteRepo;

    public MissaoService(BinaryRepository<Missao> binaryRepo, NitriteService nitriteService) {
        this.binaryRepo = binaryRepo;
        this.nitriteRepo = nitriteService.getRepository(Missao.class);
    }

    public void cadastrar(Missao m) {
        // regra: cada missão tem código único
        Optional<Missao> existe = binaryRepo.findFirst(x -> x.getCodigo().equals(m.getCodigo()));
        if (existe.isPresent()) throw new IllegalArgumentException("Código de missão já existe.");
        binaryRepo.add(m);
        nitriteRepo.insert(m);
    }

    public List<Missao> listar() { return binaryRepo.findAll(); }

    public boolean removerPorCodigo(String codigo) {
        Optional<Missao> opt = binaryRepo.findFirst(m -> m.getCodigo().equals(codigo));
        if (opt.isEmpty()) return false;
        binaryRepo.removeIf(m -> m.getCodigo().equals(codigo));
        nitriteRepo.remove(org.dizitart.no2.objects.filters.ObjectFilters.eq("codigo", codigo));
        return true;
    }

    public Optional<Missao> buscarPorCodigo(String codigo) {
        return binaryRepo.findFirst(m -> m.getCodigo().equals(codigo));
    }

    public void adicionarAstronauta(String codigoMissao, Astronauta a) {
        Missao m = buscarPorCodigo(codigoMissao).orElseThrow(() -> new IllegalArgumentException("Missão não encontrada."));
        // valida: idade do astronauta já garantida ao criar
        m.adicionarAstronauta(a);
        // atualizar persistências: remover+re-add ou manipular nitrite update
        // Simplificação: remove & re-inserir
        binaryRepo.removeIf(x -> x.getCodigo().equals(codigoMissao));
        binaryRepo.add(m);
        nitriteRepo.update(m);
    }

    public void registrarResultado(String codigo, String resultado) {
        Missao m = buscarPorCodigo(codigo).orElseThrow();
        m.setResultado(resultado);
        binaryRepo.removeIf(x -> x.getCodigo().equals(codigo));
        binaryRepo.add(m);
        nitriteRepo.update(m);
    }
}
