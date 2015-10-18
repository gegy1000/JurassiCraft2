package org.jurassicraft.common.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import org.jurassicraft.common.entity.base.EntityDinosaur;

public class EntityAIJCLookIdle extends EntityAIBase
{
    /**
     * The entity that is looking idle.
     */
    private EntityDinosaur idleEntity;

    /**
     * X offset to look at
     */
    private double lookX;

    /**
     * Z offset to look at
     */
    private double lookZ;

    /**
     * A decrementing tick that stops the entity from being idle once it reaches 0.
     */
    private int idleTime;

    /**
     * How fast to turn on the Y axis (yaw)
     */
    private float turnSpeedYaw;

    /**
     * How fast to turn on the X axis (pitch)
     */
    private float turnSpeedPitch;

    public EntityAIJCLookIdle(EntityDinosaur entity, float turnSpeedYaw, float turnSpeedPitch)
    {
        this.idleEntity = entity;
        this.turnSpeedYaw = turnSpeedYaw;
        this.turnSpeedPitch = turnSpeedPitch;

        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.idleEntity.getRNG().nextFloat() < 0.02F && !this.idleEntity.isCarcass();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.idleTime >= 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        double d0 = (Math.PI * 2D) * this.idleEntity.getRNG().nextDouble();
        this.lookX = Math.cos(d0);
        this.lookZ = Math.sin(d0);
        this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        --this.idleTime;
        this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + (double) this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, turnSpeedYaw, turnSpeedPitch);
    }
}