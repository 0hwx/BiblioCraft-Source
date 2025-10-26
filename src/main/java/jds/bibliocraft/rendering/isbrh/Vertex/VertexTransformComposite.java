package jds.bibliocraft.rendering.isbrh.Vertex;

import jds.bibliocraft.utils.math.Vector.Vector3f;
import jds.bibliocraft.utils.math.Vector.Vector3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class VertexTransformComposite implements VertexTransform {

    public final List<VertexTransform> xforms = new ArrayList<>();

    public VertexTransformComposite(VertexTransform... xforms) {
        Collections.addAll(this.xforms, xforms);
    }

    public VertexTransformComposite(List<VertexTransform> xforms) {
        this.xforms.addAll(xforms);
    }

//    VertexTransformComposite(Collection<VertexTransform> xformsIn) {
//        xforms = new VertexTransform[xformsIn.size()];
//        int i = 0;
//        for (VertexTransform xform : xformsIn) {
//            xforms[i] = xform;
//            i++;
//        }
//    }

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
