package de.uni.bremen.monty.mode.lexer.matcher;

import com.intellij.psi.tree.IElementType;
import de.uni.bremen.monty.mode.MontyElementTypes;
import de.uni.bremen.monty.mode.MontyLexer;
import de.uni.bremen.monty.mode.lexer.TokenRange;

public class SingleMatcher extends BaseMatcher implements MontyElementTypes {

    public SingleMatcher(MontyLexer montyLexer) {
        super(montyLexer);
    }

    private static class MatchableString {
        private final String[] values;
        private final IElementType token;

        MatchableString(IElementType token, String... values) {
            this.values = values;
            this.token = token;
        }
    }

    private static MatchableString[] singleCharacter = new MatchableString[]{
            new MatchableString(PARENTHESES, "(", ")"),
            new MatchableString(BRACES, "{", "}"),
            new MatchableString(BRACKETS, "[", "]"),
            new MatchableString(COMMA, ","),
            new MatchableString(DOT, ".", "->"),
            new MatchableString(ASSIGNMENT, ":=", "+=", "-=", "*=", "/=", "%=", "^="),
            new MatchableString(COLON, ":"),
            new MatchableString(OPERATOR,
                    "+", "-", "*", "/", "%", "^", "<=", ">=", "<", ">", "=", "!", "#", "~")
    };

    private static MatchableString[] multipleCharacters = new MatchableString[]{
            new MatchableString(KEYWORD, "abstract", "break", "class", "elif", "else", "false", "handle", "if", "import", "inherits",
                    "initializer", "operator", "parent", "pass", "raise", "return", "self", "skip", "true", "try", "while"),
            new MatchableString(OPERATOR, "is", "as", "or", "xor", "and", "in", "not")
    };

    @Override
    public TokenRange matches() {
        for (MatchableString type : singleCharacter) {
            for (String s : type.values) {
                if (this.getNextElement().length() >= s.length() &&
                        this.getNextElement().subSequence(0, s.length()).toString().equals(s)) {
                    return new TokenRange(type.token, this.getCurrentState(), this.getCurrentState() + s.length());
                }
            }
        }
        for (MatchableString type : multipleCharacters) {
            for (String s : type.values) {
                if (
                        this.getNextElement().length() >= s.length() &&
                                this.getNextElement().subSequence(0, s.length()).toString().equalsIgnoreCase(s)) {
                    if (s.length() == this.getNextElement().length() || !Character.isJavaIdentifierPart(this.getNextElement().charAt(s.length()))) {
                        return new TokenRange(type.token, this.getCurrentState(), this.getCurrentState() + s.length());
                    }
                }
            }
        }
        return null;
    }
}
