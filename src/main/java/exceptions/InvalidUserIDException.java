package exceptions;

public class InvalidUserIDException extends Exception {
    public InvalidUserIDException(){super("The userID entered is not valid");}
}

