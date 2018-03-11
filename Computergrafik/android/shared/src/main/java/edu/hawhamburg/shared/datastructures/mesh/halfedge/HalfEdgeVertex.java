package edu.hawhamburg.shared.datastructures.mesh.halfedge;

import java.util.ArrayList;
import java.util.List;

import edu.hawhamburg.shared.datastructures.mesh.Vertex;
import edu.hawhamburg.shared.math.Vector;

public class HalfEdgeVertex extends Vertex {

    List<HalfEdge> outgoingHalfEdges = new ArrayList<>();

    int vertexIndex;

    HalfEdgeVertex(Vector position, int index) {
        super(position);
        this.vertexIndex = index;
    }

    void computeVertexNormal() {
        Vector normal = new Vector(3);

        // all facets have equal weight
        for (HalfEdge edge : outgoingHalfEdges) {
            normal = normal.add(edge.facet.getNormal());
        }
        int count = outgoingHalfEdges.size();
        setNormal(new Vector(
            normal.get(0) / count,
            normal.get(1) / count,
            normal.get(2) / count
        ));
    }
}
