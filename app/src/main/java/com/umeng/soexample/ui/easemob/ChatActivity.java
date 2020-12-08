package com.umeng.soexample.ui.easemob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.superrtc.mediamanager.EMediaEntities;
import com.umeng.soexample.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTitle;
    RecyclerView recyChat;
    EditText inputWord;
    Button btnSend;

    private String toUserId;
    private String selfId;

    List<EMMessage> msgsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        txtTitle = findViewById(R.id.txt_title);
        recyChat = findViewById(R.id.recy_chat);
        inputWord = findViewById(R.id.input_word);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(this);
        initData();
        initMsgListner();
    }

    private void initData(){
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("touserid")){
                toUserId = intent.getStringExtra("touserid");
                txtTitle.setText(toUserId);
            }
        }
        selfId = EMClient.getInstance().getCurrentUser();
        msgsList = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                sendMsg();
                break;
        }
    }

    /**
     * 发送消息
     */
    private void sendMsg(){

        String content = inputWord.getText().toString();
        if(TextUtils.isEmpty(content)){
            Toast.makeText(this, "请输入消息内容", Toast.LENGTH_SHORT).show();
            return;
        }

        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, toUserId);
        //如果是群聊，设置chattype，默认是单聊
        message.setChatType(EMMessage.ChatType.ChatRoom);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    private void initMsgListner(){
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            msgsList.addAll(messages);
            if(toUserId.equals(messages.get(0).getFrom())){
                //好友
                messages.get(0).getBody();
                //EMMessageBody messageBody;
                if(messages.get(0).getType() == EMMessage.Type.TXT){
                    EMTextMessageBody textMessageBody = (EMTextMessageBody) messages.get(0).getBody();
                    textMessageBody.getMessage();
                }else if(messages.get(0).getType() == EMMessage.Type.LOCATION){
                    //定位销
                }

            }else if(selfId.equals(messages.get(0).getFrom())){
                //自己
            }

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };
}