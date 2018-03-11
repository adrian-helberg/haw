package edu.hawhamburg.shared.datastructures.mesh.halfedge;

public class HalfEdge {

    HalfEdge(HalfEdgeVertex start) {
        this.start = start;
        start.outgoingHalfEdges.add(this);
    }

    HalfEdgeVertex start;

    HalfEdge opposite;

    HalfEdge successor;

    HalfEdgeTriangle facet;

    HalfEdgeVertex getTarget() {
        if (successor != null) {
            return successor.start;
        }
        return null;
    }
}
