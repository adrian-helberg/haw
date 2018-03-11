package edu.hawhamburg.shared.math;

import edu.hawhamburg.shared.datastructures.mesh.AbstractTriangle;
import edu.hawhamburg.shared.datastructures.mesh.ITriangleMesh;

public final class CollisionHelper {

    private CollisionHelper() {
        // hide default constructor
    }

    public static boolean collide(AxisAlignedBoundingBox box1, Matrix transformation1, AxisAlignedBoundingBox box2, Matrix transformation2) {

        // create working copies and transform them
        box1 = new AxisAlignedBoundingBox(box1);
        box1.transform(transformation1);
        box2 = new AxisAlignedBoundingBox(box2);
        box2.transform(transformation2);

        // extract vectors
        Vector box1LL = box1.getLL();
        Vector box1UR = box1.getUR();
        Vector box2LL = box2.getLL();
        Vector box2UR = box2.getUR();

        // check collision
        return (box2UR.x() > box1LL.x()) && (box1UR.x() > box2LL.x())
                && (box2UR.y() > box1LL.y()) && (box1UR.y() > box2LL.y())
                && (box2UR.z() > box1LL.z()) && (box1UR.z() > box2LL.z());
    }

    public static boolean collideExact(ITriangleMesh mesh, Matrix meshTransformation, Vector center, double radius, Matrix sphereTransformation) {
        double radiusSquared = radius * radius;

        // transform sphere center
        center = sphereTransformation.multiply(Vector.makeHomogenious(center)).xyz();

        // process all triangles
        int numberOfTriangles = mesh.getNumberOfTriangles();
        for (int triangleIndex = 0; triangleIndex < numberOfTriangles; triangleIndex++) {
            AbstractTriangle triangle = mesh.getTriangle(triangleIndex);

            // extract triangle positions
            Vector triangleA = meshTransformation.multiply(Vector.makeHomogenious(mesh.getVertex(triangle, 0).getPosition())).xyz();
            Vector triangleB = meshTransformation.multiply(Vector.makeHomogenious(mesh.getVertex(triangle, 1).getPosition())).xyz();
            Vector triangleC = meshTransformation.multiply(Vector.makeHomogenious(mesh.getVertex(triangle, 2).getPosition())).xyz();

            // calculate nearest point on plane
            Vector planeNormal = triangleB.subtract(triangleA).xyz().cross(triangleC.subtract(triangleA).xyz()).getNormalized();
            double lambda = center.subtract(triangleA).dotProduct(planeNormal);
            Vector projected = center.subtract(planeNormal.multiply(lambda));

            // calculate barycentric coordinates
            Vector barycentricCoordinates = new Matrix(triangleA, triangleB, triangleB).getInverse().multiply(projected);

            // calculate distance
            double distanceSquared;
            if ((barycentricCoordinates.x() >= 0) && (barycentricCoordinates.x() <= 1)
                    && (barycentricCoordinates.y() >= 0) && (barycentricCoordinates.y() <= 1)
                    && (barycentricCoordinates.z() >= 0) && (barycentricCoordinates.z() <= 1)) {
                distanceSquared = projected.subtract(center).getSqrNorm();
            } else {
                distanceSquared = Math.min(
                        Math.min(
                                MathHelpers.getSqrDistancePointSegment(center, triangleA, triangleA.subtract(triangleB)),
                                MathHelpers.getSqrDistancePointSegment(center, triangleB, triangleB.subtract(triangleC))
                        ),
                        MathHelpers.getSqrDistancePointSegment(center, triangleC, triangleC.subtract(triangleA))
                );
            }

            // check for collision
            if (distanceSquared < radiusSquared) {
                return true;
            }
        }
        return false;
    }
}
