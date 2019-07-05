package network.server;
/**
 * This class contains all the method for managing the server.
 * GameServer is the core, the main server. The waiting Room is a room when clients are managed before acting in the game.
 * For example, before being added in the match, all Clients are set in wait in the gameRoom, then, after the end of timer, they are
 * added in the match.
 * Client interface is for communication with client.
 *
 * There are two contained packages, one for RMI classes, and one for Socket classes.
 */