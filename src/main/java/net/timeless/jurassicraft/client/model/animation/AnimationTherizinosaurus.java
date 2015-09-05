package net.timeless.jurassicraft.client.model.animation;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.timeless.animationapi.client.Animator;
import net.timeless.animationapi.client.JabelarAnimationHelper;
import net.timeless.jurassicraft.client.model.ModelDinosaur;
import net.timeless.jurassicraft.common.dinosaur.Dinosaur;
import net.timeless.jurassicraft.common.dinosaur.DinosaurTherizinosaurus;
import net.timeless.jurassicraft.common.entity.EntityTherizinosaurus;
import net.timeless.jurassicraft.common.entity.base.EntityDinosaur;
import net.timeless.unilib.client.model.json.IModelAnimator;
import net.timeless.unilib.client.model.json.ModelJson;
import net.timeless.unilib.client.model.tools.MowzieModelRenderer;

@SideOnly(Side.CLIENT)
public class AnimationTherizinosaurus implements IModelAnimator
{
    /*
     * Change the following fields for your custom dinosaur
     */
    protected static final Dinosaur theDinosaur = new DinosaurTherizinosaurus(); // do I need to get specific instance, I don't think so

    // Tell the code where your tabula model assets are
    // the first one must be your "default" pose (i.e one that is used at spawn time)
    protected static final String[] modelAssetPathArray = new String[] {
            "/assets/jurassicraft/models/entities/therizinosaurus/therizinosaurus_default",
            "/assets/jurassicraft/models/entities/therizinosaurus/therizinosaurus_look_left",
            "/assets/jurassicraft/models/entities/therizinosaurus/therizinosaurus_look_right",
            "/assets/jurassicraft/models/entities/therizinosaurus/therizinosaurus_rearing",
            "/assets/jurassicraft/models/entities/therizinosaurus/therizinosaurus_flap",
    };

    // Tell the code the names of all your tabula model parts
    // NOTE: all the models must use exactly same number and names of parts
    protected static final String[] partNameArray = new String[] {
        "Body main", "Body main 1", "Body hips", 
        "Right Thigh", "Right Calf 1", "Right Calf 2", "Foot Right",
        "Left Thigh", "Left Calf 1", "Left Calf 2", "Foot Left",
        "Tail 1", "Tail 2", "Tail 2 feathers", 
        "Tail 3", "Tail 3 feathers", "Tail 3 feathers r", "Tail 3 feathers l",
        "Tail 4", "Tail 4 feathers", "Tail 4 feathers r", "Tail 4 feathers l",
        "Tail 5", "Tail 5 feathers", "Tail 5 feathers r", "Tail 5 feathers l",
        "Tail 6", "Tail 6 feathers", "Tail 6 feathers r", "Tail 6 feathers l",
        "Neck base", "Neck 1", "Neck 2", "Neck 3", 
        "Neck 4", "Neck 4 feathers", "Neck 4 feathers r", "Neck 4 feathers l",
        "Neck 5", "Neck 5 feathers", "Neck 5 feathers r", "Neck 5 feathers l",
        "Neck 6", "Neck 6 feathers", "Neck 6 feathers r", "Neck 6 feathers l",
        "Neck 7", "Neck 7 feathers", "Neck 7 feathers r", "Neck 7 feathers l",
        "Head", "Snout", "Snout roof", "Lower Jaw", "Upper Jaw", 
        "Body shoulders", "Lower Arm Right", "Lower Arm Right 1",
        "Right hand", "Arm right feathers", "Right finger 1", "RF1 mid", "RF1 end", 
        "Right finger 2", "RF2 mid", "RF2 end", "Right finger 3", "RF3 mid", "RF3 end",
        "Lower Arm LEFT", "Lower Arm LEFT 1", 
        "Left hand", "Arm left feathers", "Left finger 1", "LF1 mid", "LF1 end", 
        "Left finger 2", "LF1 end", "LF2 end", "Left finger 3", "LF3 mid", "LF3 mid"
    };

    /* 
     * Define your animation sequence here
     * First element is target pose model index (i.e. order of model assets listed in
     * modelAssetPaths array above),
     * Second element is the number of ticks it should take to tween to that pose
     */
    protected static int[][] sequenceLookLeft = new int[][] {
        {0, 500}, {1, 100}, {1, 80}, {0, 100}
    };
    
    protected static int[][] sequenceLookRight = new int[][] {
        {0, 200}, {2, 100}, {2, 80}, {0, 100}
    };
    
    protected static int[][] sequenceRearing = new int[][] {
        {0, 200}, {3, 100}, {3, 80}, {0, 100}
    };
    
