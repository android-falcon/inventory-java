package com.example.user54.InventoryApp;

import static com.example.user54.InventoryApp.Item.itemList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.user54.InventoryApp.Model.CaptionItemInfo;
import com.example.user54.InventoryApp.Model.ItemCard;

import java.util.List;


public class CaptionListAdapter extends BaseAdapter {

    private Context context;
    List<CaptionItemInfo> itemsList;

    public CaptionListAdapter(Context context, List<CaptionItemInfo> itemsList) {
        this.context = context;
        this.itemsList = itemsList;

    }

    public CaptionListAdapter() {

    }

    public void setItemsList(List<CaptionItemInfo> itemsList) {
        this.itemsList = itemsList;

    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView itemCode, itemName,price,avlQty,i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14;//, price
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.caption_item_row, null);

        holder.avlQty =  view.findViewById(R.id.avlQty);

        holder.itemCode = (TextView) view.findViewById(R.id.ic);
        holder.itemName = (TextView) view.findViewById(R.id.in);
        holder.price = (TextView) view.findViewById(R.id.price);
        holder.i1 = (TextView) view.findViewById(R.id.i1);
        holder.i2 = (TextView) view.findViewById(R.id.i2);
        holder.i3 = (TextView) view.findViewById(R.id.i3);
        holder.i4 = (TextView) view.findViewById(R.id.i4);
        holder.i5 = (TextView) view.findViewById(R.id.i5);
        holder.i6 = (TextView) view.findViewById(R.id.i6);
        holder.i7 = (TextView) view.findViewById(R.id.i7);
        holder.i8 = (TextView) view.findViewById(R.id.i8);
        holder.i9 = (TextView) view.findViewById(R.id.i9);
        holder.i10 = (TextView) view.findViewById(R.id.i10);
        holder.i11 = (TextView) view.findViewById(R.id.i11);
        holder.i12 = (TextView) view.findViewById(R.id.i12);
        holder.i13 = (TextView) view.findViewById(R.id.i13);
        holder.i14 = (TextView) view.findViewById(R.id.i14);

        holder.itemCode.setText("" + itemsList.get(i).getItemOCode());
        holder.itemName.setText("" + itemsList.get(i).getItemNameA());
        holder.price.setText("" + itemsList.get(i).getF_D());
        holder.avlQty.setText("" + itemsList.get(i).getAVLQTY());

        holder.i1.setText("" + itemsList.get(i).getITEMUNIT());
        holder.i2.setText("" + itemsList.get(i).getITEMGROUP());
        holder.i3.setText("" + itemsList.get(i).getITEMCOLOR());
        holder.i4.setText("" + itemsList.get(i).getITEMSIZE());
        holder.i5.setText("" + itemsList.get(i).getITEMMODEL());
        holder.i6.setText("" + itemsList.get(i).getITEMGS());
        holder.i7.setText("" + itemsList.get(i).getITEMDIV());
        holder.i8.setText("" + itemsList.get(i).getITEMSUB1());
        holder.i9.setText("" + itemsList.get(i).getITEMSUB2());
        holder.i10.setText("" + itemsList.get(i).getITEMSUB3());
        holder.i11.setText("" + itemsList.get(i).getITEMSUB4());
        holder.i12.setText("" + itemsList.get(i).getITEMSUB5());
        holder.i13.setText("" + itemsList.get(i).getITEMSUB6());
        holder.i14.setText("" + itemsList.get(i).getITEMSUB7());

        return view;
    }




}

