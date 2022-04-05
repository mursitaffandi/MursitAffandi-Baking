package com.mursitaffandi.mursitaffandi_baking.activity;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mursitaffandi.mursitaffandi_baking.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void welcomeActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rc_listfood), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.cv_itemstep),
                        childAtPosition(
                                allOf(withId(R.id.rv_detaillistfood_step),
                                        childAtPosition(
                                                withId(R.id.llfrgdetailfood),
                                                2)),
                                0),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction frameLayout2 = onView(
                allOf(withId(R.id.cv_itemstep),
                        childAtPosition(
                                allOf(withId(R.id.rv_detaillistfood_step),
                                        childAtPosition(
                                                withId(R.id.llfrgdetailfood),
                                                2)),
                                0),
                        isDisplayed()));
        frameLayout2.check(matches(isDisplayed()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.rv_detaillistfood_step),
                        withParent(allOf(withId(R.id.llfrgdetailfood),
                                withParent(withId(R.id.nsv_detailFoodList)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction view = onView(
                allOf(withId(R.id.exo_shutter),
                        childAtPosition(
                                allOf(withId(R.id.exo_content_frame),
                                        childAtPosition(
                                                withId(R.id.exoui_frgdetailstep),
                                                0)),
                                0),
                        isDisplayed()));
        view.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_frgdetailstep_stepinstruction), withText("Recipe Introduction"),
                        childAtPosition(
                                allOf(withId(R.id.linear_stepdetail),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Recipe Introduction")));

        ViewInteraction button = onView(
                allOf(withId(R.id.btn_frgdetailstep_next),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.btn_frgdetailstep_next),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        pressBack();

        pressBack();

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
