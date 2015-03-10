package de.uni.bremen.monty.mode;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;

public class MontyElementType extends IElementType {

    public MontyElementType(String debugName) {
        super(debugName, MontyLanguage.INSTANCE);
    }

    public static class MontyFileElementType extends IFileElementType {
        public MontyFileElementType() {
            super("File", MontyLanguage.INSTANCE);
        }
    }
}
