package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.entity.EntityCandleFX;
import jds.bibliocraft.helpers.EnumMetalType;
import jds.bibliocraft.tileentities.BiblioLightTileEntity;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockLanternGold extends BiblioLightBlock
{
	public static String name = "LanternGold";
	public static BlockLanternGold instance = new BlockLanternGold();

	public BlockLanternGold()
	{
		super(name);
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
//		List<String> modelParts = new ArrayList<String>();
//		modelParts.add("lamp");
//		modelParts.add("candle");
//		modelParts.add("glass");
//		switch (tile.getVertPosition())
//		{
//			case CEILING:
//			{
//				modelParts.add("lampHanger");
//				modelParts.add("topPlate");
//				break;
//			}
//			case WALL:
//			{
//				modelParts.add("lampHanger");
//				modelParts.add("wallPlate");
//				break;
//			}
//			case FLOOR:
//			{
//				break;
//			}
//		}
//		return modelParts;
//	}

	@Override
	public void additionalLightPlacmentCommands(BiblioTileEntity biblioTile)
	{
		if (biblioTile instanceof BiblioLightTileEntity)
		{
			BiblioLightTileEntity light = (BiblioLightTileEntity)biblioTile;
			light.setLightType(EnumMetalType.GOLD);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return this.getBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.7F, 0.7F);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random rand)
    {
		Minecraft mc = Minecraft.getMinecraft();
        EntityFX candleFlame = new EntityCandleFX(worldIn, x +0.5, y +0.3, z +0.5, 0.0D, 0.001D, 0.0D);
		mc.effectRenderer.addEffect(candleFlame);
    }
}
