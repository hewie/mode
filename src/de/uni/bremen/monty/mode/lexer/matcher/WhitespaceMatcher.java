package de.uni.bremen.monty.mode.lexer.matcher;

import de.uni.bremen.monty.mode.MontyElementTypes;
import de.uni.bremen.monty.mode.MontyLexer;
import de.uni.bremen.monty.mode.lexer.TokenRange;

public class WhitespaceMatcher extends BaseMatcher {

    public WhitespaceMatcher(MontyLexer montyLexer) {
        super(montyLexer);
    }

    @Override
    public TokenRange matches() {
        int currentState = this.getCurrentState();
        int startIndex = currentState;
        TokenRange newLine = newLine(currentState, startIndex);
        if (newLine != null) {
            currentState = newLine.tokenEnd;
            if (isChar(currentState, ' ')) {
                while (isChar(currentState, ' ')) {
                    currentState++;
                }
                if ((
                        isChar(currentState, '\r') ||
                                isChar(currentState, '\n'))) {
                    return new TokenRange(MontyElementTypes.EOL, startIndex, newLine.tokenEnd);
                }
                int currentIndent = currentState - startIndex;
                int currentLevel = getIndentationStack().peek();
                if (currentIndent == currentLevel) {
                    return new TokenRange(MontyElementTypes.EOL, startIndex, newLine.tokenEnd);
                } else if (currentIndent > currentLevel) {
                    getIndentationStack().push(currentIndent);
                    return new TokenRange(MontyElementTypes.EOL_INDENT, startIndex, currentState);
                } else {
                    getIndentationStack().pop();
                    int previousLevel = getIndentationStack().peek();
                    if (currentIndent == previousLevel) {
                        return new TokenRange(MontyElementTypes.EOL_DEDENT, startIndex, currentState);
                    } else if (currentIndent > previousLevel) {
                        return new TokenRange(MontyElementTypes.BAD_DEDENT, startIndex, currentState);
                    } else {
                        return new TokenRange(MontyElementTypes.EOL_DEDENT, startIndex, startIndex + previousLevel);
                    }
                }
            } else if (getIndentationStack().peek() > 0 && !isChar(currentState, '\n') && !isChar(currentState, '\r')) {
                getIndentationStack().pop();
                return new TokenRange(MontyElementTypes.EOL_DEDENT, startIndex, currentState);
            }
            return newLine;
        }

        if (isWhitespace(currentState)) {
            while (isWhitespace(currentState) && !isChar(currentState, '\n') && !isChar(currentState, '\r')) {
                currentState++;
            }
            return new TokenRange(MontyElementTypes.WHITESPACE, startIndex, currentState);
        }

        return null;
    }

    private TokenRange newLine(int currentState, int startIndex) {
        if (isChar(currentState, '\r')) {
            currentState++;
            if (currentState < this.getEndOffset() && isChar(currentState, '\n')) {
                return new TokenRange(MontyElementTypes.EOL, startIndex, currentState + 1);
            }
            return new TokenRange(MontyElementTypes.ERROR, startIndex, currentState);
        }
        if (isChar(currentState, '\n')) {
            int i = 1;
            boolean foundOne = false;
            while (isChar(currentState + i, '\n')) {
                i++;
                foundOne = true;
            }

            if (foundOne && !isChar(currentState + i, ' ') && getIndentationStack().peek() > 0) {
                getIndentationStack().pop();
                return new TokenRange(MontyElementTypes.EOL_DEDENT, startIndex, currentState + 1);
            } else {
                return new TokenRange(MontyElementTypes.EOL, startIndex, currentState + 1);
            }
        }
        return null;
    }

    private boolean isWhitespace(int currentState) {
        if (currentState >= this.getEndOffset()) return false;
        char currentChar = getBuffer().charAt(currentState);
        return Character.isWhitespace(currentChar);
    }

    private boolean isChar(int currentState, char c) {
        return currentState < this.getEndOffset() && this.getBuffer().charAt(currentState) == c;
    }
}
