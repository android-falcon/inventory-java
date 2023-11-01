package com.example.user54.InventoryApp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.user54.InventoryApp.InventoryDatabase;
import com.example.user54.InventoryApp.Model.AssestItem;
import com.example.user54.InventoryApp.Model.ItemInfo;
import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemInfoExp;
import com.example.user54.InventoryApp.Model.ItemQR;
import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.Model.MainSetting;
import com.example.user54.InventoryApp.Model.OfferTable;
import com.example.user54.InventoryApp.Model.Stk;
import com.example.user54.InventoryApp.Model.TransferItemsInfo;
import com.example.user54.InventoryApp.Model.TransferVhfSerial;
import com.example.user54.InventoryApp.R;
import com.example.user54.InventoryApp.ROOM.AppDatabase;
import com.example.user54.InventoryApp.ROOM.UserDaoCard;
import com.example.user54.InventoryApp.ROOM.UserDaoOffer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CollectingData extends AppCompatActivity {
    Button item, report, collecting, exitAll2, setting;
    LinearLayout collectData, collectByORG, transferData, collectingByExpiry,
            collectByReceipt, UpdateQty, itemAssest,TransferPhar,addAssest;
    TextView barCodTextTemp;
    String isOnlinePrice="0";
    TextView home;
    public static TextView textViewUpdate, textItemNameUpdate;
    Dialog dialog;
    boolean open = false, collDOpen = false, collExOpen = false, openAssesst=false,collReceipt = false, collTransfer = false, collUpdate = false, collAssetsOpen = false, collAddAssetsOpen = false,collTranseOpen = false;
    String today;
    List<AssestItem>itemAssetsCa;
    InventoryDatabase InventDB;
    List<AssestItem> assestItemsList;
    EditText editText;
    AppDatabase db;
    List<String> managList, areaList, depList, secList;
    ArrayList<ItemInfo> itemUpdate = new ArrayList<>();
    ArrayList<ItemInfoExp> itemUpdateExp = new ArrayList<>();
    ArrayList<ItemInfoExp> itemUpdateExpRec = new ArrayList<>();
    ArrayList<TransferItemsInfo> itemUpdateTransfer = new ArrayList<>();
    boolean updateOpen = false, openSearch = false, openSave = false, openBarCode = false, openColUp = false;
    int textId = 0;
    List<ItemCard> itemCardsList = new ArrayList<ItemCard>();

    String StkNo = "";
    String QrUse = "";
    int MinUse = 0;
    int settingCOName=0;
    Animation animFadein;
    TableRow row;
    TableLayout noteTable;
    List<ItemCard> itemCodeCard;
    DecimalFormat numberFormat = new DecimalFormat("0.000");

    String LocationEdite = "";
    int serial=0;
    int companyNo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//                    setContentView(R.layout.menue3_new);

        if (controll.isYellow) {
            setContentView(R.layout.menue3_yellow);
        } else {
            setContentView(R.layout.menue3_yellow);
        }

        controll co=new controll();
        int data= Integer.parseInt(co.readFromFile(CollectingData.this));
        InventDB = new InventoryDatabase(CollectingData.this,data);
        db=AppDatabase.getInstanceDatabase(CollectingData.this);

        initialization();
//        transferData.setEnabled(false);
        animFadein = AnimationUtils.loadAnimation(CollectingData.this, R.anim.fade_in);
        collectData.startAnimation(animFadein);
        transferData.startAnimation(animFadein);
        UpdateQty.startAnimation(animFadein);
        itemAssest.startAnimation(animFadein);
        addAssest.startAnimation(animFadein);
        TransferPhar.startAnimation(animFadein);

        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final List<MainSetting> mainSetting = InventDB.getAllMainSetting();
        if (mainSetting.size() != 0) {
            companyNo=mainSetting.get(0).getCoName();
        }

    }


    View.OnClickListener showDialogOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.collData:
//                    collectData.setClickable(false);
//                    collDOpen = true;

//                    showCollectDataDialog();

                    boolean is=LocationDialog();
//                    if(is) {
//
//                    }else {
//                        Toast.makeText(CollectingData.this, "noLocation", Toast.LENGTH_SHORT).show();
//                    }

                    break;
//                case R.id.collByorg:
//
//                    showCollectByOrgDialog();
//                    break;
//                case R.id.collByExp:
//                    collExOpen = true;
//                    showCollectByExpDialog();
////                    Intent collectIntent = new Intent(CollectingData.this,CollectingByExp.class);
////                    collectIntent.putExtra("EXTRA_SESSION_ID", "0");
////                    startActivity(collectIntent);
//                    break;
//                case R.id.collByRecep:
//                    collReceipt = true;
////                    Intent RIntent = new Intent(CollectingData.this,CollectingByExp.class);
////                    RIntent.putExtra("EXTRA_SESSION_ID", "1");
////                    startActivity(RIntent);
//                    showCollectByReceiptDialog();
//                    break;
                case R.id.transfer:
                    transferData.setClickable(false);
                    collTransfer = true;
                    showTransferDialog();
                    break;

                case R.id.UpdateQty:


                    password();
                    break;
                case R.id.itemAssest:
                    itemAssest.setClickable(false);
                    collAssetsOpen = true;
                    showAssetsDataDialog();
                    break;
                case R.id.TransferPhar:
                    TransferPhar.setClickable(false);
                    collTranseOpen = true;
                    showTransferDataDialog();
                    break;
                case R.id.addAssest:
                    addAssest.setClickable(false);
                    collAddAssetsOpen = true;
                    showAddAssetsDataDialog();
                    break;

            }
        }
    };


    void password (){

        final EditText editText = new EditText(CollectingData.this);
        final TextView textView = new TextView(CollectingData.this);
        editText.setHint("Enter Password");
        editText.setTextColor(Color.BLACK);
        textView.setTextColor(Color.RED);
        if (SweetAlertDialog.DARK_STYLE) {
            editText.setTextColor(Color.BLACK);
        }
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(editText);
        linearLayout.addView(textView);

        SweetAlertDialog dialog = new SweetAlertDialog(CollectingData.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Password")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        String password=editText.getText().toString();
                        textView.setText("");
                        if(!password.equals("")){

                            if(password.equals("jrd12345uQ")){

                                textView.setText("");

                                UpdateQty.setClickable(false);
                                openColUp = true;
                                showItemUpdateQtyDialog();
                                sweetAlertDialog.dismissWithAnimation();

                            }else{

                                textView.setText("Password Is not correct");
//                                        if ( isExternalStorageWritable() ) {
//                                           // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "InventoryDBFolder"  ;
////                                            File appDirectory = new File( Environment.getExternalStorageDirectory() + "/MyPersonalAppFolder" );
//                                            File appDirectory = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator  + "MyPersonalAppFolder" );
//
//                                            File logDirectory = new File( appDirectory + "/logs" );
//                                            File logFile = new File( logDirectory, "logcat_" + ".txt" );
//
//                                            // create app folder
//                                            if ( !appDirectory.exists() ) {
//                                                appDirectory.mkdir();
//                                            }
//
//                                            // create log folder
//                                            if ( !logDirectory.exists() ) {
//                                                logDirectory.mkdir();
//                                            }
//
//                                            // clear the previous logcat and then write the new one to the file
//                                            try {
//                                                Process process = Runtime.getRuntime().exec("logcat -E");
//                                                process = Runtime.getRuntime().exec("logcat -f " + logFile.getAbsolutePath());
//                                                Log.e("jjj","ggg");
//                                                saveLogInSDCard(MainActivity2.this);
//                                            } catch ( IOException e ) {
//                                                e.printStackTrace();
//                                            }
//
//                                        } else if ( isExternalStorageReadable() ) {
//                                            // only readable
//                                        } else {
//                                            // not accessible
//                                        }




//                                        try {
//                                            Process process = Runtime.getRuntime().exec("logcat -e");
//                                            BufferedReader bufferedReader = new BufferedReader(
//                                                    new InputStreamReader(process.getInputStream()));
//
//                                            StringBuilder log=new StringBuilder();
//                                            String line = "";
//                                            while ((line = bufferedReader.readLine()) != null) {
//                                                log.append(line);
//                                            }
//                                           // TextView tv = (TextView)findViewById(R.id.textView1);
//                                            Log.e("","rrr"+log.length());
//
//                                            textView.setText(log.toString());
//
//                                        } catch (IOException e) {
//                                            Log.e("error1234",""+e.getMessage().toString());
//
//                                            // Handle Exception
//                                            textView.setText("error"+e.getMessage().toString());
//
//                                            Log.e("error1234",""+e.getMessage().toString());
//                                        }
                                // textView.setText(getResources().getString(R.string.NotCorrectPassword));
                            }

                        }

                    }
                });
//                        .hideConfirmButton();

        dialog.setCustomView(linearLayout);
        dialog.show();


    }
    void showItemUpdateQtyDialog() {
        final Dialog dialogBarCode = new Dialog(CollectingData.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogBarCode.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBarCode.setCancelable(false);
        if (controll.isYellow) {
            dialogBarCode.setContentView(R.layout.update_qty_yellow);
        } else {
            dialogBarCode.setContentView(R.layout.update_qty_yellow);
        }

        final Boolean[] isFound = {false};
        final boolean[] isEnter = {true};

        dialogBarCode.setCanceledOnTouchOutside(false);

        final int[] count1 = {1};

        final TextView oldQty, NewQty, itemName, StorName, StorNo;

        final Button exit, prepare, update;
        final ImageView search;
        final EditText itemCode;
        final RadioButton notMinus, minus;

        StorName = dialogBarCode.findViewById(R.id.Stor);
        StorNo = dialogBarCode.findViewById(R.id.Storno);

        notMinus = dialogBarCode.findViewById(R.id.notMinus);
        minus = dialogBarCode.findViewById(R.id.minus);


        oldQty = dialogBarCode.findViewById(R.id.oldQty);
        NewQty = dialogBarCode.findViewById(R.id.newQty);

        search = dialogBarCode.findViewById(R.id.search);

        exit = dialogBarCode.findViewById(R.id.exit);
        update = dialogBarCode.findViewById(R.id.update);
        itemName = dialogBarCode.findViewById(R.id.itemName);
        itemCode = (EditText) dialogBarCode.findViewById(R.id.itemCode);
        int companyNo=0;
        final List<MainSetting> mainSetting = InventDB.getAllMainSetting();
        if (mainSetting.size() != 0) {
            StorNo.setText("" + mainSetting.get(0).getStorNo());
            StorName.setText(InventDB.getStkName(mainSetting.get(0).getStorNo()));
            companyNo=mainSetting.get(0).getCoName();
        }

        itemName.setMovementMethod(ScrollingMovementMethod.getInstance());

        itemCode.setSelectAllOnFocus(true);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                itemCode.requestFocus();

            }
        });

//        prepare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!itemCode.getText().toString().equals("")&& openPrice){
//                    textView=salesPrice;
//                    importJson sendCloud = new importJson(Item.this,itemCode.getText().toString());
//                    sendCloud.startSending("ItemPrice");
//
//                }
//            }
//        });

        textViewUpdate = oldQty;
        textItemNameUpdate = itemName;
        textViewUpdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (textViewUpdate.getText().toString().equals("-1")) {
                    showAlertDialog(getResources().getString(R.string.falidTogetdata));
                    oldQty.setText("");
                    itemName.setText("");

                } else if (textViewUpdate.getText().toString().equals("*")) {
                    showAlertDialog(getResources().getString(R.string.thisitemnotfound));
                    oldQty.setText("");
                    NewQty.setText("");
                    itemCode.setText("");
                    itemName.setText("");
                } else if (textViewUpdate.getText().toString().equals("up")) {
                    showAlertDialog(getResources().getString(R.string.thisitemnotfound));
                    oldQty.setText("");
                    NewQty.setText("");
                    itemCode.setText("");
                    itemName.setText("");
                } else if (textViewUpdate.getText().toString().equals("-up")) {
                    showAlertDialog(getResources().getString(R.string.falidTogetdata));
                    oldQty.setText("");
                    NewQty.setText("");
                    itemCode.setText("");
                    itemName.setText("");
                } else if (textViewUpdate.getText().toString().equals("upSu")) {
//                    showAlertDialog("up Successful");
                    oldQty.setText("");
                    NewQty.setText("");
                    itemCode.setText("");
                    itemName.setText("");
                    itemCode.setSelectAllOnFocus(true);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            itemCode.requestFocus();

                        }
                    });

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

 //&& !oldQty.getText().toString().equals("") && !itemName.getText().toString().equals("")
                if (!itemCode.getText().toString().equals("") && !NewQty.getText().toString().equals("")) {
                    String Kind = "500";
                    if (minus.isChecked()) {
                        Kind = "500";
                    } else if (notMinus.isChecked()) {
                        Kind = "502";
                    }

                    JSONArray jsonObject = getJSONObjectUpdate(itemCode.getText().toString(), StorNo.getText().toString(), NewQty.getText().toString(), Kind);

                    JSONObject objTrans1 = new JSONObject();
                    try {
                        objTrans1.put("JRD", jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ExportJeson sendCloud = new ExportJeson(CollectingData.this, objTrans1);
                    sendCloud.startSending("ExportUpdate");

                }

            }
        });

        Button barcode;
        barcode = dialogBarCode.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCode = true;
                openColUp = false;
                readBarCode(itemCode, 7);
            }
        });


        itemCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
//                    if (isEnter[0]) {
                    if (!itemCode.getText().toString().equals("") && openColUp) {
//                        textView=salesPrice;
//                        textItemName= itemName;

                        if (mainSetting.size() != 0) {

                            importJson sendCloud = new importJson(CollectingData.this, itemCode.getText().toString(), 0,"","");
                            sendCloud.startSending("gETiTEM");
//                          isEnter[0]=false;
                            itemCode.setSelectAllOnFocus(true);
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    itemCode.requestFocus();

                                }
                            });
                        } else {

                            new SweetAlertDialog(CollectingData.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.mainSetting) + "!")
                                    .setContentText(getResources().getString(R.string.nomainSetting))
                                    .setConfirmText(getResources().getString(R.string.cancel))
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null).show();


                        }


                    }

//                    }

                }


                return false;
            }
        });

//        itemCode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////                if(!itemCode.getText().toString().equals("")&& openPrice){
////                    textView=salesPrice;
////                    importJson sendCloud = new importJson(Item.this,itemCode.getText().toString());
////                    sendCloud.startSending("ItemPrice");
////
////
////                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateQty.setClickable(true);
                dialogBarCode.dismiss();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                openColUp = false;
                itemName.setText("");
                oldQty.setText("");
                NewQty.setText("");
                SearchDialog(itemCode, 6);
            }
        });


        dialogBarCode.show();
    }

    void showExportToTextDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.export_item_data_to_text);
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

    void showExportTransferItemDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.export_transfer_item_data);
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

    void showExportItemDataByExpiryDateDialog() {
        dialog = new Dialog(CollectingData.this);
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
        dialog = new Dialog(CollectingData.this);
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

    void showPrintShelfTagDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.print_shelf_tag);
        dialog.setCanceledOnTouchOutside(true);

        final int[] count1 = {1};
        final TextView countText;
        Button exit;
        ImageButton upCount, downCount;

        exit = (Button) dialog.findViewById(R.id.exit);
        upCount = (ImageButton) dialog.findViewById(R.id.up);
        downCount = (ImageButton) dialog.findViewById(R.id.down);
        countText = (TextView) dialog.findViewById(R.id.countT);

        upCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1[0]++;
                countText.setText("" + count1[0]);
            }
        });
        downCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(countText.getText().toString()) != 1) {
                    count1[0]--;
                    countText.setText("" + count1[0]);
                } else
                    Toast.makeText(CollectingData.this, "count down under value 1 ", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void showNewItemDialog(final int tr) {
        final Dialog dialogNew = new Dialog(CollectingData.this, R.style.Theme_Dialog);
        dialogNew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNew.setCancelable(false);

        if (controll.isYellow) {

//            DisplayMetrics metrics = getResources().getDisplayMetrics();
//            int screenWidth = (int) (metrics.widthPixels * 0.80);
//            int screenH = (int) (metrics.heightPixels * 0.80);
//            dialogNew.setContentView(R.layout.new_item_yellow);
//
//            getWindow().setLayout(screenWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);

            dialogNew.setContentView(R.layout.new_item_yellow);

        } else {
            dialogNew.setContentView(R.layout.new_item);
        }
//

        dialogNew.setCanceledOnTouchOutside(false);


        final LinearLayout exit, save, clear, serach, deleteItem, deleteAllItem;
        final EditText itemCodeNew, itemNameNew, itemPrice;

        itemCodeNew = (EditText) dialogNew.findViewById(R.id.itemCNew);
        itemNameNew = (EditText) dialogNew.findViewById(R.id.itemNameNew);
        itemPrice = (EditText) dialogNew.findViewById(R.id.itemPrice);
//        ArrayList<ItemCard> itemCardList = new ArrayList<>();


        exit = dialogNew.findViewById(R.id.exit);
        save = dialogNew.findViewById(R.id.saveNew);
        clear = dialogNew.findViewById(R.id.clearNew);
        serach = dialogNew.findViewById(R.id.searchNew);
        deleteItem = dialogNew.findViewById(R.id.deleteItem);
        deleteAllItem = dialogNew.findViewById(R.id.deleteAllItem);


        itemCodeNew.requestFocus();

        Button barcode;
        barcode = dialogNew.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCode = true;
                openSave = false;
                readBarCode(itemCodeNew, 3);
            }
        });

        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemCodeNew.getText().toString().equals("") && !itemNameNew.getText().toString().equals("")) {
                    alertMessageDialog(CollectingData.this.getResources().getString(R.string.delete), CollectingData.this.getResources().getString(R.string.delete_item_) +
                            "\n \n" + CollectingData.this.getResources().getString(R.string.item_name) + itemNameNew.getText().toString() + " \n Item Code : " + itemCodeNew.getText().toString() + " \n\n from  Table ?", 2, itemNameNew.getText().toString(), itemCodeNew.getText().toString());

                    itemCodeNew.setText("");
                    itemNameNew.setText("");
                    itemPrice.setText("");
                    itemCodeNew.requestFocus();
                    save.setClickable(true);

                } else
//                    Toast.makeText(CollectingData.this,CollectingData.this.getResources().getString(R.string.insertData), Toast.LENGTH_SHORT).show();

                    TostMesage(CollectingData.this.getResources().getString(R.string.insertData));

            }
        });

        deleteAllItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertMessageDialog(CollectingData.this.getResources().getString(R.string.delete), CollectingData.this.getResources().getString(R.string.allItemDelete), 1, "", "");
            }
        });


        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                openSave = false;
                itemCodeNew.setText("");
                itemNameNew.setText("");
                itemPrice.setText("");
                itemCodeNew.requestFocus();
                save.setClickable(true);

                SearchDialog(itemCodeNew, 3);
            }
        });

