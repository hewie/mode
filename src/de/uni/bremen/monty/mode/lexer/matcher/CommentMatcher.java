package de.uni.bremen.monty.mode.lexer.matcher;

import de.uni.bremen.monty.mode.MontyElementTypes;
import de.uni.bremen.monty.mode.MontyLexer;
import de.uni.bremen.monty.mode.lexer.TokenRange;

public class CommentMatcher extends BaseMatcher {

    public CommentMatcher(MontyLexer montyLexer) {
        super(montyLexer);
    }

    @Override
    public TokenRange matches() {
        int currentState = this.getCurrentState();
        int startIndex = currentState;
        if (getBuffer().charAt(currentState) == '/') {
            currentState++;
            if (currentState < getEndOffset() && getBuffer().charAt(currentState) == '/') {
                while (currentState < getEndOffset()) {
                    if (getBuffer().charAt(currentState) == '\n' || getBuffer().charAt(currentState) == '\r') {
                        break;
                    }
                    currentState++;
                }
                return new TokenRange(MontyElementTypes.COMMENT, startIndex, currentState);
            }
        }
        return null;
    }
}
