package network.messages.error;

public class MaxWeaponCardError extends ErrorMessage {
    /**
     * Method for maximum number of weapon cards
     * @param error is the info for the type of error message
     */
    public MaxWeaponCardError(String error){
        super(error);
        this.content= "MaxWeaponCardError";
    }
}
