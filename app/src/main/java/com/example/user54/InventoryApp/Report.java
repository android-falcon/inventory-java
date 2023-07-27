package com.example.user54.InventoryApp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.user54.InventoryApp.Model.AssestItem;
import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemInfo;
import com.example.user54.InventoryApp.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Report extends AppCompatActivity {

    LinearLayout exportText, ExportTransfer, exportExpDate, ExportAlternative,ExportNewItemList,showAssast;
    public static boolean preparAc=false;
    Dialog dialog;
    TextView home,sumText;
    String today;
    InventoryDatabase InventDB;
    
    public static List<ItemInfo> ItemInfoListForPrint;
    List<ItemInfo> itemInfos;
    List<ItemCard> itemCard;

    List<ItemInfo> itemInfosAcu;
    Animation animFadein;

    List<AssestItem> assestItemList;
    List<AssestItem> assestItemListBackUp;
List<String>locationList;
String location="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(controll.isYellow){
            setContentView(R.layout.report_menu2_yellow);
        }else{
            setContentView(R.layout.report_menu2);
        }


        initialization();
        controll co=new controll();
        int data= Integer.parseInt(co.readFromFile(Report.this));
        InventDB=new InventoryDatabase(Report.this,data);
        itemInfos=new ArrayList<>();
        itemInfosAcu=new ArrayList<>();
        assestItemList=new ArrayList<>();
        ItemInfoListForPrint=new ArrayList<>();
        assestItemListBackUp=new ArrayList<>();
        animFadein = AnimationUtils.loadAnimation(Report.this, R.anim.fade_in);
        exportText.startAnimation(animFadein);

        ExportNewItemList.startAnimation(animFadein);
        ExportTransfer.startAnimation(animFadein);
        showAssast.startAnimation(animFadein);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                           }
        });

        ExportAlternative.setVisibility(View.GONE);
        exportExpDate.setVisibility(View.GONE);
    }




    View.OnClickListener showDialogOnClick = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.expText:
                    exportText.setClickable(false);
                    showExportToTextDialog();//here
                    break;
                case R.id.expTransfer:
                    ExportTransfer.setClickable(false);
                    showExportTransferItemDialog();
                    break;
                case R.id.expExp:
                    showExportItemDataByExpiryDateDialog();
                    break;
                case R.id.expAlternative:
                    showExportAlternativeCodeDialog();
                    break;
                case R.id.showExportNewItemList:
                    ExportNewItemList.setClickable(false);
                    showExportNewItemList();
                    break;



                case R.id.showAssast:
                    showAssast.setClickable(false);
                    assestItemListBackUp=InventDB.getAllAssesstItemInfoBackup();
                    if(assestItemListBackUp.size()!=0){

                        showAssestExportToTextDialog();

                    }else {
                        backUpAdd();

                    }

                    break;
            }
        }
    };

    void backUpAdd(){
        Log.e("masterBack","jjj");
        assestItemList=InventDB.getAllAssesstItemInfo();
        for(int i=0;i<assestItemList.size();i++){

            InventDB.addAssetsItemInfoBackup(assestItemList.get(i));

        }

        InventDB.deleteAllItem("ASSEST_TABLE_INFO");
        int serial=InventDB.getMaxSerialAss();
        for(int i=0;i<assestItemList.size();i++){

            assestItemList.get(i).setSerial(""+(serial++));
            InventDB.addAssetsItemInfo(assestItemList.get(i));

        }

        showAssestExportToTextDialog();

    }

    void alertMessageDialog(String title, String message, final int swith, final String itemName , final String ItemCode) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Report.this);
        dialog.setTitle(title)
                .setMessage(message)
                .setNegativeButton( getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }
                })
                .setPositiveButton( getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        switch (swith) {
                            case 0:
                                finish();
                                moveTaskToBack(true);

                                break;
//                            case 1:
//                                InventDB.deleteAllItem("ITEM_CARD");
//                                Toast.makeText(CollectingData.this, "All Item Delete ", Toast.LENGTH_SHORT).show();
////                             progressDialog();
//                                break;
//                            case 2:
//                                InventDB.delete(ItemCode,itemName);
//                                break;

                        }
                    }
                }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ClickableViewAccessibility")
    void showExportToTextDialog() {
        dialog = new Dialog(Report.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        if(controll.isYellow){
            dialog.setContentView(R.layout.export_item_data_to_text_yellow);
        }else{
            dialog.setContentView(R.layout.export_item_data_to_text);
        }
//

        dialog.setCanceledOnTouchOutside(false);

       final Spinner locationSpinner=dialog.findViewById(R.id.locationSpinner);
       LinearLayout delByLoc=dialog.findViewById(R.id.delByLoc);
       final TextView sum=dialog.findViewById(R.id.sum);
        sumText=dialog.findViewById(R.id.sum);
        final CheckBox AccumCheckBox =(CheckBox)dialog.findViewById(R.id.AccumCheckBox);;
        Button exit,export;
        LinearLayout PrepareButton,del;
        final TableLayout tableReport =(TableLayout)dialog.findViewById(R.id.tableReport) ;
        ScrollView scrolItem =dialog.findViewById(R.id.scrolItem);

        exit = (Button) dialog.findViewById(R.id.exit);
        PrepareButton =  dialog.findViewById(R.id.PrepareButton);
        export = (Button) dialog.findViewById(R.id.export);

        del =  dialog.findViewById(R.id.del);


        locationList=new ArrayList<>();
        locationList=InventDB.getAllLocation();
        locationList.add(0,"All");
        ArrayAdapter MangAdapter = new ArrayAdapter<String>(Report.this, R.layout.spinner_style, locationList);
        locationSpinner.setAdapter(MangAdapter);

        delByLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new SweetAlertDialog(Report.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.deleteAllItemByLoc))
                        .setContentText(getResources().getString(R.string.allItemDelete))
                        .setConfirmText(getResources().getString(R.string.ok))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
//                                InventDB.deleteAllItem("ITEMS_INFO");
//                                InventDB.updateIsDeleteItemInfoBackup();


                                InventDB.deleteItemFromItemInfoByLocation(location);
                                if(AccumCheckBox.isChecked()) {
                                    for (int i = 0; i < itemInfosAcu.size(); i++) {
                                        InventDB.updateIsDeleteItemInfoBackupByItem(itemInfosAcu.get(i).getItemCode(), "" + itemInfosAcu.get(i).getSerialNo());
                                    }
                                }else {
                                    for (int i = 0; i < itemInfos.size(); i++) {
                                        InventDB.updateIsDeleteItemInfoBackupByItem(itemInfos.get(i).getItemCode(), "" + itemInfos.get(i).getSerialNo());
                                    }
                                }

                                itemInfosAcu.clear();
                                itemInfos.clear();

                                tableReport.removeAllViews();
                                if(AccumCheckBox.isChecked()){
                                    fillTableRows(tableReport,itemInfosAcu);
                                    try {
                                        sum.setText("0");
                                    }catch (Exception e){

                                    }

                                }else{
                                    fillTableRows(tableReport,itemInfos);
                                    try {
                                        sum.setText("0");
                                    }catch (Exception e){

                                    }
                                }

                                locationList=InventDB.getAllLocation();
                                locationList.add(0,"All");
                                ArrayAdapter MangAdapter = new ArrayAdapter<String>(Report.this, R.layout.spinner_style, locationList);
                                locationSpinner.setAdapter(MangAdapter);

                                sDialog.setTitleText(getResources().getString(R.string.delete))
                                        .setContentText(getResources().getString(R.string.delete_all_item))
                                        .setConfirmText(getResources().getString(R.string.ok))
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);


                            }
                        })
                        .setCancelButton(getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();


            }
        });

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tableReport.removeAllViews();
                location=locationList.get(i);
                itemInfosAcu=InventDB.getAllItemInfoSum(location);

                itemInfos=InventDB.getAllItemInfo_Report(location);



                if(AccumCheckBox.isChecked()){
                    fillTableRows(tableReport,itemInfosAcu);

                    try{

                        sum.setText(""+itemInfosAcu.get(itemInfosAcu.size()-1).getSummation());

                    }catch (Exception e){

                    }
                }else{
                    fillTableRows(tableReport,itemInfos);
                    try{

                        sum.setText(""+itemInfos.get(itemInfos.size()-1).getSummation());

                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tableReport.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for(int i=0)
                if(preparAc){
                    ItemInfoListForPrint =itemInfos;
                }else{
                    ItemInfoListForPrint =itemInfosAcu;
                }

                Intent printExport=new Intent(Report.this,BluetoothConnectMenu.class);
                printExport.putExtra("printKey", "2");
                startActivity(printExport);

//                Intent o1 = new Intent(Report.this, bMITP.class);
//                o1.putExtra("printKey", "0");
//                startActivity(o1);


            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AlertDialog.Builder dialog = new AlertDialog.Builder(Report.this,R.style.MyTheme);
//                dialog.setTitle( getResources().getString(R.string.deleteAllItemInfo))
//                        .setMessage( getResources().getString(R.string.allItemDelete))
//                        .setNegativeButton( getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialoginterface, int i) {
//                                dialoginterface.cancel();
//                            }
//                        })
//                        .setPositiveButton( getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialoginterface, int i) {
//                                InventDB.deleteAllItem("ITEMS_INFO");
////                                itemInfosAcu=InventDB.getAllItemInfoSum();
////
////                                itemInfos=InventDB.getAllItemInfo();
//                                itemInfosAcu.clear();
//                                itemInfos.clear();
//
//                                tableReport.removeAllViews();
//                                if(AccumCheckBox.isChecked()){
//                                    fillTableRows(tableReport,itemInfosAcu);
//                                }else{
//                                    fillTableRows(tableReport,itemInfos);
//                                }
//
//                            }
//                        }).show();




                new SweetAlertDialog(Report.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.deleteAllItemInfo))
                        .setContentText(getResources().getString(R.string.allItemDelete))
                        .setConfirmText(getResources().getString(R.string.ok))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                 InventDB.deleteAllItem("ITEMS_INFO");
                                InventDB.updateIsDeleteItemInfoBackup();

//                                itemInfosAcu=InventDB.getAllItemInfoSum();
//
//                                itemInfos=InventDB.getAllItemInfo();
                                itemInfosAcu.clear();
                                itemInfos.clear();

                                tableReport.removeAllViews();
                                if(AccumCheckBox.isChecked()){
                                    fillTableRows(tableReport,itemInfosAcu);
                                    try {
                                        sum.setText("" + itemInfosAcu.get(itemInfosAcu.size() - 1).getSummation());
                                    }catch (Exception e){

                                    }

                                }else{
                                    fillTableRows(tableReport,itemInfos);
                                    try {
                                        sum.setText("" + itemInfos.get(itemInfos.size() - 1).getSummation());
                                    }catch (Exception e){

                                    }
                                }


                                sDialog.setTitleText(getResources().getString(R.string.delete))
                                        .setContentText(getResources().getString(R.string.delete_all_item))
                                        .setConfirmText(getResources().getString(R.string.ok))
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);


                            }
                        })
                        .setCancelButton(getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();



            }
        });

        scrolItem.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        PrepareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableReport.removeAllViews();
                if(AccumCheckBox.isChecked()){
                    preparAc=false;
                    fillTableRows(tableReport,itemInfosAcu);

                }else{
                    preparAc=true;
                    fillTableRows(tableReport,itemInfos);
                }
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportText.setClickable(true);
                dialog.dismiss();
            }
        });

        tableReport.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        tableReport.removeAllViews();
        itemInfosAcu=InventDB.getAllItemInfoSum(location);

        itemInfos=InventDB.getAllItemInfo_Report(location);



        if(AccumCheckBox.isChecked()){
           fillTableRows(tableReport,itemInfosAcu);
           try {
               sum.setText("" + itemInfosAcu.get(itemInfosAcu.size() - 1).getSummation());
           }catch (Exception e){

           }
        }else{
            fillTableRows(tableReport,itemInfos);
            try {
                sum.setText("" + itemInfos.get(itemInfos.size() - 1).getSummation());
            }catch (Exception e){

            }

        }


        dialog.show();
    }


    void showAssestExportToTextDialog() {
        dialog = new Dialog(Report.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        if(controll.isYellow){
            dialog.setContentView(R.layout.export_item_data_to_text_yellow);
        }else{
            dialog.setContentView(R.layout.export_item_data_to_text);
        }
//

        dialog.setCanceledOnTouchOutside(false);

        final CheckBox AccumCheckBox =(CheckBox)dialog.findViewById(R.id.AccumCheckBox);;
        Button exit,export;
        LinearLayout PrepareButton,del;
        final TableLayout tableReport =(TableLayout)dialog.findViewById(R.id.tableReport) ;
        ScrollView scrolItem =dialog.findViewById(R.id.scrolItem);

        exit = (Button) dialog.findViewById(R.id.exit);
        PrepareButton =  dialog.findViewById(R.id.PrepareButton);
        export = (Button) dialog.findViewById(R.id.export);

        del =  dialog.findViewById(R.id.del);

        AccumCheckBox.setVisibility(View.GONE);

        tableReport.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for(int i=0)
//                if(preparAc){
//                    ItemInfoListForPrint =itemInfos;
//                }else{
//                    ItemInfoListForPrint =itemInfosAcu;
//                }
//
//                Intent printExport=new Intent(Report.this,BluetoothConnectMenu.class);
//                printExport.putExtra("printKey", "2");
//                startActivity(printExport);

//                Intent o1 = new Intent(Report.this, bMITP.class);
//                o1.putExtra("printKey", "0");
//                startActivity(o1);


            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AlertDialog.Builder dialog = new AlertDialog.Builder(Report.this,R.style.MyTheme);
//                dialog.setTitle( getResources().getString(R.string.deleteAllItemInfo))
//                        .setMessage( getResources().getString(R.string.allItemDelete))
//                        .setNegativeButton( getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialoginterface, int i) {
//                                dialoginterface.cancel();
//                            }
//                        })
//                        .setPositiveButton( getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialoginterface, int i) {
//                                InventDB.deleteAllItem("ITEMS_INFO");
////                                itemInfosAcu=InventDB.getAllItemInfoSum();
////
////                                itemInfos=InventDB.getAllItemInfo();
//                                itemInfosAcu.clear();
//                                itemInfos.clear();
//
//                                tableReport.removeAllViews();
//                                if(AccumCheckBox.isChecked()){
//                                    fillTableRows(tableReport,itemInfosAcu);
//                                }else{
//                                    fillTableRows(tableReport,itemInfos);
//                                }
//
//                            }
//                        }).show();




                new SweetAlertDialog(Report.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.deleteAllItemInfo))
                        .setContentText(getResources().getString(R.string.allItemDelete))
                        .setConfirmText(getResources().getString(R.string.ok))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                InventDB.deleteAllItem("ASSEST_TABLE_INFO");
                               // InventDB.updateIsDeleteItemInfoBackup();

//                                itemInfosAcu=InventDB.getAllItemInfoSum();
//
//                                itemInfos=InventDB.getAllItemInfo();
                                assestItemList.clear();

                                tableReport.removeAllViews();
//                                if(AccumCheckBox.isChecked()){
//                                    fillTableRows(tableReport,itemInfosAcu);
//                                }else{
//                                    fillTableRows(tableReport,itemInfos);
//                                }

                                fillTableRowsAssest(tableReport,assestItemList);

                                sDialog.setTitleText(getResources().getString(R.string.delete))
                                        .setContentText(getResources().getString(R.string.delete_all_item))
                                        .setConfirmText(getResources().getString(R.string.ok))
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);


                            }
                        })
                        .setCancelButton(getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();



            }
        });

        scrolItem.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        PrepareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableReport.removeAllViews();