//        itemCodeNew.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                boolean isItemFound = false;
//
//                String itemCode = itemCodeNew.getText().toString();
//                String itemName = itemNameNew.getText().toString();
//
//
//                if (!itemCodeNew.getText().toString().equals("") && openSave) {
//                    itemCardsList = InventDB.getAllItemCard();
//
//                    for (int i = 0; i < itemCardsList.size(); i++) {
//                        if (itemCardsList.get(i).getItemCode().equals(itemCode)) {
//                            isItemFound = true;
//                            itemNameNew.setText(itemCardsList.get(i).getItemName());
//                            itemPrice.setText(itemCardsList.get(i).getSalePrc());
//                            break;
//
//                        }
//                    }
//
//                    if (!isItemFound) {
//
////                        new Handler().post(new Runnable() {
////                            @Override
////                            public void run() {
////                                itemNameNew.requestFocus();
////                            }
////                        });
//
//                        save.setClickable(true);
//                    } else {
//                        save.setClickable(false);
//                        showAlertDialog("This item Found please add another item ");
//                    }
//
//
//                }
//
//
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        itemCodeNew.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {

                    boolean isItemFound = false;

                    String itemCode = itemCodeNew.getText().toString();
                    String itemName = itemNameNew.getText().toString();
                    String itemSwitch;

                    if (!itemCodeNew.getText().toString().equals("") && openSave) {
                        itemCardsList = getItemCard();

                        if (itemCode.length() > 17) {
                            itemCode.substring(0, 16);
                        } else {
                            itemCode = itemCodeNew.getText().toString();
                        }


//                        List <ItemQR> QRList=findQRCode(itemCode);


                        List<String> itemUnite = findUnite(itemCode);
                        int uQty = 1;


                        if (itemUnite.size() != 0) {

                            itemCode = itemUnite.get(0);
                            uQty = Integer.parseInt(itemUnite.get(2));


                        } else {
                            itemSwitch = findSwitch(itemCode);
                            if (!itemSwitch.equals("")) {
                                itemCode = itemSwitch;
                            }
                            uQty = 1;

                        }


                        for (int i = 0; i < itemCardsList.size(); i++) {
                            if (itemCardsList.get(i).getItemCode().equals(itemCode)) {
                                isItemFound = true;
                                itemNameNew.setText(itemCardsList.get(i).getItemName());
                                itemPrice.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCardsList.get(i).getFDPRC()))));
                                break;

                            }
                        }

                        if (!isItemFound) {

//                        new Handler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                itemNameNew.requestFocus();
//                            }
//                        });

                            save.setClickable(true);
                        } else {
                            save.setClickable(false);
                            showAlertDialog("This item Found please add another item ");
                        }


                    }


                }
                return false;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemNameNew.getText().toString().equals("")) {
                    ItemCard itemCard = new ItemCard();
                    itemCard.setItemCode(itemCodeNew.getText().toString());
                    itemCard.setItemName(itemNameNew.getText().toString());
                    itemCard.setFDPRC(convertToEnglish(numberFormat.format(Double.parseDouble(itemPrice.getText().toString()))));
                    itemCard.setIsNew("1");
                    itemCard.setIsExport("0");
                    itemCard.setAVLQty("0");
                    itemCard.setItemG("0");
                    itemCard.setItemGs("0");
                    itemCard.setCostPrc("0");
                    itemCard.setSalePrc("0");
                    itemCard.setAVLQty("0");
                    itemCard.setBranchId("0");
                    itemCard.setBranchName("0");
                    itemCard.setDepartmentId("0");
                    itemCard.setDepartmentName("0");
                    itemCard.setItemK("0");
                    itemCard.setItemL("0");
                    itemCard.setItemDiv("0");
                    itemCard.setItemGs("0");
                    itemCard.setOrgPrice("0");
                    itemCard.setInDate("0");
                    itemCard.setIsExport("0");
                    itemCard.setIsNew("1");
                    itemCard.setItemM("0");
                    InventDB.addItemcardTable(itemCard);
                    prograseSave();
                    itemCodeNew.setText("");
                    itemNameNew.setText("");
                    itemPrice.setText("");
                    itemCodeNew.requestFocus();

                } else {
//                    Toast.makeText(CollectingData.this,CollectingData.this.getResources().getString(R.string.insertData), Toast.LENGTH_SHORT).show();
                    TostMesage(CollectingData.this.getResources().getString(R.string.insertData));

                }
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCodeNew.setText("");
                itemNameNew.setText("");
                itemPrice.setText("");
                itemCodeNew.requestFocus();
                save.setClickable(true);
            }
        });


//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                boolean isItemFound = false;
////                ArrayList<ItemCard> itemCardList = new ArrayList<>();
////                String itemCode = itemCodeNew.getText().toString();
////                String itemName = itemNameNew.getText().toString();
////
////                if (!itemCodeNew.getText().toString().equals("") && !itemNameNew.getText().toString().equals("") && openSave) {
////
////                    itemCardList = InventDB.getAllItemCard();
////
////                    for (int i = 0; i < itemCardList.size(); i++) {
////                        if (itemCardList.get(i).getItemCode().equals(itemCode)) {
////
////                            isItemFound = true;
////                            break;
////
////                        }
////                    }
////
////                    if (!isItemFound) {
//
//                        ItemCard itemCard = new ItemCard();
//
//                        itemCard.setItemCode(itemCode);
//                        itemCard.setItemName(itemName);
//
//                        InventDB.addItemcardTable(itemCard);
//                        progressDialog();
////                    itemCodes.requestFocus();
////                    itemCodes.setText("");
//
//
////                    } else {
////                        showAlertDialog("This item Found please add another item ");
////                    }
////
////
////                } else {
////                    Toast.makeText(CollectingData.this, "Please insert all data", Toast.LENGTH_SHORT).show();
////                }
//                itemCodeNew.requestFocus();
//                itemCodeNew.setText("");
//                itemNameNew.setText("");
//            }
//        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSave = false;
                switch (tr) {
                    case 0:
                        break;
                    case 1:
                        collDOpen = true;
                        editText.requestFocus();
                        editText.setText("");
                        itemCardsList =  getItemCard();
                        break;
                    case 2:
                        collExOpen = true;
                        editText.requestFocus();
                        editText.setText("");
                        itemCardsList =  getItemCard();
                        break;
                    case 3:
                        collReceipt = true;
                        editText.requestFocus();
                        editText.setText("");
                        itemCardsList = getItemCard();
                        break;

                    case 4:
                        collTransfer = true;
                        editText.requestFocus();
                        editText.setText("");
                        itemCardsList = getItemCard();
                        break;
                }

                dialogNew.dismiss();
            }
        });

        dialogNew.show();
    }


    void showImportFromTextDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.import_iteams_from_text_file);
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

    void showPrintBarcodeDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.print_item_barcode);
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


    void showChangePasswordDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.change_password);
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


    void showPrintSettingDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_collecting_data);
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


    void showSendToServerDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_collecting_data);
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


    void showReceiveFromServerDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_collecting_data);
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


    void showCollectDataDialog() {
        dialog = new Dialog(CollectingData.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        if (controll.isYellow) {
            dialog.setContentView(R.layout.activity_collecting_data_yellow);
//            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        } else {
            dialog.setContentView(R.layout.activity_collecting_data_yellow);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        }

//

        dialog.setCanceledOnTouchOutside(false);


        final int[] count = {0};
        final ArrayList<ItemInfo> itemPreviousScan = new ArrayList<>();
        ArrayList<ItemInfo> itemLastScan = new ArrayList<>();
        final String[] oldQty = {"0"};

        itemLastScan = InventDB.getAllItemInfo();
        final List<MainSetting> mainSettings = InventDB.getAllMainSetting();


        final boolean[] noEnterData = {true};

        final boolean[] upDateClick = {true};
        final boolean[] ExpClick = {false};
        final Boolean[] isFound = {false};
        final boolean[] isEnter = {true};

        final CheckBox upDateCheck = (CheckBox) dialog.findViewById(R.id.updateQ);
        final RadioButton min, NotMin;
        final CheckBox ExpDateCheckBox = (CheckBox) dialog.findViewById(R.id.ExpDateCheckBox);
        final TextView oldQtys,itemName, itemLocation, itemNameBefore, itemCodeBefore, itemLQtyBefore, itemAQtyBefore, itemDate, _qty, locations;
        final LinearLayout exit, save, clear, update, newButton, search;
        final Button barcode;
        final EditText itemCodeText, itemQty, lotNo, qrCode, salePrice;
        final int[] uQty = {1};
        TableRow rawQr,rawQrLot;

        oldQtys=dialog.findViewById(R.id.oldQty);
        rawQr= dialog.findViewById(R.id.rawQr);
        rawQrLot=dialog.findViewById(R.id.rawQrLot);
        _qty = dialog.findViewById(R.id._qty);
        lotNo = dialog.findViewById(R.id.lotNo);
        qrCode = dialog.findViewById(R.id.qrCode);

        min = dialog.findViewById(R.id.min);
        NotMin = dialog.findViewById(R.id.notMin);
        ExpDateCheckBox.setVisibility(View.GONE);
        itemCodeText = (EditText) dialog.findViewById(R.id.itemCode);
        itemQty = (EditText) dialog.findViewById(R.id.item_qty);
        itemLocation = dialog.findViewById(R.id.location);
        locations = dialog.findViewById(R.id.locations);
        salePrice = (EditText) dialog.findViewById(R.id.salePrice);
        try {
            isOnlinePrice = mainSettings.get(0).getONlINEshlf();
        }catch (Exception e){

            Toast.makeText(this, "some Error in DB", Toast.LENGTH_SHORT).show();
        }
       if(isOnlinePrice.equals("1"))
        salePrice.setEnabled(false);
        itemName = (TextView) dialog.findViewById(R.id.item_name);
        itemNameBefore = (TextView) dialog.findViewById(R.id.itemNameB);
        itemCodeBefore = (TextView) dialog.findViewById(R.id.itemCodeB);
        itemLQtyBefore = (TextView) dialog.findViewById(R.id.itemLQtyB);
        itemAQtyBefore = (TextView) dialog.findViewById(R.id.itemAQtyB);
        itemDate = (TextView) dialog.findViewById(R.id.DateItem);

        if (!itemLastScan.isEmpty()) {
            int qtyItem = itemLastScan.size();
            itemNameBefore.setText(itemLastScan.get(qtyItem - 1).getItemName());
            itemLQtyBefore.setText("" + itemLastScan.get(qtyItem - 1).getItemQty());
            itemCodeBefore.setText(itemLastScan.get(qtyItem - 1).getItemCode());
            itemAQtyBefore.setText(InventDB.getTotal(itemLastScan.get(qtyItem - 1).getItemCode(), "ITEMS_INFO"));

        }

        exit = dialog.findViewById(R.id.exit);
        save = dialog.findViewById(R.id.save);
        clear = dialog.findViewById(R.id.cler);
        update = dialog.findViewById(R.id.updateBu);
        newButton = dialog.findViewById(R.id.newBu);
        search = dialog.findViewById(R.id.search);
        barcode = dialog.findViewById(R.id.barcode);
        itemDate.setText(convertToEnglish(today));
        String StkName = "";
        min.setVisibility(View.VISIBLE);
        if (mainSettings.size() != 0) {
            StkName = InventDB.getStkName(mainSettings.get(0).getStorNo());
            StkNo = mainSettings.get(0).getStorNo();
            QrUse = mainSettings.get(0).getIsQr();
            settingCOName=mainSettings.get(0).getCoName();
            MinUse=mainSettings.get(0).getIsMinus();


        }
        locations.setEnabled(false);
        locations.setText(LocationEdite);


        itemCodeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
//                    editText.setSelection(editText.getText().length());
                    if(itemCodeText.getText().toString().equals("")){
                        itemCodeText.setFocusable(true);
                      //  itemCodeText.requestFocus();
                        new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    itemCodeText.requestFocus();
                                }
                            });
                    }
                    }

                }
        });

//
//        itemQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_NULL) {
////                    if (isEnter[0]) {
////                        if (!isFound[0]) {
////                            showAlertDialog("This item not found please add this item before ");
////                            new Handler().post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    itemCodeText.requestFocus();
////                                }
////                            });
////
////                            itemQty.setEnabled(true);
////                            itemCodeText.setEnabled(true);
////
////                            save.setClickable(true);
////                            itemCodeText.setText("");
////                            itemQty.setText("1");
////                            itemName.setText("");
////                            salePrice.setText("");
////                            itemLocation.setText("");
////                            isEnter[0] = false;
////                        }
////
////                    }
//
//                    if(!itemQty.getText().toString().equals("")){
//
//                        String Qty=""+(Double.parseDouble(itemQty.getText().toString())* Double.parseDouble(_qty.getText().toString()));
//                        itemQty.setText(Qty);
//                    }else{
//                        itemQty.setText("1");
//                    }
//
//
//
//                }
//
//
//                return false;
//            }
//        });



        if(QrUse.equals("1")){
            min.setVisibility(View.GONE);
            rawQr.setVisibility(View.VISIBLE);
            rawQrLot.setVisibility(View.VISIBLE);
        }else{

            if(MinUse==1){
                min.setVisibility(View.GONE);

            }else {
                min.setVisibility(View.VISIBLE);
            }
            rawQrLot.setVisibility(View.GONE);
            rawQr.setVisibility(View.GONE);
           // min.setVisibility(View.VISIBLE);
        }

        salePrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // TODO: the editText has just been left

                    if(QrUse.equals("1")) {
                        PasswordDialog(salePrice, locations);
                    }
                }

            }
        });


        itemDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog(itemDate);
            }
        });

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarCode = true;
                collDOpen = false;
                readBarCode(itemCodeText, 0);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                collDOpen = false;
                SearchDialog(itemCodeText, 0);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        itemQty.requestFocus();
                    }
                });

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collDOpen = false;
                itemUpdate = InventDB.getAllItemInfo();
                updateOpen = true;
                upDateDialog(itemCodeBefore, 0, itemCodeText, itemAQtyBefore);

            }
        });


        itemCodeText.requestFocus();
        itemQty.setEnabled(true);
        itemCodeText.setEnabled(true);
//010628108600282710118830172107062100000000002768
        save.setClickable(true);
        itemCodeText.setText("");
        itemQty.setText("1");
        itemName.setText("");
        itemLocation.setText(StkName);
        itemLocation.setEnabled(false);
        salePrice.setText("");
        if(isOnlinePrice.equals("1"))
            salePrice.setEnabled(false);
        final int[] serialInfo = {InventDB.getAllItemInfo().size()};

        clear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                itemCodeText.requestFocus();
                itemQty.setEnabled(true);
                itemCodeText.setEnabled(true);

                save.setClickable(true);
                itemCodeText.setText("");
                itemQty.setText("1");
                itemName.setText("");
//                itemLocation.setText("");
                _qty.setText("1");
                salePrice.setText("");
                if(isOnlinePrice.equals("1"))
                salePrice.setEnabled(false);
                qrCode.setText("0");
                lotNo.setText("0");
//                if (!itemPreviousScan.isEmpty()) {
//                    itemNameBefore.setText(itemPreviousScan.get(count[0] - 1).getItemName());
//                    itemCodeBefore.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
//                    itemLQtyBefore.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
////                    float AllQty = Float.parseFloat(itemPreviousScan.get(count[0] - 1).getItemLocation()) + itemPreviousScan.get(count[0] - 1).getItemQty();
////                    itemAQtyBefore.setText("" + AllQty);
//
//                }
            }
        });

//        ArrayList<ItemCard> itemCardsList = new ArrayList<ItemCard>();

        itemCardsList = getItemCard();
//        itemCodeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus){
//                    // TODO: the editText has just been left
//                    if(!isFound[0]){
//                        showAlertDialog("This item not found please add this item before ");
//                        new Handler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                itemCodeText.requestFocus();
//                            }
//                        });
//
//                        itemQty.setEnabled(true);
//                        itemCodeText.setEnabled(true);
//
//                        save.setClickable(true);
//                        itemCodeText.setText("");
//                        itemQty.setText("1");
//                        itemName.setText("");
//                        salePrice.setText("");
//                        itemLocation.setText("");
//                    }
//                }
//
//            }
//        });

/*from
//        itemQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_NULL) {
//
//                    if(ExpClick[0]){
//                        TimeDialog(itemDate);
//                    }
//                }
//            return false;
//            }});
*///to

        itemQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_NULL) {


                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                            String isUpSave="0";
                            try {
                                isUpSave = mainSettings.get(0).getIsSaveAfterUp();
                            }catch (Exception e){

                                Toast.makeText(CollectingData.this, "some Error in DB3", Toast.LENGTH_SHORT).show();
                            }

                            if(isUpSave.equals("1")){
                               //save

                                String itemSwitch = "";
                                String itemCode = itemCodeText.getText().toString();
//                itemSwitch=findSwitch(itemCode);
//                if(!itemSwitch.equals("")){
//                    itemCode=itemSwitch;
//                }

                                if(settingCOName==1){
                                    itemCode=itemCodeText.getText().toString();
                                    try {
                                        itemCode = itemCode.substring(0, 12);
                                    }catch (Exception e){
                                        itemCode=itemCodeText.getText().toString();
                                    }
                                    Log.e("itemCode1_",""+itemCode);
                                }else{
                                    itemCode=itemCodeText.getText().toString();
                                }
                                Log.e("itemCode2_",""+itemCode);


                                List<String> itemUnite = findUnite(itemCode);
                                int uQty = 1;


                                if (itemUnite.size() != 0) {

                                    itemCode = itemUnite.get(0);
                                    uQty = Integer.parseInt(itemUnite.get(2));

                                } else {
                                    itemSwitch = findSwitch(itemCode);
                                    if (!itemSwitch.equals("")) {
                                        itemCode = itemSwitch;
                                    }
                                    uQty = 1;

                                }


                                oldQty[0] = InventDB.getTotal(itemCode, "ITEMS_INFO");

                                if (!itemQty.getText().toString().equals("") && !itemCode.equals("") && !salePrice.getText().toString().equals("") && isFound[0]) {

                                    if(itemQty.getText().toString().length()<=5){

                                        ItemInfo item = new ItemInfo();

                                        String Lo = "";
                                        item.setItemCode(itemCode);
                                        item.setItemName(itemName.getText().toString());

                                        String Qty = "";
                                        if (!itemQty.getText().toString().equals("")) {

                                            Qty = "" + (Double.parseDouble(itemQty.getText().toString()) * Double.parseDouble(_qty.getText().toString()));
                                        } else {
                                            Qty = "" + (1 * Double.parseDouble(_qty.getText().toString()));

                                        }

                                        itemQty.setText(Qty);

                                        if (min.isChecked()) {
                                            item.setItemQty(-1 * Float.parseFloat(itemQty.getText().toString()));

                                        } else {
                                            item.setItemQty(Float.parseFloat(itemQty.getText().toString()));
                                        }

                                        List<MainSetting> mainSettings = InventDB.getAllMainSetting();
                                        if (mainSettings.size() != 0) {
                                            Lo = mainSettings.get(0).getStorNo();
                                        }
                                        item.setItemLocation(Lo);
                                        item.setRowIndex(Float.parseFloat("0.0"));
                                        item.setSerialNo(serialInfo[0]++);
                                        item.setExpDate(itemDate.getText().toString());
//                    if (ExpClick[0]) {
//                        item.setExpDate(itemDate.getText().toString());
//                    } else {
//                        item.setExpDate(today);
//                    }
                                        item.setSalePrice(Float.parseFloat(convertToEnglish(numberFormat.format(Double.parseDouble(salePrice.getText().toString())))));
                                        if(isOnlinePrice.equals("1"))
                                            salePrice.setEnabled(false);
                                        item.setTrnDate(convertToEnglish(today));
                                        item.setIsExport("0");
                                        item.setLocation("" + locations.getText().toString());
                                        item.setLotNo(lotNo.getText().toString());
                                        item.setQRCode(qrCode.getText().toString());

                                        InventDB.addItemcard(item);
                                        item.setIsDelete("0");
                                        InventDB.addItemInfoBackup(item);

                                        item.setItemLocation(oldQty[0]);
                                        itemPreviousScan.add(item);
                                        count[0]++;

                                        progressDialog();
                                        ////////CLEAR ///

                                        //   itemCodeText.requestFocus();
                                        new Handler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                itemCodeText.requestFocus();
                                            }
                                        });
                                        itemQty.setEnabled(true);
                                        itemCodeText.setEnabled(true);

                                        save.setClickable(true);
                                        itemCodeText.setText("");
                                        itemQty.setText("1");
                                        itemName.setText("");
                                        salePrice.setText("");
                                        if(isOnlinePrice.equals("1"))
                                            salePrice.setEnabled(false);
                                        _qty.setText("1");
                                        qrCode.setText("0");
                                        lotNo.setText("0");
//                    itemLocation.setText("");

                                        if (!itemPreviousScan.isEmpty()) {
                                            itemNameBefore.setText(itemPreviousScan.get(count[0] - 1).getItemName());
                                            itemCodeBefore.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
                                            itemLQtyBefore.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
                                            float AllQty = Float.parseFloat(itemPreviousScan.get(count[0] - 1).getItemLocation()) + itemPreviousScan.get(count[0] - 1).getItemQty();
                                            itemAQtyBefore.setText("" + AllQty);


                                        }
                                    }else{

                                        itemQty.setError(CollectingData.this.getResources().getString(R.string.maxNo));
                                        TostMesage(CollectingData.this.getResources().getString(R.string.maxNo));

                                    }
                                } else {
//                    Toast.makeText(CollectingData.this, "please insert all data in field", Toast.LENGTH_SHORT).show();
                                    TostMesage(CollectingData.this.getResources().getString(R.string.insertData));

                                }


                               //end save

                            }else {
                                salePrice.requestFocus();
                            }

                        }
                    });

                }
                return false;
            }
        });

