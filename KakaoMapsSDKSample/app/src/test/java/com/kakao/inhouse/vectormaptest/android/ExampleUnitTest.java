package com.kakao.inhouse.vectormaptest.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.graphics.Color;

import com.kakao.vectormap.MapGravity;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;
import com.kakao.vectormap.label.LabelTextStyle;
import com.kakao.vectormap.label.LabelTransition;
import com.kakao.vectormap.label.Transition;

import org.junit.Test;

import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        LabelStyles styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker));
        LabelStyles styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker)));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());

        // + zoomLevel
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setZoomLevel(5));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setZoomLevel(5)));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());

        // + anchor
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setAnchor(0.7f, 0.3f));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setAnchor(0.7f, 0.3f)));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());

        // + padding
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setPadding(10));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setPadding(10)));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());

        // + applyDpScale
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setApplyDpScale(false));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setApplyDpScale(false)));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());

        // + textGravity
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setTextGravity(MapGravity.CENTER));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setTextGravity(MapGravity.CENTER)));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());

        // + textStyle
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setTextStyles(20, Color.BLUE, 1, Color.WHITE));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setTextStyles(20, Color.BLUE, 1, Color.WHITE)));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());

        // + labelTransition
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setTransition(LabelTransition.from(Transition.None, Transition.Alpha)));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setTransition(LabelTransition.from(Transition.None, Transition.Alpha))));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());

        // + textStyles
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker)
                .setTextStyles(LabelTextStyle.from(15, Color.RED), LabelTextStyle.from(20, Color.GREEN)));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker)
                .setTextStyles(LabelTextStyle.from(15, Color.RED), LabelTextStyle.from(20, Color.GREEN))));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());
    }

    @Test
    public void notIdEqualsTest() {
        LabelStyles styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker));
        LabelStyles styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.roadview_icon)));
        assertNotEquals(styles1.getStyleId(), styles2.getStyleId());

        // + zoomLevel
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setZoomLevel(5));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setZoomLevel(10)));
        assertNotEquals(styles1.getStyleId(), styles2.getStyleId());

        // + anchor
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setAnchor(0.7f, 0.3f));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setAnchor(0.7f, 0.3f)));
        assertEquals(styles1.getStyleId(), styles2.getStyleId());

        // + padding
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setPadding(10));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setPadding(8)));
        assertNotEquals(styles1.getStyleId(), styles2.getStyleId());

        // + applyDpScale
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setApplyDpScale(false));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setApplyDpScale(true)));
        assertNotEquals(styles1.getStyleId(), styles2.getStyleId());

        // + textGravity
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setTextGravity(MapGravity.CENTER));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setTextGravity(MapGravity.BOTTOM)));
        assertNotEquals(styles1.getStyleId(), styles2.getStyleId());

        // + textStyle
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setTextStyles(20, Color.BLUE, 1, Color.WHITE));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setTextStyles(17, Color.BLUE, 1, Color.WHITE)));
        assertNotEquals(styles1.getStyleId(), styles2.getStyleId());

        // + labelTransition
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker).setTransition(LabelTransition.from(Transition.None, Transition.Alpha)));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker).setTransition(LabelTransition.from(Transition.Scale, Transition.Alpha))));
        assertNotEquals(styles1.getStyleId(), styles2.getStyleId());

        // + textStyles
        styles1 = LabelStyles.from(LabelStyle.from(R.drawable.best_marker)
                .setTextStyles(LabelTextStyle.from(15, Color.RED), LabelTextStyle.from(20, Color.GREEN)));
        styles2 = LabelStyles.from(Arrays.asList(LabelStyle.from(R.drawable.best_marker)
                .setTextStyles(LabelTextStyle.from(15, Color.RED), LabelTextStyle.from(19, Color.GREEN))));
        assertNotEquals(styles1.getStyleId(), styles2.getStyleId());
    }
}