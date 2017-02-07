package com.example.android.quakereport.utility;

import android.text.TextUtils;

import com.quickblox.auth.QBAuth;
import com.quickblox.core.exception.BaseServiceException;

import java.util.Date;

/**
 * Created by frensky on 06/02/2017.
 */

public class QBAuthUtil {
    public static boolean isSessionActive() {
        try {
            String token = QBAuth.getBaseService().getToken();
            Date expirationDate = QBAuth.getBaseService().getTokenExpirationDate();

            if (TextUtils.isEmpty(token)) {
                return false;
            }

            if (System.currentTimeMillis() >= expirationDate.getTime()) {
                return false;
            }

            return true;
        } catch (BaseServiceException ignored) {
        }

        return false;
    }
}
