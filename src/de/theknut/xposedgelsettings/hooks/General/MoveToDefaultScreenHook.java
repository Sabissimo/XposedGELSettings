package de.theknut.xposedgelsettings.hooks.general;

import de.theknut.xposedgelsettings.hooks.Common;
import de.theknut.xposedgelsettings.hooks.ObfuscationHelper.Fields;
import de.theknut.xposedgelsettings.hooks.PreferencesHelper;
import de.theknut.xposedgelsettings.hooks.common.XGELSCallback;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.setIntField;

public class MoveToDefaultScreenHook extends XGELSCallback {

    // http://androidxref.com/4.4.2_r1/xref/packages/apps/Launcher3/src/com/android/launcher3/Workspace.java#4429
    // void moveToDefaultScreen(boolean animate) {
    //     moveToScreen(mDefaultPage, animate);
    // }

    @Override
    public void onBeforeHookedMethod(MethodHookParam param) throws Throwable {

        int newDefaultPageNumber = ((PreferencesHelper.defaultHomescreen == 0) ? 0 : PreferencesHelper.defaultHomescreen) - 1;
        // move to our default homescreen
        if (Common.IS_TREBUCHET) {
            setIntField(param.thisObject, "mDefaultPage", newDefaultPageNumber);
            callMethod(param.thisObject, "moveToScreen", newDefaultPageNumber, param.args[0]);
            param.setResult(null);
        }
        else {
            setIntField(param.thisObject, Fields.wDefaultPage, newDefaultPageNumber);
        }
    }
}