//                if(AccumCheckBox.isChecked()){
//                    preparAc=false;
//                    fillTableRows(tableReport,itemInfosAcu);
//
//                }else{
//                    preparAc=true;
//                    fillTableRows(tableReport,itemInfos);
//                }
                fillTableRowsAssest(tableReport,assestItemList);
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAssast.setClickable(true);
                dialog.dismiss();
            }
        });

        tableReport.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        tableReport.removeAllViews();


        assestItemList=InventDB.getAllAssesstItemInfo();


//        if(AccumCheckBox.isChecked()){
//            fillTableRows(tableReport,itemInfosAcu);
//        }else{
//            fillTableRows(tableReport,itemInfos);
//        }

        fillTableRowsAssest(tableReport,assestItemList);

        dialog.show();
    }


    @SuppressLint("ClickableViewAccessibility")
    void showExportNewItemList() {
        dialog = new Dialog(Report.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        if(controll.isYellow){
            dialog.setContentView(R.layout.new_item_list);
        }else{
            dialog.setContentView(R.layout.new_item_list);
        }
//

        dialog.setCanceledOnTouchOutside(false);

//        final CheckBox AccumCheckBox =(CheckBox)dialog.findViewById(R.id.AccumCheckBox);;
        Button exit,export;
        LinearLayout PrepareButton,del;
        final TableLayout tableReport =(TableLayout)dialog.findViewById(R.id.tableReport) ;
        ScrollView scrolItem =dialog.findViewById(R.id.scrolItem);

        exit = (Button) dialog.findViewById(R.id.exit);
        PrepareButton =  dialog.findViewById(R.id.PrepareButton);
        export = (Button) dialog.findViewById(R.id.export);

        del =  dialog.findViewById(R.id.del);

        itemCard=new ArrayList<>();
        tableReport.removeAllViews();
        itemCard=InventDB.getAllItemCardNew();
        fillTableRowsItemCard(tableReport,itemCard);

        tableReport.setOnTouchListener(new View.OnTouchListener() {//2 list

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


//        export.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                for(int i=0)
//                if(preparAc){
//                    ItemInfoListForPrint =itemInfos;
//                }else{
//                    ItemInfoListForPrint =itemInfosAcu;
//                }
//
//                Intent printExport=new Intent(Report.this,BluetoothConnectMenu.class);
//                printExport.putExtra("printKey", "2");
//                startActivity(printExport);
//
////                Intent o1 = new Intent(Report.this, bMITP.class);
////                o1.putExtra("printKey", "0");
////                startActivity(o1);
//
//
//            }
//        });

//        del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                AlertDialog.Builder dialog = new AlertDialog.Builder(Report.this,R.style.MyTheme);
////                dialog.setTitle( getResources().getString(R.string.deleteAllItemInfo))
////                        .setMessage( getResources().getString(R.string.allItemDelete))
////                        .setNegativeButton( getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
////                            public void onClick(DialogInterface dialoginterface, int i) {
////                                dialoginterface.cancel();
////                            }
////                        })
////                        .setPositiveButton( getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
////                            public void onClick(DialogInterface dialoginterface, int i) {
////                                InventDB.deleteAllItem("ITEMS_INFO");
//////                                itemInfosAcu=InventDB.getAllItemInfoSum();
//////
//////                                itemInfos=InventDB.getAllItemInfo();
////                                itemInfosAcu.clear();
////                                itemInfos.clear();
////
////                                tableReport.removeAllViews();
////                                if(AccumCheckBox.isChecked()){
////                                    fillTableRows(tableReport,itemInfosAcu);
////                                }else{
////                                    fillTableRows(tableReport,itemInfos);
////                                }
////
////                            }
////                        }).show();
//
//
//
//
//                new SweetAlertDialog(Report.this, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText(getResources().getString(R.string.deleteAllItemInfo))
//                        .setContentText(getResources().getString(R.string.allItemDelete))
//                        .setConfirmText(getResources().getString(R.string.ok))
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                InventDB.deleteAllItem("ITEMS_INFO");
//                                InventDB.updateIsDeleteItemInfoBackup();
//
////                                itemInfosAcu=InventDB.getAllItemInfoSum();
////
////                                itemInfos=InventDB.getAllItemInfo();
//                                itemInfosAcu.clear();
//                                itemInfos.clear();
//
//                                tableReport.removeAllViews();
//                                if(AccumCheckBox.isChecked()){
//                                    fillTableRows(tableReport,itemInfosAcu);
//                                }else{
//                                    fillTableRows(tableReport,itemInfos);
//                                }
//
//
//                                sDialog.setTitleText(getResources().getString(R.string.delete))
//                                        .setContentText(getResources().getString(R.string.delete_all_item))
//                                        .setConfirmText(getResources().getString(R.string.ok))
//                                        .showCancelButton(false)
//                                        .setCancelClickListener(null)
//                                        .setConfirmClickListener(null)
//                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//
//
//                            }
//                        })
//                        .setCancelButton(getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.dismissWithAnimation();
//                            }
//                        })
//                        .show();
//
//
//
//            }
//        });

        scrolItem.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        PrepareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableReport.removeAllViews();
//                if(AccumCheckBox.isChecked()){
//                    preparAc=false;
//                    fillTableRowsItemCard(tableReport,itemInfosAcu);
//
//                }else{
//                    preparAc=true;
//                    fillTableRows(tableReport,itemInfos);
//                }

                fillTableRowsItemCard(tableReport,itemCard);

            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExportNewItemList.setClickable(true);
                dialog.dismiss();
            }
        });

        tableReport.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });




//        if(AccumCheckBox.isChecked()){
//            fillTableRows(tableReport,itemInfosAcu);
//        }else{
//            fillTableRows(tableReport,itemInfos);
//        }


        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    void fillTableRows(final TableLayout tableLayout, final List<ItemInfo> itemInfos) {

        for(int i=0;i<itemInfos.size();i++){
            TableRow row = new TableRow(Report.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0, 5, 0, 0);
//            row.setBottom(5);
            row.setPadding(0, 0, 0, 25);
//            row.setBackground(getResources().getDrawable(R.drawable.no_thing));
            row.setLayoutParams(lp);
            row.setTag(i+"");
        for (int k = 0; k < 4; k++) {
            final TextView textView = new TextView(Report.this);
            switch (k) {
                case 0:
                    textView.setText(itemInfos.get(i).getItemCode());
                    break;
                case 1:
                    textView.setText(itemInfos.get(i).getItemName());
                    break;
                case 2:
                    textView.setText(""+itemInfos.get(i).getItemQty());
                    break;
                case 3:
                    textView.setText(""+itemInfos.get(i).getSalePrice());
                    break;
//                case 4:
//                    textView.setText(Exp);
//                    break;


            }

            textView.setTextColor(ContextCompat.getColor(Report.this, R.color.white));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(14);
//                textView.setId(Integer.parseInt(textId + "" + i));

            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            textView.setLayoutParams(lp2);

            row.addView(textView);

        }



            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    if( preparAc){
//                        final String[] operation = { getResources().getString(R.string.update), getResources().getString(R.string.delete)};
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Report.this,R.style.MyTheme);
//                        builder.setTitle( getResources().getString(R.string.choess));
//                        builder.setItems(operation, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // the user clicked on colors[which]
//                                switch (which){
//                                    case 0:
//                                        ItemInfo itemInfo=itemInfos.get(Integer.parseInt(v.getTag().toString()));
//
//
//                                        updateQtyDialog(itemInfo, Integer.parseInt(v.getTag().toString()),tableLayout);
//
////                                        Toast.makeText(Report.this,  getResources().getString(R.string.update), Toast.LENGTH_SHORT).show();
//                                        TostMesage( getResources().getString(R.string.update));
//                                        break;
//                                    case 1:
//
//                                        final int index =Integer.parseInt(v.getTag().toString());
//                                        AlertDialog.Builder DeleteDialog = new AlertDialog.Builder(Report.this,R.style.MyTheme);
//                                        DeleteDialog.setTitle( getResources().getString(R.string.deleteAllItemInfo))
//                                                .setMessage( getResources().getString(R.string.deletethisitem)+"\n "+ getResources().getString(R.string.item_name)+itemInfos.get(index).getItemName()+"\n"+  getResources().getString(R.string.item_qty)+itemInfos.get(index).getItemQty()+"?")
//                                                .setNegativeButton( getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialoginterface, int i) {
//                                                        dialoginterface.cancel();
//                                                    }
//                                                })
//                                                .setPositiveButton( getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialoginterface, int i) {
//                                                        InventDB.deleteItemFromItemInfo(itemInfos.get(index).getItemCode(), String.valueOf(itemInfos.get(index).getSerialNo()));
//
//
//                                                        itemInfosAcu=InventDB.getAllItemInfoSum();
//                                                        tableLayout.removeAllViews();
//                                                        itemInfos.remove(index);
//                                                        fillTableRows(tableLayout,itemInfos);
//                                                    }
//                                                }).show();
//
////                                        Toast.makeText(Report.this,  getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
//                                        TostMesage(getResources().getString(R.string.delete));
//
//                                        break;
//                                }
//                            }
//                        });
//                        builder.show();



                        final Button editText = new Button(Report.this);
                        final Button checkBox = new Button(Report.this);
                        editText.setText(getResources().getString(R.string.update));

                        checkBox.setText(getResources().getString(R.string.delete));

                        if (SweetAlertDialog.DARK_STYLE) {
                            editText.setTextColor(Color.WHITE);
                            checkBox.setTextColor(Color.WHITE);
                        }

                        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        linearLayout.addView(editText);
                        linearLayout.addView(checkBox);

                        final SweetAlertDialog dialog = new SweetAlertDialog(Report.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText(getResources().getString(R.string.choess))
                                .hideConfirmButton();


                        editText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ItemInfo itemInfo=itemInfos.get(Integer.parseInt(v.getTag().toString()));


                                updateQtyDialog(itemInfo, Integer.parseInt(v.getTag().toString()),tableLayout);

//                                        Toast.makeText(Report.this,  getResources().getString(R.string.update), Toast.LENGTH_SHORT).show();
                                TostMesage( getResources().getString(R.string.update));
                                dialog.dismissWithAnimation();
                            }
                        });


                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final int index =Integer.parseInt(v.getTag().toString());
                                new SweetAlertDialog(Report.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.aresure))
                                        .setContentText(getResources().getString(R.string.deletethisitem)+"\n "+ getResources().getString(R.string.item_name)+itemInfos.get(index).getItemName()+"\n"+  getResources().getString(R.string.item_qty)+itemInfos.get(index).getItemQty()+"?")
                                        .setCancelText(getResources().getString(R.string.cancel))
                                        .setConfirmText(getResources().getString(R.string.delete))
                                        .showCancelButton(true)
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                // reuse previous dialog instance, keep widget user state, reset them if you need

                                                sDialog.setTitleText(getResources().getString(R.string.cancel)+"!")
                                                        .setContentText(getResources().getString(R.string.canceldelete))
                                                        .setConfirmText(getResources().getString(R.string.ok))
                                                        .showCancelButton(false)
                                                        .setCancelClickListener(null)
                                                        .setConfirmClickListener(null)
                                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                            }
                                        })
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {


                                                InventDB.deleteItemFromItemInfo(itemInfos.get(index).getItemCode(), String.valueOf(itemInfos.get(index).getSerialNo()));

                                                InventDB.updateIsDeleteItemInfoBackupByItem(itemInfos.get(index).getItemCode(), String.valueOf(itemInfos.get(index).getSerialNo()));
                                                        itemInfosAcu=InventDB.getAllItemInfoSum(location);
                                                        tableLayout.removeAllViews();
                                                        itemInfos.remove(index);
                                                        fillTableRows(tableLayout,itemInfos);
                                                        try {
                                                            sumText.setText("" + itemInfosAcu.get(itemInfosAcu.size() - 1).getSummation());
                                                        }catch (Exception e){

                                                        }
                                                sDialog.setTitleText(getResources().getString(R.string.delete))
                                                        .setContentText(getResources().getString(R.string.rowdelete))
                                                        .setConfirmText(getResources().getString(R.string.ok))
                                                        .showCancelButton(false)
                                                        .setCancelClickListener(null)
                                                        .setConfirmClickListener(null)
                                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            }
                                        })
                                        .show();
                                dialog.dismissWithAnimation();
                            }
                        });

                        dialog.setCustomView(linearLayout);
                        dialog.show();





                    }else{
//                        Toast.makeText(Report.this, getResources().getString(R.string.accumulate_item), Toast.LENGTH_SHORT).show();
                        TostMesage(getResources().getString(R.string.accumulate_item));

                    }




                    return false;
                }
            });


