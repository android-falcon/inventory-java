package com.example.user54.InventoryApp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
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
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.user54.InventoryApp.Model.AssestItem;
import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemQR;
import com.example.user54.InventoryApp.Model.ItemQty;
import com.example.user54.InventoryApp.Model.MainSetting;
import com.example.user54.InventoryApp.Model.OfferTable;
import com.example.user54.InventoryApp.Model.OnlineItems;
import com.example.user54.InventoryApp.Model.UnitName;
import com.example.user54.InventoryApp.ROOM.AppDatabase;
import com.example.user54.InventoryApp.ROOM.UserDaoCard;
import com.example.user54.InventoryApp.ROOM.UserDaoOffer;
import com.example.user54.InventoryApp.adapters.Cash_detail_adapter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;

public class Item extends AppCompatActivity {
    CheckBox SalesPriceCheckBoxTag;
    public static String  OnlineItems="0";
    Spinner uniteSpinner;
    RecyclerView dis_detail_recycle;
    private static final int READ_REQUEST_CODE = 42;
    public static int PrintDateFlag  =0;
    public static List<ItemQty> listItemUnitQty=new ArrayList<>();
     EditText BarcodetextView;
     TextView itemNamePrintBarcode,barcodeTextBarcode,pricePrintBarcode,textView41_;
     ImageView barcodeShelfPrintParcode;
    ImageView barcodeShelfPrint,barcodeShelfPrint2,barcodeShelfPrint3,barcodeShelfPrint4;
     TextView ItemNameEditTextTag,PriceEditTextTag,countText,ExpEditTextTag,itemNamePrint,ItemUnitEditTextTag,startEditTextTag,
             pricePrint,pricePrint4,exp,itemNamePrint2,pricePrint2,exp2,itemText,itemNamePrint4,itemNamePrint3,
             pricePrint3,printEditTextTag,salesPriceEditTextTag;
    ListView list;
     ListAdapter adapter;
    ListView listAssest;
    ListAdapterAssesst adapterAssest;
    boolean updateOpen = false, openSearch = false, openSave = false, openShelfTag =false,openBarcode = false,openPrice=false,openAssesst=false,openUpdateQty=false,openCost=false;
    int textId = 0;
    String today;
    InventoryDatabase InventDB;
    List<ItemCard> itemCardsList = new ArrayList<ItemCard>();
    Dialog dialog;
    Animation animFadein;
    String uniteNames="";
    TextView Home;
    DecimalFormat numberFormat = new DecimalFormat("0.000");
    //DecimalFormat numberFormat = new DecimalFormat("0.##");
    AppDatabase db ;
    List<MainSetting>mainSettingsList;
    public static ImageView shelfTagLinerTests;
    int settingCOName=0;
    public static TextView textView,textItemName,textQty,textViewFd;
    public static ItemCard itemCardForPrint;
    public static AssestItem itemAssesstForPrint;
    TextView pathNameFr,tempText;
    TableRow row;
    public static List<ItemCard> barcodeListForPrint;
    public static List<AssestItem> barcodeListForPrintAssest;
    LinearLayout newItem, importText, pBarcode, pShelfTag ,ItemPrice,ItemCost,Assesst,CaptionLayout;
    public static List<ItemCard> itemList;
    public static List<AssestItem> itemListAssest;
    List<ItemCard> itemCodeCard ;
    public static  TextView  texetrespone;
    ArrayList<AssestItem> itemAssetsCa ;
    public static EditText ItemCodeEditTextTag,prinS,prinSa;
    int companyNo=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.item_menu2_new);
        if(controll.isYellow){
            setContentView(R.layout.item_menu2_yellow);
        }else{
            setContentView(R.layout.item_menu2);
        }

        controll co=new controll();
        int data= Integer.parseInt(co.readFromFile(Item.this));
        InventDB = new InventoryDatabase(Item.this,data);
        db=AppDatabase.getInstanceDatabase(Item.this);

        initialization();



        animFadein = AnimationUtils.loadAnimation(Item.this, R.anim.fade_in);
        newItem.startAnimation(animFadein);
        pBarcode.startAnimation(animFadein);
        pShelfTag.startAnimation(animFadein);
        ItemPrice.startAnimation(animFadein);
        Assesst.startAnimation(animFadein);
        ItemCost.startAnimation(animFadein);
        CaptionLayout.startAnimation(animFadein);

        barcodeListForPrint=new ArrayList<>();
        barcodeListForPrintAssest=new ArrayList<>();
        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);

        final List<MainSetting> mainSetting = InventDB.getAllMainSetting();
        if (mainSetting.size() != 0) {
            companyNo=mainSetting.get(0).getCoName();
        }

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
TextView barCodTextTemp;
    String StkNo="",isOnlinePrice="0";

    boolean openBarCodeRedar =false;

//


    View.OnClickListener showDialogOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//
                case R.id.shelf:
                    pShelfTag.setClickable(false);
                    openShelfTag=true;
                    showPrintShelfTagDialog();
                    break;
                case R.id.newItem:
                    newItem.setClickable(false);
                    openSave = true;
                    showNewItemDialog(0);
                    break;
//                case R.id.impText:
//                    showImportFromTextDialog();
//                    break;
                case R.id.barcode:
                    pBarcode.setClickable(false);
                    openBarcode=true;
                    showPrintBarcodeDialog();
                    break;

                case R.id.ItemPrice:
                    ItemPrice.setClickable(false);
                    openPrice=true;
                    showItemPriceDialog();
                    break;
                case R.id.ItemCost:
                    ItemCost.setClickable(false);
                    openCost=true;
                    showItemCostDialog();
                    break;
                case R.id.Assesst:
                    Assesst.setClickable(false);
                    openAssesst=true;
                    showPrintAssesstDialog();
                    break;

                case R.id.CaptionLayout:
                    //CaptionLayout.setClickable(false);
                    Intent r=new Intent(Item.this, com.example.user54.InventoryApp.CaptionLayout.class);
                    startActivity(r);
                    break;
//

            }
        }
    };


    void showImportFromTextDialog() {
        dialog = new Dialog(Item.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.import_iteams_from_text_file);
        dialog.setCanceledOnTouchOutside(false);

        Button exit,open ;
        TextView pathName;

        exit = (Button) dialog.findViewById(R.id.exit);
        open = (Button) dialog.findViewById(R.id.openPath);

        pathName=(TextView)dialog.findViewById(R.id.path1);
        pathNameFr=pathName;
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openFolder();


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

    @SuppressLint("ClickableViewAccessibility")
    void showPrintBarcodeDialog() {
       final Dialog dialogBarCode = new Dialog(Item.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialogBarCode.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBarCode.setCancelable(false);
        if(controll.isYellow){
            dialogBarCode.setContentView(R.layout.print_item_barcode_yellow);
        }else{
            dialogBarCode.setContentView(R.layout.print_item_barcode);
        }
//


        dialogBarCode.setCanceledOnTouchOutside(false);

        final int[] count1 = {1};

        LinearLayout exit,PrintButton;
        Button searchButton;

        ImageButton upBarcode,downBarcode;
        final CheckBox PrintAllcheckBox,PriceCheckBox,nameCheckBoxTag;
        final TextView countTBarcode;
//        final TableLayout listOfItemPrint;

        final LinearLayout priceLinerPrint;
         list=dialogBarCode.findViewById(R.id.list);

        priceLinerPrint=(LinearLayout) dialogBarCode.findViewById(R.id.priceLinerPrint);
        searchButton= (Button) dialogBarCode.findViewById(R.id.searchButton);
        PrintButton=  dialogBarCode.findViewById(R.id.PrintButton);
        exit =  dialogBarCode.findViewById(R.id.exit);
        nameCheckBoxTag=dialogBarCode.findViewById(R.id.nameCheckBoxTag);

        upBarcode= (ImageButton) dialogBarCode.findViewById(R.id.upBarcode);
        downBarcode= (ImageButton) dialogBarCode.findViewById(R.id.downBarcode);

        PrintAllcheckBox= (CheckBox) dialogBarCode.findViewById(R.id.PrintAllcheckBox);
        PriceCheckBox= (CheckBox) dialogBarCode.findViewById(R.id.PriceCheckBox);

        countTBarcode= (TextView) dialogBarCode.findViewById(R.id.countTBarcode);
        itemNamePrintBarcode= (TextView) dialogBarCode.findViewById(R.id.itemNamePrintBarcode);
        barcodeTextBarcode= (TextView) dialogBarCode.findViewById(R.id.barcodeTextBarcode);
        pricePrintBarcode= (TextView) dialogBarCode.findViewById(R.id.pricePrintBarcode);

//        listOfItemPrint= (TableLayout) dialogBarCode.findViewById(R.id.listOfItemPrint);

        BarcodetextView= (EditText) dialogBarCode.findViewById(R.id.BarcodetextView);
        barcodeShelfPrintParcode= (ImageView) dialogBarCode.findViewById(R.id.barcodeShelfPrintParcode);


        itemList=new ArrayList<>();
         itemList=getItemCard();
        for(int i=0;i<itemList.size();i++){
//            fillTableRows(listOfItemPrint,itemList.get(i).getItemCode(),itemList.get(i).getItemName(),itemList.get(i).getOrgPrice(),i);
            itemList.get(i).setCheck(false);
//            ItemCard itemCard=itemList.get(i);
//            barcodeListForPrint.add(itemCard);
        }

        adapter = new ListAdapter(Item.this, itemList);
        list.setAdapter(adapter);
        BarcodetextView.requestFocus();
        BarcodetextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {


                    searchEventBarcode();

//                    String itemSwitch="";
//                   String itemCode=BarcodetextView.getText().toString();
//                   boolean isFound=false;
//                    if(!itemCode.equals("")& openBarcode){
//
//                        if(settingCOName==1){
//                            itemCode=BarcodetextView.getText().toString();
//                            try {
//                                itemCode = itemCode.substring(0, 12);
//                            }catch (Exception e){
//                                itemCode=BarcodetextView.getText().toString();
//                            }
//                            Log.e("itemCode1_",""+itemCode);
//                        }else{
//                            itemCode=BarcodetextView.getText().toString();
//                        }
//                        Log.e("itemCode2_",""+itemCode);
//
//
//                        List<String>itemUnite=findUnite(itemCode);
//                        int uQty=1;
//
//                        if(itemUnite.size()!=0){
//
//                            itemCode=itemUnite.get(0);
//                            uQty=Integer.parseInt(itemUnite.get(2));
//
//                        }else{
//                            itemSwitch=findSwitch(itemCode);
//                            if(!itemSwitch.equals("")){
//                                itemCode=itemSwitch;
//                            }
//                            uQty=1;
//
//                        }
//
//                        for(int i = 0; i< itemList.size(); i++){
//                            if(itemCode.equals(itemList.get(i).getItemCode())){
//                                itemNamePrintBarcode.setText(itemList.get(i).getItemName());
//                                barcodeTextBarcode .setText(itemList.get(i).getItemCode());
//                                pricePrintBarcode.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemList.get(i).getFDPRC()))));
////                            ItemCard itemCard=finalItemList.get(i);
////                            barcodeListForPrint.add(itemCard);
//
//                                isFound=true;
//                                itemList.get(i).setCheck(true);
//                                adapter.notifyDataSetChanged();
//                                list.setSelection(i);
//
//                                Bitmap bitmap= null;
//                                try {
//                                    bitmap = encodeAsBitmap(itemCode, BarcodeFormat.CODE_128, 350, 100);
//                                    barcodeShelfPrintParcode.setImageBitmap(bitmap);
//                                } catch (WriterException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                                break;
//                            }else{
//
//                                itemNamePrintBarcode.setText("");
//                                barcodeTextBarcode .setText("");
//                                pricePrintBarcode.setText("");
//                            }
//                        }
//
//
//                        if(isFound){
//                            //noThing
//                        }else{
//                            showAlertDialog(getResources().getString(R.string.itemNotFoundAlert));
//                            BarcodetextView .setText("");
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    BarcodetextView.requestFocus();
//                                }
//                            });
//
//
//                        }
//
//
//
//                    }


                }
                return false;
            }});


//        BarcodetextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                itemNamePrintBarcode.setText("Item Name");
//                barcodeTextBarcode .setText("00000000000");
//                pricePrintBarcode.setText("0.0");
//                barcodeListForPrint.clear();
////                finalItemList = itemList;
//                barcodeShelfPrintParcode.setImageDrawable(getResources().getDrawable(R.drawable.barcodes));
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//
//                if(!BarcodetextView.getText().toString().equals("")& openBarcode){
//
//                    for(int i = 0; i< itemList.size(); i++){
//                        if(BarcodetextView.getText().toString().equals(itemList.get(i).getItemCode())){
//                            itemNamePrintBarcode.setText(itemList.get(i).getItemName());
//                            barcodeTextBarcode .setText(itemList.get(i).getItemCode());
//                            pricePrintBarcode.setText(itemList.get(i).getSalePrc());
////                            ItemCard itemCard=finalItemList.get(i);
////                            barcodeListForPrint.add(itemCard);
//
//                            itemList.get(i).setCheck(true);
//                            adapter.notifyDataSetChanged();
//                            list.setSelection(i);
//
//                            Bitmap bitmap= null;
//                            try {
//                                bitmap = encodeAsBitmap(BarcodetextView.getText().toString(), BarcodeFormat.CODE_128, 350, 100);
//                                barcodeShelfPrintParcode.setImageBitmap(bitmap);
//                            } catch (WriterException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            break;
//                        }
//                    }
//
//
//
//                }
//
////
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        Button barcode;
        barcode = dialogBarCode.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCodeRedar = true;
                openBarcode = false;
                readBarCode(BarcodetextView,1);
            }
        });

        PrintAllcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PrintAllcheckBox.isChecked()){

                    for (int i = 0; i < itemList.size(); i++) {
                        itemList.get(i).setCheck(true);
                    }

                    ListAdapter adapter = new ListAdapter(Item.this, itemList);
                    list.setAdapter(adapter);

                }else{
                    for (int i = 0; i < itemList.size(); i++) {
                        itemList.get(i).setCheck(false);

                    }

                     ListAdapter adapter = new ListAdapter(Item.this, itemList);
                    list.setAdapter(adapter);

                }
            }
        });

        PriceCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PriceCheckBox.isChecked()){
                    priceLinerPrint.setVisibility(View.VISIBLE);
                }else{
                    priceLinerPrint.setVisibility(View.INVISIBLE);
                }
            }
        });

        nameCheckBoxTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameCheckBoxTag.isChecked()){
                    itemNamePrintBarcode.setVisibility(View.VISIBLE);
                }else{
                    itemNamePrintBarcode.setVisibility(View.INVISIBLE);
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pBarcode.setClickable(true);
                dialogBarCode.dismiss();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                openBarcode = false;
                SearchDialog(BarcodetextView, 1);
                BarcodetextView.setSelectAllOnFocus(true);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        BarcodetextView.requestFocus();

                    }
                });
            }
        });

        upBarcode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                count1[0]++;
                countTBarcode.setText("" + count1[0]);
            }
        });
        downBarcode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(countTBarcode.getText().toString()) != 1) {
                    count1[0]--;
                    countTBarcode.setText("" + count1[0]);
                } else
