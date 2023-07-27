package com.example.user54.InventoryApp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user54.InventoryApp.Model.AssestItem;
import com.example.user54.InventoryApp.Model.ItemInfo;
import com.example.user54.InventoryApp.Model.MainSetting;
import com.example.user54.InventoryApp.Port.AlertView;
import com.example.user54.InventoryApp.Model.ItemCard;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.sewoo.request.android.RequestHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.example.user54.InventoryApp.Item.prinS;
import static com.example.user54.InventoryApp.Item.prinSa;
import static com.example.user54.InventoryApp.Item.shelfTagLinerTests;
import static com.example.user54.InventoryApp.Report.ItemInfoListForPrint;
import static com.example.user54.InventoryApp.Report.preparAc;


// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)

public class BluetoothConnectMenu extends Activity {
    private static final String TAG = "BluetoothConnectMenu";
    private static final int REQUEST_ENABLE_BT = 2;
    ArrayAdapter<String> adapter;
    private BluetoothAdapter mBluetoothAdapter;
    private Vector<BluetoothDevice> remoteDevices;
    private BroadcastReceiver searchFinish;
    private BroadcastReceiver searchStart;
    private BroadcastReceiver discoveryResult;
    private BroadcastReceiver disconnectReceiver;
    private Thread hThread;
    private Context context;
    private EditText btAddrBox;
    private Button connectButton;
    private Button searchButton;

    LinearLayout item;
    private ListView list;
    private BluetoothPort bluetoothPort;
    private CheckBox chkDisconnect;
    private static final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "//temp";
    private static final String fileName;
    private String lastConnAddr;
    static String idname;

    String rotate="0";
    int resize,wid,hi;
    String getData;
    String today;
    List<Item> long_listItems;

    List<Item> itemforPrint;
    String Currency="JD";
    int companyNo=0;

    DecimalFormat decimalFormat;

    DecimalFormat numberFormat = new DecimalFormat("0.00");
//    DecimalFormat numberFormat = new DecimalFormat("#,###,###");
    //DecimalFormat numberFormat = new DecimalFormat("0.##");



    static {
        fileName = dir + "//BTPrinter";
    }

    public BluetoothConnectMenu() {

    }

    private void bluetoothSetup() {
        this.clearBtDevData();
        this.bluetoothPort = BluetoothPort.getInstance();
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter != null) {
            if (!this.mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
                this.startActivityForResult(enableBtIntent, 2);
            }

        }
    }

    private void loadSettingFile() {
//        int rin = false;
        char[] buf = new char[128];

        try {
            FileReader fReader = new FileReader(fileName);
            int rin = fReader.read(buf);
            if (rin > 0) {
                this.lastConnAddr = new String(buf, 0, rin);
                this.btAddrBox.setText(this.lastConnAddr);
            }

            fReader.close();
        } catch (FileNotFoundException var4) {
            Log.i("BluetoothConnectMenu", "Connection history not exists.");
        } catch (IOException var5) {
            Log.e("BluetoothConnectMenu", var5.getMessage(), var5);
        }

    }

    private void saveSettingFile() {
        try {
            File tempDir = new File(dir);
            if (!tempDir.exists()) {
                tempDir.mkdir();
            }

            FileWriter fWriter = new FileWriter(fileName);
            if (this.lastConnAddr != null) {
                fWriter.write(this.lastConnAddr);
            }

            fWriter.close();
        } catch (FileNotFoundException var3) {
            Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
        } catch (IOException var4) {
            Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
        }

    }

    private void clearBtDevData() {
        this.remoteDevices = new Vector();
    }

    @SuppressLint("MissingPermission")
    private void addPairedDevices() {
        Iterator iter = this.mBluetoothAdapter.getBondedDevices().iterator();

        while (iter.hasNext()) {
            BluetoothDevice pairedDevice = (BluetoothDevice) iter.next();
            // if (this.bluetoothPort.isValidAddress(pairedDevice.getAddress())) {//note
            Log.e("bloututhyyyy", "paierd =" + pairedDevice.getAddress());
            this.remoteDevices.add(pairedDevice);
            this.adapter.add(pairedDevice.getName() + "\n[" + pairedDevice.getAddress() + "] [Paired]");
            //  }
        }

    }

    double size_subList = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bluetooth_menu);
        this.btAddrBox = (EditText) this.findViewById(R.id.EditTextAddressBT);
        this.connectButton = (Button) this.findViewById(R.id.ButtonConnectBT);
        BluetoothConnectMenu.this.connectButton.setEnabled(true);
        this.searchButton = (Button) this.findViewById(R.id.ButtonSearchBT);
        this.list = (ListView) this.findViewById(R.id.BtAddrListView);
        this.chkDisconnect = (CheckBox) this.findViewById(R.id.check_disconnect);
        this.chkDisconnect.setChecked(true);
        this.context = this;
        item = this.findViewById(R.id.item);
//        obj = new DatabaseHandler(BluetoothConnectMenu.this);
        long_listItems = new ArrayList<Item>();
        decimalFormat = new DecimalFormat("##.000");

        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        today = df.format(currentTimeAndDate);


//
        getData = getIntent().getStringExtra("printKey");






//        Bundle bundle = getIntent().getExtras();
//         allStudents = (List<Item>) bundle.get("ExtraData");
//
//         Log.e("all",allStudents.get(0).getBarcode());

        Log.e("printKey", "" + getData);
        this.loadSettingFile();
        this.bluetoothSetup();

        this.connectButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!BluetoothConnectMenu.this.bluetoothPort.isConnected()) {
                    try {
                        BluetoothConnectMenu.this.btConn(BluetoothConnectMenu.this.mBluetoothAdapter.getRemoteDevice(remoteDevices.get(0).getAddress()));
                    } catch (IllegalArgumentException var3) {
                        Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
                        AlertView.showAlert(var3.getMessage(), BluetoothConnectMenu.this.context);
                        return;
                    } catch (IOException var4) {
                        Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                        AlertView.showAlert(var4.getMessage(), BluetoothConnectMenu.this.context);
                        return;
                    }
                } else {
                    BluetoothConnectMenu.this.btDisconn();
                }

            }
        });
        this.searchButton.setOnClickListener(new OnClickListener() {
            @SuppressLint("MissingPermission")
            public void onClick(View v) {
                if (!BluetoothConnectMenu.this.mBluetoothAdapter.isDiscovering()) {
                    BluetoothConnectMenu.this.clearBtDevData();
                    BluetoothConnectMenu.this.adapter.clear();
                    BluetoothConnectMenu.this.mBluetoothAdapter.startDiscovery();
                } else {
                    BluetoothConnectMenu.this.mBluetoothAdapter.cancelDiscovery();
                }

            }
        });
        this.adapter = new ArrayAdapter(BluetoothConnectMenu.this, R.layout.cci);

        this.list.setAdapter(this.adapter);
        this.addPairedDevices();
        this.list.setOnItemClickListener(new OnItemClickListener() {
            @SuppressLint("MissingPermission")
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                BluetoothDevice btDev = (BluetoothDevice) BluetoothConnectMenu.this.remoteDevices.elementAt(arg2);

                try {
                    if (BluetoothConnectMenu.this.mBluetoothAdapter.isDiscovering()) {
                        BluetoothConnectMenu.this.mBluetoothAdapter.cancelDiscovery();
                    }

                    BluetoothConnectMenu.this.btAddrBox.setText(btDev.getAddress());
                    BluetoothConnectMenu.this.btConn(btDev);
                } catch (IOException var8) {
                    AlertView.showAlert(var8.getMessage(), BluetoothConnectMenu.this.context);
                }
            }
        });
        this.discoveryResult = new BroadcastReceiver() {
            @SuppressLint("MissingPermission")
            public void onReceive(Context context, Intent intent) {
                BluetoothDevice remoteDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (remoteDevice != null) {
                    String key;
                    if (remoteDevice.getBondState() != 12) {
                        key = remoteDevice.getName() + "\n[" + remoteDevice.getAddress() + "]";
                    } else {
                        key = remoteDevice.getName() + "\n[" + remoteDevice.getAddress() + "] [Paired]";
                    }

                    if (BluetoothConnectMenu.this.bluetoothPort.isValidAddress(remoteDevice.getAddress())) {
                        BluetoothConnectMenu.this.remoteDevices.add(remoteDevice);
                        BluetoothConnectMenu.this.adapter.add(key);
                    }
                }

            }
        };
        this.registerReceiver(this.discoveryResult, new IntentFilter("android.bluetooth.device.action.FOUND"));
        this.searchStart = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                BluetoothConnectMenu.this.connectButton.setEnabled(false);
                BluetoothConnectMenu.this.btAddrBox.setEnabled(false);
