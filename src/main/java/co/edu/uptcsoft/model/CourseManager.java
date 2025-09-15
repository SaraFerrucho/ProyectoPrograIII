package co.edu.uptcsoft.model;

public class CourseManager {
    private TreeNode<Object> root; // Raíz genérica del árbol

    public CourseManager() {
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
        Object data = current.getData();
        String currentId = null;

        if (data instanceof Course)
            currentId = ((Course) data).getId();
        else if (data instanceof Module)
            currentId = ((Module) data).getId();
        else if (data instanceof Lesson)
            currentId = ((Lesson) data).getId();

        if (currentId != null && currentId.equals(id))
            return current;

        for (TreeNode<?> child : current.getChildren()) {
            TreeNode<?> found = findNode(child, id);
            if (found != null)
                return found;
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
}
