package com.neocite.eaiblza.views;

import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.neocite.eaiblza.R;
import com.neocite.eaiblza.models.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by paulo-silva on 6/2/15.
 */
public class ChatSendMessageAdapter extends ArrayAdapter<Message> {

    private final Context context;
    private final ArrayList<Message> itemsArrayList;

    public ChatSendMessageAdapter(Context context, ArrayList<Message> itemsArrayList) {
        super(context, R.layout.chat_item_rcv, itemsArrayList);


        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.chat_item_sent, parent, false);;

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.lbl1);
        TextView valueView = (TextView) rowView.findViewById(R.id.lbl2);
        TextView statusView = (TextView) rowView.findViewById(R.id.lbl3);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getOwner());
        valueView.setText(itemsArrayList.get(position).getValue());

        String format = "dd/MM/yyyy HH:mm:ss";

        SimpleDateFormat brazilDateTimeFormat = new SimpleDateFormat(format, Locale.US);

        statusView.setText("Recebido em " + brazilDateTimeFormat.format(new Date()));


        // 5. retrn rowView
        return rowView;
    }

}
