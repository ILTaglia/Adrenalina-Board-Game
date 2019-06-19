package controller;

import exceptions.CardAlreadyCollectedException;
import exceptions.MaxNumberofCardsException;
import model.Player;
import model.Match;

import exceptions.InvalidDirectionException;
import java.util.List;

public class FinalFrenzy {
    private List<Player> playersBeforetheFirst;
    private List<Player> playersAftertheFirst;

    public FinalFrenzy(Match match, int activatingPlayerColor){
        List<Player> allPLayers = match.getPlayers();
        Player pl = match.getPlayerByColor(activatingPlayerColor);
        if(allPLayers.indexOf(pl)>0){
            playersBeforetheFirst = allPLayers.subList(allPLayers.indexOf(pl), allPLayers.size()-1);
            playersAftertheFirst = allPLayers.subList(0, allPLayers.indexOf(pl)-1);
        }
        else{
            playersBeforetheFirst = allPLayers;
            //all players have just one action
        }
        List<Player> noDamagedPlayers = match.getNoDamagedPlayers();
        for(Player p:noDamagedPlayers){p.setDeathFrienzy(3);}
        /*This way when calculate score will be called again, for these players the max number of
        point will be 2, then all 1.*/

    }
    //TODO bisogna incassare i punteggi, poi per i giocatori senza danno si potrà avere massimo
    //TODo punteggio 2, 1, 1, 1, quindi per semplicità setto il loro numero di death al valore opportuno per
    //TODO avere corretta attribuzione di punti.
    //TODO prima di questo bisogna incassare. Nel controller bisogna chiamare la calculate score una prima
    //TODO volta, questa poi creerà la frenesia finale se giunti a fine gioco.

    public void runInFrenzy(Match match, List<Player> playerslist, Player player, List<String> destination) throws InvalidDirectionException {
        if(playerslist.contains(player) && playerslist.equals(this.playersBeforetheFirst)){
            if(destination.size()<=4){
                Run run = new Run();
                run.getMovement(match, player, destination);
            }
        }
    }

    public void grabAmmoInFrenzy(Match match, Player player) throws MaxNumberofCardsException, CardAlreadyCollectedException {
        GrabAmmo grabAmmo = new GrabAmmo();
        grabAmmo.grabAmmo(match, player);
    }

    public void grabWeaponInFrenzy(Match match, Player player, int indexWeapon) throws MaxNumberofCardsException{
        GrabWeapon grabWeapon = new GrabWeapon();
        grabWeapon.grabWeapon(match, player, indexWeapon);
    }

    //CHECK IS GRABBING AMMO IN FRENzY IS VALID FOR BOTH CATEGORIES
    public boolean grabAmmoIsValidInFrenzy(Match match, Player player, List<String> destination){
        if (!(playersBeforetheFirst.contains(player) && destination.size()<=2)) return false;
        if (!(playersAftertheFirst.contains(player) && destination.size()<=3)) return false;
        else {
            Run r = new Run();
            int x = player.getCel().getX();
            int y = player.getCel().getY();
            if(r.isValid(match, player, x, y, destination)) return true;
            else return false;
        }
    }

    public void shootInFrenzy(){
        Shoot shoot = new Shoot();
        //TODO da completare
    }

    public boolean shootIsValidInFrenzy(Match match, Player player, List<String> destination){
        if (!(playersBeforetheFirst.contains(player) && destination.size()<=1)) return false;
        if (!(playersAftertheFirst.contains(player) && destination.size()<=2)) return false;
        else {
            Run r = new Run();
            int x = player.getCel().getX();
            int y = player.getCel().getY();
            if(r.isValid(match, player, x, y, destination)) return true;
            else return false;
        }
    }
    //TODO
    /*
    * SEQUENZA PER LA RACCOLTA GRAB:
    * - chiamare la grabAmmoIsValidInFrenzy, a seconda che il giocatore sia prima del primo o dopo
    * cambia il check di validità
    * - fare una run con la sequenza desiderata(può anche non muoversi)
    * - fare il check sul tipo di cella
    * - in caso chiamare la grabAmmoInFrenzy o la grabWeaponInFrenzy passando anche l'indice
    * */

    //TODO
    /*Per la shoot invece,
    - chiamare la shootIsValidInFrenzy, a seconda che il giocatore sia prima del primo o dopo
    * cambia il check di validità
    * - fare una run con la sequenza desiderata(può anche non muoversi)
    * - recharge
    * //TODO manca il metodo di effetto arma. Dopo aver chiamato quello
    **/

    //TODO per controller, game. se il giocatore appartiene a playersBeforetheFirst ha due azioni
    //TODO disponibili tra quelle scritte
    //TODO se appartiene all'altra una sola
}
