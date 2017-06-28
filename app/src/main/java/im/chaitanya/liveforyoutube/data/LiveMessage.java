package im.chaitanya.liveforyoutube.data;

/**
 * Created by chaitanya on 28-06-2017.
 */

public class LiveMessage {
    private String senderName;
    private String message;

    public LiveMessage() {
    }

    public LiveMessage(String senderName, String message) {
        this.senderName = senderName;
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
