package de.uni.bremen.monty.mode;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class MontyLexerTest {

    @Test
    public void test1() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "\"asdf\"";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), (sameInstance((IElementType) MontyElementTypes.STRING)));
        assertThat(montyLexer.getState(), is(input.length()));
    }

    @Test
    public void test2() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "12345";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.NUMBER));
        assertThat(montyLexer.getState(), is(5));
    }

    @Test
    public void test3() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "\"abc\"12344";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.STRING));
        assertThat(montyLexer.getState(), is(5));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.NUMBER));
        assertThat(montyLexer.getState(), is(input.length()));
    }

    @Test
    public void test4() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "12345\"abc\"";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.NUMBER));
        assertThat(montyLexer.getState(), is(5));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.STRING));
        assertThat(montyLexer.getState(), is(input.length()));
    }

    @Test
    public void test5() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "\"";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.STRING));
        assertThat(montyLexer.getState(), is(1));
    }

    @Test
    public void test6() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "\"\"";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.STRING));
        assertThat(montyLexer.getState(), is(2));
    }

    @Test
    public void test7() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "{[()]}";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.BRACES));
        assertThat(montyLexer.getState(), is(1));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.BRACKETS));
        assertThat(montyLexer.getState(), is(2));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.PARENTHESES));
        assertThat(montyLexer.getState(), is(3));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.PARENTHESES));
        assertThat(montyLexer.getState(), is(4));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.BRACKETS));
        assertThat(montyLexer.getState(), is(5));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.BRACES));
        assertThat(montyLexer.getState(), is(6));
    }

    @Test
    public void test8() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "{77";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.BRACES));
        assertThat(montyLexer.getState(), is(1));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.NUMBER));
        assertThat(montyLexer.getState(), is(3));
    }

    @Test
    public void test9() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "(true)";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.PARENTHESES));
        assertThat(montyLexer.getState(), is(1));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.KEYWORD));
        assertThat(montyLexer.getState(), is(5));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.PARENTHESES));
        assertThat(montyLexer.getState(), is(6));
    }

    @Test
    public void test10() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "hallo";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(5));
    }

    @Test
    public void test11() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "class Ab<Ba>: ";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.KEYWORD));
        assertThat(montyLexer.getState(), is(5));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance(TokenType.WHITE_SPACE));
        assertThat(montyLexer.getState(), is(6));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.CLASS_IDENTIFIER));
        assertThat(montyLexer.getState(), is(8));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.OPERATOR));
        assertThat(montyLexer.getState(), is(9));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.CLASS_IDENTIFIER));
        assertThat(montyLexer.getState(), is(11));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.OPERATOR));
        assertThat(montyLexer.getState(), is(12));
    }

    @Test
    public void test12() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "initializer: () skipMe";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.KEYWORD));
        assertThat(montyLexer.getState(), is(11));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.COLON));
        assertThat(montyLexer.getState(), is(12));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance(TokenType.WHITE_SPACE));
        assertThat(montyLexer.getState(), is(13));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.PARENTHESES));
        assertThat(montyLexer.getState(), is(14));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.PARENTHESES));
        assertThat(montyLexer.getState(), is(15));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance(TokenType.WHITE_SPACE));
        assertThat(montyLexer.getState(), is(16));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(22));
    }

    @Test
    public void test13() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "pass";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.KEYWORD));
        assertThat(montyLexer.getState(), is(4));
    }

    @Test
    public void test14() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "A BB __B _A _B_B_ _B5_5_3 AA0 ";
        montyLexer.start(input, 0, input.length(), 0);

        String[] inputs = input.split(" ");
        for (String s : inputs) {
            assertThat(s, montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.CONSTANT_IDENTIFIER));
            montyLexer.advance();
            assertThat(montyLexer.getTokenType(), sameInstance(TokenType.WHITE_SPACE));
            montyLexer.advance();
        }

        for (String s : inputs) {
            montyLexer.start(s, 0, s.length(), 0);
            assertThat(s, montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.CONSTANT_IDENTIFIER));
        }
    }

    @Test
    public void test15() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "AbbA Abba Ab AAa AAab AAab AbbAA ";
        montyLexer.start(input, 0, input.length(), 0);

        String[] inputs = input.split(" ");
        for (String s : inputs) {
            assertThat(s, montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.CLASS_IDENTIFIER));
            montyLexer.advance();
            assertThat(montyLexer.getTokenType(), sameInstance(TokenType.WHITE_SPACE));
            montyLexer.advance();
        }

        for (String s : inputs) {
            montyLexer.start(s, 0, s.length(), 0);
            assertThat(s, montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.CLASS_IDENTIFIER));
        }
    }

    @Test
    public void test16() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "i ia i5 iO i_5 _aA _a _aA0 ";
        montyLexer.start(input, 0, input.length(), 0);

        String[] inputs = input.split(" ");
        for (String s : inputs) {
            assertThat(s, montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
            montyLexer.advance();
            assertThat(montyLexer.getTokenType(), sameInstance(TokenType.WHITE_SPACE));
            montyLexer.advance();
        }

        for (String s : inputs) {
            montyLexer.start(s, 0, s.length(), 0);
            assertThat(s, montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        }
    }

    @Test
    public void test17() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "A_a Bb_ _ _4 _Aa ";
        montyLexer.start(input, 0, input.length(), 0);

        String[] inputs = input.split(" ");


        for (String s : inputs) {
            assertThat(montyLexer.getTokenType(), sameInstance(TokenType.BAD_CHARACTER));
            montyLexer.advance();
            assertThat(montyLexer.getTokenType(), sameInstance(TokenType.WHITE_SPACE));
            montyLexer.advance();
        }

        for (String s : inputs) {
            montyLexer.start(s, 0, s.length(), 0);
            assertThat(montyLexer.getTokenType(), sameInstance(TokenType.BAD_CHARACTER));
        }
    }

    @Test
    public void test18() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "\"asdfasdf";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.STRING));
        assertThat(montyLexer.getState(), is(9));
    }

    @Test
    public void test19() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "\"asdfasdf\nsdfs\"";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.STRING));
        assertThat(montyLexer.getState(), is(9));
    }

    @Test
    public void test20() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "//comment";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.COMMENT));
        assertThat(montyLexer.getState(), is(9));
    }

    @Test
    public void test21() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "//comment\nhallo";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.COMMENT));
        assertThat(montyLexer.getState(), is(9));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL));
        assertThat(montyLexer.getState(), is(10));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(15));
    }

    @Test
    public void test22() throws Exception {
        MontyLexer montyLexer = new MontyLexer();
        String input = "a\n b\n  c\n   d\n  c\n b\na\na";
        montyLexer.start(input, 0, input.length(), 0);
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(1));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_INDENT));
        assertThat(montyLexer.getState(), is(3));
        montyLexer.advance();

        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(4));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_INDENT));
        assertThat(montyLexer.getState(), is(7));
        montyLexer.advance();

        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(8));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_INDENT));
        assertThat(montyLexer.getState(), is(12));
        montyLexer.advance();

        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(13));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_DEDENT));
        assertThat(montyLexer.getState(), is(16));
        montyLexer.advance();

        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(17));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_DEDENT));
        assertThat(montyLexer.getState(), is(19));
        montyLexer.advance();

        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(20));
        montyLexer.advance();
        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_DEDENT));
        assertThat(montyLexer.getState(), is(21));
        montyLexer.advance();

        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
        assertThat(montyLexer.getState(), is(22));
    }

//    @Test
//    public void test23() throws Exception {
//        MontyLexer montyLexer = new MontyLexer();
//        String input = "class\n fun\n  pass\nAa\n";
//        montyLexer.start(input, 0, input.length(), 0);
//        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.KEYWORD));
////        assertThat(montyLexer.getState(), is(9));
//        montyLexer.advance();
//        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_INDENT));
////        assertThat(montyLexer.getState(), is(9));
//        montyLexer.advance();
//        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.IDENTIFIER));
////        assertThat(montyLexer.getState(), is(10));
//        montyLexer.advance();
//        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_INDENT));
////        assertThat(montyLexer.getState(), is(9));
//        montyLexer.advance();
//        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.KEYWORD));
////        assertThat(montyLexer.getState(), is(15));
//        montyLexer.advance();
//        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_DEDENT));
////        assertThat(montyLexer.getState(), is(9));
//        montyLexer.advance();
//        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.EOL_DEDENT));
////        assertThat(montyLexer.getState(), is(9));
//        montyLexer.advance();
//        assertThat(montyLexer.getTokenType(), sameInstance((IElementType) MontyElementTypes.CLASS_IDENTIFIER));
////        assertThat(montyLexer.getState(), is(9));
//    }

}