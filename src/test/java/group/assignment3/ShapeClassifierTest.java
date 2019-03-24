package group.assignment3;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.Permission;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class ShapeClassifierTest {
    /*
    <ShapeClassifier Input>::= <Shape Description>, <Shape Parameters>

    <Shape Description>::= <Shape>, <Size>, <Even>
    <Shape>::= [ “Line” | “Circle” | “Ellipse” | “Equilateral” | “Isosceles” | “Scalene” | “Square” | “Rectangle”]
    <Size>::= <Large> | <Small>
    <Large>::= Any shape whose perimeter , or length in case of a line, is greater than 100
    <Small>::= Any shape whose perimeter, or length in case of a line, is less or equal than
    <Even>::= <Yes> | <No>
    <Yes>::= Means that the product of the parameters is even
    <No>::= Means that the product of the parameters is odd

    <Shape Parameters>::= <Line> | <Ellipse> | <Triangle> | <Quadrilateral>
    <Line>::=, <Length>
    <Ellipse>::= <Minor Axis>, <Major Axis>
    <Triangle>::= <Side 1>, <Side 2>, <Side 3>
    <Quadrilateral>::= <Side 1>, <Side 2>, <Side 3>, <Side 4>
    <Length>::= An integer number ϵ [1, 4095]
    <Minor Axis>::= An integer number ϵ [1, 4095]
    <Mayor Axis>::= An integer number ϵ [1, 4095]
    <Side x>::= An integer number ϵ [1, 4095]
    */

    private ShapeClassifier shapeClassifier;

    @Before
    public void before() {
        shapeClassifier = new ShapeClassifier();
    }

    @Test
    public void testDefaults() {
        //assertEquals("Yes", shapeClassifier.evaluateGuess("Equilateral,Large,Yes,100,100,100"));
        //assertEquals("No", shapeClassifier.evaluateGuess("Square,Small,No,5,5,5"));

        // > 200 на самом деле (увеличил длины сторон) и порядок передачи параметров важен (поменял порядок)
        //assertEquals("Yes", shapeClassifier.evaluateGuess("Rectangle,Large,Yes,54,60,60,54"));

        //assertEquals("No", shapeClassifier.evaluateGuess("Line,Small,No,2"));
    }

    @Test
    public void testLine() {
        assertEquals("Yes", shapeClassifier.evaluateGuess("Line,Small,No,3"));

        //assertEquals("No", shapeClassifier.evaluateGuess("Line,Large,Yes,3"));

        //assertEquals("No", shapeClassifier.evaluateGuess("Lin,Large,Yes,3"));
    }

    @Test
    public void testRectangle() {
        assertEquals("No", shapeClassifier.evaluateGuess("Rectangl,Large,Yes,50,50,60,60"));

        assertEquals("No", shapeClassifier.evaluateGuess("Rectangle,Small,Yes,4,6,6,4"));
    }

    @Test
    public void testEllipse() {
        assertEquals("No", shapeClassifier.evaluateGuess("Ellipse,Small,No,3,3"));
    }

    @Test
    public void testEquilateral() {
        assertEquals("No", shapeClassifier.evaluateGuess("Equilateral,Small,Yes,100,100,100"));
    }

    @Test
    @Ignore
    public void testFailedEllipse() {
        assertEquals("No", shapeClassifier.evaluateGuess("Ellipse,Small,Yes,2,2"));
    }

    @Test
    @Ignore
    public void testFailedEquilateral() {
        assertEquals("No", shapeClassifier.evaluateGuess("Equilateral,Small,Yes,2,2,2"));
    }

    @Test
    @Ignore
    public void testFailedEquilateralMockito() {
        ShapeClassifier shapeClassifier = new ShapeClassifier();

        ShapeClassifier spyShapeClassifier = Mockito.spy(shapeClassifier);

        //Mockito.doReturn(null).when(spyShapeClassifier).getShapeGuess(anyString());

        assertEquals("No", spyShapeClassifier.evaluateGuess("Equilateral,Small,Yes,2,2,2"));
    }

    @Test
    public void testExit() {
        SecurityManager initialSecurityManger = System.getSecurityManager();
        try {
            System.setSecurityManager(new NoExitSecurityManager());

            assertEquals("No", shapeClassifier.evaluateGuess("Line,Small,Yes,3"));

            assertEquals("No", shapeClassifier.evaluateGuess("Line,Large,Yes,3"));

            assertEquals("No", shapeClassifier.evaluateGuess("Lin,Large,Yes,3"));

            fail("Should've thrown ExitException");
        } catch (ExitException e) {
            assertThat(e.status, is(1)); // <== this fails on purpose
        } finally {
            System.setSecurityManager(initialSecurityManger);
        }
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
            throw new ExitException(status);
        }
    }
}
