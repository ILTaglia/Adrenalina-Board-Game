package controller;

import exceptions.InvalidDirectionException;
import model.*;
import java.util.ArrayList;


public class Attacks {

    ArrayList<Player> associationplayer = new ArrayList<Player>(); //Tiene traccia degli ID assegnati ai vari player

    ArrayList<Coordinate> associationcell = new ArrayList<Coordinate>(); //Tiene traccia degli ID assegnati alle varie celle

    ArrayList<Player> seconds = new ArrayList<Player>(); //rende a tutti i metodi le info aggiornate su chi attaccare, in modo da toglierli

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

    private void attackundefined(TypeAttack t, Match m, Player p, Player viewer, ArrayList<Coordinate> coordinates, ArrayList<String> moveme, ArrayList<String> moveyou) //Esegue un attaco undefined distance
    {
        ArrayList<Player> visible= new ArrayList<Player>(); //Arraylist of Players I can see
        visible=m.getVisiblePlayers(viewer);

        ArrayList<Coordinate> viscel = new ArrayList<Coordinate>(); //Arraylist of cells I can see
        viscel=m.getVisibleCells(viewer.getCel());
        if(t.getDistance()==0) //Caso in cui ho un classico undefined attack
        {
            for(int i=0;i<t.getNumberEffect();i++) //Per ogni effetto contenuto
            {
                Effect e = t.getEffect(i);
                if(e instanceof model.PlayerEffect) //Caso in cui ho un attacco rivolto a un player
                {
                    if(visible.contains(seconds.get(0))) //Caso in cui vedo chi voglio colpire
                    {
                        Player second= seconds.get(0);
                        seconds.remove(second); //cancello il player dalla lista dei giocatori da colpire
                        //CONTROLLO COMBACINO ID E ATTACCO
                        if(checkIDplayer(e,second))
                        {
                            for(int j=0;j<e.getnumberdamage();j++) //Assegno tutti i damage
                            {
                                assigndamages(m,p,second,e.getDamage(j));
                            }
                        }
                        else //Caso in cui non combaciano
                        {
                            //TODO GESTIRE CASO DI ERRORE
                        }
                    }
                    else
                    {
                        //TODO, ERRORE GIOCATORE NON ATTACCABILE
                    }
                }
                else // caso in cui ho un attacco rivolto a una cella
                {
                    //TODO CASO ATTACCO CELLA
                }
            }
        }
        else //Caso in cui attacco chi non vedo
        {
            //TODO CASO ATTACCO CHI NON VEDO
        }
    }

    private boolean checkIDplayer(Effect e, Player second) //Controllo che gli ID combacino
    {
        if(e.getId()>associationplayer.size()-1) //Controllo in caso di nuovi giocatori non ancora attaccati da attaccare
        {
            if(!associationplayer.contains(second))
            {
                associationplayer.add(second);
            }
            else
            {
                return false; //Caso in cui gli ID non combaciano perchè già inserito con ID diverso
            }
        }
        if(e.getId()!=associationplayer.size()-1) //Controllo che gli ID combacino
        {
            return false; //Non combaciano gli ID
        }
        return true;
    }


    private void assigndamages(Match m, Player first, Player second, Damage damage) //Assegno i danni ad un player
    {
        if(damage instanceof model.Life) //Caso in cui ho un danno di tipo vita
        {
            m.getPlayer(second.getcolor()).setdamage(damage.getdamage(),first.getcolor());
        }
        else //Caso in cui ho un danno di tipo marks
        {
            m.getPlayer(second.getcolor()).setdamage(damage.getdamage(),first.getcolor());
        }
    }

    private void assigncelldamages(Match m, Coordinate c, Player first, Damage damage) //assegna i danni a tutti i player di una cella
    {
        ArrayList<Player> toattack = m.getSameCellsPlayers(c);
        for(Player p : toattack)
        {
            if(!p.equals(first)) //Controllo di non assegnare danni allo stesso giocatore attaccante
            {
                assigndamages(m,first,p,damage);
            }
        }
    }


}
