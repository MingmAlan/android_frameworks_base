package com.android.systemui.statusbar.toggles;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.View;

import com.android.systemui.R;
import com.android.systemui.settings.BrightnessController.BrightnessStateChangeCallback;

public class BrightnessToggle extends BaseToggle implements BrightnessStateChangeCallback {

    @Override
    public void init(Context c, int style) {
        super.init(c, style);
        onBrightnessLevelChanged();
    }

    @Override
    public void onClick(View v) {
        collapseStatusBar();
        showBrightnessDialog();
    }

    @Override
    public boolean onLongClick(View v) {
        dismissKeyguard();
        collapseStatusBar();
        startActivity(new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS));
        return super.onLongClick(v);
    }

    @Override
    protected void updateView() {
        super.updateView();
    }

    private void showBrightnessDialog() {
        Intent intent = new Intent(Intent.ACTION_SHOW_BRIGHTNESS_DIALOG);
        mContext.sendBroadcast(intent);
    }

    @Override
    public void onBrightnessLevelChanged() {
        int mode = Settings.System.getIntForUser(mContext.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL,
                UserHandle.USER_CURRENT);
        boolean autoBrightness =
                (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        int iconId = autoBrightness
                ? R.drawable.ic_qs_brightness_auto_on
                : R.drawable.ic_qs_brightness_auto_off;
        int label = R.string.quick_settings_brightness_label;

        setIcon(iconId);
        setLabel(label);
        scheduleViewUpdate();
    }

}
