package data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BinaryRepository implements DataRepository {

    private Path filePath;
    private List<Serializable> cache = new ArrayList<>();


    public BinaryRepository(Path filePath) {
        this.filePath = filePath;
        initialize();
    }

    @Override
    public int create(Serializable object) {
        cache.add(object);
        saveCacheToFile();
        return cache.size() - 1;
    }

    @Override
    public Serializable read(int index) {
        return cache.get(index);
    }

    @Override
    public void update(int index, Serializable object) {
        cache.set(index, object);
        saveCacheToFile();
    }

    @Override
    public void delete(int index) {
        cache.remove(index);
        saveCacheToFile();
    }

    @Override
    public List<Serializable> readAll() {
        return cache;
    }

    private void initialize() {
        createFileIfDoesNotExist();
        loadDataFromFileToCache();
    }

    private void createFileIfDoesNotExist() {
        if(!Files.exists(filePath)) {
            saveCacheToFile();
        }
    }

    private void loadDataFromFileToCache() {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath.toAbsolutePath().toFile());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.cache = (List<Serializable>) objectInputStream.readObject();
            objectInputStream.close();

        } catch(IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private void saveCacheToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath.toAbsolutePath().toFile());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(cache);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