//                    Toast.makeText(Item.this, "Copy Not Less Than 1", Toast.LENGTH_SHORT).show();
                TostMesage(getResources().getString(R.string.notless));
            }
        });


//        parentScrollView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                Log.v(TAG, "PARENT TOUCH");
//
//                findViewById(R.id.child_scroll).getParent()
//                        .requestDisallowInterceptTouchEvent(false);
//                return false;
//            }
//        });
//
//
        list.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        PrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BarcodetextView.getText().toString().equals("")){
                    itemCardForPrint = new ItemCard();
                itemCardForPrint.setCostPrc(countTBarcode.getText().toString());
                if (PriceCheckBox.isChecked()) {
                    itemCardForPrint.setOrgPrice("");
                } else {
                    itemCardForPrint.setOrgPrice("**");
                }

                    if (nameCheckBoxTag.isChecked()) {
                        itemCardForPrint.setIsName("");
                    } else {
                        itemCardForPrint.setIsName("**");
                    }

                barcodeListForPrint.clear();
                for (int i = 0; i < itemList.size(); i++) {

                    if (itemList.get(i).getCheck()) {
                        ItemCard itemCard = itemList.get(i);
                        barcodeListForPrint.add(itemCard);
                    }

                }


//                    boolean Permission= isStoragePermissionGranted();
//                    if(Permission) {
                        Intent intentBarcode = new Intent(Item.this, BluetoothConnectMenu.class);
                        intentBarcode.putExtra("printKey", "1");
                        startActivity(intentBarcode);
//                    }else{
//                        TostMesage(getResources().getString(R.string.Permission));
//
//                    }



            }else{
                    TostMesage(getResources().getString(R.string.insertData));
            }

//                openBarcode = true;

            }
        });


        dialogBarCode.show();
    }

    void searchEventBarcode(){
        String itemSwitch="";
        String itemCode=BarcodetextView.getText().toString();
        boolean isFound=false;
        if(!itemCode.equals("")& openBarcode) {

            if (settingCOName == 1) {
                itemCode = BarcodetextView.getText().toString();
                try {
                    itemCode = itemCode.substring(0, 12);
                } catch (Exception e) {
                    itemCode = BarcodetextView.getText().toString();
                }
                Log.e("itemCode1_", "" + itemCode);
            } else {
                itemCode = BarcodetextView.getText().toString();
            }
            Log.e("itemCode2_", "" + itemCode);

            boolean offer=false;
            String codeFromText="";

            String price="";
            codeFromText=itemCode;
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


            List<OfferTable> offerTables=findOffer(itemCode);
            List<OfferTable> offerTablesO=findOffer(codeFromText);

            if(offerTablesO.size()!=0){

                offer=true;
//                itemCode = offerTables.get(0).getITEMOCODE();
                price= convertToEnglish(numberFormat.format(Double.parseDouble(offerTablesO.get(0).getF_D())));


            }else
            if (offerTables.size()!=0) {
                offer=true;
//                itemCode = offerTables.get(0).getITEMOCODE();
                price= convertToEnglish(numberFormat.format(Double.parseDouble(offerTables.get(0).getF_D())));

            }


            for(int i = 0; i< itemList.size(); i++){
                if(itemCode.equals(itemList.get(i).getItemCode())){
                    itemNamePrintBarcode.setText(itemList.get(i).getItemName());
                    barcodeTextBarcode .setText(itemList.get(i).getItemCode());
                    if(offer){
                        pricePrintBarcode.setText(convertToEnglish(numberFormat.format(Double.parseDouble(price))));

                    }else {
                        pricePrintBarcode.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemList.get(i).getFDPRC()))));

                    }
//                            ItemCard itemCard=finalItemList.get(i);
//                            barcodeListForPrint.add(itemCard);

                    isFound=true;
                    itemList.get(i).setCheck(true);
                    adapter.notifyDataSetChanged();
                    list.setSelection(i);

                    Bitmap bitmap= null;
                    try {
                        bitmap = encodeAsBitmap(itemCode, BarcodeFormat.CODE_128, 350, 100);
                        barcodeShelfPrintParcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }


                    break;
                }else{

                    itemNamePrintBarcode.setText("");
                    barcodeTextBarcode .setText("");
                    pricePrintBarcode.setText("");
                }
            }


            if(isFound){
                //noThing
            }else{
                showAlertDialog(getResources().getString(R.string.itemNotFoundAlert));
                BarcodetextView .setText("");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        BarcodetextView.requestFocus();
                    }
                });


            }



        }
    }

    @SuppressLint("ClickableViewAccessibility")
    void showPrintAssesstDialog() {
        final Dialog dialogBarCodeAssesst = new Dialog(Item.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogBarCodeAssesst.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBarCodeAssesst.setCancelable(false);
        if(controll.isYellow){
            dialogBarCodeAssesst.setContentView(R.layout.print_assesst_item_yellow);
        }else{
            dialogBarCodeAssesst.setContentView(R.layout.print_assesst_item_yellow);
        }
//


        dialogBarCodeAssesst.setCanceledOnTouchOutside(false);

        final int[] count1 = {1};

        LinearLayout exit,PrintButton;
        Button searchButton;

        ImageButton upBarcode,downBarcode;
        final CheckBox PrintAllcheckBox,PriceCheckBox;
        final TextView countTBarcode;
//        final TableLayout listOfItemPrint;
//        final EditText BarcodetextView;
//        final ImageView barcodeShelfPrintParcode;
        final LinearLayout priceLinerPrint;
      listAssest=dialogBarCodeAssesst.findViewById(R.id.list);

        priceLinerPrint=(LinearLayout) dialogBarCodeAssesst.findViewById(R.id.priceLinerPrint);
        searchButton= (Button) dialogBarCodeAssesst.findViewById(R.id.searchButton);
        PrintButton=  dialogBarCodeAssesst.findViewById(R.id.PrintButton);
        exit =  dialogBarCodeAssesst.findViewById(R.id.exit);

        upBarcode= (ImageButton) dialogBarCodeAssesst.findViewById(R.id.upBarcode);
        downBarcode= (ImageButton) dialogBarCodeAssesst.findViewById(R.id.downBarcode);

        PrintAllcheckBox= (CheckBox) dialogBarCodeAssesst.findViewById(R.id.PrintAllcheckBox);
        PriceCheckBox= (CheckBox) dialogBarCodeAssesst.findViewById(R.id.PriceCheckBox);

        countTBarcode= (TextView) dialogBarCodeAssesst.findViewById(R.id.countTBarcode);
        itemNamePrintBarcode= (TextView) dialogBarCodeAssesst.findViewById(R.id.itemNamePrintBarcode);
        barcodeTextBarcode= (TextView) dialogBarCodeAssesst.findViewById(R.id.barcodeTextBarcode);
        pricePrintBarcode= (TextView) dialogBarCodeAssesst.findViewById(R.id.pricePrintBarcode);

//        listOfItemPrint= (TableLayout) dialogBarCode.findViewById(R.id.listOfItemPrint);

        BarcodetextView= (EditText) dialogBarCodeAssesst.findViewById(R.id.BarcodetextView);
        barcodeShelfPrintParcode= (ImageView) dialogBarCodeAssesst.findViewById(R.id.barcodeShelfPrintParcode);

        priceLinerPrint.setVisibility(View.INVISIBLE);
        PriceCheckBox.setVisibility(View.INVISIBLE);

        itemListAssest=new ArrayList<>();
        itemListAssest=InventDB.getAllAssesstItem();
        for(int i=0;i<itemListAssest.size();i++){
//            fillTableRows(listOfItemPrint,itemList.get(i).getItemCode(),itemList.get(i).getItemName(),itemList.get(i).getOrgPrice(),i);
            itemListAssest.get(i).setCheck(false);
//            ItemCard itemCard=itemList.get(i);
//            barcodeListForPrint.add(itemCard);
        }

        pricePrintBarcode.setVisibility(View.INVISIBLE);
        adapterAssest = new ListAdapterAssesst(Item.this, itemListAssest);
        listAssest.setAdapter(adapterAssest);
        BarcodetextView.requestFocus();
        BarcodetextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {

                    OnEditorKeyPadAssest();

//                    String itemSwitch="";
//                    String itemCode=BarcodetextView.getText().toString();
//                    boolean isFound=false;
//                    if(!itemCode.equals("")& openAssesst){
//
//                        if(settingCOName==1){
//                            itemCode=BarcodetextView.getText().toString();
//                            try {
//                                itemCode = itemCode.substring(0, 12);
//                            }catch (Exception e){
//                                itemCode=BarcodetextView.getText().toString();
//                            }
//                            Log.e("itemCode1_",""+itemCode);
//                        }else{
//                            itemCode=BarcodetextView.getText().toString();
//                        }
//                        Log.e("itemCode2_",""+itemCode);
//
//
//                        List<String>itemUnite=findUnite(itemCode);
//                        int uQty=1;
//
//
//                        if(itemUnite.size()!=0){
//
//                            itemCode=itemUnite.get(0);
//                            uQty=Integer.parseInt(itemUnite.get(2));
//
//                        }else{
//                            itemSwitch=findSwitch(itemCode);
//                            if(!itemSwitch.equals("")){
//                                itemCode=itemSwitch;
//                            }
//                            uQty=1;
//
//                        }
//
//                        for(int i = 0; i< itemListAssest.size(); i++){
//                            if(itemCode.equals(itemListAssest.get(i).getAssesstCode())){
//                                itemNamePrintBarcode.setText(itemListAssest.get(i).getAssesstName());
//                                barcodeTextBarcode .setText(itemListAssest.get(i).getAssesstCode());
//                                pricePrintBarcode.setText("0.0");
////                            ItemCard itemCard=finalItemList.get(i);
////                            barcodeListForPrint.add(itemCard);
//
//                                isFound=true;
//                                itemListAssest.get(i).setCheck(true);
//                    adapterAssest.notifyDataSetChanged();
//                                listAssest.setSelection(i);
//
//                                Bitmap bitmap= null;
//                                try {
//                                    bitmap = encodeAsBitmap(itemCode, BarcodeFormat.CODE_128, 350, 100);
//                                    barcodeShelfPrintParcode.setImageBitmap(bitmap);
//                                } catch (WriterException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                                break;
//                            }else{
//
//                                itemNamePrintBarcode.setText("");
//                                barcodeTextBarcode .setText("");
//                                pricePrintBarcode.setText("");
//                            }
//                        }
//
//
//                        if(isFound){
//                            //noThing
//                        }else{
//                            showAlertDialog(getResources().getString(R.string.itemNotFoundAlert));
//                            BarcodetextView .setText("");
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    BarcodetextView.requestFocus();
//                                }
//                            });
//
//
//                        }
//
//
//
//                    }


                }
                return false;
            }});


//        BarcodetextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                itemNamePrintBarcode.setText("Item Name");
//                barcodeTextBarcode .setText("00000000000");
//                pricePrintBarcode.setText("0.0");
//                barcodeListForPrint.clear();
////                finalItemList = itemList;
//                barcodeShelfPrintParcode.setImageDrawable(getResources().getDrawable(R.drawable.barcodes));
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//
//                if(!BarcodetextView.getText().toString().equals("")& openBarcode){
//
//                    for(int i = 0; i< itemList.size(); i++){
//                        if(BarcodetextView.getText().toString().equals(itemList.get(i).getItemCode())){
//                            itemNamePrintBarcode.setText(itemList.get(i).getItemName());
//                            barcodeTextBarcode .setText(itemList.get(i).getItemCode());
//                            pricePrintBarcode.setText(itemList.get(i).getSalePrc());
////                            ItemCard itemCard=finalItemList.get(i);
////                            barcodeListForPrint.add(itemCard);
//
//                            itemList.get(i).setCheck(true);
//                            adapter.notifyDataSetChanged();
//                            list.setSelection(i);
//
//                            Bitmap bitmap= null;
//                            try {
//                                bitmap = encodeAsBitmap(BarcodetextView.getText().toString(), BarcodeFormat.CODE_128, 350, 100);
//                                barcodeShelfPrintParcode.setImageBitmap(bitmap);
//                            } catch (WriterException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            break;
//                        }
//                    }
//
//
//
//                }
//
////
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        Button barcode;
        barcode = dialogBarCodeAssesst.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCodeRedar = true;
                openAssesst = false;
                readBarCode(BarcodetextView,5);
            }
        });

        PrintAllcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PrintAllcheckBox.isChecked()){

                    for (int i = 0; i < itemListAssest.size(); i++) {
                        itemListAssest.get(i).setCheck(true);
                    }

                    ListAdapterAssesst adapter = new ListAdapterAssesst(Item.this, itemListAssest);
                    listAssest.setAdapter(adapter);

                }else{
                    for (int i = 0; i < itemListAssest.size(); i++) {
                        itemListAssest.get(i).setCheck(false);

                    }

                    ListAdapterAssesst adapter = new ListAdapterAssesst(Item.this, itemListAssest);
                    listAssest.setAdapter(adapter);

                }
            }
        });

