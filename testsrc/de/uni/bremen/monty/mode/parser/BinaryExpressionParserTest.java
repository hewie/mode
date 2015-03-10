package de.uni.bremen.monty.mode.parser;

import de.uni.bremen.monty.mode.parser.util.TestParserBase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryExpressionParserTest extends TestParserBase {

    @Test
    public void test0() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "4"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( 3 + 4 ) #) ) \n ");
    }


    @Test
    public void test1() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( 3 + ( 4 * 5 ) ) #) ) \n ");
    }

    @Test
    public void test2() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "-"),
                new Token(NUMBER, "5"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "-"),
                new Token(NUMBER, "5"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( ( ( ( 3 * 4 ) - ( 5 * 4 ) ) - 5 ) + ( 4 * 5 ) ) #) ) \n ");
    }


    @Test
    public void test3() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "-"),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( ( 3 * 4 ) - 5 ) #) ) \n ");
    }

    @Test
    public void test4() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "5"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "6"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( ( ( 3 * 4 ) * 5 ) * 6 ) #) ) \n ");
    }

    @Test
    public void test5() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( ( 3 * 4 ) * 5 ) #) ) \n ");
    }

    @Test
    public void test7() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "2"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( ( 3 * 4 ) + ( 2 * 5 ) ) #) ) \n ");
    }

    @Test
    public void test8() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "2"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( ( 3 + ( 4 * 2 ) ) + 5 ) #) ) \n ");
    }

    @Test
    public void test10() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(DOT, "."),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "or"),
                new Token(NUMBER, "5"),
                new Token(OPERATOR, "or"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "."),
                new Token(NUMBER, "5"),
                new Token(OPERATOR, "."),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "or"),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( ( ( ( 3 . 4 ) or 5 ) or ( ( 4 . 5 ) . 4 ) ) or 5 ) #) ) \n ");
    }

    @Test
    public void test11() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "."),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "or"),
                new Token(NUMBER, "5"),
                new Token(OPERATOR, "or"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "."),
                new Token(NUMBER, "5"),
                new Token(OPERATOR, "."),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "or"),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( ( ( ( 3 . 4 ) or 5 ) or ( ( 4 . 5 ) . 4 ) ) or 5 ) #) ) \n ");
    }

    @Test
    public void test12() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "."),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "5"),
                new Token(OPERATOR, "or"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "or"),
                new Token(NUMBER, "5"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "."),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( ( ( ( 3 . 4 ) + 5 ) or 4 ) or ( 5 * ( 4 . 5 ) ) ) #) ) \n ");
    }

    @Test
    public void testE1() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "+"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( ( 3 + ! ) #) ) \n ");
    }

    @Test
    public void testInvalidBooleanOperator() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "~"),
                new Token(NUMBER, "3"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( 3 ! ) ! ! ~ 3 ! ! #) \n ");
    }

    @Test
    public void testAsExpression() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "as"),
                new Token(CLASS_IDENTIFIER, "String")
        ));

        testParser(tokens, "( 4 as String ) ");
    }


}