//

        itemCodeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
                    String codeFromText="";
                    String itemCode = itemCodeText.getText().toString();
                    String itemSwitch = "";
                    String QRCode = "", lot = "", Price = "";
                    if (!itemCode.equals("") && collDOpen && noEnterData[0]) {
                        isEnter[0] = true;

                        Log.e("itemCardsList.size()", "here1-->" + itemCardsList.size()+"\tisOnlinePrice="+isOnlinePrice);
                        List<ItemQR> QRList = new ArrayList<>();
                        boolean isSaleFromUnit = false;
                        boolean offer=false;
                        if (QrUse.equals("1")) {
                            if (itemCode.length() > 17) {
                                QRCode = itemCode;
                                itemCode = itemCode.substring(0, 16);
                                Log.e("String ", "" + itemCode);
                                QRList = findQRCode(itemCode, StkNo, false);
                            } else {
                                QRCode = itemCode;
                                itemCode = itemCodeText.getText().toString();
                                QRList = findQRCode(itemCode, StkNo, true);
                            }

                        }

                        if (QRList.size() != 0) {
                            itemCode = QRList.get(0).getItemCode();
                            lot = QRList.get(0).getLotNo();
                            Price = QRList.get(0).getSalesPrice();
                            lotNo.setText(lot);
                            qrCode.setText(QRCode);
                            itemCodeText.setText(itemCode);
                            _qty.setText("1");
                            salePrice.setText("" + convertToEnglish(numberFormat.format(Double.parseDouble(QRList.get(0).getSalesPrice()))));
                            if(isOnlinePrice.equals("1"))
                                salePrice.setEnabled(false);
                            isSaleFromUnit = true;
                        } else {

                            if (settingCOName == 1) {//rawan work
                                itemCode = itemCodeText.getText().toString();
                                try {
                                    itemCode = itemCode.substring(0, 12);
                                } catch (Exception e) {
                                    itemCode = itemCodeText.getText().toString();
                                }
                                Log.e("itemCode1_", "" + itemCode);
                            } else {
                                itemCode = itemCodeText.getText().toString();
                            }
                            Log.e("itemCode2_", "" + itemCode);


                             codeFromText=itemCode;

                            List<String> itemUnite = findUnite(itemCode);

                            if (itemUnite.size() != 0) {

                                itemCode = itemUnite.get(0);
                                uQty[0] = Integer.parseInt(itemUnite.get(2));
                                Log.e("itemUnite", "" + itemCode + "  \n " + itemUnite.get(0));

                                _qty.setText("" + convertToEnglish("" + Integer.parseInt(itemUnite.get(2))));
                                salePrice.setText("" + convertToEnglish(numberFormat.format(Double.parseDouble(itemUnite.get(1)))));
                                if (isOnlinePrice.equals("1"))
                                    salePrice.setEnabled(false);
                                isSaleFromUnit = true;
                            } else {
                                itemSwitch = findSwitch(itemCode);
                                if (!itemSwitch.equals("")) {
                                    itemCode = itemSwitch;
                                }
                                uQty[0] = 1;
                                isSaleFromUnit = false;

                                Log.e("itemSwitch", "" + itemCode + "  \n ");

                            }


                        }

                        try {
                            String OldQty = InventDB.getTotal(itemCode, "ITEMS_INFO");
                            oldQtys.setText(""+OldQty);
                        }catch (Exception e){
                            oldQtys.setText("---");
                        }

                        List<OfferTable> offerTables=findOffer(itemCode);
                        List<OfferTable> offerTablesO=findOffer(codeFromText);

                        if(offerTablesO.size()!=0){

                            offer=true;
//                            itemCode = offerTables.get(0).getITEMOCODE();
                            salePrice.setText("" + convertToEnglish(numberFormat.format(Double.parseDouble(offerTablesO.get(0).getF_D()))));


                        }else
                        if (offerTables.size()!=0) {
                            offer=true;
//                            itemCode = offerTables.get(0).getITEMOCODE();
                            salePrice.setText("" + convertToEnglish(numberFormat.format(Double.parseDouble(offerTables.get(0).getF_D()))));

                        }

                        for (int i = 0; i < itemCardsList.size(); i++) {
                            String itemCodeList = itemCardsList.get(i).getItemCode();
                            if (itemCode.equals(itemCodeList)) {
                                isFound[0] = true;

                                itemName.setText(itemCardsList.get(i).getItemName());
                                itemQty.setText("" + (Double.parseDouble(itemQty.getText().toString())));
                                if (!isSaleFromUnit && !offer) {//
                                    salePrice.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCardsList.get(i).getFDPRC()))));
                                    if(isOnlinePrice.equals("1"))
                                        salePrice.setEnabled(false);
                                    _qty.setText("1");
                                }

                                if (QrUse.equals("1")) {
                                    String Sales = findQRCodeSale(itemCode, StkNo);
                                    if (!TextUtils.isEmpty(Sales)) {
                                        salePrice.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Sales))));
                                    } else {
                                        salePrice.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCardsList.get(i).getFDPRC()))));
                                    }
                                }

                                //
                                if(isOnlinePrice.equals("1"))
                                    salePrice.setEnabled(false);
                                break;
                            } else {
                                isFound[0] = false;
                            }
                        }


                        if (isFound[0]) {


                            if (upDateClick[0] || ExpClick[0]) {
                                save.setClickable(true);
                                itemCodeText.setEnabled(false);
                                itemQty.setEnabled(true);


//                                itemQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                                    @Override
//                                    public void onFocusChange(View v, boolean hasFocus) {
//                                        if (!hasFocus){
//                                            // TODO: the editText has just been left
//                                            if(ExpClick[0]){
//                                                TimeDialog(itemDate);
//                                            }
//                                        }
//
//                                    }
//                                });


                                if (event != null) {
                                    if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.ACTION_DOWN) {

                                        itemQty.setSelectAllOnFocus(true);
                                        _qty.requestFocus();
//                                        new Handler().post(new Runnable() {
//                                            @Override
//                                            public void run() {
//
//                                                itemQty.requestFocus();
//
//                                            }
//                                        });

                                    } else {

                                        itemQty.setSelectAllOnFocus(true);
                                        itemQty.requestFocus();
//                                        new Handler().post(new Runnable() {
//                                            @Override
//                                            public void run() {
//
//                                                itemQty.requestFocus();
//
//                                            }
//                                        });
//
                                    }
                                } else {
                                    itemQty.setSelectAllOnFocus(true);
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {

                                            String isUpSave="0";
                                            try {
                                                isUpSave = mainSettings.get(0).getIsSaveAfterUp();
                                            }catch (Exception e){

                                                Toast.makeText(CollectingData.this, "some Error in DB2", Toast.LENGTH_SHORT).show();
                                            }

                                            if(isUpSave.equals("1")){
                                                itemQty.setText("");
                                            }
                                            itemQty.requestFocus();

                                        }
                                    });

                                }


//                            Toast.makeText(CollectingData.this, "save no auto", Toast.LENGTH_SHORT).show();
                            } else {
//                            Toast.makeText(CollectingData.this, "save auto", Toast.LENGTH_SHORT).show();
                                if(itemQty.getText().toString().length()<=5){
                                oldQty[0] = InventDB.getTotal(itemCode, "ITEMS_INFO");
//
                                save.setClickable(false);
                                itemCodeText.setEnabled(false);
                                itemQty.setEnabled(false);

                                ItemInfo item = new ItemInfo();

                                String Lo = "";

                                List<MainSetting> mainSettings = InventDB.getAllMainSetting();
                                if (mainSettings.size() != 0) {
                                    Lo = mainSettings.get(0).getStorNo();
                                }

                                item.setItemCode(itemCode);
                                item.setItemName(itemName.getText().toString());
                                String Qty = "";
                                if (!itemQty.getText().toString().equals("")) {

                                    Qty = "" + (Double.parseDouble(itemQty.getText().toString()) * Double.parseDouble(_qty.getText().toString()));
                                } else {
                                    Qty = "" + (1 * Double.parseDouble(_qty.getText().toString()));

                                }

                                itemQty.setText(Qty);

                                if (min.isChecked()) {
                                    item.setItemQty(-1 * Float.parseFloat(itemQty.getText().toString()));

                                } else {
                                    item.setItemQty(Float.parseFloat(itemQty.getText().toString()));
                                }
                                item.setItemLocation(Lo);
                                item.setRowIndex(Float.parseFloat("0.0"));
                                item.setSerialNo(serialInfo[0]++);
                                item.setExpDate(itemDate.getText().toString());
                                item.setIsExport("0");
                                item.setLocation("" + locations.getText().toString());
//                                if (ExpClick[0]) {
//                                    item.setExpDate(itemDate.getText().toString());
//                                } else {
//                                    item.setExpDate(today);
//                                }
                                if (!salePrice.getText().toString().equals("")) {
                                    item.setSalePrice(Float.parseFloat(convertToEnglish(numberFormat.format(Double.parseDouble(salePrice.getText().toString())))));
                                } else {
                                    item.setSalePrice(0);
                                }
                                item.setTrnDate(convertToEnglish(today));
                                if (QrUse.equals(1)) {
                                    item.setLotNo(lotNo.getText().toString());
                                    item.setQRCode(qrCode.getText().toString());
                                } else {
                                    item.setLotNo("0");
                                    item.setQRCode("0");
                                }


                                InventDB.addItemcard(item);
                                item.setIsDelete("0");
                                InventDB.addItemInfoBackup(item);


                                item.setItemLocation(oldQty[0]);
                                Log.e("ol12", "===>" + oldQty[0]);
                                itemPreviousScan.add(item);
//                            Toast.makeText(CollectingData.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
                                count[0]++;

                                progressDialog();

////////////////////////////////CLEAR ////////


                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        itemCodeText.requestFocus();
                                    }
                                });


                                itemQty.setEnabled(true);
                                itemCodeText.setEnabled(true);

                                    oldQtys.setText("0");
                                save.setClickable(true);
                                itemCodeText.setText("");
                                itemQty.setText("1");
                                salePrice.setText("");
                                itemName.setText("");
                                _qty.setText("1");
                                qrCode.setText("0");
                                lotNo.setText("0");
//                                itemLocation.setText("");

                                if (!itemPreviousScan.isEmpty()) {
                                    itemNameBefore.setText(itemPreviousScan.get(count[0] - 1).getItemName());
                                    itemCodeBefore.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
                                    itemLQtyBefore.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());

                                    float AllQty = Float.parseFloat(itemPreviousScan.get(count[0] - 1).getItemLocation()) + itemPreviousScan.get(count[0] - 1).getItemQty();
                                    itemAQtyBefore.setText("" + AllQty);


                                }

                            }else {
                                    itemQty.setError(CollectingData.this.getResources().getString(R.string.maxNo));
                                    TostMesage(CollectingData.this.getResources().getString(R.string.maxNo));
                            }
                            }



                        } else {


                            showAlertDialog(CollectingData.this.getResources().getString(R.string.itemNotFoundAlert));
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    itemCodeText.requestFocus();
                                }
                            });

                            itemQty.setEnabled(true);
                            itemCodeText.setEnabled(true);

                            save.setClickable(true);
                            itemCodeText.setText("");
                            itemQty.setText("1");
                            itemName.setText("");
                            salePrice.setText("");
//                            itemLocation.setText("");
                            isEnter[0] = false;
                            _qty.setText("1");
                            qrCode.setText("0");
                            lotNo.setText("0");
                            itemCodeText.requestFocus();

                            if(isOnlinePrice.equals("1"))
                                salePrice.setEnabled(false);
//                        showAlertDialog("This item not found please add this item before ");
//                        Toast.makeText(CollectingData.this, "This item not found please add this item before ", Toast.LENGTH_SHORT).show();
                        }

                    }


                }


                return false;
            }
        });


//        itemCodeText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count1) {
//
//                String itemCode = itemCodeText.getText().toString();
//                if (!itemCode.equals("") && collDOpen && noEnterData[0]) {
//                    isEnter[0]=true;
//                    Log.e("itemCardsList.size()", "-->" + itemCardsList.size());
//                    for (int i = 0; i < itemCardsList.size(); i++) {
//                        String itemCodeList = itemCardsList.get(i).getItemCode();
//                        if (itemCode.equals(itemCodeList)) {
//                            isFound[0] = true;
//
//                            itemName.setText(itemCardsList.get(i).getItemName());
//                            salePrice.setText(itemCardsList.get(i).getSalePrc());
//
//
//                            break;
//                        } else {
//                            isFound[0] = false;
//                        }
//                    }
//
//                    if (isFound[0]) {
//
//
//                        if (upDateClick[0]|| ExpClick[0]) {
//                            save.setClickable(true);
//                            itemCodeText.setEnabled(false);
//                            itemQty.setEnabled(true);
//
//                            itemQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                                @Override
//                                public void onFocusChange(View v, boolean hasFocus) {
//                                    if (!hasFocus){
//                                    // TODO: the editText has just been left
//                                        if(ExpClick[0]){
//                                            TimeDialog(itemDate);
//                                        }
//                                }
//
//                                }
//                            });
//
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    itemQty.requestFocus();
//                                }
//                            });
//
////                            Toast.makeText(CollectingData.this, "save no auto", Toast.LENGTH_SHORT).show();
//                        } else {
////                            Toast.makeText(CollectingData.this, "save auto", Toast.LENGTH_SHORT).show();
//                            oldQty[0] = InventDB.getTotal(itemCode, "ITEMS_INFO");
//                            itemQty.setText("1");
//                            save.setClickable(false);
//                            itemCodeText.setEnabled(false);
//                            itemQty.setEnabled(false);
//                            ItemInfo item = new ItemInfo();
//
//                            item.setItemCode(itemCodeText.getText().toString());
//                            item.setItemName(itemName.getText().toString());
//                            item.setItemQty(Float.parseFloat(itemQty.getText().toString()));
//                            item.setItemLocation("12");
//                            item.setRowIndex(Float.parseFloat("0.0"));
//                            item.setSerialNo(serialInfo[0]++);
//                            if(ExpClick[0]) {
//                                item.setExpDate(itemDate.getText().toString());
//                            }else{
//                                item.setExpDate("noExp");
//                            }
//                            if(!salePrice.getText().toString().equals("")){
//                                item.setSalePrice(Float.parseFloat(salePrice.getText().toString()));
//                            }else{
//                                item.setSalePrice(0);
//                            }
//
//
//                            InventDB.addItemcard(item);
//
//                            item.setItemLocation(oldQty[0]);
//                            Log.e("ol12", "===>" + oldQty[0]);
//                            itemPreviousScan.add(item);
////                            Toast.makeText(CollectingData.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
//                            count[0]++;
//
//                            progressDialog();
//
//////////////////////////////////CLEAR ////////
//
//                            itemCodeText.requestFocus();
//                            itemQty.setEnabled(true);
//                            itemCodeText.setEnabled(true);
//
//                            save.setClickable(true);
//                            itemCodeText.setText("");
//                            itemQty.setText("1");
//                            salePrice.setText("");
//                            itemName.setText("");
//                            itemLocation.setText("");
//
//                            if (!itemPreviousScan.isEmpty()) {
//                                itemNameBefore.setText(itemPreviousScan.get(count[0] - 1).getItemName());
//                                itemCodeBefore.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
//                                itemLQtyBefore.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
//
//                                float AllQty = Float.parseFloat(itemPreviousScan.get(count[0] - 1).getItemLocation()) + itemPreviousScan.get(count[0] - 1).getItemQty();
//                                itemAQtyBefore.setText("" + AllQty);
//
//
//                            }
//
//
//                        }
//
//
//                    } else {
//
////                        showAlertDialog("This item not found please add this item before ");
////                        Toast.makeText(CollectingData.this, "This item not found please add this item before ", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                itemQty.setText("1");
//            }
//
//        });


        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collDOpen = false;
                editText = itemCodeText;
                openSave = true;

                showNewItemDialog(1);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemSwitch = "";
                String itemCode = itemCodeText.getText().toString();
//                itemSwitch=findSwitch(itemCode);
//                if(!itemSwitch.equals("")){
//                    itemCode=itemSwitch;
//                }

                if(settingCOName==1){
                    itemCode=itemCodeText.getText().toString();
                    try {
                        itemCode = itemCode.substring(0, 12);
                    }catch (Exception e){
                        itemCode=itemCodeText.getText().toString();
                    }
                    Log.e("itemCode1_",""+itemCode);
                }else{
                    itemCode=itemCodeText.getText().toString();
                }
                Log.e("itemCode2_",""+itemCode);


                List<String> itemUnite = findUnite(itemCode);
                int uQty = 1;


                if (itemUnite.size() != 0) {

                    itemCode = itemUnite.get(0);
                    uQty = Integer.parseInt(itemUnite.get(2));

                } else {
                    itemSwitch = findSwitch(itemCode);
                    if (!itemSwitch.equals("")) {
                        itemCode = itemSwitch;
                    }
                    uQty = 1;

                }


                oldQty[0] = InventDB.getTotal(itemCode, "ITEMS_INFO");

                if (!itemQty.getText().toString().equals("") && !itemCode.equals("") && !salePrice.getText().toString().equals("") && isFound[0]) {

                    if(itemQty.getText().toString().length()<=5){

                    ItemInfo item = new ItemInfo();

                    String Lo = "";
                    item.setItemCode(itemCode);
                    item.setItemName(itemName.getText().toString());

                    String Qty = "";
                    if (!itemQty.getText().toString().equals("")) {

                        Qty = "" + (Double.parseDouble(itemQty.getText().toString()) * Double.parseDouble(_qty.getText().toString()));
                    } else {
                        Qty = "" + (1 * Double.parseDouble(_qty.getText().toString()));

                    }

                    itemQty.setText(Qty);

                    if (min.isChecked()) {
                        item.setItemQty(-1 * Float.parseFloat(itemQty.getText().toString()));

                    } else {
                        item.setItemQty(Float.parseFloat(itemQty.getText().toString()));
                    }

                    List<MainSetting> mainSettings = InventDB.getAllMainSetting();
                    if (mainSettings.size() != 0) {
                        Lo = mainSettings.get(0).getStorNo();
                    }
                    item.setItemLocation(Lo);
                    item.setRowIndex(Float.parseFloat("0.0"));
                    item.setSerialNo(serialInfo[0]++);
                    item.setExpDate(itemDate.getText().toString());
//                    if (ExpClick[0]) {
//                        item.setExpDate(itemDate.getText().toString());
//                    } else {
//                        item.setExpDate(today);
//                    }
                    item.setSalePrice(Float.parseFloat(convertToEnglish(numberFormat.format(Double.parseDouble(salePrice.getText().toString())))));
                        if(isOnlinePrice.equals("1"))
                            salePrice.setEnabled(false);
                    item.setTrnDate(convertToEnglish(today));
                    item.setIsExport("0");
                    item.setLocation("" + locations.getText().toString());
                    item.setLotNo(lotNo.getText().toString());
                    item.setQRCode(qrCode.getText().toString());

                    InventDB.addItemcard(item);
                    item.setIsDelete("0");
                    InventDB.addItemInfoBackup(item);

                    item.setItemLocation(oldQty[0]);
                    itemPreviousScan.add(item);
                    count[0]++;

                    progressDialog();
                    ////////CLEAR ///

                 //   itemCodeText.requestFocus();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                itemCodeText.requestFocus();
                            }
                        });
                    itemQty.setEnabled(true);
                    itemCodeText.setEnabled(true);

                    save.setClickable(true);
                    itemCodeText.setText("");
                    itemQty.setText("1");
                    itemName.setText("");
                    salePrice.setText("");
                    if(isOnlinePrice.equals("1"))
                    salePrice.setEnabled(false);
                    _qty.setText("1");
                    qrCode.setText("0");
                    lotNo.setText("0");