//        PriceCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(PriceCheckBox.isChecked()){
//                    priceLinerPrint.setVisibility(View.VISIBLE);
//                }else{
//                    priceLinerPrint.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assesst.setClickable(true);
                dialogBarCodeAssesst.dismiss();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                openAssesst = false;
                SearchDialogForAssets(BarcodetextView, 5);
                BarcodetextView.setSelectAllOnFocus(true);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        BarcodetextView.requestFocus();

                    }
                });
            }
        });

        upBarcode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                count1[0]++;
                countTBarcode.setText("" + count1[0]);
            }
        });
        downBarcode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(countTBarcode.getText().toString()) != 1) {
                    count1[0]--;
                    countTBarcode.setText("" + count1[0]);
                } else
//                    Toast.makeText(Item.this, "Copy Not Less Than 1", Toast.LENGTH_SHORT).show();
                    TostMesage(getResources().getString(R.string.notless));
            }
        });


//        parentScrollView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                Log.v(TAG, "PARENT TOUCH");
//
//                findViewById(R.id.child_scroll).getParent()
//                        .requestDisallowInterceptTouchEvent(false);
//                return false;
//            }
//        });
//
//
        listAssest.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        PrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BarcodetextView.getText().toString().equals("")){
                    itemAssesstForPrint = new AssestItem();
                    itemAssesstForPrint.setCount(countTBarcode.getText().toString());
//                    if (PriceCheckBox.isChecked()) {
//                        itemCardForPrint.setOrgPrice("");
//                    } else {
//                        itemCardForPrint.setOrgPrice("**");
//                    }
                    itemAssesstForPrint.setPrice("**");
                    barcodeListForPrintAssest.clear();
                    for (int i = 0; i < itemListAssest.size(); i++) {

                        if (itemListAssest.get(i).getCheck()) {
                            AssestItem assestItem = itemListAssest.get(i);
                            barcodeListForPrintAssest.add(assestItem);
                        }

                    }


//                    boolean Permission= isStoragePermissionGranted();
//                    if(Permission) {
                    Intent intentBarcode = new Intent(Item.this, BluetoothConnectMenu.class);
                    intentBarcode.putExtra("printKey", "3");
                    startActivity(intentBarcode);
//                    }else{
//                        TostMesage(getResources().getString(R.string.Permission));
//
//                    }



                }else{
                    TostMesage(getResources().getString(R.string.insertData));
                }

//                openBarcode = true;

            }
        });


        dialogBarCodeAssesst.show();
    }


    void OnEditorKeyPadAssest(){
        String itemSwitch="";
        String itemCode=BarcodetextView.getText().toString();
        boolean isFound=false;
        if(!itemCode.equals("")& openAssesst){

            if(settingCOName==1){
                itemCode=BarcodetextView.getText().toString();
                try {
                    itemCode = itemCode.substring(0, 12);
                }catch (Exception e){
                    itemCode=BarcodetextView.getText().toString();
                }
                Log.e("itemCode1_",""+itemCode);
            }else{
                itemCode=BarcodetextView.getText().toString();
            }
            Log.e("itemCode2_",""+itemCode);


            List<String>itemUnite=findUnite(itemCode);
            int uQty=1;


            if(itemUnite.size()!=0){

                itemCode=itemUnite.get(0);
                uQty=Integer.parseInt(itemUnite.get(2));

            }else{
                itemSwitch=findSwitch(itemCode);
                if(!itemSwitch.equals("")){
                    itemCode=itemSwitch;
                }
                uQty=1;

            }

            for(int i = 0; i< itemListAssest.size(); i++){
                if(itemCode.equals(itemListAssest.get(i).getAssesstCode())){
                    itemNamePrintBarcode.setText(itemListAssest.get(i).getAssesstName());
                    barcodeTextBarcode .setText(itemListAssest.get(i).getAssesstCode());
                    pricePrintBarcode.setText("0.0");
//                            ItemCard itemCard=finalItemList.get(i);
//                            barcodeListForPrint.add(itemCard);

                    isFound=true;
                    itemListAssest.get(i).setCheck(true);
                    adapterAssest.notifyDataSetChanged();
                    listAssest.setSelection(i);

                    Bitmap bitmap= null;
                    try {
                        bitmap = encodeAsBitmap(itemCode, BarcodeFormat.CODE_128, 350, 100);
                        barcodeShelfPrintParcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }


                    break;
                }else{

                    itemNamePrintBarcode.setText("");
                    barcodeTextBarcode .setText("");
                    pricePrintBarcode.setText("");
                }
            }


            if(isFound){
                //noThing
            }else{
                showAlertDialog(getResources().getString(R.string.itemNotFoundAlert));
                BarcodetextView .setText("");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        BarcodetextView.requestFocus();
                    }
                });


            }



        }


    }

    void showItemPriceDialog() {
        final Dialog dialogBarCode = new Dialog(Item.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogBarCode.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBarCode.setCancelable(false);
        if(controll.isYellow){
            dialogBarCode.setContentView(R.layout.check_price_yellow);
        }else{
            dialogBarCode.setContentView(R.layout.check_price_yellow);
        }
//


        GifImageButton gib = new GifImageButton(this);
        gib.setImageResource(R.drawable.barcode_scanner);
        final MediaController mc = new MediaController(this);
        mc.setMediaPlayer((GifDrawable) gib.getDrawable());
        mc.setAnchorView(gib);
        mc.show();


        final Boolean[] isFound = {false};
        final boolean[] isEnter = {true};

        dialogBarCode.setCanceledOnTouchOutside(false);

        final int[] count1 = {1};

        final TextView salesPrice,itemName,qty_text_value;

        Button exit,prepare;
        ImageView search;
        final EditText itemCode;

        salesPrice= dialogBarCode.findViewById(R.id.salesPrice);
        search=  dialogBarCode.findViewById(R.id.search);
        qty_text_value=  dialogBarCode.findViewById(R.id.qty_text);
        exit =  dialogBarCode.findViewById(R.id.exit);

        itemName= dialogBarCode.findViewById(R.id.itemName);
        itemCode= (EditText) dialogBarCode.findViewById(R.id.itemCode);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                itemCode.requestFocus();


            }
        });
        dis_detail_recycle=dialogBarCode.findViewById(R.id.unit_detail_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Item.this);
        dis_detail_recycle.setLayoutManager(layoutManager);
        listItemUnitQty.clear();
        textView=salesPrice;
        textViewFd=salesPrice;
        textItemName= itemName;
        textQty=qty_text_value;
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("aya1,onTextChanged","onTextChanged");

                if(textView.getText().toString().equals("-1")){
                    Log.e("aya5,onTextChanged","onTextChanged");
                    listItemUnitQty.clear();
                    showAlertDialog(getResources().getString(R.string.falidTogetdata));
                    salesPrice.setText("");
                    itemName.setText("");
                    textViewFd.setText("");
                    qty_text_value.setText("");
                    Cash_detail_adapter dis_detail_adapter= new Cash_detail_adapter(listItemUnitQty, Item.this);
                    dis_detail_recycle.setAdapter(dis_detail_adapter);

                }else if(textView.getText().toString().equals("*")){
                    Log.e("aya6,onTextChanged","onTextChanged");
                    listItemUnitQty.clear();
                    showAlertDialog(getResources().getString(R.string.thisitemnotfound));
                    salesPrice.setText("");
                    itemName.setText("");
                    textViewFd.setText("");
                    qty_text_value.setText("");
                    Cash_detail_adapter dis_detail_adapter= new Cash_detail_adapter(listItemUnitQty, Item.this);
                    dis_detail_recycle.setAdapter(dis_detail_adapter);

                }
                else  if(textView.getText().toString().equals("fill_unit")){
                    Log.e("aya7",printEditTextTag.getText().toString());
                    Cash_detail_adapter dis_detail_adapter= new Cash_detail_adapter(listItemUnitQty, Item.this);
                    dis_detail_recycle.setAdapter(dis_detail_adapter);
//                   if( listItemUnitQty.size()==0)
                    textViewFd=salesPrice;
                    if(textViewFd!=null)
                    textViewFd.setText(controll.F_D);
//                    Log.e("aya2",printEditTextTag.getText().toString());

                }
                else {
//                    Log.e("aya8",printEditTextTag.getText().toString());
                    Cash_detail_adapter dis_detail_adapter= new Cash_detail_adapter(listItemUnitQty, Item.this);
                    dis_detail_recycle.setAdapter(dis_detail_adapter);
//                   if( listItemUnitQty.size()==0)
//                    Log.e("aya99,controll=",controll.F_D+"");
//                    textViewFd=salesPrice;
//                    if(textViewFd!=null)
//                    textViewFd.setText(controll.F_D);
//                    Log.e("aya2",printEditTextTag.getText().toString());

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Button barcode;
        barcode = dialogBarCode.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCodeRedar = true;
                openPrice = false;
                readBarCode(itemCode,2);
            }
        });


        itemCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL  ) {
//                    if (isEnter[0]) {
                        if(!itemCode.getText().toString().equals("")&& openPrice ){
//                            textView=salesPrice;
                            textViewFd=salesPrice;
                            textItemName= itemName;
                            textQty=qty_text_value;
                            List<MainSetting> mainSetting=InventDB.getAllMainSetting();
                            if(mainSetting.size()!=0) {

                                importJson sendCloud = new importJson(Item.this,itemCode.getText().toString(),0,"","");
                                sendCloud.startSending("ItemPrice");
//                          isEnter[0]=false;
                                itemCode.setSelectAllOnFocus(true);
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        itemCode.requestFocus();


                                    }
                                });
                            }else{

                                new SweetAlertDialog(Item.this, SweetAlertDialog.WARNING_TYPE)
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
                ItemPrice.setClickable(true);
                dialogBarCode.dismiss();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                openPrice = false;
                itemName.setText("");
                salesPrice.setText("");
                SearchDialog(itemCode, 4);
            }
        });



        dialogBarCode.show();
    }

    void showItemCostDialog() {
        final Dialog dialogBarCode = new Dialog(Item.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogBarCode.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBarCode.setCancelable(false);
        if(controll.isYellow){
            dialogBarCode.setContentView(R.layout.check_cost_yellow);
        }else{
            dialogBarCode.setContentView(R.layout.check_cost_yellow);
        }
//


        GifImageButton gib = new GifImageButton(this);
        gib.setImageResource(R.drawable.barcode_scanner);
        final MediaController mc = new MediaController(this);
        mc.setMediaPlayer((GifDrawable) gib.getDrawable());
        mc.setAnchorView(gib);
        mc.show();


        final Boolean[] isFound = {false};
        final boolean[] isEnter = {true};

        dialogBarCode.setCanceledOnTouchOutside(false);

        final int[] count1 = {1};

        final TextView salesPrice,itemName;

        Button exit,prepare;
        ImageView search;
        final EditText itemCode;

        salesPrice= dialogBarCode.findViewById(R.id.salesPrice);
        search=  dialogBarCode.findViewById(R.id.search);

        exit =  dialogBarCode.findViewById(R.id.exit);

        itemName= dialogBarCode.findViewById(R.id.itemName);
        itemCode= (EditText) dialogBarCode.findViewById(R.id.itemCode);

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

        textView=salesPrice;
        textItemName= itemName;
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(textView.getText().toString().equals("-1")){
                    showAlertDialog(getResources().getString(R.string.falidTogetdata));
                    salesPrice.setText("");
                    itemName.setText("");

                }else if(textView.getText().toString().equals("*")){
                    showAlertDialog(getResources().getString(R.string.thisitemnotfound));
                    salesPrice.setText("");
                    itemName.setText("");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Button barcode;
        barcode = dialogBarCode.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCodeRedar = true;
                openCost = false;
                readBarCode(itemCode,6);
            }
        });


        itemCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL  ) {
//                    if (isEnter[0]) {
                    if(!itemCode.getText().toString().equals("")&& openCost ){
                        textView=salesPrice;
                        textItemName= itemName;
                        List<MainSetting> mainSetting=InventDB.getAllMainSetting();
                        if(mainSetting.size()!=0) {

                            importJson sendCloud = new importJson(Item.this,itemCode.getText().toString(),0,"","");
                            sendCloud.startSending("ItemCost");
//                          isEnter[0]=false;
                            itemCode.setSelectAllOnFocus(true);
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    itemCode.requestFocus();

                                }
                            });
                        }else{

                            new SweetAlertDialog(Item.this, SweetAlertDialog.WARNING_TYPE)
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
                ItemCost.setClickable(true);
                dialogBarCode.dismiss();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                openCost = false;
                itemName.setText("");
                salesPrice.setText("");
                SearchDialog(itemCode, 6);
            }
        });



        dialogBarCode.show();
    }


    void showPrintShelfTagDialog() {//here tahani*********
        dialog = new Dialog(Item.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        if(controll.isYellow){
            dialog.setContentView(R.layout.print_shelf_tag_yellow);
        }else {
            dialog.setContentView(R.layout.print_shelf_tag);

        }
//        dialog.setContentView(R.layout.print_shelf_tag);

        dialog.setCanceledOnTouchOutside(false);

        final int[] count1 = {1};
        final int[] DesignType = {0};


        final LinearLayout exit,printShelfTag,ClearButtonTag,shelfTagLiner1,shelfTagLiner,shelfTagLiner3,shelfTagLiner4;
        Button SearchButtonTag;
        ImageButton upCount, downCount;
        ImageView shelfTagLinerTest;
EditText prinS1,prinSa1;

        final CheckBox PriceCheckBoxTag,ExpCheckBoxTag,startCheckBoxTag,nameCheckBoxTag,PrintCheckBoxTag;
        final LinearLayout priceLinerPrint,ExpLinerTag,priceLinerPrint_;
//RadioGroup design;

        startEditTextTag=dialog.findViewById(R.id.startEditTextTag);
        printEditTextTag=dialog.findViewById(R.id.printEditTextTag);

        startEditTextTag.setText(""+today);
       // pri_LinerTag=(LinearLayout) dialog.findViewById(R.id.pri_LinerTag);
        ExpLinerTag=(LinearLayout) dialog.findViewById(R.id.ExpLinerTag);
        priceLinerPrint=(LinearLayout) dialog.findViewById(R.id.priceLinerPrint);
        priceLinerPrint_=(LinearLayout) dialog.findViewById(R.id.priceLinerPrint_);
        PriceCheckBoxTag = (CheckBox) dialog.findViewById(R.id.PriceCheckBoxTag);
        SalesPriceCheckBoxTag= (CheckBox) dialog.findViewById(R.id.SalesPriceCheckBoxTag);
        PrintCheckBoxTag=dialog.findViewById(R.id.printCheckBoxTag);
        nameCheckBoxTag=dialog.findViewById(R.id.nameCheckBoxTag);
        ExpCheckBoxTag = (CheckBox) dialog.findViewById(R.id.ExpCheckBoxTag);
        startCheckBoxTag=dialog.findViewById(R.id.startCheckBoxTag);
        countText = (TextView) dialog.findViewById(R.id.countT);
        ItemNameEditTextTag= (TextView) dialog.findViewById(R.id.ItemNameEditTextTag);
        PriceEditTextTag= (TextView) dialog.findViewById(R.id.PriceEditTextTag);
        salesPriceEditTextTag= (TextView) dialog.findViewById(R.id.SalesPriceEditTextTag);
        ExpEditTextTag= (TextView) dialog.findViewById(R.id.ExpEditTextTag);
        itemNamePrint= (TextView) dialog.findViewById(R.id.itemNamePrint);
        pricePrint= (TextView) dialog.findViewById(R.id.pricePrint);
        pricePrint4= (TextView) dialog.findViewById(R.id.pricePrint_);
        //ItemUnitEditTextTag=(TextView)dialog.findViewById(R.id.ItemUnitEditTextTag);
        exp= (TextView) dialog.findViewById(R.id.exp);

        itemNamePrint2= (TextView) dialog.findViewById(R.id.itemName);//BarcodeText
        pricePrint2= (TextView) dialog.findViewById(R.id.price);
        itemText= (TextView) dialog.findViewById(R.id.itemText);
        exp2= (TextView) dialog.findViewById(R.id.exp1);

//        design=  dialog.findViewById(R.id.design);

        shelfTagLiner1 =dialog.findViewById(R.id.shelfTagLiner1);
        shelfTagLiner =dialog.findViewById(R.id.shelfTagLiner);
        shelfTagLiner3 =dialog.findViewById(R.id.shelfTagLiner3);
        shelfTagLiner4 =dialog.findViewById(R.id.shelfTagLiner_iraq);

        itemNamePrint3= (TextView) dialog.findViewById(R.id.itemName3);//BarcodeText
        itemNamePrint4= (TextView) dialog.findViewById(R.id.itemNamePrint_);//BarcodeText
        pricePrint3= (TextView) dialog.findViewById(R.id.price3);

        tempText=exp;
        ItemCodeEditTextTag= (EditText) dialog.findViewById(R.id.ItemCodeEditTextTag);
        texetrespone=dialog.findViewById(R.id.texetrespone);
        ClearButtonTag=dialog.findViewById(R.id.ClearButtonTag);
        SearchButtonTag= dialog.findViewById(R.id.SearchButtonTag);
        printShelfTag=  dialog.findViewById(R.id.printShelfTag);
        exit =  dialog.findViewById(R.id.exit);
        prinS1=dialog.findViewById(R.id.prinS);
        prinSa1=dialog.findViewById(R.id.prinSa);
        prinS=prinS1;
        prinSa=prinSa1;
        barcodeShelfPrint= (ImageView) dialog.findViewById(R.id.barcodeShelfPrint);
        barcodeShelfPrint2= (ImageView) dialog.findViewById(R.id.barcodeShelf);
        barcodeShelfPrint3= (ImageView) dialog.findViewById(R.id.barcodeShelf3);
        barcodeShelfPrint4=(ImageView) dialog.findViewById(R.id.barcodeShelfPrint_);
        shelfTagLinerTest=dialog.findViewById(R.id.shelfTagLinerTest);
        shelfTagLinerTests=shelfTagLinerTest;
        Spinner designSpinner= dialog.findViewById(R.id.spinnerDesign);
         uniteSpinner=dialog.findViewById(R.id.uniteSpinner);
        final CheckBox uniteCheckBoxTag=dialog.findViewById(R.id.uniteCheckBoxTag);
        upCount = (ImageButton) dialog.findViewById(R.id.up);
        downCount = (ImageButton) dialog.findViewById(R.id.down);
        ExpEditTextTag.setText(convertToEnglish(today));

printEditTextTag.setText(convertToEnglish(today));

        shelfTagLiner1.setVisibility(View.GONE);
        shelfTagLiner.setVisibility(View.VISIBLE);
        List<String> designList=new ArrayList<>();
        List<UnitName> uniteList=new ArrayList<>();


        designList.add(getResources().getString(R.string.des_1));
        designList.add(getResources().getString(R.string.des_2));
        designList.add(getResources().getString(R.string.des_3));
        designList.add(getResources().getString(R.string.des_4));
        designList.add(getResources().getString(R.string.des_5));

        List<MainSetting> mainSettings = InventDB.getAllMainSetting();
        if (mainSettings.size() != 0) {
            StkNo=mainSettings.get(0).getStorNo();
            isOnlinePrice=mainSettings.get(0).getOnlinePrice();
            OnlineItems=mainSettings.get(0).getONlINEshlf();
        }

      uniteList = InventDB.getAllUnite();
        final List<String> listOfUnite=new ArrayList<>();

        for (int i=0;i<uniteList.size();i++){
            if(i==0){
                uniteNames=uniteList.get(i).getItemUnitN();
            }
            listOfUnite.add(uniteList.get(i).getItemUnitN());
        }

        ArrayAdapter   DesignAdapter = new ArrayAdapter<String>(Item.this, R.layout.spinner_style, designList);
        designSpinner.setAdapter(DesignAdapter);

        ArrayAdapter   uniteAdapter = new ArrayAdapter<String>(Item.this, R.layout.spinner_style, listOfUnite);
        uniteSpinner.setAdapter(uniteAdapter);

        designSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position){

                    case 0:
                        shelfTagLiner1.setVisibility(View.GONE);
                        shelfTagLiner3.setVisibility(View.GONE);
                        shelfTagLiner4.setVisibility(View.GONE);
                        shelfTagLiner.setVisibility(View.VISIBLE);
                        DesignType[0] =0;
                        break;
                    case 1:

                        shelfTagLiner1.setVisibility(View.VISIBLE);
                        shelfTagLiner4.setVisibility(View.GONE);
                        shelfTagLiner3.setVisibility(View.GONE);
                        shelfTagLiner.setVisibility(View.GONE);
                        DesignType[0] =1;
                        break;

                    case 2:
                        shelfTagLiner4.setVisibility(View.GONE);

                        shelfTagLiner3.setVisibility(View.VISIBLE);
                        shelfTagLiner.setVisibility(View.GONE);
                        shelfTagLiner1.setVisibility(View.GONE);
                        DesignType[0] =2;
                        break;
                    case 3:

                        shelfTagLiner3.setVisibility(View.GONE);
                        shelfTagLiner.setVisibility(View.GONE);
                        shelfTagLiner1.setVisibility(View.GONE);
                        shelfTagLiner4.setVisibility(View.VISIBLE);

                        DesignType[0] =3;
                        break;
                    case 4:

                        shelfTagLiner3.setVisibility(View.GONE);
                        shelfTagLiner.setVisibility(View.GONE);
                        shelfTagLiner1.setVisibility(View.GONE);
                        shelfTagLiner4.setRotation(180.0f);
                        shelfTagLiner4.setVisibility(View.VISIBLE);

                        DesignType[0] =4;
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uniteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!OnlineItems.equals("1"))
                uniteNames=listOfUnite.get(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        design.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                switch (checkedId){
//
//                    case R.id.de1:
//                        shelfTagLiner1.setVisibility(View.GONE);
//                        shelfTagLiner.setVisibility(View.VISIBLE);
//                        DesignType[0] =0;
//                        break;
//                    case R.id.de2:
//
//                        shelfTagLiner1.setVisibility(View.VISIBLE);
//                        shelfTagLiner.setVisibility(View.GONE);
//                        DesignType[0] =1;
//                        break;
//
//                }
//
//            }
//        });




        Button barcode;
        barcode = dialog.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCodeRedar = true;
                openShelfTag = false;
                readBarCode(ItemCodeEditTextTag,0);
            }
        });

        ClearButtonTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countText.setText("1");
                ItemCodeEditTextTag.setText("");
                ItemNameEditTextTag.setText("");
