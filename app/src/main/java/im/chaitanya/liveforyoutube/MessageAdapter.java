package im.chaitanya.liveforyoutube;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import im.chaitanya.liveforyoutube.data.LiveMessage;

/**
 * Created by chaitanya on 28-06-2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView messageTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.sender_name);
            messageTextView = (TextView) itemView.findViewById(R.id.sender_message);
        }
    }

    private List<LiveMessage> messages;
    private Context context;

    public MessageAdapter(Context context, List<LiveMessage> messages) {
        this.messages = messages;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LiveMessage message = messages.get(position);
        TextView textView = holder.nameTextView;
        textView.setText(message.getSenderName());
        TextView messageTextView = holder.messageTextView;
        messageTextView.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
