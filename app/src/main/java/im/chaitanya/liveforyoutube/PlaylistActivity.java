package im.chaitanya.liveforyoutube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import timber.log.Timber;

public class PlaylistActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    DatabaseReference databaseReference;
    DatabaseReference usersReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String roomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("groups");
        roomID = getIntent().getStringExtra(MainActivity.roomIDExtra);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference.child(roomID).child("users").child(firebaseUser.getUid()).onDisconnect().setValue(null);
    }
}
