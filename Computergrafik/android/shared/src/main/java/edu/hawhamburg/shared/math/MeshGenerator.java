package edu.hawhamburg.shared.math;

import android.util.Log;
import android.util.SparseIntArray;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.hawhamburg.shared.datastructures.mesh.Triangle;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.Vertex;

public class MeshGenerator {

    private final Vertex[][] pointCloud;

    private final int resolutionX;

    private final int resolutionY;

    private final int vertexGenerationStep;

    private final int[][] vertexIndices;

    private final TriangleMesh triangleMesh = new TriangleMesh();

    public MeshGenerator(Vertex[][] pointCloud) {
        this(pointCloud, 1);
    }

    public MeshGenerator(Vertex[][] pointCloud, int vertexGenerationStep) {
        this.pointCloud = pointCloud;
        this.vertexGenerationStep = vertexGenerationStep;
        resolutionX = pointCloud.length;
        resolutionY = resolutionX > 0 ? pointCloud[0].length : 0;

        vertexIndices = new int[resolutionX][resolutionY];
        for (int x = 0; x < resolutionX; x++) {
            for(int y = 0; y < resolutionY; y++) {
                vertexIndices[x][y] = -1;
            }
        }

        // generate mesh LAST!
        generateMesh();
    }

    public TriangleMesh getTriangleMesh() {
        return triangleMesh;
    }

    private void generateMesh() {
        for (int x = 0; x < resolutionX; x += vertexGenerationStep) {
            for (int y = 0; y < resolutionY; y += vertexGenerationStep) {
                if (x + vertexGenerationStep < resolutionX && y + vertexGenerationStep < resolutionX) {
                    Triangle t = new Triangle(
                            getOrCreateMeshVertex(x, y + vertexGenerationStep),
                            getOrCreateMeshVertex(x + vertexGenerationStep, y),
                            getOrCreateMeshVertex(x, y)
                    );
                    t.setColor(pointCloud[x][y].getColor());
                    triangleMesh.addTriangle(t);
                    t = new Triangle(
                        getOrCreateMeshVertex(x, y + vertexGenerationStep),
                        getOrCreateMeshVertex(x + vertexGenerationStep, y + vertexGenerationStep),
                        getOrCreateMeshVertex(x + vertexGenerationStep, y)
                    );
                    t.setColor(pointCloud[x + vertexGenerationStep][y + vertexGenerationStep].getColor());
                    triangleMesh.addTriangle(t);
                }
            }
        }
    }

    private int getOrCreateMeshVertex(int x, int y) {
        if (vertexIndices[x][y] == -1) {
            vertexIndices[x][y] = triangleMesh.addVertex(pointCloud[x][y].getPosition());
        }
        return vertexIndices[x][y];
    }

    public String getObjString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < triangleMesh.getNumberOfVertices(); i++) {
            Vector v = triangleMesh.getVertex(i).getPosition();
            sb.append("v ").append((float) v.x());
            sb.append(" ").append((float) v.y());
            sb.append(" ").append((float) v.z());
            sb.append("\n");
        }
        for (int i = 0; i < triangleMesh.getNumberOfTriangles(); i++) {
            Triangle t = triangleMesh.getTriangle(i);
            sb.append("f ").append(t.getVertexIndex(0) + 1);
            sb.append(" ").append(t.getVertexIndex(1) + 1);
            sb.append(" ").append(t.getVertexIndex(2) + 1);
            sb.append("\n");
        }
        return sb.toString();
    }
}
