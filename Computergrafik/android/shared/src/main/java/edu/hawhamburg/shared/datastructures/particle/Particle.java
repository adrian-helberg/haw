package edu.hawhamburg.shared.datastructures.particle;

import edu.hawhamburg.shared.math.Vector;

public class Particle {

    private final double G = 9.81;

    private Vector velocity;

    private Vector position;

    private double mass;

    public Particle(Vector position, Vector velocity, double mass) {
        this.velocity = velocity;
        this.position = position;
        this.mass = mass;
    }

    private void updatePosition(double deltaT) {
        position = position.add(velocity.multiply(deltaT));
    }

    private void updateVelocity(double deltaT) {
        velocity = velocity.add(new Vector(0, -1 * (G / mass), 0).multiply(deltaT));
    }

    public Vector update(double deltaT) {
        updatePosition(deltaT);
        updateVelocity(deltaT);
        return position;
    }
}