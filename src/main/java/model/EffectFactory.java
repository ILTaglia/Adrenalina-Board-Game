package model;

public class EffectFactory {
    /**
     *
     * @param type is teh type of effect
     * @param id is teh ID of the effect
     * @return teh Effect itself
     */
    public Effect getinstanceof(int type,int id)
    {
        if(type==1)
        {
            return new PlayerEffect(id);
        }
        else
            return new CellEffect(id);
    }
}