//
//        row.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(!checkBox.isChecked()&&  preparAc){
//
////                TableRow rows = (TableRow) v.findViewById(v.getId());
////                TextView text = (TextView) rows.getChildAt(0);
//
//                    final String[] operation = {"Update", "Delete Raw"};
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Report.this,R.style.MyTheme);
//                    builder.setTitle("Click Operation ");
//                    builder.setItems(operation, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // the user clicked on colors[which]
//                            switch (which){
//                                case 0:
//                                    Toast.makeText(Report.this, "Update", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 1:
//                                    Toast.makeText(Report.this, "Delete", Toast.LENGTH_SHORT).show();
//
//                                    break;
//                            }
//                        }
//                    });
//                    builder.show();
//
//                }else{
//                    Toast.makeText(Report.this, "Accumlate Check Box is Checked ", Toast.LENGTH_SHORT).show();
//                }
//
////                Log.e("rowid,", "...." + "" + v.getId() + "----->" + text.getText().toString());
////
////                itemCodeText.setText(text.getText().toString());
////                textId = 0;
////                dialogFinsh.dismiss();
//            }
//        });


        tableLayout.addView(row);
    }

    }
    @SuppressLint("SetTextI18n")
    void fillTableRowsAssest(final TableLayout tableLayout, final List<AssestItem> itemInfos) {

        for(int i=0;i<itemInfos.size();i++){
            TableRow row = new TableRow(Report.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0, 5, 0, 0);
//            row.setBottom(5);
            row.setPadding(0, 0, 0, 25);
//            row.setBackground(getResources().getDrawable(R.drawable.no_thing));
            row.setLayoutParams(lp);
            row.setTag(i+"");
            for (int k = 0; k < 4; k++) {
                final TextView textView = new TextView(Report.this);
                switch (k) {
                    case 0:
                        textView.setText(itemInfos.get(i).getAssesstCode());
                        break;
                    case 1:
                        textView.setText(itemInfos.get(i).getAssesstName());
                        break;
                    case 2:
                        textView.setText(""+itemInfos.get(i).getAssesstQty());
                        break;
                    case 3:
                        textView.setText(""+itemInfos.get(i).getPrice());
                        break;
//                case 4:
//                    textView.setText(Exp);
//                    break;


                }

                textView.setTextColor(ContextCompat.getColor(Report.this, R.color.white));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(14);
//                textView.setId(Integer.parseInt(textId + "" + i));

                TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                textView.setLayoutParams(lp2);

                row.addView(textView);

            }



            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
//                    if( preparAc){
//                        final String[] operation = { getResources().getString(R.string.update), getResources().getString(R.string.delete)};
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Report.this,R.style.MyTheme);
//                        builder.setTitle( getResources().getString(R.string.choess));
//                        builder.setItems(operation, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // the user clicked on colors[which]
//                                switch (which){
//                                    case 0:
//                                        ItemInfo itemInfo=itemInfos.get(Integer.parseInt(v.getTag().toString()));
//
//
//                                        updateQtyDialog(itemInfo, Integer.parseInt(v.getTag().toString()),tableLayout);
//
////                                        Toast.makeText(Report.this,  getResources().getString(R.string.update), Toast.LENGTH_SHORT).show();
//                                        TostMesage( getResources().getString(R.string.update));
//                                        break;
//                                    case 1:
//
//                                        final int index =Integer.parseInt(v.getTag().toString());
//                                        AlertDialog.Builder DeleteDialog = new AlertDialog.Builder(Report.this,R.style.MyTheme);
//                                        DeleteDialog.setTitle( getResources().getString(R.string.deleteAllItemInfo))
//                                                .setMessage( getResources().getString(R.string.deletethisitem)+"\n "+ getResources().getString(R.string.item_name)+itemInfos.get(index).getItemName()+"\n"+  getResources().getString(R.string.item_qty)+itemInfos.get(index).getItemQty()+"?")
//                                                .setNegativeButton( getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialoginterface, int i) {
//                                                        dialoginterface.cancel();
//                                                    }
//                                                })
//                                                .setPositiveButton( getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialoginterface, int i) {
//                                                        InventDB.deleteItemFromItemInfo(itemInfos.get(index).getItemCode(), String.valueOf(itemInfos.get(index).getSerialNo()));
//
//
//                                                        itemInfosAcu=InventDB.getAllItemInfoSum();
//                                                        tableLayout.removeAllViews();
//                                                        itemInfos.remove(index);
//                                                        fillTableRows(tableLayout,itemInfos);
//                                                    }
//                                                }).show();
//
////                                        Toast.makeText(Report.this,  getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
//                                        TostMesage(getResources().getString(R.string.delete));
//
//                                        break;
//                                }
//                            }
//                        });
//                        builder.show();



                        final Button editText = new Button(Report.this);
                        final Button checkBox = new Button(Report.this);
                        editText.setText(getResources().getString(R.string.update));

                        checkBox.setText(getResources().getString(R.string.delete));

                        if (SweetAlertDialog.DARK_STYLE) {
                            editText.setTextColor(Color.WHITE);
                            checkBox.setTextColor(Color.WHITE);
                        }

                        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        linearLayout.addView(editText);
                        linearLayout.addView(checkBox);

                        final SweetAlertDialog dialog = new SweetAlertDialog(Report.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText(getResources().getString(R.string.choess))
                                .hideConfirmButton();


                        editText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AssestItem assestItem=assestItemList.get(Integer.parseInt(v.getTag().toString()));


                                updateQtyDialogAss(assestItem, Integer.parseInt(v.getTag().toString()),tableLayout);

//                                        Toast.makeText(Report.this,  getResources().getString(R.string.update), Toast.LENGTH_SHORT).show();
                                TostMesage( getResources().getString(R.string.update));
                                dialog.dismissWithAnimation();
                            }
                        });


                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final int index =Integer.parseInt(v.getTag().toString());
                                new SweetAlertDialog(Report.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.aresure))
                                        .setContentText(getResources().getString(R.string.deletethisitem)+"\n "+ getResources().getString(R.string.item_name)+itemInfos.get(index).getAssesstName()+"\n"+  getResources().getString(R.string.item_qty)+itemInfos.get(index).getAssesstQty()+"?")
                                        .setCancelText(getResources().getString(R.string.cancel))
                                        .setConfirmText(getResources().getString(R.string.delete))
                                        .showCancelButton(true)
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                // reuse previous dialog instance, keep widget user state, reset them if you need

                                                sDialog.setTitleText(getResources().getString(R.string.cancel)+"!")
                                                        .setContentText(getResources().getString(R.string.canceldelete))
                                                        .setConfirmText(getResources().getString(R.string.ok))
                                                        .showCancelButton(false)
                                                        .setCancelClickListener(null)
                                                        .setConfirmClickListener(null)
                                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                            }
                                        })
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {


                                                InventDB.deleteItemFromItemInfoAssest(itemInfos.get(index).getAssesstCode(), String.valueOf(itemInfos.get(index).getSerial()));

                                               // InventDB.updateIsDeleteItemInfoBackupByItem(itemInfos.get(index).getItemCode(), String.valueOf(itemInfos.get(index).getSerialNo()));
                                               // itemInfosAcu=InventDB.getAllItemInfoSum();
                                                tableLayout.removeAllViews();
                                                itemInfos.remove(index);
                                                fillTableRowsAssest(tableLayout,itemInfos);

                                                sDialog.setTitleText(getResources().getString(R.string.delete))
                                                        .setContentText(getResources().getString(R.string.rowdelete))
                                                        .setConfirmText(getResources().getString(R.string.ok))
                                                        .showCancelButton(false)
                                                        .setCancelClickListener(null)
                                                        .setConfirmClickListener(null)
                                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            }
                                        })
                                        .show();
                                dialog.dismissWithAnimation();
                            }
                        });

                        dialog.setCustomView(linearLayout);
                        dialog.show();





