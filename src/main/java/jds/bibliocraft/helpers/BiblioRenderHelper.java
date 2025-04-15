package jds.bibliocraft.helpers;



import net.minecraft.item.ItemStack;


public class BiblioRenderHelper
{

    public static String getBlockTextureString(ItemStack stack)
    {
    	String returnValue = "none";
    	if (stack != null)
    	{
//    		Block stackBlock = Block.getBlockFromItem(stack.getItem());
//    		ResourceLocation reloc = new ResourceLocation(stackBlock.getUnlocalizedName());
//    		RenderItem test = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
//    		returnValue = test.getParticleTexture().getIconName();
    	}
        return returnValue;
    }
}
