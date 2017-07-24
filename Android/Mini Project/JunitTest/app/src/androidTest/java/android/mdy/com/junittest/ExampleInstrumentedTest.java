package android.mdy.com.junittest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.mdy.com.junittest.R.id.textView;
import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private double PI;
    private MainActivity activity;

    // 액티비티같은 컴포넌트를 가져올때 @Rule 을 사용
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){ // 테스트에 사용할 변수를 초기화
        PI = 3.14;

        // 테스트룰을 통해 검사할 액티비티를 가져온다.
        activity = activityTestRule.getActivity();
    }


    @Test
    public void test() throws Exception {
        // 1. result 변수에 값을 입력하는 함수를 호출한다.
        activity.setResult();
        // 2. result에 정상적인 값이 작성되어 있는지 값을 가져와서 비교한다.
        String result = activity.getResult();

        assertEquals("Hello", result);
    }
}
