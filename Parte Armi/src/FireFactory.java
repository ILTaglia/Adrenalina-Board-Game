public class FireFactory {
    public Fire getInstance(int id)
    {
        //Sono stati messi a caso, giusto per far capire come funziona la factory
        Fire nuovo= new Fire();
        if(id==1)
        {
            //nuovo.all=4;
        }
        return nuovo;
    }
}
