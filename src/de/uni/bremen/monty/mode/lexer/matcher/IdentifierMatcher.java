package de.uni.bremen.monty.mode.lexer.matcher;

import de.uni.bremen.monty.mode.MontyElementTypes;
import de.uni.bremen.monty.mode.MontyLexer;
import de.uni.bremen.monty.mode.lexer.TokenRange;

public class IdentifierMatcher extends BaseMatcher {

    public IdentifierMatcher(MontyLexer montyLexer) {
        super(montyLexer);
    }

    @Override
    final public TokenRange matches() {
        char startChar = getBuffer().charAt(getCurrentState());
        int currentState = this.getCurrentState();
        int startIndex = currentState;

        if (isUnderscore(startChar)) {
            while (currentState < getEndOffset() && isUnderscore(getBuffer().charAt(currentState))) {
                currentState++;
            }
            if (currentState < getEndOffset()) {
                if (isUppercase(getBuffer().charAt(currentState))) {
                    while (currentState < getEndOffset() && isConstantBody(getBuffer().charAt(currentState))) {
                        currentState++;
                    }
                    if (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                        while (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                            currentState++;
                        }
                        return new TokenRange(MontyElementTypes.ERROR, startIndex, currentState);
                    }
                    return new TokenRange(MontyElementTypes.CONSTANT_IDENTIFIER, startIndex, currentState);
                } else if (isLowercase(getBuffer().charAt(currentState))) {
                    while (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                        currentState++;
                    }
                    return new TokenRange(MontyElementTypes.IDENTIFIER, startIndex, currentState);
                }
            }
            if (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                while (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                    currentState++;
                }
                return new TokenRange(MontyElementTypes.ERROR, startIndex, currentState);
            }
        } else if (isUppercase(startChar)) {
            while (currentState < getEndOffset() && isUppercaseOrDigit(getBuffer().charAt(currentState))) {
                currentState++;
            }
            if (currentState < getEndOffset()) {
                if (isLowercase(getBuffer().charAt(currentState))) {
                    while (currentState < getEndOffset() && isClassBody(getBuffer().charAt(currentState))) {
                        currentState++;
                    }
                    if (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                        while (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                            currentState++;
                        }
                        return new TokenRange(MontyElementTypes.ERROR, startIndex, currentState);
                    }
                    return new TokenRange(MontyElementTypes.CLASS_IDENTIFIER, startIndex, currentState);
                } else {
                    while (currentState < getEndOffset() && isConstantBody(getBuffer().charAt(currentState))) {
                        currentState++;
                    }
                    if (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                        while (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                            currentState++;
                        }
                        return new TokenRange(MontyElementTypes.ERROR, startIndex, currentState);
                    }
                    return new TokenRange(MontyElementTypes.CONSTANT_IDENTIFIER, startIndex, currentState);
                }
            }
            return new TokenRange(MontyElementTypes.CONSTANT_IDENTIFIER, startIndex, currentState);
        } else if (isLowercase(startChar)) {
            while (currentState < getEndOffset() && isIdentifierBody(getBuffer().charAt(currentState))) {
                currentState++;
            }
            return new TokenRange(MontyElementTypes.IDENTIFIER, startIndex, currentState);
        }
        return null;
    }


    private boolean isUppercaseOrDigit(char c) {
        return isUppercase(c) || isDigit(c);
    }

    private boolean isIdentifierBody(char c) {
        return isConstantBody(c) || isLowercase(c);
    }

    private boolean isClassBody(char c) {
        return isUppercase(c) || isLowercase(c) || isDigit(c);
    }

    private boolean isConstantBody(char c) {
        return isUppercase(c) || isUnderscore(c) || isDigit(c);
    }

    private boolean isUnderscore(char c) {
        return c == '_';
    }

    private boolean isDigit(char c) {
        return c >= 48 && c <= 57;
    }

    private boolean isUppercase(char c) {
        return c >= 65 && c <= 90;
    }

    private boolean isLowercase(char c) {
        return c >= 97 && c <= 122;
    }

}
