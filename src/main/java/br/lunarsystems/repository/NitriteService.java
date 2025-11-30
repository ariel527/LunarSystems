package br.lunarsystems.repository;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.filters.Filters;

public class NitriteService {

    private final Nitrite db;

    public NitriteService(String dbFilePath) {
        db = Nitrite.builder()
                .compressed()
                .filePath(dbFilePath)
                .openOrCreate();
    }

    public <T> ObjectRepository<T> getRepository(Class<T> type) {
        return db.getRepository(type);
    }

    public void close() {
        db.close();
    }
}
