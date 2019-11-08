package com.reyzeny.br;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import com.reyzeny.br.Home.MainActivity;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);

    private MockWebServer server;


    @Before
    public void setup() throws IOException {
        server =  new MockWebServer();
        server.start();
    }

    @Test
    public void shouldDisplayListOfPictures() {

    }

}
