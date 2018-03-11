/**
 * Diese Datei gehört zum Android/Java Framework zur Veranstaltung "Computergrafik für
 * Augmented Reality" von Prof. Dr. Philipp Jenke an der Hochschule für Angewandte
 * Wissenschaften (HAW) Hamburg. Weder Teile der Software noch das Framework als Ganzes dürfen
 * ohne die Einwilligung von Philipp Jenke außerhalb von Forschungs- und Lehrprojekten an der HAW
 * Hamburg verwendet werden.
 * <p>
 * This file is part of the Android/Java framework for the course "Computer graphics for augmented
 * reality" by Prof. Dr. Philipp Jenke at the University of Applied (UAS) Sciences Hamburg. Neither
 * parts of the framework nor the complete framework may be used outside of research or student
 * projects at the UAS Hamburg.
 */
package edu.hawhamburg.shared.misc;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.hawhamburg.shared.datastructures.mesh.ITriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.ObjReader;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMeshTools;
import edu.hawhamburg.shared.datastructures.mesh.halfedge.HalfEdgeTriangleMeshFactory;
import edu.hawhamburg.shared.math.Matrix;
import edu.hawhamburg.shared.math.Vector;
import edu.hawhamburg.shared.scenegraph.INode;
import edu.hawhamburg.shared.scenegraph.InnerNode;
import edu.hawhamburg.shared.scenegraph.ScaleNode;
import edu.hawhamburg.shared.scenegraph.TransformationNode;
import edu.hawhamburg.shared.scenegraph.TriangleMeshNode;
import platform.vuforia.VuforiaMarkerNode;

import static edu.hawhamburg.shared.scenegraph.TriangleMeshNode.RenderNormals.PER_VERTEX_NORMAL;

/**
 * Dummy scene with rather simple content.
 *
 * @author Philipp Jenke
 */
public class WatcherDefaultScene extends Scene {

    private TransformationNode watcherTransformation;

    private VuforiaMarkerNode watcherMarker;

    private Vector watcherBoundingBoxCenter;

    private VuforiaMarkerNode targetMarker;

    private Vector targetBoundingBoxCenter;

    public WatcherDefaultScene() {
        super(100, INode.RenderMode.REGULAR);
    }

    @Override
    public void onSetup(InnerNode rootNode) {

        // marker
        watcherMarker = new VuforiaMarkerNode("elphi");
        rootNode.addChild(watcherMarker);

        targetMarker = new VuforiaMarkerNode("campus");
        rootNode.addChild(targetMarker);

        // Cow
        ScaleNode cowNode = new ScaleNode(1);
        ObjReader reader = new ObjReader();
        List<ITriangleMesh> triangleMeshes = reader.read("meshes/cow.obj");

        // transform to half triangle meshes
        List<ITriangleMesh> meshes = new ArrayList<>(triangleMeshes.size());
        for (ITriangleMesh mesh : triangleMeshes) {
            if (mesh instanceof TriangleMesh) {
                meshes.add(HalfEdgeTriangleMeshFactory.create((TriangleMesh) mesh));
            } else {
                throw new RuntimeException("Mesh not instance of " + TriangleMesh.class.getCanonicalName());
            }
        }

        TriangleMeshTools.fitToUnitBox(meshes);
        TriangleMeshTools.placeOnXZPlane(meshes);

        for (ITriangleMesh mesh : meshes) {
            TriangleMeshNode TMN = new TriangleMeshNode(mesh);
            TMN.setRenderNormals(PER_VERTEX_NORMAL);
            cowNode.addChild(TMN);
        }

        // Deer
        ScaleNode deerNode = new ScaleNode(1);
        reader = new ObjReader();
        triangleMeshes = reader.read("meshes/deer.obj");

        // transform to half triangle meshes
        meshes = new ArrayList<>(triangleMeshes.size());
        for (ITriangleMesh mesh : triangleMeshes) {
            if (mesh instanceof TriangleMesh) {
                meshes.add(HalfEdgeTriangleMeshFactory.create((TriangleMesh) mesh));
            } else {
                throw new RuntimeException("Mesh not instance of " + TriangleMesh.class.getCanonicalName());
            }
        }

        TriangleMeshTools.fitToUnitBox(meshes);
        TriangleMeshTools.placeOnXZPlane(meshes);

        for (ITriangleMesh mesh : meshes) {
            TriangleMeshNode TMN = new TriangleMeshNode(mesh);
            TMN.setRenderNormals(PER_VERTEX_NORMAL);
            deerNode.addChild(TMN);
        }

        // get center vectors
        targetBoundingBoxCenter = deerNode.getBoundingBox().getCenter();
        watcherBoundingBoxCenter = cowNode.getBoundingBox().getCenter();

        // place deer on marker
        targetMarker.addChild(deerNode);

        // create transformation node
        watcherTransformation = new TransformationNode();

        // add transformed cow to watcher marker
        watcherTransformation.addChild(cowNode);
        watcherMarker.addChild(watcherTransformation);
    }

    @Override
    public void onTimerTick(int counter) {
        // Timer tick event
    }

    @Override
    public void onSceneRedraw() {
        try {
            watcherTransformation.setTransformation(getWatcherTransformationMatrix());
        } catch (Throwable t) {
            Log.e("DEBUG", "" + t.getMessage(), t);
        }
    }

    private Vector getTargetPositionInWatcherCoordinates() {
        return Vector.removeDimension(
                watcherMarker.getTransformation().getInverse()
                        .multiply(targetMarker.getTransformation())
                        .multiply(Vector.addDimension(targetBoundingBoxCenter))
        );
    }

    private Vector getWatchingDirection() {
        return getTargetPositionInWatcherCoordinates().subtract(watcherBoundingBoxCenter);
    }

    private Matrix getWatcherTransformationMatrix() {
        Vector x = getWatchingDirection().getNormalized();
        Vector z = x.cross(new Vector(0, 1, 0)).getNormalized();
        Vector y = z.cross(x).getNormalized();
        return Matrix.createTransformationMatrix(
                Vector.addDimension(x, 0),
                Vector.addDimension(y, 0),
                Vector.addDimension(z, 0),
                new Vector(0, 0, 0, 1)
        );
    }
}
