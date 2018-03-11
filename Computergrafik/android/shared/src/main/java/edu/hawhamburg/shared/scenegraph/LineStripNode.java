package edu.hawhamburg.shared.scenegraph;

import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES20;
import edu.hawhamburg.shared.math.AxisAlignedBoundingBox;
import edu.hawhamburg.shared.math.Matrix;
import edu.hawhamburg.shared.math.Vector;
import edu.hawhamburg.shared.rendering.RenderVertex;
import edu.hawhamburg.shared.rendering.Shader;
import edu.hawhamburg.shared.rendering.ShaderAttributes;
import edu.hawhamburg.shared.rendering.VertexBufferObject;

public class LineStripNode extends LeafNode {

    private final Vector start;

    private final Vector end;

    private VertexBufferObject vbo = new VertexBufferObject();

    public LineStripNode(Vector start, Vector end) {
        this.start = start;
        this.end = end;

        Vector normal = end.subtract(start).getNormalized();
        Vector color = new Vector(255. / 255, 105. / 255, 180. / 255, 1);

        List<RenderVertex> vertices = new ArrayList<>();
        vertices.add(new RenderVertex(start, normal, color));
        vertices.add(new RenderVertex(end, normal, color));
        vbo.setup(vertices, GLES20.GL_LINE_STRIP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawGL(RenderMode mode, Matrix modelMatrix) {
        ShaderAttributes.getInstance().setShaderModeParameter(Shader.ShaderMode.PHONG);
        if (mode == RenderMode.REGULAR) {
            vbo.draw();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AxisAlignedBoundingBox getBoundingBox() {
        return new AxisAlignedBoundingBox(start, end);
    }
}
