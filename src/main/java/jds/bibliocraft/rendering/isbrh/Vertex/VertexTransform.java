package jds.bibliocraft.rendering.isbrh.Vertex;


import jds.bibliocraft.utils.math.Vector.Vector3d;
import jds.bibliocraft.utils.math.Vector.Vector3f;


public interface VertexTransform {

    void apply(Vertex vertex);

    void apply(Vector3d vec);

    void applyToNormal(Vector3f vec);

}
