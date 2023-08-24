/*
 * Copyright (C) 2022 SuperiorOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.superior.lab.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Resources;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;

import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import com.android.internal.util.superior.systemUtils;

public class StatusBarSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String KEY_STATUS_BAR_PRIVACY_CAMERA = "enable_camera_privacy_indicator";
    private static final String KEY_STATUS_BAR_PRIVACY_LOC = "enable_location_privacy_indicator";
    private static final String KEY_STATUS_BAR_PRIVACY_MEDIA = "enable_projection_privacy_indicator";

    private Preference mPrivacyCam;
    private Preference mPrivacyLoc;
    private Preference mPrivacyMedia;
    private Preference mCombinedSignalIcons;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.superior_lab_statusbar);

        PreferenceScreen prefSet = getPreferenceScreen();

        mPrivacyCam = findPreference(KEY_STATUS_BAR_PRIVACY_CAMERA);
        mPrivacyCam.setOnPreferenceChangeListener(this);
        mPrivacyLoc = findPreference(KEY_STATUS_BAR_PRIVACY_LOC);
        mPrivacyLoc.setOnPreferenceChangeListener(this);
        mPrivacyMedia = findPreference(KEY_STATUS_BAR_PRIVACY_MEDIA);
        mPrivacyMedia.setOnPreferenceChangeListener(this);
        mCombinedSignalIcons = findPreference("persist.sys.flags.combined_signal_icons");
        mCombinedSignalIcons.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
      if (preference == mPrivacyCam || preference == mPrivacyLoc || preference == mPrivacyMedia) {
            systemUtils.showSystemRestartDialog(getContext());
            return true;
          }  
       else if (preference == mCombinedSignalIcons) {
            boolean value = (Boolean) objValue;
            Settings.Secure.putIntForUser(getContentResolver(),
                Settings.Secure.ENABLE_COMBINED_SIGNAL_ICONS, value ? 1 : 0, UserHandle.USER_CURRENT);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.SUPERIOR;
    }

}
