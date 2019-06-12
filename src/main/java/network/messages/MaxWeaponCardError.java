package network.messages;

public class MaxWeaponCardError extends ErrorMessage {
    public MaxWeaponCardError(String error){
        super(error);
        this.content= "MaxWeaponCardError";
    }
}
