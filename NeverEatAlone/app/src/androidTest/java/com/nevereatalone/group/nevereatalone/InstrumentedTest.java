package com.nevereatalone.group.nevereatalone;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    public static int testUserAge = 18;

    @Test
    public void useAppContext() throws Exception {
        /* Context of the app under test */
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals(testUserAge, appContext.getString(R.string.userAge));
    }
}
