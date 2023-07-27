package com.example.user54.InventoryApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.example.user54.InventoryApp.Model.CaptionItemInfo;
import com.example.user54.InventoryApp.Model.GetCaptionModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;


public class CaptionLayout extends AppCompatActivity {
    Spinner Desc1, Desc2, Desc3, Desc4, Desc5, Desc6, Desc7, Desc8, Desc9,
            Desc10, Desc11, Desc12, Desc13, Desc14;
    TextView N1, N2, N3, N4, N5, N6, N7, N8, N9,
            N10, N11, N12, N13, N14;
    importJson importJ;
    CaptionListAdapter adapter;
    ListView capList;
    List<GetCaptionModel> AllData=new ArrayList<>();
    NestedScrollView scrollView;

    String DS1="", DS2="", DS3="", DS4="", DS5="", DS6="", DS7="", DS8="", DS9="", DS10="",
            DS11="", DS12="", DS13="", DS14="",ITEMOCODEs="";

   LinearLayout getData,exit;
   EditText ItemCode,barCodTextTemp;
   Button barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caption_layout_xml);
        Initialization();

    }

    private void Initialization() {
        Desc1  = findViewById(R.id.D1);
        Desc2  = findViewById(R.id.D2);
        Desc3  = findViewById(R.id.D3);
        Desc4  = findViewById(R.id.D4);
        Desc5  = findViewById(R.id.D5);
        Desc6  = findViewById(R.id.D6);
        Desc7  = findViewById(R.id.D7);
        Desc8  = findViewById(R.id.D8);
        Desc9  = findViewById(R.id.D9);
        Desc10 = findViewById(R.id.D10);
        Desc11 = findViewById(R.id.D11);
        Desc12 = findViewById(R.id.D12);
        Desc13 = findViewById(R.id.D13);
        Desc14 = findViewById(R.id.D14);


        N1  = findViewById(R.id.N1);
        N2  = findViewById(R.id.N2);
        N3  = findViewById(R.id.N3);
        N4  = findViewById(R.id.N4);
        N5  = findViewById(R.id.N5);
        N6  = findViewById(R.id.N6);
        N7  = findViewById(R.id.N7);
        N8  = findViewById(R.id.N8);
        N9  = findViewById(R.id.N9);
        N10 = findViewById(R.id.N10);
        N11 = findViewById(R.id.N11);
        N12 = findViewById(R.id.N12);
        N13 = findViewById(R.id.N13);
        N14 = findViewById(R.id.N14);

        ItemCode=findViewById(R.id.ItemCodeEditTextTag);
        barcode=findViewById(R.id.barcode);

        capList=findViewById(R.id.capList);
//        scrollView=findViewById(R.id.NscrollView);
        exit=findViewById(R.id.exit);
        getData=findViewById(R.id.getData);
         importJ=new importJson(CaptionLayout.this,"",1,"","");
        importJ.startSending("CAPTION");

        //4   CmbBUnit.ItemIndex     := CmbBUnit.IndexOf(Trim(FieldByName('ItemU').AsString));
        //1   CmbBGroup.ItemIndex    := CmbBGroup.IndexOf(Trim(FieldByName('ItemG').AsString));
        //2   CmbBType.ItemIndex     := CmbBType.IndexOf(Trim(FieldByName('ItemK').AsString));
        //3   CmbBModal.ItemIndex    := CmbBModal.IndexOf(Trim(FieldByName('ItemM').AsString));
        //5   CmbBLocation.ItemIndex := CmbBLocation.IndexOf(Trim(FieldByName('ItemL').AsString));
        //6   CmbBSubGroup.ItemIndex := CmbBSubGroup.IndexOf(Trim(FieldByName('ITEMGS').AsString));
        //7   CmbBSection.ItemIndex  := CmbBSection.IndexOf(Trim(FieldByName('ITEMDIV').AsString));
        //8   CmbBSub1.ItemIndex     := CmbBSub1.IndexOf(Trim(FieldByName('ITEMSUB1').AsString));
        //9   CmbBSub2.ItemIndex     := CmbBSub2.IndexOf(Trim(FieldByName('ITEMSUB2').AsString));
        //10  CmbBSub3.ItemIndex     := CmbBSub3.IndexOf(Trim(FieldByName('ITEMSUB3').AsString));
        //11  CmbBSub4.ItemIndex     := CmbBSub4.IndexOf(Trim(FieldByName('ITEMSUB4').AsString));
        //12  CmbBSub5.ItemIndex     := CmbBSub5.IndexOf(Trim(FieldByName('ITEMSUB5').AsString));
        //13  CmbBSub6.ItemIndex     := CmbBSub6.IndexOf(Trim(FieldByName('ITEMSUB6').AsString));
        //14  CmbBSub7.ItemIndex     := CmbBSub7.IndexOf(Trim(FieldByName('ITEMSUB7').AsString));

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readBarCode(ItemCode);
            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////CONO=290&ItemU=&ItemG=&ItemK=&ItemM=&ItemL=&ITEMGS=&ITEMDIV=&ITEMSUB1=&ITEMSUB2=&ITEMSUB3=&ITEMSUB4=&ITEMSUB5=&ITEMSUB6=&ITEMSUB7
                if(!ItemCode.getText().toString().equals("")) {
                    ITEMOCODEs=ItemCode.getText().toString();
                }else {
                    ITEMOCODEs="";
                }
                importJ=new importJson(CaptionLayout.this,"",1,"","");
                importJ.getDataCap(DS4,DS1,DS2,DS3,DS5,DS6,DS7,DS8,DS9,DS10,DS11,DS12,DS13,DS14,ITEMOCODEs);
            }
        });


        capList.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                Log.v(TAG, "CHILD TOUCH");