//                BluetoothConnectMenu.this.searchButton.setText(BluetoothConnectMenu.this.getResources().getString(2131034114));

                BluetoothConnectMenu.this.searchButton.setText("stop ");
            }
        };
        this.registerReceiver(this.searchStart, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_STARTED"));
        this.searchFinish = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                BluetoothConnectMenu.this.connectButton.setEnabled(true);
                BluetoothConnectMenu.this.btAddrBox.setEnabled(true);
//                BluetoothConnectMenu.this.searchButton.setText(BluetoothConnectMenu.this.getResources().getString(2131034113));
                BluetoothConnectMenu.this.searchButton.setText("search");

            }
        };
        this.registerReceiver(this.searchFinish, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        if (this.chkDisconnect.isChecked()) {
            this.disconnectReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (!"android.bluetooth.device.action.ACL_CONNECTED".equals(action) && "android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                        BluetoothConnectMenu.this.DialogReconnectionOption();
                    }

                }
            };
        }
        item.setVisibility(View.GONE);
        if (remoteDevices.size() != 0) {
            coon();
        } else {
            Toast.makeText(context, "Please Connect to Bluetooth ", Toast.LENGTH_SHORT).show();
        }

    }

    public void coon() {
        if (!BluetoothConnectMenu.this.bluetoothPort.isConnected()) {
            try {
                BluetoothConnectMenu.this.btConn(BluetoothConnectMenu.this.mBluetoothAdapter.getRemoteDevice(remoteDevices.get(0).getAddress()));
            } catch (IllegalArgumentException var3) {
                Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
                AlertView.showAlert(var3.getMessage(), BluetoothConnectMenu.this.context);
                return;
            } catch (IOException var4) {
                Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                AlertView.showAlert(var4.getMessage(), BluetoothConnectMenu.this.context);
                return;
            }
        } else {
            BluetoothConnectMenu.this.btDisconn();
        }
    }

    protected void onDestroy() {
        try {
            if (this.bluetoothPort.isConnected() && this.chkDisconnect.isChecked()) {
                this.unregisterReceiver(this.disconnectReceiver);
            }

            this.saveSettingFile();
            this.bluetoothPort.disconnect();
        } catch (IOException var2) {
            Log.e("BluetoothConnectMenu", var2.getMessage(), var2);
        } catch (InterruptedException var3) {
            Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
        }

        if (this.hThread != null && this.hThread.isAlive()) {
            this.hThread.interrupt();
            this.hThread = null;
        }

        this.unregisterReceiver(this.searchFinish);
        this.unregisterReceiver(this.searchStart);
        this.unregisterReceiver(this.discoveryResult);
        super.onDestroy();
    }

    private void DialogReconnectionOption() {
        String[] items = new String[]{"Bluetooth printer"};
        Builder builder = new Builder(this);
        builder.setTitle("connection ...");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).setPositiveButton("connect", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    BluetoothConnectMenu.this.btDisconn();
                    BluetoothConnectMenu.this.btConn(BluetoothConnectMenu.this.mBluetoothAdapter.getRemoteDevice(BluetoothConnectMenu.this.btAddrBox.getText().toString()));
                } catch (IllegalArgumentException var4) {
                    Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                    AlertView.showAlert(var4.getMessage(), BluetoothConnectMenu.this.context);
                } catch (IOException var5) {
                    Log.e("BluetoothConnectMenu", var5.getMessage(), var5);
                    AlertView.showAlert(var5.getMessage(), BluetoothConnectMenu.this.context);
                }
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                BluetoothConnectMenu.this.btDisconn();
            }
        });
        builder.show();
    }

    private void btConn(BluetoothDevice btDev) throws IOException {
        (new BluetoothConnectMenu.connTask()).execute(new BluetoothDevice[]{btDev});
    }

    private void btDisconn() {
        try {
            this.bluetoothPort.disconnect();
            if (this.chkDisconnect.isChecked()) {
                this.unregisterReceiver(this.disconnectReceiver);
            }
        } catch (Exception var2) {
            Log.e("BluetoothConnectMenu", var2.getMessage(), var2);
        }

        if (this.hThread != null && this.hThread.isAlive()) {
            this.hThread.interrupt();
        }

        this.connectButton.setText("Connect");
        this.list.setEnabled(true);
        this.btAddrBox.setEnabled(true);
        this.searchButton.setEnabled(true);
        Toast toast = Toast.makeText(this.context, "disconnect", Toast.LENGTH_SHORT);
        toast.show();
    }

    class connTask extends AsyncTask<BluetoothDevice, Void, Integer> {
        private final SweetAlertDialog dialog = new SweetAlertDialog(BluetoothConnectMenu.this, SweetAlertDialog.PROGRESS_TYPE);

        connTask() {
        }

        protected void onPreExecute() {


            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText(" Connect to printer ...");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(BluetoothDevice... params) {
            Integer retVal = null;

            try {
                BluetoothConnectMenu.this.bluetoothPort.connect(params[0]);
                BluetoothConnectMenu.this.lastConnAddr = params[0].getAddress();

                retVal = 0;
            } catch (IOException var4) {
                Log.e("BluetoothConnectMenu", var4.getMessage());
                retVal = -1;
            }

            return retVal;
        }

        @SuppressLint("WrongThread")
        protected void onPostExecute(Integer result) {
            if (result == 0) {
                RequestHandler rh = new RequestHandler();
                BluetoothConnectMenu.this.hThread = new Thread(rh);
                BluetoothConnectMenu.this.hThread.start();
                BluetoothConnectMenu.this.connectButton.setText("Connect");
                BluetoothConnectMenu.this.connectButton.setEnabled(false);
                BluetoothConnectMenu.this.list.setEnabled(false);
                BluetoothConnectMenu.this.btAddrBox.setEnabled(false);
                BluetoothConnectMenu.this.searchButton.setEnabled(false);
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }


//                Toast toast = Toast.makeText(context, "Now Printing ", Toast.LENGTH_SHORT);
//                toast.show();
                try {
//                int count = Integer.parseInt(getData);
                    CPCLSample2 sample = new CPCLSample2(BluetoothConnectMenu.this);
                    String PrintType = "0";
                   String  numberType="0";
                    try {
                        controll co=new controll();
                        int dataNo= Integer.parseInt(co.readFromFile(BluetoothConnectMenu.this));

                        InventoryDatabase InventDB = new InventoryDatabase(context,dataNo);
                        final List<MainSetting> mainSettings = InventDB.getAllMainSetting();

                        if (mainSettings.size() != 0) {
                            PrintType = mainSettings.get(0).getPrinterType();
                            Currency=mainSettings.get(0).getCurrencyType();
                            companyNo=mainSettings.get(0).getCoName();
                            numberType=mainSettings.get(0).getNumberType();
                            rotate=mainSettings.get(0).getRotate();
                            resize=mainSettings.get(0).getReSize();
                            wid=mainSettings.get(0).getWidth();
                            hi=mainSettings.get(0).getHeight();
                        } else {
                            PrintType = "0";
                            Currency="JD";
                            companyNo=0;
                            numberType="0";
                            rotate="0";
                            resize=0;
                        }

                    } catch (Exception e) {
                        PrintType = "0";
                        Currency="JD";
                        companyNo=0;
                        numberType="0";
                        rotate="0";
                        resize=0;

                    }
                    if (numberType.equals("0")) {
                        numberFormat = new DecimalFormat("0.000");
                    } else if (numberType.equals("1")) {
                        numberFormat = new DecimalFormat("0.##");
                    } else if (numberType.equals("2")) {
                        numberFormat = new DecimalFormat("#,###,###");
                    }





                    switch (Integer.parseInt(getData)) {

                        case 0:

                            if (PrintType.equals("0") || PrintType.equals("2") || PrintType.equals("3")) {
                                sample.selectBlackMarkPaper();
                                if (PrintType.equals("2")) {
                                    //sample.imageTestTSC(remoteDevices.get(0).getAddress(),1);
                                }
                            }


                            Bitmap bitmap = null;

                            if(companyNo==0||companyNo==1){
                            if (Item.itemCardForPrint.getItemG().equals("0")) {
                                bitmap = convertLayoutToImage_shelfTag(Item.itemCardForPrint);
                                Log.e("Count = ", "" + Item.itemCardForPrint.getFDPRC());


                            } else if (Item.itemCardForPrint.getItemG().equals("1")) {
                                bitmap = convertLayoutToImage_shelfTag_Design2(Item.itemCardForPrint);
                                Log.e("Count = ", "" + Item.itemCardForPrint.getFDPRC());
                            } else if (Item.itemCardForPrint.getItemG().equals("2")) {
                                bitmap = convertLayoutToImage_shelfTag_Design3(Item.itemCardForPrint);
                                Log.e("Count = ", "" + Item.itemCardForPrint.getFDPRC());
                            } else if (Item.itemCardForPrint.getItemG().equals("3")) {

                                bitmap = convertLayoutToImage_shelfTag_Iraq(Item.itemCardForPrint);
                                Log.e("Count = ", "" + Item.itemCardForPrint.getFDPRC());
                            } else {

                                bitmap = convertLayoutToImage_shelfTag_Iraq_rotate(Item.itemCardForPrint);
                                Log.e("Count = ", "" + Item.itemCardForPrint.getFDPRC());
                            }
                    }else if(companyNo==2){
                                bitmap = convertLayoutToImage_shelfTag_barcode_qasion(Item.itemCardForPrint);
                                Log.e("Count = ", "" + Item.itemCardForPrint.getFDPRC());
                            }
                            if (bitmap != null) {
                                Log.e("Count = ", "" + Item.itemCardForPrint.getCostPrc() + "   " + remoteDevices.get(0).getAddress());
                                Log.e("PrintType","Blutoo "+PrintType);
                               // Toast.makeText(context, "test"+PrintType, Toast.LENGTH_SHORT).show();
                                if (PrintType.equals("0")||PrintType.equals("3")) {
                                    sample.imageTestEnglish(Integer.parseInt(Item.itemCardForPrint.getCostPrc()), bitmap, PrintType);
                                } else if (PrintType.equals("1")) {
                                    sample.imageTestEnglish(Integer.parseInt(Item.itemCardForPrint.getCostPrc()), bitmap, PrintType);
                                } else if (PrintType.equals("2")) {
                                //    Toast.makeText(context, "test_123456 "+PrintType, Toast.LENGTH_SHORT).show();
                                    sample.dmStamp(Integer.parseInt(Item.itemCardForPrint.getCostPrc()), bitmap);
                                }


                            } else {
                                Log.e("Count = ", "Can not print ");

//                            Toast.makeText(context, "CAN NOT PRINT", Toast.LENGTH_SHORT).show();
                            }

                            break;

                        case 1:
                            if (PrintType.equals("0")||PrintType.equals("2")||PrintType.equals("3")) {
                                sample.selectGapPaper();
                                if(PrintType.equals("2")){
                                  //  sample.imageTestTSC(remoteDevices.get(0).getAddress(),0);
                                }
                            }
                            for (int i = 0; i < Item.barcodeListForPrint.size(); i++) {
                                Bitmap bitmaps=null;
                                if(companyNo==0) {
                                     bitmaps = convertLayoutToImage_Barcode(Item.barcodeListForPrint.get(i), Item.itemCardForPrint.getOrgPrice(),Item.itemCardForPrint.getIsName());
                                }else if (companyNo==1){
                                     bitmaps = convertLayoutToImage_Barcode_athouab(Item.barcodeListForPrint.get(i), Item.itemCardForPrint.getOrgPrice(), Item.itemCardForPrint.getIsName());

                                }
                                if (bitmaps != null) {
                                    Log.e("Count = ", "" + Item.itemCardForPrint.getCostPrc());
                                    Log.e("PrintType = ", "" + PrintType);
                                    if(PrintType.equals("0")||  PrintType.equals("3")){
                                        sample.imageTestEnglishBarcode(Integer.parseInt(Item.itemCardForPrint.getCostPrc()), bitmaps,PrintType);
                                    }else if (PrintType.equals("1")){
                                        sample.imageTestEnglishBarcode(Integer.parseInt(Item.itemCardForPrint.getCostPrc()), bitmaps,PrintType);

                                    }else if(PrintType.equals("2")){
                                        sample.dmStamp2(Integer.parseInt(Item.itemCardForPrint.getCostPrc()), bitmaps);

                                    }

                                } else {
//                                Toast.makeText(context, "CAN NOT PRINT", Toast.LENGTH_SHORT).show();
                                }
                            }
                            break;
                        case 2:
                            if (PrintType.equals("0")||PrintType.equals("2")|| PrintType.equals("3")) {
                                sample.selectContinuousPaper();
                            }
                            double totalQty = 0.0;
                            if (ItemInfoListForPrint.size() != 0) {
                                Bitmap bitmap1 = convertLayoutToImage_Report_titel(null, "-1");
                                sample.imageTestEnglishReport(1, bitmap1);

                                for (int i = 0; i < ItemInfoListForPrint.size(); i++) {
                                    Bitmap bitmap2 = convertLayoutToImage_Report(ItemInfoListForPrint.get(i), i + "");
                                    sample.imageTestEnglishReport(1, bitmap2);

                                    totalQty += ItemInfoListForPrint.get(i).getItemQty();
                                }

                                Bitmap bitmap5 = convertLayoutToImage_Report(null, "-4");
                                sample.imageTestEnglishReport(1, bitmap5);
                                ItemInfo item = new ItemInfo();
                                item.setItemQty((float) totalQty);
                                Bitmap bitmap4 = convertLayoutToImage_Report(item, "-3");
                                sample.imageTestEnglishReport(1, bitmap4);


                                Bitmap bitmap3 = convertLayoutToImage_Report(null, "-2");
                                sample.imageTestEnglishReport(1, bitmap3);
                            } else {
//                            Toast.makeText(BluetoothConnectMenu.this, "No Item For Print ", Toast.LENGTH_SHORT).show();
                            }

                            break;

                        case 3:
                            if (PrintType.equals("0")||PrintType.equals("2")|| PrintType.equals("3")) {
                                sample.selectGapPaper();
                            }
                            for (int i = 0; i < Item.barcodeListForPrintAssest.size(); i++) {
                                Bitmap bitmaps = convertLayoutToImage_Assesst(Item.barcodeListForPrintAssest.get(i), Item.itemAssesstForPrint.getPrice());
                                if (bitmaps != null) {
                                    Log.e("Count = ", "" + Item.itemAssesstForPrint.getCount());
                                    sample.imageTestEnglishBarcode(Integer.parseInt(Item.itemAssesstForPrint.getCount()), bitmaps,PrintType);
                                } else {
//                                Toast.makeText(context, "CAN NOT PRINT", Toast.LENGTH_SHORT).show();
                                }
                            }
                            break;

                    }
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (BluetoothConnectMenu.this.chkDisconnect.isChecked()) {
                    BluetoothConnectMenu.this.registerReceiver(BluetoothConnectMenu.this.disconnectReceiver, new IntentFilter("android.bluetooth.device.action.ACL_CONNECTED"));
                    BluetoothConnectMenu.this.registerReceiver(BluetoothConnectMenu.this.disconnectReceiver, new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED"));
                }
            } else {
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

//                AlertView.showAlert("Disconnect Bluetoothُ", "Try Again ...", BluetoothConnectMenu.this.context);
                new SweetAlertDialog(BluetoothConnectMenu.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...Try Again")
                        .setContentText("Disconnect Bluetooth")
                        .show();
                //convertLayoutToImage_shelfTag(Item.itemCardForPrint);
               // convertLayoutToImage_shelfTag_barcode_qasion(Item.itemCardForPrint);
//                convertLayoutToImage_shelfTag_Design3(Item.itemCardForPrint);
            }

            super.onPostExecute(result);
        }

    }

    public void finishDialog() {
        finish();
    }

    @SuppressLint("SetTextI18n")
    private Bitmap convertLayoutToImage_shelfTag(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName, price, BarcodeText, exp,textView421;

        LinearLayout ExpLiner, priceLiner;

        ExpLiner = (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
        exp = (TextView) dialog_Header.findViewById(R.id.exp);
//        textView421=(TextView) dialog_Header.findViewById(R.id.textView421);
////        Log.e("PrintDateFlag==",Item.PrintDateFlag+"");
//        if(Item.PrintDateFlag==1)  textView421.setText("PRI:");
//        else  textView421.setText("EXP:");
        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());



        if (itemCard.getIsUnite().equals("**")) {
            itemName.setText(itemCard.getItemName() );

        }else {
            try {
                itemName.setText(itemCard.getItemName() + "/" + itemCard.getItemUnit());
            }catch (Exception e){
                itemName.setText(itemCard.getItemName() );

            }

        }

        if (itemCard.getIsName().equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setVisibility(View.VISIBLE);
        }

        if (itemCard.getSalePrc().equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);

        }

        if (itemCard.getDepartmentId().equals("**")) {
            ExpLiner.setVisibility(View.INVISIBLE);
        } else {
            exp.setText(itemCard.getDepartmentId());
        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 80);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }



        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);

        if (rotate.equals("1")) {
            linearView.setRotation(180f);
        }

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        // dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();

        Bitmap bit = linearView.getDrawingCache();



        if(resize==1){
            Bitmap b=getResizedBitmap(bit,wid,hi);
            shelfTagLinerTests.setImageBitmap(b);
            return b;// creates bitmap and returns the same
        }else {
            return bit;// creates bitmap and returns the same
        }



    }

    @SuppressLint("SetTextI18n")
    private Bitmap convertLayoutToImage_shelfTag_barcode_qasion(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog_design_qusion);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName, price, BarcodeText, exp,start;

        LinearLayout ExpLiner, priceLiner,startDay;

        ExpLiner = (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);
        startDay=dialog_Header.findViewById(R.id.startDay);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
        exp = (TextView) dialog_Header.findViewById(R.id.exp);
        start=dialog_Header.findViewById(R.id.startDayT);
        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());


        if (itemCard.getIsUnite().equals("**")) {
            itemName.setText(itemCard.getItemName() );

        }else {
            try {
                itemName.setText(itemCard.getItemName() + "/" + itemCard.getItemUnit());
            }catch (Exception e){
                itemName.setText(itemCard.getItemName() );

            }

        }


        if (itemCard.getIsName().equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setVisibility(View.VISIBLE);
        }

        if (itemCard.getSalePrc().equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);

        }

        if (itemCard.getDepartmentId().equals("**")) {
            ExpLiner.setVisibility(View.INVISIBLE);
        } else {
            exp.setText(itemCard.getDepartmentId());
        }

        if (itemCard.getIsStartDay().equals("**")) {
            startDay.setVisibility(View.INVISIBLE);
        } else {
            start.setText(today);
        }

        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 90);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }



        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);

        if (rotate.equals("1")) {
            linearView.setRotation(180f);
        }

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        // dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();

        Bitmap bit = linearView.getDrawingCache();
