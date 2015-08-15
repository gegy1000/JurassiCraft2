package net.timeless.jurassicraft.common.entity;

import net.minecraft.world.World;
import net.reuxertz.ecoapi.ecology.role.ICarnivore;
import net.reuxertz.ecoapi.entity.IEntityAICreature;
import net.timeless.animationapi.AnimationAPI;
import net.timeless.animationapi.IAnimatedEntity;
import net.timeless.jurassicraft.common.entity.ai.animations.JCAutoAnimSoundBase;
import net.timeless.jurassicraft.common.entity.base.EntityDinosaurAggressive;
import net.timeless.unilib.common.animation.ChainBuffer;

public class EntityTyrannosaurus extends EntityDinosaurAggressive implements IAnimatedEntity, IEntityAICreature, ICarnivore
{
    private static final String[] hurtSounds = new String[]{"tyrannosaurus_hurt_1", "tyrannosaurus_hurt_2"};
    private static final String[] livingSounds = new String[]{"tyrannosaurus_living_1", "tyrannosaurus_living_2", "tyrannosaurus_living_3", "tyrannosaurus_living_4", "tyrannosaurus_living_5", "tyrannosaurus_living_6"};
    private static final String[] deathSounds = new String[]{"tyrannosaurus_death_1"};
    private static final String[] roarSounds = new String[]{"tyrannosaurus_roar_1"};
    private static final String[] breathSounds = new String[]{"tyrannosaurus_breath_1"};
    public ChainBuffer tailBuffer = new ChainBuffer(6);

    public EntityTyrannosaurus(World world)
    {
        super(world);

        tasks.addTask(2, new JCAutoAnimSoundBase(this, 75, 1, "jurassicraft:" + roarSounds[0]));
        tasks.addTask(2, new JCAutoAnimSoundBase(this, 75, 2, "jurassicraft:" + roarSounds[0]));
        tasks.addTask(2, new JCAutoAnimSoundBase(this, 75, 3, "jurassicraft:" + roarSounds[0]));
    }

    public String getLivingSound()
    {
        return randomSound(breathSounds);
    }

    public String getHurtSound()
    {
        return randomSound(hurtSounds);
    }

    public String getDeathSound()
    {
        return randomSound(deathSounds);
    }

    public void onUpdate()
    {
        this.tailBuffer.calculateChainSwingBuffer(68.0F, 5, 4.0F, this);
        super.onUpdate();
        if (getAnimID() == 0)
            AnimationAPI.sendAnimPacket(this, 1);
    }
}
