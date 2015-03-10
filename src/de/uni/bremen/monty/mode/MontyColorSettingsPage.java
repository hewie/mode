package de.uni.bremen.monty.mode;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collections;
import java.util.Map;

public class MontyColorSettingsPage implements ColorSettingsPage {
    @Nullable
    @Override
    public Icon getIcon() {
        return MontyFileType.INSTANCE.getIcon();
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new MontySyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "class Ab<Ba>:\n  - Ba b\n  + initializer(Ba v):\n    self.setB(v)\n    Ba var := self.getB()\n\n  + Ba getB():\n    return self.b\n\n  + setB(Ba b):\n    self.b := b\n\n\nAb<String> x := Ab<String>(\"Monty!\")\nString s := x.getB()\nprint(s)\n\nAb<String> copy(Ab<String> source):\n  return Ab<String>(source.b)\n\nprint(copy(x).b)";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return Collections.emptyMap();
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return new AttributesDescriptor[]{
                new AttributesDescriptor("Keyword", MontySyntaxHighlighter.KEYWORD),

                new AttributesDescriptor("Identifier", MontySyntaxHighlighter.IDENTIFIER),
                new AttributesDescriptor("Constant", MontySyntaxHighlighter.CONSTANT_IDENTIFIER),
                new AttributesDescriptor("Class Name", MontySyntaxHighlighter.CLASS_IDENTIFIER),

                new AttributesDescriptor("String", MontySyntaxHighlighter.STRING_LITERAL),
                new AttributesDescriptor("Number", MontySyntaxHighlighter.NUMBER_LITERAL),

                new AttributesDescriptor("Parentheses", MontySyntaxHighlighter.PARENTHESES),
                new AttributesDescriptor("Braces", MontySyntaxHighlighter.BRACES),
                new AttributesDescriptor("Brackets", MontySyntaxHighlighter.BRACKETS),

                new AttributesDescriptor("Comma", MontySyntaxHighlighter.COMMA_TOKEN),
                new AttributesDescriptor("Dot", MontySyntaxHighlighter.DOT_TOKEN),
                new AttributesDescriptor("Assignment", MontySyntaxHighlighter.ASSIGNMENT),
                new AttributesDescriptor("Colon", MontySyntaxHighlighter.COLON_TOKEN),
                new AttributesDescriptor("Operator", MontySyntaxHighlighter.OPERATOR),

                new AttributesDescriptor("Comment", MontySyntaxHighlighter.COMMENT)
        };
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Monty";
    }
}
