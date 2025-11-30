package br.lunarsystems.service;

import br.lunarsystems.model.Astronauta;
import br.lunarsystems.repository.BinaryRepository;
import br.lunarsystems.repository.NitriteService;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.List;
import java.util.Optional;

public class AstronautaService {
    private final BinaryRepository<Astronauta> binaryRepo;
    private final ObjectRepository<Astronauta> nitriteRepo;

    public AstronautaService(BinaryRepository<Astronauta> binaryRepo, NitriteService nitriteService) {
        this.binaryRepo = binaryRepo;
        this.nitriteRepo = nitriteService.getRepository(Astronauta.class);
    }

    public void cadastrar(Astronauta a) {

        binaryRepo.add(a);
        nitriteRepo.insert(a);
    }

    public List<Astronauta> listar() {
        return binaryRepo.findAll();
    }

    public boolean removerPorNome(String nome) {
        Optional<Astronauta> opt = binaryRepo.findFirst(a -> a.getNome().equals(nome));
        if (opt.isEmpty()) return false;
        binaryRepo.removeIf(a -> a.getNome().equals(nome));
        nitriteRepo.remove(org.dizitart.no2.objects.filters.ObjectFilters.eq("nome", nome));
        return true;
    }

    public Optional<Astronauta> buscarPorNome(String nome) {
        return binaryRepo.findFirst(a -> a.getNome().equals(nome));
    }
}
