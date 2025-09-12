package co.edu.uptcsoft.persistence;

import co.edu.uptcsoft.model.Course;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private static final String archiveCourse = "resources-data/courses.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Course> loadCourses() {
        try (FileReader reader = new FileReader(archiveCourse)) {
            Type listType = new TypeToken<List<Course>>() {}.getType();
            List<Course> courses = gson.fromJson(reader, listType);
            return (courses != null) ? courses : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    public void saveCourses(List<Course> courses) {
        try (FileWriter writer = new FileWriter(archiveCourse)) {
            gson.toJson(courses, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar cursos: " + e.getMessage());
        }
    }
}