//                    itemLocation.setText("");

                    if (!itemPreviousScan.isEmpty()) {
                        itemNameBefore.setText(itemPreviousScan.get(count[0] - 1).getItemName());
                        itemCodeBefore.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
                        itemLQtyBefore.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
                        float AllQty = Float.parseFloat(itemPreviousScan.get(count[0] - 1).getItemLocation()) + itemPreviousScan.get(count[0] - 1).getItemQty();
                        itemAQtyBefore.setText("" + AllQty);


                    }
                }else{

                        itemQty.setError(CollectingData.this.getResources().getString(R.string.maxNo));
                        TostMesage(CollectingData.this.getResources().getString(R.string.maxNo));

                    }
                } else {
//                    Toast.makeText(CollectingData.this, "please insert all data in field", Toast.LENGTH_SHORT).show();
                    TostMesage(CollectingData.this.getResources().getString(R.string.insertData));

                }

            }
        });

        upDateCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upDateCheck.isChecked()) {
                    upDateClick[0] = true;
                } else {
                    upDateClick[0] = false;
                }
            }
        });

        ExpDateCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ExpDateCheckBox.isChecked()) {
                    ExpClick[0] = true;
                } else {
                    ExpClick[0] = false;
                }
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectData.setClickable(true);
                collDOpen = false;
                noEnterData[0] = false;
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    void showAssetsDataDialog() {
        dialog = new Dialog(CollectingData.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        if (controll.isYellow) {
            dialog.setContentView(R.layout.activity_assets_data_yellow);
//            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        } else {
            dialog.setContentView(R.layout.activity_assets_data_yellow);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        }

//

        dialog.setCanceledOnTouchOutside(false);

        final boolean[] noEnterData = {true};

        final boolean[] isEnter = {true};

        final TextView itemName,search_1;
        final LinearLayout exit, save, clear;
        final Button barcode;
        final EditText itemCodeText, itemQty;
        final Spinner mangment, depart, section, area;

        mangment = dialog.findViewById(R.id.manSpin);
        depart = dialog.findViewById(R.id.depSpin);
        section = dialog.findViewById(R.id.secSpin);
        area = dialog.findViewById(R.id.AreaSpin);
        search_1=dialog.findViewById(R.id.search_1);

        itemCodeText = (EditText) dialog.findViewById(R.id.itemCode);
        itemQty = (EditText) dialog.findViewById(R.id.item_qty);
        itemName = (TextView) dialog.findViewById(R.id.item_name);


        exit = dialog.findViewById(R.id.exit);
        save = dialog.findViewById(R.id.save);
        clear = dialog.findViewById(R.id.cler);

        barcode = dialog.findViewById(R.id.barcode);

        managList = new ArrayList<>();
        areaList = new ArrayList<>();
        depList = new ArrayList<>();
        secList = new ArrayList<>();


        managList = InventDB.getAllAssesstMang();
        depList = InventDB.getAllAssesstDepart();
        secList = InventDB.getAllAssesstSec();
        areaList = InventDB.getAllAssesstArea();
        assestItemsList = new ArrayList<>();

        ArrayAdapter MangAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, managList);
        mangment.setAdapter(MangAdapter);

        ArrayAdapter DepAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, depList);
        depart.setAdapter(DepAdapter);

        ArrayAdapter SecAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, secList);
        section.setAdapter(SecAdapter);

        ArrayAdapter AreaAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, areaList);
        area.setAdapter(AreaAdapter);

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarCode = true;
                collAssetsOpen = false;
                readBarCode(itemCodeText, 8);

            }
        });

        search_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch = true;
                collExOpen = false;
                SearchDialogForAssets(itemCodeText, 5);

            }
        });

        itemCodeText.requestFocus();
        itemQty.setEnabled(true);
        itemCodeText.setEnabled(true);

        save.setClickable(true);
        itemCodeText.setText("");
        itemQty.setText("1");
        itemName.setText("");


        clear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                itemCodeText.requestFocus();
                itemQty.setEnabled(true);
                itemCodeText.setEnabled(true);

                save.setClickable(true);
                itemCodeText.setText("");
                itemQty.setText("1");
                itemName.setText("");
            }
        });


        itemCodeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
//                    if (isEnter[0]) {
//                        if (!isFound[0]) {
//                            showAlertDialog("This item not found please add this item before ");
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    itemCodeText.requestFocus();
//                                }
//                            });
//
//                            itemQty.setEnabled(true);
//                            itemCodeText.setEnabled(true);
//
//                            save.setClickable(true);
//                            itemCodeText.setText("");
//                            itemQty.setText("1");
//                            itemName.setText("");
//                            salePrice.setText("");
//                            itemLocation.setText("");
//                            isEnter[0] = false;
//                        }
//
//                    }


                    String itemCode = itemCodeText.getText().toString();

                    if (!itemCode.equals("") && collAssetsOpen && noEnterData[0]) {
                        isEnter[0] = true;

                        assestItemsList = findAssest(itemCode);
                        if (assestItemsList.size() != 0) {

                            itemName.setText("" + assestItemsList.get(0).getAssesstName());
                            itemQty.setSelectAllOnFocus(true);
                            itemQty.requestFocus();

                        } else {
//                            Toast.makeText(CollectingData.this, "Not Found", Toast.LENGTH_SHORT).show();
                            showAlertDialog(getResources().getString(R.string.thisitemnotfound));
                            itemCodeText.setSelectAllOnFocus(true);
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    itemCodeText.requestFocus();
                                }
                            });

                        }

                    }
                }
                return false;
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assestItemsList.size() != 0) {
                    if (!itemCodeText.getText().toString().equals("") && !itemName.getText().toString().equals("") && !itemQty.getText().toString().equals("")) {
                        AssestItem assestItem = new AssestItem();
                        String manageString = "", depString = "", secString = "", areaString = "";
                        if (managList.size() != 0) {
                            manageString = managList.get(mangment.getSelectedItemPosition());

                        } else {
                            manageString = "";
                        }

                        if (depList.size() != 0) {
                            depString = depList.get(depart.getSelectedItemPosition());

                        } else {
                            depString = "";
                        }


                        if (secList.size() != 0) {
                            secString = secList.get(section.getSelectedItemPosition());

                        } else {
                            secString = "";
                        }


                        if (areaList.size() != 0) {
                            areaString = areaList.get(area.getSelectedItemPosition());

                        } else {
                            areaString = "";
                        }


                        serial=InventDB.getMaxSerialAss();
                        assestItem.setAssesstMangment(manageString);
                        assestItem.setAssesstDEPARTMENT(depString);
                        assestItem.setAssesstSECTION(secString);
                        assestItem.setAssesstAREANAME(areaString);

                        assestItem.setAssesstBarcode(itemCodeText.getText().toString());
                        assestItem.setAssesstName(itemName.getText().toString());
                        assestItem.setAssesstType(assestItemsList.get(0).getAssesstType());
                        assestItem.setAssesstNo(assestItemsList.get(0).getAssesstNo());

                        assestItem.setAssesstQty(itemQty.getText().toString());
                        assestItem.setAssesstDate(convertToEnglish(today));
                        assestItem.setAssesstCode(assestItemsList.get(0).getAssesstCode());
                        assestItem.setIsExport("0");
                        assestItem.setSerial(""+(serial+1));

                        InventDB.addAssetsItemInfo(assestItem);
                        progressDialog();

                        itemCodeText.requestFocus();

                        itemQty.setEnabled(true);
                        itemCodeText.setEnabled(true);

                        save.setClickable(true);
                        itemCodeText.setText("");
                        itemQty.setText("1");
                        itemName.setText("");
                        isEnter[0] = false;

                        assestItemsList.clear();
                    } else {
                        TostMesage(CollectingData.this.getResources().getString(R.string.insertData));
                        itemCodeText.setSelectAllOnFocus(true);
                        itemCodeText.requestFocus();
                    }
                } else {
                    TostMesage(CollectingData.this.getResources().getString(R.string.insertData));
                    itemCodeText.setSelectAllOnFocus(true);
                    itemCodeText.requestFocus();
                }

            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemAssest.setClickable(true);
                collAssetsOpen = false;
                noEnterData[0] = false;
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void showAddAssetsDataDialog() {
        dialog = new Dialog(CollectingData.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        if (controll.isYellow) {
            dialog.setContentView(R.layout.activity_add_assets_data_yellow);
//            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        } else {
            dialog.setContentView(R.layout.activity_assets_data_yellow);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        }

//

        dialog.setCanceledOnTouchOutside(false);

        final boolean[] noEnterData = {true};

        final boolean[] isEnter = {true};

        final EditText itemName;
        final LinearLayout exit, save, clear;
        final Button barcode;
        final EditText itemCodeText;//, itemQty;
        final Spinner mangment, depart, section, area;

        mangment = dialog.findViewById(R.id.manSpin);
        depart = dialog.findViewById(R.id.depSpin);
        section = dialog.findViewById(R.id.secSpin);
        area = dialog.findViewById(R.id.AreaSpin);

        itemCodeText = (EditText) dialog.findViewById(R.id.itemCode);
      //  itemQty = (EditText) dialog.findViewById(R.id.item_qty);
        itemName =  dialog.findViewById(R.id.item_name);


        exit = dialog.findViewById(R.id.exit);
        save = dialog.findViewById(R.id.save);
        clear = dialog.findViewById(R.id.cler);

        barcode = dialog.findViewById(R.id.barcode);

        managList = new ArrayList<>();
        areaList = new ArrayList<>();
        depList = new ArrayList<>();
        secList = new ArrayList<>();


        managList = InventDB.getAllAssesstMang();
        depList = InventDB.getAllAssesstDepart();
        secList = InventDB.getAllAssesstSec();
        areaList = InventDB.getAllAssesstArea();
        assestItemsList = new ArrayList<>();

//        managList.add("test");
//        depList .add("test");
//        secList .add("test");
//        areaList.add("test");


        ArrayAdapter MangAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, managList);
        mangment.setAdapter(MangAdapter);

        ArrayAdapter DepAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, depList);
        depart.setAdapter(DepAdapter);

        ArrayAdapter SecAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, secList);
        section.setAdapter(SecAdapter);

        ArrayAdapter AreaAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, areaList);
        area.setAdapter(AreaAdapter);

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarCode = true;
                collAddAssetsOpen = false;
                readBarCode(itemCodeText, 9);

            }
        });

        itemCodeText.requestFocus();
       // itemQty.setEnabled(true);
        itemCodeText.setEnabled(true);

        save.setClickable(true);
        itemCodeText.setText("");
        //itemQty.setText("1");
        itemName.setText("");


        clear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                itemCodeText.requestFocus();
          //      itemQty.setEnabled(true);
                itemCodeText.setEnabled(true);

                save.setClickable(true);
                itemCodeText.setText("");
            //    itemQty.setText("1");
                itemName.setText("");
            }
        });


        itemCodeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
//                    if (isEnter[0]) {
//                        if (!isFound[0]) {
//                            showAlertDialog("This item not found please add this item before ");
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    itemCodeText.requestFocus();
//                                }
//                            });
//
//                            itemQty.setEnabled(true);
//                            itemCodeText.setEnabled(true);
//
//                            save.setClickable(true);
//                            itemCodeText.setText("");
//                            itemQty.setText("1");
//                            itemName.setText("");
//                            salePrice.setText("");
//                            itemLocation.setText("");
//                            isEnter[0] = false;
//                        }
//
//                    }


                    String itemCode = itemCodeText.getText().toString();

                    if (!itemCode.equals("") && collAddAssetsOpen && noEnterData[0]) {
                        isEnter[0] = true;

                        assestItemsList = findAssest(itemCode);
                        if (assestItemsList.size() != 0) {


                            showAlertDialog(getResources().getString(R.string.itemFoundAlert));
                         //   itemName.setText("" + assestItemsList.get(0).getAssesstName());
                            //itemQty.setSelectAllOnFocus(true);
                            //itemQty.requestFocus();

                        } else {




//                            Toast.makeText(CollectingData.this, "Not Found", Toast.LENGTH_SHORT).show();
                           // showAlertDialog(getResources().getString(R.string.thisitemnotfound));
//                            itemCodeText.setSelectAllOnFocus(true);
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    itemCodeText.requestFocus();
//                                }
//                            });



                        }

                    }
                }
                return false;
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assestItemsList.size() == 0) {
                    if (!itemCodeText.getText().toString().equals("") && !itemName.getText().toString().equals("")) {
                        AssestItem assestItem = new AssestItem();
                        String manageString = "", depString = "", secString = "", areaString = "";
                        if (managList.size() != 0) {
                            manageString = managList.get(mangment.getSelectedItemPosition());

                        } else {
                            manageString = "";
                        }

                        if (depList.size() != 0) {
                            depString = depList.get(depart.getSelectedItemPosition());

                        } else {
                            depString = "";
                        }


                        if (secList.size() != 0) {
                            secString = secList.get(section.getSelectedItemPosition());

                        } else {
                            secString = "";
                        }


                        if (areaList.size() != 0) {
                            areaString = areaList.get(area.getSelectedItemPosition());

                        } else {
                            areaString = "";
                        }


                       // serial=InventDB.getMaxSerialAss();
                        assestItem.setAssesstMangment(manageString);
                        assestItem.setAssesstDEPARTMENT(depString);
                        assestItem.setAssesstSECTION(secString);
                        assestItem.setAssesstAREANAME(areaString);

                        assestItem.setAssesstBarcode(itemCodeText.getText().toString());
                        assestItem.setAssesstName(itemName.getText().toString());
                        assestItem.setAssesstType("");
                        assestItem.setAssesstNo(itemCodeText.getText().toString());

                        //assestItem.setAssesstQty(itemQty.getText().toString());
                        assestItem.setAssesstDate(convertToEnglish(today));
                        assestItem.setAssesstCode(itemCodeText.getText().toString());
                        assestItem.setIsExport("0");
                       // assestItem.setSerial(""+(serial+1));

                        InventDB.addAssetsItem(assestItem);
                        progressDialog();

                        itemCodeText.requestFocus();

                        //itemQty.setEnabled(true);
                        itemCodeText.setEnabled(true);

                        save.setClickable(true);
                        itemCodeText.setText("");
                       // itemQty.setText("1");
                        itemName.setText("");
                        isEnter[0] = false;

                        assestItemsList.clear();
                    } else {
                        TostMesage(CollectingData.this.getResources().getString(R.string.insertData));
                        itemCodeText.setSelectAllOnFocus(true);
                        itemCodeText.requestFocus();
                    }
                } else {
                    TostMesage(CollectingData.this.getResources().getString(R.string.insertData));
                    itemCodeText.setSelectAllOnFocus(true);
                    itemCodeText.requestFocus();
                }

            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssest.setClickable(true);
                collAddAssetsOpen = false;
                noEnterData[0] = false;
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void showTransferDataDialog() {
        dialog = new Dialog(CollectingData.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        if (controll.isYellow) {
            dialog.setContentView(R.layout.activity_assets_data_yellow);
        } else {
            dialog.setContentView(R.layout.activity_assets_data_yellow);
        }

//

        dialog.setCanceledOnTouchOutside(false);

        final boolean[] noEnterData = {true};

        final boolean[] isEnter = {true};

        final TextView itemName;
        final LinearLayout exit, save, clear;
        final Button barcode;
        final EditText itemCodeText, itemQty;
        final Spinner mangment, depart, section, area;

        mangment = dialog.findViewById(R.id.manSpin);
        depart = dialog.findViewById(R.id.depSpin);
        section = dialog.findViewById(R.id.secSpin);
        area = dialog.findViewById(R.id.AreaSpin);

        itemCodeText = (EditText) dialog.findViewById(R.id.itemCode);
        itemQty = (EditText) dialog.findViewById(R.id.item_qty);
        itemName = (TextView) dialog.findViewById(R.id.item_name);


        exit = dialog.findViewById(R.id.exit);
        save = dialog.findViewById(R.id.save);
        clear = dialog.findViewById(R.id.cler);

        barcode = dialog.findViewById(R.id.barcode);

        managList = new ArrayList<>();
        areaList = new ArrayList<>();
        depList = new ArrayList<>();
        secList = new ArrayList<>();


        managList = InventDB.getAllAssesstMang();
        depList = InventDB.getAllAssesstDepart();
        secList = InventDB.getAllAssesstSec();
        areaList = InventDB.getAllAssesstArea();
        assestItemsList = new ArrayList<>();

        ArrayAdapter MangAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, managList);
        mangment.setAdapter(MangAdapter);

        ArrayAdapter DepAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, depList);
        depart.setAdapter(DepAdapter);

        ArrayAdapter SecAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, secList);
        section.setAdapter(SecAdapter);

        ArrayAdapter AreaAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, areaList);
        area.setAdapter(AreaAdapter);

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarCode = true;
                collAssetsOpen = false;
                readBarCode(itemCodeText, 8);

            }
        });

        itemCodeText.requestFocus();
        itemQty.setEnabled(true);
        itemCodeText.setEnabled(true);

        save.setClickable(true);
        itemCodeText.setText("");
        itemQty.setText("1");
        itemName.setText("");


        clear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                itemCodeText.requestFocus();
                itemQty.setEnabled(true);
                itemCodeText.setEnabled(true);

                save.setClickable(true);
                itemCodeText.setText("");
                itemQty.setText("1");
                itemName.setText("");
            }
        });


        itemCodeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
//                    if (isEnter[0]) {
//                        if (!isFound[0]) {
//                            showAlertDialog("This item not found please add this item before ");
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    itemCodeText.requestFocus();
//                                }
//                            });
//
//                            itemQty.setEnabled(true);
//                            itemCodeText.setEnabled(true);
//
//                            save.setClickable(true);
//                            itemCodeText.setText("");
//                            itemQty.setText("1");
//                            itemName.setText("");
//                            salePrice.setText("");
//                            itemLocation.setText("");
//                            isEnter[0] = false;
//                        }
//
//                    }


                    String itemCode = itemCodeText.getText().toString();

                    if (!itemCode.equals("") && collAssetsOpen && noEnterData[0]) {
                        isEnter[0] = true;

                        assestItemsList = findAssest(itemCode);
                        if (assestItemsList.size() != 0) {

                            itemName.setText("" + assestItemsList.get(0).getAssesstName());
                            itemQty.setSelectAllOnFocus(true);
                            itemQty.requestFocus();

                        } else {
//                            Toast.makeText(CollectingData.this, "Not Found", Toast.LENGTH_SHORT).show();
                            showAlertDialog(getResources().getString(R.string.thisitemnotfound));
                            itemCodeText.setSelectAllOnFocus(true);
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    itemCodeText.requestFocus();
                                }
                            });

                        }

                    }
                }
                return false;
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assestItemsList.size() != 0) {
                    if (!itemCodeText.getText().toString().equals("") && !itemName.getText().toString().equals("") && !itemQty.getText().toString().equals("")) {
                        AssestItem assestItem = new AssestItem();
                        String manageString = "", depString = "", secString = "", areaString = "";
                        if (managList.size() != 0) {
                            manageString = managList.get(mangment.getSelectedItemPosition());

                        } else {
                            manageString = "";
                        }

                        if (depList.size() != 0) {
                            depString = depList.get(depart.getSelectedItemPosition());

                        } else {
                            depString = "";
                        }


                        if (secList.size() != 0) {
                            secString = secList.get(section.getSelectedItemPosition());

                        } else {
                            secString = "";
                        }


                        if (areaList.size() != 0) {
                            areaString = areaList.get(area.getSelectedItemPosition());

                        } else {
                            areaString = "";
                        }


                        assestItem.setAssesstMangment(manageString);
                        assestItem.setAssesstDEPARTMENT(depString);
                        assestItem.setAssesstSECTION(secString);
                        assestItem.setAssesstAREANAME(areaString);

                        assestItem.setAssesstBarcode(itemCodeText.getText().toString());
                        assestItem.setAssesstName(itemName.getText().toString());
                        assestItem.setAssesstType(assestItemsList.get(0).getAssesstType());
                        assestItem.setAssesstNo(assestItemsList.get(0).getAssesstNo());

                        assestItem.setAssesstQty(itemQty.getText().toString());
                        assestItem.setAssesstDate(convertToEnglish(today));
                        assestItem.setAssesstCode(assestItemsList.get(0).getAssesstCode());
                        assestItem.setIsExport("0");

                        InventDB.addAssetsItemInfo(assestItem);
                        progressDialog();

                        itemCodeText.requestFocus();

                        itemQty.setEnabled(true);
                        itemCodeText.setEnabled(true);

                        save.setClickable(true);
                        itemCodeText.setText("");
                        itemQty.setText("1");
                        itemName.setText("");
                        isEnter[0] = false;

                        assestItemsList.clear();
                    } else {
                        TostMesage(CollectingData.this.getResources().getString(R.string.insertData));
                        itemCodeText.setSelectAllOnFocus(true);
                        itemCodeText.requestFocus();
                    }
                } else {
                    TostMesage(CollectingData.this.getResources().getString(R.string.insertData));
                    itemCodeText.setSelectAllOnFocus(true);
                    itemCodeText.requestFocus();
                }

            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferPhar.setClickable(true);
                collTranseOpen = false;
                noEnterData[0] = false;
                dialog.dismiss();
            }
        });

        dialog.show();
    }


