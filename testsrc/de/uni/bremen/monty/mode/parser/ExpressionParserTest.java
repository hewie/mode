package de.uni.bremen.monty.mode.parser;

import de.uni.bremen.monty.mode.parser.util.TestParserBase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionParserTest extends TestParserBase {

    @Test
    public void testFunctionCallWithoutArguments() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( #) ) \n ");
    }

    @Test
    public void testFunctionCallWithOneArgument() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( 3 #) ) \n ");
    }

    @Test
    public void testFunctionCallWithTwoArgument() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(COMMA, ","),
                new Token(NUMBER, "4"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( 3 , 4 #) ) \n ");
    }

    @Test
    public void testFunctionCallWithUnfinishedArgument() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(COMMA, ","),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( calc #( 3 , ! #) ) \n ");
    }

    @Test
    public void testInitializerCall() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "initializer"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( initializer #( 3 #) ) \n ");
    }

    @Test
    public void testUnfinishedInitializerCall() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "initializer"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( initializer ! ) \n ");
    }

    @Test
    public void testConstructorCall() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Ab"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(PARENTHESES, ")"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( ( Ab ) #( 3 #) ) \n ");
    }

    @Test
    public void testWhile() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(NUMBER, "3"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while 3 : \n  pass \n ) ");
    }

    @Test
    public void testUnfinishedWhile() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(NUMBER, "3"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while 3 ! \n  pass \n ) ");
    }

    @Test
    public void testIfElseExpr() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(NUMBER, "3"),
                new Token(KEYWORD, "if"),
                new Token(KEYWORD, "true"),
                new Token(KEYWORD, "else"),
                new Token(NUMBER, "5"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while ( 3 if true else 5 ) : \n  pass \n ) ");
    }

    @Test
    public void testIfExprWithoutElse() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(NUMBER, "3"),
                new Token(KEYWORD, "if"),
                new Token(KEYWORD, "true"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while ( 3 if true ! ) : \n  pass \n ) ");
    }

    @Test
    public void testIfExprWithoutCondition() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(NUMBER, "3"),
                new Token(KEYWORD, "if"),
                new Token(KEYWORD, "else"),
                new Token(NUMBER, "3"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while ( 3 if ! else 3 ) : \n  pass \n ) ");
    }

    @Test
    public void testMultipleIfExpr() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(NUMBER, "5"),
                new Token(KEYWORD, "if"),

                new Token(KEYWORD, "false"),
                new Token(KEYWORD, "if"),
                new Token(NUMBER, "3"),
                new Token(KEYWORD, "else"),
                new Token(NUMBER, "true"),

                new Token(KEYWORD, "else"),
                new Token(NUMBER, "3"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while ( 5 if ( false if 3 else true ) else 3 ) : \n  pass \n ) ");
    }

    @Test
    public void testBinaryAndIfExpr() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "5"),
                new Token(KEYWORD, "if"),

                new Token(KEYWORD, "false"),

                new Token(KEYWORD, "else"),
                new Token(NUMBER, "3"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while ( 3 + ( 5 if false else 3 ) ) : \n  pass \n ) ");
    }

    @Test
    public void testIfExprWithoutElseExpr() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(NUMBER, "3"),
                new Token(KEYWORD, "if"),
                new Token(NUMBER, "3"),
                new Token(KEYWORD, "else"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while ( 3 if 3 else ! ) : \n  pass \n ) ");
    }

    @Test
    public void testParenthesis() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "4"),
                new Token(PARENTHESES, ")"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "5"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while ( #( ( 3 + 4 ) #) * 5 ) : \n  pass \n ) ");
    }

    @Test
    public void testMissingClosingParenthesis() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(PARENTHESES, "("),
                new Token(NUMBER, "3"),
                new Token(OPERATOR, "+"),
                new Token(NUMBER, "4"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "5"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while #( ( 3 + ( 4 * 5 ) ) ! : \n  pass \n ) ");
    }

    @Test
    public void testNoExprWithingParanthesis() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")"),
                new Token(OPERATOR, "*"),
                new Token(NUMBER, "5"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while ( #( ! #) * 5 ) : \n  pass \n ) ");
    }

    @Test
    public void testUnfinishedUnary() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "while"),
                new Token(PARENTHESES, "("),
                new Token(OPERATOR, "not"),
                new Token(PARENTHESES, ")"),
                new Token(OPERATOR, "or"),
                new Token(IDENTIFIER, "a"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( while ( #( ( not ! ) #) or a ) : \n  pass \n ) ");
    }

    @Test
    public void testLargeArrayLiteral() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(BRACKETS, "["),
                new Token(NUMBER, "1"),
                new Token(COMMA, ","),
                new Token(NUMBER, "2"),
                new Token(COMMA, ","),
                new Token(NUMBER, "3"),
                new Token(BRACKETS, "]")
        ));

        testParser(tokens, "( ( ( Aa ) a ) := ( [ 1 , 2 , 3 ] ) ) ");
    }

    @Test
    public void testUnfinishedArrayLiteral() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(BRACKETS, "["),
                new Token(NUMBER, "1"),
                new Token(COMMA, ","),
                new Token(NUMBER, "2"),
                new Token(COMMA, ","),
                new Token(BRACKETS, "]")
        ));

        testParser(tokens, "( ( ( Aa ) a ) := ( [ 1 , 2 , ! ] ) ) ");
    }

    @Test
    public void testUnclosedArrayLiteral() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(BRACKETS, "["),
                new Token(NUMBER, "1"),
                new Token(COMMA, ","),
                new Token(NUMBER, "2")
        ));

        testParser(tokens, "( ( ( Aa ) a ) := ( [ 1 , 2 ! ) ) ");
    }

    @Test
    public void testArrayLiteralWithoutFirstExpr() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(BRACKETS, "["),
                new Token(COMMA, ","),
                new Token(NUMBER, "2"),
                new Token(BRACKETS, "]")
        ));

        testParser(tokens, "( ( ( Aa ) a ) := ( [ ! ) ) ! ! , 2 ! ! ] ");
    }

    @Test
    public void testParent() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "parent"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Ab"),
                new Token(PARENTHESES, ")"),
                new Token(DOT, "."),
                new Token(KEYWORD, "initializer"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")")
        ));

        testParser(tokens, "( ( parent #( Ab #) ) . ( initializer #( #) ) ) ");
    }
}