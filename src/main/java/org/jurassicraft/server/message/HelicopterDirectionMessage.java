package org.jurassicraft.server.message;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.timeless.unilib.utils.MutableVec3;
import org.jurassicraft.server.vehicles.helicopter.HelicopterBaseEntity;

public class HelicopterDirectionMessage extends AbstractMessage<HelicopterDirectionMessage>
{
    private int heliID;
    private MutableVec3 direction;

    public HelicopterDirectionMessage()
    {
        direction = new MutableVec3(0, 0, 0);
    }

    public HelicopterDirectionMessage(int heliID, MutableVec3 direction)
    {
        this.heliID = heliID;
        this.direction = direction;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, HelicopterDirectionMessage message, EntityPlayer player, MessageContext messageContext)
    {
        HelicopterBaseEntity helicopter = HelicopterMessages.getHeli(player.worldObj, message.heliID);
        if (helicopter != null)
        {
            helicopter.setDirection(message.direction);
        }
    }

    @Override
    public void onServerReceived(MinecraftServer server, HelicopterDirectionMessage message, EntityPlayer player, MessageContext messageContext)
    {
        HelicopterBaseEntity helicopter = HelicopterMessages.getHeli(player.worldObj, message.heliID);
        if (helicopter != null)
        {
            helicopter.setDirection(message.direction);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        heliID = buf.readInt();
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        direction.set(x, y, z);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(heliID);
        buf.writeDouble(direction.xCoord);
        buf.writeDouble(direction.yCoord);
        buf.writeDouble(direction.zCoord);
    }
}