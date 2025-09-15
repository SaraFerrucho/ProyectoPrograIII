package co.edu.uptcsoft.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import co.edu.uptcsoft.model.TreeNode;

public class CourseDAO {
    private static final String file = "Courses.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Guardar árbol completo
    public void save(TreeNode<Object> root) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(root, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    // Cargar árbol completo
    public TreeNode<Object> load() {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, TreeNode.class);
        } catch (IOException e) {
            System.err.println("No se pudo cargar el archivo, creando uno nuevo...");
            return new TreeNode<>("Catálogo de Cursos");
        }
    }
}
