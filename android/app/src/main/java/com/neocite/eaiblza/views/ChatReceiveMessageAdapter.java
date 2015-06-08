package com.neocite.eaiblza.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.neocite.eaiblza.R;
import com.neocite.eaiblza.models.Message;

import java.util.ArrayList;

/**
 * Created by paulo-silva on 6/2/15.
 */
public class ChatReceiveMessageAdapter extends ArrayAdapter<Message> {

    private final Context context;
    private final ArrayList<Message> itemsArrayList;

    public ChatReceiveMessageAdapter(Context context, ArrayList<Message> itemsArrayList) {

        super(context, R.layout.activity_chat_received_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.activity_chat_received_row, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getOwner());
        valueView.setText(itemsArrayList.get(position).getValue());

        // 5. retrn rowView
        return rowView;
    }

}
