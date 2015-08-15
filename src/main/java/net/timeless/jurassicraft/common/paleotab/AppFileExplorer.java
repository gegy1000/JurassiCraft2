package net.timeless.jurassicraft.common.paleotab;

import net.minecraft.nbt.NBTTagCompound;

public class AppFileExplorer extends App
{
    private String path;

    @Override
    public String getName()
    {
        return "File Explorer";
    }

    @Override
    public void update()
    {

    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        if (path != null && path.length() != 0)
        {
            nbt.setString("Path", path);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        path = nbt.getString("Path");

        if (path == null)
        {
            path = "";
        }
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    @Override
    public void init()
    {

    }
}