//        Bitmap b=null;
//        try {
//            b = getResizedBitmap(bit, wid, hi);
//        }catch (Exception e){
//
//        }
//        shelfTagLinerTests.setImageBitmap(bit);

//        if(resize==1){
//            return b;// creates bitmap and returns the same
//        }else {
            return bit;// creates bitmap and returns the same
//        }



    }

    @SuppressLint("SetTextI18n")
    private Bitmap convertLayoutToImage_shelfTag_Iraq(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(true);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog_1);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName, price, BarcodeText;//, exp;

        LinearLayout /*ExpLiner,*/ priceLiner;

       // ExpLiner = (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
      //  exp = (TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());


        if (itemCard.getIsUnite().equals("**")) {
            itemName.setText(itemCard.getItemName() );

        }else {
            try {
                itemName.setText(itemCard.getItemName() + "/" + itemCard.getItemUnit());
            }catch (Exception e){
                itemName.setText(itemCard.getItemName() );

            }

        }


        if (itemCard.getIsName().equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setVisibility(View.VISIBLE);
        }

        if (itemCard.getSalePrc().equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);

        }

//        if (itemCard.getDepartmentId().equals("**")) {
//            ExpLiner.setVisibility(View.INVISIBLE);
//        } else {
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 70);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        linearView = (LinearLayout)  dialog_Header.findViewById(R.id.shelfTagLiner);
//        if (rotate.equals("1")) {
//           // linearView.setRotation(180.0f);
//            linearView.setRotationX(180.0f);
//
//        }
        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
         dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit = linearView.getDrawingCache();

        if(resize==1){
            Bitmap b=getResizedBitmap(bit,wid,hi);
            shelfTagLinerTests.setImageBitmap(b);
            return b;// creates bitmap and returns the same
        }else {
            return bit;// creates bitmap and returns the same
        }


    }

    private Bitmap convertLayoutToImage_shelfTag_Iraq_rotate(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(true);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog_rotat);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName, price, BarcodeText;//, exp;

        LinearLayout /*ExpLiner,*/ priceLiner;

        // ExpLiner = (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
        //  exp = (TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());


        if (itemCard.getIsUnite().equals("**")) {
            itemName.setText(itemCard.getItemName() );

        }else {
            try {
                itemName.setText(itemCard.getItemName() + "/" + itemCard.getItemUnit());
            }catch (Exception e){
                itemName.setText(itemCard.getItemName() );

            }

        }


        if (itemCard.getIsName().equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setVisibility(View.VISIBLE);
        }

        if (itemCard.getSalePrc().equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);

        }

