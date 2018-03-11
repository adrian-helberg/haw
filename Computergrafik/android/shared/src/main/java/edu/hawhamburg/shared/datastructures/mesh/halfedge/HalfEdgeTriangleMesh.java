package edu.hawhamburg.shared.datastructures.mesh.halfedge;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.hawhamburg.shared.datastructures.mesh.*;
import edu.hawhamburg.shared.math.AxisAlignedBoundingBox;
import edu.hawhamburg.shared.math.Vector;
import edu.hawhamburg.shared.rendering.Texture;

public class HalfEdgeTriangleMesh implements ITriangleMesh {

    private List<HalfEdgeVertex> vertices = new ArrayList<>();

    private List<HalfEdgeTriangle> triangles = new ArrayList<>();

    /**
     * Add a new vertex (given by position) to the vertex list. The new vertex is
     * appended to the end of the list.
     *
     * @param position
     */
    @Override
    public int addVertex(Vector position) {
        int index = vertices.size();
        vertices.add(new HalfEdgeVertex(position, index));
        return index;
    }

    /**
     * Index in vertex list.
     *
     * @param index
     */
    @Override
    public HalfEdgeVertex getVertex(int index) {
        return vertices.get(index);
    }

    /**
     * Index in triangle, must be in 0, 1, 2.
     *
     * @param triangle
     * @param index
     */
    @Override
    public HalfEdgeVertex getVertex(AbstractTriangle triangle, int index) {
        if (triangle instanceof HalfEdgeTriangle) {
            return ((HalfEdgeTriangle) triangle).edges[index].start;
        }
        throw new InvalidParameterException("triangle has to be an instance of " + HalfEdgeTriangle.class.getCanonicalName());
    }

    @Override
    public int getNumberOfVertices() {
        return vertices.size();
    }

    /**
     * Add a new triangle to the mesh with the vertex indices a, b, c. The index
     * of the first vertex is 0.
     *
     * @param vertexIndex1
     * @param vertexIndex2
     * @param vertexIndex3
     */
    @Override
    public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
        HalfEdgeVertex v1 = getVertex(vertexIndex1);
        HalfEdgeVertex v2 = getVertex(vertexIndex2);
        HalfEdgeVertex v3 = getVertex(vertexIndex3);

        HalfEdge e12 = new HalfEdge(v1);
        HalfEdge e23 = new HalfEdge(v2);
        HalfEdge e31 = new HalfEdge(v3);

        e12.successor = e23;
        e23.successor = e31;
        e31.successor = e12;

