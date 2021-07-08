package simulation.models;

public class Tree {
    private static final double IGNITION_TEMPERATURE = 561; // Kelvin
    private static final double MAX_TEMPERATURE = 1073; // Kelvin
    private static final double HEAT_CAPACITY = 2000; // J/(kg * K)
    private static final double MASS = 200; // kg (solo la madera de la copa)
    private static final double RADIUS = 2; // m
    private static final double BURN_TIME = 500*60; // s
    private static final double AMB_DIFF = 115; // K. Basicamente AMB_DIFF = T_fuego(BURN_TIME)-T_amb
    private final double roomTemperature;
    private final Position position;
    private double temperature;
    private TreeState state;
    private double timeOnFire;

    public Tree(Tree tree){
        this.position = tree.position;
        this.temperature = tree.temperature;
        this.state = tree.state;
        this.timeOnFire = tree.timeOnFire;
        this.roomTemperature = tree.roomTemperature;
    }
    public Tree(Position position, double temperature, double roomTemperature) {
        this.position = position;
        this.temperature = temperature;
        this.state = TreeState.HEALTHY;
        this.timeOnFire = 0;
        this.roomTemperature = roomTemperature;
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
            double asin = Math.asin(IGNITION_TEMPERATURE/MAX_TEMPERATURE);
            temperature = MAX_TEMPERATURE * Math.sin(((Math.PI/2 - asin)/(BURN_TIME/2)) * (timeOnFire) + asin);
        } else if (timeOnFire < BURN_TIME){ // segunda mitad de la curva
            double b = 2/BURN_TIME * Math.log((MAX_TEMPERATURE - roomTemperature)/AMB_DIFF);
            double a = AMB_DIFF * Math.exp(BURN_TIME*b);
            temperature = a * Math.exp(-b * timeOnFire) + roomTemperature;
        } else {
            state = TreeState.DEAD;
        }
    }
}