//        if (itemCard.getDepartmentId().equals("**")) {
//            ExpLiner.setVisibility(View.INVISIBLE);
//        } else {
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 70);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        linearView = (LinearLayout)  dialog_Header.findViewById(R.id.shelfTagLiner);
//        if (rotate.equals("1")) {
//           // linearView.setRotation(180.0f);
//            linearView.setRotationX(180.0f);
//
//        }
        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit = linearView.getDrawingCache();

        if(resize==1){
            Bitmap b=getResizedBitmap(bit,wid,hi);
            shelfTagLinerTests.setImageBitmap(b);
            return b;// creates bitmap and returns the same
        }else {
            return bit;// creates bitmap and returns the same
        }

    }

    private Bitmap convertLayoutToImage_shelfTag_Design2(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog_design2_bitaltmoin);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName, price, BarcodeText, exp,textView421;

        LinearLayout ExpLiner, priceLiner;

        ExpLiner = (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
        exp = (TextView) dialog_Header.findViewById(R.id.exp);
//        textView421=(TextView) dialog_Header.findViewById(R.id.textView421);
//        if(Item.PrintDateFlag==1)  textView421.setText("PRI:");
//        else  textView421.setText("EXP:");
        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());

        if (itemCard.getIsUnite().equals("**")) {
            itemName.setText(itemCard.getItemName() );

        }else {
            try {
                itemName.setText(itemCard.getItemName() + "/" + itemCard.getItemUnit());
            }catch (Exception e){
                itemName.setText(itemCard.getItemName() );

            }

        }

        if (itemCard.getIsName().equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setVisibility(View.VISIBLE);
        }

        if(itemCard.getSalePrc().equals("**")){
            priceLiner.setVisibility(View.INVISIBLE);
        }else{
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+" "+Currency);
        }

        if(itemCard.getDepartmentId().equals("**")){
            ExpLiner.setVisibility(View.INVISIBLE);
        }else{
            exp.setText(itemCard.getDepartmentId());
        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 90);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);
        if (rotate.equals("1")) {
            linearView.setRotation(180f);
        }
        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit = linearView.getDrawingCache();

        if(resize==1){
            Bitmap b=getResizedBitmap(bit,wid,hi);
            shelfTagLinerTests.setImageBitmap(b);
            return b;// creates bitmap and returns the same
        }else {
            return bit;// creates bitmap and returns the same
        }

    }

    private Bitmap convertLayoutToImage_shelfTag_Design3(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog_design3);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName, price, itemBar,exp;//,BarcodeText;// ;

        LinearLayout priceLiner,ExpLiner;

        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        itemBar = (TextView) dialog_Header.findViewById(R.id.itemBar);