//                ItemUnitEditTextTag.setText("");
                PriceEditTextTag.setText("");
                salesPriceEditTextTag.setText("");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ItemCodeEditTextTag.requestFocus();
                    }
                });
                ExpEditTextTag.setText(convertToEnglish(today));
                printEditTextTag.setText(convertToEnglish(today));
                itemNamePrint.setText("Item Name");
                pricePrint.setText(" 0.00 JD");
                barcodeShelfPrint.setImageDrawable(getResources().getDrawable(R.drawable.barcodes));
                itemNamePrint2.setText("Item Name");
                pricePrint2.setText(" 0.00 JD");
//                barcodeShelfPrint2.setImageDrawable(getResources().getDrawable(R.drawable.barcodes));
                itemNamePrint3.setText("Item Name");
                pricePrint3.setText(" 0.00 JD");
//                barcodeShelfPrint3.setImageDrawable(getResources().getDrawable(R.drawable.barcodes));
                itemNamePrint4.setText("Item Name");
                pricePrint4.setText(" 0.00 JD");
            }
        });


        PriceCheckBoxTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnlineItems.equals("0"))
                onEditorKeyPadChange();
                if(PriceCheckBoxTag.isChecked()){
                    priceLinerPrint.setVisibility(View.VISIBLE);
                }else{
                    priceLinerPrint.setVisibility(View.INVISIBLE);
                }

            }
        });
        SalesPriceCheckBoxTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(OnlineItems.equals("0"))
                onEditorKeyPadChange();

                if(SalesPriceCheckBoxTag.isChecked()){
                    priceLinerPrint.setVisibility(View.VISIBLE);
                }else{
                    priceLinerPrint.setVisibility(View.INVISIBLE);
                }

            }
        });

         PrintCheckBoxTag.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if(PrintCheckBoxTag.isChecked()){
                     ExpLinerTag.setVisibility(View.VISIBLE);

//                     textView41_.setText("PRI:");
                 }else{
                     ExpLinerTag.setVisibility(View.INVISIBLE);
          //           textView41_.setText("EXP:");
                 }
             }
         });

        ExpCheckBoxTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ExpCheckBoxTag.isChecked()){
                    ExpLinerTag.setVisibility(View.VISIBLE);
                }else{
                    ExpLinerTag.setVisibility(View.INVISIBLE);
                }
            }
        });

//        TimeDialog
        ExpEditTextTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog(ExpEditTextTag);
                exp.setText(ExpEditTextTag.getText().toString());
            }
        });

        printEditTextTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog(printEditTextTag);
                exp.setText(printEditTextTag.getText().toString());
            }
        });

        PriceEditTextTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!PriceEditTextTag.getText().toString().equals("")) {
                    if (PriceEditTextTag.getText().toString().equals("*")) {
                        showAlertDialog(getResources().getString(R.string.itemNotFoundAlert));
                        ItemCodeEditTextTag.setText("");
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                ItemCodeEditTextTag.requestFocus();
                            }
                        });
                        PriceEditTextTag.setText("");
                        salesPriceEditTextTag.setText("");
                        ItemNameEditTextTag.setText("");
                  //      ItemUnitEditTextTag.setText("");
                        pricePrint.setText("");
                    }
                    else if (PriceEditTextTag.getText().toString().equals("-1")) {
                        showAlertDialog(getResources().getString(R.string.falidTogetdata));
                        ItemCodeEditTextTag.setText("");
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                ItemCodeEditTextTag.requestFocus();
                            }
                        });
                        PriceEditTextTag.setText("");
                        salesPriceEditTextTag.setText("");
                        ItemNameEditTextTag.setText("");
                    //    ItemUnitEditTextTag.setText("");
                        pricePrint.setText("");
                    } else
                    {
                        itemNamePrint.setText(ItemNameEditTextTag.getText().toString()+"/"+uniteNames);
                        exp.setText(ExpEditTextTag.getText().toString());

                        if(salesPriceEditTextTag.getText().toString().trim().length()==0)salesPriceEditTextTag.setText(PriceEditTextTag.getText().toString());
                        pricePrint.setText(convertToEnglish(numberFormat.format(Double.parseDouble(PriceEditTextTag.getText().toString()))) + " JD");
                        pricePrint.setText(convertToEnglish(numberFormat.format(Double.parseDouble(salesPriceEditTextTag.getText().toString()))) + " JD");

                        pricePrint2.setText(convertToEnglish(numberFormat.format(Double.parseDouble(PriceEditTextTag.getText().toString()))) + " JD");
                        pricePrint2.setText(convertToEnglish(numberFormat.format(Double.parseDouble(salesPriceEditTextTag.getText().toString()))) + " JD");

                        pricePrint3.setText(convertToEnglish(numberFormat.format(Double.parseDouble(PriceEditTextTag.getText().toString()))) + " JD");
                        pricePrint3.setText(convertToEnglish(numberFormat.format(Double.parseDouble(salesPriceEditTextTag.getText().toString()))) + " JD");

                        pricePrint4.setText(convertToEnglish(numberFormat.format(Double.parseDouble(PriceEditTextTag.getText().toString()))) + " JD");
                        pricePrint4.setText(convertToEnglish(numberFormat.format(Double.parseDouble(salesPriceEditTextTag.getText().toString()))) + " JD");

                        itemNamePrint2.setText(ItemNameEditTextTag.getText().toString()+" / "+uniteNames);
                        itemNamePrint3.setText(ItemNameEditTextTag.getText().toString()+" / "+uniteNames);
                        itemNamePrint4.setText(ItemNameEditTextTag.getText().toString()+" / "+uniteNames);
                        exp2.setText(ExpEditTextTag.getText().toString());
                        itemText.setText(ItemCodeEditTextTag.getText().toString());

                        try {
                            Bitmap bitmapBa = encodeAsBitmap(ItemCodeEditTextTag.getText().toString(), BarcodeFormat.CODE_128, 350, 100);
                            barcodeShelfPrint.setImageBitmap(bitmapBa);
                            barcodeShelfPrint2.setImageBitmap(bitmapBa);
                            barcodeShelfPrint3.setImageBitmap(bitmapBa);
                            barcodeShelfPrint4.setImageBitmap(bitmapBa);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        texetrespone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
          if(editable.length()!=0){
              onEditorKeyPadChange2();
          }
            }
        });
        ItemCodeEditTextTag.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
                    if(OnlineItems.equals("0"))
                    onEditorKeyPadChange();
                    else
                    {     importJson json=new importJson(Item.this,"",0,"","");
                        json .getItems(ItemCodeEditTextTag.getText().toString());


                    }