        addTriangle(new HalfEdgeTriangle(e12, e23, e31));
    }

    /**
     * Add a new triangle to the mesh with the vertex indices a, b, c. The index
     * of the first vertex is 0.
     *
     * @param t
     */
    @Override
    public void addTriangle(AbstractTriangle t) {
        if (!(t instanceof HalfEdgeTriangle)) {
            throw new InvalidParameterException("t has to be an instance of " + HalfEdgeTriangle.class.getCanonicalName());
        }
        HalfEdgeTriangle triangle = (HalfEdgeTriangle) t;

        // add triangle to mesh
        triangles.add(triangle);

        // connect edges
        for (HalfEdge edge : triangle.edges) {
            for (HalfEdge other : edge.getTarget().outgoingHalfEdges) {

                // check if we have an inverse edge
                if (edge.start == other.getTarget()) {
                    edge.opposite = other;
                    other.opposite = edge;

                    // exit inner loop since we've found the opposite edge
                    break;
                }
            }
        }
    }

    /**
     * Add triangle by vertex indices and corresponding texture coordinate
     * indices.
     *
     * @param vertexIndex1
     * @param vertexIndex2
     * @param vertexIndex3
     * @param texCoordIndex1
     * @param texCoordIndex2
     * @param texCoordIndex3
     */
    @Override
    public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3, int texCoordIndex1, int texCoordIndex2, int texCoordIndex3) {

        // TODO: ignored texture coordinates
        addTriangle(vertexIndex1, vertexIndex2, vertexIndex3);
    }

    @Override
    public int getNumberOfTriangles() {
        return triangles.size();
    }

    @Override
    public AbstractTriangle getTriangle(int triangleIndex) {
        return triangles.get(triangleIndex);
    }

    /**
     * Clear mesh - remove all triangles and vertices.
     */
    @Override
    public void clear() {
        triangles.clear();
        vertices.clear();
    }

    /**
     * Compute the triangles normals.
     */
    @Override
    public void computeTriangleNormals() {

        // hijack this method to calculate both: triangle and vertex normals
        for (int triangleIndex = 0; triangleIndex < getNumberOfTriangles(); triangleIndex++) {
            triangles.get(triangleIndex).computeTriangleNormal();
        }

        for (int vertexIndex = 0; vertexIndex < getNumberOfVertices(); vertexIndex++) {
            vertices.get(vertexIndex).computeVertexNormal();
        }
    }

    @Override
    public Vector getTextureCoordinate(int index) {
        // TODO implement
        return null;
    }

    /**
     * Add texture coordinate to mesh.
     *
     * @param t
     */
    @Override
    public void addTextureCoordinate(Vector t) {
        // TODO implement
    }

    @Override
    public Texture getTexture() {

        // TODO implement
        return null;
    }

    /**
     * Create a mesh of the shadow polygons.
     * <p>
     * lightPosition: Position of the light source. extend: Length of the polygons
     * shadowPolygonMesh: Result is put in there
     *
     * @param lightPosition
     * @param extend
     * @param shadowPolygonMesh
     */
    @Override
    public void createShadowPolygons(Vector lightPosition, float extend, ITriangleMesh shadowPolygonMesh) {
        shadowPolygonMesh.clear();
        List<Edge> silhouetteEdges = getSilhouette(lightPosition);
        for (int i = 0; i < silhouetteEdges.size(); i++) {
            Edge edge = silhouetteEdges.get(i);
            Vector v0 = getVertex(edge.a).getPosition();
            Vector v1 = getVertex(edge.b).getPosition();
            Vector dv0 = v0.subtract(lightPosition).getNormalized().multiply(extend);
            Vector dv1 = v1.subtract(lightPosition).getNormalized().multiply(extend);
            Vector v0Dash = v0.add(dv0);
            Vector v1Dash = v1.add(dv1);
            int v0Index = shadowPolygonMesh.addVertex(v0);
            int v1Index = shadowPolygonMesh.addVertex(v1);
            int v0DashIndex = shadowPolygonMesh.addVertex(v0Dash);
            int v1DashIndex = shadowPolygonMesh.addVertex(v1Dash);
            Triangle t1 = new Triangle(v0Index, v0DashIndex, v1DashIndex);
            t1.setColor(new Vector(0.25, 0.025, 0.75, 0.5));
            Triangle t2 = new Triangle(v0Index, v1DashIndex, v1Index);
            t2.setColor(new Vector(0.25, 0.025, 0.75, 0.5));
            shadowPolygonMesh.addTriangle(t1);
            shadowPolygonMesh.addTriangle(t2);
        }
        shadowPolygonMesh.computeTriangleNormals();
        // System.out.println("Created " + shadowPolygonMesh.getNumberOfTriangles()
        // + " shadow triangles.");
    }

    /**
     * Compute the silhouette (list of edges) for a given position
     */
    private List<Edge> getSilhouette(Vector position) {
        List<Edge> silhouetteEdges = new ArrayList<>();
        Map<Edge, Integer> edge2FacetMap = new HashMap<>();
        for (int triangleIndex = 0; triangleIndex < triangles.size(); triangleIndex++) {
            HalfEdgeTriangle t = triangles.get(triangleIndex);
            for (HalfEdge halfEdge : t.edges) {
                int a = halfEdge.start.vertexIndex;
                int b = halfEdge.getTarget().vertexIndex;
                Edge edge = new Edge(a, b);
                if (edge2FacetMap.containsKey(edge)) {
                    // Opposite egde found
                    int oppositeTriangle = edge2FacetMap.get(edge);
                    if (isSilhouetteEdge(position, triangleIndex, oppositeTriangle, edge)) {
                        silhouetteEdges.add(edge);
                    }
                    // Debugging: if edge map is empty in the end, then the surface is
                    // closed.
                    edge2FacetMap.remove(edge);
                } else {
                    // Opposite edge not yet found
                    edge2FacetMap.put(edge, triangleIndex);
                }
            }
        }
        // System.out
        // .println("Created " + silhouetteEdges.size() + " silhouette edges.");
        return silhouetteEdges;
    }

    /**
     * Returns true if the edge between the two given triangles is a silhouette
     * edge for the given position.
     */
    private boolean isSilhouetteEdge(Vector position, int triangle1Index, int triangle2Index, Edge edge) {
        HalfEdgeTriangle t1 = triangles.get(triangle1Index);
        HalfEdgeTriangle t2 = triangles.get(triangle2Index);
        HalfEdgeVertex v1 = t1.edges[0].start;
        HalfEdgeVertex v2 = t2.edges[0].start;
        double d1 = t1.getNormal().multiply(position) - t1.getNormal().multiply(v1.getPosition());
        double d2 = t2.getNormal().multiply(position) - t2.getNormal().multiply(v2.getPosition());
        if (d1 < 0) {
            edge.Flip();
        }
        return d1 * d2 < 0;
    }

    /**
     * Return the number of texture coordinates in the mesh.
     */
    @Override
    public int getNumberOfTextureCoordinates() {

        // TODO implement
        return 0;
    }

    /**
     * Returns true if the mesh has a texture assigned.
     */
    @Override
    public boolean hasTexture() {

        // TODO implement
        return false;
    }

    /**
     * Return the bounding box of the mesh.
     */
    @Override
    public AxisAlignedBoundingBox getBoundingBox() {
        AxisAlignedBoundingBox bb = new AxisAlignedBoundingBox();
        for (HalfEdgeVertex v : vertices) {
            bb.add(v.getPosition());
        }
        return bb;
    }

    @Override
    public void setTextureName(String textureFilename) {
        // TODO implement
    }

    /**
     * Set color to all triangles and all vertices of the mesh.
     *
     * @param color
     */
    @Override
    public void setColor(Vector color) {
        // TODO implement
    }

    /**
     * Sets the alpha (blendding/trasparency) value for all triangles.
     *
     * @param alpha
     */
    @Override
    public void setTransparency(double alpha) {
        // TODO implement
    }
}
