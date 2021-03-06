package net.wigle.wigleandroid;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SpeechActivity extends ActionBarActivity {
    private static final int MENU_RETURN = 12;

    // used for shutting extraneous activities down on an error
    public static SpeechActivity speechActivity;

    /** Called when the activity is first created. */
    @Override
    public void onCreate( final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // set language
        MainActivity.setLocale( this );
        setContentView( R.layout.speech );
        speechActivity = this;

        // force media volume controls
        this.setVolumeControlStream( AudioManager.STREAM_MUSIC );

        final SharedPreferences prefs = this.getSharedPreferences( ListFragment.SHARED_PREFS, 0);
        doCheckbox( prefs, R.id.speech_gps, ListFragment.PREF_SPEECH_GPS );
        doCheckbox( prefs, R.id.speech_run, ListFragment.PREF_SPEAK_RUN );
        doCheckbox( prefs, R.id.speech_new_wifi, ListFragment.PREF_SPEAK_NEW_WIFI );
        doCheckbox( prefs, R.id.speech_new_cell, ListFragment.PREF_SPEAK_NEW_CELL );
        doCheckbox( prefs, R.id.speech_queue, ListFragment.PREF_SPEAK_QUEUE );
        doCheckbox( prefs, R.id.speech_miles, ListFragment.PREF_SPEAK_MILES );
        doCheckbox( prefs, R.id.speech_time, ListFragment.PREF_SPEAK_TIME );
        doCheckbox( prefs, R.id.speech_battery, ListFragment.PREF_SPEAK_BATTERY );
        doCheckbox( prefs, R.id.speech_ssid, ListFragment.PREF_SPEAK_SSID, false );
        doCheckbox( prefs, R.id.speech_wifi_restart, ListFragment.PREF_SPEAK_WIFI_RESTART );
    }

    @Override
    public void onDestroy() {
        speechActivity = null;
        super.onDestroy();
    }

    private void doCheckbox( final SharedPreferences prefs, final int id, final String pref ) {
        doCheckbox( prefs, id, pref, true );
    }

    private void doCheckbox( final SharedPreferences prefs, final int id, final String pref, final boolean defaultVal ) {
        final CheckBox box = (CheckBox) findViewById( id );
        box.setChecked( prefs.getBoolean( pref, defaultVal ) );
        box.setOnCheckedChangeListener( new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( final CompoundButton buttonView, final boolean isChecked ) {
                final Editor editor = prefs.edit();
                editor.putBoolean( pref, isChecked );
                editor.apply();
            }
        });
    }

    /* Creates the menu items */
    @Override
    public boolean onCreateOptionsMenu( final Menu menu ) {
        MenuItem item = menu.add(0, MENU_RETURN, 0, getString(R.string.menu_return));
        item.setIcon( android.R.drawable.ic_media_previous );

        return true;
    }

    /* Handles item selections */
    @Override
    public boolean onOptionsItemSelected( final MenuItem item ) {
        switch ( item.getItemId() ) {
            case MENU_RETURN:
                finish();
                return true;
        }
        return false;
    }
}
