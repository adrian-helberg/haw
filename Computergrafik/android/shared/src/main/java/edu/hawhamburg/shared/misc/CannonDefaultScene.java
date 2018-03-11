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
import java.util.Collections;
import java.util.List;

import edu.hawhamburg.shared.datastructures.mesh.ITriangleMesh;
import edu.hawhamburg.shared.datastructures.mesh.ObjReader;
import edu.hawhamburg.shared.datastructures.mesh.TriangleMeshTools;
import edu.hawhamburg.shared.datastructures.particle.Particle;
import edu.hawhamburg.shared.math.CollisionHelper;
import edu.hawhamburg.shared.math.Vector;
import edu.hawhamburg.shared.scenegraph.INode;
import edu.hawhamburg.shared.scenegraph.InnerNode;
import edu.hawhamburg.shared.scenegraph.ScaleNode;
import edu.hawhamburg.shared.scenegraph.SphereNode;
import edu.hawhamburg.shared.scenegraph.TransformationNode;
import edu.hawhamburg.shared.scenegraph.TranslationNode;
import edu.hawhamburg.shared.scenegraph.TriangleMeshNode;
import platform.vuforia.VuforiaMarkerNode;

import static edu.hawhamburg.shared.scenegraph.TriangleMeshNode.RenderNormals.PER_TRIANGLE_NORMAL;

public class CannonDefaultScene extends Scene {

    private static final double TICK_MOVE = 0.01;
    private static final double PROJECTILE_RADIUS = 0.05;

    private TriangleMeshNode target;
    private ScaleNode targetScale;

    private TriangleMeshNode cannon;
    private VuforiaMarkerNode cannonMarker;

    private List<Projectile> projectiles = Collections.synchronizedList(new ArrayList<Projectile>());

    public CannonDefaultScene() {
        super(100, INode.RenderMode.REGULAR);
    }

    @Override
    public void onSetup(InnerNode rootNode) {
        try {
            Button button = new Button("kanone_abfeuern.png",
                    -0.7, -0.7, 0.2, new ButtonHandler() {
                @Override
                public void handle() {
                    shoot();
                }
            });
            addButton(button);

            // Cannon
            cannonMarker = new VuforiaMarkerNode("elphi");
            rootNode.addChild(cannonMarker);

            ObjReader reader = new ObjReader();
            List<ITriangleMesh> triangleMeshes = reader.read("meshes/cannon.obj");
            TriangleMeshTools.fitToUnitBox(triangleMeshes);
            TriangleMeshTools.placeOnXZPlane(triangleMeshes);

            ITriangleMesh cannonMesh = TriangleMeshTools.unite(triangleMeshes);
            cannon = new TriangleMeshNode(cannonMesh);
            cannon.setRenderNormals(PER_TRIANGLE_NORMAL);

            cannonMarker.addChild(cannon);

            // Target
            VuforiaMarkerNode targetMarker = new VuforiaMarkerNode("campus");
            rootNode.addChild(targetMarker);

            triangleMeshes = reader.read("meshes/tree01.obj");
            TriangleMeshTools.fitToUnitBox(triangleMeshes);
            TriangleMeshTools.placeOnXZPlane(triangleMeshes);

            ITriangleMesh targetMesh = TriangleMeshTools.unite(triangleMeshes);
            target = new TriangleMeshNode(targetMesh);
            target.setRenderNormals(PER_TRIANGLE_NORMAL);
            targetScale = new ScaleNode(1);
            targetScale.addChild(target);

            targetMarker.addChild(targetScale);
        } catch (Throwable t) {
            Log.e("DEBUG", "" + t.getMessage(), t);
        }
    }

    @Override
    public void onTimerTick(int counter) {
        try {
            boolean hit = false;
            for (int i = projectiles.size() - 1; i >= 0; i--) {
                Projectile p = projectiles.get(i);
                p.translationNode.setTranslation(p.particle.update(TICK_MOVE));
                if (CollisionHelper.collide(p.bullet.getBoundingBox(), p.bullet.getTransformation(), target.getBoundingBox(), target.getTransformation())) {
                    hit = true;
                    projectiles.remove(i);
                    cannonMarker.removeChild(p.translationNode);
                } else if (p.t > 1000) {
                    projectiles.remove(i);
                    cannonMarker.removeChild(p.translationNode);
                } else {
                    p.t += TICK_MOVE;
                }
            }

            if (hit) {
                targetScale.setScale(0.3);
            } else {
                targetScale.setScale(1.0);
            }
        } catch (Throwable t) {
            Log.e("DEBUG", "" + t.getMessage(), t);
        }
    }

    @Override
    public void onSceneRedraw() {
    }

    private void shoot() {
        try {
            Vector initialPosition = cannon.getBoundingBox().getCenter()
                    .add(new Vector(0, -0.06, -0.2));
            Vector initialVelocity = new Vector(0, 1, -5);
            Particle particle = new Particle(initialPosition, initialVelocity, 1);
            SphereNode bullet = new SphereNode(PROJECTILE_RADIUS, 100);
            TranslationNode initialTranslation = new TranslationNode(initialPosition);
            initialTranslation.addChild(bullet);
            TranslationNode bulletTranslation = new TranslationNode(initialPosition);
            bulletTranslation.addChild(initialTranslation);
            cannonMarker.addChild(bulletTranslation);
            projectiles.add(new Projectile(particle, bulletTranslation, bullet));
        } catch (Throwable t) {
            Log.e("DEBUG", "" + t.getMessage(), t);
        }
    }

    private static class Projectile {
        private final Particle particle;
        private final TranslationNode translationNode;
        private final SphereNode bullet;
        private double t = 0;

        private Projectile(Particle particle, TranslationNode translationNode, SphereNode bullet) {
            this.particle = particle;
            this.translationNode = translationNode;
            this.bullet = bullet;
        }
    }
}
