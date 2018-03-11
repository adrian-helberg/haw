/**
 * Diese Datei gehört zum Android/Java Framework zur Veranstaltung "Computergrafik für
 * Augmented Reality" von Prof. Dr. Philipp Jenke an der Hochschule für Angewandte
 * Wissenschaften (HAW) Hamburg. Weder Teile der Software noch das Framework als Ganzes dürfen
 * ohne die Einwilligung von Philipp Jenke außerhalb von Forschungs- und Lehrprojekten an der HAW
 * Hamburg verwendet werden.
 * <p>
 * This file is part of the Android/Java framework for the course "Computer graphics for augmented
 * reality" by Prof. Dr. Philipp Jenke at the University of Applied (UAS) Sciences Hamburg. Neither
 * parts of the framework nor the complete framework may be used outside of research or student
 * projects at the UAS Hamburg.
 */
package edu.hawhamburg.shared.math;

import edu.hawhamburg.shared.datastructures.mesh.Vertex;
import edu.hawhamburg.shared.datastructures.skeleton.Bone;

/**
 * Contains various helper classes.
 *
 * @author Philipp Jenke
 */
public final class MathHelpers {

    /**
     * Numerical precision.
     */
    public static final double EPSILON = 0.0000001;

    public static final int DIMENSION_2 = 2;

    public static final int DIMENSION_3 = 3;

    public static final int DIMENSION_4 = 4;

    public static final int INDEX_0 = 0;

    public static final int INDEX_1 = 1;

    public static final int INDEX_2 = 2;

    public static final int INDEX_3 = 3;

    private static final double ONE_HUNDRET_EIGHTY = 180.0;

    /**
     * Disallow instanciation.
     */
    private MathHelpers() {
    }

    /**
     * Convert a radiens angle to a degree angle.
     */
    public static double radiens2degree(double radiens) {
        return radiens / Math.PI * ONE_HUNDRET_EIGHTY;
    }

    /**
     * Convert a degree angle to a radiens angle.
     */
    public static double degree2radiens(final double degree) {
        return degree / ONE_HUNDRET_EIGHTY * Math.PI;
    }

    /**
     * Compute a over b.
     *
     * @param a First parameter
     * @param b Second parameter
     * @return a over b.
     */
    public static double over(int a, int b) {
        return factorial(a) / (factorial(b) * factorial(a - b));
    }

    /**
     * Compute the factorial of a number.
     *
     * @param n Number used.
     * @return Factorial of n.
     */
    public static int factorial(int n) {
        int factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }

    /**
     * Return true if the two values are (numerically) equal.
     */
    public static boolean equals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    public static void createOrthonormal(Vector n, Vector u, Vector v) {
        u.copy(new Vector(1, 0, 0));
        if (Math.abs(u.multiply(n)) > 0.9) {
            u.copy(new Vector(0, 1, 0));
        }
        v.copy(n.cross(u));
        u.copy(v.cross(n));
        u.normalize();
        v.normalize();
    }

    /**
     * Calculates the distance between the given vertex and bone.
     *
     * @param vertex The vertex.
     * @param bone   The bone.
     * @return The calculated distance.
     */
    public static double getDistanceVertexBone(Vertex vertex, Bone bone) {

        // get line segment from bone (it's important to not normalize the direction)
        Vector lineBase = bone.getStart();
        return getDistancePointSegment(vertex.getPosition(), lineBase, bone.getEnd().subtract(lineBase));
    }

    public static double getSqrDistancePointSegment(Vector point, Vector lineBase, Vector lineDirection) {

        // calculate nearest point on line segment (lambda has to be within [0,1] to stay on the segment)
        double lambda = Math.max(0, Math.min(1, point.subtract(lineBase).multiply(lineDirection) / lineDirection.getSqrNorm()));
        Vector nearest = lineBase.add(lineDirection.multiply(lambda));

        // calculate distance
        return nearest.subtract(point).getSqrNorm();
    }

    public static double getDistancePointSegment(Vector point, Vector lineBase, Vector lineDirection) {
        return Math.sqrt(getSqrDistancePointSegment(point, lineBase, lineDirection));
    }

    public static Matrix getBoneTransformation(Bone bone) {
        return bone.getTransformationAtStart()
                .multiply(bone.getRestStateTransformationAtStart().getInverse());
    }

    public static Vector getTransformedPositionFromBone(Vector vector, Bone bone) {
        return getBoneTransformation(bone).multiply(Vector.makeHomogenious(vector)).xyz();
    }
}
