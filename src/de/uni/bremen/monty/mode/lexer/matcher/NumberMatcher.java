package de.uni.bremen.monty.mode.lexer.matcher;

import com.intellij.psi.tree.IElementType;
import de.uni.bremen.monty.mode.MontyElementTypes;
import de.uni.bremen.monty.mode.MontyLexer;

public class NumberMatcher extends RangeMatcher {

    public NumberMatcher(MontyLexer montyLexer) {
        super(montyLexer);
    }

    @Override
    public IElementType token() {
        return MontyElementTypes.NUMBER;
    }

    @Override
    public boolean isValidChar(char c) {
        return c >= 48 && c <= 57;
    }
}
