package com.amnesty.panicbutton.common;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import com.amnesty.panicbutton.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowEditText;
import roboguice.activity.RoboFragmentActivity;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Robolectric.application;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MessageFragmentTest {
    private MessageFragment messageFragment;
    private EditText editText;
    private TextView textView;

    @Before
    public void setup() {
        messageFragment = new MessageFragment();
        FragmentManager fragmentManager = new RoboFragmentActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(messageFragment, null);
        fragmentTransaction.commit();

        editText = (EditText) messageFragment.getView().findViewById(R.id.message_edit_text);
        textView = (TextView) messageFragment.getView().findViewById(R.id.characters_left_view);
    }

    @Test
    public void shouldInitializeTheMessageEditText() {
        ShadowEditText shadowEditText = (ShadowEditText) shadowOf(editText);

        assertEquals(application.getString(R.string.characters_left) + String.valueOf(85 - editText.getText().length()), textView.getText());

        InputFilter[] filters = shadowEditText.getFilters();
        InputFilter inputFilter = filters[0];
        assertEquals(inputFilter.getClass(), InputFilter.LengthFilter.class);

        List<TextWatcher> watchers = shadowEditText.getWatchers();
        assertEquals(1, watchers.size());
        assertTrue(watchers.get(0).getClass().equals(MessageLimitWatcher.class));
    }
}