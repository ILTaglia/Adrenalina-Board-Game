package exceptions;

import Model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidColorExceptionTest {
    @Test
    public void test(){
        assertThrows(InvalidColorException.class, () -> new Player("Bellatrix", "red", "12220987"));
    }
}