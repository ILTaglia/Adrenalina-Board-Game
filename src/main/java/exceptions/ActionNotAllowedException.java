package exceptions;

public class ActionNotAllowedException extends Exception {
    /**
     *
     * @param info is exception for not allowed action
     */
    public ActionNotAllowedException(String info){super(info);}
}
