package co.edu.uptcsoft.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ProgressRepository {

    // Guarda el archivo JSON al lado del .jar / raíz del proyecto
    private static final String FILE_NAME = "src\\main\\resources\\co\\edu\\uptcsoft\\progress.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Type MAP_TYPE = new TypeToken<Map<Integer, Map<String, Set<String>>>>() {}.getType();

    private synchronized Map<Integer, Map<String, Set<String>>> loadAll() {
        File f = new File(FILE_NAME);

        // Si ya existe el externo, léelo
        if (f.exists()) {
            try (Reader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8)) {
                Map<Integer, Map<String, Set<String>>> data = gson.fromJson(reader, MAP_TYPE);
                return (data != null) ? data : new HashMap<>();
            } catch (IOException e) {
                throw new RuntimeException("Error leyendo " + FILE_NAME, e);
            }
        }

        // Si no existe, intenta una semilla desde resources (opcional)
        try (InputStream in = getClass().getClassLoader()
                .getResourceAsStream("co/edu/uptcsoft/progress.json")) {
            if (in != null) {
                String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                Map<Integer, Map<String, Set<String>>> data = gson.fromJson(json, MAP_TYPE);
                return (data != null) ? data : new HashMap<>();
            }
        } catch (IOException ignore) { }

        return new HashMap<>();
    }

    private synchronized void saveAll(Map<Integer, Map<String, Set<String>>> data) {
        try (Writer w = new OutputStreamWriter(new FileOutputStream(FILE_NAME), StandardCharsets.UTF_8)) {
            gson.toJson(data, w);
        } catch (IOException e) {
            throw new RuntimeException("Error guardando " + FILE_NAME, e);
        }
    }

    public synchronized Set<String> getCompletedLessons(int studentId, String courseId) {
        Map<Integer, Map<String, Set<String>>> data = loadAll();
        return new HashSet<>(
                data.getOrDefault(studentId, Collections.emptyMap())
                    .getOrDefault(courseId, Collections.emptySet()));
    }

    public synchronized void markLesson(int studentId, String courseId, String lessonId) {
        Map<Integer, Map<String, Set<String>>> data = loadAll();
        data.computeIfAbsent(studentId, k -> new HashMap<>())
            .computeIfAbsent(courseId, k -> new HashSet<>())
            .add(lessonId);
        saveAll(data);
    }

    public synchronized void unmarkLesson(int studentId, String courseId, String lessonId) {
        Map<Integer, Map<String, Set<String>>> data = loadAll();
        data.computeIfAbsent(studentId, k -> new HashMap<>())
            .computeIfAbsent(courseId, k -> new HashSet<>())
            .remove(lessonId);
        saveAll(data);
    }
}