//
//                // Disallow the touch request for parent scroll on touch of  child view
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Desc1  .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc2  .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc3  .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc4  .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc5  .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc6  .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc7  .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc8  .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc9  .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc10 .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc11 .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc12 .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc13 .setOnItemSelectedListener(new OnItemSelectedCodepage());
        Desc14 .setOnItemSelectedListener(new OnItemSelectedCodepage());


    }


    private class OnItemSelectedCodepage implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3)
        {

            Log.e("inSelect","up");

            switch (arg0.getId()){

                case R.id.D1:
                     DS1=AllData.get(0).getDescList().get(arg2).toString();
                    Log.e("inSelect","up => "+DS1);
                    break;
                case R.id.D2:
                    DS2=AllData.get(1).getDescList().get(arg2).toString();
                    Log.e("inSelect","up2 => "+DS2);
                    break;
                case R.id.D3:
                    DS3=AllData.get(2).getDescList().get(arg2).toString();
                    Log.e("inSelect","up3 => "+DS3);

                    break;
                case R.id.D4:
                    DS4=AllData.get(3).getDescList().get(arg2).toString();

                    break;
                case R.id.D5:
                    DS5=AllData.get(4).getDescList().get(arg2).toString();

                    break;
                case R.id.D6:
                    DS6=AllData.get(5).getDescList().get(arg2).toString();

                    break;
                case R.id.D7:
                    DS7=AllData.get(6).getDescList().get(arg2).toString();

                    break;
                case R.id.D8:

                    DS8=AllData.get(7).getDescList().get(arg2).toString();

                    break;
                case R.id.D9:
                    DS9=AllData.get(8).getDescList().get(arg2).toString();

                    break;
                case R.id.D10:
                    DS10=AllData.get(9).getDescList().get(arg2).toString();

                    break;
                case R.id.D11:
                    DS11=AllData.get(10).getDescList().get(arg2).toString();

                    break;
                case R.id.D12:
                    DS12=AllData.get(11).getDescList().get(arg2).toString();

                    break;
                case R.id.D13:
                    DS13=AllData.get(12).getDescList().get(arg2).toString();

                    break;
                case R.id.D14:
                    DS14=AllData.get(13).getDescList().get(arg2).toString();

                    break;


            }

        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0)
        {
            // TODO Auto-generated method stub
        }
    }


    void  fillCaption(List<GetCaptionModel> list){

        AllData=list;

        if(list.size()!=0) {
            try {


                N1.setText("" + list.get(0).getDESCNAMEA());
                N2.setText("" + list.get(1).getDESCNAMEA());
                N3.setText("" + list.get(2).getDESCNAMEA());
                N4.setText("" + list.get(3).getDESCNAMEA());
                N5.setText("" + list.get(4).getDESCNAMEA());
                N6.setText("" + list.get(5).getDESCNAMEA());
                N7.setText("" + list.get(6).getDESCNAMEA());
                N8.setText("" + list.get(7).getDESCNAMEA());
                N9.setText("" + list.get(8).getDESCNAMEA());
                N10.setText("" + list.get(9).getDESCNAMEA());
                N11.setText("" + list.get(10).getDESCNAMEA());
                N12.setText("" + list.get(11).getDESCNAMEA());
                N13.setText("" + list.get(12).getDESCNAMEA());
                N14.setText("" + list.get(13).getDESCNAMEA());


                ArrayAdapter Apd1= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(0).getDescList());
                Desc1.setAdapter(Apd1);
                ArrayAdapter Apd2= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(1).getDescList());
                Desc2.setAdapter(Apd2);
                ArrayAdapter Apd3= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(2).getDescList());
                Desc3.setAdapter(Apd3);
                ArrayAdapter Apd4= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(3).getDescList());
                Desc4.setAdapter(Apd4);
                ArrayAdapter Apd5= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(4).getDescList());
                Desc5.setAdapter(Apd5);
                ArrayAdapter Apd6= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(5).getDescList());
                Desc6.setAdapter(Apd6);
                ArrayAdapter Apd7= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(6).getDescList());
                Desc7.setAdapter(Apd7);
                ArrayAdapter Apd8= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(7).getDescList());
                Desc8.setAdapter(Apd8);
                ArrayAdapter Apd9= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(8).getDescList());
                Desc9.setAdapter(Apd9);
                ArrayAdapter Apd10= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(9).getDescList());
                Desc10.setAdapter(Apd10);
                ArrayAdapter Apd11= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(10).getDescList());
                Desc11.setAdapter(Apd11);
                ArrayAdapter Apd12= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(11).getDescList());
                Desc12.setAdapter(Apd12);
                ArrayAdapter Apd13= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(12).getDescList());
                Desc13.setAdapter(Apd13);
                ArrayAdapter Apd14= new ArrayAdapter<String>(CaptionLayout.this, R.layout.spinner_style, list.get(13).getDescList());
                Desc14.setAdapter(Apd14);



            } catch (Exception e) {

            }


        }

    }


   public void  fillArrayDataAfterFillter(List<CaptionItemInfo> captionItemInfos){

        adapter = new CaptionListAdapter(CaptionLayout.this, captionItemInfos);
       capList.setAdapter(adapter);
    }


    public void readBarCode(EditText itemCodeText) {

        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(CaptionLayout.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(true);

        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Log.d("MainActivity", "cancelled scan");
//                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, getResources().getString(R.string.cancel), Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Log.e("MainActivity", "Result.getContents()"+Result.getContents().toString());
//
                barCodTextTemp.setText(Result.getContents() + "");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
