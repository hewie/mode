package de.uni.bremen.monty.mode.lexer.matcher;

import de.uni.bremen.monty.mode.MontyElementTypes;
import de.uni.bremen.monty.mode.MontyLexer;
import de.uni.bremen.monty.mode.lexer.TokenRange;

public class StringMatcher extends BaseMatcher {

    public StringMatcher(MontyLexer montyLexer) {
        super(montyLexer);
    }

    @Override
    public TokenRange matches() {
        int currentState = this.getCurrentState();
        int startIndex = currentState;
        if (this.getBuffer().charAt(currentState) == '"') {
            currentState++;
            while (currentState < this.getEndOffset()) {
                if (this.getBuffer().charAt(currentState) == '"') {
                    currentState++;
                    break;
                } else if (this.getBuffer().charAt(currentState) == '\n' || this.getBuffer().charAt(currentState) == '\r') {
                    break;
                }
                currentState++;
            }
            return new TokenRange(MontyElementTypes.STRING, startIndex, currentState);
        } else {
            return null;
        }
    }
}
