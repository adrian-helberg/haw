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

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import edu.hawhamburg.shared.datastructures.mesh.ITriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.ObjReader;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMeshTools;
import edu.hawhamburg.shared.datastructures.mesh.halfedge.HalfEdgeTriangleMeshFactory;
import edu.hawhamburg.shared.datastructures.spline.HermiteSpline;
import edu.hawhamburg.shared.math.Matrix;
import edu.hawhamburg.shared.math.Vector;
import edu.hawhamburg.shared.scenegraph.*;
import platform.vuforia.VuforiaMarkerNode;
import static edu.hawhamburg.shared.scenegraph.TriangleMeshNode.RenderNormals.PER_VERTEX_NORMAL;

/**
 * Dummy scene with rather simple content.
 *
 * @author Philipp Jenke
 */
public class SplineDefaultScene extends Scene {

    private static final double TICK_MOVE = 1. / 200;

    private TransformationNode transformationNode;

    private HermiteSpline hermiteSpline;

    private double t;

    public SplineDefaultScene() {
        super(100, INode.RenderMode.REGULAR);
    }

    @Override
    public void onSetup(InnerNode rootNode) {

        try {

            // marker
            VuforiaMarkerNode markerNode = new VuforiaMarkerNode("elphi");
            rootNode.addChild(markerNode);

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

            // create spline
            hermiteSpline = new HermiteSpline(
                    new Vector(-.25, 0, -.25),
                    new Vector(0, .5, -.25),
                    new Vector(0, .5, .25),
                    new Vector(.25, .25, .25)
            );

            // draw spline
            double step = 1. / 100;
            for (double t = 0; t < 1; t += step) {
                markerNode.addChild(new LineStripNode(
                        hermiteSpline.evaluateCurve(t),
                        hermiteSpline.evaluateCurve(t + step)
                ));
            }

            // add cow
            transformationNode = new TransformationNode();
            markerNode.addChild(transformationNode);
            transformationNode.addChild(cowNode);

            // initially set t
            t = 0;

        } catch (Throwable t) {
            Log.e("DEBUG", "" + t.getMessage(), t);
        }
    }

    @Override
    public void onTimerTick(int counter) {

        // update t
        if (t >= 1) {
            t = 0;
        }
        t += TICK_MOVE;

        // update matrix
        transformationNode.setTransformation(getTransformationMatrix(t));
    }

    @Override
    public void onSceneRedraw() {
        // Redraw event
    }

    private Matrix getTransformationMatrix(double t) {
        Vector x = hermiteSpline.evaluateTangent(t).getNormalized();
        Vector z = x.cross(new Vector(0, 1, 0)).getNormalized();
        Vector y = z.cross(x).getNormalized();

        Vector p = hermiteSpline.evaluateCurve(t);

        return Matrix.createTransformationMatrix(
                Vector.addDimension(x, 0),
                Vector.addDimension(y, 0),
                Vector.addDimension(z, 0),
                Vector.addDimension(p, 1)
        );
    }
}