//   void Clear(EditText itemCodeText,EditText itemQty ,EditText itemName ,TextView itemNameBefore ,TextView itemCodeBefore
//   ,TextView itemLQtyBefore){
//
//       itemCodeText.requestFocus();
//       itemQty.setEnabled(true);
//       itemCodeText.setEnabled(true);
//
//       save.setClickable(true);
//       itemCodeText.setText("");
//       itemQty.setText("1");
//       itemName.setText("");
//       itemLocation.setText("");
//
//       if (!itemPreviousScan.isEmpty()) {
//           itemNameBefore.setText(itemPreviousScan.get(count[0] - 1).getItemName());
//           itemCodeBefore.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
//           itemLQtyBefore.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
//           float AllQty = Float.parseFloat(itemPreviousScan.get(count[0] - 1).getItemLocation()) + itemPreviousScan.get(count[0] - 1).getItemQty();
//           itemAQtyBefore.setText("" + AllQty);
//
//       }
//
//
//   }


    void showCollectByOrgDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_collecting_data);
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

    void showCollectByExpDialog() {
        final Dialog dialog1 = new Dialog(CollectingData.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.collecting_data_by_expiry_data);
        dialog1.setCanceledOnTouchOutside(false);

//        Toast.makeText(this, "1234567", Toast.LENGTH_SHORT).show();
        final boolean[] noEnter = {true};
        final Boolean[] isFoundExp = {false};
        final int[] count = {0};


        final String[] oldQty = {"0"};
        /////
        final Button exit, save, clear, update, newButton, search, barcode;
        final CheckBox upDateQty = (CheckBox) dialog1.findViewById(R.id.upDateQyt);//batch
        final CheckBox BatchNo = (CheckBox) dialog1.findViewById(R.id.batch);
        ArrayList<ItemInfoExp> itemLastScan = new ArrayList<>();
//        ArrayList<ItemCard> itemCardsList = new ArrayList<ItemCard>();
        final EditText batchText, itemCodeText1, itemQty;
        final TextView itemName, itemAvl, itemCodeP, itemSaleP, itemCodeBefor, itemNameBefor,
                itemAlBe, itemLastBe, itemDate;


        exit = (Button) dialog1.findViewById(R.id.exit);
        save = (Button) dialog1.findViewById(R.id.saveExp);
        clear = (Button) dialog1.findViewById(R.id.clearExp);
        update = (Button) dialog1.findViewById(R.id.updateExp);
        newButton = (Button) dialog1.findViewById(R.id.newBuEx);
        search = (Button) dialog1.findViewById(R.id.searchExp);

        itemCodeText1 = (EditText) dialog1.findViewById(R.id.items1);
        itemQty = (EditText) dialog1.findViewById(R.id.item_Qty);
        batchText = (EditText) dialog1.findViewById(R.id.batchText);

        itemName = (TextView) dialog1.findViewById(R.id.itemNames);
        itemAvl = (TextView) dialog1.findViewById(R.id.Avl);
        itemCodeP = (TextView) dialog1.findViewById(R.id.costP);
        itemSaleP = (TextView) dialog1.findViewById(R.id.saleP);
        itemCodeBefor = (TextView) dialog1.findViewById(R.id.itemCodeBef);
        itemNameBefor = (TextView) dialog1.findViewById(R.id.itemNameBef);
        itemAlBe = (TextView) dialog1.findViewById(R.id.itemAllBef);
        itemLastBe = (TextView) dialog1.findViewById(R.id.itemLQtyBef);
        itemDate = (TextView) dialog1.findViewById(R.id.DateItem);
        barcode = (Button) dialog1.findViewById(R.id.barcode);


        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarCode = true;
                collExOpen = false;
                readBarCode(itemCodeText1, 1);

            }
        });
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collExOpen = false;
                editText = itemCodeText1;
                openSave = true;
                showNewItemDialog(2);
            }
        });

        itemLastScan = InventDB.getAllItemInfoExp();
        final int[] serialExp = {itemLastScan.size()};

        itemCardsList =  getItemCard();


        itemCodeText1.setEnabled(true);
        itemCodeText1.requestFocus();
        itemQty.setEnabled(true);


        save.setClickable(true);
        itemCodeText1.setText("");
        itemQty.setText("1");
        itemName.setText("");
        itemDate.setText(convertToEnglish(today));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                collExOpen = false;
                SearchDialog(itemCodeText1, 1);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collExOpen = false;
                itemUpdateExp = InventDB.getAllItemInfoExp();
                updateOpen = true;
                upDateDialog(itemCodeBefor, 1, itemCodeText1, itemAlBe);

            }
        });


        itemDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog(itemDate);
            }
        });

        if (!itemLastScan.isEmpty()) {
            int qtyItem = itemLastScan.size();
            itemNameBefor.setText(itemLastScan.get(qtyItem - 1).getItemName());
            itemLastBe.setText("" + itemLastScan.get(qtyItem - 1).getItemQty());
            itemCodeBefor.setText(itemLastScan.get(qtyItem - 1).getItemCode());
            itemAlBe.setText(InventDB.getTotal(itemLastScan.get(qtyItem - 1).getItemCode(), "ITEM_INFO_EXP"));
        }

//        final ArrayList<ItemCard> itemCardsList = itemCardsList;
        final ArrayList<ItemInfoExp> itemPreviousScan = new ArrayList<>();

        clear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                itemCodeText1.requestFocus();
                itemQty.setEnabled(true);
                itemCodeText1.setEnabled(true);

                save.setClickable(true);
                itemCodeText1.setText("");
                itemQty.setText("1");
                itemName.setText("");
                batchText.setText("");

//                if (!itemPreviousScan.isEmpty()) {
//                    itemNameBefor.setText(itemPreviousScan.get(count[0] - 1).getItemName());
//                    itemCodeBefor.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
//                    itemLastBe.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
//                    float AllQty = itemPreviousScan.get(count[0] - 1).getRowIndex() + itemPreviousScan.get(count[0] - 1).getItemQty();
//                    itemAlBe.setText("" + AllQty);
//
//                }
            }
        });

        itemCodeText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count2) {
                String itemCode = itemCodeText1.getText().toString();
                if (!itemCode.equals("") && collExOpen && noEnter[0]) {

                    for (int i = 0; i < itemCardsList.size(); i++) {
                        String itemCodeList = itemCardsList.get(i).getItemCode();
                        if (itemCode.equals(itemCodeList)) {
                            itemName.setText(itemCardsList.get(i).getItemName());
                            itemAvl.setText(itemCardsList.get(i).getAVLQty());
                            itemCodeP.setText(itemCardsList.get(i).getCostPrc());
                            itemSaleP.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCardsList.get(i).getFDPRC()))));

                            isFoundExp[0] = true;
                            break;
                        } else {
                            isFoundExp[0] = false;
                        }
                    }

                    if (isFoundExp[0]) {

                        if (upDateQty.isChecked()) {
                            save.setClickable(true);
                            itemCodeText1.setEnabled(false);
                            itemQty.setEnabled(true);
                            itemQty.setSelectAllOnFocus(true);
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    itemQty.requestFocus();
                                }
                            });

//                            Toast.makeText(CollectingData.this, "save no auto", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(CollectingData.this, "save auto", Toast.LENGTH_SHORT).show();
                            oldQty[0] = InventDB.getTotal(itemCode, "ITEM_INFO_EXP");
                            itemQty.setText("1");
                            save.setClickable(false);
                            itemCodeText1.setEnabled(false);
                            itemQty.setEnabled(false);

                            if (BatchNo.isChecked()) {
                                if (!batchText.getText().toString().equals("")) {

                                    count[0]++;

                                    saveFunction(itemCodeText1, itemName, itemQty, itemDate, 1, "--",
                                            batchText.getText().toString(), itemAvl, itemNameBefor, itemLastBe, itemAlBe,
                                            itemCodeP, itemSaleP, itemPreviousScan, count[0], save, itemCodeBefor, oldQty[0], serialExp[0]++);

//                            if (!batchText.getText().toString().equals("")) {
//
//                                ItemInfoExp item = new ItemInfoExp();
//
//                                item.setItemCode(itemCodeText1.getText().toString());
//                                item.setItemName(itemName.getText().toString());
//                                item.setItemQty(Float.parseFloat(itemQty.getText().toString()));
//                                item.setExpDate(itemDate.getText().toString());
//                                item.setBatchNo(batchText.getText().toString());
//                                item.setRowIndex(Float.parseFloat("0.0"));
//                                item.setAVLQTY(itemAvl.getText().toString());
//                                item.setCostPrc(itemCodeP.getText().toString());
//                                item.setSalePrc(itemSaleP.getText().toString());
//                                item.setReceiptNo("");
//
//                                InventDB.addItemInfoExp(item);
//
//                                item.setRowIndex(Float.parseFloat(oldQty[0]));
//                                itemPreviousScan.add(item);
////                            Toast.makeText(CollectingData.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
//                                count[0]++;
//
//                                progressDialog();
//
//////////////////////////////////CLEAR ////////
//
//                                itemCodeText1.requestFocus();
//                                itemQty.setEnabled(true);
//                                itemCodeText1.setEnabled(true);
//
//                                save.setClickable(true);
//                                itemCodeText1.setText("");
//                                itemQty.setText("1");
//                                itemName.setText("");
//                                itemAvl.setText("");
//                                itemCodeP.setText("");
//                                itemSaleP.setText("");
//                                batchText.setText("");
//
//                                if (!itemPreviousScan.isEmpty()) {
//                                    itemNameBefor.setText(itemPreviousScan.get(count[0] - 1).getItemName());
//                                    itemCodeBefor.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
//                                    itemLastBe.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
//
//                                    float AllQty = itemPreviousScan.get(count[0] - 1).getRowIndex() + itemPreviousScan.get(count[0] - 1).getItemQty();
//                                    itemAlBe.setText("" + AllQty);
//
//
//                                }

                                } else {
                                    showAlertDialog("Batch Filed Empty Please Enter data");

                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            batchText.requestFocus();
                                        }
                                    });
                                    save.setClickable(true);
                                }
                            } else {
                                count[0]++;
                                saveFunction(itemCodeText1, itemName, itemQty, itemDate, 1, "--",
                                        "--", itemAvl, itemNameBefor, itemLastBe, itemAlBe,
                                        itemCodeP, itemSaleP, itemPreviousScan, count[0], save, itemCodeBefor, oldQty[0], serialExp[0]++);
                            }


                        }


                    } else {
                        showAlertDialog("This item not found please add this item before ");
//                        Toast.makeText(CollectingData.this, "This item not found please add this item before ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldQty[0] = InventDB.getTotal(itemCodeText1.getText().toString(), "ITEM_INFO_EXP");

                if (!itemQty.getText().toString().equals("") && !itemCodeText1.getText().toString().equals("") && isFoundExp[0]) {
                    if (BatchNo.isChecked()) {
                        if (!batchText.getText().toString().equals("")) {
                            count[0]++;
                            saveFunction(itemCodeText1, itemName, itemQty, itemDate, 1, "--",
                                    batchText.getText().toString(), itemAvl, itemNameBefor, itemLastBe, itemAlBe,
                                    itemCodeP, itemSaleP, itemPreviousScan, count[0], save, itemCodeBefor, oldQty[0], serialExp[0]++);

//                        ItemInfoExp item = new ItemInfoExp();
//
//                        item.setItemCode(itemCodeText1.getText().toString());
//                        item.setItemName(itemName.getText().toString());
//                        item.setItemQty(Float.parseFloat(itemQty.getText().toString()));
//                        item.setExpDate(itemDate.getText().toString());
//                        item.setBatchNo(batchText.getText().toString());
//                        item.setRowIndex(Float.parseFloat("0.0"));
//                        item.setAVLQTY(itemAvl.getText().toString());
//                        item.setCostPrc(itemCodeP.getText().toString());
//                        item.setSalePrc(itemSaleP.getText().toString());
//                        item.setReceiptNo("");
//
//                        InventDB.addItemInfoExp(item);
//
//                        item.setRowIndex(Float.parseFloat(oldQty[0]));
//                        itemPreviousScan.add(item);
//                        count[0]++;
//
//                        progressDialog();
//                        ////////CLEAR ///
//
//                        itemCodeText1.requestFocus();
//                        itemQty.setEnabled(true);
//                        itemCodeText1.setEnabled(true);
//
//                        save.setClickable(true);
//                        itemCodeText1.setText("");
//                        itemQty.setText("1");
//                        itemName.setText("");
//                        itemAvl.setText("");
//                        itemCodeP.setText("");
//                        itemSaleP.setText("");
//                        batchText.setText("");
//
//                        if (!itemPreviousScan.isEmpty()) {
//                            itemNameBefor.setText(itemPreviousScan.get(count[0] - 1).getItemName());
//                            itemCodeBefor.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
//                            itemLastBe.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
//                            float AllQty = itemPreviousScan.get(count[0] - 1).getRowIndex() + itemPreviousScan.get(count[0] - 1).getItemQty();
//                            itemAlBe.setText("" + AllQty);
//
//
//                        }
                        } else {
                            showAlertDialog("Batch Filed Empty Please Enter data");

                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    batchText.requestFocus();
                                }
                            });


                        }
                    } else {
                        count[0]++;
                        saveFunction(itemCodeText1, itemName, itemQty, itemDate, 1, "--",
                                "--", itemAvl, itemNameBefor, itemLastBe, itemAlBe,
                                itemCodeP, itemSaleP, itemPreviousScan, count[0], save, itemCodeBefor, oldQty[0], serialExp[0]++);
                    }

                } else {
                    Toast.makeText(CollectingData.this, "please insert all data in field", Toast.LENGTH_SHORT).show();

                }

            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noEnter[0] = false;
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }


    void showAlertDialog(String TextMessage) {
//        final Dialog dialogAlert = new Dialog(CollectingData.this);
//        dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogAlert.setCancelable(false);
//        dialogAlert.setContentView(R.layout.alert_dialog_not_found);
//        dialogAlert.setCanceledOnTouchOutside(false);
//        TextView textMessage = (TextView) dialogAlert.findViewById(R.id.alertMessage);
//        Button exitAlert = (Button) dialogAlert.findViewById(R.id.exitAlert);
//        textMessage.setText(TextMessage);
//
//        dialogAlert.show();
////        try {
////            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
////            r.play();
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
//        tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
//
//        exitAlert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogAlert.dismiss();
//
//            }
//        });


        SweetAlertDialog newSe= new SweetAlertDialog(CollectingData.this, SweetAlertDialog.ERROR_TYPE);
        newSe .setTitleText(getResources().getString(R.string.ops));
        newSe.setContentText(TextMessage);
        newSe.setCanceledOnTouchOutside(false);
        newSe .show();

        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);


//
//        new SweetAlertDialog(CollectingData.this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Are you sure?")
//                .setContentText(TextMessage)
//                .setConfirmText("Item!")
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.dismissWithAnimation();
//                    }
//                })
//                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.dismissWithAnimation();
//                    }
//                })
//                .show();


    }


    void showCollectByReceiptDialog() {
        dialog = new Dialog(CollectingData.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.collecting_data_with_receipt);
        dialog.setCanceledOnTouchOutside(false);

        final boolean[] noEnterR = {true};
        final Boolean[] isFoundRec = {false};
        final String[] oldQty = {"0"};
        final int[] count = {0};


        final Button exit, save, clear, update, newButton, search, barcode;
        final TextView itemName, Avl, costPrc, salePrc, dateExp, itemNameBefore, itemCodeBefore, lastQty, allQty;
        final EditText itemCodeTextReceipt, itemQty, batchNo, receiptNo;

        itemCodeTextReceipt = (EditText) dialog.findViewById(R.id.itemCodes12);
        itemQty = (EditText) dialog.findViewById(R.id.itemQt);
        batchNo = (EditText) dialog.findViewById(R.id.batchRecpic);
        receiptNo = (EditText) dialog.findViewById(R.id.receirtNo);

        itemName = (TextView) dialog.findViewById(R.id.itemName);
        Avl = (TextView) dialog.findViewById(R.id.avlQty);
        costPrc = (TextView) dialog.findViewById(R.id.costP);
        salePrc = (TextView) dialog.findViewById(R.id.saleP);
        dateExp = (TextView) dialog.findViewById(R.id.dateExp);
        itemNameBefore = (TextView) dialog.findViewById(R.id.itemNameBe);
        itemCodeBefore = (TextView) dialog.findViewById(R.id.itemCodeBe);
        lastQty = (TextView) dialog.findViewById(R.id.itemLastBe);
        allQty = (TextView) dialog.findViewById(R.id.itemAllBe);


        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);
        exit = (Button) dialog.findViewById(R.id.exit);
        update = (Button) dialog.findViewById(R.id.updateRec);
        newButton = (Button) dialog.findViewById(R.id.new1);
        search = (Button) dialog.findViewById(R.id.searchExpRec);

        barcode = (Button) dialog.findViewById(R.id.barcode);


        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarCode = true;
                collReceipt = false;
                readBarCode(itemCodeTextReceipt, 2);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                collReceipt = false;
                SearchDialog(itemCodeTextReceipt, 2);
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collReceipt = false;
                editText = itemCodeTextReceipt;
                openSave = true;
                showNewItemDialog(3);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collReceipt = false;
                String itemCode = itemCodeBefore.getText().toString();

                itemUpdateExpRec = InventDB.getAllItemInfoExpRec();
                updateOpen = true;
                upDateDialog(itemCodeBefore, 2, itemCodeTextReceipt, allQty);

            }
        });


        final CheckBox upDateQty, batchCheck;
        upDateQty = (CheckBox) dialog.findViewById(R.id.upDateRecip);
        batchCheck = (CheckBox) dialog.findViewById(R.id.batchBox);


        ArrayList<ItemInfoExp> itemLastScan = new ArrayList<ItemInfoExp>();
//        ArrayList<ItemCard> itemCardsList = new ArrayList<ItemCard>();

        itemLastScan = InventDB.getAllItemInfoExpRec();
        final int[] serialRec = {itemLastScan.size()};

        itemCodeTextReceipt.setEnabled(true);
        itemCodeTextReceipt.requestFocus();
        itemQty.setEnabled(true);


        save.setClickable(true);
        itemCodeTextReceipt.setText("");
        itemQty.setText("1");
        itemName.setText("");
        dateExp.setText(convertToEnglish(today));

        Log.e("4", "addText");
        dateExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog(dateExp);
            }
        });

        if (!itemLastScan.isEmpty()) {
            int qtyItem = itemLastScan.size();
            itemNameBefore.setText(itemLastScan.get(qtyItem - 1).getItemName());
            lastQty.setText("" + itemLastScan.get(qtyItem - 1).getItemQty());
            itemCodeBefore.setText(itemLastScan.get(qtyItem - 1).getItemCode());
            allQty.setText(InventDB.getTotal(itemLastScan.get(qtyItem - 1).getItemCode(), "ITEM_INFO_EXP_REC"));
        }


        final ArrayList<ItemInfoExp> itemPreviousScan = new ArrayList<>();
        itemCardsList = getItemCard();

