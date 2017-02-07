package com.example.android.quakereport.utility;

import android.os.Bundle;

import com.example.android.quakereport.activity.EarthquakeActivity;
import com.example.android.quakereport.callback.QbEntityCallbackTwoTypeWrapper;
import com.example.android.quakereport.callback.QbEntityCallbackWrapper;
import com.example.android.quakereport.model.QBSetting;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.LogLevel;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.DiscussionHistory;

import java.util.ArrayList;

/**
 * Created by frensky on 06/02/2017.
 */

public class ChatHelper {
    private static final String TAG = ChatHelper.class.getSimpleName();

    public static final int DIALOG_ITEMS_PER_PAGE = 100;
    public static final int CHAT_HISTORY_ITEMS_PER_PAGE = 50;
    private static final String CHAT_HISTORY_ITEMS_SORT_FIELD = "date_sent";

    private static ChatHelper instance;

    private QBChatService qbChatService;

    public static synchronized ChatHelper getInstance() {
        if (instance == null) {
            QBSettings.getInstance().setLogLevel(LogLevel.DEBUG);
            QBChatService.setDebugEnabled(true);
            QBChatService.setConfigurationBuilder(buildChatConfigs());
            instance = new ChatHelper();
        }
        return instance;
    }

    public static QBUser getCurrentUser() {
        return QBChatService.getInstance().getUser();
    }

    private ChatHelper() {
        qbChatService = QBChatService.getInstance();
        qbChatService.setUseStreamManagement(true);
    }

    private static QBChatService.ConfigurationBuilder buildChatConfigs(){
        QBChatService.ConfigurationBuilder configurationBuilder = new QBChatService.ConfigurationBuilder();

        String content = FunctionUtil.readFileFromAssets("qb_setting.json", EarthquakeActivity.getsInstance());

        QBSetting sampleConfigs = new Gson().fromJson(content, QBSetting.class);

        int port = sampleConfigs.getPort();
        int socketTimeout = sampleConfigs.getSocket_timeout();
        boolean useTls = sampleConfigs.isUse_tls();
        boolean keepAlive = sampleConfigs.isKeep_alive();
        boolean autoJoinEnabled = sampleConfigs.isAuto_join();
        boolean autoMarkDelivered = sampleConfigs.isAuto_mark_delivered();
        boolean reconnectionAllowed = sampleConfigs.isReconnection_allowed();
        boolean allowListenNetwork = sampleConfigs.isAllow_listen_network();

        if (port != 0) {
            configurationBuilder.setPort(port);
        }

        configurationBuilder.setSocketTimeout(socketTimeout);
        configurationBuilder.setUseTls(useTls);
        configurationBuilder.setKeepAlive(keepAlive);
        configurationBuilder.setAutojoinEnabled(autoJoinEnabled);
        configurationBuilder.setAutoMarkDelivered(autoMarkDelivered);
        configurationBuilder.setReconnectionAllowed(reconnectionAllowed);
        configurationBuilder.setAllowListenNetwork(allowListenNetwork);

        return configurationBuilder;
    }

    public void addConnectionListener(ConnectionListener listener) {
        qbChatService.addConnectionListener(listener);
    }

    public void removeConnectionListener(ConnectionListener listener) {
        qbChatService.removeConnectionListener(listener);
    }

    public void login(final QBUser user, final QBEntityCallback<Void> callback) {
        // Create REST API session on QuickBlox
        QBUsers.signIn(user).performAsync(new QbEntityCallbackTwoTypeWrapper<QBUser, Void>(callback) {
            @Override
            public void onSuccess(QBUser qbUser, Bundle args) {
                user.setId(qbUser.getId());
                loginToChat(user, new QbEntityCallbackWrapper<Void>(callback));
            }
        });
    }

    private void loginToChat(final QBUser user, final QBEntityCallback<Void> callback) {
        if (qbChatService.isLoggedIn()) {
            callback.onSuccess(null, null);
            return;
        }

        qbChatService.login(user, callback);
    }

    public void join(QBChatDialog chatDialog, final QBEntityCallback<Void> callback) {
        DiscussionHistory history = new DiscussionHistory();
        history.setMaxStanzas(0);

        chatDialog.join(history, callback);
    }

    public void leaveChatDialog(QBChatDialog chatDialog) throws XMPPException, SmackException.NotConnectedException {
        chatDialog.leave();
    }

    public void logout(final QBEntityCallback<Void> callback) {
        qbChatService.logout(callback);
    }

    public void loadChatHistory(QBChatDialog dialog, int skipPagination,
                                final QBEntityCallback<ArrayList<QBChatMessage>> callback) {
        QBRequestGetBuilder customObjectRequestBuilder = new QBRequestGetBuilder();
        customObjectRequestBuilder.setSkip(skipPagination);
        customObjectRequestBuilder.setLimit(CHAT_HISTORY_ITEMS_PER_PAGE);
        customObjectRequestBuilder.sortDesc(CHAT_HISTORY_ITEMS_SORT_FIELD);

        QBRestChatService.getDialogMessages(dialog, customObjectRequestBuilder).performAsync(
                new QbEntityCallbackWrapper<ArrayList<QBChatMessage>>(callback) {
                    @Override
                    public void onSuccess(ArrayList<QBChatMessage> qbChatMessages, Bundle bundle) {

                        callback.onSuccess(qbChatMessages, bundle);
                        //getUsersFromMessages(qbChatMessages, callback);
                        // Not calling super.onSuccess() because
                        // we're want to load chat users before triggering the callback
                    }
                });
    }

    public void getDialogById(String dialogId, final QBEntityCallback<QBChatDialog> callback) {
        QBRestChatService.getChatDialogById(dialogId).performAsync(callback);
    }

}
