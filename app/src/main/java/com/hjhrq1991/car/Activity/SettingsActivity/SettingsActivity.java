package com.hjhrq1991.car.Activity.SettingsActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.hjhrq1991.car.Activity.AboutActivity.AboutActivity;
import com.hjhrq1991.car.Activity.CityActivity.CityActivity;
import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.Event.GasSettingChangedEvent;
import com.hjhrq1991.car.R;
import com.hjhrq1991.tool.Base.BaseActivity;
import com.zuck.swipe.hitblockrefresh.view.FunGameFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * @author hjhrq1991 created at 3/2/16 14 47.
 * @Package com.hjhrq1991.car.Activity.SettingsActivity
 * @Description:
 */
public class SettingsActivity extends BaseActivity {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_settings_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragment();
    }

    private void initFragment() {
        getFragmentManager().beginTransaction().replace(R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

        private SwitchPreference mSettingUseGB;
        private ListPreference mSettingSecondGas;
        private SwitchPreference mSettingCalculate;
        private SwitchPreference mSettingDefaultGame;

        private Preference mSettingSetCity;
        private Preference mSettingAbout;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings);
            initPreferences();
        }

        private void initPreferences() {
            mSettingUseGB = (SwitchPreference) findPreference(CustomConstant.SETTINGS_USE_GB);
            mSettingSecondGas = (ListPreference) findPreference(CustomConstant.SETTINGS_SECOND_GAS);
            mSettingCalculate = (SwitchPreference) findPreference(CustomConstant.SETTINGS_CALCULATE);
            mSettingSetCity = findPreference(CustomConstant.SETTINGS_SET_CITY);
            mSettingAbout = findPreference(CustomConstant.SETTINGS_ABOUT);
            mSettingDefaultGame = (SwitchPreference) findPreference(CustomConstant.SETTINGS_DEFAULT_GAME);

            mSettingUseGB.setOnPreferenceChangeListener(this);
            mSettingSecondGas.setOnPreferenceChangeListener(this);
            mSettingDefaultGame.setOnPreferenceChangeListener(this);

            mSettingSetCity.setOnPreferenceClickListener(this);
            mSettingAbout.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            switch (preference.getKey()) {
                case CustomConstant.SETTINGS_USE_GB:
                    EventBus.getDefault().post(new GasSettingChangedEvent());
                    break;
                case CustomConstant.SETTINGS_SECOND_GAS:
                    EventBus.getDefault().post(new GasSettingChangedEvent());
                    break;
                case CustomConstant.SETTINGS_DEFAULT_GAME:
                    boolean value = (boolean) newValue;
                    SharedPreferences sp = getActivity().getSharedPreferences("FunGame",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("defaultStyle", value ? 0 : 1);
                    editor.apply();
                    break;
            }
            return true;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()) {
                case CustomConstant.SETTINGS_SET_CITY:
                    CityActivity.launch(getActivity());
                    break;
                case CustomConstant.SETTINGS_ABOUT:
                    AboutActivity.launch(getActivity());
                    break;
            }
            return true;
        }
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

}