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

        assertEquals("Yes", shapeClassifier.evaluateGuess("Ellipse,Large,Yes,300")); // 5
    }
}
