package de.uni.bremen.monty.mode.parser;

import de.uni.bremen.monty.mode.parser.util.TestParserBase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MontyParseStepTest extends TestParserBase {
    @Test
    public void test1() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "import"),
                new Token(IDENTIFIER, "a"),
                new Token(EOL, "\n"),
                new Token(KEYWORD, "import"),
                new Token(IDENTIFIER, "b"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( import a \n ) ( import b \n ) ");
    }

    @Test
    public void test2() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "import"),
                new Token(IDENTIFIER, "a"),
                new Token(EOL, "\n"),
                new Token(KEYWORD, "import"),
                new Token(IDENTIFIER, "b")
        ));

        testParser(tokens, "( import a \n ) ( import b ) ");
    }

    @Test
    public void test3() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(EOL, "\n"),
                new Token(EOL, "\n"),
                new Token(KEYWORD, "import"),
                new Token(IDENTIFIER, "a"),
                new Token(EOL, "\n"),
                new Token(EOL, "\n"),
                new Token(KEYWORD, "import"),
                new Token(IDENTIFIER, "b"),
                new Token(EOL, "\n"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "\n \n ( import a \n \n ) ( import b \n \n ) ");
    }

    @Test
    public void testE1() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "import"),
                new Token(IDENTIFIER, "a"),
                new Token(KEYWORD, "import"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( import a ! ) ( import ! \n ) ");
    }

    @Test
    public void testE2() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "import"),
                new Token(IDENTIFIER, "a"),
                new Token(EOL, "\n"),
                new Token(OPERATOR, "~")
        ));

        testParser(tokens, "( import a \n ) ! ~ ");
    }

//
//    @Test
//    public void testLeadingEOL() throws Exception {
//        final List<Token> tokens = new ArrayList<>(Arrays.asList(
//                new Token(EOL, "\n"),
//                new Token(KEYWORD, "import"),
//                new Token(IDENTIFIER, "a"),
//                new Token(EOL, "\n")
//        ));
//
//        testParser(tokens, "\n ( import a \n ) ");
//    }
}