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
 * Shared interface for all triangle mesh implementations.
 *
 * @author Philipp Jenke
 */
public interface ITriangleMesh {
    /**
     * Add a new vertex (given by position) to the vertex list. The new vertex is
     * appended to the end of the list.
     */
    int addVertex(Vector position);

    /**
     * Index in vertex list.
     */
    Vertex getVertex(int index);

    int getNumberOfVertices();

    /**
     * Add a new triangle to the mesh with the vertex indices a, b, c. The index
     * of the first vertex is 0.
     */
    void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3);

    /**
     * Add a new triangle to the mesh with the vertex indices a, b, c. The index
     * of the first vertex is 0.
     */
    void addTriangle(AbstractTriangle t);

    int getNumberOfTriangles();

    AbstractTriangle getTriangle(int triangleIndex);

    /**
     * Clear mesh - remove all triangles and vertices.
     */
    void clear();

    /**
     * Compute the triangles normals.
     */
    void computeTriangleNormals();


    Vector getTextureCoordinate(int index);

    /**
     * Add texture coordinate to mesh.
     */
    void addTextureCoordinate(Vector t);

    /**
     * Return the number of texture coordinates in the mesh.
     */
    int getNumberOfTextureCoordinates();

    /**
     * Set color to all triangles and all vertices of the mesh.
     */
    void setColor(Vector color);
}
