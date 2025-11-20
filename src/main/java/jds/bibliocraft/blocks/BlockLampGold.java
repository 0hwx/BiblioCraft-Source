package jds.bibliocraft.blocks;

import jds.bibliocraft.blocks.base.BiblioLightBlock;
import jds.bibliocraft.helpers.EnumMetalType;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.tileentities.base.BiblioLightTileEntity;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;


public class BlockLampGold extends BiblioLightBlock
{
	public static String name = "LampGold";
	public static BlockLampGold instance = new BlockLampGold();

	public BlockLampGold()
	{
		super(name);
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
//		List<String> modelParts = new ArrayList<String>();
//		switch (tile.getVertPosition())
//		{
//			case CEILING:
//			{
//				modelParts.add("ceilingPlate");
//				modelParts.add("lampTopCeiling");
//				break;
//			}
//			case WALL:
//			{
//				modelParts.add("wallPlate");
//				modelParts.add("lampTopWall");
//				break;
//			}
//			case FLOOR:
//			{
//				modelParts.add("baseFloor");
//				modelParts.add("lampTopFloor");
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
		AxisAlignedBB output = this.getBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		TileEntity pretile = world.getTileEntity(x, y, z);
    	if (pretile != null && pretile instanceof BiblioLightTileEntity)
    	{
    		BiblioLightTileEntity tile = (BiblioLightTileEntity)pretile;
    		EnumVertPosition style = tile.getVertPosition();
    		switch (style)
    		{
    			case FLOOR:{output = this.getBlockBounds(0.18F, 0.0F, 0.18F, 0.82F, 1.0F, 0.82F); break;}
    			case WALL:{output = this.getBlockBounds(0.1F, 0.05F, 0.1F, 0.9F, 0.55F, 0.9F); break;}
    			case CEILING:{output = this.getBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F); break;}
    			default:{output = this.getBlockBounds(0.18F, 0.0F, 0.18F, 0.82F, 1.0F, 0.82F); break;}
    		}
    	}
    	return output;
	}
}
