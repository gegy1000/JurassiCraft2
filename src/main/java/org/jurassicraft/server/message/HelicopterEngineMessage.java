package org.jurassicraft.server.message;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.server.vehicles.helicopter.HelicopterBaseEntity;

public class HelicopterEngineMessage extends AbstractMessage<HelicopterEngineMessage>
{
    private int heliID;
    private boolean engineState;

    public HelicopterEngineMessage()
    {
    }

    public HelicopterEngineMessage(int heliID, boolean engineState)
    {
        this.heliID = heliID;
        this.engineState = engineState;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, HelicopterEngineMessage message, EntityPlayer player, MessageContext messageContext)
    {
        HelicopterBaseEntity helicopter = HelicopterMessages.getHeli(player.worldObj, message.heliID);
        if (helicopter != null)
        {
            helicopter.setEngineRunning(message.engineState);
        }
    }

    @Override
    public void onServerReceived(MinecraftServer server, HelicopterEngineMessage message, EntityPlayer player, MessageContext messageContext)
    {
        HelicopterBaseEntity helicopter = HelicopterMessages.getHeli(player.worldObj, message.heliID);
        if (helicopter != null)
        {
            helicopter.setEngineRunning(message.engineState);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        heliID = buf.readInt();
        engineState = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(heliID);
        buf.writeBoolean(engineState);
    }
}