package edu.hawhamburg.shared.math;

import org.junit.Test;

import edu.hawhamburg.shared.datastructures.mesh.Vertex;
import edu.hawhamburg.shared.datastructures.skeleton.Bone;
import edu.hawhamburg.shared.datastructures.skeleton.Skeleton;

import static org.junit.Assert.*;

public class MathHelpersTest {

    private static final double DELTA = MathHelpers.EPSILON;

    @Test
    public void getDistanceVertexBone() throws Exception {
        Bone bone = new Bone(new Skeleton(), 10);

        // distance from end-points to bone has to be zero
        assertEquals(0, MathHelpers.getDistanceVertexBone(new Vertex(bone.getStart()), bone), DELTA);
        assertEquals(0, MathHelpers.getDistanceVertexBone(new Vertex(bone.getEnd()), bone), DELTA);

        // distance to any points on the bone has to be zero
        Vector boneDirection = bone.getEnd().subtract(bone.getStart());
        assertEquals(0, MathHelpers.getDistanceVertexBone(new Vertex(bone.getStart().add(boneDirection.multiply(0.2))), bone), DELTA);
        assertEquals(0, MathHelpers.getDistanceVertexBone(new Vertex(bone.getStart().add(boneDirection.multiply(0.7))), bone), DELTA);

        // distance to points orthogonal to the bone has to be the orthogonal's vector length
        Vector orthogonalDirection = boneDirection.cross(new Vector(0, 0, 1));
        assertEquals(orthogonalDirection.getNorm(), MathHelpers.getDistanceVertexBone(new Vertex(bone.getStart().add(orthogonalDirection)), bone), DELTA);
        assertEquals(orthogonalDirection.getNorm(), MathHelpers.getDistanceVertexBone(new Vertex(bone.getEnd().add(orthogonalDirection)), bone), DELTA);

        // distance to points on the line through the bone, but not on the bone itself have to have the correct distance
        assertEquals(boneDirection.getNorm(), MathHelpers.getDistanceVertexBone(new Vertex(bone.getEnd().add(boneDirection)), bone), DELTA);
        assertEquals(boneDirection.getNorm(), MathHelpers.getDistanceVertexBone(new Vertex(bone.getStart().add(boneDirection.multiply(-1))), bone), DELTA);
    }
}