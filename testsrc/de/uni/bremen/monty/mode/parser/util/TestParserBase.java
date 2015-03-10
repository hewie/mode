package de.uni.bremen.monty.mode.parser.util;

import com.intellij.psi.tree.IElementType;
import de.uni.bremen.monty.mode.MontyElementTypes;
import de.uni.bremen.monty.mode.parser.ExpressionParserTest;
import de.uni.bremen.monty.mode.parser.MontyParseStep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestParserBase implements MontyElementTypes {
    protected void testParser(List<Token> tokens, String expected) {
        TestPsiBuilder builder = new TestPsiBuilder(tokens);
        MontyParseStep montyParseStep = new MontyParseStep(builder);

        montyParseStep.parseModule();

        assertThat(builder.currentIndex, is(tokens.size()));

        List<Ele> eles = new ArrayList<>();

        int a = 'a';

//        ArrayList<TestMarker> testMarkers = new ArrayList<>(builder.markers);
//        ArrayList<TestMarker> t2 = new ArrayList<>(builder.endMarkers);
//        testMarkers.removeAll(builder.endMarkers);
//        t2.removeAll(builder.markers);
//        testMarkers.addAll(t2);
//        assertThat("dangling: " + testMarkers, builder.markers.size(), is(builder.endMarkers.size()));
        for (Map.Entry<Integer, List<TestMarker>> markers : builder.markerMap.entrySet()) {
            List<TestMarker> value = markers.getValue();
            for (int i = 0; i < value.size(); i++) {
                TestMarker marker = value.get(i);
                if (marker.startIndex == markers.getKey()) {
                    if (marker.error != null) {
                        System.out.println("Error Token: " + marker.error);
                        System.out.println("Error Token: s: " + marker.startIndex + " e: " + marker.endIndex);
                        if (marker.startIndex != marker.endIndex) {
                            eles.add(new Ele(marker.startIndex, "!("));
                        } else {
                            if (i + 1 < value.size()) {
                                if (marker == (markers.getValue().get(i + 1))) {
                                    eles.add(new Ele(marker.startIndex, "!"));
                                } else {
                                    if (markers.getValue().subList(i + 1, markers.getValue().size()).contains(marker)) {
                                        eles.add(new Ele(marker.startIndex, "!("));
                                    }
                                }
                            } else {
                                if (marker == (markers.getValue().get(i - 1))) {
                                    //DO NOTHING
                                } else {
                                    if (markers.getValue().subList(0, i - 1).contains(marker)) {
                                        eles.add(new Ele(marker.startIndex, "!)"));
                                    }
                                }
                            }
                        }
                    } else {
                        eles.add(new Ele(marker.startIndex, "("));
                        System.out.println(marker.type.toString());
                        System.out.println("s: " + marker.startIndex + " e: " + marker.endIndex);
                    }
                } else if (marker.endIndex == markers.getKey()) {
                    if (marker.error != null) {
                        if (marker.startIndex != marker.endIndex) {
                            eles.add(new Ele(marker.endIndex, "!)"));
                        }
                    } else {
                        eles.add(new Ele(marker.endIndex, ")"));
                    }
                } else {
                    throw new RuntimeException("");
                }

            }
        }
        Ele[] array = new Ele[eles.size()];
        eles.toArray(array);

        Arrays.sort(array);
        int i = 0;

        for (Ele ele : array) {
            tokens.add(ele.index + i, new Token(ERROR, ele.text + ""));
            i++;
        }

        String result = "";
        for (Token token : tokens) {
            String text = token.text;
            if (token.type == PARENTHESES) {
                text = "#" + text;
            }
            result += text + " ";
        }
        assertThat(result, is(expected));
    }

    public static class Token {
        public final IElementType type;
        public final String text;

        public Token(IElementType type, String text) {
            this.type = type;
            this.text = text;
        }

        @Override
        public String toString() {
            return "Token{" +
                    "type=" + type +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    public static class Ele implements Comparable<ExpressionParserTest.Ele> {
        public final Integer index;
        public final String text;

        public Ele(int index, String text) {
            this.index = index;
            this.text = text;
        }

        @Override
        public int compareTo(Ele o) {
            return index.compareTo(o.index);
        }
    }
}