    protected static int[][] sequenceFlapping = new int[][] {
        {0, 200}, {4, 40}, {3, 20}, {4, 20}, {3, 20}, {3, 20}, {3, 20},
        {4, 20}, {3, 20}, {4, 20}, {3, 20}, {4, 20}, {3, 20}, {4, 20}, {3, 20}, 
        {4, 20}, {3, 20}, {4, 20}, {3, 20}, {4, 20}, {3, 20}, {4, 20}, {0, 100}
    };

    protected static int[][][] arrayOfSequences = new int[][][] {
    	sequenceLookLeft,
    	sequenceLookRight,
    	sequenceRearing,
    	sequenceFlapping
    };

    // maps each entity with its current animation 
    protected HashMap<Integer, JabelarAnimationHelper> animationInstanceToEntityMap = new HashMap<Integer, JabelarAnimationHelper>();

    // need boolean to indicate first tick run because need to copy between static and instance fields
    protected boolean isFirstTick = true;
    
    // cast model and entity to JurassiCraft2 classes
    @Override
    public void setRotationAngles(ModelJson parModel, float f, float f1, float rotation, float rotationYaw, float rotationPitch, float partialTicks, Entity parEntity)
    {
        setRotationAngles((ModelDinosaur)parModel, f, f1, rotation, rotationYaw, rotationPitch, partialTicks, (EntityTherizinosaurus)parEntity);
    }

    /*
     * You should NOT change anything in this method! Models and tween sequences are defined
     * in the field initializations at the top of this class' code
     */
    protected void setRotationAngles(ModelDinosaur parModel, float f, float f1, float rotation, float rotationYaw, float rotationPitch, float partialTicks, EntityTherizinosaurus parEntity)    
    {
        updateCurrentAnimationIfNewEntity(parModel, parEntity);

        animationInstanceToEntityMap.get(parEntity.getEntityId()).performJabelarAnimations(parModel, f, f1, rotation, rotationYaw, rotationPitch, partialTicks, parEntity);

        // you can still add chain, walk, bob, etc.
        performMowzieAnimations(parModel, f, f1, rotation, rotationYaw, rotationPitch, partialTicks, parEntity);
    }
    
    protected void updateCurrentAnimationIfNewEntity(ModelDinosaur parModel, EntityDinosaur parEntity)
    {
        // add entry to hashmap if new entity
        if (!animationInstanceToEntityMap.containsKey(parEntity.getEntityId()))
        {
            // DEBUG
            System.out.println("Adding entity to hashmap with id = "+parEntity.getEntityId());
            animationInstanceToEntityMap.put(parEntity.getEntityId(), (new JabelarAnimationHelper(parModel, modelAssetPathArray, partNameArray, arrayOfSequences, true, true)));
        }
    }
    
