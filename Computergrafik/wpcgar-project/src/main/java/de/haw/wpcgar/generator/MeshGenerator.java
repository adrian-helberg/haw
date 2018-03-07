package de.haw.wpcgar.generator;

import edu.hawhamburg.shared.datastructures.mesh.Triangle;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.Vertex;
import edu.hawhamburg.shared.math.Vector;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Create a mesh out of a point cloud an write it into a file.
 * Contains a vertex generation step for complexity of mesh.
 *
 * @author Adrian Helberg
 */
public class MeshGenerator {
    private final Vertex[][] pointCloud;

    private static List<Vector> colors = new ArrayList<>();

    private final int resolutionX;

    private final int resolutionY;

    private final int vertexGenerationStep;

    private final int[][] vertexIndices;

    private final TriangleMesh triangleMesh = new TriangleMesh();

    public MeshGenerator(Vertex[][] pointCloud, List<Vector> colors) {
        this(pointCloud, colors, 1);
    }

    public MeshGenerator(Vertex[][] pointCloud, List<Vector> colors, int vertexGenerationStep) {
        this.pointCloud = pointCloud;
        this.colors = colors;
        this.vertexGenerationStep = vertexGenerationStep;
        resolutionX = pointCloud.length;
        resolutionY = resolutionX > 0 ? pointCloud[0].length : 0;

        vertexIndices = new int[resolutionX][resolutionY];
        for (int x = 0; x < resolutionX; x++) {
            for (int y = 0; y < resolutionY; y++) {
                vertexIndices[x][y] = -1;
            }
        }

        // Generate mesh
        generateMesh();
    }

    public TriangleMesh getTriangleMesh() {
        return triangleMesh;
    }

    private void generateMesh() {
        for (int x = 0; x < resolutionX; x += vertexGenerationStep) {
            for (int y = 0; y < resolutionY; y += vertexGenerationStep) {
                if (x + vertexGenerationStep < resolutionX && y + vertexGenerationStep < resolutionX) {

                    Triangle t1, t2;

                    t1 = new Triangle(
                            getOrCreateMeshVertex(x, y + vertexGenerationStep),
                            getOrCreateMeshVertex(x + vertexGenerationStep, y),
                            getOrCreateMeshVertex(x, y)
                    );
                    triangleMesh.addTriangle(t1);

//                    triangleMesh.addTriangle(
//                            getOrCreateMeshVertex(x, y + vertexGenerationStep),
//                            getOrCreateMeshVertex(x + vertexGenerationStep, y),
//                            getOrCreateMeshVertex(x, y)
//                    );

                    t2 = new Triangle(
                            getOrCreateMeshVertex(x, y + vertexGenerationStep),
                            getOrCreateMeshVertex(x + vertexGenerationStep, y + vertexGenerationStep),
                            getOrCreateMeshVertex(x + vertexGenerationStep, y)
                    );
                    triangleMesh.addTriangle(t2);

//                    triangleMesh.addTriangle(
//                            getOrCreateMeshVertex(x, y + vertexGenerationStep),
//                            getOrCreateMeshVertex(x + vertexGenerationStep, y + vertexGenerationStep),
//                            getOrCreateMeshVertex(x + vertexGenerationStep, y)
//                    );

                    if (Objects.equals(triangleMesh.getVertex(t1.getVertexIndex(0)).getColor().getName(), "ocean")
                     || Objects.equals(triangleMesh.getVertex(t2.getVertexIndex(0)).getColor().getName(), "ocean"))
                    {
                        triangleMesh.addTriangle(
                            getOrCreateOceanLevelVertex(x, y + vertexGenerationStep),
                            getOrCreateOceanLevelVertex(x + vertexGenerationStep, y),
                            getOrCreateOceanLevelVertex(x, y)
                        );

                        triangleMesh.addTriangle(
                            getOrCreateOceanLevelVertex(x, y + vertexGenerationStep),
                            getOrCreateOceanLevelVertex(x + vertexGenerationStep, y + vertexGenerationStep),
                            getOrCreateOceanLevelVertex(x + vertexGenerationStep, y)
                        );
                    }
                }
            }
        }
    }

    private int getOrCreateMeshVertex(int x, int y) {
        if (vertexIndices[x][y] == -1) {
            vertexIndices[x][y] = triangleMesh.addVertex(pointCloud[x][y].getPosition(), pointCloud[x][y].getColor());
        }
        return vertexIndices[x][y];
    }

    private int getOrCreateOceanLevelVertex(int x, int y) {
        Vertex current = pointCloud[x][y];
        Vector v = new Vector(current.getPosition().x(), 0.13, current.getPosition().z());

        return triangleMesh.addVertex(v, current.getColor());
    }

    public void writeOBJ(Writer writer) throws IOException {
        writer.append("mtllib world.mtl").append("\n");
        for (int i = 0; i < triangleMesh.getNumberOfVertices(); i++) {
            Vector v = triangleMesh.getVertex(i).getPosition();
            writer.append("v ").append(Float.toString((float) v.x()));
            writer.append(" ").append(Float.toString((float) v.y()));
            writer.append(" ").append(Float.toString((float) v.z()));
            writer.append("\n");
        }

        String currentMLT = "default";
        String mtl;

        for (int i = 0; i < triangleMesh.getNumberOfTriangles(); i++) {
            Triangle t = triangleMesh.getTriangle(i);
            mtl = triangleMesh.getVertex(t.getVertexIndex(0) + 1).getColor().getName();

            if(!Objects.equals(mtl, currentMLT) || i == 0) {
                writer.append("usemtl ").append(mtl).append("\n");
                currentMLT = mtl;
            }

            writer.append("f ").append(Integer.toString(t.getVertexIndex(0) + 1));
            writer.append(" ").append(Integer.toString(t.getVertexIndex(1) + 1));
            writer.append(" ").append(Integer.toString(t.getVertexIndex(2) + 1));
            writer.append("\n");
        }
    }

    public void writeMTL(Writer writer) throws IOException {
        for (Vector c : colors) {
            writer.append("newmtl ").append(c.getName());
            writer.append("\n");
            writer.append("kd ").append(String.valueOf(c.x()));
            writer.append(" ").append(String.valueOf(c.y()));
            writer.append(" ").append(String.valueOf(c.z()));
            writer.append("\n");
            writer.append("ks 0.0 0.0 0.0").append("\n");
            writer.append("illum 0");
            writer.append("\n\n");
        }
    }
}
