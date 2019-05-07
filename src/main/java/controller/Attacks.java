package controller;

import exceptions.InvalidDirectionException;
import model.*;

import java.util.ArrayList;

public class Attacks {
    public int useattack(Match m, Player first, ArrayList<Player>second, Weapon w, int type, ArrayList<String> direction, ArrayList<Coordinate> coordinates)
    {
        if(m.getPlayer(first.getcolor()).equals(first) && first.weaponIspresent(w) && !w.getStatus()) //Se il player esiste e possiede l'arma indicata
        {
            int moveme; //Mi serve per tener traccia dei movimenti restanti

            ArrayList<ArrayList<TypeAttack>> attacks = createlistofattacks(w); // Creo lista di liste di attacchi, ogni lista ha lo stesso typeattack

            ArrayList<TypeAttack> attacklist = attacks.get(type); //Estraggo solo il tipo di serie di attacchi scelto

            Player viewer= first; //E' colui che sta effettuando l'attacco, inizialmente è chi attacca

            for(TypeAttack t : attacklist) //Per ogni attacco
            {
                if(controlammo(m,first,t)) //Controllo che abbia le munizioni
                {
                    //ESEGUO ORA GLI ATTACCHI A SECONDA DEL TIPO
                    if(t instanceof model.UndefinedDistance)
                    {
                        //TODO ATTACCO UNDEFINED DISTANCE
                    }
                    else
                        if(t instanceof  model.FiniteDistance)
                        {
                            //TODO ATTACCO UNDEFINED DISTANCE
                        }
                        else
                            if(t instanceof model.MoreDistance)
                            {
                                //TODO ATTACCO MORE DISTANCE
                            }
                            else
                            {
                                //TODO ATTACCO CARDINAL
                            }
                }
            }











        }
        else
        {
            return -1; //Restituisco messaggio di errore, controllo non superato
        }
        return 0; //Caso in cui è andato tutto a buon fine
    }


    private void move(Match m, Player p, String direction) //Costruisce una lista contenente direction e poi avvia la move con la lista
    {
        ArrayList<String> directions = new ArrayList<String>();
        directions.add(direction);
    }

    private void move(Match m, Player p, ArrayList<String> direction) //Sposta un Player in una lista di direzioni
    {
        Run r = new Run();
        try
        {
            r.getMovement(m,p,direction);
        }
        catch (InvalidDirectionException e)
        {

        }
    }

    private ArrayList<ArrayList<TypeAttack>> createlistofattacks(Weapon weapon) //Crea una lista di liste, ogni lista contenuta ha un typeplayer comune
    {
        int cont=0;
        ArrayList<ArrayList<TypeAttack>> attacks= new ArrayList<ArrayList<TypeAttack>>();
        ArrayList<TypeAttack> attack = new ArrayList<TypeAttack>();

        for(int i=0; i<weapon.getNumberAttack();i++)
        {
            TypeAttack t = weapon.getAttack(i);
            if(t.getTypePlayer()==cont)
            {
                attack.add(t);
            }
            else {
                attacks.add(attack);
                cont = t.getTypePlayer();
            }
        }
        return attacks;
    }

    private boolean controlammo(Match m, Player player, TypeAttack t) //Controlla se il player ha tutti gli extra richiesti, restituisce true se è vero, false altrimenti
    {
        ArrayList<Integer> extra= t.getExtras();
        int n=0;
        for(int i : extra)
        {
            if(player.getAmmo(n)<i)
            {
                return false;
            }
            n++;
        }
        return true;
    }

    private void attackundefined(Match m, Player p, Player viewer, ArrayList<Player> second, ArrayList<Coordinate> coordinates, ArrayList<String> moveme, ArrayList<String> moveyou)
    {

    }

}
