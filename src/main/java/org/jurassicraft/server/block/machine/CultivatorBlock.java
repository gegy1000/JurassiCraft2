package org.jurassicraft.server.block.machine;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.server.api.ISubBlocksBlock;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.item.itemblock.CultivateItemBlock;
import org.jurassicraft.server.tileentity.CultivatorTile;

import java.util.List;

public class CultivatorBlock extends BlockContainer implements ISubBlocksBlock
{
    public static final PropertyEnum COLOR = PropertyEnum.create("color", EnumDyeColor.class);

    public CultivatorBlock(String position)
    {
        super(Material.iron);
        this.setUnlocalizedName("cultivator_" + position);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
        this.setHardness(2.0F);
        this.setResistance(5.0F);
    }

    /**
     * Get the damage value that this Block should drop
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return ((EnumDyeColor) state.getValue(COLOR)).getMetadata();
    }

    public void dropItems(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() == BlockHandler.INSTANCE.cultivate_top)
        {
            pos.add(0, -1, 0);
        }

        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof CultivatorTile)
        {
            InventoryHelper.dropInventoryItems(world, pos, (CultivatorTile) tile);
        }
    }

    /**
     * returns a subtypes of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> subtypes)
    {
        EnumDyeColor[] colors = EnumDyeColor.values();

        for (EnumDyeColor color : colors)
        {
            subtypes.add(new ItemStack(item, 1, color.getMetadata()));
        }
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass()
    {
        return CultivateItemBlock.class;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new CultivatorTile();
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    @Override
    public MapColor getMapColor(IBlockState state)
    {
        return ((EnumDyeColor) state.getValue(COLOR)).getMapColor();
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumDyeColor) state.getValue(COLOR)).getMetadata();
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] { COLOR });
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return 3;
    }
}