package im.chaitanya.liveforyoutube;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.chaitanya.liveforyoutube.data.LiveMessage;
import timber.log.Timber;

public class PlaylistActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    DatabaseReference databaseReference;
    DatabaseReference roomReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @BindView(R.id.message_recycler) RecyclerView messageRecycler;
    @BindView(R.id.message_text) EditText messageText;
    String roomID;
    List<LiveMessage> messages = new ArrayList<>();

    private static final int RECOVERY_REQUEST = 1;
    public static final String YT_DEV_KEY = "AIzaSyACWSrV0r6-HoRBr4f1XyTl1qynRa8fRkc";
    private YouTubePlayerView youTubeView;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("groups");
        roomID = getIntent().getStringExtra(MainActivity.roomIDExtra);
        roomReference = databaseReference.child(roomID).child("messages");
        roomReference.addValueEventListener(messageListener);
        adapter = new MessageAdapter(this, messages);
        messageRecycler.setAdapter(adapter);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference.child(roomID).child("users").child(firebaseUser.getUid()).onDisconnect().setValue(null);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(YT_DEV_KEY, this);
    }

    ValueEventListener messageListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                LiveMessage message = messageSnapshot.getValue(LiveMessage.class);
                messages.add(message);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer,
                                        boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.loadPlaylist(roomID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YT_DEV_KEY, this);
        }
    }

    @OnClick(R.id.send_button)
    public void sendMessage() {
        LiveMessage message = new LiveMessage(firebaseAuth.getCurrentUser().getDisplayName().split(" ")[0], messageText.getText().toString());
        messageText.setText("");
        databaseReference.child(roomID).child("messages").push().setValue(message);
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
