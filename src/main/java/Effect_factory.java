public class Effect_factory {
    public Effect getinstanceof(int type,int id)
    {
        if(type==1)
        {
            return new Player_effect(id);
        }
        else
            return new Cell_effect(id);
    }
}
