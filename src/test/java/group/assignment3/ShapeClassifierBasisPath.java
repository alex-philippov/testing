package group.assignment3;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.security.Permission;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ShapeClassifierBasisPath {
    private ShapeClassifier shapeClassifier;

    @Before
    public void before() {
        shapeClassifier = new ShapeClassifier();
    }

    @Test
    public void testPath1() {
        assertEquals("Yes", shapeClassifier.evaluateGuess("Equilateral,Large,Yes,100,100,100")); // 1

        assertEquals("Yes", shapeClassifier.evaluateGuess("Line,Large,Yes,300")); // 3

        assertEquals("Yes", shapeClassifier.evaluateGuess("Circle,Large,Yes,300,300")); // 5

        assertEquals("Yes", shapeClassifier.evaluateGuess("Rectangle,Large,Yes,300,300,200,200")); //TODO 6 перепроверить

        assertEquals("Yes", shapeClassifier.evaluateGuess("Square,Large,Yes,300,300,300,300")); // 7 -- bug in classify4Parameters

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Rectangle,Large,Yes,300,300,300")); // 8 -- infisible

        assertEquals("Yes", shapeClassifier.evaluateGuess("Equilateral,Small,Yes,2,2,2")); // 9

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Equilateral,Small,Yes,200,200,200")); // 10 -- infisible

        assertEquals("Yes", shapeClassifier.evaluateGuess("Isosceles,Small,No,2,2,3")); // 11

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Isosceles,Large,Yes,2,2,3")); // 12 --infisible

        //assertEquals("No", shapeClassifier.evaluateGuess("Isosceles,Large,Yes,2,2,3")); // 13 -- переделать - сложно тестировать

        assertEquals("No", shapeClassifier.evaluateGuess("Isosceles,Large,Yes,2,2,3")); // 14

        SecurityManager initialSecurityManger = System.getSecurityManager();
        try {
            System.setSecurityManager(new ShapeClassifierTest.NoExitSecurityManager());

            assertEquals("No", shapeClassifier.evaluateGuess("Isosceles,Large,Yes,2,2,3")); // 13

            assertEquals("No", shapeClassifier.evaluateGuess("Isosceles,Large,Yes,2,2,3")); // 13

            assertEquals("No", shapeClassifier.evaluateGuess("Isosceles,Large,Yes,2,2,3")); // 13

            fail("Should've thrown ExitException");
        } catch (ShapeClassifierTest.ExitException e) {
            assertThat(e.status, is(1)); // <== this fails on purpose
        } finally {
            System.setSecurityManager(initialSecurityManger);
        }
    }

    @Test
    @Ignore
    public void testPath2() {
        assertEquals("Yes", shapeClassifier.evaluateGuess("Equilateral,Large,Yes,100,100,100")); // 1

        //assertEquals("No", shapeClassifier.evaluateGuess("Equilateral,Large,Yes,100,100,100")); // 1

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Line,Large,Yes,300")); // 3

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Circle,Large,Yes,300,300")); // 5

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Rectangle,Large,Yes,300,300,300,300")); // 6

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Square,Large,Yes,300,300,300,300")); // 7 -- bug in classify4Parameters

        // assertEquals("Yes", shapeClassifier.evaluateGuess("Circle,Large,Yes,300,300,300")); // 8 -- infisible

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Circle,Large,Yes,300,300,300")); // 8 -- infisible
    }

    public static class ExitException extends SecurityException {
        public final int status;

        public ExitException(int status) {
            this.status = status;
        }
    }


    // custom security manager
    public static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
        }

        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new ShapeClassifierTest.ExitException(status);
        }
    }
}
