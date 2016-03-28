package org.jurassicraft.server.block.tree;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jurassicraft.server.api.IGrindableItem;
import org.jurassicraft.server.creativetab.TabHandler;
import org.jurassicraft.server.item.ItemHandler;
import org.jurassicraft.server.plant.PlantHandler;

import java.util.Random;

public class JCLogBlock extends BlockLog implements IGrindableItem
{
    private boolean petrified;
    private TreeType type;

    public JCLogBlock(TreeType treeType, boolean petrified)
    {
        this.setDefaultState(getBlockState().getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
        this.setHardness(2.0F);
        this.setResistance(0.5F);
        this.setStepSound(Block.soundTypeWood);
        this.setCreativeTab(TabHandler.INSTANCE.plants);
        this.petrified = petrified;
        this.type = treeType;

        String name = treeType.name().toLowerCase() + "_log";

        if (petrified)
        {
            name += "_petrified";
            this.setHarvestLevel("pickaxe", 2);
            this.setHardness(4.0F);
            this.setResistance(4.0F);
        }

        this.setUnlocalizedName(name);
    }

    public TreeType getType()
    {
        return type;
    }

    public boolean isPetrified()
    {
        return petrified;
    }

    @Override
    public Material getMaterial()
    {
        return petrified ? Material.rock : super.getMaterial();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(LOG_AXIS, EnumAxis.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(LOG_AXIS).ordinal();
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, LOG_AXIS);
    }

    @Override
    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public boolean isGrindable(ItemStack stack)
    {
        return isPetrified();
    }

    @Override
    public ItemStack getGroundItem(ItemStack stack, Random random)
    {
        NBTTagCompound tag = stack.getTagCompound();

        int outputType = random.nextInt(6);

        if (outputType == 5)
        {
            ItemStack output = new ItemStack(ItemHandler.INSTANCE.plant_soft_tissue, 1, PlantHandler.INSTANCE.getPlantId(type.getPlant()));
            output.setTagCompound(tag);
            return output;
        }
        else if (outputType < 3)
        {
            return new ItemStack(Items.dye, 1, 15);
        }

        return new ItemStack(Items.flint);
    }
}