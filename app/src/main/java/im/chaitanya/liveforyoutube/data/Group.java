package im.chaitanya.liveforyoutube.data;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by chaitanya on 28-06-2017.
 */

@IgnoreExtraProperties
public class Group{

    private String groupID;
    private List<LiveMessage> messages;
    private List<User> users;
    private List<String> playlist;
    int index;
    int seek;

    public Group() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Group(String groupID, List<LiveMessage> messages, List<User> users, List<String> playlist, int index, int seek) {
        this.groupID = groupID;
        this.messages = messages;
        this.users = users;
        this.playlist = playlist;
        this.index = index;
        this.seek = seek;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public List<LiveMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<LiveMessage> messages) {
        this.messages = messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<String> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist = playlist;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSeek() {
        return seek;
    }

    public void setSeek(int seek) {
        this.seek = seek;
    }
}