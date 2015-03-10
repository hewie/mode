package de.uni.bremen.monty.mode;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import de.uni.bremen.monty.mode.lexer.TokenRange;
import de.uni.bremen.monty.mode.lexer.matcher.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MontyLexer extends LexerBase implements MontyElementTypes {

    private CharSequence buffer;
    private int startOffset;
    private int endOffset;
    private int currentState;

    private TokenRange currentToken;
    private CharSequence nextElement;
    private Stack<Integer> indentationStack;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {

        this.buffer = buffer;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.currentState = initialState;
        this.indentationStack = new Stack<Integer>();
        this.indentationStack.push(0);
        advance();
    }

    @Override
    public int getState() {
        return currentState;
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        return currentToken.element;
    }

    @Override
    public int getTokenStart() {
        return currentToken.tokenStart;
    }

    @Override
    public int getTokenEnd() {
        return currentToken.tokenEnd;
    }

    @Override
    public void advance() {

        int endNextToken = currentState;
        while (endNextToken < endOffset && !Character.isWhitespace(buffer.charAt(endNextToken++))) ;
        nextElement = buffer.subSequence(currentState, endNextToken);

        currentToken = nextToken();
        currentState = currentToken.tokenEnd;
    }

    private TokenRange nextToken() {
        if (currentState >= endOffset) {
            return new TokenRange(null, currentState, currentState);
        }
        for (BaseMatcher matcher : matchers) {
            TokenRange newToken = matcher.matches();
            if (newToken != null) {
                return newToken;
            }
        }

        return new TokenRange(ERROR, currentState, currentState + 1);
    }

    private List<? extends BaseMatcher> matchers = Arrays.asList(
            new WhitespaceMatcher(this),
            new CommentMatcher(this), new StringMatcher(this), new NumberMatcher(this),
            new SingleMatcher(this), new IdentifierMatcher(this)
    );

    @NotNull
    @Override
    public CharSequence getBufferSequence() {
        return buffer;
    }

    @Override
    public int getBufferEnd() {
        return endOffset;
    }

    public CharSequence getBuffer() {
        return buffer;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public int getCurrentState() {
        return currentState;
    }

    public TokenRange getCurrentToken() {
        return currentToken;
    }

    public CharSequence getNextElement() {
        return nextElement;
    }

    public Stack<Integer> getIndentationStack() {
        return indentationStack;
    }
}