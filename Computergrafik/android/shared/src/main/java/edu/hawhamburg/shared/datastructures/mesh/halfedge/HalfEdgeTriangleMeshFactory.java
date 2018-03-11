package edu.hawhamburg.shared.datastructures.mesh.halfedge;

import edu.hawhamburg.shared.datastructures.mesh.*;

public class HalfEdgeTriangleMeshFactory {

    /**
     * Hide default constructor
     */
    private HalfEdgeTriangleMeshFactory() {

        // hide default constructor
    }

    public static HalfEdgeTriangleMesh create(TriangleMesh triangleMesh) {
        HalfEdgeTriangleMesh halfEdgeTriangleMesh = new HalfEdgeTriangleMesh();

        // copy all vertices
        int verticesCount = triangleMesh.getNumberOfVertices();
        int createdIndex;
        Vertex originalVertex;
        for (int vertexIndex = 0; vertexIndex < verticesCount; vertexIndex++) {
            originalVertex = triangleMesh.getVertex(vertexIndex);
            createdIndex = halfEdgeTriangleMesh.addVertex(originalVertex.getPosition());
            halfEdgeTriangleMesh.getVertex(createdIndex).setColor(originalVertex.getColor());
        }

        // copy all triangles
        int trianglesCount = triangleMesh.getNumberOfTriangles();
        Triangle originalTriangle;
        for (int triangleIndex = 0; triangleIndex < trianglesCount; triangleIndex++) {
            originalTriangle = triangleMesh.getTriangle(triangleIndex);
            halfEdgeTriangleMesh.addTriangle(
                    originalTriangle.getVertexIndex(0),
                    originalTriangle.getVertexIndex(1),
                    originalTriangle.getVertexIndex(2)
            );
        }

        return halfEdgeTriangleMesh;
    }
}
