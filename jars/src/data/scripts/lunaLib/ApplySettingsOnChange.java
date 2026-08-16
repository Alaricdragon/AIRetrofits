package data.scripts.lunaLib;

import lunalib.lunaSettings.LunaSettingsListener;

public class ApplySettingsOnChange implements LunaSettingsListener {
    @Override
    public void settingsChanged(String s) {
        StoredSettings.getSettings();
    }
}
