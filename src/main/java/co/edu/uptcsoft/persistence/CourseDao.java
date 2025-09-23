package co.edu.uptcsoft.persistence;

import co.edu.uptcsoft.model.*;
import co.edu.uptcsoft.model.Module;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    private static final String file = "src\\main\\resources\\co\\edu\\uptcsoft\\Courses.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Guardar cursos completos
    public void save(List<TreeNode<Course>> courses) {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(courses, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    // Cargar cursos completos (con módulos y lecciones)
    public List<TreeNode<Course>> load() {
        try (Reader reader = new FileReader(file)) {
            JsonArray coursesArray = JsonParser.parseReader(reader).getAsJsonArray();
            List<TreeNode<Course>> courses = new ArrayList<>();
            for (JsonElement elem : coursesArray) {
                courses.add(parseCourseNode(elem.getAsJsonObject()));
            }
            return courses;
        } catch (IOException e) {
            System.err.println("No se pudo cargar el archivo, creando lista vacía...");
        } catch (JsonParseException e) {
            System.err.println("Error al parsear JSON: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Reconstruir un curso con sus módulos y lecciones
    private TreeNode<Course> parseCourseNode(JsonObject courseJson) {
        JsonObject data = courseJson.getAsJsonObject("data");
        String courseId = data.get("id").getAsString();
        String courseTitle = data.get("title").getAsString();
        Course course = new Course(courseId, courseTitle);
        TreeNode<Course> courseNode = new TreeNode<>(course);

        // Agregar módulos
        JsonArray modulesArray = courseJson.getAsJsonArray("children");
        for (JsonElement moduleElem : modulesArray) {
            courseNode.addChild(parseModuleNode(moduleElem.getAsJsonObject()));
        }

        return courseNode;
    }

    // Reconstruir un módulo con sus lecciones
    private TreeNode<Module> parseModuleNode(JsonObject moduleJson) {
        JsonObject data = moduleJson.getAsJsonObject("data");
        String moduleId = data.get("id").getAsString();
        String moduleTitle = data.get("title").getAsString();
        Module module = new Module(moduleId, moduleTitle);
        TreeNode<Module> moduleNode = new TreeNode<>(module);

        // Agregar lecciones
        JsonArray lessonsArray = moduleJson.getAsJsonArray("children");
        for (JsonElement lessonElem : lessonsArray) {
            JsonObject lessonData = lessonElem.getAsJsonObject().getAsJsonObject("data");
            String lessonId = lessonData.get("id").getAsString();
            String lessonTitle = lessonData.get("title").getAsString();
            String lessonType = lessonData.get("type").getAsString();
            Lesson lesson = new Lesson(lessonId, lessonTitle, lessonType);
            TreeNode<Lesson> lessonNode = new TreeNode<>(lesson);
            moduleNode.addChild(lessonNode);
        }

        return moduleNode;
    }
}
