package client;

public interface View {

    //Message to advise the player the game has started
    void welcomeMessage(int idClient);

    //Message to advise the player the game has finished
    void endMessage();

    //Method to print the map to the client
    void printMap();
}
