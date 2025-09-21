package co.edu.uptcsoft.persistence;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
public class ProgressRepository {
    
    private static final String file = "progress.json";  // archivo en la raíz
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Tipo de dato: studentId -> (courseId -> set de lecciones completadas)
    private final Type MAP_TYPE = new TypeToken<Map<Integer, Map<String, Set<String>>>>() {}.getType();

    // === Métodos internos ===
    private Map<Integer, Map<String, Set<String>>> loadAll() {
        try (Reader reader = new FileReader(file)) {
            Map<Integer, Map<String, Set<String>>> data = gson.fromJson(reader, MAP_TYPE);
            return (data != null) ? data : new HashMap<>();
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, devuelve un mapa vacío
            return new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo " + file, e);
        }
    }

    private void saveAll(Map<Integer, Map<String, Set<String>>> data) {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error guardando " + file, e);
        }
    }

    // === API pública ===

    /** Devuelve las lecciones completadas de un estudiante en un curso */
    public Set<String> getCompletedLessons(int studentId, String courseId) {
        Map<Integer, Map<String, Set<String>>> data = loadAll();
        return new HashSet<>(
            data.getOrDefault(studentId, Collections.emptyMap())
                .getOrDefault(courseId, Collections.emptySet())
        );
    }

    /** Marca una lección como completada */
    public void markLesson(int studentId, String courseId, String lessonId) {
        Map<Integer, Map<String, Set<String>>> data = loadAll();
        data.computeIfAbsent(studentId, k -> new HashMap<>())
            .computeIfAbsent(courseId, k -> new HashSet<>())
            .add(lessonId);
        saveAll(data);
    }

    /** Desmarca una lección */
    public void unmarkLesson(int studentId, String courseId, String lessonId) {
        Map<Integer, Map<String, Set<String>>> data = loadAll();
        data.computeIfAbsent(studentId, k -> new HashMap<>())
            .computeIfAbsent(courseId, k -> new HashSet<>())
            .remove(lessonId);
        saveAll(data);
    }
}
