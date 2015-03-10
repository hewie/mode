package de.uni.bremen.monty.mode.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import de.uni.bremen.monty.mode.MontyElementTypes;
import de.uni.bremen.monty.mode.psi.element.ClassDeclaration;

public class MontyElementCreator implements MontyElementTypes {

    private static final Logger log = Logger.getInstance("#MontyElementCreator");

    public ASTWrapperPsiElement create(ASTNode node) {
        if (node.getElementType().equals(CLASS_DECLARATION)) {
            return new ClassDeclaration(node);
        } else {
//            log.warn("MISSING PSI for" + node);
            return new ASTWrapperPsiElement(node);
        }
    }
}
