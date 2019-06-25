package controller;

import exceptions.ActionNotAllowedException;
import exceptions.InvalidDirectionException;
import exceptions.NotYourTurnException;
import model.*;
import java.util.ArrayList;
import java.util.List;


public class Attacks {

    List<Player> associationplayer = new ArrayList<Player>(); //Tiene traccia degli ID assegnati ai vari player

    List<Coordinate> associationcell = new ArrayList<Coordinate>(); //Tiene traccia degli ID assegnati alle varie celle

    List<Player> seconds = new ArrayList<Player>(); //rende a tutti i metodi le info aggiornate su chi attaccare, in modo da toglierli

    public int useattack(Match m, Player first, List<Player>second, Weapon w, int type, List<String> direction, List<Coordinate> coordinates)
    {

        if(m.getPlayer(first.getColor()).equals(first) && first.weaponIspresent(w) && !w.getStatus()) //Se il player esiste e possiede l'arma indicata
        {
            int moveme; //Mi serve per tener traccia dei movimenti restanti

            List<List<TypeAttack>> attacks = createlistofattacks(w); // Creo lista di liste di attacchi, ogni lista ha lo stesso typeattack

            List<TypeAttack> attacklist = attacks.get(type); //Estraggo solo il tipo di serie di attacchi scelto

            Player viewer= first; //E' colui che sta effettuando l'attacco, inizialmente è chi attacca

            for(TypeAttack t : attacklist) //Per ogni attacco
            {
                List<Player> visible= elaboratelistplayer(m,t,viewer);
                List<Coordinate> viscel = elavoratelistcell(m,t,viewer);
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
        List<String> directions = new ArrayList<String>();
        directions.add(direction);
    }

    private void move(Match m, Player p, List<String> direction) //Sposta un Player in una lista di direzioni
    {
        Run r = new Run();
        try
        {
            r.movement(m,p.getID(),direction,false,false);  //TODO:verifica
        }
        catch (InvalidDirectionException e)
        {

        } catch (NotYourTurnException e) {
            e.printStackTrace();
        } catch (ActionNotAllowedException e) {
            e.printStackTrace();
        }
    }

    private List<List<TypeAttack>> createlistofattacks(Weapon weapon) //Crea una lista di liste, ogni lista contenuta ha un typeplayer comune
    {
        int cont=0;
        List<List<TypeAttack>> attacks= new ArrayList<List<TypeAttack>>();
        List<TypeAttack> attack = new ArrayList<TypeAttack>();

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
        List<Integer> extra= t.getExtras();
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

    private Player attackundefined(TypeAttack t, Match m, Player p, List<Player> visible, List<Coordinate> viscel, List<Coordinate> coordinates, List<String> moveme, List<String> moveyou) //Esegue un attaco undefined distance e restituisco il player che vede
    {
        Player returned = p;
        for(int i=0;i<t.getNumberEffect();i++) //Per ogni effetto contenuto
        {
            Effect e = t.getEffect(i);
            if(e instanceof model.PlayerEffect) //Caso in cui ho un attacco rivolto a un player
            {
                if(visible.contains(seconds.get(0))) //Caso in cui vedo chi voglio colpire
                {
                    Player second= seconds.get(0);
                    seconds.remove(0); //cancello il player dalla lista dei giocatori da colpire
                    //CONTROLLO COMBACINO ID E ATTACCO
                    if(checkID((PlayerEffect) e,second))
                    {
                        if(t.getTypePlayer()==1) //Nel caso di torpedirne restituirò poi il player attaccato come viewer
                        {
                            returned=second;
                        }
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
                if(viscel.contains(coordinates.get(0)))
                {
                    Coordinate cell= coordinates.get(0);
                    coordinates.remove(0);
                    if(checkID((CellEffect) e, cell ))
                    {
                        for(int j=0; j<e.getnumberdamage();j++)
                        {
                            assigncelldamages(m,cell,p,e.getDamage(j));
                        }
                    }
                }
            }
        }
        return returned;
    }


    private List<Player> elaboratelistplayer(Match m, TypeAttack attack, Player viewer) //returns the list of all player attackable
    {
        int i =attack.getType();
        int flagnotseen=0;
        int distance= attack.getDistance();
        if(i==0 && distance!=0)
        {
            flagnotseen=1;
        }
        List<Player> list =new ArrayList<Player>();
        list=m.getVisiblePlayers(viewer);
        if(i==0)
        {
            if(flagnotseen==1)
            {
                List<Player> notseen=m.getPlayers();
                for(Player p: notseen)
                {
                    if(list.contains(p))
                    {
                        notseen.remove(p);
                    }
                }
                return notseen;
            }
            else
                return list;
        }
        else
            if(i==1)
            {
                 for(Player p : list)
                 {
                     if(m.getPlayersMD(viewer,p)!= distance)
                     {
                         list.remove(p);
                     }
                 }
                 return list;
            }
            else
                if(i==2)
                {
                    for(Player p :list)
                    {
                        if(m.getPlayersMD(viewer,p)<distance)
                        {
                            list.remove(p);
                        }
                    }
                    return list;
                }
                else
                {
                    //TODO CASO CARDINAL, FORSE LO TRATTO SINGOLARMENTE
                }
        return null; //Caso di errore
    }

    private List<Coordinate> elavoratelistcell(Match m, TypeAttack attack, Player viewer) //returns the list of all cells attackable
    {
        int i =attack.getType();
        int flagnotseen=0;
        int distance= attack.getDistance();
        if(i==0 && distance!=0)
        {
            flagnotseen=1;
        }
        List<Coordinate> list =new ArrayList<Coordinate>();
        list=m.getVisibleCells(viewer.getCel());
        if(i==0)
        {
            return list;
        }
        else
        if(i==1)
        {
            for(Coordinate p : list)
            {
                if(m.getCellsMD(viewer.getCel(),p)!= distance)
                {
                    list.remove(p);
                }
            }
            return list;
        }
        else
        if(i==2)
        {
            for(Coordinate p :list)
            {
                if(m.getCellsMD(viewer.getCel(),p)<distance)
                {
                    list.remove(p);
                }
            }
            return list;
        }
        else
        {
            //TODO CASO CARDINAL, FORSE LO TRATTO SINGOLARMENTE
        }
        return null; //Caso di errore
    }



    private void attackdefined(TypeAttack t, Match m, Player p, Player viewer, List<Coordinate> coordinates, List<String> moveme, List<String> moveyou)
    {

    }

    private boolean checkID(PlayerEffect e, Player second) //Controllo che gli ID combacino
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

    private boolean checkID(CellEffect e, Coordinate cell )
    {
        if(e.getId()>associationcell.size()-1)
        {
            if(!associationcell.contains(cell))
            {
                associationcell.add(cell);
            }
            else
            {
                return false;
            }
        }
        if(e.getId()!=associationcell.size()-1)
        {
            return false;
        }
        return true;
    }


    private void assigndamages(Match m, Player first, Player second, Damage damage) //Assegno i danni ad un player
    {
        if(damage instanceof model.Life) //Caso in cui ho un danno di tipo vita
        {
            m.getPlayer(second.getColor()).setDamage(damage.getdamage(),first.getColor());
        }
        else //Caso in cui ho un danno di tipo marks
        {
            m.getPlayer(second.getColor()).setDamage(damage.getdamage(),first.getColor());
        }
    }

    private void assigncelldamages(Match m, Coordinate c, Player first, Damage damage) //assegna i danni a tutti i player di una cella
    {
        List<Player> toattack = m.getSameCellsPlayers(c);
        for(Player p : toattack)
        {
            if(!p.equals(first)) //Controllo di non assegnare danni allo stesso giocatore attaccante
            {
                assigndamages(m,first,p,damage);
            }
        }
    }


}