//                    boolean isItemFound = false;
//
//                    String itemCode = ItemCodeEditTextTag.getText().toString();
//                    String itemName = ItemNameEditTextTag.getText().toString();
//
//                    String itemSwitch = "",QRCode="",Price="",itemUnit="";
//                    if (!ItemCodeEditTextTag.getText().toString().equals("") && openShelfTag) {
////                        itemCardsList = InventDB.getAllItemCard();
//
//                      if(isOnlinePrice.equals("0")) {
//                          boolean isPriceUnite = false;
//                          if (itemCode.length() > 17) {
//                              QRCode = itemCode;
//                              itemCode = itemCode.substring(0, 16);
//                              Log.e("String ", "" + itemCode);
//
//                          } else {
//                              itemCode = ItemCodeEditTextTag.getText().toString();
//                          }
//
//                          List<ItemQR> QRList = findQRCode(itemCode, StkNo);
//
//                          if (QRList.size() != 0) {
//                              itemCode = QRList.get(0).getItemCode();
//
//                              Price = QRList.get(0).getSalesPrice();
//
//                              ItemCodeEditTextTag.setText(itemCode);
//                              isPriceUnite = true;
//                          } else {
//
//                              if(settingCOName==1){
//                                  itemCode=ItemCodeEditTextTag.getText().toString();
//                                  try {
//                                      itemCode = itemCode.substring(0, 12);
//                                  }catch (Exception e){
//                                      itemCode=ItemCodeEditTextTag.getText().toString();
//                                  }
//                                  Log.e("itemCode1_",""+itemCode);
//                              }else{
//                                  itemCode=ItemCodeEditTextTag.getText().toString();
//                              }
//                              Log.e("itemCode2_",""+itemCode);
//
//
//
//                              List<String> itemUnite = findUnite(itemCode);
//                              int uQty = 1;
//
//
//                              if (itemUnite.size() != 0) {
//
//                                  itemCode = itemUnite.get(0);
//                                  uQty = Integer.parseInt(itemUnite.get(2));
//                                  Price = itemUnite.get(1);
//                                  itemUnit=itemUnite.get(3);
//                                  isPriceUnite = true;
//
//                              } else {
//                                  itemSwitch = findSwitch(itemCode);
//                                  if (!itemSwitch.equals("")) {
//                                      itemCode = itemSwitch;
//                                  }
//                                  uQty = 1;
//                                  isPriceUnite = false;
//                              }
//                          }
//
//
//                          ItemCard itemCard = InventDB.getItemCardByBarCode(itemCode);
//                          String it = itemCard.getItemCode();
//                          Log.e("itemcard_oo", "" + it);
////                        for (int i = 0; i < itemCardsList.size(); i++) {
//                          if (!TextUtils.isEmpty(it)) {
//                              if (it.equals(itemCode)) {
//                                  isItemFound = true;
//                                  ItemNameEditTextTag.setText(itemCard.getItemName());
//                               //   ItemUnitEditTextTag.setText(itemUnit);
//                                  if (!isPriceUnite) {
//                                      PriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))));
//
//                                      pricePrint.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) + " JD");
//
//                                      pricePrint2.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) + " JD");
//
//                                      pricePrint3.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) + " JD");
//
//                                      pricePrint4.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) + " JD");
//
//                                  } else {
//                                      PriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))));
//
//                                      pricePrint.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");
//
//                                      pricePrint2.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");
//
//                                      pricePrint3.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");
//                                      pricePrint4.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");
//
//                                  }
////                            ExpEditTextTag.setText(itemCardsList.get(i).g());
//                                  itemNamePrint.setText(itemCard.getItemName());
//                                  exp.setText(ExpEditTextTag.getText().toString());
//
//                                  itemNamePrint2.setText(itemCard.getItemName());
//                                  itemNamePrint3.setText(itemCard.getItemName());
//                                  itemNamePrint4.setText(itemCard.getItemName());
//                                  exp2.setText(ExpEditTextTag.getText().toString());
//                                  itemText.setText(ItemCodeEditTextTag.getText().toString());
//
//                                  try {
//                                      Bitmap bitmapBa = encodeAsBitmap(ItemCodeEditTextTag.getText().toString(), BarcodeFormat.CODE_128, 350, 100);
//                                      barcodeShelfPrint.setImageBitmap(bitmapBa);
//                                      barcodeShelfPrint2.setImageBitmap(bitmapBa);
//                                      barcodeShelfPrint3.setImageBitmap(bitmapBa);
//                                      barcodeShelfPrint4.setImageBitmap(bitmapBa);
//                                  } catch (WriterException e) {
//                                      e.printStackTrace();
//                                  }
//
////                                break;
//
//                              }
//
////                            else{
////
////                                ItemNameEditTextTag.setText("");
////                                PriceEditTextTag.setText("");
//////                            ExpEditTextTag.setText(itemCardsList.get(i).g());
////                                itemNamePrint.setText("");
////                                pricePrint.setText("");
////
////
////                            }
////                        }
////////
//
//                              //
//
//                          }
//
//                          if (isItemFound) {
//
//                              //nothing
//
//                          } else {
////                        save.setClickable(false);
//                              showAlertDialog(getResources().getString(R.string.itemNotFoundAlert));
//                              ItemCodeEditTextTag.setText("");
//                              new Handler().post(new Runnable() {
//                                  @Override
//                                  public void run() {
//                                      ItemCodeEditTextTag.requestFocus();
//                                  }
//                              });
//                              PriceEditTextTag.setText("");
//                              ItemNameEditTextTag.setText("");
//                           //   ItemUnitEditTextTag.setText("");
//                              pricePrint.setText("");
//                          }
//
//                      }else{
//
//                          textView=PriceEditTextTag;
//                          textItemName= ItemNameEditTextTag;
//
//                          importJson sendCloud = new importJson(Item.this,ItemCodeEditTextTag.getText().toString(),0,"","");
//                          sendCloud.startSending("ItemPrice");
//
//
//
//                      }
//
//
//                    }
                }
                return false;
            }});

//        ItemCodeEditTextTag.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                if(s.toString().trim().length()!=0){
//
//                    boolean isItemFound = false;
//
//                    String itemCode = ItemCodeEditTextTag.getText().toString();
//                    String itemName = ItemNameEditTextTag.getText().toString();
//
//                    String itemSwitch = "",QRCode="",Price="";
//                    if (!ItemCodeEditTextTag.getText().toString().equals("") && openShelfTag) {
////                        itemCardsList = InventDB.getAllItemCard();
//
//                        if(isOnlinePrice.equals("0")) {
//                            boolean isPriceUnite = false;
//                            if (itemCode.length() > 17) {
//                                QRCode = itemCode;
//                                itemCode = itemCode.substring(0, 16);
//                                Log.e("String ", "" + itemCode);
//
//                            } else {
//                                itemCode = ItemCodeEditTextTag.getText().toString();
//                            }
//
//                            List<ItemQR> QRList = findQRCode(itemCode, StkNo);
//
//                            if (QRList.size() != 0) {
//                                itemCode = QRList.get(0).getItemCode();
//
//                                Price = QRList.get(0).getSalesPrice();
//
//                                ItemCodeEditTextTag.setText(itemCode);
//                                isPriceUnite = true;
//                            } else {
//                                List<String> itemUnite = findUnite(itemCode);
//                                int uQty = 1;
//
//
//                                if (itemUnite.size() != 0) {
//
//                                    itemCode = itemUnite.get(0);
//                                    uQty = Integer.parseInt(itemUnite.get(2));
//                                    Price = itemUnite.get(1);
//                                    isPriceUnite = true;
//
//                                } else {
//                                    itemSwitch = findSwitch(itemCode);
//                                    if (!itemSwitch.equals("")) {
//                                        itemCode = itemSwitch;
//                                    }
//                                    uQty = 1;
//                                    isPriceUnite = false;
//                                }
//                            }
//
//
//                            ItemCard itemCard = InventDB.getItemCardByBarCode(itemCode);
//                            String it = itemCard.getItemCode();
//                            Log.e("itemcard_oo", "" + it);
////                        for (int i = 0; i < itemCardsList.size(); i++) {
//                            if (!TextUtils.isEmpty(it)) {
//                                if (it.equals(itemCode)) {
//                                    isItemFound = true;
//                                    ItemNameEditTextTag.setText(itemCard.getItemName());
//                                    if (!isPriceUnite) {
//                                        PriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))));
//
//                                        pricePrint.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) + " JD");
//
//                                        pricePrint2.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) + " JD");
//
//                                        pricePrint3.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) + " JD");
//
//
//                                    } else {
//                                        PriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))));
//
//                                        pricePrint.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");
//
//                                        pricePrint2.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");
//
//                                        pricePrint3.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");
//                                    }
////                            ExpEditTextTag.setText(itemCardsList.get(i).g());
//                                    itemNamePrint.setText(itemCard.getItemName());
//                                    exp.setText(ExpEditTextTag.getText().toString());
//
//                                    itemNamePrint2.setText(itemCard.getItemName());
//                                    itemNamePrint3.setText(itemCard.getItemName());
//                                    exp2.setText(ExpEditTextTag.getText().toString());
//                                    itemText.setText(ItemCodeEditTextTag.getText().toString());
//
//                                    try {
//                                        Bitmap bitmapBa = encodeAsBitmap(ItemCodeEditTextTag.getText().toString(), BarcodeFormat.CODE_128, 350, 100);
//                                        barcodeShelfPrint.setImageBitmap(bitmapBa);
//                                        barcodeShelfPrint2.setImageBitmap(bitmapBa);
//                                        barcodeShelfPrint3.setImageBitmap(bitmapBa);
//                                    } catch (WriterException e) {
//                                        e.printStackTrace();
//                                    }
//
////                                break;
//
//                                }
//
////                            else{
////
////                                ItemNameEditTextTag.setText("");
////                                PriceEditTextTag.setText("");
//////                            ExpEditTextTag.setText(itemCardsList.get(i).g());
////                                itemNamePrint.setText("");
////                                pricePrint.setText("");
////
////
////                            }
////                        }
////////
//
//                                //
//
//                            }
//
//                            if (isItemFound) {
//
//                                //nothing
//
//                            } else {
////                        save.setClickable(false);
//                                showAlertDialog(getResources().getString(R.string.itemNotFoundAlert));
//                                ItemCodeEditTextTag.setText("");
//                                new Handler().post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        ItemCodeEditTextTag.requestFocus();
//                                    }
//                                });
//                                PriceEditTextTag.setText("");
//                                ItemNameEditTextTag.setText("");
//                                pricePrint.setText("");
//                            }
//
//                        }else{
//
//                            textView=PriceEditTextTag;
//                            textItemName= ItemNameEditTextTag;
//
//                            importJson sendCloud = new importJson(Item.this,ItemCodeEditTextTag.getText().toString(),0,"","");
//                            sendCloud.startSending("ItemPrice");
//
//
//
//                        }
//
//
//                    }
//
//
//                }
//
//
//
//
//            }
//        });




