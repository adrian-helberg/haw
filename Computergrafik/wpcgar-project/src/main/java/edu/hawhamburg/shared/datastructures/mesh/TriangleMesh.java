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

import java.util.ArrayList;
import java.util.List;

import edu.hawhamburg.shared.math.Vector;

/**
 * Implementation of a indexed vertex list triangle mesh.
 */
public class TriangleMesh implements ITriangleMesh {

    /**
     * Vertices.
     */
    private List<Vertex> vertices = new ArrayList<Vertex>();

    /**
     * Triangles.
     */
    private List<Triangle> triangles = new ArrayList<Triangle>();

    /**
     * Texture coordinates.
     */
    private List<Vector> textureCoordinates = new ArrayList<Vector>();

    /**
     * Texture object, leave null if no texture is used.
     */
    private String textureName = null;

    public TriangleMesh() {
    }

    public TriangleMesh(String textureName) {
        this.textureName = textureName;
    }


    @Override
    public void clear() {
        vertices.clear();
        triangles.clear();
        textureCoordinates.clear();
    }

    @Override
    public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
        triangles.add(new Triangle(vertexIndex1, vertexIndex2, vertexIndex3));
    }

    @Override
    public void addTriangle(AbstractTriangle t) {
        if (t instanceof Triangle) {
            triangles.add((Triangle) t);
        } else {
            throw new IllegalArgumentException("Can only add Triangle objects.");
        }
    }

    @Override
    public int addVertex(Vector position) {
        vertices.add(new Vertex(position));
        return vertices.size() - 1;
    }

    @Override
    public Vertex getVertex(int index) {
        return vertices.get(index);
    }

    @Override
    public int getNumberOfTriangles() {
        return triangles.size();
    }

    @Override
    public int getNumberOfVertices() {
        return vertices.size();
    }

    @Override
    public Triangle getTriangle(int triangleIndex) {
        return triangles.get(triangleIndex);
    }

    @Override
    public Vector getTextureCoordinate(int texCoordIndex) {
        return textureCoordinates.get(texCoordIndex);
    }

    @Override
    public void computeTriangleNormals() {
        for (int triangleIndex = 0; triangleIndex < getNumberOfTriangles(); triangleIndex++) {
            Triangle t = triangles.get(triangleIndex);
            Vector a = vertices.get(t.getVertexIndex(0)).getPosition();
            Vector b = vertices.get(t.getVertexIndex(1)).getPosition();
            Vector c = vertices.get(t.getVertexIndex(2)).getPosition();
            Vector normal = b.subtract(a).cross(c.subtract(a));
            double norm = normal.getNorm();
            if (norm > 1e-5) {
                normal.multiply(1.0 / norm);
            }
            t.setNormal(normal);
        }
    }

    @Override
    public void addTextureCoordinate(Vector t) {
        textureCoordinates.add(t);
    }

    @Override
    public int getNumberOfTextureCoordinates() {
        return textureCoordinates.size();
    }

    @Override
    public void setColor(Vector color) {
        if (color.getDimension() != 4) {
            return;
        }
        for (Triangle triangle : triangles) {
            triangle.setColor(color);
        }
        for (Vertex vertex : vertices) {
            vertex.setColor(color);
        }
    }
}