//                    }else{
////                        Toast.makeText(Report.this, getResources().getString(R.string.accumulate_item), Toast.LENGTH_SHORT).show();
//                        TostMesage(getResources().getString(R.string.accumulate_item));
//
//                    }




                    return false;
                }
            });


//
//        row.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(!checkBox.isChecked()&&  preparAc){
//
////                TableRow rows = (TableRow) v.findViewById(v.getId());
////                TextView text = (TextView) rows.getChildAt(0);
//
//                    final String[] operation = {"Update", "Delete Raw"};
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Report.this,R.style.MyTheme);
//                    builder.setTitle("Click Operation ");
//                    builder.setItems(operation, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // the user clicked on colors[which]
//                            switch (which){
//                                case 0:
//                                    Toast.makeText(Report.this, "Update", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 1:
//                                    Toast.makeText(Report.this, "Delete", Toast.LENGTH_SHORT).show();
//
//                                    break;
//                            }
//                        }
//                    });
//                    builder.show();
//
//                }else{
//                    Toast.makeText(Report.this, "Accumlate Check Box is Checked ", Toast.LENGTH_SHORT).show();
//                }
//
////                Log.e("rowid,", "...." + "" + v.getId() + "----->" + text.getText().toString());
////
////                itemCodeText.setText(text.getText().toString());
////                textId = 0;
////                dialogFinsh.dismiss();
//            }
//        });


            tableLayout.addView(row);
        }

    }


    void fillTableRowsItemCard(final TableLayout tableLayout, final List<ItemCard> itemInfos) {

        for(int i=0;i<itemInfos.size();i++){
            TableRow row = new TableRow(Report.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0, 5, 0, 0);
//            row.setBottom(5);
            row.setPadding(0, 0, 0, 25);
//            row.setBackground(getResources().getDrawable(R.drawable.no_thing));
            row.setLayoutParams(lp);
            row.setTag(i+"");
            for (int k = 0; k < 3; k++) {
                final TextView textView = new TextView(Report.this);
                switch (k) {
                    case 0:
                        textView.setText(itemInfos.get(i).getItemCode());
                        break;
                    case 1:
                        textView.setText(itemInfos.get(i).getItemName());
                        break;
//                    case 2:
//                        textView.setText(""+itemInfos.get(i).getItemQty());
//                        break;
                    case 2:
                        textView.setText(""+itemInfos.get(i).getFDPRC());
                        break;
//                case 4:
//                    textView.setText(Exp);
//                    break;


                }

                textView.setTextColor(ContextCompat.getColor(Report.this, R.color.white));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(14);
//                textView.setId(Integer.parseInt(textId + "" + i));

                TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                textView.setLayoutParams(lp2);

                row.addView(textView);

            }


//
//            row.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(final View v) {
//                    if( preparAc){
////                        final String[] operation = { getResources().getString(R.string.update), getResources().getString(R.string.delete)};
////
////                        AlertDialog.Builder builder = new AlertDialog.Builder(Report.this,R.style.MyTheme);
////                        builder.setTitle( getResources().getString(R.string.choess));
////                        builder.setItems(operation, new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                // the user clicked on colors[which]
////                                switch (which){
////                                    case 0:
////                                        ItemInfo itemInfo=itemInfos.get(Integer.parseInt(v.getTag().toString()));
////
////
////                                        updateQtyDialog(itemInfo, Integer.parseInt(v.getTag().toString()),tableLayout);
////
//////                                        Toast.makeText(Report.this,  getResources().getString(R.string.update), Toast.LENGTH_SHORT).show();
////                                        TostMesage( getResources().getString(R.string.update));
////                                        break;
////                                    case 1:
////
////                                        final int index =Integer.parseInt(v.getTag().toString());
////                                        AlertDialog.Builder DeleteDialog = new AlertDialog.Builder(Report.this,R.style.MyTheme);
////                                        DeleteDialog.setTitle( getResources().getString(R.string.deleteAllItemInfo))
////                                                .setMessage( getResources().getString(R.string.deletethisitem)+"\n "+ getResources().getString(R.string.item_name)+itemInfos.get(index).getItemName()+"\n"+  getResources().getString(R.string.item_qty)+itemInfos.get(index).getItemQty()+"?")
////                                                .setNegativeButton( getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
////                                                    public void onClick(DialogInterface dialoginterface, int i) {
////                                                        dialoginterface.cancel();
////                                                    }
////                                                })
////                                                .setPositiveButton( getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
////                                                    public void onClick(DialogInterface dialoginterface, int i) {
////                                                        InventDB.deleteItemFromItemInfo(itemInfos.get(index).getItemCode(), String.valueOf(itemInfos.get(index).getSerialNo()));
////
////
////                                                        itemInfosAcu=InventDB.getAllItemInfoSum();
////                                                        tableLayout.removeAllViews();
////                                                        itemInfos.remove(index);
////                                                        fillTableRows(tableLayout,itemInfos);
////                                                    }
////                                                }).show();
////
//////                                        Toast.makeText(Report.this,  getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
////                                        TostMesage(getResources().getString(R.string.delete));
////
////                                        break;
////                                }
////                            }
////                        });
////                        builder.show();
//
//
//
//                        final Button editText = new Button(Report.this);
//                        final Button checkBox = new Button(Report.this);
//                        editText.setText(getResources().getString(R.string.update));
//
//                        checkBox.setText(getResources().getString(R.string.delete));
//
//                        if (SweetAlertDialog.DARK_STYLE) {
//                            editText.setTextColor(Color.WHITE);
//                            checkBox.setTextColor(Color.WHITE);
//                        }
//
//                        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
//                        linearLayout.setOrientation(LinearLayout.VERTICAL);
//                        linearLayout.addView(editText);
//                        linearLayout.addView(checkBox);
//
//                        final SweetAlertDialog dialog = new SweetAlertDialog(Report.this, SweetAlertDialog.NORMAL_TYPE)
//                                .setTitleText(getResources().getString(R.string.choess))
//                                .hideConfirmButton();
//
//
//                        editText.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                ItemInfo itemInfo=itemInfos.get(Integer.parseInt(v.getTag().toString()));
//
//
//                                updateQtyDialog(itemInfo, Integer.parseInt(v.getTag().toString()),tableLayout);
//
////                                        Toast.makeText(Report.this,  getResources().getString(R.string.update), Toast.LENGTH_SHORT).show();
//                                TostMesage( getResources().getString(R.string.update));
//                                dialog.dismissWithAnimation();
//                            }
//                        });
//
//
//                        checkBox.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                final int index =Integer.parseInt(v.getTag().toString());
//                                new SweetAlertDialog(Report.this, SweetAlertDialog.WARNING_TYPE)
//                                        .setTitleText(getResources().getString(R.string.aresure))
//                                        .setContentText(getResources().getString(R.string.deletethisitem)+"\n "+ getResources().getString(R.string.item_name)+itemInfos.get(index).getItemName()+"\n"+  getResources().getString(R.string.item_qty)+itemInfos.get(index).getItemQty()+"?")
//                                        .setCancelText(getResources().getString(R.string.cancel))
//                                        .setConfirmText(getResources().getString(R.string.delete))
//                                        .showCancelButton(true)
//                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sDialog) {
//                                                // reuse previous dialog instance, keep widget user state, reset them if you need
//
//                                                sDialog.setTitleText(getResources().getString(R.string.cancel)+"!")
//                                                        .setContentText(getResources().getString(R.string.canceldelete))
//                                                        .setConfirmText(getResources().getString(R.string.ok))
//                                                        .showCancelButton(false)
//                                                        .setCancelClickListener(null)
//                                                        .setConfirmClickListener(null)
//                                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
//
//                                            }
//                                        })
//                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sDialog) {
//
//
//                                                InventDB.deleteItemFromItemInfo(itemInfos.get(index).getItemCode(), String.valueOf(itemInfos.get(index).getSerialNo()));
//
//                                                InventDB.updateIsDeleteItemInfoBackupByItem(itemInfos.get(index).getItemCode(), String.valueOf(itemInfos.get(index).getSerialNo()));
//                                                itemInfosAcu=InventDB.getAllItemInfoSum();
//                                                tableLayout.removeAllViews();
//                                                itemInfos.remove(index);
//                                                fillTableRows(tableLayout,itemInfos);
//
//                                                sDialog.setTitleText(getResources().getString(R.string.delete))
//                                                        .setContentText(getResources().getString(R.string.rowdelete))
//                                                        .setConfirmText(getResources().getString(R.string.ok))
//                                                        .showCancelButton(false)
//                                                        .setCancelClickListener(null)
//                                                        .setConfirmClickListener(null)
//                                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                                            }
//                                        })
//                                        .show();
//                                dialog.dismissWithAnimation();
//                            }
//                        });
//
//                        dialog.setCustomView(linearLayout);
//                        dialog.show();
//
//
//
//
//
//                    }else{
////                        Toast.makeText(Report.this, getResources().getString(R.string.accumulate_item), Toast.LENGTH_SHORT).show();
//                        TostMesage(getResources().getString(R.string.accumulate_item));
//
//                    }
//
//
//
//
//                    return false;
//                }
//            });


//
//        row.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(!checkBox.isChecked()&&  preparAc){
//
////                TableRow rows = (TableRow) v.findViewById(v.getId());
////                TextView text = (TextView) rows.getChildAt(0);
//
//                    final String[] operation = {"Update", "Delete Raw"};
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Report.this,R.style.MyTheme);
//                    builder.setTitle("Click Operation ");
//                    builder.setItems(operation, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // the user clicked on colors[which]
//                            switch (which){
//                                case 0:
//                                    Toast.makeText(Report.this, "Update", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 1:
//                                    Toast.makeText(Report.this, "Delete", Toast.LENGTH_SHORT).show();
//
//                                    break;
//                            }
//                        }
//                    });
//                    builder.show();
//
//                }else{
//                    Toast.makeText(Report.this, "Accumlate Check Box is Checked ", Toast.LENGTH_SHORT).show();
//                }
//
////                Log.e("rowid,", "...." + "" + v.getId() + "----->" + text.getText().toString());
////
////                itemCodeText.setText(text.getText().toString());
////                textId = 0;
////                dialogFinsh.dismiss();
//            }
//        });


            tableLayout.addView(row);
        }

    }



    void upDate (){

        final EditText editText = new EditText(this);
        final CheckBox checkBox = new CheckBox(this);
        editText.setText("Some edit text");
        checkBox.setChecked(true);
        checkBox.setText("Some checkbox");

        if (SweetAlertDialog.DARK_STYLE) {
            editText.setTextColor(Color.WHITE);
            checkBox.setTextColor(Color.WHITE);
        }

        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(editText);
        linearLayout.addView(checkBox);

        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Custom view")
                .hideConfirmButton();

        dialog.setCustomView(linearLayout);
        dialog.show();

    }



    void updateQtyDialog(final ItemInfo itemInfo, final int index, final TableLayout tableLayout){

        final Dialog updateDialog=new Dialog(Report.this);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setCancelable(false);
        if(controll.isYellow){
            updateDialog.setContentView(R.layout.update_dialog_new);
        }else{
            updateDialog.setContentView(R.layout.update_dialog_new);
        }

        updateDialog.setCanceledOnTouchOutside(false);
        TextView oldQty,itemCode,itemName;
        oldQty=(TextView)updateDialog.findViewById(R.id.oldQty);
        itemCode=(TextView)updateDialog.findViewById(R.id.itemCode);
        itemName=(TextView)updateDialog.findViewById(R.id.itemName);

        final EditText NewQty=(EditText)updateDialog.findViewById(R.id.newQty);
        final LinearLayout exit ,update;
        itemCode.setText(itemInfo.getItemCode());
        itemName .setText(itemInfo.getItemName());
        oldQty.setText(itemInfo.getItemQty()+"");
        exit=updateDialog.findViewById(R.id.exitAlert);
        update=updateDialog.findViewById(R.id.updates);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(!NewQty.getText().toString().equals("")) {
                    if(Double.parseDouble(NewQty.getText().toString())!=0) {
                        InventDB.updateExpTable("ITEMS_INFO", NewQty.getText().toString(), String.valueOf(itemInfo.getSerialNo()), itemInfo.getItemCode());
                        itemInfos.get(index).setItemQty(Float.parseFloat(NewQty.getText().toString()));
                        tableLayout.removeAllViews();
                        fillTableRows(tableLayout, itemInfos);
                        itemInfosAcu.clear();
                        itemInfosAcu = InventDB.getAllItemInfoSum(location);
                        try{

                            sumText.setText(""+itemInfosAcu.get(itemInfosAcu.size()-1).getSummation());

                        }catch (Exception e){

                        }
                        updateDialog.dismiss();
                    }else{
//                        Toast.makeText(Report.this, getResources().getString(R.string.qty_0), Toast.LENGTH_SHORT).show();
                        TostMesage(getResources().getString(R.string.qty_0));
                    }

                }else{
//                    Toast.makeText(Report.this,  getResources().getString(R.string.newq), Toast.LENGTH_SHORT).show();
                    TostMesage(getResources().getString(R.string.newq));

                }
            }
        });


