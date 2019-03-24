package group.assignment3;

//Download and add JUnitParams-1.0.2.jar to your build path
//You can download it from: https://junitparams.googlecode.com/files/JUnitParams-1.0.2.jar
import junitparams.JUnitParamsRunner;
import junitparams.FileParameters;
import junitparams.mappers.CsvWithHeaderMapper;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.io.BufferedWriter;
import java.io.IOException;

import org.junit.runner.RunWith;
import org.junit.runner.Description;
import org.junit.rules.TestWatcher;
import org.junit.AfterClass;
import org.junit.Rule;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class ShapeClassifierBlackBoxTest2Wise {

    @org.junit.Test
    @FileParameters(
            value = "./2wise.csv",
            mapper = CsvWithHeaderMapper.class)
    public void test(
            String Shape,
            String Size,
            String Even,
            String Side1,
            String Side2,
            String Side3,
            String Side4,
            String result
    ) {
        sbTestResults
                .append(Shape).append(",")
                .append(Size).append(",")
                .append(Even).append(",")
                .append(Side1).append(",")
                .append(Side2).append(",")
                .append(Side3).append(",")
                .append(Side4).append(",")
                .append(result);

        if (result.equals("0")) {
            result = "No";
        }
        else {
            result = "Yes";
        }

        if (Even.equals("true")) {
            Even = "Yes";
        }
        else {
            Even = "No";
        }

        assertEquals(result, new ShapeClassifier().evaluateGuess(Shape + "," + Size + "," + Even + "," + sides(new String[]{Side1, Side2, Side3, Side4})));

        //fail("Not yet implemented");
    }

    private String sides(String[] sides) {
        String result = "";

        for (int i = 0; i < sides.length; i++) {
            if (sides[i].equals("0")) {
                result += "";
            }
            else {
                result += sides[i] + ",";
            }
        }

        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    private static StringBuffer sbTestResults = new StringBuffer();

    @AfterClass
    public static void exportCsvResult() throws IOException {
        BufferedWriter resultWriter = Files.newBufferedWriter(
                Paths.get("./2wise_Results.csv"),
                Charset.defaultCharset());
        resultWriter.write("Shape, Size, Even, Side1, Side2, Side3, Side4, Result, TestResult");
        resultWriter.newLine();
        resultWriter.write(sbTestResults.toString());
        resultWriter.close();
    }

    @Rule
    public TestWatcher addCsvResult = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            sbTestResults.append(", Passed")
                    .append(System.getProperty("line.separator"));
        }
        @Override
        protected void failed(Throwable e, Description description) {
            sbTestResults.append(", Failed")
                    .append(System.getProperty("line.separator"));
        }
    };


}
