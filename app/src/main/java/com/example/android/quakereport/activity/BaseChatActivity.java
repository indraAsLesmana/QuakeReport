package com.example.android.quakereport.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.android.quakereport.R;
import com.example.android.quakereport.callback.QbSessionStateCallback;
import com.example.android.quakereport.helper.Helpers;
import com.example.android.quakereport.utility.ChatHelper;
import com.example.android.quakereport.utility.QBAuthUtil;
import com.example.android.quakereport.utility.QBPreferenceUserUtil;

import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

public abstract class BaseChatActivity extends AppCompatActivity implements QbSessionStateCallback {
    private static final String TAG = BaseChatActivity.class.getSimpleName();

    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    protected ActionBar actionBar;
    protected boolean isAppSessionActive;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();

        boolean wasAppRestored = savedInstanceState != null;
        boolean isQbSessionActive = QBAuthUtil.isSessionActive();
        final boolean needToRestoreSession = wasAppRestored || !isQbSessionActive;
        Log.v(TAG, "wasAppRestored = " + wasAppRestored);
        Log.v(TAG, "isQbSessionActive = " + isQbSessionActive);

        // Triggering callback via Handler#post() method
        // to let child's code in onCreate() to execute first
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (needToRestoreSession) {
                    recreateChatSession();
                    isAppSessionActive = false;
                } else {
                    onSessionCreated(true);
                    isAppSessionActive = true;
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("dummy_value", 0);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    protected abstract View getSnackbarAnchorView();


    private void reloginToChat(final QBUser user) {
        Helpers.showProgressDialog(this, R.string.dlg_restoring_chat_session);

        ChatHelper.getInstance().login(user, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void result, Bundle bundle) {
                Log.v(TAG, "Chat login onSuccess()");
                isAppSessionActive = true;
                onSessionCreated(true);
                Helpers.hideProgressDialog();

            }

            @Override
            public void onError(QBResponseException e) {
                isAppSessionActive = false;
                Helpers.hideProgressDialog();
                Log.w(TAG, "Chat login onError(): " + e);
                Helpers.showToast(BaseChatActivity.this,"Error recreate session", false);
                onSessionCreated(false);
            }
        });
    }

    private void recreateChatSession() {
        Log.d(TAG, "Need to recreate chat session");

        QBUser user = QBPreferenceUserUtil.getQbUser();
        if (user == null) {
            throw new RuntimeException("User is null, can't restore session");
        }

        reloginToChat(user);
    }
}