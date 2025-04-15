package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.blocks.BlockMarkerPole;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityMarkerPole;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.common.util.ForgeDirection;

public class BiblioMeasure implements IMessage {
    int posX;
    int posY;
    int posZ;
    boolean newTest;
    int direction;

    public BiblioMeasure() {

    }

    public BiblioMeasure(int x, int y, int z, boolean newTest, int direction) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.newTest = newTest;
        this.direction = direction;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.newTest = buf.readBoolean();
        this.direction = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeBoolean(this.newTest);
        buf.writeInt(this.direction);
    }

    public static class Handler implements IMessageHandler<BiblioMeasure, IMessage> {

        @Override
        public IMessage onMessage(BiblioMeasure message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                ForgeDirection facing = ForgeDirection.getOrientation(message.direction);
                World world = player.worldObj;
                int iadj = 0;
                int jadj = 0;
                int kadj = 0;
                switch (message.direction) {
                    case 0:
                        jadj = -1;
                        break;
                    case 1:
                        jadj = 1;
                        break;
                    case 2:
                        kadj = -1;
                        break;
                    case 3:
                        kadj = 1;
                        break;
                    case 4:
                        iadj = -1;
                        break;
                    case 5:
                        iadj = 1;
                        break;
                    default:
                        iadj = 1;
                        break;
                }
                int posX = message.posX + iadj;
                int posY = message.posY + jadj;
                int posZ = message.posZ + kadj;
                if (Utils.hasPointLoaded(player, posX, posY, posZ)) {
                    if (message.newTest) {
                        if (world.isAirBlock(posX, posY, posZ)) {
                            Block st = BlockMarkerPole.instance;
                            world.setBlock(posX, posY, posZ, st);
                            TileEntityMarkerPole poleTile = (TileEntityMarkerPole) world.getTileEntity(posX, posY, posZ);
                            if (poleTile != null) {
                                poleTile.setAngle(ForgeDirection.NORTH);
                                if (facing == ForgeDirection.UP) {
                                    poleTile.setVertPosition(EnumVertPosition.FLOOR);
                                } else if (facing == ForgeDirection.DOWN) {
                                    poleTile.setVertPosition(EnumVertPosition.CEILING);
                                } else {
                                    switch (facing) {
                                        case NORTH: {
                                            facing = ForgeDirection.WEST;
                                            break;
                                        }
                                        case WEST: {
                                            facing = ForgeDirection.SOUTH;
                                            break;
                                        }
                                        case SOUTH: {
                                            facing = ForgeDirection.EAST;
                                            break;
                                        }
                                        case EAST: {
                                            facing = ForgeDirection.NORTH;
                                            break;
                                        }
                                        default:
                                            break;
                                    }
                                    poleTile.setAngle(facing);
                                    poleTile.setVertPosition(EnumVertPosition.WALL);
                                }
                                world.markBlockRangeForRenderUpdate(posX, posY, posZ, posX, posY, posZ);
                            }
                        }
                    } else {
                        // destroy block
                        if (world.getBlock(posX, posY, posZ) == BlockMarkerPole.instance) {
                            world.func_147480_a(posX, posY, posZ, false);
                        }
                    }
                }
            return null;
        }

    }
}
