package exceptions;

import model.Player;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidColorExceptionTest {
    @Test
    public void test(){
        assertThrows(InvalidColorException.class, () -> new Player("Bellatrix", "red", "12220987"));
    }
}