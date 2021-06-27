package simulation.models;

public class Cell {
    private final Tree tree; // null si no hay un arbol ahi

    public Cell(Tree tree) {
        this.tree = tree;
    }

    public Tree getTree() {
        return tree;
    }
}
