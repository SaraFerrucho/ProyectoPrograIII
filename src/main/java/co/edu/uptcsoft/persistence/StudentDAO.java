package co.edu.uptcsoft.persistence;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptcsoft.model.Student;
public class StudentDAO {
private static final String FILE =
        java.nio.file.Paths.get(System.getProperty("user.dir"), "students.json").toString();

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type LIST_TYPE = new TypeToken<List<Student>>(){}.getType();

    // ------------ utilidades internas ------------
    private List<Student> loadAll() {
        try (Reader r = new FileReader(FILE)) {
            List<Student> data = gson.fromJson(r, LIST_TYPE);
            return (data != null) ? data : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo " + FILE, e);
        }
    }

    private void saveAll(List<Student> students) {
        try (Writer w = new FileWriter(FILE)) {
            gson.toJson(students, w);
        } catch (IOException e) {
            throw new RuntimeException("Error guardando " + FILE, e);
        }
    }

    // ------------ API CRUD ------------
    public List<Student> findAll() {
        return new ArrayList<>(loadAll()); // copia defensiva
    }

    public Optional<Student> findById(int id) {
        return loadAll().stream().filter(s -> s.getId() == id).findFirst();
    }

    /** Inserta si no existe; si existe, actualiza (por id). */
    public void upsert(Student s) {
        List<Student> all = loadAll();
        int idx = -1;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == s.getId()) { idx = i; break; }
        }
        if (idx >= 0) all.set(idx, s);
        else all.add(s);
        saveAll(all);
    }

    /** Elimina por id. Devuelve true si eliminó. */
    public boolean delete(int id) {
        List<Student> all = loadAll();
        boolean removed = all.removeIf(s -> s.getId() == id);
        if (removed) saveAll(all);
        return removed;
    }
    
}
