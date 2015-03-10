package de.uni.bremen.monty.mode.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.impl.PsiBuilderAdapter;

public class MontyPsiBuilder extends PsiBuilderAdapter {
    public MontyPsiBuilder(PsiBuilder delegate) {
        super(delegate);
    }

//    private IElementType lookBehind(int steps) {
////        int cur = myCurrentLexeme + steps;
////        return cur < myLexemeCount && cur >= 0 ? myLexTypes[cur] : null;
//        int offset = getCurrentOffset();
//
//        IElementType current = rawLookup(getCurrentOffset() - offset);
//
//        while()
//    }
}