//

        updateDialog.show();

    }

    void updateQtyDialogAss(final AssestItem itemInfos, final int index, final TableLayout tableLayout){

        final Dialog updateDialog=new Dialog(Report.this);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setCancelable(false);
        if(controll.isYellow){
            updateDialog.setContentView(R.layout.update_dialog_new);
        }else{
            updateDialog.setContentView(R.layout.update_dialog_new);
        }

        updateDialog.setCanceledOnTouchOutside(false);
        TextView oldQty,itemCode,itemName;
        oldQty=(TextView)updateDialog.findViewById(R.id.oldQty);
        itemCode=(TextView)updateDialog.findViewById(R.id.itemCode);
        itemName=(TextView)updateDialog.findViewById(R.id.itemName);

        final EditText NewQty=(EditText)updateDialog.findViewById(R.id.newQty);
        final LinearLayout exit ,update;
        itemCode.setText(itemInfos.getAssesstCode());
        itemName .setText(itemInfos.getAssesstName());
        oldQty.setText(itemInfos.getAssesstQty()+"");
        exit=updateDialog.findViewById(R.id.exitAlert);
        update=updateDialog.findViewById(R.id.updates);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(!NewQty.getText().toString().equals("")) {
                    if(Double.parseDouble(NewQty.getText().toString())!=0) {
                        InventDB.updateExpTableAss("ASSEST_TABLE_INFO", NewQty.getText().toString(), String.valueOf(itemInfos.getSerial()), itemInfos.getAssesstCode());
                        assestItemList.get(index).setAssesstQty(NewQty.getText().toString());
                        tableLayout.removeAllViews();
                        fillTableRowsAssest(tableLayout, assestItemList);
                        assestItemList.clear();
                        //itemInfosAcu = InventDB.getAllItemInfoSum();
                        updateDialog.dismiss();
                    }else{
//                        Toast.makeText(Report.this, getResources().getString(R.string.qty_0), Toast.LENGTH_SHORT).show();
                        TostMesage(getResources().getString(R.string.qty_0));
                    }

                }else{
//                    Toast.makeText(Report.this,  getResources().getString(R.string.newq), Toast.LENGTH_SHORT).show();
                    TostMesage(getResources().getString(R.string.newq));

                }
            }
        });


