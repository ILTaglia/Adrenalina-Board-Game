package exceptions;

import model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidColorExceptionTest {
    @Test
    public void test(){
        assertThrows(InvalidColorException.class, () -> new Player("Bellatrix", "Red", "12220987"));
    }
}