//        final ArrayList<ItemCard> itemCardsList = itemCardsList;

        itemCodeTextReceipt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count3) {

                String itemCode = itemCodeTextReceipt.getText().toString();
                if (!itemCode.equals("") && collReceipt && noEnterR[0]) {

                    for (int i = 0; i < itemCardsList.size(); i++) {
                        String itemCodeList = itemCardsList.get(i).getItemCode();
                        if (itemCode.equals(itemCodeList)) {
                            itemName.setText(itemCardsList.get(i).getItemName());
                            Avl.setText(itemCardsList.get(i).getAVLQty());
                            costPrc.setText(itemCardsList.get(i).getCostPrc());
                            salePrc.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCardsList.get(i).getFDPRC()))));

                            isFoundRec[0] = true;
                            break;
                        } else {
                            isFoundRec[0] = false;
                        }
                    }

                    if (isFoundRec[0]) {

                        if (upDateQty.isChecked()) {
                            save.setClickable(true);
                            itemCodeTextReceipt.setEnabled(false);
                            itemQty.setEnabled(true);
                            itemQty.setSelectAllOnFocus(true);
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    itemQty.requestFocus();
                                }
                            });

                        } else {
                            oldQty[0] = InventDB.getTotal(itemCode, "ITEM_INFO_EXP_REC");
                            itemQty.setText("1");
                            save.setClickable(false);
                            itemCodeTextReceipt.setEnabled(false);
                            itemQty.setEnabled(false);

                            if (batchCheck.isChecked()) {
                                if (!batchNo.getText().toString().equals("")) {

                                    count[0]++;

                                    saveFunction(itemCodeTextReceipt, itemName, itemQty, dateExp, 2, receiptNo.getText().toString(),
                                            batchNo.getText().toString(), Avl, itemNameBefore, lastQty, allQty,
                                            costPrc, salePrc, itemPreviousScan, count[0], save, itemCodeBefore, oldQty[0], serialRec[0]++);

                                } else {
                                    showAlertDialog("Batch Filed Empty Please Enter data");

                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            batchNo.requestFocus();
                                        }
                                    });
                                    save.setClickable(true);
                                }
                            } else {
                                count[0]++;
                                saveFunction(itemCodeTextReceipt, itemName, itemQty, dateExp, 2, receiptNo.getText().toString(),
                                        "--", Avl, itemNameBefore, lastQty, allQty,
                                        costPrc, salePrc, itemPreviousScan, count[0], save, itemCodeBefore, oldQty[0], serialRec[0]++);
                            }


                        }


                    } else {
                        showAlertDialog("This item not found please add this item before ");
//                        Toast.makeText(CollectingData.this, "This item not found please add this item before ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCodeTextReceipt.requestFocus();
                itemQty.setEnabled(true);
                itemCodeTextReceipt.setEnabled(true);

                save.setClickable(true);
                itemCodeTextReceipt.setText("");
                itemQty.setText("1");
                itemName.setText("");
                batchNo.setText("");
                receiptNo.setText("");

//                if (!itemPreviousScan.isEmpty()) {
//                    itemNameBefore.setText(itemPreviousScan.get(count[0] - 1).getItemName());
//                    itemCodeBefore.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
//                    lastQty.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
//                    float AllQty = itemPreviousScan.get(count[0] - 1).getRowIndex() + itemPreviousScan.get(count[0] - 1).getItemQty();
//                    allQty.setText("" + AllQty);
//
//                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldQty[0] = InventDB.getTotal(itemCodeTextReceipt.getText().toString(), "ITEM_INFO_EXP_REC");

                if (!itemQty.getText().toString().equals("") && !itemCodeTextReceipt.getText().toString().equals("") && isFoundRec[0]) {
                    if (batchCheck.isChecked()) {
                        if (!batchNo.getText().toString().equals("")) {
                            count[0]++;
                            saveFunction(itemCodeTextReceipt, itemName, itemQty, dateExp, 2, receiptNo.getText().toString(),
                                    "--", Avl, itemNameBefore, lastQty, allQty,
                                    costPrc, salePrc, itemPreviousScan, count[0], save, itemCodeBefore, oldQty[0], serialRec[0]++);

                        } else {
                            showAlertDialog("Batch Filed Empty Please Enter data");

                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    batchNo.requestFocus();
                                }
                            });


                        }
                    } else {
                        count[0]++;
                        saveFunction(itemCodeTextReceipt, itemName, itemQty, dateExp, 2, receiptNo.getText().toString(),
                                "--", Avl, itemNameBefore, lastQty, allQty,
                                costPrc, salePrc, itemPreviousScan, count[0], save, itemCodeBefore, oldQty[0], serialRec[0]++);
                    }

                } else {
                    Toast.makeText(CollectingData.this, "please insert all data in field", Toast.LENGTH_SHORT).show();

                }

            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collReceipt = false;
                noEnterR[0] = false;
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    @SuppressLint("SetTextI18n")
    void showTransferDialog() {
        final Dialog dialogTransfer = new Dialog(CollectingData.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogTransfer.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransfer.setCancelable(false);
        if (controll.isYellow) {
            dialogTransfer.setContentView(R.layout.transfer_data_yellow);
        } else {
            dialogTransfer.setContentView(R.layout.transfer_data);
        }
        dialogTransfer.setCanceledOnTouchOutside(false);


        itemCardsList = getItemCard();
        String VHF = InventDB.getVhf();
        int VHF_no = 0;
        if (!VHF.equals("")) {
            VHF_no = Integer.parseInt(VHF);
        } else {
            VHF_no = 1;
            InventDB.addVhf(new TransferVhfSerial(1));
        }

        final double[] uQty = {1};


//
        final ArrayList<TransferItemsInfo> itemPreviousScan = new ArrayList<>();

        final int[] serialInfo = {InventDB.getAllTransferItemInfo().size() + 1};


        final String[] oldQty = {"0"};

        final LinearLayout exit, save, search, news, endVhf, update;
        final EditText Qty, ItemCode;
        final Spinner from, to;
        final TextView ItemName, ItemCodeBefor, ItemNameBefor, LastQ, LastAll, endVHFt, _qty;

        _qty = dialogTransfer.findViewById(R.id._qty);
        ItemName = dialogTransfer.findViewById(R.id.ItemName);
        ItemCodeBefor = dialogTransfer.findViewById(R.id.ItemBCode);
        ItemNameBefor = dialogTransfer.findViewById(R.id.ItemBName);
        LastQ = dialogTransfer.findViewById(R.id.Last);
        LastAll = dialogTransfer.findViewById(R.id.AllQty);
        endVHFt = dialogTransfer.findViewById(R.id.endVHFt);

        Qty = dialogTransfer.findViewById(R.id.Qty);
        ItemCode = dialogTransfer.findViewById(R.id.ItemCode);

        from = dialogTransfer.findViewById(R.id.fromS);
        to = dialogTransfer.findViewById(R.id.toS);


        exit = dialogTransfer.findViewById(R.id.exit);
        save = dialogTransfer.findViewById(R.id.save);
        search = dialogTransfer.findViewById(R.id.search);
        news = dialogTransfer.findViewById(R.id.newBu);
        endVhf = dialogTransfer.findViewById(R.id.endVHF);
        update = dialogTransfer.findViewById(R.id.updateBu);
        final CheckBox updateCheck = dialogTransfer.findViewById(R.id.updateCheckBox);


        Button barcode;
        barcode = dialogTransfer.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarCode = true;
                collTransfer = false;
                readBarCode(ItemCode, 6);
            }
        });

        final int[] count = {0};
        final Boolean[] isFound = {false};
        final boolean[] upDateClick = {false};
        final boolean[] Enterin = {true};

        ItemCode.requestFocus();
        Qty.setEnabled(true);
        ItemCode.setEnabled(true);

        from.setEnabled(true);
        to.setEnabled(true);

        save.setClickable(true);
        ItemCode.setText("");
        Qty.setText("1");
        ItemName.setText("");
        endVHFt.setText("" + (VHF_no));


        ArrayList<TransferItemsInfo> itemLastScan = new ArrayList<>();

        itemLastScan = InventDB.getAllTransferItemInfo();

        if (!itemLastScan.isEmpty()) {
            int qtyItem = itemLastScan.size();
            ItemNameBefor.setText(itemLastScan.get(qtyItem - 1).getItemName());
            LastQ.setText("" + itemLastScan.get(qtyItem - 1).getItemQty());
            ItemCodeBefor.setText(itemLastScan.get(qtyItem - 1).getItemCode());
            LastAll.setText(InventDB.getTotal(itemLastScan.get(qtyItem - 1).getItemCode(), "TRANSFER_ITEMS_INFO"));

        }


        itemCardsList =  getItemCard();
        final String[] fromStore = new String[1];
        final String[] ToStore = new String[1];

        final List<Stk> STKList = InventDB.getAllStk();
        final List<String> StokNo = new ArrayList<>();
        ArrayAdapter<String> StkAdapter;
        if (STKList.size() != 0) {
            StokNo.clear();
            for (int i = 0; i < STKList.size(); i++) {
                StokNo.add(STKList.get(i).getStkNo());
            }

            StkAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, StokNo);
            from.setAdapter(StkAdapter);

            StkAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, StokNo);
            to.setAdapter(StkAdapter);
        }


//
//        Qty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_NULL) {
////                    if (isEnter[0]) {
////                        if (!isFound[0]) {
////                            showAlertDialog("This item not found please add this item before ");
////                            new Handler().post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    itemCodeText.requestFocus();
////                                }
////                            });
////
////                            itemQty.setEnabled(true);
////                            itemCodeText.setEnabled(true);
////
////                            save.setClickable(true);
////                            itemCodeText.setText("");
////                            itemQty.setText("1");
////                            itemName.setText("");
////                            salePrice.setText("");
////                            itemLocation.setText("");
////                            isEnter[0] = false;
////                        }
////
////                    }
//
//                    if(!Qty.getText().toString().equals("")){
//
//                        String Qtys=""+(Integer.parseInt(Qty.getText().toString())* uQty[0]);
//                        Qty.setText(Qtys);
//                    }else{
//                        Qty.setText("1");
//                    }
//
//
//
//                }
//
//
//                return false;
//            }
//        });


        endVhf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int i= finalVHF_no1 +1;

                int i = 0;
                String VHF = InventDB.getVhf();
                int VHF_no = InventDB.getMax();
                if (!VHF.equals("")) {
                    if (Integer.parseInt(VHF) == VHF_no) {
                        i = VHF_no + 1;
                        InventDB.updateVhfNo("" + i);
                    } else if (Integer.parseInt(VHF) >= VHF_no) {
                        i = Integer.parseInt(VHF);
                    }

                } else {

                    InventDB.addVhf(new TransferVhfSerial(VHF_no));
                }

                endVHFt.setText("" + i);
                from.setEnabled(true);
                to.setEnabled(true);
            }
        });


        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collTransfer = false;
                editText = ItemCode;
                openSave = true;

                showNewItemDialog(4);
            }
        });

        from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromStore[0] = StokNo.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateCheck.isChecked()) {
                    upDateClick[0] = true;
                } else {
                    upDateClick[0] = false;
                }
            }
        });

        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToStore[0] = StokNo.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                collTransfer = false;
                SearchDialog(ItemCode, 4);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collTransfer = false;
                itemUpdateTransfer = InventDB.getAllTransferItemInfo();
                updateOpen = true;
                upDateDialog(ItemCodeBefor, 3, ItemCode, LastAll);

            }
        });


        ItemCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
//                    if (isEnter[0]) {
//                        if (!isFound[0]) {
//                            showAlertDialog("This item not found please add this item before ");
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    itemCodeText.requestFocus();
//                                }
//                            });
//
//                            itemQty.setEnabled(true);
//                            itemCodeText.setEnabled(true);
//
//                            save.setClickable(true);
//                            itemCodeText.setText("");
//                            itemQty.setText("1");
//                            itemName.setText("");
//                            salePrice.setText("");
//                            itemLocation.setText("");
//                            isEnter[0] = false;
//                        }
//
//                    }

                    if (Enterin[0]) {
                        String codeFromText="";
                        String itemCode = ItemCode.getText().toString();
                        String itemSwitch = "";
                        Enterin[0] = false;

                        if (!itemCode.equals("") && collTransfer) {


                            Log.e("itemCardsList.size()", "-->" + itemCardsList.size());

//                                offer = false;

                                 codeFromText=itemCode;
                                List<String> itemUnite = findUnite(itemCode);


                                if (itemUnite.size() != 0) {

                                    itemCode = itemUnite.get(0);
                                    uQty[0] = Double.parseDouble(itemUnite.get(2));
                                    _qty.setText("" + convertToEnglish("" + Double.parseDouble(itemUnite.get(2))));

                                } else {
                                    itemSwitch = findSwitch(itemCode);
                                    if (!itemSwitch.equals("")) {
                                        itemCode = itemSwitch;
                                    }
                                    uQty[0] = 1;
                                    _qty.setText("1");

                                }

                            List<OfferTable> offerTables=findOffer(itemCode);
                            List<OfferTable> offerTablesO=findOffer(codeFromText);

                            if(offerTablesO.size()!=0){

//                                offer=true;
//                            itemCode = offerTables.get(0).getITEMOCODE();


                            }else
                            if (offerTables.size()!=0) {
//                                offer=true;
//                            itemCode = offerTables.get(0).getITEMOCODE();

                            }
                            for (int i = 0; i < itemCardsList.size(); i++) {
                                String itemCodeList = itemCardsList.get(i).getItemCode();
                                if (itemCode.equals(itemCodeList)) {
                                    isFound[0] = true;

                                    Qty.setText("" + (Integer.parseInt(Qty.getText().toString())));
                                    ItemName.setText(itemCardsList.get(i).getItemName());


                                    break;
                                } else {
                                    isFound[0] = false;
                                }
                            }


                            if (isFound[0]) {


                                if (upDateClick[0]) {
                                    save.setClickable(true);
                                    ItemCode.setEnabled(false);
                                    Qty.setEnabled(true);


//                                itemQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                                    @Override
//                                    public void onFocusChange(View v, boolean hasFocus) {
//                                        if (!hasFocus){
//                                            // TODO: the editText has just been left
//                                            if(ExpClick[0]){
//                                                TimeDialog(itemDate);
//                                            }
//                                        }
//
//                                    }
//                                });


                                    if (event != null) {
                                        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.ACTION_DOWN) {

                                            Qty.setSelectAllOnFocus(true);
                                            _qty.requestFocus();

                                        } else {
                                            Qty.setSelectAllOnFocus(true);
                                            Qty.requestFocus();
                                        }
                                    } else {
                                        Qty.setSelectAllOnFocus(true);
                                        new Handler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Qty.requestFocus();
                                            }
                                        });

                                    }


//                            Toast.makeText(CollectingData.this, "save no auto", Toast.LENGTH_SHORT).show();
                                } else {
//                            Toast.makeText(CollectingData.this, "save auto", Toast.LENGTH_SHORT).show();
                                    oldQty[0] = InventDB.getTotal(itemCode, "TRANSFER_ITEMS_INFO");
//                                Qty.setText("1");
                                    save.setClickable(false);
                                    ItemCode.setEnabled(false);
                                    ItemName.setEnabled(false);

                                    TransferItemsInfo item = new TransferItemsInfo();

                                    String Lo = "";

                                    List<MainSetting> mainSettings = InventDB.getAllMainSetting();
                                    if (mainSettings.size() != 0) {
                                        Lo = mainSettings.get(0).getStorNo();
                                    }

                                    item.setItemCode(itemCode);
                                    item.setItemName(ItemName.getText().toString());


                                    String Qtys = "";
                                    if (!Qty.getText().toString().equals("")) {

                                        Qtys = "" + (Double.parseDouble(Qty.getText().toString()) * Double.parseDouble(_qty.getText().toString()));
                                    } else {
                                        Qtys = "" + (1 * Double.parseDouble(_qty.getText().toString()));

                                    }

                                    Qty.setText(Qtys);


                                    item.setItemQty(Float.parseFloat(Qty.getText().toString()));


                                    item.setRowIndex(serialInfo[0]++);
                                    item.setFromStr(fromStore[0]);
                                    item.setToStr(ToStore[0]);
                                    item.setVhfNo(Integer.parseInt(endVHFt.getText().toString()));
                                    Log.e("Store", "===>" + fromStore[0] + "--->" + ToStore[0]);
                                    item.setIsExport("0");

                                    InventDB.addItemTransferInfo(item);


                                    item.setTotalQty(oldQty[0]);
                                    Log.e("ol12", "===>" + oldQty[0]);
                                    itemPreviousScan.add(item);
//                            Toast.makeText(CollectingData.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
                                    count[0]++;

                                    progressDialog();

////////////////////////////////CLEAR ////////


                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ItemCode.requestFocus();
                                        }
                                    });

                                    from.setEnabled(false);
                                    to.setEnabled(false);
                                    Qty.setEnabled(true);
                                    ItemCode.setEnabled(true);


                                    save.setClickable(true);
                                    ItemCode.setText("");
                                    Qty.setText("1");
                                    ItemName.setText("");
                                    _qty.setText("1");

//                                itemLocation.setText("");

                                    if (!itemPreviousScan.isEmpty()) {
                                        ItemNameBefor.setText(itemPreviousScan.get(count[0] - 1).getItemName());
                                        ItemCodeBefor.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
                                        LastQ.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());

                                        float AllQty = Float.parseFloat(itemPreviousScan.get(count[0] - 1).getTotalQty()) + itemPreviousScan.get(count[0] - 1).getItemQty();
                                        LastAll.setText("" + AllQty);


                                    }


                                }


                            } else {

                                showAlertDialog(CollectingData.this.getResources().getString(R.string.itemNotFoundAlert));
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ItemCode.requestFocus();
                                    }
                                });

                                from.setEnabled(false);
                                to.setEnabled(false);

                                Qty.setEnabled(true);
                                ItemCode.setEnabled(true);

                                save.setClickable(true);
                                ItemCode.setText("");
                                Qty.setText("1");
                                ItemName.setText("");

//                            itemLocation.setText("");


