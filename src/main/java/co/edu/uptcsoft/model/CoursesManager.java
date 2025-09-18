package co.edu.uptcsoft.model;

public class CoursesManager {
    private TreeNode<Object> root; // Raíz genérica del árbol

    public CoursesManager() {
        root = new TreeNode<>("Catálogo de Cursos");
    }

    public TreeNode<Object> getRoot() {
        return root;
    }

      public void setRoot(TreeNode<Object> root) {
        this.root = root;
    }

    // Agregar un nuevo curso al catálogo
    public void addCourse(Course course) {
        TreeNode<Object> courseNode = new TreeNode<>(course);
        root.addChild(courseNode);
    }

    // Agregar un módulo a un curso específico
    public void addModule(String courseId, Module module) {
        TreeNode<?> courseNode = findNode(root, courseId);
        if (courseNode != null && courseNode.getData() instanceof Course) {
            ((TreeNode<Object>) courseNode).addChild(new TreeNode<>(module));
        } else {
            System.out.println("Curso no encontrado con ID: " + courseId);
        }
    }

    // Agregar una lección a un módulo específico
    public void addLesson(String moduleId, Lesson lesson) {
        TreeNode<?> moduleNode = findNode(root, moduleId);
        if (moduleNode != null && moduleNode.getData() instanceof Module) { 
            ((TreeNode<Object>) moduleNode).addChild(new TreeNode<>(lesson));
        } else {
            System.out.println("Módulo no encontrado con ID: " + moduleId);
        }
    }

    // Buscar un nodo por ID (recursivo)
    public TreeNode<?> findNode(TreeNode<?> current, String id) {
    if (current == null) return null;
    String currentId = extractId(current.getData());
    if (currentId != null && currentId.equals(id)) return current;

    for (TreeNode<?> child : current.getChildren()) {
        TreeNode<?> found = findNode(child, id);
        if (found != null) return found;
    }
    return null;
    }

    public void printTree() {
        printTreeRec(root, 0);
    }

    private void printTreeRec(TreeNode<?> node, int level) {
        for (int i = 0; i < level; i++)
            System.out.print("  ");
        System.out.println(node.getData());
        for (TreeNode<?> child : node.getChildren()) {
            printTreeRec(child, level + 1);
        }
    }
    // === Helpers para progreso ===
    // src/main/java/co/edu/uptcsoft/model/CoursesManager.java
// 👇 extrae el id tanto de objetos reales como de Map (Gson)
    private String extractId(Object data) {
    if (data == null) return null;
    if (data instanceof Course) return ((Course) data).getId();
    if (data instanceof Module) return ((Module) data).getId();
    if (data instanceof Lesson) return ((Lesson) data).getId();
    if (data instanceof java.util.Map) {
        Object v = ((java.util.Map<?,?>) data).get("id");
        return (v != null) ? String.valueOf(v) : null;
    }
    return null;
    }

    // 👇 trata como lección a hojas (y a Lesson real)
    private boolean isLessonNode(TreeNode<?> node) {
    if (node.getData() instanceof Lesson) return true;
    if (node.getData() instanceof java.util.Map && node.getChildren().isEmpty()) return true;
    return false;
    }
// Devuelve TODOS los IDs de lecciones bajo un nodo (curso o módulo)
    public java.util.List<String> lessonIdsUnder(String nodeId) {
    java.util.List<String> out = new java.util.ArrayList<>();
    TreeNode<?> n = findNode(root, nodeId);
    if (n != null) collectLessonIds(n, out);
    return out;
    }

    private void collectLessonIds(TreeNode<?> n, java.util.List<String> out) {
    if (isLessonNode(n)) {
        String id = extractId(n.getData());
        if (id != null) out.add(id);
    }
    for (TreeNode<?> ch : n.getChildren()) collectLessonIds(ch, out);
    }

    // === NUEVO: IDs de módulos directos de un curso (evitamos new Module()) ===
    public java.util.List<String> moduleIdsOfCourse(String courseId) {
    java.util.List<String> ids = new java.util.ArrayList<>();
    TreeNode<?> c = findNode(root, courseId);
    if (c != null) {
        for (TreeNode<?> ch : c.getChildren()) {
            String id = extractId(ch.getData());
            if (id != null) ids.add(id);
        }
    }
    return ids;
}

    
}
