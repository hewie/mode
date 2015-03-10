package de.uni.bremen.monty.mode.parser;

import de.uni.bremen.monty.mode.parser.util.TestParserBase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatementParserTest extends TestParserBase {

    @Test
    public void testAssignment() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "b"),
                new Token(DOT, "."),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")"),
                new Token(DOT, "."),
                new Token(IDENTIFIER, "c"),
                new Token(ASSIGNMENT, ":="),
                new Token(STRING, "\"abc\"")
        ));

        testParser(tokens, "( ( ( b . ( a #( #) ) ) . c ) := \"abc\" ) ");
    }

    @Test
    public void testAssignmentWithoutRightSide() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "b"),
                new Token(DOT, "."),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")"),
                new Token(DOT, "."),
                new Token(IDENTIFIER, "c"),
                new Token(ASSIGNMENT, ":="),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( ( ( b . ( a #( #) ) ) . c ) := ! \n ) ");
    }

    @Test
    public void testTry() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "try"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),

                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(BRACKETS, "["),
                new Token(OPERATOR, "-"),
                new Token(NUMBER, "1"),
                new Token(BRACKETS, "]"),
                new Token(EOL_DEDENT, "\n "),

                new Token(KEYWORD, "handle"),
                new Token(CLASS_IDENTIFIER, "Ee"),
                new Token(IDENTIFIER, "e"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n ")
        ));

        testParser(tokens, "( try : \n" +
                "  ( a := ( [ ( - 1 ) ] ) ) \n" +
                "  handle ( ( Ee ) e ) : \n" +
                "  pass \n" +
                "  ) ");
    }

    @Test
    public void testTryWithMultipleHandle() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "try"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),

                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(NUMBER, "1"),
                new Token(EOL_DEDENT, "\n "),

                new Token(KEYWORD, "handle"),
                new Token(CLASS_IDENTIFIER, "Ee"),
                new Token(IDENTIFIER, "e"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n "),

                new Token(KEYWORD, "handle"),
                new Token(CLASS_IDENTIFIER, "Xx"),
                new Token(IDENTIFIER, "x"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass")
        ));

        testParser(tokens, "( try : \n" +
                "  ( a := 1 ) \n" +
                "  handle ( ( Ee ) e ) : \n" +
                "  pass \n" +
                "  handle ( ( Xx ) x ) : \n" +
                "  pass ) ");
    }

    @Test
    public void testTryWithoutHandle() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "try"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),

                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(NUMBER, "1")
        ));

        testParser(tokens, "( try : \n  ( a := 1 ) ! ) ");
    }

    @Test
    public void testIf() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "if"),
                new Token(KEYWORD, "true"),

                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "return"),
                new Token(NUMBER, "1")
        ));

        testParser(tokens, "( if true : \n  ( return 1 ) ) ");
    }

    @Test
    public void testIfWithElse() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "if"),
                new Token(KEYWORD, "true"),

                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "raise"),
                new Token(EOL_DEDENT, "\n "),
                new Token(KEYWORD, "else"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "skip")
        ));

        testParser(tokens, "( if true : \n  ( raise ) \n  else : \n  ( skip ) ) ");
    }

    @Test
    public void testIfWithElIf() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "if"),
                new Token(KEYWORD, "true"),

                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "return"),
                new Token(NUMBER, "1"),
                new Token(EOL_DEDENT, "\n "),

                new Token(KEYWORD, "elif"),
                new Token(KEYWORD, "false"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "break")
        ));

        testParser(tokens, "( if true : \n  ( return 1 ) \n  elif false : \n  ( break ) ) ");
    }

    @Test
    public void testIfWithElIfAndElse() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "if"),
                new Token(KEYWORD, "true"),

                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "return"),
                new Token(NUMBER, "1"),
                new Token(EOL_DEDENT, "\n "),

                new Token(KEYWORD, "elif"),
                new Token(KEYWORD, "false"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "break"),
                new Token(EOL_DEDENT, "\n "),

                new Token(KEYWORD, "else"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
//                new Token(KEYWORD, "pass"),
//
                new Token(IDENTIFIER, "a"),
                new Token(DOT, "."),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")")
        ));

        testParser(tokens, "( if true : \n  ( return 1 ) \n  elif false : \n  ( break ) \n  else : \n  ( a . ( a #( #) ) ) ) ");
    }

    @Test
    public void testIfWithNestedIf() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "if"),
                new Token(KEYWORD, "true"),

                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),

                new Token(KEYWORD, "if"),
                new Token(KEYWORD, "false"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "return"),
                new Token(EOL_DEDENT, "\n "),
                new Token(EOL_DEDENT, "\n ")
        ));

        testParser(tokens, "( if true : \n  ( if false : \n  ( return ) \n  ) \n  ) ");
    }


    @Test
    public void twoFunctionCalls() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( a #( #) ) \n ( a #( #) ) \n ");
    }


}