//                        showAlertDialog("This item not found please add this item before ");
//                        Toast.makeText(CollectingData.this, "This item not found please add this item before ", Toast.LENGTH_SHORT).show();
                            }

                        }
                        Enterin[0] = true;
                    }


                }


                return false;
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferData.setClickable(true);
                dialogTransfer.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemSwitch;
                String itemCode = ItemCode.getText().toString();
                List<String> itemUnite = findUnite(itemCode);
                int uQty = 1;


                if (itemUnite.size() != 0) {

                    itemCode = itemUnite.get(0);
                    uQty = Integer.parseInt(itemUnite.get(2));

                } else {
                    itemSwitch = findSwitch(itemCode);
                    if (!itemSwitch.equals("")) {
                        itemCode = itemSwitch;
                    }
                    uQty = 1;

                }


                if (!Qty.getText().toString().equals("") && !itemCode.equals("") && isFound[0]) {
                    oldQty[0] = InventDB.getTotal(itemCode, "TRANSFER_ITEMS_INFO");
                    TransferItemsInfo item = new TransferItemsInfo();

                    String Lo = "";
                    item.setItemCode(itemCode);
                    item.setItemName(ItemName.getText().toString());

                    String Qtys = "";
                    if (!Qty.getText().toString().equals("")) {

                        Qtys = "" + (Double.parseDouble(Qty.getText().toString()) * Double.parseDouble(_qty.getText().toString()));
                    } else {
                        Qtys = "" + (1 * Double.parseDouble(_qty.getText().toString()));

                    }

                    Qty.setText(Qtys);

                    item.setItemQty(Float.parseFloat(Qty.getText().toString()));
                    item.setToStr(ToStore[0]);
                    item.setFromStr(fromStore[0]);

//                    List<MainSetting>mainSettings=InventDB.getAllMainSetting();
//                    if(mainSettings.size()!=0){
//                        Lo=mainSettings.get(0).getStorNo();
//                    }
                    item.setIsExport("0");
                    item.setRowIndex(serialInfo[0]++);
                    item.setVhfNo(Integer.parseInt(endVHFt.getText().toString()));
                    InventDB.addItemTransferInfo(item);
                    item.setTotalQty(oldQty[0]);
                    itemPreviousScan.add(item);
                    count[0]++;

                    progressDialog();
                    ////////CLEAR ///

                    ItemCode.requestFocus();
                    Qty.setEnabled(true);
                    ItemCode.setEnabled(true);


                    from.setEnabled(false);
                    to.setEnabled(false);

                    save.setClickable(true);
                    ItemCode.setText("");
                    Qty.setText("1");
                    _qty.setText("1");
                    ItemName.setText("");

//                    itemLocation.setText("");

                    if (!itemPreviousScan.isEmpty()) {
                        ItemNameBefor.setText(itemPreviousScan.get(count[0] - 1).getItemName());
                        ItemCodeBefor.setText(itemPreviousScan.get(count[0] - 1).getItemCode());
                        LastQ.setText("" + itemPreviousScan.get(count[0] - 1).getItemQty());
                        float AllQty = Float.parseFloat(itemPreviousScan.get(count[0] - 1).getTotalQty()) + itemPreviousScan.get(count[0] - 1).getItemQty();
                        LastAll.setText("" + AllQty);


                    }

                } else {
//                    Toast.makeText(CollectingData.this, CollectingData.this.getResources().getString(R.string.insertData), Toast.LENGTH_SHORT).show();
                    TostMesage(CollectingData.this.getResources().getString(R.string.insertData));
                }


            }
        });

        dialogTransfer.show();
    }

    void saveFunction(EditText itemCodeText1, TextView itemName, EditText itemQty, TextView itemDate, int switches, String receiptNo,
                      String batchText, TextView itemAvl, TextView itemNameBefor, TextView itemLastBe, TextView itemAlBe,
                      TextView itemCodeP, TextView itemSaleP, ArrayList<ItemInfoExp> itemPreviousScan, int count, Button save, TextView itemCodeBefor, String oldQty, int serial) {


        ItemInfoExp item = new ItemInfoExp();


        item.setItemCode(itemCodeText1.getText().toString());
        item.setItemName(itemName.getText().toString());
        item.setItemQty(Float.parseFloat(itemQty.getText().toString()));
        item.setExpDate(itemDate.getText().toString());
        item.setBatchNo(batchText);
        item.setRowIndex(Float.parseFloat("0.0"));
        item.setAVLQTY(itemAvl.getText().toString());
        item.setCostPrc(itemCodeP.getText().toString());
        item.setSalePrc(itemSaleP.getText().toString());
        item.setReceiptNo(receiptNo);

        switch (switches) {
            case 1:

                item.setSerialNo(serial);
                InventDB.addItemInfoExp(item);
                break;
            case 2:
                serial = InventDB.getAllItemInfoExpRec().size();
                item.setSerialNo(serial);
                InventDB.addItemInfoReceipt(item);
                break;
        }

        item.setRowIndex(Float.parseFloat(oldQty));
        itemPreviousScan.add(item);

        progressDialog();

////////////////////////////////CLEAR ////////

        itemCodeText1.requestFocus();
        itemQty.setEnabled(true);
        itemCodeText1.setEnabled(true);

        save.setClickable(true);
        itemCodeText1.setText("");
        itemQty.setText("1");
        itemName.setText("");
        itemAvl.setText("");
        itemCodeP.setText("");
        itemSaleP.setText("");


        if (!itemPreviousScan.isEmpty()) {
            itemNameBefor.setText(itemPreviousScan.get(count - 1).getItemName());
            itemCodeBefor.setText(itemPreviousScan.get(count - 1).getItemCode());
            itemLastBe.setText("" + itemPreviousScan.get(count - 1).getItemQty());

            float AllQty = itemPreviousScan.get(count - 1).getRowIndex() + itemPreviousScan.get(count - 1).getItemQty();
            itemAlBe.setText("" + AllQty);


        }


    }


    void upDateDialog(final TextView edit1, final int switches, final EditText edit, final TextView AllQty) {
        final Dialog dialogUpdate = new Dialog(CollectingData.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpdate.setCancelable(false);
        if (controll.isYellow) {
            dialogUpdate.setContentView(R.layout.update_layout_yellow);
        } else {
            dialogUpdate.setContentView(R.layout.update_layout);
        }

        dialogUpdate.setCanceledOnTouchOutside(false);
        final TableLayout recipeTable = (TableLayout) dialogUpdate.findViewById(R.id.Table);

        final EditText ItemCodeT;
        ItemCodeT = (EditText) dialogUpdate.findViewById(R.id.itemCodeUp);

        ItemCodeT.requestFocus();
        edit.setEnabled(false);

        final String ItemCode = ItemCodeT.getText().toString();

        LinearLayout done, exit;
        exit = dialogUpdate.findViewById(R.id.exit);
        done = dialogUpdate.findViewById(R.id.done);

        Button barcode;
        barcode = dialogUpdate.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCode = true;
                updateOpen = false;
                readBarCode(ItemCodeT, 5);
            }
        });

        ///////////////////////////////////////////////////////////////////////////

        TextWatcher item = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recipeTable.removeAllViews();
                if (updateOpen && !ItemCodeT.getText().toString().equals("")) {

                    switch (switches) {
                        case 0:
                            for (int i = 0; i < itemUpdate.size(); i++) {
                                if (itemUpdate.get(i).getItemCode().contains(ItemCodeT.getText().toString()) || itemUpdate.get(i).getItemName().contains(ItemCodeT.getText().toString())) {
                                    insertRowUpdate(itemUpdate.get(i).getItemCode(), itemUpdate.get(i).getItemName(), String.valueOf(itemUpdate.get(i).getSerialNo()),
                                            String.valueOf(itemUpdate.get(i).getItemQty()), recipeTable);
                                }

                            }
                            break;
                        case 1:

                            for (int i = 0; i < itemUpdateExp.size(); i++) {
                                if (itemUpdateExp.get(i).getItemCode().contains(ItemCodeT.getText().toString()) || itemUpdateExp.get(i).getItemName().contains(ItemCodeT.getText().toString())) {
                                    insertRowUpdate(itemUpdateExp.get(i).getItemCode(), itemUpdateExp.get(i).getItemName(), String.valueOf(itemUpdateExp.get(i).getSerialNo()),
                                            String.valueOf(itemUpdateExp.get(i).getItemQty()), recipeTable);
                                }

                            }

                            break;
                        case 2:
                            for (int i = 0; i < itemUpdateExpRec.size(); i++) {
                                if (itemUpdateExpRec.get(i).getItemCode().contains(ItemCodeT.getText().toString()) || itemUpdateExpRec.get(i).getItemName().contains(ItemCodeT.getText().toString())) {
                                    insertRowUpdate(itemUpdateExpRec.get(i).getItemCode(), itemUpdateExpRec.get(i).getItemName(), String.valueOf(itemUpdateExpRec.get(i).getSerialNo()),
                                            String.valueOf(itemUpdateExpRec.get(i).getItemQty()), recipeTable);
                                }

                            }
                            break;

                        case 3:
                            for (int i = 0; i < itemUpdateTransfer.size(); i++) {
                                if (itemUpdateTransfer.get(i).getItemCode().contains(ItemCodeT.getText().toString()) || itemUpdateTransfer.get(i).getItemName().contains(ItemCodeT.getText().toString())) {
                                    insertRowUpdate(itemUpdateTransfer.get(i).getItemCode(), itemUpdateTransfer.get(i).getItemName(), String.valueOf(itemUpdateTransfer.get(i).getRowIndex()),
                                            String.valueOf(itemUpdateTransfer.get(i).getItemQty()), recipeTable);
                                }

                            }
                            break;

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        };

        ItemCodeT.addTextChangedListener(item);
        ItemCodeT.setText(edit1.getText().toString());


        ///////////////////////////////////////////////////////////////////////////


//        ItemCodeT.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                recipeTable.removeAllViews();
//                if (updateOpen && !ItemCodeT.getText().toString().equals("")) {
//
//                    switch (switches) {
//                        case 0:
//                            for (int i = 0; i < itemUpdate.size(); i++) {
//                                if (itemUpdate.get(i).getItemCode().contains(ItemCodeT.getText().toString()) || itemUpdate.get(i).getItemName().contains(ItemCodeT.getText().toString())) {
//                                    insertRowUpdate(itemUpdate.get(i).getItemCode(), itemUpdate.get(i).getItemName(), String.valueOf(itemUpdate.get(i).getSerialNo()),
//                                            String.valueOf(itemUpdate.get(i).getItemQty()), recipeTable);
//                                }
//
//                            }
//                            break;
//                        case 1:
//
//                            for (int i = 0; i < itemUpdateExp.size(); i++) {
//                                if (itemUpdateExp.get(i).getItemCode().contains(ItemCodeT.getText().toString()) || itemUpdateExp.get(i).getItemName().contains(ItemCodeT.getText().toString())) {
//                                    insertRowUpdate(itemUpdateExp.get(i).getItemCode(), itemUpdateExp.get(i).getItemName(), String.valueOf(itemUpdateExp.get(i).getSerialNo()),
//                                            String.valueOf(itemUpdateExp.get(i).getItemQty()), recipeTable);
//                                }
//
//                            }
//
//                            break;
//                        case 2:
//                            for (int i = 0; i < itemUpdateExpRec.size(); i++) {
//                                if (itemUpdateExpRec.get(i).getItemCode().contains(ItemCodeT.getText().toString()) || itemUpdateExpRec.get(i).getItemName().contains(ItemCodeT.getText().toString())) {
//                                    insertRowUpdate(itemUpdateExpRec.get(i).getItemCode(), itemUpdateExpRec.get(i).getItemName(), String.valueOf(itemUpdateExpRec.get(i).getSerialNo()),
//                                            String.valueOf(itemUpdateExpRec.get(i).getItemQty()), recipeTable);
//                                }
//
//                            }
//                            break;
//
//                    }
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Serial, newValue, itemCode;
                boolean AllTrue = false;

                for (int i = 0; i < recipeTable.getChildCount(); i++) {
                    TableRow t = (TableRow) recipeTable.getChildAt(i);
                    TextView ItemCode = (TextView) t.getChildAt(0);
                    TextView upQty = (TextView) t.getChildAt(3);
                    if (!upQty.getText().toString().equals("") && !upQty.getText().toString().equals("0")) {
                        Serial = ItemCode.getTag().toString();
                        newValue = upQty.getText().toString();
                        itemCode = ItemCode.getText().toString();
                        switch (switches) {
                            case 0:
                                InventDB.updateExpTable("ITEMS_INFO", newValue, Serial, itemCode);
//                                allQty.setText(InventDB.getTotal(itemCode, "ITEMS_INFO"));
                                break;
                            case 1:
                                InventDB.updateExpTable("ITEM_INFO_EXP", newValue, Serial, itemCode);
//                                allQty.setText(InventDB.getTotal(itemCode, "ITEM_INFO_EXP"));
                                break;
                            case 2:
                                InventDB.updateExpTable("ITEM_INFO_EXP_REC", newValue, Serial, itemCode);
//                                allQty.setText(InventDB.getTotal(itemCode, "ITEM_INFO_EXP_REC"));
                                break;
                            case 3:
                                InventDB.updateTransferTable("TRANSFER_ITEMS_INFO", newValue, Serial, itemCode);
//                                allQty.setText(InventDB.getTotal(itemCode, "ITEM_INFO_EXP_REC"));
                                break;
                        }

                        if (edit1.getText().toString().equals(itemCode)) {
                            AllTrue = true;
                        }

                    }

                }

                switch (switches) {
                    case 0:
                        collDOpen = true;
                        if (AllTrue) {
                            AllQty.setText(InventDB.getTotal(edit1.getText().toString(), "ITEMS_INFO"));
                        }
                        break;
                    case 1:
                        collExOpen = true;
                        if (AllTrue) {
                            AllQty.setText(InventDB.getTotal(edit1.getText().toString(), "ITEM_INFO_EXP"));
                        }
                        break;
                    case 2:
                        collReceipt = true;
                        if (AllTrue) {
                            AllQty.setText(InventDB.getTotal(edit1.getText().toString(), "ITEM_INFO_EXP_REC"));
                        }
                        break;
                    case 3:
                        collTransfer = true;
                        if (AllTrue) {
                            AllQty.setText(InventDB.getTotal(edit1.getText().toString(), "TRANSFER_ITEMS_INFO"));
                        }
                        break;
                }

                updateOpen = false;
                edit.requestFocus();
                edit.setEnabled(true);
                edit.setText("");
                dialogUpdate.dismiss();
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (switches) {
                    case 0:
                        collDOpen = true;
                        break;
                    case 1:
                        collExOpen = true;
                        break;
                    case 2:
                        collReceipt = true;
                        break;
                    case 3:
                        collTransfer = true;
                        break;
                }
                updateOpen = false;
                edit.requestFocus();
                edit.setEnabled(true);
                edit.setText("");
                dialogUpdate.dismiss();
            }
        });

        dialogUpdate.show();


    }

    void insertRowUpdate(String itemCodes, String itemName, String serial, String itemQty, TableLayout recipeTable) {


        row = new TableRow(CollectingData.this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);

        for (int i = 0; i < 4; i++) {
            final EditText editText = new EditText(CollectingData.this);
            final TextView textView = new TextView(CollectingData.this);
            if (i == 3) {
                editText.setTextColor(ContextCompat.getColor(CollectingData.this, R.color.ExitButton));
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, 100, 1.0f);
                editText.setLayoutParams(lp2);
                editText.setText("0");
//                editText.setBackgroundColor(getResources().getColor(R.color.freeButton));
                //                editText.setId(Integer.parseInt(textId + "3"));
                row.addView(editText);

            } else {
                switch (i) {
                    case 0:
                        textView.setText(itemCodes);
                        textView.setTag(serial);
                        break;
                    case 1:
                        textView.setText(itemName);
                        break;
                    case 2:
                        textView.setText(itemQty);
                        break;


                }
                textView.setTextColor(ContextCompat.getColor(CollectingData.this, R.color.white));
                textView.setGravity(Gravity.CENTER);

//                textView.setId(Integer.parseInt(textId + "" + i));

                TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                textView.setLayoutParams(lp2);


                row.addView(textView);

            }

        }
//        row.setId(textId);
        recipeTable.addView(row);
//        textId++;


    }

    @SuppressLint("ClickableViewAccessibility")
    void SearchDialog(final EditText itemCodeText, final int swSearch) {
        final Dialog dialogSearch = new Dialog(CollectingData.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSearch.setCancelable(false);
        if (controll.isYellow) {
            dialogSearch.setContentView(R.layout.search_dialog_yellow);
        } else {
            dialogSearch.setContentView(R.layout.search_dialog);
        }

        dialogSearch.setCanceledOnTouchOutside(false);


        LinearLayout exit = dialogSearch.findViewById(R.id.exitsearch);
        final EditText itemCodeSearch = (EditText) dialogSearch.findViewById(R.id.itemCodeSearch);
        final ListView tabeSearch = (ListView) dialogSearch.findViewById(R.id.tableSearch);

        TextView sizeM,colorM;
        sizeM=dialogSearch.findViewById(R.id.sizeM);
        colorM=dialogSearch.findViewById(R.id.colorM);

        if(companyNo==1){
            sizeM.setVisibility(View.VISIBLE);
            colorM.setVisibility(View.VISIBLE);
        }else{
            sizeM.setVisibility(View.GONE);
            colorM.setVisibility(View.GONE);
        }

        itemCodeCard = new ArrayList<>();
        itemCodeCard = getItemCard();


        Button barcode;
        barcode = dialogSearch.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarCode = true;
                openSearch = false;
                readBarCode(itemCodeSearch, 4);
            }
        });

        final ListAdapterSearch listAdapterSearch = new ListAdapterSearch(CollectingData.this, itemCodeCard,companyNo);
        tabeSearch.setAdapter(listAdapterSearch);

        final ArrayList<ItemCard> ItemCodeCardSearch = new ArrayList<>();
        for (int i = 0; i < itemCodeCard.size(); i++) {
//            insertRowSearch(itemCodeCard.get(i).getItemName(), itemCodeCard.get(i).getItemCode(), tabeSearch, dialogSearch, itemCodeText, swSearch);
            ItemCard itemCard = itemCodeCard.get(i);
            ItemCodeCardSearch.add(itemCard);

        }

        itemCodeSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String itemCodeReader = itemCodeSearch.getText().toString();
                ItemCodeCardSearch.clear();
                if (!itemCodeReader.equals("")) {
                    if (openSearch) {

                        for (int i = 0; i < itemCodeCard.size(); i++) {
                            if (itemCodeCard.get(i).getItemCode().toUpperCase().contains(itemCodeReader.toUpperCase()) || itemCodeCard.get(i).getItemName().toUpperCase().contains(itemCodeReader.toUpperCase())||itemCodeCard.get(i).getItemL().toUpperCase().contains(itemCodeReader.toUpperCase())) {
//                            insertRowSearch(finalItemCodeCard.get(i).getItemName(), finalItemCodeCard.get(i).getItemCode(), tabeSearch, dialogSearch, itemCodeText, swSearch);
                                ItemCard itemCard = itemCodeCard.get(i);
                                ItemCodeCardSearch.add(itemCard);
                            }
                            ListAdapterSearch listAdapterSearch = new ListAdapterSearch(CollectingData.this, ItemCodeCardSearch,companyNo);
                            tabeSearch.setAdapter(listAdapterSearch);
                        }
                    }
                } else {

                    ListAdapterSearch listAdapterSearch = new ListAdapterSearch(CollectingData.this, itemCodeCard,companyNo);
                    tabeSearch.setAdapter(listAdapterSearch);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tabeSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openSearch = false;
                switch (swSearch) {
                    case 0:
                        collDOpen = true;
                        break;
                    case 1:
                        collExOpen = true;
                        break;
                    case 2:
                        collReceipt = true;
                        break;
                    case 3:
                        openSave = true;
                        break;
                    case 4:
                        collTransfer = true;
                        break;
                    case 6:
                        openColUp = true;
                        break;
                }

//                Log.e("rowid,", "...." + "" + v.getId() + "----->" + text.getText().toString());

                try {
                    itemCodeText.setText(ItemCodeCardSearch.get(position).getItemCode());
                }catch (Exception e)
                {
                    Toast.makeText(CollectingData.this, "size error ", Toast.LENGTH_SHORT).show();
                }
                textId = 0;
                dialogSearch.dismiss();

            }
        });

        tabeSearch.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSearch.dismiss();
                openSearch = false;
                switch (swSearch) {
                    case 0:
                        collDOpen = true;
                        break;
                    case 1:
                        collExOpen = true;
                        break;
                    case 2:
                        collReceipt = true;
                        break;
                    case 3:
                        openSave = true;
                        break;
                    case 6:
                        openColUp = true;
                        break;
                }
                itemCodeText.requestFocus();
                itemCodeText.setText("");
                textId = 0;
            }
        });


        dialogSearch.show();

    }



    void SearchDialogForAssets(final EditText itemCodeText, final int swSearch) {
        final Dialog dialogSearch = new Dialog(CollectingData.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSearch.setCancelable(false);
        if(controll.isYellow){
            dialogSearch.setContentView(R.layout.search_dialog_yellow);
        }else{
            dialogSearch.setContentView(R.layout.search_dialog);
        }


        dialogSearch.setCanceledOnTouchOutside(false);


        TextView sizeM,colorM;
        sizeM=dialogSearch.findViewById(R.id.sizeM);
        colorM=dialogSearch.findViewById(R.id.colorM);

        if(companyNo==1){
            sizeM.setVisibility(View.VISIBLE);
            colorM.setVisibility(View.VISIBLE);
        }else{
            sizeM.setVisibility(View.GONE);
            colorM.setVisibility(View.GONE);
        }

        LinearLayout exit =  dialogSearch.findViewById(R.id.exitsearch);
        final EditText itemCodeSearch = (EditText) dialogSearch.findViewById(R.id.itemCodeSearch);
        final ListView tabeSearch = (ListView) dialogSearch.findViewById(R.id.tableSearch);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                itemCodeSearch.requestFocus();
            }
        });

        itemAssetsCa = new ArrayList<>();

        itemAssetsCa = InventDB.getAllAssesstItem();

        ListAdapterSearchAssets listAdapterSearch=new ListAdapterSearchAssets(CollectingData.this,itemAssetsCa,companyNo);
        tabeSearch.setAdapter(listAdapterSearch);
        final ArrayList<AssestItem> ItemCodeCardSearch=new ArrayList<>();
        for (int i = 0; i < itemAssetsCa.size(); i++) {
//            insertRowSearch(itemCodeCard.get(i).getItemName(), itemCodeCard.get(i).getItemCode(), tabeSearch, dialogSearch, itemCodeText, swSearch);
            AssestItem itemCard= itemAssetsCa.get(i);
            ItemCodeCardSearch.add(itemCard);

        }


        Button barcode;
        barcode = dialogSearch.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCode = true;
                openSearch = false;
                readBarCode(itemCodeSearch, 4);


            }
        });


        itemCodeSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String itemCodeReader = itemCodeSearch.getText().toString();
                ItemCodeCardSearch.clear();
                if (!itemCodeReader.equals("")) {
                    if (openSearch) {
                        for (int i = 0; i < itemAssetsCa.size(); i++) {
                            if (itemAssetsCa.get(i).getAssesstCode().contains(itemCodeReader) || itemAssetsCa.get(i).getAssesstName().contains(itemCodeReader)) {
//                            insertRowSearch(finalItemCodeCard.get(i).getItemName(), finalItemCodeCard.get(i).getItemCode(), tabeSearch, dialogSearch, itemCodeText, swSearch);
                                AssestItem itemCard= itemAssetsCa.get(i);
                                ItemCodeCardSearch.add(itemCard);
                            }
                        }
                        ListAdapterSearchAssets listAdapterSearch=new ListAdapterSearchAssets(CollectingData.this,ItemCodeCardSearch,companyNo);
                        tabeSearch.setAdapter(listAdapterSearch);
                    }
                }else{
                    ListAdapterSearchAssets listAdapterSearch=new ListAdapterSearchAssets(CollectingData.this,ItemCodeCardSearch,companyNo);
                    tabeSearch.setAdapter(listAdapterSearch);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tabeSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openSearch = false;
                switch (swSearch) {

                    case 5:
                        openAssesst = true;
                        break;

                }

//                Log.e("rowid,", "...." + "" + v.getId() + "----->" + text.getText().toString());

                itemCodeText.setText(ItemCodeCardSearch.get(position).getAssesstCode());


                switch (swSearch) {

                }
                textId = 0;
                dialogSearch.dismiss();

            }
        });

        tabeSearch.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSearch.dismiss();
                openSearch = false;
                switch (swSearch) {
                    case 5:
                    openAssesst=true;
                    break;

                }
                itemCodeText.requestFocus();
                itemCodeText.setText("");
                textId = 0;
            }
        });


        dialogSearch.show();

    }


    void insertRowSearch(String itemName, String itemCode, TableLayout recipeTable, final Dialog dialogFinsh, final EditText itemCodeText, final int swSearch) {


        row = new TableRow(CollectingData.this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        row.setPadding(0, 5, 0, 20);

        for (int i = 0; i < 2; i++) {
            final TextView textView = new TextView(CollectingData.this);
            switch (i) {
                case 0:
                    textView.setText(itemCode);
                    break;
                case 1:
                    textView.setText(itemName);
                    break;


            }
            textView.setTextColor(ContextCompat.getColor(CollectingData.this, R.color.textColor));
            textView.setGravity(Gravity.CENTER);

            textView.setId(Integer.parseInt(textId + "" + i));

            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            textView.setLayoutParams(lp2);

            row.addView(textView);


        }
        row.setId(textId);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TableRow rows = (TableRow) v.findViewById(v.getId());
                TextView text = (TextView) rows.getChildAt(0);
                openSearch = false;
                switch (swSearch) {
                    case 0:
                        collDOpen = true;
                        break;
                    case 1:
                        collExOpen = true;
                        break;
                    case 2:
                        collReceipt = true;
                        break;
                    case 3:
                        openSave = true;
                        break;
                }

                Log.e("rowid,", "...." + "" + v.getId() + "----->" + text.getText().toString());

                itemCodeText.setText(text.getText().toString());
                textId = 0;
                dialogFinsh.dismiss();
            }
        });


        recipeTable.addView(row);
        textId++;
    }


    public void Clickable() {

        collectData.setClickable(true);
        collectByORG.setClickable(true);
        transferData.setClickable(true);
        collectingByExpiry.setClickable(true);
        collectByReceipt.setClickable(true);
        UpdateQty.setClickable(true);

//        pSetting.setClickable(true);
//        changePassword.setClickable(true);
//        sendServer.setClickable(true);
//        receiveServer.setClickable(true);
//
//        newItem.setClickable(true);
//        importText.setClickable(true);
//        pBarcode.setClickable(true);
//        pShelfTag.setClickable(true);
//
//        exportText.setClickable(true);
//        ExportTransfer.setClickable(true);
//        exportExpDate.setClickable(true);
//        ExportAlternative.setClickable(true);
    }

    public void notClickable() {
        collectData.setClickable(false);
        collectByORG.setClickable(false);
        transferData.setClickable(false);
        collectingByExpiry.setClickable(false);
        collectByReceipt.setClickable(false);
        UpdateQty.setClickable(false);

//        pSetting.setClickable(false);
//        changePassword.setClickable(false);
//        sendServer.setClickable(false);
//        receiveServer.setClickable(false);
//
//        newItem.setClickable(false);
//        importText.setClickable(false);
//        pBarcode.setClickable(false);
//        pShelfTag.setClickable(false);
//
//        exportText.setClickable(false);
//        ExportTransfer.setClickable(false);
//        exportExpDate.setClickable(false);
//        ExportAlternative.setClickable(false);
    }

    void alertMessageDialog(String title, String message, final int swith, final String itemName, final String ItemCode) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(CollectingData.this);
