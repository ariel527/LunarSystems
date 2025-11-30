package br.lunarsystems.repository;

import java.io.*;
import java.util.*;

public class BinaryRepository<T extends Serializable> {
    private final File file;
    private List<T> items = new ArrayList<>();

    public BinaryRepository(String path) {
        this.file = new File(path);
        load();
    }

    @SuppressWarnings("unchecked")
    private void load() {
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List) items = (List<T>) obj;
        } catch (Exception e) {
            System.err.println("Falha ao carregar arquivo binário: " + e.getMessage());
            items = new ArrayList<>();
        }
    }

    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(items);
        } catch (IOException e) {
            System.err.println("Falha ao salvar arquivo binário: " + e.getMessage());
        }
    }

    public List<T> findAll() { return new ArrayList<>(items); }

    public void add(T item) {
        items.add(item);
        save();
    }

    public void removeIf(java.util.function.Predicate<T> predicate) {
        items.removeIf(predicate);
        save();
    }

    public Optional<T> findFirst(java.util.function.Predicate<T> predicate) {
        return items.stream().filter(predicate).findFirst();
    }
}
