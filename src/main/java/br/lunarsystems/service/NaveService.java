package br.lunarsystems.service;

import br.lunarsystems.model.Nave;
import br.lunarsystems.repository.BinaryRepository;
import br.lunarsystems.repository.NitriteService;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.List;
import java.util.Optional;

public class NaveService {
    private final BinaryRepository<Nave> binaryRepo;
    private final ObjectRepository<Nave> nitriteRepo;

    public NaveService(BinaryRepository<Nave> binaryRepo, NitriteService nitriteService) {
        this.binaryRepo = binaryRepo;
        this.nitriteRepo = nitriteService.getRepository(Nave.class);
    }

    public void cadastrar(Nave n) {
        binaryRepo.add(n);
        nitriteRepo.insert(n);
    }

    public List<Nave> listar() { return binaryRepo.findAll(); }

    public boolean removerPorNome(String nome) {
        Optional<Nave> opt = binaryRepo.findFirst(n -> n.getNome().equals(nome));
        if (opt.isEmpty()) return false;
        binaryRepo.removeIf(n -> n.getNome().equals(nome));
        nitriteRepo.remove(org.dizitart.no2.objects.filters.ObjectFilters.eq("nome", nome));
        return true;
    }

    public Optional<Nave> buscarPorNome(String nome) {
        return binaryRepo.findFirst(n -> n.getNome().equals(nome));
    }
}
