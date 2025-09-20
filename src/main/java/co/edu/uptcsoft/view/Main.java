package co.edu.uptcsoft.view;

import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.model.Course;
import co.edu.uptcsoft.model.Module;
import co.edu.uptcsoft.model.Lesson;

public class Main {
    public static void main(String[] args) {
        CourseController controller = new CourseController();

        // Cargar datos previos si existen
        controller.loadData();

        // Crear un curso
        Course course1 = new Course("C1", "Programación en Java");
        controller.addCourse(course1);

        // Crear módulos
        Module mod1 = new Module("M1", "Introducción a Java");
        Module mod2 = new Module("M2", "POO en Java");

        controller.addModule("C1", mod1);
        controller.addModule("C1", mod2);

        // Crear lecciones
        Lesson l1 = new Lesson("L1", "Historia de Java", "video");
        Lesson l2 = new Lesson("L2", "Instalación de JDK", "texto");
        Lesson l3 = new Lesson("L3", "Clases y Objetos", "quiz");

        controller.addLesson("C1", "M1", l1);
        controller.addLesson("C1", "M1", l2);
        controller.addLesson("C1", "M2", l3);

        // Imprimir en consola
        System.out.println("\n📚 Catálogo de Cursos:");
        controller.printCourses();

        // Guardar datos
        controller.saveData();

        System.out.println("\n✅ Datos guardados en Courses.json");
    }
}
