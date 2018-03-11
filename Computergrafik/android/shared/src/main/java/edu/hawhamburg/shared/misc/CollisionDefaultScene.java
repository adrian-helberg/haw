package edu.hawhamburg.shared.misc;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.hawhamburg.shared.datastructures.mesh.ITriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.ObjReader;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMeshTools;
import edu.hawhamburg.shared.datastructures.mesh.halfedge.HalfEdgeTriangleMeshFactory;
import edu.hawhamburg.shared.math.CollisionHelper;
import edu.hawhamburg.shared.math.Vector;
import edu.hawhamburg.shared.scenegraph.BoundingBoxNode;
import edu.hawhamburg.shared.scenegraph.INode;
import edu.hawhamburg.shared.scenegraph.InnerNode;
import edu.hawhamburg.shared.scenegraph.SphereNode;
import edu.hawhamburg.shared.scenegraph.TranslationNode;
import edu.hawhamburg.shared.scenegraph.TriangleMeshNode;
import platform.vuforia.VuforiaMarkerNode;

import static edu.hawhamburg.shared.scenegraph.TriangleMeshNode.RenderNormals.PER_TRIANGLE_NORMAL;
import static edu.hawhamburg.shared.scenegraph.TriangleMeshNode.RenderNormals.PER_VERTEX_NORMAL;

public class CollisionDefaultScene extends Scene {

    private static final double SPHERE_RADIUS = 0.2;

    private static final Vector COLOR_GREEN = new Vector(60. / 255, 200. / 255, 40. / 255, 1);

    private static final Vector COLOR_ORANGE = new Vector(240. / 255, 200. / 255, 20. / 255, 1);

    private static final Vector COLOR_RED = new Vector(240. / 255, 20. / 255, 20. / 255, 1);

    private SphereNode sphere;

    private BoundingBoxNode sphereBoundingNode;

    private TriangleMeshNode cow;

    private BoundingBoxNode cowBoundingNode;

    private ITriangleMesh cowMesh;

    public CollisionDefaultScene() {
        super(100, INode.RenderMode.REGULAR);
    }

    @Override
    public void onSetup(InnerNode rootNode) {
        try {

            // Sphere
            VuforiaMarkerNode sphereMarker = new VuforiaMarkerNode("elphi");
            rootNode.addChild(sphereMarker);

            sphere = new SphereNode(SPHERE_RADIUS, 100);
            TranslationNode sphereTranslation = new TranslationNode(new Vector(0, -0.6, -0.8));
            sphereTranslation.addChild(sphere);
            sphereMarker.addChild(sphereTranslation);

            sphereBoundingNode = new BoundingBoxNode(sphere.getBoundingBox());
            sphereTranslation.addChild(sphereBoundingNode);

            // Cow
            VuforiaMarkerNode secondMarker = new VuforiaMarkerNode("campus");
            rootNode.addChild(secondMarker);

            ObjReader reader = new ObjReader();
            List<ITriangleMesh> triangleMeshes = reader.read("meshes/cow.obj");
            TriangleMeshTools.fitToUnitBox(triangleMeshes);
            TriangleMeshTools.placeOnXZPlane(triangleMeshes);

            cow = new TriangleMeshNode(cowMesh = TriangleMeshTools.unite(triangleMeshes));
            cow.setRenderNormals(PER_TRIANGLE_NORMAL);

            TranslationNode cowTranslation = new TranslationNode(new Vector(0, 0, 0));
            cowTranslation.addChild(cow);

            // Create bounding box
            cowBoundingNode = new BoundingBoxNode(cow.getBoundingBox());
            cowTranslation.addChild(cowBoundingNode);

            secondMarker.addChild(cowTranslation);
        } catch (Throwable t) {
            Log.e("[onSetup]", t.getMessage());
        }
    }

    @Override
    public void onTimerTick(int counter) {
        // Nothing to do here
    }

    @Override
    public void onSceneRedraw() {
        try {

            // Bounding box collision check
            if (CollisionHelper.collide(sphere.getBoundingBox(), sphere.getTransformation(), cow.getBoundingBox(), cow.getTransformation())) {

                // Object collision check (the sphere center is (0,0,0) in sphere-local coordinates)
                if (CollisionHelper.collideExact(cowMesh, cow.getTransformation(), new Vector(0, 0, 0), SPHERE_RADIUS, sphere.getTransformation())) {
                    sphereBoundingNode.setColor(COLOR_RED);
                    cowBoundingNode.setColor(COLOR_RED);
                } else {
                    sphereBoundingNode.setColor(COLOR_ORANGE);
                    cowBoundingNode.setColor(COLOR_ORANGE);
                }
            } else {
                sphereBoundingNode.setColor(COLOR_GREEN);
                cowBoundingNode.setColor(COLOR_GREEN);
            }
        } catch (Throwable t) {
            Log.e("[onSceneRedraw]", t.getMessage());
        }
    }
}