//        BarcodeText=(TextView) dialog_Header.findViewById(R.id.BarcodeText);
        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

//        BarcodeText.setText(itemCard.getItemCode());

        if (itemCard.getIsUnite().equals("**")) {
            itemName.setText(itemCard.getItemName() );

        }else {
            try {
                itemName.setText(itemCard.getItemName() + "/" + itemCard.getItemUnit());
            }catch (Exception e){
                itemName.setText(itemCard.getItemName() );

            }

        }

        if (itemCard.getIsName().equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setVisibility(View.VISIBLE);
        }

        itemBar.setText("" + itemCard.getItemCode());
        if (itemCard.getSalePrc().equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);
        }

        if(itemCard.getDepartmentId().equals("**")){
            ExpLiner.setVisibility(View.INVISIBLE);
        }else{
            exp.setText(itemCard.getDepartmentId());
        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 40);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }

       // barcode.setVisibility(View.GONE);
        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);
        if (rotate.equals("1")) {
            linearView.setRotation(180f);
            Log.e("setRotation","true"+rotate);

        }
        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit = linearView.getDrawingCache();

        if(resize==1){
            Bitmap b=getResizedBitmap(bit,wid,hi);
            shelfTagLinerTests.setImageBitmap(b);
            return b;// creates bitmap and returns the same
        }else {
            return bit;// creates bitmap and returns the same
        }

    }
    private Bitmap convertLayoutToImage_Barcode_qastas(ItemCard itemCard, String index,String nameIn) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.barcode_print_dialog_qastas);


        TextView itemName, price, BarcodeText, exp;

        LinearLayout ExpLiner, priceLiner;

//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());
        itemName.setText(itemCard.getItemName());
        if (index.equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);
        }
        if (nameIn.equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setText(itemCard.getItemName()+"");
        }

//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 50, 50);
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
        Bitmap bit = linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }

    private Bitmap convertLayoutToImage_Barcode(ItemCard itemCard, String index,String nameIn) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.barcode_print_dialog);


        TextView itemName, price, BarcodeText, exp;

        LinearLayout ExpLiner, priceLiner;

//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());
        itemName.setText(itemCard.getItemName());
        if (index.equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);
        }
        if (nameIn.equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setText(itemCard.getItemName()+"");
        }

//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 50, 50);
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
        Bitmap bit = linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }
    private Bitmap convertLayoutToImage_Barcode_pink(ItemCard itemCard, String index,String nameIn) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.barcode_print_dialog_pink);


        TextView itemName, price, BarcodeText, exp;

        LinearLayout ExpLiner, priceLiner;

//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());
      //  itemName.setText(itemCard.getItemName());
        if (index.equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);
        }
        if (nameIn.equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setText(itemCard.getItemName()+"");
        }


//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 50, 50);
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
        Bitmap bit = linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }

    private Bitmap convertLayoutToImage_Barcode_dimno_red(ItemCard itemCard, String index,String nameIn) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.barcode_print_dialog_dimono_red);


        TextView itemName, price, BarcodeText, exp;

        LinearLayout ExpLiner, priceLiner;

//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());
        //  itemName.setText(itemCard.getItemName());
        if (index.equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);
        }
        if (nameIn.equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setText(itemCard.getItemName()+"");
        }


//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 50, 50);
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
        Bitmap bit = linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }

    private Bitmap convertLayoutToImage_Barcode_athouab(ItemCard itemCard, String index,String indexName) {//اثواب
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.barcode_print_dialog_athouab);


        TextView itemName, price, BarcodeText, exp,modelNo,color,size;

        LinearLayout ExpLiner, priceLiner;

//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);
        modelNo=dialog_Header.findViewById(R.id.modelNo);
        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);
        color=dialog_Header.findViewById(R.id.color);
        size=dialog_Header.findViewById(R.id.size);

        BarcodeText.setText(itemCard.getItemCode());

        if (index.equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);
        }

        if (indexName.equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setText(itemCard.getItemName());
        }
        modelNo.setText(itemCard.getItemL()+"");
        color.setText(itemCard.getItemK()+"");
        size.setText(itemCard.getItemM()+"");
//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.EAN_13, 210, 50);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        //dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit = linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }


    private Bitmap convertLayoutToImage_Barcode_max(ItemCard itemCard, String index ,String nameIn) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.barcode_print_dialog_max);


        TextView itemName, price, BarcodeText, exp;

        LinearLayout ExpLiner, priceLiner;

//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());
        itemName.setText(itemCard.getItemName());
        if (index.equals("**")) {
            priceLiner.setVisibility(View.INVISIBLE);
        } else {
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC()))) +" "+ Currency);
        }

        if (nameIn.equals("**")) {
            itemName.setVisibility(View.INVISIBLE);
        } else {
            itemName.setText(itemCard.getItemName()+"");
        }

//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 50, 50);
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
        Bitmap bit = linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        try {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            return resizedBitmap;
        }catch (Exception e){
            Toast.makeText(context, "bitmap Not ReSize", Toast.LENGTH_SHORT).show();
            return null;
        }
    }



    private Bitmap convertLayoutToImage_Assesst(AssestItem itemCard, String index) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.barcode_print_dialog_qastas_assest);


        TextView itemName, price, BarcodeText, exp;

        LinearLayout ExpLiner, priceLiner;

//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner = (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText = (TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getAssesstCode());
        itemName.setText(itemCard.getAssesstName());

        priceLiner.setVisibility(View.INVISIBLE);

//        if(index.equals("**")){
//            priceLiner.setVisibility(View.INVISIBLE);
//        }else{
//            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+" JD");
//        }

//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getAssesstCode(), BarcodeFormat.CODE_128, 300, 40);
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
        Bitmap bit = linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }

    private Bitmap convertLayoutToImage_Report(ItemInfo itemInfo, String index) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.row_for_print);
//        List list2 = ((List) ((ArrayList) long_listItems).clone());

        TextView itemName, price, ItemCode, Qty;

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        ItemCode = (TextView) dialog_Header.findViewById(R.id.itemCode);
        Qty = (TextView) dialog_Header.findViewById(R.id.qty);


        if (Integer.parseInt(index) == -1) {
            ItemCode.setText("Item Code");
            itemName.setText("Item Name");
            price.setText("Price");
            Qty.setText("Qty");
        } else if (Integer.parseInt(index) == -2) {
            ItemCode.setText(" ");
            itemName.setText("   ");
            price.setText(" ");
            Qty.setText(" ");
        } else if (Integer.parseInt(index) == -3) {
            ItemCode.setText(" ");
            itemName.setText("Total Qty =");
            price.setText(" ");
            Qty.setText(itemInfo.getItemQty() + "");

        } else if (Integer.parseInt(index) == -4) {
            ItemCode.setText("----------------------");
            itemName.setText("----------------------");
            price.setText("----------------");
            Qty.setText("----------------");
        } else {
            ItemCode.setText(itemInfo.getItemCode());
            itemName.setText(itemInfo.getItemName());
            price.setText(itemInfo.getSalePrice() + "");
            Qty.setText(itemInfo.getItemQty() + "");

        }


        linearView = (LinearLayout) dialog_Header.findViewById(R.id.LinerRaw);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit = linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }


    private Bitmap convertLayoutToImage_Report_titel(ItemInfo itemInfo, String index) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.title_for_print);


        TextView itemName, price, ItemCode, Qty, accu, date;

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        ItemCode = (TextView) dialog_Header.findViewById(R.id.itemCode);
        Qty = (TextView) dialog_Header.findViewById(R.id.qty);
        accu = (TextView) dialog_Header.findViewById(R.id.accu);
        date = (TextView) dialog_Header.findViewById(R.id.date);
        date.setText(today);

        if (preparAc) {
            accu.setText("Not Accum");
        } else {
            accu.setText("Accum");
        }


        if (Integer.parseInt(index) == -1) {
            ItemCode.setText("Item Code");
            itemName.setText("Item Name");
            price.setText("Price");
            Qty.setText("Qty");
        }


        linearView = (LinearLayout) dialog_Header.findViewById(R.id.LinerRaw);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit = linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


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
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
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

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

