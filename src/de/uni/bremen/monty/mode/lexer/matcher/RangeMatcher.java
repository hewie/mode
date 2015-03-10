package de.uni.bremen.monty.mode.lexer.matcher;

import com.intellij.psi.tree.IElementType;
import de.uni.bremen.monty.mode.MontyLexer;
import de.uni.bremen.monty.mode.lexer.TokenRange;

public abstract class RangeMatcher extends BaseMatcher {

    public RangeMatcher(MontyLexer montyLexer) {
        super(montyLexer);
    }

    @Override
    final public TokenRange matches() {
        char startChar = this.getBuffer().charAt(this.getCurrentState());
        int currentState = this.getCurrentState();
        int startIndex = currentState;

        if (isValidChar(startChar)) {
            while (currentState < this.getEndOffset() && isValidChar(this.getBuffer().charAt(currentState))) {
                currentState++;
            }
            return new TokenRange(token(), startIndex, currentState);
        } else return null;
    }

    public abstract IElementType token();

    public abstract boolean isValidChar(char c);
}
