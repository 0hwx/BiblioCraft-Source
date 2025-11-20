package jds.bibliocraft.rendering.tesr;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.rendering.tesr.base.BiblioTileRenderer;
import jds.bibliocraft.tileentities.TileEntityBell;
import jds.bibliocraft.utils.VanillaResource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class TESRBell extends BiblioTileRenderer<TileEntityBell> {
    private IModelCustom bell = AdvancedModelLoader.loadModel(new ResourceLocation(BiblioCraft.MODID, "models/block/desk_bell.obj"));

    @Override
    public void renderBiblioTileEntityAt(TileEntityBell tile, double x, double y, double z, float tick){
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        this.bindTexture(VanillaResource.IRON);
        bell.renderOnly("center", "up", "north", "south", "east", "west");
        this.bindTexture(VanillaResource.STONE);
        bell.renderOnly("top", "bottom");
    }


    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case ENTITY:
                GL11.glTranslated(-1.5F, 0F, -1.5F);
                break;
            case EQUIPPED:
                GL11.glTranslated(-1F, 0.5F, -1F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslated(-1F, 0.5F, -1F);
                break;
            case INVENTORY:
                GL11.glTranslated(0F, 1F, 0F);
                break;
        }
        GL11.glScaled(3F, 3F, 3F);
        TileEntityBell dummyTile = new TileEntityBell();
        this.renderBiblioTileEntityAt(dummyTile, 0, 0, 0, 0f);
    }
}
