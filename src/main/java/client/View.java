package client;

public interface View {

    //Message to advise the player the game has started
    void welcomeMessage(int idClient);

    //Message to advise the player the game has finished
    void endMessage();

    //Method to print the map to the client
    void printMap();

    //Method to show player its weapon cards
    void showPlayerWeapons();

    //Method to show Weapon Cards in SpawnPoint Cell
    void showSpawnPointWeapons();

    //Method to show player its PowCards
    void showPlayerPows();

    //Method to ask the player which cards he wants to buy if in a SpawnPoint Cell
    int getWeaponCard();

    //Method to ask the player which PowCard he wants to use to attack or in response, @return the position of card,
    //according to the print of player cards
    int getPowCard();

    //Method to ask the player which Weapon card he wants to use to attack, @return the position of card,
    //according to the print of player cards
    int getWeaponCardtoAttack();

    //Method to show other players a player has moved
    void printPlayerMove();

    //Method to show the player its current data
    void printPlayerData();

    //Method to advise the player he has been damaged
    void printDamagedPlayer(int numberdamages, String attackerplayername);

    //Method to advise the player he has been given marks
    void printMarkedPlayer(int numbermarks, String attackerplayername);

    //Method to advise the player of the consequences of his attack
    void printDamagerAndMarkerPlayer(int numberdamages, int numbermarks, String attackedplayername);
}
