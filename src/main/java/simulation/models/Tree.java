package simulation.models;

import java.util.List;

public class Tree {
    private static final double IGNITION_TEMPERATURE = 561; // Kelvin
    private static final double MAX_TEMPERATURE = 873; // Kelvin
    private static final double HEAT_CAPACITY = 2386; // J/(kg * K) source:http://leias.fa.unam.mx/wp-content/uploads/2018/07/180515_Practica12_LES.pdf
    private static final double MASS = 7500; // kg
    private static final double RADIUS = 2; // m
    private static final double BURN_TIME = 500; // s
    private final Position position;
    private double temperature;
    private TreeState state;
    private double timeOnFire;

    public Tree(Tree tree){
        this.position = tree.position;
        this.temperature = tree.temperature;
        this.state = tree.state;
        this.timeOnFire = tree.timeOnFire;
    }
    public Tree(Position position, double temperature) {
        this.position = position;
        this.temperature = temperature;
        this.state = TreeState.HEALTHY;
        this.timeOnFire = 0;
    }

    public double getTemperature() {
        return temperature;
    }

    public TreeState getState() {
        return state;
    }

    public void setState(TreeState state) {
        this.state = state;
    }

    public double getIgnitionTemperature() {
        return IGNITION_TEMPERATURE;
    }

    public static double getStartingTemperature() {
        return IGNITION_TEMPERATURE;
    }

    public double getRadius() {
        return RADIUS;
    }

    public static double getMaxTemperature() {
        return MAX_TEMPERATURE;
    }

    public double getHeatCapacity() {
        return HEAT_CAPACITY;
    }

    public double getMass() {
        return MASS;
    }

    public static double getBurnTime() {
        return BURN_TIME;
    }

    public double getTimeOnFire() {
        return timeOnFire;
    }

    public Position getPosition() {
        return position;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setTimeOnFire(double timeOnFire) {
        this.timeOnFire = timeOnFire;
    }

    public void evoluteTemperature(double timeStep) {
        if(state != TreeState.ON_FIRE)
            throw new IllegalStateException();

        timeOnFire += timeStep;
        if(timeOnFire < BURN_TIME / 2){ // primera mitad de la curva
            double asin = Math.asin(561.0/873);
            temperature = MAX_TEMPERATURE * Math.sin(((Math.PI/2 - asin)/(BURN_TIME/2)) * (timeOnFire) + asin);
        } else if (timeOnFire < BURN_TIME){ // segunda mitad de la curva
            temperature = 2875 * Math.exp((-Math.log(5)/250) * timeOnFire) + 298;
        } else {
            state = TreeState.DEAD;
        }
    }
}
