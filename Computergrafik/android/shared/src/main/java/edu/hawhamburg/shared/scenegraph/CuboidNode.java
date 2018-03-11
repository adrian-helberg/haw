package edu.hawhamburg.shared.scenegraph;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

import edu.hawhamburg.shared.math.AxisAlignedBoundingBox;
import edu.hawhamburg.shared.math.Matrix;
import edu.hawhamburg.shared.math.Vector;
import edu.hawhamburg.shared.rendering.RenderVertex;
import edu.hawhamburg.shared.rendering.Shader;
import edu.hawhamburg.shared.rendering.ShaderAttributes;
import edu.hawhamburg.shared.rendering.VertexBufferObject;

/**
 * CuboidNode component
 * @author Adrian Helberg
 */
public class CuboidNode extends LeafNode {

    private VertexBufferObject vbo = new VertexBufferObject();

    private double length;
    private double width;
    private double height;

    public CuboidNode(double l, double w, double h) {
        length = l;
        width = w;
        height = h;
        createVbo();
    }

    private void createVbo() {
        List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

        Vector p0 = new Vector(-width, -height, -length);
        Vector p1 = new Vector(width, -height, -length);
        Vector p2 = new Vector(width, height, -length);
        Vector p3 = new Vector(-width, height, -length);
        Vector p4 = new Vector(-width, -height, length);
        Vector p5 = new Vector(width, -height, length);
        Vector p6 = new Vector(width, height, length);
        Vector p7 = new Vector(-width, height, length);
        Vector n0 = new Vector(0, 0, -1);
        Vector n1 = new Vector(1, 0, 0);
        Vector n2 = new Vector(0, 0, 1);
        Vector n3 = new Vector(-1, 0, 0);
        Vector n4 = new Vector(0, 1, 0);
        Vector n5 = new Vector(0, -1, 0);
        Vector color = new Vector(0.25, 0.75, 0.25, 1);

        AddSideVertices(renderVertices, p0, p1, p2, p3, n0, color);
        AddSideVertices(renderVertices, p1, p5, p6, p2, n1, color);
        AddSideVertices(renderVertices, p4, p7, p6, p5, n2, color);
        AddSideVertices(renderVertices, p0, p3, p7, p4, n3, color);
        AddSideVertices(renderVertices, p2, p6, p7, p3, n4, color);
        AddSideVertices(renderVertices, p5, p1, p0, p4, n5, color);

        vbo.setup(renderVertices, GLES20.GL_TRIANGLES);
    }

    private void AddSideVertices(List<RenderVertex> renderVertices, Vector p0,
                                 Vector p1, Vector p2, Vector p3, Vector normal, Vector color) {
        renderVertices.add(new RenderVertex(p3, normal, color));
        renderVertices.add(new RenderVertex(p2, normal, color));
        renderVertices.add(new RenderVertex(p0, normal, color));
        renderVertices.add(new RenderVertex(p2, normal, color));
        renderVertices.add(new RenderVertex(p1, normal, color));
        renderVertices.add(new RenderVertex(p0, normal, color));
    }

    @Override
    public void drawGL(RenderMode mode, Matrix modelMatrix) {
        ShaderAttributes.getInstance().setShaderModeParameter(Shader.ShaderMode.PHONG);
        if (mode == RenderMode.REGULAR) {
            vbo.draw();
        }
    }

    @Override
    public AxisAlignedBoundingBox getBoundingBox() {
        return new AxisAlignedBoundingBox(new Vector(-width, -height, -height),
                new Vector(width, height, height));
    }
}
