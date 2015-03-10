package de.uni.bremen.monty.mode.parser;

import de.uni.bremen.monty.mode.parser.util.TestParserBase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeclarationParserTest extends TestParserBase {

    @Test
    public void testProcedure() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "A"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( calc #( ( ( ( A ) a ) ) #) : \n  pass \n ) ");
    }

    @Test
    public void testProcedureWithEofAsDedent() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "A"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass")
        ));

        testParser(tokens, "( calc #( ( ( ( A ) a ) ) #) : \n  pass ) ");
    }

    @Test
    public void testProcedureWithUnexpectedBody() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "A"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(IDENTIFIER, "error"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( calc #( ( ( ( A ) a ) ) #) : \n  pass !( error !) \n ) ");
    }

    @Test
    public void testModuleVariable() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( ( Aa ) a ) \n ");
    }

    @Test
    public void testModuleVariableWithAssignment() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(NUMBER, "4"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( ( ( Aa ) a ) := 4 ) \n ");
    }

    @Test
    public void testModuleVariableWithUnfinishedAssignment() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( ( ( Aa ) a ) := ! ) \n ");
    }

    @Test
    public void testModuleConstant() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(CONSTANT_IDENTIFIER, "A"),
                new Token(ASSIGNMENT, ":="),
                new Token(NUMBER, "4"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( ( ( Aa ) A ) := 4 ) \n ");
    }

    @Test
    public void testModuleConstantWithoutAssignment() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(CONSTANT_IDENTIFIER, "A"),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( ( Aa ) A ) ! \n ");
    }

    @Test
    public void testModuleConstantWithUnfinishedAssignment() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(CONSTANT_IDENTIFIER, "A"),
                new Token(ASSIGNMENT, ":="),
                new Token(EOL, "\n")
        ));

        testParser(tokens, "( ( ( Aa ) A ) := ! ) \n ");
    }

    @Test
    public void testFunction() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(IDENTIFIER, "calc"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) calc #( ( ( ( Aa ) a ) ) #) : \n  pass \n ) ");
    }

    @Test
    public void testOperation() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(KEYWORD, "operator"),
                new Token(OPERATOR, "+"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) operator + #( ( ( ( Aa ) a ) ) #) : \n  pass \n ) ");
    }

    @Test
    public void testUnfinishedOperation() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(KEYWORD, "operator"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) operator ! #( ( ( ( Aa ) a ) ) #) : \n  pass \n ) ");
    }

    @Test
    public void testBracketOperation() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(KEYWORD, "operator"),
                new Token(BRACKETS, "["),
                new Token(BRACKETS, "]"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) operator [ ] #( ( ( ( Aa ) a ) ) #) : \n  pass \n ) ");
    }

    @Test
    public void testUnfinishedBracketOperation() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(KEYWORD, "operator"),
                new Token(BRACKETS, "["),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) operator [ ! #( ( ( ( Aa ) a ) ) #) : \n  pass \n ) ");
    }

    @Test
    public void testInvalidOperation() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(KEYWORD, "operator"),
                new Token(OPERATOR, "~"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) operator !( ~ !) #( ( ( ( Aa ) a ) ) #) : \n  pass \n ) ");
    }

    @Test
    public void testDefaultParamater() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(IDENTIFIER, "b"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(NUMBER, "4"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) b #( ( ( ( Aa ) a ) := 4 ) #) : \n  pass \n ) ");
    }

    @Test
    public void testUnfinishedDefaultParamater() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(IDENTIFIER, "b"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) b #( ( ( ( Aa ) a ) := ! ) #) : \n  pass \n ) ");
    }

    @Test
    public void testTwoDefaultParamater() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(IDENTIFIER, "b"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(NUMBER, "4"),
                new Token(COMMA, ","),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "b"),
                new Token(ASSIGNMENT, ":="),
                new Token(NUMBER, "5"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) b #( ( ( ( Aa ) a ) := 4 ) , ( ( ( Aa ) b ) := 5 ) #) : \n  pass \n ) ");
    }

    @Test
    public void testNeededDefaultParamater() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(IDENTIFIER, "b"),
                new Token(PARENTHESES, "("),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "a"),
                new Token(ASSIGNMENT, ":="),
                new Token(NUMBER, "4"),
                new Token(COMMA, ","),
                new Token(CLASS_IDENTIFIER, "Aa"),
                new Token(IDENTIFIER, "b"),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( ( Bb ) b #( ( ( ( Aa ) a ) := 4 ) , ( !( ( ( Aa ) b ) !) ) #) : \n  pass \n ) ");
    }


    //TODO type missing
//    @Test
//    public void test1() throws Exception {
//        final List<Token> tokens = new ArrayList<>(Arrays.asList(
//                new Token(IDENTIFIER, "calc"),
//                new Token(PARENTHESES, "("),
////                new Token(CLASS_IDENTIFIER, "A"),
//                new Token(IDENTIFIER, "a"),
//                new Token(PARENTHESES, ")"),
//                new Token(COLON, ":"),
//                new Token(EOL_INDENT, "\n "),
//                new Token(KEYWORD, "pass"),
//                new Token(EOL_DEDENT, "\n")
//        ));
//
//        testParser(tokens, "( ( calc #( 3 ) \n ) ) ");
//    }
}