package data;

import java.io.Serializable;
import java.util.List;

public interface DataRepository {
    int create(Serializable object);
    Serializable read(int id);
    void update(int id, Serializable object);
    void delete(int id);
    List<Serializable> readAll();
}
