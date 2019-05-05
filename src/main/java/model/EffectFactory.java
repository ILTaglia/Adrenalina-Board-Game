package model;

public class EffectFactory {
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