//        ItemCodeEditTextTag.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                boolean isItemFound = false;
//
//                String itemCode = ItemCodeEditTextTag.getText().toString();
//                String itemName = ItemNameEditTextTag.getText().toString();
//
//
//                if (!ItemCodeEditTextTag.getText().toString().equals("") && openShelfTag) {
//                    itemCardsList = InventDB.getAllItemCard();
//
//                    for (int i = 0; i < itemCardsList.size(); i++) {
//                        if (itemCardsList.get(i).getItemCode().equals(itemCode)) {
//                            isItemFound = true;
//                            ItemNameEditTextTag.setText(itemCardsList.get(i).getItemName());
//                            PriceEditTextTag.setText(itemCardsList.get(i).getSalePrc());
////                            ExpEditTextTag.setText(itemCardsList.get(i).g());
//                            itemNamePrint.setText(itemCardsList.get(i).getItemName());
//                            pricePrint.setText(itemCardsList.get(i).getSalePrc());
//                            exp.setText(ExpEditTextTag.getText().toString());
//                            try {
//                                Bitmap bitmapBa= encodeAsBitmap(ItemCodeEditTextTag.getText().toString(), BarcodeFormat.CODE_128, 350, 100);
//                                barcodeShelfPrint.setImageBitmap(bitmapBa);
//                            } catch (WriterException e) {
//                                e.printStackTrace();
//                            }
//
//                            break;
//
//                        }
//                    }
////
////                    if (!isItemFound) {
////
//////                        new Handler().post(new Runnable() {
//////                            @Override
//////                            public void run() {
//////                                itemNameNew.requestFocus();
//////                            }
//////                        });
////
//////                        save.setClickable(true);
////                    } else {
//////                        save.setClickable(false);
////                        showAlertDialog("This item Found please add another item ");
////                    }
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

        SearchButtonTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch = true;
                openShelfTag = false;
                SearchDialog(ItemCodeEditTextTag,2);

                //////clear

                ItemCodeEditTextTag.setText("");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ItemCodeEditTextTag.requestFocus();
                    }
                });
                PriceEditTextTag.setText("");
                ItemNameEditTextTag.setText("");
                pricePrint.setText("");

            }
        });

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
//                    Toast.makeText(Item.this, getResources().getString(R.string.notless), Toast.LENGTH_SHORT).show();
                TostMesage(getResources().getString(R.string.notless));
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pShelfTag.setClickable(true);
                dialog.dismiss();
            }
        });

        printShelfTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCardForPrint=new ItemCard();
                if(!(ItemCodeEditTextTag.getText().toString().equals(""))&!(ItemNameEditTextTag.getText().toString().equals(""))) {
                    itemCardForPrint.setItemCode(ItemCodeEditTextTag.getText().toString());
                    itemCardForPrint.setItemName(ItemNameEditTextTag.getText().toString());
                  //  itemCardForPrint.setItemUnit(ItemUnitEditTextTag.getText().toString());
                    itemCardForPrint.setCostPrc(countText.getText().toString());
                    if(ExpCheckBoxTag.isChecked()){
                      //  PrintDateFlag  =0;
                            itemCardForPrint.setDepartmentId("" + ExpEditTextTag.getText().toString());

                    }else{
                        itemCardForPrint.setDepartmentId("**" );
                    }
//                    if(PrintCheckBoxTag.isChecked()){
//                        PrintDateFlag=1;
//                        itemCardForPrint.setDepartmentId("" + printEditTextTag.getText().toString());
//
//                    }else{
//                        itemCardForPrint.setDepartmentId("**" );
//                    }

                    itemCardForPrint.setItemG(""+DesignType[0]);

                    if(PriceCheckBoxTag.isChecked()){
                        itemCardForPrint.setSalePrc(""+PriceEditTextTag.getText().toString() );
                        itemCardForPrint.setFDPRC(convertToEnglish(numberFormat.format(Double.parseDouble(PriceEditTextTag.getText().toString()))));

                    }else{
                        if(SalesPriceCheckBoxTag.isChecked()){
                            itemCardForPrint.setSalePrc(""+salesPriceEditTextTag.getText().toString() );
                            itemCardForPrint.setFDPRC(convertToEnglish(numberFormat.format(Double.parseDouble(salesPriceEditTextTag.getText().toString()))));

                        }else
                        itemCardForPrint.setSalePrc("**");
                    }


                    if(nameCheckBoxTag.isChecked()){
                        itemCardForPrint.setIsName("");
                    }else{
                        itemCardForPrint.setIsName("**");
                    }

                    if(startCheckBoxTag.isChecked()){
                        itemCardForPrint.setIsStartDay(""+startEditTextTag.getText().toString() );

                    }else{
                        itemCardForPrint.setIsStartDay("**");
                    }

                    if(uniteCheckBoxTag.isChecked()){
                        //**************here unit*******************
                        itemCardForPrint.setIsUnite("");
                        itemCardForPrint.setItemUnit(uniteNames);

                    }else{
                        itemCardForPrint.setIsUnite("**");
                    }


//                   boolean Permission= isStoragePermissionGranted();
//                    if(Permission) {
                        Intent i = new Intent(Item.this, BluetoothConnectMenu.class);
                        i.putExtra("printKey", "0");
                        startActivity(i);

//                   Bitmap bitmap= convertLayoutToImage_shelfTag_Design3(itemCardForPrint);
//                    SendSocket sendSocket=new SendSocket(Item.this);
//                    sendSocket.sendMessage(bitmap);

//                    }else{
//                        TostMesage(getResources().getString(R.string.Permission));
//
//                    }
                    //////clear

//                    ItemCodeEditTextTag.setText("");
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            ItemCodeEditTextTag.requestFocus();
//                        }
//                    });
//                    PriceEditTextTag.setText("");
//                    ItemNameEditTextTag.setText("");
//                    pricePrint.setText("");

                }else{

                    TostMesage(getResources().getString(R.string.insertData));

                }

//                countText.setText("1");
                ItemCodeEditTextTag.setText("");
                ItemNameEditTextTag.setText("");
                PriceEditTextTag.setText("");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ItemCodeEditTextTag.requestFocus();
                    }
                });
//                ExpEditTextTag.setText(convertToEnglish(today));
                itemNamePrint.setText("Item Name");
                pricePrint.setText(" 0.00 JD");
                barcodeShelfPrint.setImageDrawable(getResources().getDrawable(R.drawable.barcodes));
                itemNamePrint2.setText("Item Name");
                pricePrint2.setText(" 0.00 JD");
//                barcodeShelfPrint2.setImageDrawable(getResources().getDrawable(R.drawable.barcodes));
                itemNamePrint3.setText("Item Name");
                pricePrint3.setText(" 0.00 JD");
//                barcodeShelfPrint3.setImageDrawable(getResources().getDrawable(R.drawable.barcodes));
                itemNamePrint4.setText("Item Name");
                pricePrint4.setText(" 0.00 JD");
            }
        });

        dialog.show();
    }

    void onEditorKeyPadChange(){

        boolean isItemFound = false;

        String itemCode = ItemCodeEditTextTag.getText().toString();
        String itemName = ItemNameEditTextTag.getText().toString();
        String codeFromText="";
        String itemSwitch = "",QRCode="",Price="",itemUnit="";
        if (!ItemCodeEditTextTag.getText().toString().equals("") && openShelfTag) {
//                        itemCardsList = InventDB.getAllItemCard();

            if(isOnlinePrice.equals("0")) {
                boolean isPriceUnite = false;
                if (itemCode.length() > 17) {
                    QRCode = itemCode;
                    itemCode = itemCode.substring(0, 16);
                    Log.e("String ", "" + itemCode);

                } else {
                    itemCode = ItemCodeEditTextTag.getText().toString();
                }

                List<ItemQR> QRList = findQRCode(itemCode, StkNo);

                if (QRList.size() != 0) {
                    itemCode = QRList.get(0).getItemCode();

                    Price = QRList.get(0).getSalesPrice();

                    ItemCodeEditTextTag.setText(itemCode);
                    isPriceUnite = true;
                } else {

                    if(settingCOName==1){
                        itemCode=ItemCodeEditTextTag.getText().toString();
                        try {
                            itemCode = itemCode.substring(0, 12);
                        }catch (Exception e){
                            itemCode=ItemCodeEditTextTag.getText().toString();
                        }
                        Log.e("itemCode1_",""+itemCode);
                    }else{
                        itemCode=ItemCodeEditTextTag.getText().toString();
                    }
                    Log.e("itemCode2_",""+itemCode);

                    codeFromText=itemCode;

                        List<String> itemUnite = findUnite(itemCode);
                        int uQty = 1;


                        if (itemUnite.size() != 0) {

                            itemCode = itemUnite.get(0);
                            uQty = Integer.parseInt(itemUnite.get(2));
                            Price = itemUnite.get(1);
                            itemUnit = itemUnite.get(3);
                            isPriceUnite = true;

                        } else {
                            itemSwitch = findSwitch(itemCode);
                            if (!itemSwitch.equals("")) {
                                itemCode = itemSwitch;
                            }
                            uQty = 1;
                            isPriceUnite = false;
                        }

                }

                boolean offer=false;

                List<OfferTable> offerTables=findOffer(itemCode);
                List<OfferTable> offerTablesO=findOffer(codeFromText);

                if(offerTablesO.size()!=0){

                    offer=true;
                    isPriceUnite=true;
//                    itemCode = offerTablesO.get(0).getITEMOCODE();
                    Price= convertToEnglish(numberFormat.format(Double.parseDouble(offerTablesO.get(0).getF_D())));


                }else
                if (offerTables.size()!=0) {
                    offer=true;
                    isPriceUnite=true;
//                    itemCode = offerTables.get(0).getITEMOCODE();
                    Price= convertToEnglish(numberFormat.format(Double.parseDouble(offerTables.get(0).getF_D())));

                }





                ItemCard itemCard = InventDB.getItemCardByBarCode(itemCode);
                String it = itemCard.getItemCode();
                Log.e("itemcard_oo", "" + it);
//                        for (int i = 0; i < itemCardsList.size(); i++) {
                if (!TextUtils.isEmpty(it)) {
                    if (it.equals(itemCode)) {
                        isItemFound = true;
                        ItemNameEditTextTag.setText(itemCard.getItemName());
                        //   ItemUnitEditTextTag.setText(itemUnit);
                        if (!isPriceUnite) {
                            String priceValue=convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())));
                            if(SalesPriceCheckBoxTag.isChecked())
                                priceValue=convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getSalePrc())));


                            salesPriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getSalePrc()))));

                            PriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))));

                            pricePrint.setText(priceValue+ " JD");

                            pricePrint2.setText(priceValue+ " JD");

                            pricePrint3.setText(priceValue+ " JD");

                            pricePrint4.setText(priceValue+ " JD");

                        } else {
                            salesPriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))));

                            PriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))));

                            pricePrint.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");

                            pricePrint2.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");

                            pricePrint3.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");
                            pricePrint4.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");

                        }
//                            ExpEditTextTag.setText(itemCardsList.get(i).g());
                        itemNamePrint.setText(itemCard.getItemName());
                        exp.setText(ExpEditTextTag.getText().toString());

                        itemNamePrint2.setText(itemCard.getItemName());
                        itemNamePrint3.setText(itemCard.getItemName());
                        itemNamePrint4.setText(itemCard.getItemName());
                        exp2.setText(ExpEditTextTag.getText().toString());
                        itemText.setText(ItemCodeEditTextTag.getText().toString());

                        try {
                            Bitmap bitmapBa = encodeAsBitmap(ItemCodeEditTextTag.getText().toString(), BarcodeFormat.CODE_128, 350, 100);
                            barcodeShelfPrint.setImageBitmap(bitmapBa);
                            barcodeShelfPrint2.setImageBitmap(bitmapBa);
                            barcodeShelfPrint3.setImageBitmap(bitmapBa);
                            barcodeShelfPrint4.setImageBitmap(bitmapBa);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

//                                break;

                    }

//                            else{
//
//                                ItemNameEditTextTag.setText("");
//                                PriceEditTextTag.setText("");
////                            ExpEditTextTag.setText(itemCardsList.get(i).g());
//                                itemNamePrint.setText("");
//                                pricePrint.setText("");
//
//
//                            }
//                        }
//////

                    //

                }

                if (isItemFound) {

                    //nothing

                } else {
//                        save.setClickable(false);
                    showAlertDialog(getResources().getString(R.string.itemNotFoundAlert));
                    ItemCodeEditTextTag.setText("");
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            ItemCodeEditTextTag.requestFocus();
                        }
                    });
                    PriceEditTextTag.setText("");
                    ItemNameEditTextTag.setText("");
                    //   ItemUnitEditTextTag.setText("");
                    pricePrint.setText("");
                }

            }else{

                textView=PriceEditTextTag;
                textItemName= ItemNameEditTextTag;

                importJson sendCloud = new importJson(Item.this,ItemCodeEditTextTag.getText().toString(),0,"","");
                sendCloud.startSending("ItemPrice");



            }


        }

    }
    void onEditorKeyPadChange2(){

        boolean isItemFound = false;

        String itemCode = ItemCodeEditTextTag.getText().toString();
        String itemName = ItemNameEditTextTag.getText().toString();

        String itemSwitch = "",QRCode="",Price="",itemUnit="";
        if (!ItemCodeEditTextTag.getText().toString().equals("") && openShelfTag) {
//                        itemCardsList = InventDB.getAllItemCard();


//                boolean isPriceUnite = false;
//                if (itemCode.length() > 17) {
//                    QRCode = itemCode;
//                    itemCode = itemCode.substring(0, 16);
//                    Log.e("String ", "" + itemCode);
//
//                } else {
//                    itemCode = ItemCodeEditTextTag.getText().toString();
//                }
//
//
//                if (importJson.onlineItems.size() != 0 ) {
//                    if(!importJson.onlineItems.get(0).getITEMU().equals("") )
//                    itemCode = importJson.onlineItems.get(0).getItemOCode();
//
//                    Price = importJson.onlineItems.get(0).getSalePrice();
//
//                    ItemCodeEditTextTag.setText(itemCode);
//                    isPriceUnite = true;
//                } else {
//
//                    if(settingCOName==1){
//                        itemCode=ItemCodeEditTextTag.getText().toString();
//                        try {
//                            itemCode = itemCode.substring(0, 12);
//                        }catch (Exception e){
//                            itemCode=ItemCodeEditTextTag.getText().toString();
//                        }
//                        Log.e("itemCode1_",""+itemCode);
//                    }else{
//                        itemCode=ItemCodeEditTextTag.getText().toString();
//                    }
//                    Log.e("itemCode2_",""+itemCode);
//
//
//
//                    List<String> itemUnite = findUnite(itemCode);
//                    int uQty = 1;
//
//
//
//
//                        itemCode =    importJson.onlineItems.get(0).getItemOCode();
//                        uQty =   1;
//                        Price = importJson.onlineItems.get(0).getSalePrice();
//                        itemUnit=importJson.onlineItems.get(0).getITEMU();
//                        isPriceUnite = true;
//
//                }
            boolean isPriceUnite = false;

                ItemCard itemCards = new ItemCard();
            itemCards.setItemCode(importJson.onlineItems.get(0).getItemOCode());
            itemCards.setItemName(importJson.onlineItems.get(0).getItemNameA());
            itemCards.setCostPrc(importJson.onlineItems.get(0).getF_D());
            itemCards.setSalePrc(importJson.onlineItems.get(0).getSalePrice());
            itemCards.setAVLQty(importJson.onlineItems.get(0).getAVLQTY());
            itemCards.setFDPRC(importJson.onlineItems.get(0).getF_D());
            Price=importJson.onlineItems.get(0).getF_D();
            itemCards.setBranchId("");
            itemCards.setBranchName("");
            itemCards.setDepartmentId("");
            itemCards.setDepartmentName("");
            itemCards.setItemG(importJson.onlineItems.get(0).getItemG());
            itemCards.setItemK(importJson.onlineItems.get(0).getItemK());
            itemCards.setItemL(importJson.onlineItems.get(0).getItemL());
            itemCards.setItemDiv(importJson.onlineItems.get(0).getITEMDIV());
            itemCards.setItemGs(importJson.onlineItems.get(0).getItemG());
            itemCards.setInDate(importJson.onlineItems.get(0).getInDate());
            itemCards.setOrgPrice("");
            itemCards.setIsExport("");
            itemCards.setIsNew("");
            itemCards.setItemM("");
            itemCards.setItemUnit(importJson.onlineItems.get(0).getITEMU());
            uniteNames=importJson.onlineItems.get(0).getITEMU();

                String it = itemCards.getItemCode();
                Log.e("itemcard_oo", "" + it);
//                        for (int i = 0; i < itemCardsList.size(); i++) {
                if (!TextUtils.isEmpty(it)) {
                    Log.e("itemcard_oo==", "" + it+"    "+itemCode);

                        isItemFound = true;
                        ItemNameEditTextTag.setText(itemCards.getItemName());
                        //   ItemUnitEditTextTag.setText(itemUnit);
                        if (!isPriceUnite) {
                            String priceValue=convertToEnglish(numberFormat.format(Double.parseDouble(itemCards.getFDPRC())));
                            if(SalesPriceCheckBoxTag.isChecked())
                                priceValue=convertToEnglish(numberFormat.format(Double.parseDouble(itemCards.getSalePrc())));


                            salesPriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCards.getSalePrc()))));

                            PriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCards.getFDPRC()))));

                            pricePrint.setText(priceValue+ " JD");

                            pricePrint2.setText(priceValue+ " JD");

                            pricePrint3.setText(priceValue+ " JD");

                            pricePrint4.setText(priceValue+ " JD");

                        } else {
                            salesPriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))));

                            PriceEditTextTag.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))));



                            pricePrint.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");

                            pricePrint2.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");

                            pricePrint3.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");
                            pricePrint4.setText(convertToEnglish(numberFormat.format(Double.parseDouble(Price))) + " JD");

                        }