//    public String convertToEnglish(String value) {
//        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
//        return newValue;
//    }
//
//    private Bitmap convertLayoutToImage_HEADER(Voucher voucher) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.header_voucher_print);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//        TextView doneinsewooprint = (TextView) dialog_Header.findViewById(R.id.done);
//
//        TextView compname, tel, taxNo, vhNo, date, custname, note, vhType, paytype,salesName     ;
//        ImageView img = (ImageView) dialog_Header.findViewById(R.id.img);
//        compname = (TextView) dialog_Header.findViewById(R.id.compname);
//        tel = (TextView) dialog_Header.findViewById(R.id.tel);
//        taxNo = (TextView) dialog_Header.findViewById(R.id.taxNo);
//        vhNo = (TextView) dialog_Header.findViewById(R.id.vhNo);
//        date = (TextView) dialog_Header.findViewById(R.id.date);
//        custname = (TextView) dialog_Header.findViewById(R.id.custname);
//        note = (TextView) dialog_Header.findViewById(R.id.note);
//        vhType = (TextView) dialog_Header.findViewById(R.id.vhType);
//        paytype = (TextView) dialog_Header.findViewById(R.id.paytype);
//        salesName = (TextView) dialog_Header.findViewById(R.id.salesman_name);
//        String voucherTyp = "";
//        switch (voucher.getVoucherType()) {
//            case 504:
//                voucherTyp = "فاتورة بيع";
//                break;
//            case 506:
//                voucherTyp = "فاتورة مرتجعات";
//                break;
//            case 508:
//                voucherTyp = "طلب جديد";
//                break;
//        }
//        if (companyInfo.getLogo()!=(null))
//        {
//        img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//        compname.setText(companyInfo.getCompanyName());
//        tel.setText("" + companyInfo.getcompanyTel());
//        taxNo.setText("" + companyInfo.getTaxNo());
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        custname.setText(voucher.getCustName());
//        note.setText(voucher.getRemark());
//        vhType.setText(voucherTyp);
//        salesName.setText(obj.getAllSettings().get(0).getSalesMan_name());
//        paytype.setText((voucher.getPayMethod() == 0 ? "ذمم" : "نقدا"));
//        dialog_Header.show();
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }
//    private Bitmap convertLayoutToImage_HEADER_Ejabe(Voucher voucher) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.header_voucher_print_ejabe);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//        TextView doneinsewooprint = (TextView) dialog_Header.findViewById(R.id.done);
//
//        TextView compname, store,tel, taxNo, vhNo, date, custname, note, vhType, paytype,salesName     ;
//        ImageView img = (ImageView) dialog_Header.findViewById(R.id.img);
//        compname = (TextView) dialog_Header.findViewById(R.id.compname);
//        tel = (TextView) dialog_Header.findViewById(R.id.tel);
//        taxNo = (TextView) dialog_Header.findViewById(R.id.taxNo);
//        vhNo = (TextView) dialog_Header.findViewById(R.id.vhNo);
//        date = (TextView) dialog_Header.findViewById(R.id.date);
//        custname = (TextView) dialog_Header.findViewById(R.id.custname);
//        note = (TextView) dialog_Header.findViewById(R.id.note);
//        vhType = (TextView) dialog_Header.findViewById(R.id.vhType);
//        paytype = (TextView) dialog_Header.findViewById(R.id.paytype);
//        store= (TextView) dialog_Header.findViewById(R.id.store);
//        salesName = (TextView) dialog_Header.findViewById(R.id.salesman_name);
//        String salesmaname=obj.getSalesmanName();
//        salesName.setText(salesmaname);
//        String voucherTyp = "";
//        switch (voucher.getVoucherType()) {
//            case 504:
//                voucherTyp = "Sales Invoice";
//                break;
//            case 506:
//                voucherTyp = "Return Invoice";
//                break;
//            case 508:
//                voucherTyp = "New Order";
//                break;
//        }
//        if (companyInfo.getLogo()!=(null))
//        {
//            img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//        compname.setText(companyInfo.getCompanyName());
//        tel.setText("" + companyInfo.getcompanyTel());
//        taxNo.setText("" + companyInfo.getTaxNo());
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        custname.setText(voucher.getCustName());
//        note.setText(voucher.getRemark());
//        vhType.setText(voucherTyp);
//        store.setText(Login.salesMan);
////        salesName.setText(obj.getAllSettings().get(0).getSalesMan_name());
//        paytype.setText((voucher.getPayMethod() == 0 ? "Credit" : "Cash"));
//        dialog_Header.show();
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }
//
//    private Bitmap convertLayoutToImage_Footer(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialog_footer = new Dialog(BluetoothConnectMenu.this);
//        dialog_footer.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_footer.setCancelable(false);
//        dialog_footer.setContentView(R.layout.footer_voucher_print);
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//        TextView doneinsewooprint = (TextView) dialog_footer.findViewById(R.id.done);
//
//        TextView total, discount, tax, ammont, Total_qty_total;
//        total = (TextView) dialog_footer.findViewById(R.id.total);
//        discount = (TextView) dialog_footer.findViewById(R.id.discount);
//        tax = (TextView) dialog_footer.findViewById(R.id.tax);
//        ammont = (TextView) dialog_footer.findViewById(R.id.ammont);
//        total.setText("" + voucher.getSubTotal());
//        discount.setText(convertToEnglish(String.valueOf(decimalFormat.format( voucher.getTotalVoucherDiscount()))));
//        tax.setText("" + voucher.getTax());
//        ammont.setText("" + voucher.getNetSales());
//        Total_qty_total=(TextView) dialog_footer.findViewById(R.id.total_qty);
//        Total_qty_total.setText(count+"");
//        linearView = (LinearLayout) dialog_footer.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//    }
//    private Bitmap convertLayoutToImage_Footer_ejabe(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialog_footer = new Dialog(BluetoothConnectMenu.this);
//        dialog_footer.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_footer.setCancelable(false);
//        dialog_footer.setContentView(R.layout.footer_voucher_print_ejabe);
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//        TextView doneinsewooprint = (TextView) dialog_footer.findViewById(R.id.done);
//
//        TextView total, discount, tax, ammont, Total_qty_total;
//        total = (TextView) dialog_footer.findViewById(R.id.total);
//        discount = (TextView) dialog_footer.findViewById(R.id.discount);
//        tax = (TextView) dialog_footer.findViewById(R.id.tax);
//        ammont = (TextView) dialog_footer.findViewById(R.id.ammont);
//        total.setText("" + voucher.getSubTotal());
//        discount.setText(convertToEnglish(String.valueOf(decimalFormat.format( voucher.getTotalVoucherDiscount()))));
//        tax.setText("" + voucher.getTax());
//        ammont.setText("" + voucher.getNetSales());
//        Total_qty_total=(TextView) dialog_footer.findViewById(R.id.total_qty);
//        Total_qty_total.setText(count+"");
//        linearView = (LinearLayout) dialog_footer.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//    }
//
//
//    private Bitmap convertLayoutToImage(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.sewo30_printer_layout);
////            fill_theVocher( voucher);
//
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//       TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//
//        TextView compname, tel, taxNo, vhNo, date, custname, note, vhType, paytype, total, discount, tax, ammont, textW,total_qty_text,salesName;
//        ImageView img = (ImageView) dialogs.findViewById(R.id.img);
////
//        compname = (TextView) dialogs.findViewById(R.id.compname);
//        tel = (TextView) dialogs.findViewById(R.id.tel);
//        taxNo = (TextView) dialogs.findViewById(R.id.taxNo);
//        vhNo = (TextView) dialogs.findViewById(R.id.vhNo);
//        date = (TextView) dialogs.findViewById(R.id.date);
//        custname = (TextView) dialogs.findViewById(R.id.custname);
//        salesName = (TextView) dialogs.findViewById(R.id.salesman_name);
//        note = (TextView) dialogs.findViewById(R.id.note);
//        vhType = (TextView) dialogs.findViewById(R.id.vhType);
//        paytype = (TextView) dialogs.findViewById(R.id.paytype);
//        total = (TextView) dialogs.findViewById(R.id.total);
//        discount = (TextView) dialogs.findViewById(R.id.discount);
//        tax = (TextView) dialogs.findViewById(R.id.tax);
//        ammont = (TextView) dialogs.findViewById(R.id.ammont);
//        textW = (TextView) dialogs.findViewById(R.id.wa1);
//        total_qty_text= (TextView) dialogs.findViewById(R.id.total_qty);
//        //total_qty
//
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//        String voucherTyp = "";
//        switch (voucher.getVoucherType()) {
//            case 504:
//                voucherTyp = "فاتورة بيع";
//                break;
//            case 506:
//                voucherTyp = "فاتورة مرتجعات";
//                break;
//            case 508:
//                voucherTyp = "طلب جديد";
//                break;
//        }
////        img.setImageBitmap(companyInfo.getLogo());
//        compname.setText(companyInfo.getCompanyName());
//        if (companyInfo.getLogo()!=(null))
//        {
//            img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//
//        tel.setText("" + companyInfo.getcompanyTel());
//        taxNo.setText("" + companyInfo.getTaxNo());
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        custname.setText(voucher.getCustName());
//        salesName.setText(obj.getAllSettings().get(0).getSalesMan_name());
//        note.setText(voucher.getRemark());
//        vhType.setText(voucherTyp);
//        paytype.setText((voucher.getPayMethod() == 0 ? "ذمم" : "نقدا"));
//        total.setText("" + voucher.getSubTotal());
//        discount.setText(convertToEnglish(String.valueOf(decimalFormat.format( voucher.getTotalVoucherDiscount()))));
//        tax.setText("" + voucher.getTax());
//        ammont.setText("" + voucher.getNetSales());
//
//       int count=0;
//
//        if (obj.getAllSettings().get(0).getUseWeightCase() != 1) {
//            textW.setVisibility(View.GONE);
//        } else {
//            textW.setVisibility(View.VISIBLE);
//        }
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        for (int j = 0; j < items.size(); j++) {
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <= 7; i++) {
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
//                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemName());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getUnit());
//                                textView.setLayoutParams(lp2);
//                            } else {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                            }
//                            break;
//
//                        case 2:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                                textView.setVisibility(View.VISIBLE);
//                            } else {
//                                textView.setVisibility(View.GONE);
//                            }
//                            break;
//
//                        case 3:
//                            textView.setText("" + items.get(j).getPrice());
//                            textView.setLayoutParams(lp2);
//                            break;
//
//
//                        case 4:
//                            String amount = "" + (items.get(j).getQty() * items.get(j).getPrice() - items.get(j).getDisc());
////                            amount = convertToEnglish(amount);
//                            amount =String.valueOf(decimalFormat.format(Double.parseDouble(amount)));
//                            textView.setText(convertToEnglish(amount));
////                            textView.setText(amount);
//                            textView.setLayoutParams(lp2);
//                            break;
//                    }
//                    row.addView(textView);
//                }
//
//
//
//                tabLayout.addView(row);
//            }
//        }
//
//
//        total_qty_text.setText(count+"");
//        Log.e("countItem",""+count);
//
////        linearView  = (LinearLayout) this.getLayoutInflater().inflate(R.layout.printdialog, null, false); //you can pass your xml layout
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        dialogs.show();
//
////        linearView.setDrawingCacheEnabled(true);
////        linearView.buildDrawingCache();
////        Bitmap bit =linearView.getDrawingCache();
//
////        Bitmap bitmap = Bitmap.createBitmap(linearView.getWidth(), linearView.getHeight(), Bitmap.Config.ARGB_8888);
////        Canvas canvas = new Canvas(bitmap);
////        Drawable bgDrawable = linearView.getBackground();
////        if (bgDrawable != null) {
////            bgDrawable.draw(canvas);
////        } else {
////            canvas.drawColor(Color.WHITE);
////        }
////        linearView.draw(canvas);
//
//        return bit;// creates bitmap and returns the same
//    }
//
//    private Bitmap convertLayoutToImageEjape(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.sewo30_printer_layout_ejaby);
////            fill_theVocher( voucher);
//
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//        TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//
//        TextView compname,store, tel, taxNo, vhNo, date, custname, note, vhType, paytype, total, discount, tax, ammont, textW,total_qty_text,salesName;
//        ImageView img = (ImageView) dialogs.findViewById(R.id.img);
////
//        compname = (TextView) dialogs.findViewById(R.id.compname);
//        tel = (TextView) dialogs.findViewById(R.id.tel);
//        taxNo = (TextView) dialogs.findViewById(R.id.taxNo);
//        vhNo = (TextView) dialogs.findViewById(R.id.vhNo);
//        date = (TextView) dialogs.findViewById(R.id.date);
//        custname = (TextView) dialogs.findViewById(R.id.custname);
//        salesName = (TextView) dialogs.findViewById(R.id.salesman_name);
//        note = (TextView) dialogs.findViewById(R.id.note);
//        vhType = (TextView) dialogs.findViewById(R.id.vhType);
//        paytype = (TextView) dialogs.findViewById(R.id.paytype);
//        total = (TextView) dialogs.findViewById(R.id.total);
//        discount = (TextView) dialogs.findViewById(R.id.discount);
//        tax = (TextView) dialogs.findViewById(R.id.tax);
//        ammont = (TextView) dialogs.findViewById(R.id.ammont);
//        textW = (TextView) dialogs.findViewById(R.id.wa1);
//        store= (TextView) dialogs.findViewById(R.id.store);
//        total_qty_text= (TextView) dialogs.findViewById(R.id.total_qty);
//        String salesmaname=obj.getSalesmanName();
//        salesName.setText(salesmaname);
//        //total_qty
//
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//        String voucherTyp = "";
//        switch (voucher.getVoucherType()) {
//            case 504:
//                voucherTyp = "Sales Invoice";
//                break;
//            case 506:
//                voucherTyp = "Return Invoice";
//                break;
//            case 508:
//                voucherTyp = "New Order";
//                break;
//        }
////        img.setImageBitmap(companyInfo.getLogo());
//        compname.setText(companyInfo.getCompanyName());
//        if (companyInfo.getLogo()!=(null))
//        {
//            img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//
//        tel.setText("" + companyInfo.getcompanyTel());
//        taxNo.setText("" + companyInfo.getTaxNo());
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        custname.setText(voucher.getCustName());
////        salesName.setText(obj.getAllSettings().get(0).getSalesMan_name());
//        note.setText(voucher.getRemark());
//        vhType.setText(voucherTyp);
//        paytype.setText((voucher.getPayMethod() == 0 ? "Credit" : "Cash"));
//        total.setText("" + voucher.getSubTotal());
//        discount.setText(convertToEnglish(String.valueOf(decimalFormat.format( voucher.getTotalVoucherDiscount()))));
//        tax.setText("" + voucher.getTax());
//        ammont.setText("" + voucher.getNetSales());
//        store.setText(Login.salesMan);
//        int count=0;
//
//        if (obj.getAllSettings().get(0).getUseWeightCase() != 1) {
//            textW.setVisibility(View.GONE);
//        } else {
//            textW.setVisibility(View.VISIBLE);
//        }
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        for (int j = 0; j < items.size(); j++) {
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <= 7; i++) {
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
////                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemNo());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getUnit());
//                                textView.setLayoutParams(lp2);
//                            } else {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                            }
//                            break;
//
//                        case 2:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                                textView.setVisibility(View.VISIBLE);
//                            } else {
//                                textView.setVisibility(View.GONE);
//                            }
//                            break;
//
//                        case 3:
//                            textView.setText("" + items.get(j).getPrice());
//                            textView.setLayoutParams(lp2);
//                            break;
//
//
//                        case 4:
//                            String amount = "" + (items.get(j).getQty() * items.get(j).getPrice() - items.get(j).getDisc());
////                            amount = convertToEnglish(amount);
//                            amount =String.valueOf(decimalFormat.format(Double.parseDouble(amount)));
//                            textView.setText(convertToEnglish(amount));
////                            textView.setText(amount);
//                            textView.setLayoutParams(lp2);
//                            break;
//                    }
//                    row.addView(textView);
//
//
//                }
////                final TableRow rows = new TableRow(BluetoothConnectMenu.this);
////                TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
////                lp.setMargins(0, 10, 0, 0);
////                rows.setLayoutParams(lp);
//                TextView textViews = new TextView(BluetoothConnectMenu.this);
//                textViews.setTextSize(14);
//                textViews.setPadding(0,0,0,5);
////                textViews.setTypeface(null, Typeface.BOLD);
//                textViews.setTextColor(getResources().getColor(R.color.text_view_color));
//                textViews.setText(items.get(j).getItemName());
////                rows.addView(textView);
//
//                tabLayout.addView(row);
//                tabLayout.addView(textViews);
//            }
//        }
//
//
//        total_qty_text.setText(count+"");
//        Log.e("countItem",""+count);
//
////        linearView  = (LinearLayout) this.getLayoutInflater().inflate(R.layout.printdialog, null, false); //you can pass your xml layout
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
////        dialogs.show();
//
////        linearView.setDrawingCacheEnabled(true);
////        linearView.buildDrawingCache();
////        Bitmap bit =linearView.getDrawingCache();
//
////        Bitmap bitmap = Bitmap.createBitmap(linearView.getWidth(), linearView.getHeight(), Bitmap.Config.ARGB_8888);
////        Canvas canvas = new Canvas(bitmap);
////        Drawable bgDrawable = linearView.getBackground();
////        if (bgDrawable != null) {
////            bgDrawable.draw(canvas);
////        } else {
////            canvas.drawColor(Color.WHITE);
////        }
////        linearView.draw(canvas);
//
//        return bit;// creates bitmap and returns the same
//    }
//    private Bitmap convertLayoutToImageEjape_Stock(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.print_stock_request_sewo30);
////            fill_theVocher( voucher);
//
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//        TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//
//        TextView compname,store, vhNo, date, custname, note,total_qty_text,salesName;
//        ImageView img = (ImageView) dialogs.findViewById(R.id.img);//
//        compname = (TextView) dialogs.findViewById(R.id.compname);
//        vhNo = (TextView) dialogs.findViewById(R.id.vhNo);
//        date = (TextView) dialogs.findViewById(R.id.date);
//        salesName = (TextView) dialogs.findViewById(R.id.salesman_name);
//        note = (TextView) dialogs.findViewById(R.id.note);
//        store= (TextView) dialogs.findViewById(R.id.store);
//        total_qty_text= (TextView) dialogs.findViewById(R.id.total_qty);
//        // total_qty
//
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//
////        img.setImageBitmap(companyInfo.getLogo());
//        compname.setText(companyInfo.getCompanyName());
//        if (companyInfo.getLogo()!=(null))
//        {
//            img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        String salesmaname=obj.getSalesmanName();
//        salesName.setText(salesmaname);
//        note.setText(voucher.getRemark());
//
//        store.setText(Login.salesMan);
//        int count=0;
//        String s="";
//
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        for (int j = 0; j < items.size(); j++) {
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <3; i++) {
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
////                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemNo());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            textView.setText("" + items.get(j).getQty());
//                            textView.setLayoutParams(lp2);
////                            textView.setText("" + items.get(j).getItemName().substring(0,6));
////                            textView.setLayoutParams(lp2);
//                            break;
//
//                        case 2:
//
//                            break;
//
//                    }
//                    row.addView(textView);
//
//
//                }
//                TextView textViews = new TextView(BluetoothConnectMenu.this);
//                textViews.setTextSize(14);
//                textViews.setPadding(0,0,0,5);
////                textViews.setTypeface(null, Typeface.BOLD);
//                textViews.setTextColor(getResources().getColor(R.color.text_view_color));
//                textViews.setText(items.get(j).getItemName());
////                rows.addView(textView);
//
//                tabLayout.addView(row);
//                tabLayout.addView(textViews);
//            }
//        }
//
//
//        total_qty_text.setText(count+"");
//        Log.e("countItem",""+count);
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//
//        return bit;// creates bitmap and returns the same
//    }
//
//    int count=0;
//    private Bitmap convertLayoutToImage_Body(Voucher voucher,List<Item> items,int visible) {
//        LinearLayout linearView = null;
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.body_voucher_print);
////            fill_theVocher( voucher);
//        TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//        TextView  total, discount, tax, ammont, textW;
//        textW = (TextView) dialogs.findViewById(R.id.wa1);
////        int count=0;
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//        TableRow row_header=(TableRow)dialogs.findViewById(R.id.row_header);
//        if(visible==0)
//        {
//            row_header.setVisibility(View.VISIBLE);
//        }
//        else {
//            row_header.setVisibility(View.INVISIBLE);
//        }
//
//        if (obj.getAllSettings().get(0).getUseWeightCase() != 1) {
//            textW.setVisibility(View.GONE);
//        } else {
//            textW.setVisibility(View.VISIBLE);
//        }
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        Log.e("itemSize",""+items.size());
//
//        for (int j = 0; j < items.size(); j++) {
//
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <= 7; i++) {
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(4);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
//                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemName());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getUnit());
//                                textView.setLayoutParams(lp2);
//                            } else {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                            }
//                            break;
//
//                        case 2:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                                textView.setVisibility(View.VISIBLE);
//                            } else {
//                                textView.setVisibility(View.GONE);
//                            }
//                            break;
//
//                        case 3:
//                            textView.setText("" + items.get(j).getPrice());
//                            textView.setLayoutParams(lp2);
//                            break;
//
//
//                        case 4:
//                            String amount = "" + (items.get(j).getQty() * items.get(j).getPrice() - items.get(j).getDisc());
////                            amount = convertToEnglish(amount);
//                            amount =String.valueOf(decimalFormat.format(Double.parseDouble(amount)));
//                            textView.setText(convertToEnglish(amount));
//                            textView.setLayoutParams(lp2);
//                            break;
//                    }
//                    row.addView(textView);
//                }
//
//
//                tabLayout.addView(row);
//            }
//        }
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//    }
//    private Bitmap convertLayoutToImage_Body_ejabi(Voucher voucher,List<Item> items,int visible) {
//        LinearLayout linearView = null;
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.body_voucher_print_ejabe);
////            fill_theVocher( voucher);
//        TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//        TextView  total, discount, tax, ammont, textW;
//        textW = (TextView) dialogs.findViewById(R.id.wa1);
////        int count=0;
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//        TableRow row_header=(TableRow)dialogs.findViewById(R.id.row_header);
//        if(visible==0)
//        {
//            row_header.setVisibility(View.VISIBLE);
//        }
//        else {
//            row_header.setVisibility(View.INVISIBLE);
//        }
//
//        if (obj.getAllSettings().get(0).getUseWeightCase() != 1) {
//            textW.setVisibility(View.GONE);
//        } else {
//            textW.setVisibility(View.VISIBLE);
//        }
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        Log.e("itemSize",""+items.size());
//
//        for (int j = 0; j < items.size(); j++) {
//
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <= 7; i++) {
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(4);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
////                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemNo());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getUnit());
//                                textView.setLayoutParams(lp2);
//                            } else {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                            }
//                            break;
//
//                        case 2:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                                textView.setVisibility(View.VISIBLE);
//                            } else {
//                                textView.setVisibility(View.GONE);
//                            }
//                            break;
//
//                        case 3:
//                            textView.setText("" + items.get(j).getPrice());
//                            textView.setLayoutParams(lp2);
//                            break;
//
//
//                        case 4:
//                            String amount = "" + (items.get(j).getQty() * items.get(j).getPrice() - items.get(j).getDisc());
////                            amount = convertToEnglish(amount);
//                            amount =String.valueOf(decimalFormat.format(Double.parseDouble(amount)));
//                            textView.setText(convertToEnglish(amount));
//                            textView.setLayoutParams(lp2);
//                            break;
//                    }
//                    row.addView(textView);
//                }
//
//                TextView textViews = new TextView(BluetoothConnectMenu.this);
//                textViews.setTextSize(14);
//                textViews.setPadding(0,0,0,5);
////                textViews.setTypeface(null, Typeface.BOLD);
//                textViews.setTextColor(getResources().getColor(R.color.text_view_color));
//                textViews.setText(items.get(j).getItemName());
//
//                tabLayout.addView(row);
//                tabLayout.addView(textViews);
//            }
//        }
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//    }



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
