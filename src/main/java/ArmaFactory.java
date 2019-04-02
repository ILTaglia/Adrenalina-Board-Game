public class ArmaFactory {
    public Arma getInstance(int id)
    {
        //Sono stati messi a caso, giusto per far capire come funziona la factory
        if(id==5)
            return new Vista();
        else
            if(id==6)
                return  new Incell();
            else
                return new Special();
    }
}
