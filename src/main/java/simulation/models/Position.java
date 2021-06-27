package simulation.models;

public class Position {
    private final double x;
    private final double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Position other){
        double relativeX = other.x - x;
        double relativeY = other.y - y;
        return Math.sqrt(relativeX * relativeX + relativeY * relativeY);
    }

    public double angleBetween(Position other){
        double relativeX = other.x - x;
        double relativeY = other.y - y;
        return Math.atan2(relativeY, relativeX);
    }
}
