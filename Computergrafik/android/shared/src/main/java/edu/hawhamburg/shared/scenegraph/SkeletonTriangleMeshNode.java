package edu.hawhamburg.shared.scenegraph;

import java.util.ArrayList;
import java.util.List;

import edu.hawhamburg.shared.datastructures.mesh.TriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.Vertex;
import edu.hawhamburg.shared.datastructures.skeleton.Bone;
import edu.hawhamburg.shared.datastructures.skeleton.Skeleton;
import edu.hawhamburg.shared.math.MathHelpers;
import edu.hawhamburg.shared.math.Matrix;
import edu.hawhamburg.shared.math.Vector;

public class SkeletonTriangleMeshNode extends TriangleMeshNode {

    private static final double OMEGA = 0.1;

    private static final double TWO_OMEGA_SQUARED = 2 * OMEGA * OMEGA;

    private static final double WEIGHT_FACTOR = (1 / Math.sqrt(Math.PI * TWO_OMEGA_SQUARED));

    private final TriangleMesh originalMesh;

    /**
     * Cached list with original, homogenious vertex positions.
     */
    private final List<Vector> originalVertexPositions;

    /**
     * Cached list with bones.
     */
    private final List<Bone> skeletonChildren;

    /**
     * Cached number of vertices.
     */
    private final int numberOfVertices;

    public SkeletonTriangleMeshNode(TriangleMesh mesh, Skeleton skeleton) {
        super(mesh);
        originalMesh = new TriangleMesh(mesh);
        numberOfVertices = originalMesh.getNumberOfVertices();
        skeletonChildren = skeleton.getChildren();
        originalVertexPositions = new ArrayList<>(numberOfVertices);
        for (int i = 0; i < numberOfVertices; i++) {
            originalVertexPositions.add(i, Vector.makeHomogenious(originalMesh.getVertex(i).getPosition()));
        }
    }

    public void updateVertexPositionsFromNearestBone() {

        // iterate over all vertices
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {

            // get vertex
            Vertex currentVertex = originalMesh.getVertex(vertexIndex);

            // get the nearest bone
            Bone nearestBone = null;
            double minDistance = Double.MAX_VALUE;
            for (Bone currentBone : skeletonChildren) {
                double testDistance = MathHelpers.getDistanceVertexBone(currentVertex, currentBone);
                if (testDistance < minDistance) {
                    minDistance = testDistance;
                    nearestBone = currentBone;
                }
            }

            // update vertex position
            mesh.getVertex(vertexIndex).getPosition().copy(
                    MathHelpers.getTransformedPositionFromBone(currentVertex.getPosition(), nearestBone)
            );
        }

        // update render data
        updateVbo();
    }

    public void updateVertexPositionsFromWeightedSkeleton() {

        // memory allocation
        Vector vectorSum = new Vector(0, 0, 0, 0);
        double weightSum;
        double currentWeight;
        BoneData currentBoneData;
        Vector currentVertexPosition;

        // get all bone information
        List<BoneData> boneTransformations = new ArrayList<>(skeletonChildren.size());
        for (Bone currentBone : skeletonChildren) {
            Vector start = currentBone.getStart();
            boneTransformations.add(new BoneData(
                    MathHelpers.getBoneTransformation(currentBone),
                    start,
                    currentBone.getEnd().subtract(start)
            ));
        }

        // iterate over all vertices
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {

            // reset working variables
            vectorSum.set(0, 0);
            vectorSum.set(1, 0);
            vectorSum.set(2, 0);
            vectorSum.set(3, 0);
            weightSum = 0;

            // iterate over all bones
            for (int boneIndex = 0; boneIndex < skeletonChildren.size(); boneIndex++) {
                currentBoneData = boneTransformations.get(boneIndex);
                currentVertexPosition = originalVertexPositions.get(vertexIndex);

                currentWeight = calculateWeightFromSqrDistance(MathHelpers.getSqrDistancePointSegment(
                        currentVertexPosition.xyz(),
                        currentBoneData.start,
                        currentBoneData.direction
                ));
                weightSum += currentWeight;
                vectorSum = vectorSum.add(currentBoneData.transformation
                        .multiply(currentVertexPosition)
                        .multiply(currentWeight)
                );
            }

            // update vertex position
            mesh.getVertex(vertexIndex).getPosition().copy(vectorSum.multiply(1 / weightSum).xyz());
        }

        // update render data
        updateVbo();
    }

    private double calculateWeightFromSqrDistance(double sqrDistance) {
        return WEIGHT_FACTOR * Math.exp(-(sqrDistance / TWO_OMEGA_SQUARED));
    }

    /**
     * Cache data class
     */
    private static class BoneData {

        private Matrix transformation;

        private Vector start;

        private Vector direction;

        private BoneData(Matrix transformation, Vector start, Vector direction) {
            this.transformation = transformation;
            this.start = start;
            this.direction = direction;
        }
    }
}
