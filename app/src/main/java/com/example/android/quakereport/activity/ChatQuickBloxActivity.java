package com.example.android.quakereport.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.quakereport.R;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * Created by indraaguslesmana on 2/6/17.
 */

public class ChatQuickBloxActivity extends AppCompatActivity {

    private RecyclerView mRecycleview;
    private TextView mEmpetyView;

    private ImageButton mBtn_send;
    private EditText mChat_text;
    private ListView mMessageListview;
    private ImageButton mImageSelect;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBtn_send = (ImageButton) findViewById(R.id.send_chat);
        mChat_text = (EditText) findViewById(R.id.ed_chat);
        mMessageListview = (ListView) findViewById(R.id.list_chat);
        mImageSelect = (ImageButton) findViewById(R.id.imageSelect);

        mEmpetyView = (TextView)findViewById(R.id.textEmpety);
        mRecycleview = (RecyclerView) findViewById(R.id.list);

        QbBloxInitialized();

        ConnectionListener connectionListener = new ConnectionListener() {
            @Override
            public void connected(XMPPConnection xmppConnection) {

            }

            @Override
            public void authenticated(XMPPConnection xmppConnection, boolean b) {

            }

            @Override
            public void connectionClosed() {

            }

            @Override
            public void connectionClosedOnError(Exception e) {

            }

            @Override
            public void reconnectionSuccessful() {

            }

            @Override
            public void reconnectingIn(int i) {

            }

            @Override
            public void reconnectionFailed(Exception e) {

            }
        };

        QBChatService.getInstance().addConnectionListener(connectionListener);
    }

    private void QbBloxInitialized () {
        QBChatService.setDebugEnabled(true); // enable chat logging
        QBChatService.setDefaultAutoSendPresenceInterval(60); //enable sending online status every 60 sec to keep connection alive

        QBChatService.ConfigurationBuilder chatServiceConfigurationBuilder =
                new QBChatService.ConfigurationBuilder();
        chatServiceConfigurationBuilder.setSocketTimeout(60); //Sets chat socket's read timeout in seconds
        chatServiceConfigurationBuilder.setKeepAlive(true); //Sets connection socket's keepAlive option.
        chatServiceConfigurationBuilder.setUseTls(true); //Sets the TLS security mode used when making the connection. By default TLS is disabled.
        QBChatService.setConfigurationBuilder(chatServiceConfigurationBuilder);

        // Initialise Chat service
        QBChatService chatService = QBChatService.getInstance();

        final QBUser user = new QBUser("indra", "indrapassword");
        QBAuth.createSession(user, new QBEntityCallback<QBSession>() {
            // success, login to chat

            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {

            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }


}
