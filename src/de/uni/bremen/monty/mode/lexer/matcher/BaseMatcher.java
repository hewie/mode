package de.uni.bremen.monty.mode.lexer.matcher;

import de.uni.bremen.monty.mode.MontyLexer;
import de.uni.bremen.monty.mode.lexer.TokenRange;

import java.util.Stack;

public abstract class BaseMatcher {

    private MontyLexer lexer;

    public BaseMatcher(MontyLexer lexer) {
        this.lexer = lexer;
    }

    public abstract TokenRange matches();

    public CharSequence getBuffer() {
        return lexer.getBuffer();
    }

    public int getStartOffset() {
        return lexer.getStartOffset();
    }

    public int getEndOffset() {
        return lexer.getEndOffset();
    }

    public int getCurrentState() {
        return lexer.getCurrentState();
    }

    public TokenRange getCurrentToken() {
        return lexer.getCurrentToken();
    }

    public CharSequence getNextElement() {
        return lexer.getNextElement();
    }

    public Stack<Integer> getIndentationStack() {
        return lexer.getIndentationStack();
    }
}
