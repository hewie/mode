package de.uni.bremen.monty.mode.parser.util;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.WhitespacesAndCommentsBinder;
import com.intellij.psi.tree.IElementType;

import java.util.*;

public class TestMarker implements PsiBuilder.Marker {


    public int startIndex;
    public int endIndex;
    public IElementType type;
    public String error;

    private TestPsiBuilder builder;

    public TestMarker(TestPsiBuilder builder) {
        this(builder, builder.currentIndex, -1);
    }

    public TestMarker(TestPsiBuilder builder, int offset, int index) {
        this.startIndex = builder.currentIndex;
        this.builder = builder;

        addThis(offset, index);
    }

    private void addThis(int offset, int index) {
        List<TestMarker> col = builder.markerMap.get(offset);
        if (col == null) {
            builder.markerMap.put(offset, new LinkedList<>(Collections.singleton(this)));
        } else {
            if (index == -1) {
                col.add(this);
            } else {
                col.add(index, this);
            }
        }
    }

    @Override
    public PsiBuilder.Marker precede() {
        int index = -1;
        for (List<TestMarker> testMarkers : builder.markerMap.values()) {
            index = testMarkers.indexOf(this);
            if (index != -1) {
                break;
            }
        }

        TestMarker testMarker = new TestMarker(builder, this.startIndex, index);
        testMarker.startIndex = this.startIndex;
        return testMarker;
    }

    @Override
    public void drop() {
        for (List<TestMarker> testMarkers : builder.markerMap.values()) {
            testMarkers.remove(this);
        }
    }

    @Override
    public void rollbackTo() {

        List<TestMarker> removedEntries = new ArrayList<>();

        Iterator<Map.Entry<Integer, List<TestMarker>>> iterator = builder.markerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<TestMarker>> entry = iterator.next();
            if (entry.getKey() > this.startIndex) {
                removedEntries.addAll(entry.getValue());
                iterator.remove();
            }
        }
        List<TestMarker> list = builder.markerMap.get(this.startIndex);
        List<TestMarker> sub = list.subList(list.indexOf(this), list.size());
        removedEntries.addAll(sub);
        sub.clear();

        for (List<TestMarker> testMarkers : builder.markerMap.values()) {
            Iterator<TestMarker> iterator1 = testMarkers.iterator();
            while (iterator1.hasNext()) {
                TestMarker testMarker = iterator1.next();
                if (removedEntries.contains(testMarker)) {
                    iterator1.remove();
                }
            }
        }
        builder.currentIndex = startIndex;
    }

    @Override
    public void done(IElementType type) {
        this.type = type;
        endIndex = builder.currentIndex;
        addThis(builder.currentIndex, -1);
    }

    @Override
    public void collapse(IElementType type) {
        throw new IllegalArgumentException();
    }

    @Override
    public void doneBefore(IElementType type, PsiBuilder.Marker before) {
        throw new IllegalArgumentException();
    }

    @Override
    public void doneBefore(IElementType type, PsiBuilder.Marker before, String errorMessage) {
        throw new IllegalArgumentException();
    }

    @Override
    public void error(String message) {
        this.error = message;
        endIndex = builder.currentIndex;
        addThis(builder.currentIndex, -1);
    }

    @Override
    public void errorBefore(String message, PsiBuilder.Marker before) {
        throw new IllegalArgumentException();
    }

    @Override
    public void setCustomEdgeTokenBinders(WhitespacesAndCommentsBinder left, WhitespacesAndCommentsBinder right) {
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "TestMarker{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", builder=" + builder +
                ", type=" + type +
                ", error='" + error + '\'' +
                '}';
    }

}
