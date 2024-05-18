import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class A4DriverTest {

    private final ByteArrayOutputStream outData = new ByteArrayOutputStream();
    private final PrintStream origOutput = System.out;
    private final InputStream origInput = System.in;

    @Nested
    @DisplayName("Basic Tests")
    class BasicTests {

        @BeforeEach
        public void init() {
            System.setOut(new PrintStream(outData));
        }

        @AfterEach
        public void restore() {
            System.setOut(origOutput);
            System.out.println("raw test output");
            System.out.println(outData.toString());
            System.out.println("end of raw test output");
            System.setIn(origInput);
        }

        @Test
        @DisplayName("Basic 1 Bone Test")
            //test reads in from in1.txt
            //should output a single line with ankle-bone
        void oneBone() throws java.io.FileNotFoundException{
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in1.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            String sampleLine = sampleScanner.nextLine();

            final String answerFile = resourceDir.toAbsolutePath() + "/out1.txt";
            FileInputStream fio = new FileInputStream(new File(answerFile));
            Scanner answerScanner = new Scanner(fio);
            //ankle bone
            String answerLine = answerScanner.nextLine();
            assertEquals(answerLine, sampleLine);
        }

        @Test
        @DisplayName("Basic 1 rests on Test")
            //test reads in from in2.txt
            //should output 2 lines: shin-bone\nankle-bone\n
        void oneRests() throws java.io.FileNotFoundException{
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in2.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            String sampleLine1 = sampleScanner.nextLine();
            String sampleLine2 = sampleScanner.nextLine();

            final String answerFile = resourceDir.toAbsolutePath() + "/out2.txt";
            FileInputStream fio = new FileInputStream(new File(answerFile));
            Scanner answerScanner = new Scanner(fio);
            //ankle bone
            String answerLine1 = answerScanner.nextLine();
            String answerLine2 = answerScanner.nextLine();

            //the shin-bone is connected to the ankle-bone
            assertEquals(answerLine1, sampleLine1, "line 1");
            assertEquals(answerLine2, sampleLine2, "line 2");
        }
    }
    @Nested
    @DisplayName("Additional Tests")
    class AddedTests {

        @BeforeEach
        public void init() {
            System.setOut(new PrintStream(outData));
        }

        @AfterEach
        public void restore() {
            System.setOut(origOutput);
            System.out.println("raw test output");
            System.out.println(outData.toString());
            System.out.println("end of raw test output");
            System.setIn(origInput);
        }

        @Test
        @DisplayName("impossible big input test")
            //test reads in from in4.txt
            //a test case for which no correct order exists
        void impossibleBigInput() throws java.io.FileNotFoundException {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in4.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            String sampleLine = sampleScanner.nextLine();

            final String answerFile = resourceDir.toAbsolutePath() + "/out4.txt";
            FileInputStream fio = new FileInputStream(new File(answerFile));
            Scanner answerScanner = new Scanner(fio);
            //ankle bone
            String answerLine = answerScanner.nextLine();
            assertEquals(answerLine, sampleLine);
        }


        @Test
        @DisplayName("medium input test")
            //test reads in from in3.txt
        void mediumInput() throws java.io.FileNotFoundException {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in3.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for(int i = 0; i < 13; i++) {
                String nextLine = sampleScanner.nextLine();
                map.put(nextLine, i);
            }
            //test a few sample relationships
            assertTrue(map.get("hand-bone") < map.get("wrist-bone"), "hand before wrist");
            assertTrue(map.get("shin-bone") < map.get("arm-bone"), "shin before arm");
            assertTrue(map.get("trom-bone") < map.get("eye-bone"), "trom before eye");
            assertTrue(map.get("tongue-bone") < map.get("toe-bone"), "tongue before toe");
            assertTrue(map.get("spine-bone") < map.get("tongue-bone"), "spine before tongue");
        }

        @Test
        @DisplayName("biggish input test")
            //test reads in from in5.txt
        void biggishInput() throws java.io.FileNotFoundException {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in5.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for(int i = 0; i < 30; i++) {
                String nextLine = sampleScanner.nextLine();
                map.put(nextLine, i);
            }
            //test a few sample relationships
            assertTrue(map.get("pinky-bone") < map.get("hip-bone"), "pinky before hip");
            assertTrue(map.get("shoulder-bone") < map.get("head-bone"), "shoulder before head");
            assertTrue(map.get("nose-bone") < map.get("arm-bone"), "nose before arm");
            assertTrue(map.get("tail-bone") < map.get("nail-bone"), "tail before nail");
            assertTrue(map.get("thumb-bone") < map.get("tail-bone"), "thumb before tail");
            assertTrue(map.get("neck-bone") < map.get("tongue-bone"), "neck before tongue");
        }

        @Test
        @DisplayName("all roads lead to the foot-bone")
            //test reads in from in6.txt
        void allRoadsToFoot() throws java.io.FileNotFoundException {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in6.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            String lastLine = "";
            while(sampleScanner.hasNext() ) {
                String prevLine = lastLine;
                lastLine = sampleScanner.nextLine();
                if(lastLine.equals("")) {
                    lastLine = prevLine;
                }
            }
            assertEquals("foot-bone", lastLine, "foot comes last");
        }

        @Test
        @DisplayName("all roads exit from the toe-bone")
            //test reads in from in7.txt
        void allRoadsExitToe() throws java.io.FileNotFoundException {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in7.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            String lastLine = "";
            while(sampleScanner.hasNext() ) {
                String prevLine = lastLine;
                lastLine = sampleScanner.nextLine();
                if(lastLine.equals("")) {
                    lastLine = prevLine;
                }
            }
            assertEquals("foot-bone", lastLine, "foot comes last");
        }
        @Test
        @DisplayName("3 vertex tournament")
            //test reads in from in8.txt
        void small3VerticesTest() throws java.io.FileNotFoundException {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in8.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            String nextLine = "";
            String[] expectedLines = {"trom-bone","ankle-bone","finger-bone"};
            int i = 0;
            while(sampleScanner.hasNext() ) {
                nextLine = sampleScanner.nextLine().strip();
                if(i >= expectedLines.length)
                    break;
                assertEquals(expectedLines[i], nextLine, "trom then ankle then finger");
                i++;
            }
        }

        @Test
        @DisplayName("small impossible")
            //test reads in from in9.txt
        void smallImpossibleTest() throws java.io.FileNotFoundException {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in9.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            String nextLine = sampleScanner.nextLine().strip();
            assertEquals("impossible", nextLine, "output should be impossible");
        }

        @Test
        @DisplayName("small possible with order check")
            //test reads in from in10.txt
        void smallPossibleTest() throws java.io.FileNotFoundException {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String dataFile = resourceDir.toAbsolutePath() + "/in10.txt";
            InputStream in = new FileInputStream(new File(dataFile));
            System.setIn(in);
            A4Driver.main(null);
            String output = outData.toString();
            Scanner sampleScanner = new Scanner(output);
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for(int i = 0; i < 7; i++) {
                String nextLine = sampleScanner.nextLine();
                map.put(nextLine, i);
            }
            assertEquals(0, map.get("tongue-bone"), "tongue first");
            assertTrue(map.get("toe-bone") < map.get("ankle-bone"), "toe before ankle");
            assertTrue(map.get("foot-bone") < map.get("eye-bone"), "foot before eye");
            assertTrue(map.get("eye-bone") < map.get("trom-bone"), "eye before trom");
            assertTrue(map.get("trom-bone") < map.get("ankle-bone"), "trom before ankle");
            assertTrue(map.get("finger-bone") < map.get("foot-bone"), "finger before foot");
            assertTrue(map.get("foot-bone") < map.get("trom-bone"), "foot before trom");

        }
    }
}