//                            ExpEditTextTag.setText(itemCardsList.get(i).g());
                        itemNamePrint.setText(itemCards.getItemName());
                        exp.setText(ExpEditTextTag.getText().toString());

                        itemNamePrint2.setText(itemCards.getItemName());
                        itemNamePrint3.setText(itemCards.getItemName());
                        itemNamePrint4.setText(itemCards.getItemName());
                        exp2.setText(ExpEditTextTag.getText().toString());
                        itemText.setText(ItemCodeEditTextTag.getText().toString());
                        final List<String> listOfUnite1=new ArrayList<>();
                        listOfUnite1.add(itemCards.getItemUnit());
                        ArrayAdapter   uniteAdapter = new ArrayAdapter<String>(Item.this, R.layout.spinner_style, listOfUnite1);
                        uniteSpinner.setAdapter(uniteAdapter);

                        if(listOfUnite1.size()!=0)
                        {


                            uniteSpinner.setSelection(0);

                        }


                        try {
                            Bitmap bitmapBa = encodeAsBitmap(ItemCodeEditTextTag.getText().toString(), BarcodeFormat.CODE_128, 350, 100);
                            barcodeShelfPrint.setImageBitmap(bitmapBa);
                            barcodeShelfPrint2.setImageBitmap(bitmapBa);
                            barcodeShelfPrint3.setImageBitmap(bitmapBa);
                            barcodeShelfPrint4.setImageBitmap(bitmapBa);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

//                                break;



//                            else{
//
//                                ItemNameEditTextTag.setText("");
//                                PriceEditTextTag.setText("");
////                            ExpEditTextTag.setText(itemCardsList.get(i).g());
//                                itemNamePrint.setText("");
//                                pricePrint.setText("");
//
//
//                            }
//                        }
//////

                    //

                }

                if (isItemFound) {

                    //nothing

                } else {
//                        save.setClickable(false);
                    showAlertDialog(getResources().getString(R.string.itemNotFoundAlert));
                    ItemCodeEditTextTag.setText("");
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            ItemCodeEditTextTag.requestFocus();
                        }
                    });
                    PriceEditTextTag.setText("");
                    ItemNameEditTextTag.setText("");
                    //   ItemUnitEditTextTag.setText("");
                    pricePrint.setText("");
                }




        }

    }

    void showNewItemDialog(final int tr) {
        final Dialog dialogNew = new Dialog(Item.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogNew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNew.setCancelable(false);
        if(controll.isYellow){
            dialogNew.setContentView(R.layout.new_item_yellow);
        }else {
            dialogNew.setContentView(R.layout.new_item);
        }
//


        dialogNew.setCanceledOnTouchOutside(false);


        final LinearLayout exit, save, clear, serach, deleteItem, deleteAllItem;
        final EditText itemCodeNew, itemNameNew ,itemPrice;

        itemCodeNew = (EditText) dialogNew.findViewById(R.id.itemCNew);
        itemNameNew = (EditText) dialogNew.findViewById(R.id.itemNameNew);
        itemPrice=(EditText) dialogNew.findViewById(R.id.itemPrice);
//        ArrayList<ItemCard> itemCardList = new ArrayList<>();

        exit =  dialogNew.findViewById(R.id.exit);
        save =  dialogNew.findViewById(R.id.saveNew);
        clear =  dialogNew.findViewById(R.id.clearNew);
        serach =  dialogNew.findViewById(R.id.searchNew);
        deleteItem =  dialogNew.findViewById(R.id.deleteItem);
        deleteAllItem =  dialogNew.findViewById(R.id.deleteAllItem);


        Button barcode;
        barcode = dialogNew.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCodeRedar = true;
                openSave = false;
                readBarCode(itemCodeNew,3);
            }
        });


        itemCodeNew.requestFocus();

        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemCodeNew.getText().toString().equals("") && !itemNameNew.getText().toString().equals("")) {
                    alertMessageDialog(getResources().getString(R.string.delete), getResources().getString(R.string.delete_item_) +
                            "\n \n" + getResources().getString(R.string.item_name) + itemNameNew.getText().toString() + " \n"+ getResources().getString(R.string.delete_item_) + itemCodeNew.getText().toString() + getResources().getString(R.string.fromtable), 2, itemNameNew.getText().toString(), itemCodeNew.getText().toString());

                    itemCodeNew.setText("");
                    itemNameNew.setText("");
                    itemPrice.setText("");
                    itemCodeNew.requestFocus();
                    save.setClickable(true);

                } else
//                    Toast.makeText(Item.this, getResources().getString(R.string.insertData), Toast.LENGTH_SHORT).show();
                    TostMesage(getResources().getString(R.string.insertData));
            }
        });

        deleteAllItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertMessageDialog(getResources().getString(R.string.delete), getResources().getString(R.string.delete_all_item), 1, "", "");
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

        itemCodeNew.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
                    boolean isItemFound = false;
                    String itemCode = itemCodeNew.getText().toString();
                    String itemName = itemNameNew.getText().toString();
                    String itemSwitch = "";

                    if (!itemCodeNew.getText().toString().equals("") && openSave) {
                        itemCardsList = getItemCard();

                        if(settingCOName==1){
                            itemCode=itemCodeNew.getText().toString();
                            try {
                                itemCode = itemCode.substring(0, 12);
                            }catch (Exception e){
                                itemCode=itemCodeNew.getText().toString();
                            }
                            Log.e("itemCode1_",""+itemCode);
                        }else{
                            itemCode=itemCodeNew.getText().toString();
                        }
                        Log.e("itemCode2_",""+itemCode);




                        List<String>itemUnite=findUnite(itemCode);
                        int uQty=1;


                        if(itemUnite.size()!=0){

                            itemCode=itemUnite.get(0);
                            uQty=Integer.parseInt(itemUnite.get(2));

                        }else{
                            itemSwitch=findSwitch(itemCode);
                            if(!itemSwitch.equals("")){
                                itemCode=itemSwitch;
                            }
                            uQty=1;

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
                            showAlertDialog(getResources().getString(R.string.itemFoundAlert));
                        }


                    }
                }
                return false;
            }});


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


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemNameNew.getText().toString().equals("")) {
                    ItemCard itemCard = new ItemCard();

                    itemCard.setItemCode(itemCodeNew.getText().toString());
                    itemCard.setItemName(itemNameNew.getText().toString());
                    itemCard.setFDPRC(convertToEnglish(numberFormat.format(Double.parseDouble(itemPrice.getText().toString()))));
                    itemCard.setIsExport("0");
                    itemCard.setIsNew("1");
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
                    dd(itemCard);
                    prograseSave();
                    itemCodeNew.setText("");
                    itemNameNew.setText("");
                    itemPrice.setText("");
                    itemCodeNew.requestFocus();

                } else {
//                    Toast.makeText(Item.this, getResources().getString(R.string.insertData), Toast.LENGTH_SHORT).show();
                    TostMesage(getResources().getString(R.string.insertData));
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
                newItem.setClickable(true);
                openSave = false;
                switch (tr) {
                    case 0:
                        break;
//                    case 1:
//                        collDOpen = true;
//                        editText.requestFocus();
//                        editText.setText("");
//                        itemCardsList = InventDB.getAllItemCard();
//                        break;
//                    case 2:
//                        collExOpen = true;
//                        editText.requestFocus();
//                        editText.setText("");
//                        itemCardsList = InventDB.getAllItemCard();
//                        break;
//                    case 3:
//                        collReceipt = true;
//                        editText.requestFocus();
//                        editText.setText("");
//                        itemCardsList = InventDB.getAllItemCard();
//                        break;
                }

                dialogNew.dismiss();
            }
        });

        dialogNew.show();
    }
    private Bitmap convertLayoutToImage_shelfTag_Design3(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(Item.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(true);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog_design3);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName,price;//,BarcodeText;//,exp ;

        LinearLayout priceLiner;//ExpLiner,

//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner= (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
//        BarcodeText=(TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

//        BarcodeText.setText(itemCard.getItemCode());
        itemName.setText(itemCard.getItemName());
        if(itemCard.getSalePrc().equals("**")){
            priceLiner.setVisibility(View.INVISIBLE);
        }else{
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+"JD");
        }

//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 40);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }

    void alertMessageDialog(String title, String message, final int swith, final String itemName, final String ItemCode) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(Item.this,R.style.MyTheme);
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
//                                break;
//                            case 1:
//                                InventDB.deleteAllItem("ITEM_CARD");
////                                Toast.makeText(Item.this, getResources().getString(R.string.allItemDelete), Toast.LENGTH_SHORT).show();
//                                TostMesage(getResources().getString(R.string.allItemDelete));
////
////                             progressDialog();
//                                break;
//                            case 2:
//                                InventDB.delete(ItemCode, itemName);
//                                break;
//
//
//                        }
//                    }
//                }).show();



        new SweetAlertDialog(Item.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(Item.this.getResources().getString(R.string.areYouSurre)+title)
                .setContentText(message)
                .setConfirmText(title+"!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
//
                        switch (swith) {
                            case 0:
                                finish();
                                moveTaskToBack(true);
                                break;
                            case 1:
                                InventDB.deleteAllItem("ITEM_CARD");
//                                Toast.makeText(Item.this, getResources().getString(R.string.allItemDelete), Toast.LENGTH_SHORT).show();
                                TostMesage(getResources().getString(R.string.allItemDelete));
//
//                             progressDialog();
                                break;
                            case 2:
                                InventDB.delete(ItemCode, itemName);
                                break;


                        }
                        sDialog.dismissWithAnimation();

                    }
                })
                .setCancelButton(Item.this.getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();




    }

    void progressDialog() {

        final ProgressDialog progressDialog = new ProgressDialog(Item.this);
        progressDialog.setMessage("Saving Success ..."); // Setting Message
        progressDialog.setTitle("Saving ..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog

        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(980);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }


    void prograseSave(){

        final int[] i = {0};
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getResources().getString(R.string.lodaing));
        pDialog.show();
        pDialog.setCancelable(false);
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
                pDialog.setTitleText(getResources().getString(R.string.sucess))
                        .setConfirmText(getResources().getString(R.string.ok))
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        }.start();

    }



    @SuppressLint("ClickableViewAccessibility")
    void SearchDialog(final EditText itemCodeText, final int swSearch) {
        final Dialog dialogSearch = new Dialog(Item.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSearch.setCancelable(false);
        if(controll.isYellow){
            dialogSearch.setContentView(R.layout.search_dialog_yellow);
        }else{
            dialogSearch.setContentView(R.layout.search_dialog);
        }


        dialogSearch.setCanceledOnTouchOutside(false);

        LinearLayout exit =  dialogSearch.findViewById(R.id.exitsearch);
        final EditText itemCodeSearch = (EditText) dialogSearch.findViewById(R.id.itemCodeSearch);
        final ListView tabeSearch = (ListView) dialogSearch.findViewById(R.id.tableSearch);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                itemCodeSearch.requestFocus();
            }
        });



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

        ListAdapterSearch listAdapterSearch=new ListAdapterSearch(Item.this,itemCodeCard,companyNo);
        tabeSearch.setAdapter(listAdapterSearch);
        final ArrayList<ItemCard> ItemCodeCardSearch=new ArrayList<>();
        for (int i = 0; i < itemCodeCard.size(); i++) {
//            insertRowSearch(itemCodeCard.get(i).getItemName(), itemCodeCard.get(i).getItemCode(), tabeSearch, dialogSearch, itemCodeText, swSearch);
            ItemCard itemCard= itemCodeCard.get(i);
            ItemCodeCardSearch.add(itemCard);

        }


        Button barcode;
        barcode = dialogSearch.findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBarCodeRedar = true;
                openSearch = false;
                readBarCode(itemCodeSearch,4);
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
                    for (int i = 0; i < itemCodeCard.size(); i++) {
                        if (itemCodeCard.get(i).getItemCode().contains(itemCodeReader) || itemCodeCard.get(i).getItemName().contains(itemCodeReader)|| itemCodeCard.get(i).getItemL().contains(itemCodeReader)) {
//                            insertRowSearch(finalItemCodeCard.get(i).getItemName(), finalItemCodeCard.get(i).getItemCode(), tabeSearch, dialogSearch, itemCodeText, swSearch);
                          ItemCard itemCard= itemCodeCard.get(i);
                            ItemCodeCardSearch.add(itemCard);
                        }
                    }
                        ListAdapterSearch listAdapterSearch=new ListAdapterSearch(Item.this,ItemCodeCardSearch,companyNo);
                        tabeSearch.setAdapter(listAdapterSearch);
                }
            }else{
                    ListAdapterSearch listAdapterSearch=new ListAdapterSearch(Item.this,itemCodeCard,companyNo);
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
                    case 3:
                        openSave = true;
                        break;
                    case 2:
                        openShelfTag = true;
                        break;
                    case 1:
                        openBarcode = true;
                        searchEventBarcode();
                        break;
                    case 4:
                        openPrice = true;
                        break;
                    case 5:
                        openAssesst = true;
                        break;
                    case 6:
                        openCost = true;
                        break;
                }

