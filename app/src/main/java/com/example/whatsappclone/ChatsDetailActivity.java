package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.whatsappclone.Adapters.ChatAdapter;
import com.example.whatsappclone.Fragments.ChatsFragment;
import com.example.whatsappclone.Models.MessageModel;
import com.example.whatsappclone.databinding.ActivityChatsDetailBinding;
import com.example.whatsappclone.databinding.FragmentChatsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatsDetailActivity extends AppCompatActivity {
    ActivityChatsDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderID = auth.getUid();
        String receiverID = getIntent().getStringExtra("userID");
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

        binding.chatsToolbarUsername.setText(userName);
        // Use Glide instead of Picasso to load pics. Picasso has orientation issues.
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile);
        Glide.with(ChatsDetailActivity.this).load(profilePic).apply(options).into(binding.chatsToolbarProfilepic);

        binding.chatsToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatsDetailActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageModels,this, receiverID);
        binding.chattingRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // Used for seeing the last message and not the first message(in the conversation) upon opening a Chats Activity with an Individual.
                                             // Also used for sending a message from the bottom of an activity.
        binding.chattingRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderID + receiverID;
        final String receiverRoom = receiverID + senderID;

        database.getReference().child("Chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot val : snapshot.getChildren()){
                            MessageModel model = val.getValue(MessageModel.class);
                            model.setMessageID(val.getKey());
                            messageModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged(); // Used for instantly updating the sender's messages in the RecyclerView Adapter.
                        if (messageModels.size()>=1){
                            binding.chattingRecyclerView.smoothScrollToPosition(messageModels.size()-1); // Scrolls down to the latest message as soon as it's sent.
                        }
                        else {
                            // Toast.makeText(ChatsDetailActivity.this, "No Messages to Display !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.chattingSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = binding.chattingMsg.getText().toString();
                if (!msg.isEmpty())
                {
                    final MessageModel model = new MessageModel(senderID,msg);
                    model.setTimestamp(new Date().getTime());
                    binding.chattingMsg.setText("");

                    database.getReference().child("Chats")
                            .child(senderRoom)
                            .push()
                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            database.getReference().child("Chats")
                                    .child(receiverRoom)
                                    .push()
                                    .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                        }
                    });
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ChatsFragment.adapter.notifyDataSetChanged();
    }
}
