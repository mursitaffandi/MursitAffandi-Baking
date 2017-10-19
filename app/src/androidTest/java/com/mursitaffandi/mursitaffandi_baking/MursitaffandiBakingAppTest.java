package com.mursitaffandi.mursitaffandi_baking;

import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

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
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
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
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));

        onView(withText("Brownies")).check(matches(isDisplayed()));

        onView(withText("Yellow Cake")).check(matches(isDisplayed()));

        onView(withText("Cheesecake")).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.tv_item_title), withText("Nutella Pie"),
                childAtPosition(childAtPosition(withId(R.id.card_food), 0), 1)))
                .check(matches(withText("Nutella Pie")));

        onView(allOf(withId(R.id.tv_item_ingredient), withText("9Ingredient"), hasSibling(withText("Nutella Pie"))))
                .check(matches(withText("9Ingredient")));

        onView(allOf(withId(R.id.rc_listfood))).perform(actionOnItemAtPosition(0, click()));

        onView(allOf(withText("Ingredient"), childAtPosition(childAtPosition(
                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class), 0), 0),
                isDisplayed())).check(matches(withText("Ingredient")));

        onView(allOf(withId(R.id.tv_frgdetailfood_step), withText("Step"),
                isDisplayed())).check(matches(withText("Step")));

        onView(allOf(withId(R.id.tv_detaillistfood_ingredient),
                childAtPosition(childAtPosition(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class), 0), 1),
                isDisplayed())).check(matches(withText("1. 2.0 CUP of Graham Cracker crumbs.\n" +
                "2. 6.0 TBLSP of unsalted butter, melted.\n" +
                "3. 0.5 CUP of granulated sugar.\n" +
                "4. 1.5 TSP of salt.\n" +
                "5. 5.0 TBLSP of vanilla.\n" +
                "6. 1.0 K of Nutella or other chocolate-hazelnut spread.\n" +
                "7. 500.0 G of Mascapone Cheese(room temperature).\n" +
                "8. 1.0 CUP of heavy cream(cold).\n" +
                "9. 4.0 OZ of cream cheese(softened).\n")));


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

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.rv_detaillistfood_step),
                        withParent(allOf(withId(R.id.llfrgdetailfood),
                                withParent(withId(R.id.nsv_detailFoodList)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withText("Nutella Pie - Recipe Introduction")))
                .check(matches(withText("Nutella Pie - Recipe Introduction")));

        onView(allOf(withId(R.id.exoui_frgdetailstep), withParent(withId(R.id.linear_stepdetail)))).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.tv_frgdetailstep_stepinstruction), withText("Recipe Introduction")))
                .check(matches(withText("Recipe Introduction")));

        onView(allOf(withId(R.id.btn_frgdetailstep_next)))
                .check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.btn_frgdetailstep_next),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        button.perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction button1 = onView(
                allOf(withId(R.id.btn_frgdetailstep_prev),
                        isDisplayed()));
        button1.check(matches(isDisplayed()));

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
