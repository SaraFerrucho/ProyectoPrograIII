package co.edu.uptcsoft.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreeNode<T> {
    private T data;
    private List<TreeNode<?>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public List<TreeNode<?>> getChildren() {
        return children;
    }

    public void addChild(TreeNode<?> child) {
        children.add(child);
    }

    public void removeChild(TreeNode<?> child) {
        children.remove(child);
    }

    public Optional<TreeNode<?>> findChildById(String id) {
        for (TreeNode<?> child : children) { // recorre los hijos directos
            Object childData = child.getData(); 
            if (childData instanceof Identifiable) { ///El dato del hijo es Identifiable?
                if (((Identifiable) childData).getId().equals(id)) {
                    return Optional.of(child); ///retorna el hijo
                }
            }
        }
        return Optional.empty();
    }
}
