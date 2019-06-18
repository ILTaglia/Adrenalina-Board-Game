package controller;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AttackNewVersion {




    public int attack(Player player, Match match) throws IOException {

        //CHIEDO ALL'UTENTE L'ARMA CHE INTENDE UTILIZZARE
        Weapon weapon = getWeapon(match,player);

        //GENERO LISTA CONTENENTE LA SERIE DI ATTACCHI DESIDERATA
        List<TypeAttack> lista = getListofAttacks(weapon);


        //PER TUTTI GLI ATTACCHI CONTENUTI NELLA LISTA
        int i=0;
        for(TypeAttack attack : lista)
        {
            if(i!=0)
            {
                //TODO CHIEDO ALL'UTENTE SE DESIDERA ESEGUIRE ALTRI ATTACCHI O FERMARSI, NEL CASO INTERROMPO IL FOR
            }

            //SVOLGO L'ATTACCO A SECONDA DEL TIPO DI ATTACCO DOPO AVER VERIFICATO SE POSSA PAGARE EVENTUALI EXTRA


        }

        //GENERAZIONE LISTE DI ATTACCHI
        return 20;

    }

    private Weapon getWeapon(Match m, Player player)
    {
        //TODO CHIEDE ALL'UTENTE DI INSERIRE UN'ARMA E VERIFICA CHE ESSA SIA PRESENTE E CARICA, ALTRIMENTI RICHIEDE
        return null;
    }


    private List<TypeAttack> getListofAttacks(Weapon weapon)
    {

        //CREA LISTA DI TUTTI GLI ATTACCHI DISPONIBILI
        List<List<TypeAttack>> lista = createlistofattacks(weapon);


        //CHIEDE ALL'UTENTE IL NUMERO DI SERIE CHE DESIDERA FINO A QUANDO NON SIA ACCETTABILE
        int n=0;
        //TODO CHIEDERE NUMERO ALL'UTENTE E VERIFICARE CORRETTEZZA


        //RESTITUISCO LA SERIE RICHIESTA
        return lista.get(n);

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

    private int Attack (Match m, Player p, Player viewer, Weapon weapon)
    {
        //TODO CONTROLLO CHE POSSA PAGARE GLI EXTRA ALTRIMENTI TERMINO





        return 0;
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





}
