package de.uni.bremen.monty.mode.lexer;

import com.intellij.psi.tree.IElementType;

public class TokenRange {
    public final IElementType element;
    public final int tokenStart;
    public final int tokenEnd;

    public TokenRange(IElementType element, int tokenStart, int tokenEnd) {
        this.element = element;
        this.tokenStart = tokenStart;
        this.tokenEnd = tokenEnd;
    }
}
