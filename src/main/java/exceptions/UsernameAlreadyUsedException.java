package exceptions;

public class UsernameAlreadyUsedException extends Exception {
    /**
     * is exception for username already used
     */
    public UsernameAlreadyUsedException(){super("An other user has already this username in your Match, please change it");}
}
