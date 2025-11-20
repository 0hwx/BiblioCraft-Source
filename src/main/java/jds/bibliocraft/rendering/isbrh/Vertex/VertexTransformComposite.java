package jds.bibliocraft.rendering.isbrh.Vertex;

import jds.bibliocraft.utils.math.Vector.Vector3f;
import jds.bibliocraft.utils.math.Vector.Vector3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class VertexTransformComposite implements VertexTransform {

    // Use a thread-safe list for read-heavy, write-rare operations
    public final List<VertexTransform> xforms = new CopyOnWriteArrayList<>();

    public VertexTransformComposite(VertexTransform... xforms) {
        Collections.addAll(this.xforms, xforms);
    }

    public VertexTransformComposite(List<VertexTransform> xforms) {
        this.xforms.addAll(xforms);
    }


    public void add(VertexTransform xform) {
        this.xforms.add(xform);
    }

    @Override
    public void apply(Vertex vertex) {
        for (VertexTransform xform : xforms) {
            xform.apply(vertex);
        }
    }

    @Override
    public void apply(Vector3d vec) {
        for (VertexTransform xform : xforms) {
            xform.apply(vec);
        }
    }

    @Override
    public void applyToNormal(Vector3f vec) {
        for (VertexTransform xform : xforms) {
            xform.applyToNormal(vec);
        }
    }

}
