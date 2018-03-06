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
package edu.hawhamburg.shared.datastructures.mesh;

import edu.hawhamburg.shared.math.Vector;

/**
 * Generic triangle representation
 *
 * @author Philipp Jenke
 */
public class AbstractTriangle {

    /**
     * Triangle color in RGBA format.
     */
    protected Vector color = new Vector(0.5, 0.5, 0.5, 1);

    /**
     * Facet normal
     */
    private Vector normal = new Vector(0, 1, 0);

    /**
     * Indices of the texture coordinates.
     */
    private int[] texCoordIndices = {-1, -1, -1};

    AbstractTriangle(AbstractTriangle triangle) {
        this.color = new Vector(triangle.color);
        this.normal = new Vector(triangle.normal);
        texCoordIndices[0] = triangle.texCoordIndices[0];
        texCoordIndices[1] = triangle.texCoordIndices[1];
        texCoordIndices[2] = triangle.texCoordIndices[2];
    }

    AbstractTriangle(int tA, int tB, int tC, Vector normal) {
        this.normal = normal;
        texCoordIndices[0] = tA;
        texCoordIndices[1] = tB;
        texCoordIndices[2] = tC;
    }

    public Vector getColor() {
        return color;
    }

    /**
     * Color must be a 4D vector in RGBA format.
     */
    public void setColor(Vector color) {
        this.color = color;
    }

    void setNormal(Vector normal) {
        this.normal.copy(normal);
    }

    public Vector getNormal() {
        return normal;
    }
}
