package edu.hawhamburg.shared.datastructures.spline;

import edu.hawhamburg.shared.math.Vector;

public class ProjectileMotionSpine {
    // Gravity
    private double G = 9.981;
    // Initial velocity
    private double v;
    // Initial position
    private Vector pos;

    public ProjectileMotionSpine(double velocity, Vector position) {
        v = velocity;
        pos = position;
    }

    public Vector getPosition(double t) {
        return new Vector(
                v * Math.cos(45) * t,
                pos.y() * Math.sin(45) * t - 0.5 * G * t * t,
                pos.z()
        );
    }
}
