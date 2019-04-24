package Model;

public abstract class Pow_Card extends Card {
    private int color;
    private int id;

    public void setColor(int color) {
        this.color=color;
    }
    public int getColor()
    {
        return this.color;
    }
    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return this.id;
    }
    public String getName()
    {
        if(this.id==0)
        {
            return "Mirino";
        }
        else
            if(this.id==1)
            {
                return "Raggio Cinetico";
            }
            else
                if(this.id==2)
                {
                    return "Granata Venom";
                }
                else
                {
                    return "Teletrasporto";
                }
    }
}