//

        updateDialog.show();

    }

    void showExportTransferItemDialog() {
        dialog = new Dialog(Report.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.export_transfer_item_data);
        if(controll.isYellow){
            dialog.setContentView(R.layout.export_transfer_item_data_yellow);
        }else{
            dialog.setContentView(R.layout.export_transfer_item_data);
        }

        dialog.setCanceledOnTouchOutside(false);

        Button exit;

        exit = (Button) dialog.findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExportTransfer.setClickable(true);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void showExportItemDataByExpiryDateDialog() {
        dialog = new Dialog(Report.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.export_item_data_by_expiry);
        dialog.setCanceledOnTouchOutside(false);

        Button exit;

        exit = (Button) dialog.findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void showExportAlternativeCodeDialog() {
        dialog = new Dialog(Report.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.export_alternatev_code);
        dialog.setCanceledOnTouchOutside(false);

        Button exit;

        exit = (Button) dialog.findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }




    public void notClickable() {
        ExportNewItemList.setClickable(false);
        exportText.setClickable(false);
        //CaptionLayout.setClickable(false);
        ExportTransfer.setClickable(false);
        exportExpDate.setClickable(false);
        ExportAlternative.setClickable(false);
        showAssast.setClickable(false);
    }

    void TostMesage(String message){

//        new SweetAlertDialog(CollectingData.this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Are you sure?")
//                .setContentText("Won't be able to recover this file!")
//                .setConfirmText("Yes,delete it!")
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog
//                                .setTitleText("Deleted!")
//                                .setContentText("Your imaginary file has been deleted!")
//                                .setConfirmText("OK")
//                                .setConfirmClickListener(null)
//                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                    }
//                })
//                .show();

        SweetAlertDialog sd2 = new SweetAlertDialog(this);
        sd2.setCancelable(true);
        sd2.setCanceledOnTouchOutside(true);
        sd2.setContentText(message);
        sd2.hideConfirmButton();
        sd2.show();


    }

    public void Clickable() {

        ExportNewItemList.setClickable(true);
        exportText.setClickable(true);
        ExportTransfer.setClickable(true);
        exportExpDate.setClickable(true);
        ExportAlternative.setClickable(true);
        showAssast.setClickable(true);
    }

    void initialization() {

        home =(TextView) findViewById(R.id.home);

        exportText = (LinearLayout) findViewById(R.id.expText);
        ExportTransfer = (LinearLayout) findViewById(R.id.expTransfer);
        exportExpDate = (LinearLayout) findViewById(R.id.expExp);
        ExportAlternative = (LinearLayout) findViewById(R.id.expAlternative);
        ExportNewItemList= (LinearLayout) findViewById(R.id.showExportNewItemList);
        showAssast=  findViewById(R.id.showAssast);

        ExportTransfer.setEnabled(false);
        exportText.setOnClickListener(showDialogOnClick);

        ExportNewItemList.setOnClickListener(showDialogOnClick);
//        ExportTransfer.setOnClickListener(showDialogOnClick);
        exportExpDate.setOnClickListener(showDialogOnClick);
        ExportAlternative.setOnClickListener(showDialogOnClick);
        showAssast.setOnClickListener(showDialogOnClick);

         Clickable();

    }



}