//        dialog.setTitle(title)
//                .setMessage(message)
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialoginterface, int i) {
//                        dialoginterface.cancel();
//                    }
//                })
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialoginterface, int i) {
//                        switch (swith) {
//                            case 0:
//                                finish();
//                                moveTaskToBack(true);
//
//                                break;
//                            case 1:
//                                InventDB.deleteAllItem("ITEM_CARD");
//                                Toast.makeText(CollectingData.this, "All Item Delete ", Toast.LENGTH_SHORT).show();
////                             progressDialog();
//                                break;
//                            case 2:
//                                InventDB.delete(ItemCode, itemName);
//                                break;
//
//                        }
//                    }
//                }).show();


        new SweetAlertDialog(CollectingData.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(CollectingData.this.getResources().getString(R.string.areYouSurre) + title)
                .setContentText(message)
                .setConfirmText(title + "!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
//
                        switch (swith) {
                            case 0:
                                finish();
                                moveTaskToBack(true);
                                sDialog.dismissWithAnimation();


                                break;
                            case 1:
                                InventDB.deleteAllItem("ITEM_CARD");

                                Toast.makeText(CollectingData.this, CollectingData.this.getResources().getString(R.string.allItemDelete), Toast.LENGTH_SHORT).show();
//                             progressDialog();
                                sDialog.dismissWithAnimation();

                                break;
                            case 2:
                                InventDB.delete(ItemCode, itemName);
                                sDialog.dismissWithAnimation();

                                break;

                        }


                    }
                })
                .setCancelButton(CollectingData.this.getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();


    }

//    public void visibilityLayout(RelativeLayout ins) {
//        main.setVisibility(View.VISIBLE);
//        mainR.setVisibility(View.VISIBLE);
//        secondR.setVisibility(View.INVISIBLE);
//        collectR.setVisibility(View.INVISIBLE);
//        itemR.setVisibility(View.INVISIBLE);
//        reportR.setVisibility(View.INVISIBLE);
//        toolsR.setVisibility(View.INVISIBLE);
//
//        ins.setVisibility(View.VISIBLE);
//
//    }


    SweetAlertDialog TostMesage(String message) {

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

        return sd2;

    }

    void progressDialog() {

        final ProgressDialog progressDialog = new ProgressDialog(CollectingData.this);
        progressDialog.setMessage("Saving Success ..."); // Setting Message
        progressDialog.setTitle("Saving ..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog

        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();

//        final SweetAlertDialog pDialog = new SweetAlertDialog(CollectingData.this, SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#D5FDD835"));
//        pDialog.setTitleText("Saving ...");
//        pDialog.setCancelable(true);
//        pDialog.show();
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(50);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                pDialog.cancel();
//            }
//        });

    }

    void prograseSave() {

        final int[] i = {0};
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading");
        pDialog.show();
        pDialog.setCancelable(true);
        new CountDownTimer(150 * 7, 150) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i[0]++;
                switch (i[0]) {
                    case 0:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blueDark));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.cancel_button));
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.gray));
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blueLight));
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.gray));
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.searchButton));
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.layou3));
                        break;
                }
            }

            public void onFinish() {
                i[0] = -1;
                pDialog.setTitleText("Success!")
                        .setConfirmText("OK")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        }.start();

    }


    void TimeDialog(final TextView itemExpDate) {

        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(CollectingData.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {

                selectedMonth = selectedMonth + 1;
                itemExpDate.setText(convertToEnglish("" + selectedDay + "/" + selectedMonth + "/" + selectedYear));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    //    void addDialog(final TableLayout recipeTable) {
//        final Dialog dialogAdd = new Dialog(CollectingData.this);
//        dialogAdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogAdd.setCancelable(false);
//        dialogAdd.setContentView(R.layout.add_dialog);
//        dialogAdd.setCanceledOnTouchOutside(false);
//
//        Button done, exit, delete;
//        final EditText message = (EditText) dialogAdd.findViewById(R.id.Message);
//        done = (Button) dialogAdd.findViewById(R.id.don2Add);
//        exit = (Button) dialogAdd.findViewById(R.id.exitAdd);
//
//        done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String messsage = message.getText().toString();
//
//                row = new TableRow(CollectingData.this);
//                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
//                row.setLayoutParams(lp);
//                row.setPadding(0, 5, 0, 20);
//
//                final CheckBox textView = new CheckBox(CollectingData.this);
//                textView.setTextColor(ContextCompat.getColor(CollectingData.this, R.color.textColor));
//                TableRow.LayoutParams lp2 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//
//                textView.setLayoutParams(lp2);
//                textView.setText(messsage);
//                row.addView(textView);
//                recipeTable.addView(row);
//                //
//                dialogAdd.dismiss();
//
//
//            }
//        });
//        exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogAdd.dismiss();
//            }
//        });
//
//        dialogAdd.show();
//
//    }


    public String findSwitch(String Item) {

        String itemOCode = InventDB.getItemSwitch(Item);

        return itemOCode;
    }
    public List<OfferTable> findOffer(String Item) {


        UserDaoOffer userDao = db.itemOffer();
        List<OfferTable> itemOCode =   userDao.getOfferByCode(Item);
        List<OfferTable> itemResult=new ArrayList<>();
        for(int i=0;i<itemOCode.size();i++){
            if((formatDate(convertToEnglish(today)).after(formatDate(itemOCode.get(i).getFRMDATE() ))
                    &&formatDate(convertToEnglish(today)).before(formatDate(itemOCode.get(i).getTODATE() )))||
                    (formatDate(convertToEnglish(today)).equals(formatDate(itemOCode.get(i).getFRMDATE() )))||
                    formatDate(convertToEnglish(today)).equals(formatDate(itemOCode.get(i).getTODATE() ))){
                OfferTable offerTable=new OfferTable();
                offerTable=itemOCode.get(i);
                itemResult.add(offerTable);
                break;
            }

        }

        return itemResult;
    }



    public Date formatDate(String date) {

//        Log.e("date", date);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        Date d = null;
        try {
            d = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }


    public List<ItemQR> findQRCode(String ItemQR, String strNo, boolean eqQR) {
        List<ItemQR> itemOCode = new ArrayList<>();
        if (!eqQR) {
            itemOCode = InventDB.getAllQRItem(ItemQR, strNo);
        } else {
            itemOCode = InventDB.getAllQRItemEqQr(ItemQR, strNo);
        }


        return itemOCode;
    }

    public String findQRCodeSale(String ItemCode, String strNo) {

        String Sales = InventDB.getSalesQRItem(ItemCode, strNo);

        return Sales;
    }

    public List<String> findUnite(String Item) {

        List<String> itemOCode = InventDB.getItemUnite(Item);

        return itemOCode;
    }

    public List<AssestItem> findAssest(String Item) {

        List<AssestItem> itemOCode = InventDB.getItemAssets(Item);

        return itemOCode;
    }

    public void readBarCode(TextView itemCodeText, int swBarcode) {

        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(CollectingData.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();

        openBarCode = false;
        switch (swBarcode) {
            case 0:
                collDOpen = true;
                break;
            case 1:
                collExOpen = true;
                break;
            case 2:
                collReceipt = true;
                break;
            case 3:
                openSave = true;
                break;
            case 4:
                openSearch = true;
                break;

            case 5:
                updateOpen = true;
                break;

            case 6:
                collTransfer = true;
                break;
            case 7:
                openColUp = true;
                break;
            case 8:
                collAssetsOpen = true;
                break;

            case 9:
                collAddAssetsOpen = true;
                break;
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Log.d("MainActivity", "cancelled scan");
//                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
                TostMesage(getResources().getString(R.string.cancel));
            } else {
                Log.d("MainActivity", "Scanned");
//                Toast.makeText(this, getString(R.string.scan) + Result.getContents(), Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
                barCodTextTemp.setText(Result.getContents() + "");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    boolean LocationDialog() {
        final boolean[] isLocation = {false};
        final boolean[] onClicke = {true};

        final EditText editText = new EditText(CollectingData.this);
        Spinner spinnerLocation=new Spinner(CollectingData.this);
//        final TextView textView = new TextView(CollectingData.this);
        editText.setHint(CollectingData.this.getResources().getString(R.string.enter_location));
        editText.setTextColor(Color.BLACK);
//        textView.setTextColor(Color.RED);
        if (SweetAlertDialog.DARK_STYLE) {
            editText.setTextColor(Color.BLACK);
        }
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(editText);
        linearLayout.addView(spinnerLocation);
//        linearLayout.addView(textView);

        final List<String>locationList=InventDB.getAllLocation();
        //locationList.add(0,"All");
        ArrayAdapter MangAdapter = new ArrayAdapter<String>(CollectingData.this, R.layout.spinner_style, locationList);
        spinnerLocation.setAdapter(MangAdapter);

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                editText.setText(""+locationList.get(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                editText.setText("");
            }
        });

        final SweetAlertDialog dialog = new SweetAlertDialog(CollectingData.this, SweetAlertDialog.NORMAL_TYPE);
        dialog.setTitleText(CollectingData.this.getResources().getString(R.string.location));
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                String LocationEditeStr = editText.getText().toString();
//                        textView.setText("");
                if (!LocationEditeStr.equals("")) {
                    if(onClicke[0]) {
                        onClicke[0] = false;

                        dialog.dismissWithAnimation();
                        LocationEdite = LocationEditeStr;

                        collectData.setClickable(false);
                        collDOpen = true;
                        showCollectDataDialog();
                        isLocation[0] = true;
                    }

                } else {
                    Toast.makeText(CollectingData.this, "Please Enter Location ", Toast.LENGTH_SHORT).show();
                }

            }
        });

//                        .hideConfirmButton();

        dialog.setCustomView(linearLayout);
        dialog.show();

        return isLocation[0];
    }

    void PasswordDialog(final EditText salePrice, final TextView master) {

        final EditText editText = new EditText(CollectingData.this);
        final TextView textView = new TextView(CollectingData.this);
        editText.setHint("Enter Password");
        editText.setTextColor(Color.BLACK);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        textView.setTextColor(Color.RED);
        if (SweetAlertDialog.DARK_STYLE) {
            editText.setTextColor(Color.BLACK);
        }
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(editText);
        linearLayout.addView(textView);

        final SweetAlertDialog dialog = new SweetAlertDialog(CollectingData.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Password")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        String password = editText.getText().toString();
                        textView.setText("");
                        if (!password.equals("")) {
                            if (password.equals("882020")) {

                                textView.setText("");
                                salePrice.setEnabled(true);
                                sweetAlertDialog.dismissWithAnimation();

                            } else {
                                textView.setText(getResources().getString(R.string.NotCorrectPassword));
                                salePrice.setEnabled(false);
                            }

                        }

                    }
                })
                .setCancelButton(CollectingData.this.getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {

                                master.requestFocus();

                            }
                        });
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
//                        .hideConfirmButton();

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCustomView(linearLayout);
        dialog.show();

    }


    void initialization() {

        exitAll2 = (Button) findViewById(R.id.exit2);
        item = (Button) findViewById(R.id.items);
        setting = (Button) findViewById(R.id.setting);
        report = (Button) findViewById(R.id.report);

        collectData = (LinearLayout) findViewById(R.id.collData);
        collectByORG = (LinearLayout) findViewById(R.id.collByorg);
        transferData = (LinearLayout) findViewById(R.id.transfer);
        collectingByExpiry = (LinearLayout) findViewById(R.id.collByExp);
        collectByReceipt = (LinearLayout) findViewById(R.id.collByRecep);
        UpdateQty = (LinearLayout) findViewById(R.id.UpdateQty);
        home = (TextView) findViewById(R.id.home);
        itemAssest = findViewById(R.id.itemAssest);
        TransferPhar=findViewById(R.id.TransferPhar);
        addAssest = findViewById(R.id.addAssest);

        collectByORG.setVisibility(View.GONE);
        collectingByExpiry.setVisibility(View.GONE);
        collectByReceipt.setVisibility(View.GONE);

        collectData.setOnClickListener(showDialogOnClick);
        UpdateQty.setOnClickListener(showDialogOnClick);
        itemAssest.setOnClickListener(showDialogOnClick);
        addAssest.setOnClickListener(showDialogOnClick);
//        collectByORG.setOnClickListener(showDialogOnClick);
        transferData.setOnClickListener(showDialogOnClick);
        TransferPhar.setOnClickListener(showDialogOnClick);
//        collectingByExpiry.setOnClickListener(showDialogOnClick);
//        collectByReceipt.setOnClickListener(showDialogOnClick);

        Clickable();

    }

    private BoomMenuButton initBmb(int res) {
        BoomMenuButton bmb = (BoomMenuButton) findViewById(res);
        assert bmb != null;
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder());

        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {

                switch (index) {
                    case 0:
//                        Intent coll = new Intent(CollectingData.this, CollectingData.class);
//                        startActivity(coll);
                        break;
                    case 1:
                        Intent item = new Intent(CollectingData.this, Item.class);
                        startActivity(item);

                        break;
                    case 2:
                        Intent report = new Intent(CollectingData.this, Report.class);
                        startActivity(report);
                        break;
                    case 3:
                        Intent tools = new Intent(CollectingData.this, Tools.class);
                        startActivity(tools);
                        break;
                    case 4:
                        Toast.makeText(CollectingData.this, "index" + index, Toast.LENGTH_SHORT).show();

                        break;
                    case 5:
                        alertMessageDialog(CollectingData.this.getResources().getString(R.string.exitfromapp), CollectingData.this.getResources().getString(R.string.exitMessage), 0, "", "");
                        break;

                }


            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });
        return bmb;
    }

    public JSONArray getJSONObjectUpdate(String itemCode, String storeNo, String itemQty, String Kind) { // for server

//        {"JRD":{"ITEMCODE":1001500,STORENO:1,ITEMQTY:101,KIND:500}]}

        JSONArray objArray = new JSONArray();

        JSONObject obj = new JSONObject();
        try {
            obj.put("ITEMCODE", itemCode);
            obj.put("STORENO", storeNo);
            obj.put("ITEMQTY", itemQty);
            obj.put("KIND", Kind);

        } catch (JSONException e) {
            Log.e("Tag", "JSONException");
        }
        return objArray.put(obj);
    }
//    public void dd(ItemCard list){
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "InventoryDBase") .fallbackToDestructiveMigration().allowMainThreadQueries().build();
//
//        UserDaoCard userDao = db.itemCard();
//
//        userDao.insertAll(Collections.singletonList(list));
//
//
//
//    }


    public List<ItemCard> getItemCard(){

        // UserDaoCard userDao = db.itemCard();

        return  db.itemCard().getAll();


    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("databaseNo.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}
