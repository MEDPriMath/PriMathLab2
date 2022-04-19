package ru.itmo.primath.lab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EssentialsTest {

    @Test
    public void jupiterTest() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void jupiterTest2() {
        String danila = "Danila" + 56 + " is a slacker";
        assertEquals(danila, "Danila56 is a slacker");
    }

}
