//package ru.itmo.primath.lab2;
//
//import org.junit.jupiter.api.Test;
//import ru.itmo.primath.lab2.util.TemporaryFunctionStorage;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class EssentialsTest {
//    private final TemporaryFunctionStorage funcAndGradStorage = new TemporaryFunctionStorage(0);
//
//    @Test
//    public void jupiterTest() {
//        assertEquals(4, 2 + 2);
//    }
//
//    @Test
//    public void jupiterTest2() {
//        String danila = "Danila" + 56 + " is a slacker";
//        assertEquals(danila, "Danila56 is a slacker");
//    }
//
//    @Test
//    public void funcCalculationTest() {
//        assertEquals(funcAndGradStorage.calculateFunc(0, 0), 0);
//        assertEquals(funcAndGradStorage.calculateFunc(1, 1), 7);
//        assertEquals(funcAndGradStorage.calculateFunc(-1, -1), 7);
//    }
//
//    @Test
//    public void gradCalculationTest() {
//        assertEquals(funcAndGradStorage.calculateGrad(0, 0), 0);
//        assertEquals(funcAndGradStorage.calculateGrad(1, 1), 14);
//        assertEquals(funcAndGradStorage.calculateGrad(-1, -1), -14);
//    }
//}
