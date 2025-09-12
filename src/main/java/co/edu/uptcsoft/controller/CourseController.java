package co.edu.uptcsoft.controller;

import java.util.List;

import co.edu.uptcsoft.model.Course;

public class CourseController { //clase para crear actualizar y eliminar nodos del arbol 
    private List<Course> courses;

    public CourseController(List<Course> courses) {
        this.courses = courses;
    }

}
