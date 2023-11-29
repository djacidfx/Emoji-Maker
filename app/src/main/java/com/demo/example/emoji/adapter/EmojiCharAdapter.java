package com.demo.example.emoji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.example.R;
import com.demo.example.emoji.callbacks.EmojiCharItemClicked;
import com.demo.example.emoji.models.EmojiObject;
import java.util.List;


public class EmojiCharAdapter extends BaseAdapter {
    private EmojiCharItemClicked emojiCharItemClickedCallback;
    private LayoutInflater layoutInflater;
    private List<EmojiObject> listEmoji;
    private Context mContext;

    @Override 
    public long getItemId(int i) {
        return (long) i;
    }

    public EmojiCharAdapter(Context context, List<EmojiObject> list, EmojiCharItemClicked emojiCharItemClicked) {
        this.mContext = context;
        this.listEmoji = list;
        this.emojiCharItemClickedCallback = emojiCharItemClicked;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override 
    public int getCount() {
        return this.listEmoji.size();
    }

    @Override 
    public Object getItem(int i) {
        return this.listEmoji.get(i);
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        this.layoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        View inflate = this.layoutInflater.inflate(R.layout.item_emoji_char, viewGroup, false);
        final TextView textView = (TextView) inflate.findViewById(R.id.tvEmojiChar);
        textView.setText(this.listEmoji.get(i).getLinkEmojiNomal());
        textView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view2) {
                EmojiCharAdapter.this.emojiCharItemClickedCallback.emojiCharItemClickedListenner(textView.getText().toString());
            }
        });
        return inflate;
    }

    
    class ViewHolder {
        TextView tvEmoji;

        ViewHolder() {
        }
    }

    public void setDataChanges(List<EmojiObject> list) {
        this.listEmoji = list;
        notifyDataSetChanged();
    }
}
