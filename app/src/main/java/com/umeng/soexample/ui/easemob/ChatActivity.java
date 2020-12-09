package com.umeng.soexample.ui.easemob;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.superrtc.mediamanager.EMediaEntities;
import com.umeng.soexample.R;
import com.umeng.soexample.app.Constants;
import com.umeng.soexample.module.data.EMUserInfo;
import com.umeng.soexample.utils.GlideEngine;
import com.umeng.soexample.utils.SpUtils;
import com.umeng.soexample.utils.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTitle;
    RecyclerView recyChat;
    EditText inputWord;
    Button btnSend;
    ImageView img;

    private String toUserId;
    private String selfId;

    List<EMMessage> msgsList;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        txtTitle = findViewById(R.id.txt_title);
        recyChat = findViewById(R.id.recy_chat);
        inputWord = findViewById(R.id.input_word);
        btnSend = findViewById(R.id.btn_send);
        img = findViewById(R.id.img_input);

        btnSend.setOnClickListener(this);
        img.setOnClickListener(this);
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
        chatAdapter = new ChatAdapter(this,msgsList);
        recyChat.setLayoutManager(new LinearLayoutManager(this));
        recyChat.setAdapter(chatAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                sendMsg();
                break;
            case R.id.img_input:
                openPhoto();
                break;
        }
    }

    private void openPhoto(){
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                .maxSelectNum(9)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // onResult Callback
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() == 0) return;
                //获取本地图片的选择地址，上传到服务器
                //头像的压缩和二次采样
                //把选中的图片插入到列表
                for(LocalMedia item:selectList){
                    sendMsgByImage(item.getPath());
                }
                break;
            default:
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

    /**
     * 发图片
     */
    private void sendMsgByImage(String path){
        Uri uri = Uri.parse(path);
        EMMessage msg = EMMessage.createImageSendMessage(uri, false, toUserId);
        /*EMImageMessageBody body = new EMImageMessageBody(uri);
        msg.addBody(body);*/
        //如果是群聊，设置chattype，默认是单聊
        EMClient.getInstance().chatManager().sendMessage(msg);
    }

    private void initMsgListner(){
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            msgsList.addAll(messages);
            recyChat.post(new Runnable() {
                @Override
                public void run() {
                    chatAdapter.notifyDataSetChanged();
                }
            });
        }

        /**
         * 接收透传消息  --- 头像更新
         * @param messages
         */
        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            for(EMMessage item:messages){
                if(item.getType() == EMMessage.Type.CMD){
                    EMCmdMessageBody msg = (EMCmdMessageBody) item.getBody();
                    if(Constants.ACTION_UPDATEHEADER.equals(msg.action())){
                        //刷新界面更新用户头像
                        String action = msg.action();
                        if(!TextUtils.isEmpty(action)){
                            String uid = item.getFrom();
                            SpUtils.getInstance().setValue(uid,action);
                            EMUserInfo user = UserInfoManager.getInstance().getUserInfoByUid(uid);
                            if(user != null){
                                user.setHeader(action);
                            }
                        }

                    }else if(Constants.ACTION_UPDATENICKNAME.equals(msg.action())){
                        //刷新界面更新用户昵称

                    }
                }
            }
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