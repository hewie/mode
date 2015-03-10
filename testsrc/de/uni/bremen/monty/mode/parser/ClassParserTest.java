package de.uni.bremen.monty.mode.parser;

import de.uni.bremen.monty.mode.parser.util.TestParserBase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassParserTest extends TestParserBase {
    @Test
    public void testNormalClass() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "Ab"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( Ab ) : \n  pass \n ) ");
    }

    @Test
    public void testAbstractClass() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "abstract"),
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( abstract class ( AAb ) : \n  pass \n ) ");
    }

    @Test
    public void testGenericClass() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(OPERATOR, "<"),
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(OPERATOR, ">"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb < ( Bb ) > ) : \n  pass \n ) ");
    }

    @Test
    public void testMultipleGenericClass() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(OPERATOR, "<"),
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(COMMA, ","),
                new Token(CLASS_IDENTIFIER, "Cc"),
                new Token(OPERATOR, ">"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb < ( Bb ) , ( Cc ) > ) : \n  pass \n ) ");
    }

    @Test
    public void testInheritingClass() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(KEYWORD, "inherits"),
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb ) inherits ( Bb ) : \n  pass \n ) ");
    }

    @Test
    public void testMultipleInheritingClass() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(KEYWORD, "inherits"),
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(COMMA, ","),
                new Token(CLASS_IDENTIFIER, "Cc"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb ) inherits ( Bb ) , ( Cc ) : \n  pass \n ) ");
    }

    @Test
    public void testClassWithoutAdditionalEOLBeforeDedent() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(OPERATOR, "+"),
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(IDENTIFIER, "b"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb ) : \n  + ( ( Bb ) b ) \n ) ");
    }

    @Test
    public void testClassWithoutAdditionalEOLBeforeDedentII() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(OPERATOR, "+"),
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(IDENTIFIER, "b"),
                new Token(EOL, "\n"),
                new Token(OPERATOR, "+"),
                new Token(CLASS_IDENTIFIER, "Cc"),
                new Token(IDENTIFIER, "c"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb ) : \n  + ( ( Bb ) b ) \n + ( ( Cc ) c ) \n ) ");
    }

    @Test
    public void testClassWithInitializer() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "Ab"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(OPERATOR, "-"),
                new Token(KEYWORD, "initializer"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( Ab ) : \n  - initializer ( #( #) : \n  pass \n ) \n ) ");
    }

    @Test
    public void testInitializerWithoutParams() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "Ab"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(OPERATOR, "-"),
                new Token(KEYWORD, "initializer"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( Ab ) : \n  - initializer ( ! : \n  pass \n ) \n ) ");
    }

    @Test
    public void testClassWithMissingColon() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "abstract"),
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( abstract class ( AAb ) ! \n  pass \n ) ");
    }

    @Test
    public void testClassWithMissingIndent() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "abstract"),
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(COLON, ":"),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( abstract class ( AAb ) : ! pass \n ) ");
    }

    @Test
    public void testClassWithMissingColonAndIndent() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "abstract"),
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( abstract class ( AAb ) ! ! pass \n ) ");
    }

    @Test
    public void testClassWithEmptyGenerics() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(OPERATOR, "<"),
                new Token(OPERATOR, ">"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb < ! > ) : \n  pass \n ) ");
    }

    @Test
    public void testClassWithUnclosedGenerics() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(OPERATOR, "<"),
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb < ( Bb ) ! ) : \n  pass \n ) ");
    }

    @Test
    public void testClassWithUnfinishedGenerics() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(OPERATOR, "<"),
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(COMMA, ","),
                new Token(OPERATOR, ">"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb < ( Bb ) , ! > ) : \n  pass \n ) ");
    }

    @Test
    public void testClassWithOpenInheriting() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(KEYWORD, "inherits"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb ) inherits ! : \n  pass \n ) ");
    }

    @Test
    public void testClassWithUnfinishedInheriting() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "AAb"),
                new Token(KEYWORD, "inherits"),
                new Token(CLASS_IDENTIFIER, "Bb"),
                new Token(COMMA, ","),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( AAb ) inherits ( Bb ) , ! : \n  pass \n ) ");
    }

    @Test
    public void testClassWithUnexpectedBody() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "Ab"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(NUMBER, "4"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( Ab ) : \n  !( 4 !) \n ) ");
    }

    @Test
    public void testClassWithUnfinishedMember() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "Ab"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(OPERATOR, "#"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( Ab ) : \n  # ! \n ) ");
    }

    @Test
    public void testClassWithMemberWithoutAccessModifier() throws Exception {
        final List<Token> tokens = new ArrayList<>(Arrays.asList(
                new Token(KEYWORD, "class"),
                new Token(CLASS_IDENTIFIER, "Ab"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "initializer"),
                new Token(PARENTHESES, "("),
                new Token(PARENTHESES, ")"),
                new Token(COLON, ":"),
                new Token(EOL_INDENT, "\n "),
                new Token(KEYWORD, "pass"),
                new Token(EOL_DEDENT, "\n"),
                new Token(EOL_DEDENT, "\n")
        ));

        testParser(tokens, "( class ( Ab ) : \n  ! initializer ( #( #) : \n  pass \n ) \n ) ");
    }
}