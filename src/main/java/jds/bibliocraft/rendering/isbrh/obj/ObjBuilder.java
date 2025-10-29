package jds.bibliocraft.rendering.isbrh.obj;


import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexRotation;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexRotationFacing;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexTransform;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexTransformComposite;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexTranslation;
import jds.bibliocraft.utils.math.Vector.Vector3d;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.WavefrontObject;

import java.util.ArrayList;
import java.util.List;

import static jds.bibliocraft.rendering.isbrh.obj.ObjRenderHelper.makeShiftTransform;
import static net.minecraftforge.common.util.ForgeDirection.WEST;


public class ObjBuilder {

    private Tessellator tess;
    private WavefrontObject model;
    private ObjContext context;

    public ObjBuilder(Tessellator tess) {
        this.tess = tess;
    }

    public ObjBuilder setModel(EnumObjModels modelEnum) {
        this.model = modelEnum.getModel();
        return this;
    }

    public ObjBuilder setContext(ObjContext context) {
        this.context = context;
        return this;
    }

    public ObjBuilder start() {
        tess.startDrawingQuads();
        tess.setColorOpaque_F(1, 1, 1);
        return this;
    }

    public ObjBuilder end() {
        tess.draw();
        return this;
    }

    public void renderPart(String[] partName, IIcon icon) {
        if (model == null || context == null || icon == null ) return;
        for (GroupObject group : model.groupObjects) {
            for (String part : partName) {
                if (group.name.equalsIgnoreCase(part)) {
                    renderGroup(group, icon);
                }
            }
        }
    }


    public void renderPart(String partName, IIcon icon) {
        if (model == null || context == null || icon == null ) return;
        for (GroupObject group : model.groupObjects) {
            if (group.name.equalsIgnoreCase(partName)) {
                renderGroup(group, icon);
            }
        }
    }

    private void renderGroup(GroupObject group, IIcon icon) {
        Vector3d center = new Vector3d(0.5, 0.5, 0.5);
        List<VertexTransform> transforms = new ArrayList<>();

        //        // === Handle vertical orientation ===
        if (context.vert == EnumVertPosition.CEILING) {
            // Flip upside-down around X-axis
            // todo fix
//            transforms.add(new VertexRotation(Math.PI * 2, new Vector3d(1, 0, 0), center));
        } else if (context.vert == EnumVertPosition.FLOOR) {
            transforms.add(new VertexRotation(Math.PI / 2, new Vector3d(1, 0, 0), center));
        }

        VertexRotationFacing facingRot = new VertexRotationFacing(WEST);
        facingRot.setRotation(context.facing);
        transforms.add(facingRot);

        VertexTranslation shift = makeShiftTransform(context.shift, context.facing);
        if (shift != null) transforms.add(shift);

        VertexTransformComposite transform = new VertexTransformComposite(transforms);
        ObjRenderHelper.renderWithIcon(group, icon,null, tess, context, transform, true);
    }
}