//                Log.e("rowid,", "...." + "" + v.getId() + "----->" + text.getText().toString());

                try {
                    itemCodeText.setText(ItemCodeCardSearch.get(position).getItemCode());
                    switch (swSearch) {
                        case 3:
//                            openSave = true;
                            break;
                        case 2:
                            onEditorKeyPadChange();
//                            openShelfTag = true;
                            break;
                        case 1:
//                            openBarcode = true;
                            searchEventBarcode();
                            break;
                        case 4:
//                            openPrice = true;
                            break;
                        case 5:
//                            openAssesst = true;
                            break;
                        case 6:
//                            openCost = true;
                            break;
                    }
                    textId = 0;
                }catch (Exception e ){
                    Log.e("dialogSearch,",""+e.getMessage());

                }
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
                    case 1:
                        openBarcode = true;
                        break;
                    case 2:
                        openShelfTag = true;
                        break;
                    case 3:
                        openSave = true;
                        break;
                    case 4:
                        openPrice = true;
                        break;
                    case 5:
                        openAssesst = true;
                        break;
                    case 6:
                        openCost = true;
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
        final Dialog dialogSearch = new Dialog(Item.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
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

        ListAdapterSearchAssets listAdapterSearch=new ListAdapterSearchAssets(Item.this,itemAssetsCa,companyNo);
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

                openBarCodeRedar = true;
                openSearch = false;
                readBarCode(itemCodeSearch,4);
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
                        ListAdapterSearchAssets listAdapterSearch=new ListAdapterSearchAssets(Item.this,ItemCodeCardSearch,companyNo);
                        tabeSearch.setAdapter(listAdapterSearch);
                    }
                }else{
                    ListAdapterSearchAssets listAdapterSearch=new ListAdapterSearchAssets(Item.this,ItemCodeCardSearch,companyNo);
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
                    case 3:
                        openSave = true;
                        break;
                    case 2:
                        openShelfTag = true;
                        break;
                    case 1:
                        openBarcode = true;
                        break;
                    case 4:
                        openPrice = true;
                        break;
                    case 5:
                        openAssesst = true;
                        break;
                    case 6:
                        openCost = true;
                        break;
                }

//                Log.e("rowid,", "...." + "" + v.getId() + "----->" + text.getText().toString());

                itemCodeText.setText(ItemCodeCardSearch.get(position).getAssesstCode());


                switch (swSearch) {
                    case 3:
//                        openSave = true;
                        break;
                    case 2:
//                        openShelfTag = true;
                        break;
                    case 1:
//                        openBarcode = true;
                        break;
                    case 4:
//                        openPrice = true;
                        break;
                    case 5:
                        OnEditorKeyPadAssest();
//                        openAssesst = true;
                        break;
                    case 6:
//                        openCost = true;
                        break;
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
                    case 1:
                        openBarcode = true;
                        break;
                    case 2:
                        openShelfTag = true;
                        break;
                    case 3:
                        openSave = true;
                        break;
                    case 4:
                        openPrice = true;
                        break;
                    case 5:
                        openAssesst = true;
                        break;
                    case 6:
                        openCost = true;
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


        row = new TableRow(Item.this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        row.setPadding(0, 5, 0, 20);

        for (int i = 0; i < 2; i++) {
            final TextView textView = new TextView(Item.this);
            switch (i) {
                case 0:
                    textView.setText(itemCode);
                    break;
                case 1:
                    textView.setText(itemName);
                    break;


            }
            textView.setTextColor(ContextCompat.getColor(Item.this, R.color.textColor));
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
                    case 3:
                        openSave = true;
                        break;
                    case 2:
                        openShelfTag = true;
                        break;
                    case 1:
                        openBarcode = true;
                        break;
                    case 4:
                        openPrice = true;
                        break;
                    case 5:
                        openAssesst = true;
                        break;
                    case 6:
                        openCost = true;
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


    void showAlertDialog(String TextMessage) {
//        final Dialog dialogAlert = new Dialog(Item.this);
//        dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogAlert.setCancelable(false);
//        dialogAlert.setContentView(R.layout.alert_dialog_not_found);
//        dialogAlert.setCanceledOnTouchOutside(false);
//        TextView textMessage = (TextView) dialogAlert.findViewById(R.id.alertMessage);
//        Button exitAlert = (Button) dialogAlert.findViewById(R.id.exitAlert);
//        textMessage.setText(TextMessage);
//
//        dialogAlert.show();
//
//        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
//        tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
//
//        exitAlert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogAlert.dismiss();
//            }
//        });

        new SweetAlertDialog(Item.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getResources().getString(R.string.ops))
                .setContentText(TextMessage)
                .show();

        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);


    }



//    public void openFolder(){
//        Intent chooser = new Intent(Intent.ACTION_GET_CONTENT);
//        Uri uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString());
//        chooser.addCategory(Intent.CATEGORY_OPENABLE);
//        chooser.setDataAndType(uri, "*/*");
//// startActivity(chooser);
//        try {
//            startActivityForResult(chooser, READ_REQUEST_CODE);
//        }
//        catch (android.content.ActivityNotFoundException ex)
//        {
//            Toast.makeText(this, getResources().getString(R.string.insertData),
//                    Toast.LENGTH_SHORT).show();
//
//
//        }
//
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode,
//                                 Intent resultData) {
//
//        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
//        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
//        // response to some other intent, and the code below shouldn't run at all.
//
//        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//
//            Uri uri = null;
//            if (resultData != null) {
//                uri = resultData.getData();
//                Log.i("123*", "Uri: " + uri.toString());
//
//                pathNameFr.setText( uri.toString());
//
//            }
//        }
//    }


    public void Clickable() {

        newItem.setClickable(true);
        importText.setClickable(true);
        pBarcode.setClickable(true);
        pShelfTag.setClickable(true);
        ItemPrice.setClickable(true);
        Assesst.setClickable(true);

    }
    void TimeDialog(final TextView itemExpDate) {

        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(Item.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {

                selectedMonth = selectedMonth + 1;
                itemExpDate.setText(convertToEnglish("" + selectedDay + "/" + selectedMonth + "/" + selectedYear));

                tempText.setText(convertToEnglish("" + selectedDay + "/" + selectedMonth + "/" + selectedYear));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();

    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫","."));
        return newValue;
    }

    void TostMesage(String message){

        SweetAlertDialog sd2 = new SweetAlertDialog(this);
        sd2.setCancelable(true);
        sd2.setCanceledOnTouchOutside(true);
        sd2.setContentText(message);
        sd2.hideConfirmButton();
        sd2.show();


    }


    public void notClickable() {

        newItem.setClickable(false);
        importText.setClickable(false);
        pBarcode.setClickable(false);
        pShelfTag.setClickable(false);

    }

    void fillTableRows(TableLayout tableLayout, String ItemCode, String ItemName, String Price,int index) {


        row = new TableRow(Item.this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1f);
        row.setLayoutParams(lp);
        row.setPadding(0, 5, 0, 20);

        for (int i = 0; i < 4; i++) {
            final TextView textView = new TextView(Item.this);
            switch (i) {
                case 0:
                    CheckBox checkBox = new CheckBox(this);
                    TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    textViewParam.setMargins(2, 0, 2, 0);
                    checkBox.setLayoutParams(textViewParam);
                    checkBox.setText("");
                    checkBox.setTag(""+index);
                    checkBox.setTextColor(ContextCompat.getColor(this, R.color.white));
//                    checkBox.setBackgroundResource( R.color);
                    row.addView(checkBox);
                    break;
                case 1:
                    textView.setText(ItemCode);
                    break;
                case 2:
                    textView.setText(ItemName);
                    break;
                case 3:
                    textView.setText(Price);
                    break;



            }
            if(i!=0) {
                textView.setTextColor(ContextCompat.getColor(Item.this, R.color.textColor));
                textView.setGravity(Gravity.CENTER);

//                textView.setId(Integer.parseInt(textId + "" + i));

                TableRow.LayoutParams lp2 = new TableRow.LayoutParams(80, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                textView.setLayoutParams(lp2);

                row.addView(textView);

            }
        }
//        row.setId(textId);
//        row.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                TableRow rows = (TableRow) v.findViewById(v.getId());
//                TextView text = (TextView) rows.getChildAt(0);
//                openSearch = false;
//                switch (swSearch) {
//                    case 3:
//                        openSave = true;
//                        break;
//                    case 2:
//                        openShelfTag = true;
//                        break;
//                    case 2:
//                        openBarcode = true;
//                        break;
//                }
//
//                Log.e("rowid,", "...." + "" + v.getId() + "----->" + text.getText().toString());
//
//                itemCodeText.setText(text.getText().toString());
//                textId = 0;
//                dialogFinsh.dismiss();
//            }
//        });


        tableLayout.addView(row);
//        textId++;
    }

    public String findSwitch(String Item){

        String itemOCode= InventDB.getItemSwitch(Item);

        return itemOCode;
    }

    public List<ItemQR> findQRCode(String ItemQR,String strNo) {

        List<ItemQR> itemOCode = InventDB.getAllQRItem(ItemQR,strNo);

        return itemOCode;
    }

    public void readBarCode(TextView itemCodeText, int swBarcode) {

        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(Item.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(true);

        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();


//        scanActivity();

        openBarCodeRedar = false;
        switch (swBarcode) {
            case 0:
                openShelfTag = true;
                break;
            case 1:
                openBarcode = true;
                break;
            case 2:
                openPrice = true;
                break;
            case 3:
                openSave = true;
                break;
            case 4:
                openSearch = true;
                break;

            case 5:
                openAssesst = true;
                break;
            case 6:
                openCost = true;
                break;

//            case 6:
//                collTransfer = true;
//                break;
        }


    }

    private void scanActivity() {


        Intent i = new Intent(Item.this, ScanActivity.class);
        i.putExtra("key", "1");
        startActivity(i);
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
                Log.e("MainActivity", "Result.getContents()"+Result.getContents().toString());
//                Toast.makeText(this, getString(R.string.scan) + Result.getContents(), Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
                barCodTextTemp.setText(Result.getContents() + "");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public  List<String> findUnite(String Item){

        List<String> itemOCode= InventDB.getItemUnite(Item);

        return itemOCode;
    }


    void initialization() {
        Home=(TextView) findViewById(R.id.home);
        newItem = (LinearLayout) findViewById(R.id.newItem);
        importText = (LinearLayout) findViewById(R.id.impText);
        pBarcode = (LinearLayout) findViewById(R.id.barcode);
        pShelfTag = (LinearLayout) findViewById(R.id.shelf);
        ItemPrice= (LinearLayout) findViewById(R.id.ItemPrice);
        ItemCost= (LinearLayout) findViewById(R.id.ItemCost);
        CaptionLayout=findViewById(R.id.CaptionLayout);
        Assesst= (LinearLayout) findViewById(R.id.Assesst);

        importText.setVisibility(View.GONE);
        newItem.setOnClickListener(showDialogOnClick);
        CaptionLayout.setOnClickListener(showDialogOnClick);
//        importText.setOnClickListener(showDialogOnClick);
        pBarcode.setOnClickListener(showDialogOnClick);
        pShelfTag.setOnClickListener(showDialogOnClick);
        ItemPrice.setOnClickListener(showDialogOnClick);
        Assesst.setOnClickListener(showDialogOnClick);
        ItemCost.setOnClickListener(showDialogOnClick);
        Clickable();
        mainSettingsList=new ArrayList<>();
        mainSettingsList= InventDB.getAllMainSetting();
        if(mainSettingsList.size()!=0){
            settingCOName=mainSettingsList.get(0).getCoName();
        }else {
            settingCOName=0;
        }

    }

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK :TRANSPARENT;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

//    public boolean isStoragePermissionGranted() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                Log.v("", "Permission is granted");
//                return true;
//            } else {
//
//                Log.v("", "Permission is revoked");
//                ActivityCompat.requestPermissions(
//                        this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        1);
//                return false;
//            }
//        } else { // permission is automatically granted on sdk<23 upon
//            // installation
//            Log.v("", "Permission is granted");
//            return true;
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Log.v("", "Permission: " + permissions[0] + "was "
//                    + grantResults[0]);
//            // resume tasks needing this permission
////
////            File file = null;
////            try {
////                file = createPdf();
////                PrintAll(file);
////                bundleInfoForPrint.clear();
////            } catch (IOException e) {
////                e.printStackTrace();
////            } catch (DocumentException e) {
////                e.printStackTrace();
////            }
//
//        }
//
//    }

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





    public void dd(ItemCard list){
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "InventoryDBase") .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        UserDaoCard userDao = db.itemCard();

        userDao.insertUsers(list);


    }

    public List<ItemCard> getItemCard(){

       // UserDaoCard userDao = db.itemCard();

       return  db.itemCard().getAll();


    }

}
