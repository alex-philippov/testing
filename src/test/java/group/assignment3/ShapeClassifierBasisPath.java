package group.assignment3;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShapeClassifierBasisPath {
    private ShapeClassifier shapeClassifier;

    @Before
    public void before() {
        shapeClassifier = new ShapeClassifier();
    }

    @Test
    public void testPath1() {
        //assertEquals("Yes", shapeClassifier.evaluateGuess("Equilateral,Large,Yes,100,100,100")); // 1

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Line,Large,Yes,300")); // 3

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Circle,Large,Yes,300,300")); // 5

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Rectangle,Large,Yes,300,300,300,300")); // 6

        //assertEquals("Yes", shapeClassifier.evaluateGuess("Square,Large,Yes,300,300,300,300")); // 7 -- bug in classify4Parameters

        assertEquals("Yes", shapeClassifier.evaluateGuess("Square,Large,Yes,300,300,300")); // 8
    }
}
