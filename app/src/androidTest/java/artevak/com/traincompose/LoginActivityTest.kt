package artevak.com.traincompose

import android.util.Log
import androidx.test.ext.junit.rules.activityScenarioRule
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test

class LoginActivityTest : ScreenshotTest{

    @get:Rule
    var activityScenarioRule = activityScenarioRule<LoginActivity>()

    @Test
    fun activityTest() {
        val activity = launchActivity()

        compareScreenshot(activity)
    }

    // Hack needed until we fully support Activity Scenarios
    private fun launchActivity(): LoginActivity {
        var activity: LoginActivity? = null
        activityScenarioRule.scenario.onActivity {
            activity = it
        }
        while (activity == null) {
            Log.d("LoginTest", "Waiting for activity to be initialized")
        }
        return activity!!
    }
}