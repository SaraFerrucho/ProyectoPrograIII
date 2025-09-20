package co.edu.uptcsoft.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import co.edu.uptcsoft.model.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private static final String file = "Courses.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Guardar lista completa de cursos
    public void save(List<Course> courses) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(courses, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    public List<Course> load() {
        try (FileReader reader = new FileReader(file)) {
            Course[] coursesArray = gson.fromJson(reader, Course[].class);
            if (coursesArray != null) {
                List<Course> courses = new ArrayList<>();
                for (Course c : coursesArray) {
                    courses.add(c);
                }
                return courses;
            }
        } catch (IOException e) {
            System.err.println("No se pudo cargar el archivo, creando lista vacía...");
        }
        return new ArrayList<>(); // Si no existe el archivo o está vacío
    }
}
