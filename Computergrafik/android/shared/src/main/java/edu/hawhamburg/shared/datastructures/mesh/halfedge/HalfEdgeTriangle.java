package edu.hawhamburg.shared.datastructures.mesh.halfedge;

import edu.hawhamburg.shared.datastructures.mesh.AbstractTriangle;
import edu.hawhamburg.shared.math.Vector;

public class HalfEdgeTriangle extends AbstractTriangle {

    HalfEdgeTriangle(HalfEdge a, HalfEdge b, HalfEdge c) {
        edges = new HalfEdge[] {a, b, c};
        a.facet = b.facet = c.facet = this;
    }

    HalfEdge[] edges;

    void computeTriangleNormal() {
        Vector a = edges[0].start.getPosition();
        Vector b = edges[1].start.getPosition();
        Vector c = edges[2].start.getPosition();
        Vector normal = b.subtract(a).cross(c.subtract(a));
        double norm = normal.getNorm();
        if (norm > 1e-5) {
            normal = normal.multiply(1.0 / norm);
        }
        setNormal(normal);
    }
}
