package org.jurassicraft.server.entity.disease;

import org.jurassicraft.server.entity.base.DinosaurEntity;

public class CancerDisease extends Disease
{
    private DinosaurEntity dinosaur;
    
    public CancerDisease(DinosaurEntity dinosaur)
    {
        super("Cancer");
        
        this.setTerminal();
        this.dinosaur = dinosaur;
    }

    @Override
    public void affects()
    {

    }

    public DinosaurEntity getDinosaur()
    {
        return dinosaur;
    }
}