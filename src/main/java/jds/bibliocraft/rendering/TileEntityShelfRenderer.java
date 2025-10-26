package jds.bibliocraft.rendering;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.helpers.EnumShiftPosition;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.models.ModelShelf;
import jds.bibliocraft.rendering.isbrh.obj.EnumObjModels;
import jds.bibliocraft.rendering.isbrh.obj.ObjBuilder;
import jds.bibliocraft.rendering.isbrh.obj.ObjContext;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityShelf;
import jds.bibliocraft.utils.BiblioWoodRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class TileEntityShelfRenderer extends TileEntityBiblioRenderer {

    @Override
    public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick) {

        this.renderSlotItem(tile.getStackInSlot(0), 0.25,0.6875,0.25, 1f);
        this.renderSlotItem(tile.getStackInSlot(1), 0.75, 0.6875, 0.25, 1f);
        this.renderSlotItem(tile.getStackInSlot(2), 0.25, 0.1875, 0.25, 1f);
        this.renderSlotItem(tile.getStackInSlot(3), 0.75, 0.1875, 0.25, 1f);
    }


//    // to render block in a bigger size but not other items
//    public void isBlock(BiblioTileEntity tile) {
//        for (int i = 0; i < tile.getSizeInventory(); i++) {
//            ItemStack stack = tile.getStackInSlot(i);
//            // this is from RenderItem
//            if (stack != null && stack.getItemSpriteNumber() == 1 && stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType())) {
//                this.renderSlotItem(stack, 0.25 + (i % 2) * 0.5, 0.66 - (i / 2) * 0.49, 0.25, 1.5f);
//            } else {
//                this.renderSlotItem(stack, 0.25 + (i % 2) * 0.5, 0.66 - (i / 2) * 0.49, 0.25, 0.9f);
//            }
//        }
//    }

}
