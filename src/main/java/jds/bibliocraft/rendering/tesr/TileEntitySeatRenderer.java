package jds.bibliocraft.rendering.tesr;

import jds.bibliocraft.blocks.BlockSeat;
import jds.bibliocraft.models.ModelSeat;
import jds.bibliocraft.states.TextureState;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntitySeat;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;


public class TileEntitySeatRenderer extends TileEntityBiblioRenderer
{

    private int meta;
    private int rotation;
    private ModelSeat seat = new ModelSeat();
    private BlockSeat block = new BlockSeat();
    private TextureState texture;

    @Override
    public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
    {
        TileEntitySeat seat = (TileEntitySeat) tile;
        TextureState textureString = new TextureState(seat.getCustomTextureString());
        textureString =  block.addAdditionTextureStateInformation(seat, textureString);
        if (seat != null)
        {
            this.meta = tile.getBlockMetadata();
            GL11.glPushMatrix();
//            switch (this.getAngle()) {
//                case WEST: // south
//                    rotation = 0;
//                    xshift -= 0.5f;
//                    zshift -= 0.5f;
//                    break;
//                case SOUTH: // east
//                    rotation = 90;
//                    xshift -= 0.5f;
//                    zshift += 0.5f;
//                    break;
//                case EAST: // north
//                    rotation = 180;
//                    xshift += 0.5f;
//                    zshift += 0.5f;
//                    break;
//                case NORTH: // west
//                    rotation = 270;
//                    xshift += 0.5f;
//                    zshift -= 0.5f;
//                    break;
//            }
            renderSeat(x, y, z, rotation, this.meta);
            GL11.glPopMatrix();
        }
    }

    public void renderSeat(double x, double y, double z, double rotate, int meta) {
        GL11.glTranslated(x, y, z);
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
//        mc.renderEngine.bindTexture(BiblioWoodRegistry.getResource(meta));
        seat.Stool();
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderSeat(0, -0.5D, 0.3D,180.0D, item.getItemDamage());
                return;
            case EQUIPPED_FIRST_PERSON:
                renderSeat(0.5D, 0, 0.5D, 90.0D, item.getItemDamage());
                return;
            case EQUIPPED:
                renderSeat(0.5D, 0, 0.5D, 0, item.getItemDamage());
                return;
            default:
                renderSeat(0, -0.5D, -0.25D, 0, item.getItemDamage());
        }
    }
}
