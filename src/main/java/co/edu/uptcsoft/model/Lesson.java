package co.edu.uptcsoft.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Lesson {
    private String id;
    private String title;
    private String type; // video, text, quiz...

    // propiedad para checkbox en tabla
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    public Lesson(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Lesson [id=" + id + ", title=" + title + ", type=" + type + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // -------------------------
    // PROPIEDAD DE SELECCION
    // -------------------------
    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }
}
