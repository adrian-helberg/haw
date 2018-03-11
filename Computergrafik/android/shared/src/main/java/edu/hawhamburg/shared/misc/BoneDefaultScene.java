package edu.hawhamburg.shared.misc;

import android.util.Log;

import java.util.List;

import edu.hawhamburg.shared.datastructures.mesh.*;
import edu.hawhamburg.shared.datastructures.skeleton.Bone;
import edu.hawhamburg.shared.datastructures.skeleton.Skeleton;
import edu.hawhamburg.shared.datastructures.skeleton.SkeletonNode;
import edu.hawhamburg.shared.math.MathHelpers;
import edu.hawhamburg.shared.math.Matrix;
import edu.hawhamburg.shared.math.Vector;
import edu.hawhamburg.shared.scenegraph.InnerNode;
import edu.hawhamburg.shared.scenegraph.SkeletonTriangleMeshNode;
import edu.hawhamburg.shared.scenegraph.TransformationNode;
import edu.hawhamburg.shared.scenegraph.TriangleMeshNode;
import platform.vuforia.VuforiaMarkerNode;

/**
 * Test scene for sceleton applications.
 *
 * @author Philipp Jenke
 */

public class BoneDefaultScene extends Scene {
    private double animationAlpha = 0;

    private Bone trunkBone;
    private Bone cylinderBottom;
    private Bone cylinderMiddle;
    private Bone cylinderTop;
    private SkeletonTriangleMeshNode meshNode;
    private ShowBones showBones = ShowBones.BONES_ONLY;
    private SkeletonNode skeletonNode;

    public enum ShowBones {
        BONES_ONLY, MESH_AND_BONES, MESH_ONLY;

        public ShowBones next() {
            int index = ordinal() + 1;
            if (index >= values().length) {
                index = 0;
            }
            return values()[index];
        }
    }

    public BoneDefaultScene() {
    }

    @Override
    public void onSetup(InnerNode rootNode) {
        try {

            Button button = new Button("skeleton.png",
                    -0.7, -0.7, 0.2, new ButtonHandler() {
                @Override
                public void handle() {
                    showBones = showBones.next();
                    updateRenderSettings();
                }
            });
            addButton(button);

            VuforiaMarkerNode markerNode = new VuforiaMarkerNode("elphi");
            rootNode.addChild(markerNode);

            // Skeleton
            Skeleton skeleton = new Skeleton();
            trunkBone = new Bone(skeleton, 0.2);
            trunkBone.setRotation(Matrix.createRotationMatrix4(new Vector(0, 0, 1), 90 * Math.PI / 180.0));
            cylinderBottom = new Bone(trunkBone, 0.26);
            cylinderMiddle = new Bone(cylinderBottom, 0.26);
            cylinderTop = new Bone(cylinderMiddle, 0.26);

            // Preserve the current state of all bones as the rest state
            skeleton.setRestState();
            skeletonNode = new SkeletonNode(skeleton);
            getRoot().addChild(skeletonNode);

            // Pine-Tree
//            ObjReader reader = new ObjReader();
//            List<ITriangleMesh> meshes = reader.read("meshes/pinetree.obj");
//            TriangleMesh mesh = TriangleMeshTools.unite(meshes);

            // Cylinder
            TriangleMesh mesh = new TriangleMesh();
            TriangleMeshFactory.createCylinder(mesh, 0.025, 1, 8, 20);

            meshNode = new SkeletonTriangleMeshNode(mesh, skeleton);
            markerNode.addChild(meshNode);
            markerNode.addChild(skeletonNode);

            updateRenderSettings();
            getRoot().setLightPosition(new Vector(1, 2, 1));

        } catch (Throwable t) {
            Log.e("DEBUG", "" + t.getMessage(), t);
        }
    }

    private void updateRenderSettings() {
        switch (showBones) {
            case MESH_ONLY:
                skeletonNode.setActive(false);
                meshNode.setTransparency(1.0);
                meshNode.setActive(true);
                break;
            case MESH_AND_BONES:
                skeletonNode.setActive(true);
                meshNode.setTransparency(0.5);
                meshNode.setActive(true);
                break;
            case BONES_ONLY:
                skeletonNode.setActive(true);
                meshNode.setTransparency(0.5);
                meshNode.setActive(false);
                break;
        }
    }

    @Override
    public void onTimerTick(int counter) {

    }

    @Override
    public void onSceneRedraw() {
        try {

            // Animate skeleton
            double DELTA_ANIMATION_ALPHA = 0.03;
            animationAlpha += DELTA_ANIMATION_ALPHA;
            double angle = Math.cos(animationAlpha) * Math.PI / 180.0 * 8;
            trunkBone.setRotation(Matrix.createRotationMatrix4(new Vector(0, 0, 1), 90 * Math.PI / 180.0));
            cylinderTop.setRotation(Matrix.createRotationMatrix4(new Vector(0, 1, 0), angle));
            cylinderMiddle.setRotation(Matrix.createRotationMatrix4(new Vector(0, 1, 0), 2 * angle));
            cylinderBottom.setRotation(Matrix.createRotationMatrix4(new Vector(0, 1, 0), 3 * angle));

            // Update mesh based on skeleton
//            meshNode.updateVertexPositionsFromNearestBone();
            meshNode.updateVertexPositionsFromWeightedSkeleton();

        } catch (Throwable t) {
            Log.e("DEBUG", "" + t.getMessage(), t);
        }
    }
}
