package org.jurassicraft.server.world;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.tree.TreeType;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.base.EntityHandler;
import org.jurassicraft.server.period.EnumTimePeriod;

import java.util.List;
import java.util.Random;

public class WorldGenerator implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.provider.getDimensionId() == 0)
        {
            generateOverworld(world, random, chunkX * 16, chunkZ * 16);
        }
    }

    public void generateOverworld(World world, Random random, int chunkX, int chunkZ)
    {
        for (int i = 0; i < world.getHorizon() * 0.15625; i++)
        {
            int randPosX = chunkX + random.nextInt(16);
            int randPosZ = chunkZ + random.nextInt(16);
            int randPosY = random.nextInt(Math.max(0, world.getTopSolidOrLiquidBlock(new BlockPos(randPosX, 0, randPosZ)).getY() - 10));

            generatePetrifiedTree(world, TreeType.values()[random.nextInt(TreeType.values().length)], randPosX, randPosY, randPosZ, random);
        }

        for (int i = 0; i < 32; i++)
        {
            int randPosX = chunkX + random.nextInt(16);
            int randPosY = random.nextInt(64);
            int randPosZ = chunkZ + random.nextInt(16);

            EnumTimePeriod period = null;

            for (EnumTimePeriod p : EnumTimePeriod.values())
            {
                if (randPosY < EnumTimePeriod.getEndYLevel(p) && randPosY > EnumTimePeriod.getStartYLevel(p))
                {
                    period = p;

                    break;
                }
            }

            if (period != null)
            {
                randPosY += random.nextInt(8) - 4;

                List<Dinosaur> dinos = EntityHandler.INSTANCE.getDinosaursFromPeriod(period);

                if (dinos != null && dinos.size() > 0)
                {
                    Dinosaur dinosaur = dinos.get(random.nextInt(dinos.size()));

                    if (dinosaur.shouldRegister())
                    {
                        int meta = BlockHandler.INSTANCE.getMetadata(dinosaur);

                        new WorldGenMinable(BlockHandler.INSTANCE.getFossilBlock(dinosaur).getStateFromMeta(meta), 5).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
                    }
                }
            }
        }
        generateOre(world, chunkX, chunkZ, 64, 128, 3, BlockHandler.INSTANCE.plant_fossil.getDefaultState(), random, BlockHelper.forBlock(Blocks.coal_ore));

        Predicate<IBlockState> defaultPredicate = BlockHelper.forBlock(Blocks.stone);

        generateOre(world, chunkX, chunkZ, 20, 8, 3, BlockHandler.INSTANCE.amber_ore.getDefaultState(), random, defaultPredicate);
        generateOre(world, chunkX, chunkZ, 64, 8, 1, BlockHandler.INSTANCE.ice_shard.getDefaultState(), random, defaultPredicate);
        generateOre(world, chunkX, chunkZ, 128, 32, 10, BlockHandler.INSTANCE.gypsum_stone.getDefaultState(), random, defaultPredicate);
    }

    public void generateOre(World world, int chunkX, int chunkZ, int minHeight, int veinsPerChunk, int veinSize, IBlockState state, Random random, Predicate<IBlockState> predicate)
    {
        WorldGenMinable worldGenMinable = new WorldGenMinable(state, veinSize, predicate);

        for (int i = 0; i < veinsPerChunk; i++)
        {
            int randPosX = chunkX + random.nextInt(16);
            int randPosY = random.nextInt(minHeight);
            int randPosZ = chunkZ + random.nextInt(16);

            worldGenMinable.generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
        }
    }

    private void generatePetrifiedTree(World world, TreeType treeType, int x, int y, int z, Random rand)
    {
        float rotX = (float) (rand.nextDouble() * 360.0F);
        float rotY = (float) (rand.nextDouble() * 360.0F) - 180.0F;

        IBlockState state = BlockHandler.INSTANCE.petrified_logs.get(treeType).getDefaultState();

        float horizontal = MathHelper.cos(rotX * (float) Math.PI / 180.0F);
        float vertical = MathHelper.sin(rotX * (float) Math.PI / 180.0F);

        float xOffset = -MathHelper.sin(rotY * (float) Math.PI / 180.0F) * horizontal;
        float yOffset = MathHelper.cos(rotY * (float) Math.PI / 180.0F) * horizontal;

        for (int i = 0; i < rand.nextInt(7) + 3; i++)
        {
            int blockX = x + Math.round(xOffset * i);
            int blockY = y + Math.round(vertical * i);
            int blockZ = z + Math.round(yOffset * i);

            if (blockY > 0 && blockY < 256)
            {
                BlockPos pos = new BlockPos(blockX, blockY, blockZ);
                Block previousBlock = world.getBlockState(pos).getBlock();

                if (previousBlock != Blocks.bedrock)
                {
                    world.setBlockState(pos, state);
                }
            }
        }
    }
}