 protected void performMowzieAnimations(ModelDinosaur parModel, float f, float f1, float rotation, float rotationYaw, float rotationPitch, float partialTicks, EntityTherizinosaurus parEntity)
 {
     Animator animator = parModel.animator;

     MowzieModelRenderer rightThigh = parModel.getCube("Right Thigh");
     MowzieModelRenderer bodyHips = parModel.getCube("Body hips");
     MowzieModelRenderer rightCalf1 = parModel.getCube("Right Calf 1");
     MowzieModelRenderer rightCalf2 = parModel.getCube("Right Calf 2");
     MowzieModelRenderer footRight = parModel.getCube("Foot Right");
     MowzieModelRenderer tail1 = parModel.getCube("Tail 1");
     MowzieModelRenderer bodyMain = parModel.getCube("Body main");
     MowzieModelRenderer tail2 = parModel.getCube("Tail 2");
     MowzieModelRenderer tail3 = parModel.getCube("Tail 3");
     MowzieModelRenderer tail2Feathers = parModel.getCube("Tail 2 feathers");
     MowzieModelRenderer tail4 = parModel.getCube("Tail 4");
     MowzieModelRenderer tail3Feathers = parModel.getCube("Tail 3 feathers");
     MowzieModelRenderer tail5 = parModel.getCube("Tail 5");
     MowzieModelRenderer tail4Feathers = parModel.getCube("Tail 4 feathers");
     MowzieModelRenderer tail6 = parModel.getCube("Tail 6");
     MowzieModelRenderer tail5Feathers = parModel.getCube("Tail 5 feathers");
     MowzieModelRenderer tail6Feathers = parModel.getCube("Tail 6 feathers");
     MowzieModelRenderer tail6FeathersR = parModel.getCube("Tail 6 feathers r");
     MowzieModelRenderer tail6FeathersL = parModel.getCube("Tail 6 feathers l");
     MowzieModelRenderer tail5FeathersR = parModel.getCube("Tail 5 feathers r");
     MowzieModelRenderer tail5FeathersL = parModel.getCube("Tail 5 feathers l");
     MowzieModelRenderer tail4FeathersR = parModel.getCube("Tail 4 feathers r");
     MowzieModelRenderer tail4FeathersL = parModel.getCube("Tail 4 feathers l");
     MowzieModelRenderer tail3FeathersR = parModel.getCube("Tail 3 feathers r");
     MowzieModelRenderer tail3FeathersL = parModel.getCube("Tail 3 feathers l");
     MowzieModelRenderer bodyShoulders = parModel.getCube("Body shoulders");
     MowzieModelRenderer bodyMain1 = parModel.getCube("Body main 1");
     MowzieModelRenderer neckBase = parModel.getCube("Neck base");
     MowzieModelRenderer neck1 = parModel.getCube("Neck 1");
     MowzieModelRenderer neck2 = parModel.getCube("Neck 2");
     MowzieModelRenderer neck3 = parModel.getCube("Neck 3");
     MowzieModelRenderer neck4 = parModel.getCube("Neck 4");
     MowzieModelRenderer neck5 = parModel.getCube("Neck 5");
     MowzieModelRenderer neck4feathers = parModel.getCube("Neck 4 feathers");
     MowzieModelRenderer neck6 = parModel.getCube("Neck 6");
     MowzieModelRenderer neck5Feathers = parModel.getCube("Neck 5 feathers");
     MowzieModelRenderer neck7 = parModel.getCube("Neck 7");
     MowzieModelRenderer neck6Feathers = parModel.getCube("Neck 6 feathers");
     MowzieModelRenderer head = parModel.getCube("Head");
     MowzieModelRenderer neck7Feathers = parModel.getCube("Neck 7 feathers");
     MowzieModelRenderer snout = parModel.getCube("Snout");
     MowzieModelRenderer lowerJaw = parModel.getCube("Lower Jaw");
     MowzieModelRenderer snoutRoof = parModel.getCube("Snout roof");
     MowzieModelRenderer upperJaw = parModel.getCube("Upper Jaw");
     MowzieModelRenderer neck7FeathersR = parModel.getCube("Neck 7 feathers r");
     MowzieModelRenderer neck7FeathersL = parModel.getCube("Neck 7 feathers l");
     MowzieModelRenderer neck6FeathersR = parModel.getCube("Neck 6 feathers r");
     MowzieModelRenderer neck6FeathersL = parModel.getCube("Neck 6 feathers l");
     MowzieModelRenderer neck5FeathersR = parModel.getCube("Neck 5 feathers r");
     MowzieModelRenderer neck5FeathersL = parModel.getCube("Neck 5 feathers l");
     MowzieModelRenderer neck4FeathersR = parModel.getCube("Neck 4 feathers r");
     MowzieModelRenderer neck4FeathersL = parModel.getCube("Neck 4 feathers l");
     MowzieModelRenderer lowerArmRight = parModel.getCube("Lower Arm Right");
     MowzieModelRenderer lowerArmRight1 = parModel.getCube("Lower Arm Right 1");
     MowzieModelRenderer rightHand = parModel.getCube("Right hand");
     MowzieModelRenderer armRightFeathers = parModel.getCube("Arm right feathers");
     MowzieModelRenderer rightFinger1 = parModel.getCube("Right finger 1");
     MowzieModelRenderer rightFinger2 = parModel.getCube("Right finger 2");
     MowzieModelRenderer righFinger3 = parModel.getCube("Right finger 3");
     MowzieModelRenderer rF1mid = parModel.getCube("RF1 mid");
     MowzieModelRenderer rF1end = parModel.getCube("RF1 end");
     MowzieModelRenderer rF2mid = parModel.getCube("RF2 mid");
     MowzieModelRenderer rF2end = parModel.getCube("RF2 end");
     MowzieModelRenderer rF3mid = parModel.getCube("RF3 mid");
     MowzieModelRenderer rF3end = parModel.getCube("RF3 end");
     MowzieModelRenderer lowerArmLeft = parModel.getCube("Lower Arm LEFT");
     MowzieModelRenderer lowerArmLeft1 = parModel.getCube("Lower Arm LEFT 1");
     MowzieModelRenderer leftHand = parModel.getCube("Left hand");
     MowzieModelRenderer armLeftFeathers = parModel.getCube("Arm left feathers");
     MowzieModelRenderer leftfinger1 = parModel.getCube("Left finger 1");
     MowzieModelRenderer leftfinger2 = parModel.getCube("Left finger 2");
     MowzieModelRenderer leftfinger3 = parModel.getCube("Left finger 3");
     MowzieModelRenderer lF1mid = parModel.getCube("LF1 mid");
     MowzieModelRenderer lF1end = parModel.getCube("LF1 end");
     MowzieModelRenderer lF2mid = parModel.getCube("LF1 end");
     MowzieModelRenderer lF2end = parModel.getCube("LF2 end");
     MowzieModelRenderer lF3mid = parModel.getCube("LF3 mid");
     MowzieModelRenderer lF3end = parModel.getCube("LF3 mid");
     MowzieModelRenderer leftThigh = parModel.getCube("Left Thigh");
     MowzieModelRenderer leftCalf1 = parModel.getCube("Left Calf 1");
     MowzieModelRenderer leftCalf2 = parModel.getCube("Left Calf 2");
     MowzieModelRenderer footLeft = parModel.getCube("Foot Left");

     MowzieModelRenderer[] neck = new MowzieModelRenderer[] { head, neck7, neck6, neck5, neck4, neck3, neck2, neck1, neckBase, bodyShoulders };
     MowzieModelRenderer[] tail = new MowzieModelRenderer[] { tail1, tail2, tail3, tail4, tail5, tail6 };
     MowzieModelRenderer[] armLeft = new MowzieModelRenderer[] { lowerArmLeft, lowerArmLeft1, leftHand};
     MowzieModelRenderer[] armRight = new MowzieModelRenderer[] { lowerArmRight, lowerArmRight1, rightHand };

     int frame = parEntity.ticksExisted;

     float globalSpeed = 0.05F;
     float globalDegree = 0.06F;
//     float globalHeight = 2F;
//     float frontOffset = -1.35f;

     //The tail must always be up when the neck is down
     float speed = 0.75F;
     float height = 3F;

     parModel.bob(bodyHips, 1F * speed, height, false, f, f1);
     parModel.flap(bodyHips, 0.5F*speed, 0.5F, false, 0, 0, f, f1);
     parModel.flap(bodyMain, 0.5F*speed, 0.1F, true, 0, 0, f, f1);
     parModel.flap(bodyShoulders, 0.5F*speed, 0.4F, true, 0, 0, f, f1);
     parModel.flap(tail1, 0.5F*speed, 0.2F, true, 0, 0, f, f1);
     parModel.flap(tail3, 0.5F*speed, 0.2F, true, 0, 0, f, f1);
     parModel.flap(tail5, 0.5F*speed, 0.1F, true, 0, 0, f, f1);
     parModel.bob(leftThigh, 1F * speed, height, false, f, f1);
     parModel.bob(rightThigh, 1F * speed, height, false, f, f1);
     parModel.walk(bodyShoulders, 1F * speed, 0.2F, true, 1, 0, f, f1);
     parModel.walk(bodyMain1, 1F * speed, 0.2F, false, 0.5F, 0, f, f1);

     parModel.walk(leftThigh, 0.5F * speed, 0.7F, false, 3.14F, 0.2F, f, f1);
     parModel.walk(leftCalf1, 0.5F * speed, 0.6F, false, 1.5F, 0.3F, f, f1);
     parModel.walk(leftCalf2, 0.5F * speed, 0.8F, false, -1F, -0.1F, f, f1);
     parModel.walk(footLeft, 0.5F * speed, 1.5F, true, -1F, 1F, f, f1);

     parModel.walk(rightThigh, 0.5F * speed, 0.7F, true, 3.14F, 0.2F, f, f1);
     parModel.walk(rightCalf1, 0.5F * speed, 0.6F, true, 1.5F, 0.3F, f, f1);
     parModel.walk(rightCalf2, 0.5F * speed, 0.8F, true, -1F, -0.1F, f, f1);
     parModel.walk(footRight, 0.5F * speed, 1.5F, false, -1F, 1F, f, f1);

     parModel.chainSwing(tail, 0.5F * speed, -0.02F, 2, f, f1);
     parModel.chainWave(tail, 1F * speed, -0.02F, 2.5F, f, f1);
     parModel.chainSwing(neck, 0.5F * speed, 0.02F, 2, f, f1);
     parModel.chainWave(neck, 1F * speed, 0.02F, 2.5F, f, f1);
//     parModel.chainWave(bodyParts, 1F * speed, -0.1F, 4, f, f1);

     parModel.chainWave(armRight, 1F * speed, -0.3F, 4, f, f1);
     parModel.chainWave(armLeft, 1F * speed, -0.3F, 4, f, f1);

     // Idling
     parModel.chainWave(tail, 0.1F, 0.02F, 2, frame, 1F);
     parModel.chainWave(neck, 0.1F, 0.03F, 2, frame, 1F);
//     parModel.chainWave(bodyParts, 0.1F, -0.03F, 5, frame, 1F);
     parModel.chainWave(armRight, 0.1F, -0.1F, 4, frame, 1F);
     parModel.chainWave(armLeft, 0.1F, -0.1F, 4, frame, 1F);
     
     parEntity.tailBuffer.applyChainSwingBuffer(tail);

     }
 

     public Dinosaur getDinosaur()
     {
         return theDinosaur;
     }

}