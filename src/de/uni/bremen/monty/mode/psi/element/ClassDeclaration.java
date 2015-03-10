package de.uni.bremen.monty.mode.psi.element;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;

public class ClassDeclaration extends ASTWrapperPsiElement {
    public ClassDeclaration(ASTNode node) {
        super(node);
    }
}
