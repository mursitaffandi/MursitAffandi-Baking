package com.mursitaffandi.mursitaffandi_baking;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mursitaffandi.mursitaffandi_baking.activity.WelcomeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mursitaffandi.mursitaffandi_baking.R;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Ingat Mati on 25/09/2017.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MursitaffandiBakingAppTest {
    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void mursitaffandibakingapptest() {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withId(R.id.tv_item_title), withText("Nutella Pie"),
                childAtPosition(childAtPosition(withId(R.id.card_food), 0), 1)))
                .check(matches(withText("Nutella Pie")));

        onView(allOf(withId(R.id.tv_item_ingredient), withText("9ingredients"),
                childAtPosition(childAtPosition(withId(R.id.card_food), 0), 2)))
                .check(matches(withText("9ingredients")));

        onView(allOf(withId(R.id.tv_item_step), withText("7steps"),
                childAtPosition(childAtPosition(withId(R.id.card_food), 0), 3)))
                .check(matches(withText("7steps")));

        onView(allOf(withId(R.id.tv_item_serving), withText("8serving"),
                childAtPosition(childAtPosition(withId(R.id.card_food), 0), 4)))
                .check(matches(withText("8serving")));

        onView(allOf(withId(R.id.rc_listfood))).perform(actionOnItemAtPosition(0, click()));

        onView(allOf(withText("Ingredients :"), childAtPosition(childAtPosition(
                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class), 0), 0),
                isDisplayed())).check(matches(withText("Ingredients :")));

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
