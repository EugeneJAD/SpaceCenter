package com.eugene.spacecenter.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by E.Iatsenko on 25.12.2017.
 */

public class SnackbarUtils {

    public static void showSnackbar(View v, String snackbarText) {
        if (v == null || snackbarText == null) {
            return;
        }
        Snackbar.make(v, snackbarText, Snackbar.LENGTH_LONG).show();
    }

}
