package edu.hawhamburg.shared.math;

import edu.hawhamburg.shared.datastructures.mesh.Triangle;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.Vertex;

import java.io.IOException;
import java.io.Writer;

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
                    triangleMesh.addTriangle(
                        getOrCreateMeshVertex(x, y + vertexGenerationStep),
                        getOrCreateMeshVertex(x + vertexGenerationStep, y),
                        getOrCreateMeshVertex(x, y)
                    );
                    triangleMesh.addTriangle(
                        getOrCreateMeshVertex(x, y + vertexGenerationStep),
                        getOrCreateMeshVertex(x + vertexGenerationStep, y + vertexGenerationStep),
                        getOrCreateMeshVertex(x + vertexGenerationStep, y)
                    );
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

    public void writeOBJ(Writer writer) throws IOException {
        for (int i = 0; i < triangleMesh.getNumberOfVertices(); i++) {
            Vector v = triangleMesh.getVertex(i).getPosition();
            writer.append("v ").append(Float.toString((float) v.x()));
            writer.append(" ").append(Float.toString((float) v.y()));
            writer.append(" ").append(Float.toString((float) v.z()));
            writer.append("\n");
        }
        for (int i = 0; i < triangleMesh.getNumberOfTriangles(); i++) {
            Triangle t = triangleMesh.getTriangle(i);
            writer.append("f ").append(Integer.toString(t.getVertexIndex(0) + 1));
            writer.append(" ").append(Integer.toString(t.getVertexIndex(1) + 1));
            writer.append(" ").append(Integer.toString(t.getVertexIndex(2) + 1));
            writer.append("\n");
        }
    }
}
