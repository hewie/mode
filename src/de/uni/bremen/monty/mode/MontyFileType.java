package de.uni.bremen.monty.mode;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MontyFileType extends LanguageFileType {

    public static MontyFileType INSTANCE = new MontyFileType();
    private final Icon icon;

    private MontyFileType() {
        super(MontyLanguage.INSTANCE);
        icon = IconLoader.getIcon("/icons/monty_16_16.png");
    }

    @NotNull
    @Override
    public String getName() {
        return "Monty";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Monty source code";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "monty";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return "UTF-8";
    }
}
