package com.example.user54.InventoryApp;

import static com.example.user54.InventoryApp.controll.dataBaseNo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.user54.InventoryApp.Model.AssestItem;
import com.example.user54.InventoryApp.Model.ItemInfo;
import com.example.user54.InventoryApp.Model.ItemQR;
import com.example.user54.InventoryApp.Model.ItemQty;
import com.example.user54.InventoryApp.Model.ItemSwitch;
import com.example.user54.InventoryApp.Model.MainSetting;
import com.example.user54.InventoryApp.Model.Password;
import com.example.user54.InventoryApp.Model.Stk;
import com.example.user54.InventoryApp.Model.TblSetting;
import com.example.user54.InventoryApp.Model.TransferVhfSerial;
import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemInfoExp;
import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.Model.Order;
import com.example.user54.InventoryApp.Model.SewooSetting;
import com.example.user54.InventoryApp.Model.TransferItemsInfo;
import com.example.user54.InventoryApp.Model.UnitName;

import java.util.ArrayList;
import java.util.List;

public class InventoryDatabase extends SQLiteOpenHelper {

    private static int DATABASE_VERSION =dataBaseNo;//version Db
    private static final String DATABASE_Name = "InventoryDBase";//name Db

    static SQLiteDatabase Idb;

    //___________________________________________________________________________________
    private static final String ITEM_CARD = "ITEM_CARD";

    private static final String ITEM_CODE = "ITEM_CODE";
    private static final String ITEM_NAME = "ITEM_NAME";
    private static final String COST_PRC = "COST_PRC";
    private static final String SALE_PRC = "SALE_PRC";
    private static final String AVL_QTY = "AVL_QTY";
    private static final String FD_PRC = "FD_PRC";
    private static final String BRANCH_ID = "BRANCH_ID";
    private static final String BRANCH_NAME = "BRANCH_NAME";
    private static final String DEPARTMENT_ID = "DEPARTMENT_ID";
    private static final String DEPARTMENT_NAME = "DEPARTMENT_NAME";
    private static final String ITEM_G = "ITEM_G";
    private static final String ITEM_K = "ITEM_K";
    private static final String ITEM_L = "ITEM_L";
    private static final String ITEM_DIV = "ITEM_DIV";
    private static final String ITEM_GS = "ITEM_GS";
    private static final String ORG_PRICE = "ORG_PRICE";
    private static final String IN_DATE = "IN_DATE";
    private static final String IS_EXPORT = "IS_EXPORT";
    private static final String IS_NEW = "IS_NEW";
    private static final String ITEM_M = "ITEM_M";

    //________________________________________________________________________________

    private static final String ITEM_INFO_EXP = "ITEM_INFO_EXP";

    private static final String ITEM_CODE1 = "ITEM_CODE";
    private static final String ITEM_NAME1 = "ITEM_NAME";
    private static final String ITEM_QTY1 = "ITEM_QTY";
    private static final String EXP_DATE1 = "EXP_DATE";
    private static final String BATCH_NO1 = "BATCH_NO";
    private static final String ROW_INDEX1 = "ROW_INDEX";
    private static final String AVL_QTY1 = "AVL_QTY";
    private static final String COST_PRC1 = "COST_PRC";
    private static final String SALE_PRC1 = "SALE_PRC";
    private static final String RECEIPT_NO1 = "RECEIPT_NO";
    private static final String SERIAL_NO1 = "SERIAL_NO";


    //________________________________________________________________________________

    private static final String ITEM_INFO_EXP_REC = "ITEM_INFO_EXP_REC";

    private static final String ITEM_CODE2 = "ITEM_CODE";
    private static final String ITEM_NAME2 = "ITEM_NAME";
    private static final String ITEM_QTY2 = "ITEM_QTY";
    private static final String EXP_DATE2 = "EXP_DATE";
    private static final String BATCH_NO2 = "BATCH_NO";
    private static final String ROW_INDEX2 = "ROW_INDEX";
    private static final String AVL_QTY2 = "AVL_QTY";
    private static final String COST_PRC2 = "COST_PRC";
    private static final String SALE_PRC2 = "SALE_PRC";
    private static final String RECEIPT_NO2 = "RECEIPT_NO";
    private static final String SERIAL_NO2 = "SERIAL_NO";

    //________________________________________________________________________________

    private static final String ITEMS_QTY = "ITEMS_QTY";

    private static final String ITEM_CODE3 = "ITEM_CODE";
    private static final String ITEM_NAME3 = "ITEM_NAME";
    private static final String QTY3 = "QTY";

    //________________________________________________________________________________

    private static final String ITEMS_INFO = "ITEMS_INFO";

    private static final String ITEM_CODE4 = "ITEM_CODE";
    private static final String ITEM_NAME4 = "ITEM_NAME";
    private static final String ITEM_QTY4 = "ITEM_QTY";
    private static final String ROW_INDEX4 = "ROW_INDEX";
    private static final String ITEM_LOC4 = "ITEM_LOC";
    private static final String SERIAL_NO4 = "SERIAL_NO";
    private static final String EXPIRY_DATA4 = "EXPIRY_DATA";
    private static final String SALES_PRICE4 = "SALES_PRICE";
    private static final String TRN_DATE4 = "TRN_DATE";
    private static final String IS_EXPORT4 = "IS_EXPORT";
    private static final String ITEM_LOCATION4 = "ITEM_LOCATION";
    private static final String QR_CODE4 = "QR_CODE";
    private static final String LOT_NO4 = "LOT_NO";


    //________________________________________________________________________________

    private static final String ITEM_SWITCH = "ITEM_SWITCH";

    private static final String ITEM_O_CODE = "ITEM_O_CODE";
    private static final String ITEM_N_CODE = "ITEM_N_CODE";
    private static final String ITEM_NAME_A = "ITEM_NAME_A";
    private static final String ITEM_NAME_E = "ITEM_NAME_E";
    private static final String IN_DATE4 = "IN_DATE";

    //___________________________________________________________________________________
    private static final String ITEM_UNITS = "ITEM_UNITS";

    private static final String ITEM_O_CODE5 = "ITEM_O_CODE";
    private static final String ITEM_BARCODE5 = "ITEM_BARCODE";
    private static final String SALE_PRICE5 = "SALE_PRICE";
    private static final String ITEM_U5 = "ITEM_U";
    private static final String U_QTY5 = "U_QTY";
    private static final String U_SERIAL5 = "U_SERIAL5";
    private static final String CALC_QTY5 = "CALC_QTY";
    private static final String WHOLE_SALE_PRC5 = "WHOLE_SALE_PRC";
    private static final String PURCHASE_PRICE5 = "PURCHASE_PRICE";
    private static final String PCLASS1 = "PCLASS1";
    private static final String PCLASS2 = "PCLASS2";
    private static final String PCLASS3 = "PCLASS3";
    private static final String IN_DATE5 = "IN_DATE";
    private static final String UNIT_NAME5 = "UNIT_NAME";
    private static final String ORG_SALEPRICE = "ORG_SALE_PRICE";
    private static final String OLD_SALE_PRICE = "OLD_SALE_PRICE";
    private static final String UPDATE_DATE = "UPDATE_DATE";

    //___________________________________________________________________________________
    private static final String ORDERS = "ORDERS";

    private static final String STK_NO6 = "STK_NO";
    private static final String LOAD_DATE6 = "LOAD_DATE";
    private static final String LOAD_QTY6 = "LOAD_QTY";
    private static final String NET_QTY6 = "NET_QTY";
    private static final String ITEM_BARCODE6 = "ITEM_BARCODE";
    private static final String ITEM_CODE6 = "ITEM_CODE";

    //___________________________________________________________________________________
    private static final String PASSWORD = "PASSWORD";

    private static final String PASSWORD7 = "PASSWORD";


    //___________________________________________________________________________________
    private static final String SEWOO_SETTING = "SEWOO_SETTING";

    private static final String COMPORT = "COMPORT";
    private static final String BARCODE_X = "BARCODE_X";
    private static final String BARCODE_Y = "BARCODE_Y";
    private static final String ITEM_X = "ITEM_X";
    private static final String ITEM_Y = "ITEM_Y";
    private static final String ITEM_FONT_TYPE = "ITEM_FONT_TYPE";
    private static final String ITEM_FONT_SIZE = "ITEM_FONT_SIZE";
    private static final String PRC_X = "PRC_X";
    private static final String PRC_Y = "PRC_Y";
    private static final String PRC_FONT_TYPE = "PRC_FONT_TYPE";
    private static final String PRC_FONT_SIZE = "PRC_FONT_SIZE";
    private static final String BARCODE_TYPE = "BARCODE_TYPE";
    private static final String BARCODE_BN = "BARCODE_BN";
    private static final String COMP_NAME = "COMP_NAME";
    private static final String COMP_NM_X = "COMP_NM_X";
    private static final String COMP_NM_Y = "COMP_NM_Y";
    private static final String DEV_ID = "DEV_ID";

    //___________________________________________________________________________________
    private static final String STK = "STK";

    private static final String STK_NO = "STK_NO";
    private static final String STK_NAME = "STK_NAME";

    //___________________________________________________________________________________
    private static final String TBL_SETTING = "TBL_SETTING";

    private static final String SETTING_NAME = "SETTING_NAME";
    private static final String SETTING_INT_VAL = "SETTING_INT_VAL";
    private static final String SETTING_STR_VAL = "SETTING_STR_VAL";

    //___________________________________________________________________________________
    private static final String MAIN_SETTING_TABLE = "MAIN_SETTING_TABLE";

    private static final String IP_SETTING = "IP_SETTING";
    private static final String STORNO = "STORNO";
    private static final String IS_ASSEST = "IS_ASSEST";
    private static final String IS_QRJARD = "IS_QRJARD";
    private static final String ONLINE_PRICE = "ONLINE_PRICE";
    private static final String COMPANY_NO = "COMPANY_NO";
    private static final String PRINTER_TYPE="PRINTER_TYPE";
    private static final String CURRENCY_TYPE="CURRENCY_TYPE";
    private static final String CO_NAME="CO_NAME";
    private static final String NUMBER_TYPE="NUMBER_TYPE";
    private static final String ROTATE="ROTATE";
    private static final String DATABASE_NO="DATABASE_NO";
    private static final String ROOM_DATABASE_NO="ROOM_DATABASE_NO";
    private static final String RESIZE="RESIZE";
    private static final String SHELF_TAG_WIDTH="SHELF_TAG_WIDTH";
    private static final String SHELF_TAG_HEIGHT="SHELF_TAG_HEIGHT";
    private static final String IS_QTY_MINUS="IS_QTY_MINUS";
    private static final String ONlINEshlf="ONlINEshlf";
    private static final String SAVE_AFTER_UP="SAVE_AFTER_UP";
    private static final String COMPANY_NAME="COMPANY_NAME";


    //___________________________________________________________________________________
    private static final String TRANSFER_ITEMS_INFO = "TRANSFER_ITEMS_INFO";

    private static final String ITEM_CODE8 = "ITEM_CODE";
    private static final String ITEM_NAME8 = "ITEM_NAME";
    private static final String ITEM_QTY8 = "ITEM_QTY";
    private static final String FRM_STR8 = "FRM_STR";
    private static final String TO_STR8 = "TO_STR";
    private static final String ROW_INDEX8 = "ROW_INDEX";
    private static final String VHF_NO8 = "VHF_NO";
    private static final String ISEXPORT8 = "ISEXPORT";

    //___________________________________________________________________________________
    private static final String TRANSF_VHF_SER = "TRANSF_VHF_SER";

    private static final String VHF_SERIAL = "VHF_SERIAL";

    //___________________________________________________________________________________
    private static final String ASSEST_TABLE = "ASSEST_TABLE";

    private static final String ASSESST_CODE = "ASSESST_CODE";
    private static final String ASSESST_NAME = "ASSESST_NAME";
    private static final String ASSESST_TYPE = "ASSESST_TYPE";
    private static final String ASSESST_NO = "ASSESST_NO";
    private static final String ASSESST_MAINMNG = "ASSESST_MAINMNG";
    private static final String ASSESST_DEPARTMENT = "ASSESST_DEPARTMENT";
    private static final String ASSESST_SECTION = "ASSESST_SECTION";
    private static final String ASSESST_AREANAME = "ASSESST_AREANAME";
    private static final String ASSESST_BARCODE = "ASSESST_BARCODE";

    //___________________________________________________________________________________
    private static final String ASSEST_TABLE_INFO = "ASSEST_TABLE_INFO";

    private static final String ASSESST_CODE_INFO = "ASSESST_CODE";
    private static final String ASSESST_NAME_INFO= "ASSESST_NAME";
    private static final String ASSESST_TYPE_INFO = "ASSESST_TYPE";
    private static final String ASSESST_NO_INFO = "ASSESST_NO";
    private static final String ASSESST_MAINMNG_INFO= "ASSESST_MAINMNG";
    private static final String ASSESST_DEPARTMENT_INFO = "ASSESST_DEPARTMENT";
    private static final String ASSESST_SECTION_INFO = "ASSESST_SECTION";
    private static final String ASSESST_AREANAME_INFO = "ASSESST_AREANAME";
    private static final String ASSESST_QTY_INFO = "ASSESST_QTY_INFO";
    private static final String ASSESST_DATE_INFO = "ASSESST_DATE_INFO";
    private static final String ASSESST_ISEXPORT_INFO = "ASSESST_ISEXPORT_INFO";
    private static final String ASSESST_BARCODE_INFO = "ASSESST_BARCODE_INFO";
    private static final String ASSESST_SERIAL_INFO = "ASSESST_SERIAL_INFO";
    //___________________________________________________________________________________
    private static final String ASSEST_TABLE_INFO_BACKUP = "ASSEST_TABLE_INFO_BACKUP";

    private static final String ASSESST_CODE_INFO_BACKUP = "ASSESST_CODE_BACKUP";
    private static final String ASSESST_NAME_INFO_BACKUP= "ASSESST_NAME_BACKUP";
    private static final String ASSESST_TYPE_INFO_BACKUP = "ASSESST_TYPE_BACKUP";
    private static final String ASSESST_NO_INFO_BACKUP = "ASSESST_NO_BACKUP";
    private static final String ASSESST_MAINMNG_INFO_BACKUP= "ASSESST_MAINMNG_BACKUP";
    private static final String ASSESST_DEPARTMENT_INFO_BACKUP = "ASSESST_DEPARTMENT_BACKUP";
    private static final String ASSESST_SECTION_INFO_BACKUP = "ASSESST_SECTION_BACKUP";
    private static final String ASSESST_AREANAME_INFO_BACKUP = "ASSESST_AREANAME_BACKUP";
    private static final String ASSESST_QTY_INFO_BACKUP = "ASSESST_QTY_INFO_BACKUP";
    private static final String ASSESST_DATE_INFO_BACKUP = "ASSESST_DATE_INFO_BACKUP";
    private static final String ASSESST_ISEXPORT_INFO_BACKUP = "ASSESST_ISEXPORT_INFO_BACKUP";
    private static final String ASSESST_BARCODE_INFO_BACKUP = "ASSESST_BARCODE_INFO_BACKUP";
    private static final String ASSESST_SERIAL_INFO_BACKUP = "ASSESST_SERIAL_INFO_BACKUP";
    //________________________________________________________________________________

    private static final String ITEMS_INFO_BACKUP = "ITEMS_INFO_BACKUP";

    private static final String ITEM_CODE5 = "ITEM_CODE";
    private static final String ITEM_NAME5 = "ITEM_NAME";
    private static final String ITEM_QTY5= "ITEM_QTY";
    private static final String ROW_INDEX5 = "ROW_INDEX";
    private static final String ITEM_LOC5= "ITEM_LOC";
    private static final String SERIAL_NO5 = "SERIAL_NO";
    private static final String EXPIRY_DATA5 = "EXPIRY_DATA";
    private static final String SALES_PRICE5 = "SALES_PRICE";
    private static final String TRN_DATE5 = "TRN_DATE";
    private static final String IS_EXPORT5 = "IS_EXPORT";
    private static final String ITEM_LOCATION5 = "ITEM_LOCATION";
    private static final String QR_CODE5 = "QR_CODE";
    private static final String LOT_NO5 = "LOT_NO";
    private static final String IS_DELETE5 = "IS_DELETE";
    //________________________________________________________________________________

    private static final String ITEM_QR_CODE_TABLE = "ITEM_QR_CODE_TABLE";

    private static final String STR_NO7 = "STR_NO";
    private static final String ITEM_CODE7 = "ITEM_CODE";
    private static final String ITEM_NAME7 = "ITEM_NAME";
    private static final String PRICE7 = "PRICE";
    private static final String QR_CODE7 = "QR_CODE";
    private static final String LOT_NUMBER7 = "LOT_NUMBER";

    //___________________________________________________________________________________
    private static final String ACTIVATE_TABLE = "ACTIVATE_TABLE";

    private static final String ACTIVATE = "ACTIVATE";

    //___________________________________________________________________________________
    private static final String CURRENCY_TABLE = "CURRENCY_TABLE";

    private static final String CURRENCY_NAME = "CURRENCY_NAME";
    //___________________________________________________________________________________
    private static final String ITEM_UNIT = "ITEM_UNIT";

    private static final String UNITE_NAME = "UNITE_NAME";


    //_________________________________________________________________________________

    public InventoryDatabase(Context context,int data) {
        super(context, DATABASE_Name, null, data);
    }
    //__________________________________________________________________________________


    @Override
    public void onCreate(SQLiteDatabase Idb) {

        String CREATE_TABLE_ITEM_CARD = "CREATE TABLE " + ITEM_CARD + "("
                + ITEM_CODE + " NVARCHAR NOT NULL,"
                + ITEM_NAME + " NVARCHAR NOT NULL,"
                + COST_PRC + " NVARCHAR ,"
                + SALE_PRC + " NVARCHAR ,"
                + AVL_QTY + " NVARCHAR ,"
                + FD_PRC + " NVARCHAR,"
                + BRANCH_ID + " NVARCHAR,"
                + BRANCH_NAME + " NVARCHAR,"
                + DEPARTMENT_ID + " NVARCHAR,"
                + DEPARTMENT_NAME + " NVARCHAR,"
                + ITEM_G + " NVARCHAR,"
                + ITEM_K + " NVARCHAR,"
                + ITEM_L + " NVARCHAR,"
                + ITEM_DIV + " NVARCHAR,"
                + ITEM_GS + " NVARCHAR,"
                + ORG_PRICE + " NVARCHAR,"
                + IN_DATE + " NVARCHAR,"
                + IS_EXPORT + " NVARCHAR,"
                + IS_NEW + " NVARCHAR,"
                + ITEM_M + " NVARCHAR" + ")";
        Idb.execSQL(CREATE_TABLE_ITEM_CARD);

//=========================================================================================

        String CREATE_TABLE_ITEM_INFO_EXP = "CREATE TABLE " + ITEM_INFO_EXP + "("
                + ITEM_CODE1 + " NVARCHAR ,"
                + ITEM_NAME1 + " NVARCHAR ,"
                + ITEM_QTY1 + " FLOAT ,"
                + EXP_DATE1 + " NVARCHAR ,"
                + BATCH_NO1 + " NVARCHAR ,"
                + ROW_INDEX1 + " FLOAT ,"
                + AVL_QTY1 + " NVARCHAR ,"
                + COST_PRC1 + " NVARCHAR ,"
                + SALE_PRC1 + " NVARCHAR ,"
                + RECEIPT_NO1 + " NVARCHAR ,"
                + SERIAL_NO1 + " INTEGER " + ")";
        Idb.execSQL(CREATE_TABLE_ITEM_INFO_EXP);
//=========================================================================================

        String CREATE_TABLE_ITEM_INFO_EXP_PREC = "CREATE TABLE " + ITEM_INFO_EXP_REC + "("
                + ITEM_CODE2 + " NVARCHAR ,"
                + ITEM_NAME2 + " NVARCHAR ,"
                + ITEM_QTY2 + " FLOAT ,"
                + EXP_DATE2 + " NVARCHAR ,"
                + BATCH_NO2 + " NVARCHAR ,"
                + ROW_INDEX2 + " FLOAT ,"
                + AVL_QTY2 + " NVARCHAR ,"
                + COST_PRC2 + " NVARCHAR ,"
                + SALE_PRC2 + " NVARCHAR ,"
                + RECEIPT_NO2 + " NVARCHAR ,"
                + SERIAL_NO2 + " INTEGER " + ")";
        Idb.execSQL(CREATE_TABLE_ITEM_INFO_EXP_PREC);

//=========================================================================================

        String CREATE_TABLE_ITEM_QTY = "CREATE TABLE " + ITEMS_QTY + "("
                + ITEM_CODE3 + " NVARCHAR ,"
                + ITEM_NAME3 + " NVARCHAR ,"
                + QTY3 + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ITEM_QTY);

//=========================================================================================

        String CREATE_TABLE_ITEMS_INFO = "CREATE TABLE " + ITEMS_INFO + "("
                + ITEM_CODE4 + " NVARCHAR ,"
                + ITEM_NAME4 + " NVARCHAR ,"
                + ITEM_QTY4 +  " NVARCHAR ,"
                + ROW_INDEX4 + " NVARCHAR ,"
                + ITEM_LOC4 + " NVARCHAR ,"
                + SERIAL_NO4 + " INTEGER ,"
                + EXPIRY_DATA4 + " NVARCHAR ,"
                + SALES_PRICE4 + " NVARCHAR ,"
                + TRN_DATE4 + " NVARCHAR ,"
                + IS_EXPORT4 + " NVARCHAR ,"
                + ITEM_LOCATION4 + " NVARCHAR ,"
                + QR_CODE4 + " NVARCHAR ,"
                + LOT_NO4 +  " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ITEMS_INFO);

//=========================================================================================

        String CREATE_TABLE_ITEMS_SWITCH = "CREATE TABLE " + ITEM_SWITCH + "("
                + ITEM_O_CODE + " NVARCHAR NOT NULL ,"
                + ITEM_N_CODE + " NVARCHAR NOT NULL,"
                + ITEM_NAME_A + " NVARCHAR ,"
                + ITEM_NAME_E + " NVARCHAR ,"
                + IN_DATE4 + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ITEMS_SWITCH);
//=========================================================================================


        String CREATE_TABLE_ITEMS_UNITS = "CREATE TABLE " + ITEM_UNITS + "("
                + ITEM_O_CODE5 + " NVARCHAR  ,"
                + ITEM_BARCODE5 + " NVARCHAR ,"
                + SALE_PRICE5 + " FLOAT ,"
                + ITEM_U5 + " NVARCHAR ,"
                + U_QTY5 + " INT ,"
                + U_SERIAL5 + " INT ,"
                + CALC_QTY5 + " INT ,"
                + WHOLE_SALE_PRC5 + " FLOAT ,"
                + PURCHASE_PRICE5 + " FLOAT ,"
                + PCLASS1 + " FLOAT ,"
                + PCLASS2 + " FLOAT ,"
                + PCLASS3 + " FLOAT ,"
                + IN_DATE5 + " NVARCHAR ,"
                + UNIT_NAME5 + " NVARCHAR ,"
                + ORG_SALEPRICE + " NVARCHAR ,"
                + OLD_SALE_PRICE + " NVARCHAR ,"
                + UPDATE_DATE + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ITEMS_UNITS);

//=========================================================================================

        String CREATE_TABLE_ORDERS = "CREATE TABLE " + ORDERS + "("
                + STK_NO6 + " INT  ,"
                + LOAD_DATE6 + " NVARCHAR ,"
                + LOAD_QTY6 + " FLOAT DEFAULT (0) ,"
                + NET_QTY6 + " FLOAT DEFAULT (0) ,"
                + ITEM_BARCODE6 + " NVARCHAR ,"
                + ITEM_CODE6 + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ORDERS);

//=========================================================================================

        String CREATE_TABLE_PASSWORD = "CREATE TABLE " + PASSWORD + "("
                + PASSWORD7 + " NVARCHAR NOT NULL " + ")";
        Idb.execSQL(CREATE_TABLE_PASSWORD);

//=========================================================================================

        String CREATE_TABLE_SEWOO_SETING = "CREATE TABLE " + SEWOO_SETTING + "("
                + COMPORT + " NVARCHAR NOT NULL  ,"
                + BARCODE_X + " INT NOT NULL ,"
                + BARCODE_Y + " INT NOT NULL ,"
                + ITEM_X + " INT NOT NULL ,"
                + ITEM_Y + " INT NOT NULL ,"
                + ITEM_FONT_TYPE + " INT NOT NULL ,"
                + ITEM_FONT_SIZE + " INT NOT NULL ,"
                + PRC_X + " INT NOT NULL ,"
                + PRC_Y + " INT NOT NULL ,"
                + PRC_FONT_TYPE + " INT NOT NULL ,"
                + PRC_FONT_SIZE + " INT NOT NULL ,"
                + BARCODE_TYPE + " INT ,"
                + BARCODE_BN + " INT ,"
                + COMP_NAME + " NVARCHAR ,"
                + COMP_NM_X + " NVARCHAR ,"
                + COMP_NM_Y + " NVARCHAR ,"
                + DEV_ID + " NVARCHAR  DEFAULT (1)" + ")";
        Idb.execSQL(CREATE_TABLE_SEWOO_SETING);

//=========================================================================================
        String CREATE_TABLE_STK = "CREATE TABLE " + STK + "("
                + STK_NO + " NVARCHAR   ,"
                + STK_NAME + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_STK);

//=========================================================================================
        String CREATE_TABLE_TBL_SETTING = "CREATE TABLE " + TBL_SETTING + "("
                + SETTING_NAME + " NVARCHAR   ,"
                + SETTING_INT_VAL + " INT DEFAULT (0)  ,"
                + SETTING_STR_VAL + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_TBL_SETTING);

//=========================================================================================
        String CREATE_TABLE_MAIN_SETTING = "CREATE TABLE " + MAIN_SETTING_TABLE + "("
                + IP_SETTING + " NVARCHAR   ,"
                + STORNO + " NVARCHAR   ,"
                + IS_ASSEST + " NVARCHAR   ,"
                + IS_QRJARD + " NVARCHAR   ,"
                + ONLINE_PRICE + " NVARCHAR   ,"
                + COMPANY_NO + " NVARCHAR   ,"
                + PRINTER_TYPE + " NVARCHAR   ,"
                + CURRENCY_TYPE + " NVARCHAR   ,"
                + CO_NAME + " NVARCHAR   ,"
                + NUMBER_TYPE + " NVARCHAR   ,"
                + ROTATE + " NVARCHAR   ,"
                + DATABASE_NO + " NVARCHAR   ,"
                + ROOM_DATABASE_NO + " NVARCHAR   ,"
                + RESIZE + " NVARCHAR   ,"
                + SHELF_TAG_WIDTH + " NVARCHAR   ,"
                + SHELF_TAG_HEIGHT + " NVARCHAR   ,"
                + IS_QTY_MINUS + " NVARCHAR ,"
                + ONlINEshlf + " NVARCHAR ,"
                + SAVE_AFTER_UP + " NVARCHAR ,"
                + COMPANY_NAME + " NVARCHAR " +

                ")";
        Idb.execSQL(CREATE_TABLE_MAIN_SETTING);

//=========================================================================================
        String CREATE_TABLE_TRANSFER_ITEM_INFO = "CREATE TABLE " + TRANSFER_ITEMS_INFO + "("
                + ITEM_CODE8 + " NVARCHAR   ,"
                + ITEM_NAME8 + " NVARCHAR   ,"
                + ITEM_QTY8 + " FLOAT   ,"
                + FRM_STR8 + " NVARCHAR ,"
                + TO_STR8 + " NVARCHAR ,"
                + ROW_INDEX8 + " FLOAT   ,"
                + VHF_NO8 + " INT NOT NULL   ,"
                + ISEXPORT8 + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_TRANSFER_ITEM_INFO);

//=========================================================================================
        String CREATE_TABLE_TRANSF_VHF_SERIAL = "CREATE TABLE " + TRANSF_VHF_SER + "("
                + VHF_SERIAL + " INT NOT NULL " + ")";
        Idb.execSQL(CREATE_TABLE_TRANSF_VHF_SERIAL);

//=========================================================================================
        String CREATE_TABLE_ASSEST_TABLE = "CREATE TABLE " + ASSEST_TABLE + "("
                + ASSESST_CODE + " NVARCHAR   ,"
                + ASSESST_NAME + " NVARCHAR   ,"
                + ASSESST_NO + " NVARCHAR   ,"
                + ASSESST_TYPE + " NVARCHAR   ,"
                + ASSESST_MAINMNG + " NVARCHAR   ,"
                + ASSESST_DEPARTMENT + " NVARCHAR   ,"
                + ASSESST_SECTION + " NVARCHAR   ,"
                + ASSESST_AREANAME + " NVARCHAR   ,"
                + ASSESST_BARCODE + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ASSEST_TABLE);

//=========================================================================================
        String CREATE_TABLE_ASSEST_INFO_TABLE = "CREATE TABLE " + ASSEST_TABLE_INFO + "("
                + ASSESST_CODE_INFO + " NVARCHAR   ,"
                + ASSESST_NAME_INFO + " NVARCHAR   ,"
                + ASSESST_NO_INFO + " NVARCHAR   ,"
                + ASSESST_TYPE_INFO + " NVARCHAR   ,"
                + ASSESST_MAINMNG_INFO + " NVARCHAR   ,"
                + ASSESST_DEPARTMENT_INFO + " NVARCHAR   ,"
                + ASSESST_SECTION_INFO + " NVARCHAR   ,"
                + ASSESST_AREANAME_INFO + " NVARCHAR   ,"
                + ASSESST_QTY_INFO + " NVARCHAR   ,"
                + ASSESST_DATE_INFO + " NVARCHAR   ,"
                + ASSESST_ISEXPORT_INFO + " NVARCHAR   ,"
                + ASSESST_BARCODE_INFO + " NVARCHAR   ,"
                + ASSESST_SERIAL_INFO + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ASSEST_INFO_TABLE);
//=========================================================================================
        String CREATE_TABLE_ASSEST_INFO_BACKUP_TABLE = "CREATE TABLE " + ASSEST_TABLE_INFO_BACKUP + "("
                + ASSESST_CODE_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_NAME_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_NO_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_TYPE_INFO_BACKUP+ " NVARCHAR   ,"
                + ASSESST_MAINMNG_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_DEPARTMENT_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_SECTION_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_AREANAME_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_QTY_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_DATE_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_ISEXPORT_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_BARCODE_INFO_BACKUP + " NVARCHAR   ,"
                + ASSESST_SERIAL_INFO_BACKUP + " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ASSEST_INFO_BACKUP_TABLE);
        //=========================================================================================

        String CREATE_TABLE_ITEMS_INFO_BACKUP = "CREATE TABLE " + ITEMS_INFO_BACKUP + "("
                + ITEM_CODE5 + " NVARCHAR ,"
                + ITEM_NAME5 + " NVARCHAR ,"
                + ITEM_QTY5 +  " NVARCHAR ,"
                + ROW_INDEX5 + " NVARCHAR ,"
                + ITEM_LOC5 + " NVARCHAR ,"
                + SERIAL_NO5 + " INTEGER ,"
                + EXPIRY_DATA5 + " NVARCHAR ,"
                + SALES_PRICE5 + " NVARCHAR ,"
                + TRN_DATE5 + " NVARCHAR ,"
                + IS_EXPORT5 + " NVARCHAR ,"
                + ITEM_LOCATION5 + " NVARCHAR ,"
                + QR_CODE5 + " NVARCHAR ,"
                + LOT_NO5 + " NVARCHAR ,"
                + IS_DELETE5 +  " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ITEMS_INFO_BACKUP);


        //=========================================================================================

        String CREATE_TABLE_ITEMS_QR_CODE_TABLE = "CREATE TABLE " + ITEM_QR_CODE_TABLE + "("
                + STR_NO7 + " NVARCHAR ,"
                + ITEM_CODE7 + " NVARCHAR ,"
                + ITEM_NAME7 +  " NVARCHAR ,"
                + PRICE7 + " NVARCHAR ,"
                + QR_CODE7 + " NVARCHAR ,"
                + LOT_NUMBER7 +  " NVARCHAR " + ")";
        Idb.execSQL(CREATE_TABLE_ITEMS_QR_CODE_TABLE);

        //=========================================================================================

        String CREATE_TABLE_ACTIVATE = "CREATE TABLE " + ACTIVATE_TABLE + "("
                + ACTIVATE + " NVARCHAR NOT NULL " + ")";
        Idb.execSQL(CREATE_TABLE_ACTIVATE);

//=========================================================================================

        String CREATE_TABLE_CURRENCY = "CREATE TABLE " + CURRENCY_TABLE + "("
                + CURRENCY_NAME + " NVARCHAR NOT NULL " + ")";
        Idb.execSQL(CREATE_TABLE_CURRENCY);

//=========================================================================================

        String CREATE_TABLE_UNITE = "CREATE TABLE " + ITEM_UNIT + "("
                + UNITE_NAME + " NVARCHAR NOT NULL " + ")";
        Idb.execSQL(CREATE_TABLE_UNITE);

//=========================================================================================

    }

    @Override
    public void onUpgrade(SQLiteDatabase Idb, int oldVersion, int newVersion) {

        try {
            Idb.execSQL("ALTER TABLE ITEMS_INFO ADD " + TRN_DATE4 + " TEXT");
        }catch (Exception e){
            Log.e("upgrade", "ITEMS_INFO TRN_DATE4");
        }
        try {
            Idb.execSQL("ALTER TABLE ITEM_CARD ADD " + IN_DATE + " TEXT " +" DEFAULT '05/03/2020'");
        }catch (Exception e){
            Log.e("upgrade", "ITEMS_INFO TRN_DATE4");
        }

        try {
            Idb.execSQL("ALTER TABLE ITEM_SWITCH ADD " + IN_DATE4 + " TEXT "+" DEFAULT '05/03/2020'");
        }catch (Exception e){
            Log.e("upgrade", "ITEMS_INFO TRN_DATE4");
        }

        try {
            Idb.execSQL("ALTER TABLE ITEMS_INFO ADD " + IS_EXPORT4 + " TEXT");
        }catch (Exception e){
            Log.e("upgrade", "ITEMS_INFO IS_EXPORT4");
        }

        try {
            Idb.execSQL("ALTER TABLE TRANSFER_ITEMS_INFO ADD " + ISEXPORT8 + " TEXT");
        }catch (Exception e){
            Log.e("upgrade", "TRANSFER_ITEMS_INFO IS_EXPORT8");
        }

        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + IS_ASSEST + " TEXT"+" DEFAULT '0'");
        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE IS_ASSEST");
        }

        try{
            String CREATE_TABLE_ASSEST_TABLE = "CREATE TABLE " + ASSEST_TABLE + "("
                    + ASSESST_CODE + " NVARCHAR   ,"
                    + ASSESST_NAME + " NVARCHAR   ,"
                    + ASSESST_NO + " NVARCHAR   ,"
                    + ASSESST_TYPE + " NVARCHAR " + ")";
            Idb.execSQL(CREATE_TABLE_ASSEST_TABLE);
        }catch (Exception e){
            Log.e("upgrade", "create ASSESST TABLE");
        }

        try {
            Idb.execSQL("ALTER TABLE ITEM_UNITS ADD " + ORG_SALEPRICE + " TEXT"+" DEFAULT '0'");
            Idb.execSQL("ALTER TABLE ITEM_UNITS ADD " + OLD_SALE_PRICE + " TEXT"+" DEFAULT '0'");
            Idb.execSQL("ALTER TABLE ITEM_UNITS ADD " + UPDATE_DATE + " TEXT"+" DEFAULT '0'");
        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE IS_ASSEST");
        }

        try {
            Idb.execSQL("ALTER TABLE ITEMS_INFO ADD " + ITEM_LOCATION4 + " TEXT"+" DEFAULT ''");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE IS_ASSEST");
        }

        try {
            Idb.execSQL("ALTER TABLE ASSEST_TABLE ADD " + ASSESST_MAINMNG + " TEXT"+" DEFAULT ''");
            Idb.execSQL("ALTER TABLE ASSEST_TABLE ADD " + ASSESST_DEPARTMENT + " TEXT"+" DEFAULT ''");
            Idb.execSQL("ALTER TABLE ASSEST_TABLE ADD " + ASSESST_SECTION + " TEXT"+" DEFAULT ''");
            Idb.execSQL("ALTER TABLE ASSEST_TABLE ADD " + ASSESST_AREANAME + " TEXT"+" DEFAULT ''");
        }catch (Exception e){
            Log.e("upgrade", "ASSEST_TABLE ASSESST_MAINMNG ,ASSESST_DEPARTMENT ,ASSESST_SECTION  ,ASSESST_AREANAME");
        }

        try{
            String CREATE_TABLE_ASSEST_INFO_TABLE = "CREATE TABLE " + ASSEST_TABLE_INFO + "("
                    + ASSESST_CODE_INFO + " NVARCHAR   ,"
                    + ASSESST_NAME_INFO + " NVARCHAR   ,"
                    + ASSESST_NO_INFO + " NVARCHAR   ,"
                    + ASSESST_TYPE_INFO + " NVARCHAR   ,"
                    + ASSESST_MAINMNG_INFO + " NVARCHAR   ,"
                    + ASSESST_DEPARTMENT_INFO + " NVARCHAR   ,"
                    + ASSESST_SECTION_INFO + " NVARCHAR   ,"
                    + ASSESST_AREANAME_INFO + " NVARCHAR   ,"
                    + ASSESST_QTY_INFO + " NVARCHAR   ,"
                    + ASSESST_DATE_INFO + " NVARCHAR " + ")";
            Idb.execSQL(CREATE_TABLE_ASSEST_INFO_TABLE);

        }catch (Exception e){
            Log.e("upgrade", "ASSEST_TABLE_INFO ");
        }

        try {
            Idb.execSQL("ALTER TABLE ASSEST_TABLE_INFO ADD " + ASSESST_ISEXPORT_INFO + " TEXT "+" DEFAULT '0'");
        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE IS_ASSEST");
        }


        try {
            Idb.execSQL("ALTER TABLE ASSEST_TABLE ADD " + ASSESST_BARCODE + " TEXT"+" DEFAULT ''");

        }catch (Exception e){
            Log.e("upgrade", "ASSEST_TABLE ASSESST_BARCODE");
        }


        try {
            Idb.execSQL("ALTER TABLE ASSEST_TABLE_INFO ADD " + ASSESST_BARCODE_INFO + " TEXT"+" DEFAULT ''");

        }catch (Exception e){
            Log.e("upgrade", "ASSEST_TABLE_INFO ASSESST_BARCODE");
        }


        try {
            Idb.execSQL("ALTER TABLE ITEMS_INFO ADD " + QR_CODE4 + " TEXT"+" DEFAULT '0'");
            Idb.execSQL("ALTER TABLE ITEMS_INFO ADD " + LOT_NO4 + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade", "ADD FAILD IN ITEMS_INFO QR_CODE4,LOT_NO4");
        }

        try {

            String CREATE_TABLE_ITEMS_INFO_BACKUP = "CREATE TABLE " + ITEMS_INFO_BACKUP + "("
                    + ITEM_CODE5 + " NVARCHAR ,"
                    + ITEM_NAME5 + " NVARCHAR ,"
                    + ITEM_QTY5 +  " NVARCHAR ,"
                    + ROW_INDEX5 + " NVARCHAR ,"
                    + ITEM_LOC5 + " NVARCHAR ,"
                    + SERIAL_NO5 + " INTEGER ,"
                    + EXPIRY_DATA5 + " NVARCHAR ,"
                    + SALES_PRICE5 + " NVARCHAR ,"
                    + TRN_DATE5 + " NVARCHAR ,"
                    + IS_EXPORT5 + " NVARCHAR ,"
                    + ITEM_LOCATION5 + " NVARCHAR ,"
                    + QR_CODE5 + " NVARCHAR ,"
                    + LOT_NO5 + " NVARCHAR ,"
                    + IS_DELETE5 +  " NVARCHAR " + ")";
            Idb.execSQL(CREATE_TABLE_ITEMS_INFO_BACKUP);


        }catch (Exception e){
            Log.e("upgrade", "ITEMS_INFO_BACKUP TABLE");
        }

        try {
            String CREATE_TABLE_ITEMS_QR_CODE_TABLE = "CREATE TABLE " + ITEM_QR_CODE_TABLE + "("
                    + STR_NO7 + " NVARCHAR ,"
                    + ITEM_CODE7 + " NVARCHAR ,"
                    + ITEM_NAME7 +  " NVARCHAR ,"
                    + PRICE7 + " NVARCHAR ,"
                    + QR_CODE7 + " NVARCHAR ,"
                    + LOT_NUMBER7 +  " NVARCHAR " + ")";
            Idb.execSQL(CREATE_TABLE_ITEMS_QR_CODE_TABLE);

        }catch (Exception e){
            Log.e("upgrade", "ITEM_QR_CODE_TABLE TABLE");
        }


        try{

            String CREATE_TABLE_ACTIVATE = "CREATE TABLE " + ACTIVATE_TABLE + "("
                    + ACTIVATE + " NVARCHAR NOT NULL " + ")";
            Idb.execSQL(CREATE_TABLE_ACTIVATE);

        }catch (Exception e){
            Log.e("upgrade", "ACTIVATE_TABLE TABLE");
        }

        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + IS_QRJARD + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE TABLE  IS_QRJARD");
        }

        try {
            Idb.execSQL("ALTER TABLE ITEM_CARD ADD " + IS_EXPORT + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade", "ITEM_CARD TABLE  IS_EXPORT");
        }



        try {
            Idb.execSQL("ALTER TABLE ITEM_CARD ADD " + IS_NEW + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade", "ITEM_CARD TABLE  IS_NEW");
        }


        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + ONLINE_PRICE + " TEXT"+" DEFAULT '0'");
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + COMPANY_NO + " TEXT"+" DEFAULT '290'");

        }catch (Exception e){
            Log.e("upgrade", "ITEM_CARD TABLE  IS_NEW");
        }

        try {
            Idb.execSQL("ALTER TABLE ASSEST_TABLE_INFO ADD " + ASSESST_SERIAL_INFO + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade", "ASSEST_TABLE_INFO");
        }


        try {
            String CREATE_TABLE_ASSEST_INFO_BACKUP_TABLE = "CREATE TABLE " + ASSEST_TABLE_INFO_BACKUP + "("// this for add item from table ASSEST_TABLE_INFO becuse use serial
                    + ASSESST_CODE_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_NAME_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_NO_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_TYPE_INFO_BACKUP+ " NVARCHAR   ,"
                    + ASSESST_MAINMNG_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_DEPARTMENT_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_SECTION_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_AREANAME_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_QTY_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_DATE_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_ISEXPORT_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_BARCODE_INFO_BACKUP + " NVARCHAR   ,"
                    + ASSESST_SERIAL_INFO_BACKUP + " NVARCHAR " + ")";
            Idb.execSQL(CREATE_TABLE_ASSEST_INFO_BACKUP_TABLE);

        }catch (Exception e){
            Log.e("upgrade", "ASSEST_TABLE_INFO_backup");
        }

        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + PRINTER_TYPE + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE TABLE  PRINTER_TYPE");
        }

        try{

            String CREATE_TABLE_CURRENCY = "CREATE TABLE " + CURRENCY_TABLE + "("
                    + CURRENCY_NAME + " NVARCHAR NOT NULL " + ")";
            Idb.execSQL(CREATE_TABLE_CURRENCY);

        }catch (Exception e){
            Log.e("upgrade", "CURRENCY_TABLE TABLE  create");
        }

        try{

            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + CURRENCY_TYPE + " TEXT"+" DEFAULT 'JD'");
        }catch (Exception e){

        }


        try{

            String CREATE_TABLE_UNITE = "CREATE TABLE " + ITEM_UNIT + "("
                    + UNITE_NAME + " NVARCHAR NOT NULL " + ")";
            Idb.execSQL(CREATE_TABLE_UNITE);
        }catch (Exception e){

        }

        try{

            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + CO_NAME + " TEXT"+" DEFAULT '0'");
        }catch (Exception e){

                }
        try {
            Idb.execSQL("ALTER TABLE ITEM_CARD ADD " + ITEM_M + " TEXT"+" DEFAULT ''");

        }catch (Exception e){
            Log.e("upgrade", "ASSEST_TABLE ASSESST_BARCODE");
        }
        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + NUMBER_TYPE + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE NUMBER_TYPE");
        }

        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + ROTATE + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE ROTATE");
        }

        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + DATABASE_NO + " TEXT"+" DEFAULT '51'");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE DATABASE_NO");
        }


        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + ROOM_DATABASE_NO + " TEXT"+" DEFAULT '51'");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE ROOM_DATABASE_NO");
        }

        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + RESIZE + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE RESIZE");
        }

        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + SHELF_TAG_WIDTH + " TEXT"+" DEFAULT '200'");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE SHELF_TAG_WIDTH");
        }

        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + SHELF_TAG_HEIGHT + " TEXT"+" DEFAULT '200'");

        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE SHELF_TAG_HEIGHT");
        }


        try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + IS_QTY_MINUS + " TEXT"+" DEFAULT '0'");
        }catch (Exception e){
            Log.e("upgrade", "MAIN_SETTING_TABLE IS_ASSEST");
        }

        try {
            Log.e("upgrade","MAIN_SETTING_TABLE TABLE  ONlINEshlf");
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + ONlINEshlf + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("Exceptionupgrade", "MAIN_SETTING_TABLE TABLE  ONlINEshlf");
        }

        try {
            Log.e("upgrade","MAIN_SETTING_TABLE TABLE  SAVE_AFTER_UP");
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + SAVE_AFTER_UP + " TEXT"+" DEFAULT '0'");

        }catch (Exception e){
            Log.e("Exceptionupgrade", "MAIN_SETTING_TABLE TABLE  SAVE_AFTER_UP");
        }

        try {
            Log.e("upgrade","MAIN_SETTING_TABLE TABLE  COMPANY_NAME");
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + COMPANY_NAME + " TEXT"+" DEFAULT '_'");

        }catch (Exception e){
            Log.e("Exceptionupgrade", "MAIN_SETTING_TABLE TABLE  COMPANY_NAME");
        }
    }


    public void addItemcard(ItemInfo itemInfo) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_CODE4, convertToEnglish(itemInfo.getItemCode()));
        values.put(ITEM_NAME4, itemInfo.getItemName());
        values.put(ITEM_QTY4,convertToEnglish(""+itemInfo.getItemQty()));
        values.put(ROW_INDEX4, itemInfo.getRowIndex());
        values.put(ITEM_LOC4, itemInfo.getItemLocation());
        values.put(SERIAL_NO4, itemInfo.getSerialNo());
        values.put(EXPIRY_DATA4,  convertToEnglish(itemInfo.getExpDate()));
        values.put(SALES_PRICE4,  convertToEnglish(""+itemInfo.getSalePrice()));
        values.put(TRN_DATE4,  convertToEnglish(itemInfo.getTrnDate()));
        values.put(IS_EXPORT4, itemInfo.getIsExport());
        values.put(ITEM_LOCATION4, itemInfo.getLocation());
        values.put(QR_CODE4, itemInfo.getQRCode());
        values.put(LOT_NO4, itemInfo.getLotNo());

        Idb.insert(ITEMS_INFO, null, values);
        Idb.close();
    }


    public void addItemU(UnitName itemInfo) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(UNITE_NAME, convertToEnglish(itemInfo.getItemUnitN()));


        Idb.insert(ITEM_UNIT, null, values);
        Idb.close();
    }

    public void addCurrencyTable(String currencyName) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(CURRENCY_NAME, convertToEnglish(currencyName));


        Idb.insert(CURRENCY_TABLE, null, values);
        Idb.close();
    }


    public void addItemInfoBackup(ItemInfo itemInfo) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_CODE5, convertToEnglish(itemInfo.getItemCode()));
        values.put(ITEM_NAME5, itemInfo.getItemName());
        values.put(ITEM_QTY5,convertToEnglish(""+itemInfo.getItemQty()));
        values.put(ROW_INDEX5, itemInfo.getRowIndex());
        values.put(ITEM_LOC5, itemInfo.getItemLocation());
        values.put(SERIAL_NO5, itemInfo.getSerialNo());
        values.put(EXPIRY_DATA5,  convertToEnglish(itemInfo.getExpDate()));
        values.put(SALES_PRICE5,  convertToEnglish(""+itemInfo.getSalePrice()));
        values.put(TRN_DATE5,  convertToEnglish(itemInfo.getTrnDate()));
        values.put(IS_EXPORT5, itemInfo.getIsExport());
        values.put(ITEM_LOCATION5, itemInfo.getLocation());
        values.put(QR_CODE5, itemInfo.getQRCode());
        values.put(LOT_NO5, itemInfo.getLotNo());
        values.put(IS_DELETE5, itemInfo.getIsDelete());

        Idb.insert(ITEMS_INFO_BACKUP, null, values);
        Idb.close();
    }

    public void addItemQR(ItemQR itemQR) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(STR_NO7, itemQR.getStoreNo());
        values.put(ITEM_CODE7, itemQR.getItemCode());
        values.put(ITEM_NAME7,itemQR.getItemNmae());
        values.put(PRICE7, itemQR.getSalesPrice());
        values.put(QR_CODE7, itemQR.getQrCode());
        values.put(LOT_NUMBER7, itemQR.getLotNo());

        Idb.insert(ITEM_QR_CODE_TABLE, null, values);
        Idb.close();
    }




    public void addVhf(TransferVhfSerial itemInfo) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(VHF_SERIAL, convertToEnglish( ""+itemInfo.getVhfSerial()));


        Idb.insert(TRANSF_VHF_SER, null, values);
        Idb.close();
    }

    public void addItemTransferInfo(TransferItemsInfo itemInfo) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        values.put(ITEM_CODE8, itemInfo.getItemCode());
        values.put(ITEM_NAME8, itemInfo.getItemName());
        values.put(ITEM_QTY8,  convertToEnglish(""+itemInfo.getItemQty()));
        values.put(FRM_STR8,  convertToEnglish(itemInfo.getFromStr()));
        values.put(TO_STR8,  convertToEnglish(itemInfo.getToStr()));
        values.put(ROW_INDEX8,  convertToEnglish(""+itemInfo.getRowIndex()));
        values.put(VHF_NO8,  convertToEnglish(""+itemInfo.getVhfNo()));
        values.put(ISEXPORT8, itemInfo.getIsExport());



        Idb.insert(TRANSFER_ITEMS_INFO, null, values);
        Idb.close();
    }


    public void addAllMainSetting(MainSetting mainSetting) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(IP_SETTING, convertToEnglish( mainSetting.getIP()));
        values.put(STORNO,  convertToEnglish(mainSetting.getStorNo()));
        values.put(IS_ASSEST,  convertToEnglish(mainSetting.getIsAssest()));
        values.put(IS_QRJARD,  convertToEnglish(mainSetting.getIsQr()));

        values.put(ONLINE_PRICE,  convertToEnglish(mainSetting.getOnlinePrice()));
        values.put(COMPANY_NO,  convertToEnglish(mainSetting.getCompanyNo()));
        values.put(PRINTER_TYPE,  convertToEnglish(mainSetting.getPrinterType()));
        values.put(CURRENCY_TYPE,  convertToEnglish(mainSetting.getCurrencyType()));
        values.put(CO_NAME,  convertToEnglish(""+mainSetting.getCoName()));
        values.put(DATABASE_NO,  (""+mainSetting.getDataBaseNo()));
        values.put(ROOM_DATABASE_NO,  (""+mainSetting.getDataBaseNoRoom()));

        values.put(NUMBER_TYPE,  convertToEnglish(""+mainSetting.getNumberType()));
        values.put(ROTATE,  convertToEnglish(""+mainSetting.getRotate()));
        values.put(RESIZE,  convertToEnglish(""+mainSetting.getReSize()));
        values.put(SHELF_TAG_WIDTH,  convertToEnglish(""+mainSetting.getWidth()));
        values.put(SHELF_TAG_HEIGHT,  convertToEnglish(""+mainSetting.getHeight()));
        values.put(IS_QTY_MINUS,  convertToEnglish(""+mainSetting.getIsMinus()));
        values.  put(ONlINEshlf,  convertToEnglish(""+mainSetting.getONlINEshlf()));
        values.  put(SAVE_AFTER_UP,  convertToEnglish(""+mainSetting.getIsSaveAfterUp()));
        values.  put(COMPANY_NAME,  convertToEnglish(""+mainSetting.getCompanyName()));

        Idb.insert(MAIN_SETTING_TABLE, null, values);
        Idb.close();
    }


    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public void addItemcardTable(ItemCard itemInfo) {
         Idb = this.getReadableDatabase();
        Idb.beginTransaction();
        ContentValues values = new ContentValues();

        values.put(ITEM_CODE, itemInfo.getItemCode());
        values.put(ITEM_NAME, itemInfo.getItemName());
        values.put(COST_PRC, itemInfo.getCostPrc());
        values.put(SALE_PRC, convertToEnglish( itemInfo.getSalePrc()));
        values.put(AVL_QTY,  convertToEnglish(itemInfo.getAVLQty()));
        values.put(FD_PRC,  convertToEnglish(itemInfo.getFDPRC()));
        values.put(BRANCH_ID,  convertToEnglish(itemInfo.getBranchId()));
        values.put(BRANCH_NAME, itemInfo.getBranchName());
        values.put(DEPARTMENT_ID, itemInfo.getDepartmentId());
        values.put(DEPARTMENT_NAME, itemInfo.getDepartmentName());
        values.put(ITEM_G, itemInfo.getItemG());
        values.put(ITEM_K, itemInfo.getItemK());
        values.put(ITEM_L, itemInfo.getItemL());
        values.put(ITEM_DIV, itemInfo.getItemDiv());
        values.put(ITEM_GS, itemInfo.getItemGs());
        values.put(ORG_PRICE,  convertToEnglish(itemInfo.getOrgPrice()));
        values.put(IN_DATE,  itemInfo.getInDate());
        values.put(IS_EXPORT,  itemInfo.getIsExport());
        values.put(IS_NEW,  itemInfo.getIsNew());
        values.put(ITEM_M,  itemInfo.getItemM());

        Idb.insertWithOnConflict(ITEM_CARD, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Idb.setTransactionSuccessful();
        Idb.endTransaction();

       // Idb.close();
    }
//    public void addItemcardTableTest(List<ItemCard> itemInfo) {
//        SQLiteDatabase Idb = this.getReadableDatabase();
//        Idb.beginTransaction();
//        ContentValues  values []= new ContentValues[itemInfo.size()];
//        for(int i=0;i<itemInfo.size();i+=10) {
//
//           String q= " INSERT INTO ITEM_CARD VALUES("+itemInfo.get(i).getItemCode()+","+ '123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555'),"
//                    +" ('123555555', '123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555'),"
//                    +" ('123555555', '123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555','123555555');";
//
//
//            Idb.execSQL(q);
//        }
//
//
//        Idb.close();
//    }

    public void addItemcardTableTestAll(List<ItemCard> itemInfo) {//test_1
        SQLiteDatabase Idb = this.getReadableDatabase();
        Idb.beginTransaction();
        ContentValues  values []= new ContentValues[itemInfo.size()];

        String q="INSERT INTO ITEM_CARD VALUES";
        for(int i=0;i<itemInfo.size();i++) {
            q+="("+
                    itemInfo.get(i).getItemCode()
                    +","+ itemInfo.get(i).getItemName()
                    +","+itemInfo.get(i).getCostPrc()
                   +","+itemInfo.get(i).getSalePrc()
                    +","+itemInfo.get(i).getAVLQty()
                    +","+itemInfo.get(i).getFDPRC()
                    +","+itemInfo.get(i).getBranchId()
                    +","+itemInfo.get(i).getBranchName()
                    +","+itemInfo.get(i).getDepartmentId()
                    +","+itemInfo.get(i).getDepartmentName()
                    +","+itemInfo.get(i).getItemG()
                    +","+itemInfo.get(i).getItemK()
                    +","+itemInfo.get(i).getItemL()
                    +","+itemInfo.get(i).getItemDiv()
                    +","+itemInfo.get(i).getItemGs()
                    +","+itemInfo.get(i).getOrgPrice()
                    +","+itemInfo.get(i).getInDate()
                    +","+itemInfo.get(i).getIsExport()
                    +","+itemInfo.get(i).getIsNew()
                    +"),";

        }


        q=q.substring(0,q.lastIndexOf(","))+";";
        Idb.execSQL(q);

        Idb.close();
    }

    public void addItemcardTableTestAll_2(String itemInfo) {//test_1
        SQLiteDatabase Idb = this.getReadableDatabase();
        Idb.beginTransaction();
//        ContentValues  values []= new ContentValues[itemInfo.size()];

        itemInfo=itemInfo.substring(0,itemInfo.lastIndexOf(","))+";";
        Log.e("rrrrrrrrrrr=",""+itemInfo);



        Idb.execSQL(itemInfo);
        Idb.setTransactionSuccessful();
        Idb.endTransaction();
        Idb.close();
        Log.e("rrrrrrrrrrr2=","done");

    }
        public void addItemcardTableTester(List<ItemCard> itemInfo) {

        SQLiteDatabase Idb = this.getReadableDatabase();
        Idb.beginTransaction();
        for (int i = 0; i < itemInfo.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(ITEM_CODE, itemInfo.get(i).getItemCode());
            values.put(ITEM_NAME, itemInfo.get(i).getItemName());
            values.put(COST_PRC, itemInfo.get(i).getCostPrc());
            values.put(SALE_PRC, convertToEnglish( itemInfo.get(i).getSalePrc()));
            values.put(AVL_QTY,  convertToEnglish(itemInfo.get(i).getAVLQty()));
            values.put(FD_PRC,  convertToEnglish(itemInfo.get(i).getFDPRC()));
            values.put(BRANCH_ID,  convertToEnglish(itemInfo.get(i).getBranchId()));
            values.put(BRANCH_NAME, itemInfo.get(i).getBranchName());
            values.put(DEPARTMENT_ID, itemInfo.get(i).getDepartmentId());
            values.put(DEPARTMENT_NAME, itemInfo.get(i).getDepartmentName());
            values.put(ITEM_G, itemInfo.get(i).getItemG());
            values.put(ITEM_K, itemInfo.get(i).getItemK());
            values.put(ITEM_L, itemInfo.get(i).getItemL());
            values.put(ITEM_DIV, itemInfo.get(i).getItemDiv());
            values.put(ITEM_GS, itemInfo.get(i).getItemGs());
            values.put(ORG_PRICE,  convertToEnglish(itemInfo.get(i).getOrgPrice()));
            values.put(IN_DATE,  itemInfo.get(i).getInDate());
            values.put(IS_EXPORT, itemInfo.get(i).getIsExport());
            values.put(IS_NEW,  itemInfo.get(i).getIsNew());
            values.put(ITEM_M,  itemInfo.get(i).getItemM());

            Idb.insertWithOnConflict(ITEM_CARD, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        }

        Idb.setTransactionSuccessful();
        Idb.endTransaction();
//        Idb.close();
    }


//    public  void addTable(List<ItemCard> itemInfo) {
//
//        final long startTime = System.currentTimeMillis();
//
//        try {
//            Idb.execSQL("PRAGMA synchronous=OFF");
//            Idb.setLockingEnabled(false);
//            Idb.beginTransaction();
//            DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(Idb, "ITEM_CARD");
//            for (int i = 0; i < itemInfo.size(); i++) {
//                ih.prepareForInsert();
//
//                ih.bind(nameColumn, itemInfo.get(i));
//                ih.bind(1, itemInfo.get(i).getItemName());
//                ih.bind(ITEM_CODE, itemInfo.get(i).getItemCode());
//                ih.bind(COST_PRC, itemInfo.get(i).getCostPrc());
//                ih.bind(SALE_PRC, convertToEnglish( itemInfo.get(i).getSalePrc()));
//                ih.bind(AVL_QTY,  convertToEnglish(itemInfo.get(i).getAVLQty()));
//                ih.bind(FD_PRC,  convertToEnglish(itemInfo.get(i).getFDPRC()));
//                values.put(BRANCH_ID,  convertToEnglish(itemInfo.get(i).getBranchId()));
//                values.put(BRANCH_NAME, itemInfo.get(i).getBranchName());
//                values.put(DEPARTMENT_ID, itemInfo.get(i).getDepartmentId());
//                values.put(DEPARTMENT_NAME, itemInfo.get(i).getDepartmentName());
//                values.put(ITEM_G, itemInfo.get(i).getItemG());
//                values.put(ITEM_K, itemInfo.get(i).getItemK());
//                values.put(ITEM_L, itemInfo.get(i).getItemL());
//                values.put(ITEM_DIV, itemInfo.get(i).getItemDiv());
//                values.put(ITEM_GS, itemInfo.get(i).getItemGs());
//                values.put(ORG_PRICE,  convertToEnglish(itemInfo.get(i).getOrgPrice()));
//                values.put(IN_DATE,  itemInfo.get(i).getInDate());
//                values.put(IS_EXPORT, itemInfo.get(i).getIsExport());
//                values.put(IS_NEW,  itemInfo.get(i).getIsNew());
//
//
//
//
//
//
//                ih.execute();
//            }
//            Idb.setTransactionSuccessful();
//        } finally {
//            Idb.endTransaction();
//            Idb.setLockingEnabled(true);
//            Idb.execSQL("PRAGMA synchronous=NORMAL");
//            ih.close();
////            if (Globals.ENABLE_LOGGING) {
//                final long endtime = System.currentTimeMillis();
//                Log.i("Time to insert: ", String.valueOf(endtime - startTime));
////            }
//        }
//
//    }





    public void addItemUniteTable(List<ItemUnit> itemUnits) {

        SQLiteDatabase Idb = this.getReadableDatabase();
        Idb.beginTransaction();

        for (int i = 0; i < itemUnits.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(ITEM_O_CODE5, itemUnits.get(i).getItemOCode());
            values.put(ITEM_BARCODE5, itemUnits.get(i).getItemBarcode());
            values.put(SALE_PRICE5, itemUnits.get(i).getSalePrice());
            values.put(ITEM_U5, convertToEnglish( itemUnits.get(i).getItemU()));
            values.put(U_QTY5,  convertToEnglish(""+itemUnits.get(i).getUQty()));
            values.put(U_SERIAL5,  convertToEnglish(""+itemUnits.get(i).getuSerial()));
            values.put(CALC_QTY5,  convertToEnglish(""+itemUnits.get(i).getCalcQty()));
            values.put(WHOLE_SALE_PRC5, itemUnits.get(i).getWholeSalePrc());
            values.put(PURCHASE_PRICE5, itemUnits.get(i).getPurchasePrc());
            values.put(PCLASS1, itemUnits.get(i).getPclAss1());
            values.put(PCLASS2, itemUnits.get(i).getPclAss2());
            values.put(PCLASS3, itemUnits.get(i).getPclAss3());
            values.put(IN_DATE5, itemUnits.get(i).getInDate());
            values.put(UNIT_NAME5, itemUnits.get(i).getUnitName());
            values.put(ORG_SALEPRICE, itemUnits.get(i).getOrgSalePrice());
            values.put(OLD_SALE_PRICE,  convertToEnglish(itemUnits.get(i).getOldSalePrice()));
            values.put(UPDATE_DATE,  itemUnits.get(i).getUpdateDate());

            Idb.insertWithOnConflict(ITEM_UNITS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        }

        Idb.setTransactionSuccessful();
        Idb.endTransaction();
//        Idb.close();
    }




    public void addStory(Stk Stk) {
        SQLiteDatabase Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(STK_NAME, Stk.getStkName());
        values.put(STK_NO,  convertToEnglish(Stk.getStkNo()));

        Idb.insert(STK, null, values);
        Idb.close();
    }

    public void addItemSwitch(ItemSwitch itemSwitch) {
        SQLiteDatabase Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_O_CODE, itemSwitch.getItemOCode());
        values.put(ITEM_N_CODE, itemSwitch.getItemNCode());
        values.put(ITEM_NAME_A, itemSwitch.getItemNameA());
        values.put(ITEM_NAME_E, itemSwitch.getItemNameE());
        values.put(IN_DATE4, itemSwitch.getInDate());


        Idb.insert(ITEM_SWITCH, null, values);
        Idb.close();
    }

    public void addActive(String activ) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ACTIVATE,activ );


        Idb.insert(ACTIVATE_TABLE, null, values);
        Idb.close();
    }


    public void addItemSwitchTester(List<ItemSwitch> itemSwitch) {

        SQLiteDatabase Idb = this.getReadableDatabase();
        Idb.beginTransaction();

        for (int i = 0; i < itemSwitch.size(); i++) {
            ContentValues values = new ContentValues();

            values.put(ITEM_O_CODE, itemSwitch.get(i).getItemOCode());
            values.put(ITEM_N_CODE, itemSwitch.get(i).getItemNCode());
            values.put(ITEM_NAME_A, itemSwitch.get(i).getItemNameA());
            values.put(ITEM_NAME_E, itemSwitch.get(i).getItemNameE());
            values.put(IN_DATE4, itemSwitch.get(i).getInDate());


//            Idb.insert(ITEM_SWITCH, null, values);
            Idb.insertWithOnConflict(ITEM_SWITCH, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
        Idb.setTransactionSuccessful();
        Idb.endTransaction();

    }




    public void addItemQRList(List<ItemQR> itemQRList) {
        SQLiteDatabase Idb = this.getReadableDatabase();
        Idb.beginTransaction();



        for (int i = 0; i < itemQRList.size(); i++) {
            ContentValues values = new ContentValues();

            values.put(STR_NO7, itemQRList.get(i).getStoreNo());
            values.put(ITEM_CODE7,  itemQRList.get(i).getItemCode());
            values.put(ITEM_NAME7, itemQRList.get(i).getItemNmae());
            values.put(PRICE7,  itemQRList.get(i).getSalesPrice());
            values.put(QR_CODE7,  itemQRList.get(i).getQrCode());
            values.put(LOT_NUMBER7,  itemQRList.get(i).getLotNo());

            Idb.insertWithOnConflict(ITEM_QR_CODE_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
        Idb.setTransactionSuccessful();
        Idb.endTransaction();

    }


    public void addItemInfoExp(ItemInfoExp itemInfoExp) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_CODE1, itemInfoExp.getItemCode());
        values.put(ITEM_NAME1, itemInfoExp.getItemName());
        values.put(ITEM_QTY1,  convertToEnglish(""+itemInfoExp.getItemQty()));
        values.put(EXP_DATE1,  convertToEnglish(itemInfoExp.getExpDate()));
        values.put(BATCH_NO1, itemInfoExp.getBatchNo());
        values.put(ROW_INDEX1, itemInfoExp.getRowIndex());
        values.put(AVL_QTY1, itemInfoExp.getAVLQTY());
        values.put(COST_PRC1, itemInfoExp.getCostPrc());
        values.put(SALE_PRC1, itemInfoExp.getSalePrc());
        values.put(RECEIPT_NO1, itemInfoExp.getReceiptNo());
        values.put(SERIAL_NO1, itemInfoExp.getSerialNo());

        Idb.insert(ITEM_INFO_EXP, null, values);
        Idb.close();
    }
    public void addItemInfoReceipt(ItemInfoExp itemInfo) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_CODE2, itemInfo.getItemCode());
        values.put(ITEM_NAME2, itemInfo.getItemName());
        values.put(ITEM_QTY2, itemInfo.getItemQty());
        values.put(EXP_DATE2, itemInfo.getExpDate());
        values.put(BATCH_NO2, itemInfo.getBatchNo());
        values.put(ROW_INDEX2, itemInfo.getRowIndex());
        values.put(AVL_QTY2, itemInfo.getAVLQTY());
        values.put(COST_PRC2, itemInfo.getCostPrc());
        values.put(SALE_PRC2, itemInfo.getSalePrc());
        values.put(RECEIPT_NO2, itemInfo.getReceiptNo());
        values.put(SERIAL_NO2, itemInfo.getSerialNo());

        Idb.insert(ITEM_INFO_EXP_REC, null, values);
        Idb.close();
    }

    public void addAssetsItem(AssestItem assestItem) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ASSESST_CODE,assestItem.getAssesstCode());
        values.put(ASSESST_NAME, assestItem.getAssesstName());
        values.put(ASSESST_NO, assestItem.getAssesstNo());
        values.put(ASSESST_TYPE, assestItem.getAssesstType());
        values.put(ASSESST_MAINMNG, assestItem.getAssesstMangment());
        values.put(ASSESST_DEPARTMENT, assestItem.getAssesstDEPARTMENT());
        values.put(ASSESST_SECTION, assestItem.getAssesstSECTION());
        values.put(ASSESST_AREANAME, assestItem.getAssesstAREANAME());
        values.put(ASSESST_BARCODE, assestItem.getAssesstBarcode());

        Idb.insert(ASSEST_TABLE, null, values);
        Idb.close();
    }


    public void addAssetsItemInfo(AssestItem assestItem) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ASSESST_CODE_INFO,assestItem.getAssesstCode());
        values.put(ASSESST_NAME_INFO, assestItem.getAssesstName());
        values.put(ASSESST_NO_INFO, assestItem.getAssesstNo());
        values.put(ASSESST_TYPE_INFO, assestItem.getAssesstType());
        values.put(ASSESST_MAINMNG_INFO, assestItem.getAssesstMangment());
        values.put(ASSESST_DEPARTMENT_INFO, assestItem.getAssesstDEPARTMENT());
        values.put(ASSESST_SECTION_INFO, assestItem.getAssesstSECTION());
        values.put(ASSESST_AREANAME_INFO, assestItem.getAssesstAREANAME());
        values.put(ASSESST_QTY_INFO, assestItem.getAssesstQty());
        values.put(ASSESST_DATE_INFO, assestItem.getAssesstDate());
        values.put(ASSESST_ISEXPORT_INFO, assestItem.getIsExport());
        values.put(ASSESST_BARCODE_INFO, assestItem.getAssesstBarcode());
        values.put(ASSESST_SERIAL_INFO, assestItem.getSerial());


        Idb.insert(ASSEST_TABLE_INFO, null, values);
        Idb.close();
    }

    public void addAssetsItemInfoBackup(AssestItem assestItem) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ASSESST_CODE_INFO_BACKUP,assestItem.getAssesstCode());
        values.put(ASSESST_NAME_INFO_BACKUP, assestItem.getAssesstName());
        values.put(ASSESST_NO_INFO_BACKUP, assestItem.getAssesstNo());
        values.put(ASSESST_TYPE_INFO_BACKUP, assestItem.getAssesstType());
        values.put(ASSESST_MAINMNG_INFO_BACKUP, assestItem.getAssesstMangment());
        values.put(ASSESST_DEPARTMENT_INFO_BACKUP, assestItem.getAssesstDEPARTMENT());
        values.put(ASSESST_SECTION_INFO_BACKUP, assestItem.getAssesstSECTION());
        values.put(ASSESST_AREANAME_INFO_BACKUP, assestItem.getAssesstAREANAME());
        values.put(ASSESST_QTY_INFO_BACKUP, assestItem.getAssesstQty());
        values.put(ASSESST_DATE_INFO_BACKUP, assestItem.getAssesstDate());
        values.put(ASSESST_ISEXPORT_INFO_BACKUP, assestItem.getIsExport());
        values.put(ASSESST_BARCODE_INFO_BACKUP, assestItem.getAssesstBarcode());
        values.put(ASSESST_SERIAL_INFO_BACKUP, assestItem.getSerial());


        Idb.insert(ASSEST_TABLE_INFO_BACKUP, null, values);
        Idb.close();
    }
    public void addAssetsItemList(List<AssestItem> assestItemList) {

        SQLiteDatabase Idb = this.getReadableDatabase();
        Idb.beginTransaction();

        for (int i = 0; i < assestItemList.size(); i++) {
            ContentValues values = new ContentValues();


            values.put(ASSESST_CODE, assestItemList.get(i).getAssesstCode());
            values.put(ASSESST_NAME, assestItemList.get(i).getAssesstName());
            values.put(ASSESST_NO, assestItemList.get(i).getAssesstNo());
            values.put(ASSESST_TYPE, assestItemList.get(i).getAssesstType());
            values.put(ASSESST_MAINMNG, assestItemList.get(i).getAssesstMangment());
            values.put(ASSESST_DEPARTMENT, assestItemList.get(i).getAssesstDEPARTMENT());
            values.put(ASSESST_SECTION, assestItemList.get(i).getAssesstSECTION());
            values.put(ASSESST_AREANAME, assestItemList.get(i).getAssesstAREANAME());
            values.put(ASSESST_BARCODE, assestItemList.get(i).getAssesstBarcode());

//            Idb.insert(ITEM_SWITCH, null, values);
            Idb.insertWithOnConflict(ASSEST_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
        Idb.setTransactionSuccessful();
        Idb.endTransaction();

    }

    public ArrayList<ItemInfo> getAllItemInfo_Report(String loc) {
        ArrayList<ItemInfo> itemInfos = new ArrayList<>();
        String selectQuery="";
        if(loc.equals("All")) {
            selectQuery = "SELECT  * FROM " + ITEMS_INFO ;
        }else {
            selectQuery = "SELECT  * FROM " + ITEMS_INFO + " where ITEM_LOCATION='" + loc+"'";

        }
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        double sum=0;
        if (cursor.moveToFirst()) {
            do {
                ItemInfo item = new ItemInfo();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setItemQty(cursor.getFloat(2));
                item.setRowIndex(cursor.getFloat(3));
                item.setItemLocation(cursor.getString(4));
                item.setSerialNo(cursor.getInt(5));
                item.setExpDate(cursor.getString(6));
                item.setSalePrice(cursor.getFloat(7));
                item.setTrnDate(cursor.getString(8));
                item.setIsExport(cursor.getString(9));
                item.setLocation(cursor.getString(10));
                item.setQRCode(cursor.getString(11));
                item.setLotNo(cursor.getString(12));
sum+=cursor.getFloat(2);
                item.setSummation(sum);
                itemInfos.add(item);
            } while (cursor.moveToNext());
        }
        return itemInfos;
    }


    public ArrayList<ItemInfo> getAllItemInfo() {
        ArrayList<ItemInfo> itemInfos = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEMS_INFO;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemInfo item = new ItemInfo();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setItemQty(cursor.getFloat(2));
                item.setRowIndex(cursor.getFloat(3));
                item.setItemLocation(cursor.getString(4));
                item.setSerialNo(cursor.getInt(5));
                item.setExpDate(cursor.getString(6));
                item.setSalePrice(cursor.getFloat(7));
                item.setTrnDate(cursor.getString(8));
                item.setIsExport(cursor.getString(9));
                item.setLocation(cursor.getString(10));
                item.setQRCode(cursor.getString(11));
                item.setLotNo(cursor.getString(12));

                itemInfos.add(item);
            } while (cursor.moveToNext());
        }
        return itemInfos;
    }

    public ArrayList<String> getAllCurrency() {
        ArrayList<String> currencyList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + CURRENCY_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                currencyList.add(cursor.getString(0));


            } while (cursor.moveToNext());
        }
        return currencyList;
    }

    public ArrayList<String> getAllLocation() {
        ArrayList<String> currencyList = new ArrayList<>();

        String selectQuery = "SELECT  ITEM_LOCATION FROM " + ITEMS_INFO +" Group by ITEM_LOCATION ";


        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                currencyList.add(cursor.getString(0));


            } while (cursor.moveToNext());
        }
        return currencyList;
    }

    public ArrayList<ItemInfo> getAllItemInfoBackUp() {
        ArrayList<ItemInfo> itemInfos = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEMS_INFO_BACKUP;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemInfo item = new ItemInfo();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setItemQty(cursor.getFloat(2));
                item.setRowIndex(cursor.getFloat(3));
                item.setItemLocation(cursor.getString(4));
                item.setSerialNo(cursor.getInt(5));
                item.setExpDate(cursor.getString(6));
                item.setSalePrice(cursor.getFloat(7));
                item.setTrnDate(cursor.getString(8));
                item.setIsExport(cursor.getString(9));
                item.setLocation(cursor.getString(10));
                item.setQRCode(cursor.getString(11));
                item.setLotNo(cursor.getString(12));
                item.setIsDelete(cursor.getString(13));

                itemInfos.add(item);
            } while (cursor.moveToNext());
        }
        return itemInfos;
    }

    public ArrayList<ItemInfo> getAllItemInfoSum(String loc) {
        ArrayList<ItemInfo> itemInfos = new ArrayList<>();
        String selectQuery="";
        if(loc.equals("All")){
             selectQuery = "SELECT ITEM_CODE, ITEM_NAME , SALES_PRICE , SUM(ITEM_QTY) ITEM_QTY FROM  ITEMS_INFO  GROUP BY   ITEM_CODE ";

        }else {
             selectQuery = "SELECT ITEM_CODE, ITEM_NAME , SALES_PRICE , SUM(ITEM_QTY) ITEM_QTY FROM  ITEMS_INFO where ITEM_LOCATION= '"+loc+"' GROUP BY   ITEM_CODE ";

        }
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        double sum=0;

        if (cursor.moveToFirst()) {
            do {
                ItemInfo item = new ItemInfo();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setSalePrice(cursor.getFloat(2));
                item.setItemQty(cursor.getFloat(3));
                sum+=item.getItemQty();

                item.setSummation(sum);

                itemInfos.add(item);
            } while (cursor.moveToNext());
        }

        return itemInfos;
    }


    public ArrayList<ItemCard> getAllItemCard() {
        ArrayList<ItemCard> itemCards = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEM_CARD;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemCard item = new ItemCard();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setCostPrc(cursor.getString(2));
                item.setSalePrc(cursor.getString(3));
                item.setAVLQty(cursor.getString(4));
                item.setFDPRC(cursor.getString(5));
                item.setBranchId(cursor.getString(6));
                item.setBranchName(cursor.getString(7));
                item.setDepartmentId(cursor.getString(8));
                item.setDepartmentName(cursor.getString(9));
                item.setItemG(cursor.getString(10));
                item.setItemK(cursor.getString(11));
                item.setItemL(cursor.getString(12));
                item.setItemDiv(cursor.getString(13));
                item.setItemGs(cursor.getString(14));
                item.setOrgPrice(cursor.getString(15));
                item.setInDate(cursor.getString(16));
                item.setIsExport(cursor.getString(17));
                item.setIsNew(cursor.getString(18));
                item.setItemM(cursor.getString(19));

                itemCards.add(item);
            } while (cursor.moveToNext());
        }
        return itemCards;
    }


    public ArrayList<ItemCard> getAllItemCardNew() {
        ArrayList<ItemCard> itemCards = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEM_CARD +" where IS_NEW ='1'";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemCard item = new ItemCard();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setCostPrc(cursor.getString(2));
                item.setSalePrc(cursor.getString(3));
                item.setAVLQty(cursor.getString(4));
                item.setFDPRC(cursor.getString(5));
                item.setBranchId(cursor.getString(6));
                item.setBranchName(cursor.getString(7));
                item.setDepartmentId(cursor.getString(8));
                item.setDepartmentName(cursor.getString(9));
                item.setItemG(cursor.getString(10));
                item.setItemK(cursor.getString(11));
                item.setItemL(cursor.getString(12));
                item.setItemDiv(cursor.getString(13));
                item.setItemGs(cursor.getString(14));
                item.setOrgPrice(cursor.getString(15));
                item.setInDate(cursor.getString(16));
                item.setIsExport(cursor.getString(17));
                item.setIsNew(cursor.getString(18));
                item.setItemM(cursor.getString(19));

                itemCards.add(item);
            } while (cursor.moveToNext());
        }
        return itemCards;
    }

    public ItemCard getItemCardByBarCode(String ITEM_CODE) {
       ItemCard itemCards = new ItemCard();

        String selectQuery = "SELECT  * FROM " + ITEM_CARD+ " where ITEM_CODE = '"+ITEM_CODE+"'";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                itemCards.setItemCode(cursor.getString(0));
                itemCards.setItemName(cursor.getString(1));
                itemCards.setCostPrc(cursor.getString(2));
                itemCards.setSalePrc(cursor.getString(3));
                itemCards.setAVLQty(cursor.getString(4));
                itemCards.setFDPRC(cursor.getString(5));
                itemCards.setBranchId(cursor.getString(6));
                itemCards.setBranchName(cursor.getString(7));
                itemCards.setDepartmentId(cursor.getString(8));
                itemCards.setDepartmentName(cursor.getString(9));
                itemCards.setItemG(cursor.getString(10));
                itemCards.setItemK(cursor.getString(11));
                itemCards.setItemL(cursor.getString(12));
                itemCards.setItemDiv(cursor.getString(13));
                itemCards.setItemGs(cursor.getString(14));
                itemCards.setOrgPrice(cursor.getString(15));
                itemCards.setIsExport(cursor.getString(17));
                itemCards.setIsNew(cursor.getString(18));
                itemCards.setItemM(cursor.getString(19));


            } while (cursor.moveToNext());
        }
        return itemCards;
    }


    public ArrayList<ItemInfoExp> getAllItemInfoExp() {
        ArrayList<ItemInfoExp> itemInfoExps = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEM_INFO_EXP;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemInfoExp item = new ItemInfoExp();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setItemQty(cursor.getFloat(2));
                item.setExpDate(cursor.getString(3));
                item.setBatchNo(cursor.getString(4));
                item.setRowIndex(cursor.getFloat(5));
                item.setAVLQTY(cursor.getString(6));
                item.setCostPrc(cursor.getString(7));
                item.setSalePrc(cursor.getString(8));
                item.setReceiptNo(cursor.getString(9));
                item.setSerialNo(cursor.getInt(10));


                itemInfoExps.add(item);
            } while (cursor.moveToNext());
        }
        return itemInfoExps;
    }


    public ArrayList<ItemInfoExp> getAllItemInfoExpRec() {
        ArrayList<ItemInfoExp> itemInfoExpRec = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEM_INFO_EXP_REC;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemInfoExp item = new ItemInfoExp();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setItemQty(cursor.getFloat(2));
                item.setExpDate(cursor.getString(3));
                item.setBatchNo(cursor.getString(4));
                item.setRowIndex(cursor.getFloat(5));
                item.setAVLQTY(cursor.getString(6));
                item.setCostPrc(cursor.getString(7));
                item.setSalePrc(cursor.getString(8));
                item.setReceiptNo(cursor.getString(9));
                item.setSerialNo(cursor.getInt(10));

                itemInfoExpRec.add(item);
            } while (cursor.moveToNext());
        }
        return itemInfoExpRec;
    }


    public ArrayList<ItemQty> getAllItemQty() {
        ArrayList<ItemQty> itemQty = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEMS_QTY;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemQty item = new ItemQty();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setQty(cursor.getString(2));

                itemQty.add(item);
            } while (cursor.moveToNext());
        }
        return itemQty;
    }

    public ArrayList<ItemSwitch> getAllItemSwitch() {
        ArrayList<ItemSwitch> itemSwitch = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEM_SWITCH;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemSwitch item = new ItemSwitch();

                item.setItemOCode(cursor.getString(0));
                item.setItemNCode(cursor.getString(1));
                item.setItemNameA(cursor.getString(2));
                item.setItemNameE(cursor.getString(3));
                item.setInDate(cursor.getString(4));

                itemSwitch.add(item);
            } while (cursor.moveToNext());
        }
        return itemSwitch;
    }

    public String getItemSwitch(String ITEM_N_CODEs) {
       String itemSwitch = "";

        String selectQuery = "SELECT ITEM_O_CODE FROM " + ITEM_SWITCH +" where ITEM_N_CODE = '"+ITEM_N_CODEs+"'";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
//            do {


                itemSwitch=cursor.getString(0);



//            } while (cursor.moveToNext());
        }
        return itemSwitch;
    }


    public List<String> getItemUnite(String ITEM_BARCODE) {
//        String itemSwitch = "";
        List<String>itemSwitch=new ArrayList<>();
        String selectQuery = "SELECT ITEM_O_CODE , SALE_PRICE , CALC_QTY , ITEM_U  FROM " + ITEM_UNITS +" where ITEM_BARCODE = '"+ITEM_BARCODE+"'";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
//            do {


            Log.e("SalesPri",""+cursor.getString(1));
            Log.e("SalesPri",""+cursor.getString(0));
            Log.e("SalesPrname",""+cursor.getString(3));
            itemSwitch.add(cursor.getString(0));
            itemSwitch.add(cursor.getString(1));
            itemSwitch.add(cursor.getString(2));
            itemSwitch.add(cursor.getString(3));


//            } while (cursor.moveToNext());
        }
        return itemSwitch;
    }

    public List<AssestItem> getItemAssets(String ASSESST_BARCODE) {

        ArrayList<AssestItem> assestItems = new ArrayList<>();
        String selectQuery = "SELECT *  FROM " + ASSEST_TABLE +" where ASSESST_CODE = '"+ASSESST_BARCODE+"'";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AssestItem item = new AssestItem();

                item.setAssesstCode(cursor.getString(0));
                item.setAssesstName(cursor.getString(1));
                item.setAssesstNo(cursor.getString(2));
                item.setAssesstType(cursor.getString(3));

                item.setAssesstMangment(cursor.getString(4));
                item.setAssesstDEPARTMENT(cursor.getString(5));
                item.setAssesstSECTION(cursor.getString(6));
                item.setAssesstAREANAME(cursor.getString(7));

                assestItems.add(item);
            } while (cursor.moveToNext());
        }
        return assestItems;
    }

    public ArrayList<ItemUnit> getAllItemUnit() {
        ArrayList<ItemUnit> itemUnit = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEM_UNITS;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemUnit item = new ItemUnit();

                item.setItemOCode(cursor.getString(0));
                item.setItemBarcode(cursor.getString(1));
                item.setSalePrice(cursor.getFloat(2));
                item.setItemU(cursor.getString(3));
                item.setUQty(cursor.getInt(4));
                item.setUSerial(cursor.getInt(5));
                item.setCalcQty(cursor.getInt(6));
                item.setWholeSalePrc(cursor.getFloat(7));
                item.setPurchasePrc(cursor.getFloat(8));
                item.setPclAss1(cursor.getFloat(9));
                item.setPclAss2(cursor.getFloat(10));
                item.setPclAss3(cursor.getFloat(11));
                item.setInDate(cursor.getString(12));
                item.setUnitName(cursor.getString(13));

                itemUnit.add(item);

            } while (cursor.moveToNext());
        }
        return itemUnit;
    }


    public ArrayList<Order> getAllOrder() {
        ArrayList<Order> orders = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ORDERS;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order item = new Order();

                item.setStockNo(cursor.getInt(0));
                item.setLoadDate(cursor.getString(1));
                item.setLoadQty(cursor.getFloat(2));
                item.setNetQty(cursor.getFloat(3));
                item.setItemBarcode(cursor.getString(4));
                item.setItemCode(cursor.getString(5));

                orders.add(item);

            } while (cursor.moveToNext());
        }
        return orders;
    }


    public ArrayList<Password> getAllPassword() {
        ArrayList<Password> passwords = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + PASSWORD;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Password item = new Password();

                item.setPasswords(cursor.getString(0));

                passwords.add(item);

            } while (cursor.moveToNext());
        }
        return passwords;
    }

    public ArrayList<MainSetting> getAllMainSetting() {
        ArrayList<MainSetting> passwords = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + MAIN_SETTING_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MainSetting item = new MainSetting();

                item.setIP(cursor.getString(0));
                item.setStorNo(cursor.getString(1));
                item.setIsAssest(cursor.getString(2));
                item.setIsQr(cursor.getString(3));
                item.setOnlinePrice(cursor.getString(4));
                item.setCompanyNo(cursor.getString(5));
                item.setPrinterType(cursor.getString(6));
                item.setCurrencyType(cursor.getString(7));
                item.setCoName(cursor.getInt(8));

                item.setNumberType(cursor.getString(9));
                item.setRotate(cursor.getString(10));
                try {
                    item.setDataBaseNo(cursor.getInt(11));
                }catch (Exception e){}
                item.setReSize(cursor.getInt(13));
                item.setWidth(cursor.getInt(14));
                item.setHeight(cursor.getInt(15));
                item.setIsMinus(cursor.getInt(16));
                item.setONlINEshlf(cursor.getString(17));
                item.setIsSaveAfterUp(cursor.getString(18));
                item.setCompanyName(cursor.getString(19));
                passwords.add(item);

            } while (cursor.moveToNext());
        }
        return passwords;
    }

    public ArrayList<SewooSetting> getAllSewooSetting() {
        ArrayList<SewooSetting> sewooSettings = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + SEWOO_SETTING;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SewooSetting item = new SewooSetting();

                item.setComPort(cursor.getString(0));
                item.setBarcodeX(cursor.getInt(1));
                item.setBarcodeY(cursor.getInt(2));
                item.setItemX(cursor.getInt(3));
                item.setItemY(cursor.getInt(4));
                item.setItemFontType(cursor.getInt(5));

                item.setItemFontSize(cursor.getInt(6));
                item.setPrcX(cursor.getInt(7));
                item.setPrcY(cursor.getInt(8));
                item.setPrcFontType(cursor.getInt(9));
                item.setPrcFontSize(cursor.getInt(10));
                item.setBarcodeType(cursor.getInt(11));

                item.setBarcodeBn(cursor.getInt(12));
                item.setCompName(cursor.getString(13));
                item.setCompNMX(cursor.getString(14));
                item.setCompNMY(cursor.getString(15));
                item.setDevID(cursor.getString(16));

                sewooSettings.add(item);

            } while (cursor.moveToNext());
        }
        return sewooSettings;
    }


    public ArrayList<Stk> getAllStk() {
        ArrayList<Stk> stks = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + STK;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Stk item = new Stk();

                item.setStkNo(cursor.getString(0));
                item.setStkName(cursor.getString(1));

                stks.add(item);

            } while (cursor.moveToNext());
        }
        return stks;
    }

    public ArrayList<UnitName> getAllUnite() {
        ArrayList<UnitName> unitNames = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ITEM_UNIT;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UnitName item = new UnitName();

                item.setItemUnitN(cursor.getString(0));

                unitNames.add(item);

            } while (cursor.moveToNext());
        }
        return unitNames;
    }

    public String getActivate() {
      String activate="";
        String selectQuery = "SELECT  * FROM " + ACTIVATE_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                activate=cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return activate;
    }

    //select * from ITEM_QR_CODE_TABLE where QR_CODE LIKE '0106281086002827%'


    public ArrayList<ItemQR> getAllQRItem(String Qrcode,String STR_NO) {
        ArrayList<ItemQR> QRList = new ArrayList<>();//STR_NO ='"+STR_NO+"' and

        String selectQuery = "SELECT  * FROM " + ITEM_QR_CODE_TABLE +" WHERE  QR_CODE LIKE '"+Qrcode+"%'";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemQR item = new ItemQR();

                item.setStoreNo(cursor.getString(0));
                item.setItemCode(cursor.getString(1));
                item.setItemNmae(cursor.getString(2));
                item.setSalesPrice(cursor.getString(3));
                item.setQrCode(cursor.getString(4));
                item.setLotNo(cursor.getString(5));

                QRList.add(item);
            } while (cursor.moveToNext());
        }
        return QRList;
    }

    public ArrayList<ItemQR> getAllQRItemEqQr(String Qrcode,String STR_NO) {
        ArrayList<ItemQR> QRList = new ArrayList<>();//STR_NO ='"+STR_NO+"' and

        String selectQuery = "SELECT  * FROM " + ITEM_QR_CODE_TABLE +" WHERE  QR_CODE = '"+Qrcode+"'";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemQR item = new ItemQR();

                item.setStoreNo(cursor.getString(0));
                item.setItemCode(cursor.getString(1));
                item.setItemNmae(cursor.getString(2));
                item.setSalesPrice(cursor.getString(3));
                item.setQrCode(cursor.getString(4));
                item.setLotNo(cursor.getString(5));

                QRList.add(item);
            } while (cursor.moveToNext());
        }
        return QRList;
    }

    public String getSalesQRItem(String ITEM_CODE,String STR_NO) {
       String QRList = "";//STR_NO ='"+STR_NO+"' and

        String selectQuery = "SELECT  PRICE FROM " + ITEM_QR_CODE_TABLE +" WHERE  ITEM_CODE = '"+ITEM_CODE+"'";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                QRList= cursor.getString(0);


            } while (cursor.moveToNext());
        }
        return QRList;
    }

    public ArrayList<AssestItem> getAllAssesstItem() {
        ArrayList<AssestItem> assestItems = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ASSEST_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AssestItem item = new AssestItem();

                item.setAssesstCode(cursor.getString(0));
                item.setAssesstName(cursor.getString(1));
                item.setAssesstNo(cursor.getString(2));
                item.setAssesstType(cursor.getString(3));

                item.setAssesstMangment(cursor.getString(4));
                item.setAssesstDEPARTMENT(cursor.getString(5));
                item.setAssesstSECTION(cursor.getString(6));
                item.setAssesstAREANAME(cursor.getString(7));
                item.setAssesstBarcode(cursor.getString(8));

                assestItems.add(item);
            } while (cursor.moveToNext());
        }
        return assestItems;
    }

    public ArrayList<AssestItem> getAllAssesstItemInfo() {
        ArrayList<AssestItem> assestItems = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ASSEST_TABLE_INFO;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AssestItem item = new AssestItem();

                item.setAssesstCode(cursor.getString(0));
                item.setAssesstName(cursor.getString(1));
                item.setAssesstNo(cursor.getString(2));
                item.setAssesstType(cursor.getString(3));

                item.setAssesstMangment(cursor.getString(4));
                item.setAssesstDEPARTMENT(cursor.getString(5));
                item.setAssesstSECTION(cursor.getString(6));
                item.setAssesstAREANAME(cursor.getString(7));

                item.setAssesstQty(cursor.getString(8));
                item.setAssesstDate(cursor.getString(9));
                item.setIsExport(cursor.getString(10));
                item.setAssesstBarcode(cursor.getString(11));
                item.setSerial(cursor.getString(12));


                assestItems.add(item);
            } while (cursor.moveToNext());
        }
        return assestItems;
    }
    public ArrayList<AssestItem> getAllAssesstItemInfoBackup() {
        ArrayList<AssestItem> assestItems = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ASSEST_TABLE_INFO_BACKUP;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AssestItem item = new AssestItem();

                item.setAssesstCode(cursor.getString(0));
                item.setAssesstName(cursor.getString(1));
                item.setAssesstNo(cursor.getString(2));
                item.setAssesstType(cursor.getString(3));

                item.setAssesstMangment(cursor.getString(4));
                item.setAssesstDEPARTMENT(cursor.getString(5));
                item.setAssesstSECTION(cursor.getString(6));
                item.setAssesstAREANAME(cursor.getString(7));

                item.setAssesstQty(cursor.getString(8));
                item.setAssesstDate(cursor.getString(9));
                item.setIsExport(cursor.getString(10));
                item.setAssesstBarcode(cursor.getString(11));
                item.setSerial(cursor.getString(12));


                assestItems.add(item);
            } while (cursor.moveToNext());
        }
        return assestItems;
    }
    public ArrayList<ItemCard> getAllItemCardNotExport() {
        ArrayList<ItemCard> itemCards = new ArrayList<>();

//        SELECT  * FROM  ITEM_CARD  where IS_EXPORT = '0' and  IS_NEW = '1' and (IN_DATE  is NULL or  IN_DATE ='')

        String selectQuery = "SELECT  * FROM " + ITEM_CARD + " where IS_EXPORT = '0' and  IS_NEW = '1' and (IN_DATE  is NULL or  IN_DATE ='') ";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemCard item = new ItemCard();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setCostPrc(cursor.getString(2));
                item.setSalePrc(cursor.getString(3));
                item.setAVLQty(cursor.getString(4));
                item.setFDPRC(cursor.getString(5));
                item.setBranchId(cursor.getString(6));
                item.setBranchName(cursor.getString(7));
                item.setDepartmentId(cursor.getString(8));
                item.setDepartmentName(cursor.getString(9));
                item.setItemG(cursor.getString(10));
                item.setItemK(cursor.getString(11));
                item.setItemL(cursor.getString(12));
                item.setItemDiv(cursor.getString(13));
                item.setItemGs(cursor.getString(14));
                item.setOrgPrice(cursor.getString(15));
                item.setInDate(cursor.getString(16));
                item.setIsExport(cursor.getString(17));
                item.setIsNew(cursor.getString(18));
                item.setItemM(cursor.getString(19));


                itemCards.add(item);
            } while (cursor.moveToNext());
        }
        return itemCards;
    }


    public ArrayList<String> getAllAssesstMang() {
        ArrayList<String> MangList = new ArrayList<>();

        String selectQuery = "SELECT  DISTINCT ASSESST_MAINMNG FROM " + ASSEST_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(0)!=null&&!cursor.getString(0).equals("")) {
                    MangList.add(cursor.getString(0));
                    Log.e("Manage", "" + cursor.getString(0));
                }

            } while (cursor.moveToNext());
        }
        return MangList;
    }

    public ArrayList<String> getAllAssesstDepart() {
        ArrayList<String> DepartList = new ArrayList<>();

        String selectQuery = "SELECT  DISTINCT ASSESST_DEPARTMENT FROM " + ASSEST_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(0)!=null&&!cursor.getString(0).equals("")) {
                    DepartList.add(cursor.getString(0));
                    Log.e("DEPARTMENT", "" + cursor.getString(0));
                }

            } while (cursor.moveToNext());
        }
        return DepartList;
    }

    public ArrayList<String> getAllAssesstSec() {
        ArrayList<String> DepartList = new ArrayList<>();

        String selectQuery = "SELECT  DISTINCT ASSESST_SECTION FROM " + ASSEST_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                if(cursor.getString(0)!=null&&!cursor.getString(0).equals("")) {
                    DepartList.add(cursor.getString(0));
                    Log.e("SECTION", "" + cursor.getString(0));
                }

            } while (cursor.moveToNext());
        }
        return DepartList;
    }

    public ArrayList<String> getAllAssesstArea() {
        ArrayList<String> DepartList = new ArrayList<>();

        String selectQuery = "SELECT  DISTINCT ASSESST_AREANAME FROM " + ASSEST_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(0)!=null&&!cursor.getString(0).equals("")) {
                    DepartList.add(cursor.getString(0));
                    Log.e("AREANAME", "" + cursor.getString(0));
                }

            } while (cursor.moveToNext());
        }
        return DepartList;
    }



    public String getVhf() {

        String vhf="";
        String selectQuery = " SELECT VHF_SERIAL FROM " + TRANSF_VHF_SER;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                vhf=  cursor.getString(0);



            } while (cursor.moveToNext());
        }
        return vhf;
    }

    public String getStkName(String STORNo) {

        String stkName="";
        String selectQuery = "SELECT  STK_NAME FROM " + STK +" where STK_NO = "+STORNo;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                stkName= cursor.getString(0);



            } while (cursor.moveToNext());
        }
        return stkName;
    }


    public ArrayList<TblSetting> getAllTblSetting() {
        ArrayList<TblSetting> tblSetting = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TBL_SETTING;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TblSetting item = new TblSetting();

                item.setSettingName(cursor.getString(0));
                item.setSettingIntVal(cursor.getInt(1));
                item.setSettingStrVal(cursor.getString(2));

                tblSetting.add(item);

            } while (cursor.moveToNext());
        }
        return tblSetting;
    }


    public ArrayList<TransferItemsInfo> getAllTransferItemInfo() {
        ArrayList<TransferItemsInfo> transferItems = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TRANSFER_ITEMS_INFO;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TransferItemsInfo item = new TransferItemsInfo();

                item.setItemCode(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setItemQty(cursor.getFloat(2));
                item.setFromStr(cursor.getString(3));
                item.setToStr(cursor.getString(4));
                item.setRowIndex(cursor.getFloat(5));
                item.setVhfNo(cursor.getInt(6));
                item.setIsExport(cursor.getString(7));

                transferItems.add(item);

            } while (cursor.moveToNext());
        }
        return transferItems;
    }

    public ArrayList<TransferVhfSerial> getAllTransferVhfSer() {
        ArrayList<TransferVhfSerial> transferItems = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TRANSF_VHF_SER;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TransferVhfSerial item = new TransferVhfSerial();

                item.setVhfSerial(cursor.getInt(0));

                transferItems.add(item);

            } while (cursor.moveToNext());
        }
        return transferItems;
    }

    public String getTotal(String ItemCode,String TableName)
    {

        String Total = "0";//NVL(SUM(Price), 0)
        String selectQuery="SELECT COALESCE(sum(ITEM_QTY),0)  FROM  " +TableName +" WHERE ITEM_CODE = '" + ItemCode + "'";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Total = cursor.getString(0);

            }while (cursor.moveToNext());
            Log.e("Total ..","...."+Total);
        }


        return Total;
    }



    public void updateExpTable(String TableName ,String newValue,String serialNo,String itemCode) {
        Idb = this.getWritableDatabase();
String filter=SERIAL_NO4 + " = '" + serialNo + "' and " + ITEM_CODE4 + " = '" + itemCode + "'";
        ContentValues args = new ContentValues();
        args.put(ITEM_QTY4, newValue);

        Idb.update(TableName, args, filter, null);


    }

    public void updateExpTableAss(String TableName ,String newValue,String serialNo,String itemCode) {
        Idb = this.getWritableDatabase();
        String filter=ASSESST_SERIAL_INFO + " = '" + serialNo + "' and " + ASSESST_CODE_INFO + " = '" + itemCode + "'";
        ContentValues args = new ContentValues();
        args.put(ASSESST_QTY_INFO, newValue);

        Idb.update(TableName, args, filter, null);


    }
    public void updateTransferTable(String TableName ,String newValue,String serialNo,String itemCode) {
        Idb = this.getWritableDatabase();
        String filter=ROW_INDEX8 + " = '" + serialNo + "' and " + ITEM_CODE8 + " = '" + itemCode + "'";
        ContentValues args = new ContentValues();
        args.put(ITEM_QTY8, newValue);

        Idb.update(TableName, args, filter, null);


    }

    public int  getMax(){
        int maxNo=0;
        //select IFNULL(Max(VHF_NO),0)  FROM TRANSFER_ITEMS_INFO

        String selectQuery = "select IFNULL(Max(VHF_NO),1)  FROM TRANSFER_ITEMS_INFO";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                maxNo=cursor.getInt(0);


            } while (cursor.moveToNext());
        }
        return maxNo;

    }
    public int  getMaxSerialAss(){
        int maxNo=0;
        //select IFNULL(Max(VHF_NO),0)  FROM TRANSFER_ITEMS_INFO


        String selectQuery = "select IFNULL(Max(ASSESST_SERIAL_INFO),1)  FROM ASSEST_TABLE_INFO";
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                maxNo=cursor.getInt(0);


            } while (cursor.moveToNext());
        }
        return maxNo;

    }
    public String  getMaxInDate(String tableName){
        String maxNo="";
        //select IFNULL(Max(VHF_NO),0)  FROM TRANSFER_ITEMS_INFO

//        String selectQuery = "select IFNULL(Max(IN_DATE),-1)  FROM  "+tableName;
        String selectQuery = "SELECT IFNULL (MAX(datetime(substr(IN_DATE, 7, 4) || '-' || substr(IN_DATE, 4, 2) || '-' || substr(IN_DATE, 1, 2))),'2020-03-05') FROM "+tableName;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                maxNo=cursor.getString(0);


            } while (cursor.moveToNext());
        }
        return maxNo;

    }




    public void updatePasswordTable(String newValue,String serialNo,String oldPass) {
        Idb = this.getWritableDatabase();
        //SERIAL_NO4 + " = '" + serialNo + "' and " +
        String filter= PASSWORD7 + " = '" + oldPass + "'";
        ContentValues args = new ContentValues();
        args.put(PASSWORD7, newValue);

        Idb.update(PASSWORD, args, filter, null);


    }


    public void updateVhfNo(String newValue) {
        Idb = this.getWritableDatabase();
        //SERIAL_NO4 + " = '" + serialNo + "' and " +

        ContentValues args = new ContentValues();
        args.put(VHF_SERIAL, newValue);

        Idb.update(TRANSF_VHF_SER, args, null, null);


    }



    public void updateIsExport() {
        Idb = this.getWritableDatabase();
        //SERIAL_NO4 + " = '" + serialNo + "' and " +

        ContentValues args = new ContentValues();
        args.put(IS_EXPORT4, "1");

        Idb.update(ITEMS_INFO, args, null, null);


    }

    public void updateIsExportBackupInfoTable() {
        Idb = this.getWritableDatabase();
        //SERIAL_NO4 + " = '" + serialNo + "' and " +

        ContentValues args = new ContentValues();
        args.put(IS_EXPORT5, "1");

        Idb.update(ITEMS_INFO_BACKUP, args, null, null);


    }

    public void updateIsExportTransfer() {
        Idb = this.getWritableDatabase();
        //SERIAL_NO4 + " = '" + serialNo + "' and " +

        ContentValues args = new ContentValues();
        args.put(ISEXPORT8, "1");

        Idb.update(TRANSFER_ITEMS_INFO, args, null, null);


    }


    public void updateIsExportAssets() {
        Idb = this.getWritableDatabase();
        //SERIAL_NO4 + " = '" + serialNo + "' and " +

        ContentValues args = new ContentValues();
        args.put(ASSESST_ISEXPORT_INFO, "1");

        Idb.update(ASSEST_TABLE_INFO, args, null, null);


    }

    public void updateIsDeleteItemInfoBackup() {
        Idb = this.getWritableDatabase();
        //SERIAL_NO4 + " = '" + serialNo + "' and " +

        ContentValues args = new ContentValues();
        args.put(IS_DELETE5, "1");

        Idb.update(ITEMS_INFO_BACKUP, args, null, null);


    }

    public void updateIsDeleteItemInfoBackupByItem(String itemCode,String serialNo) {
        Idb = this.getWritableDatabase();
        //SERIAL_NO4 + " = '" + serialNo + "' and " +
        String filter= SERIAL_NO5 + " = '" + serialNo + "' and ITEM_CODE = '"+itemCode+"'";
        ContentValues args = new ContentValues();
        args.put(IS_DELETE5, "1");

        Idb.update(ITEMS_INFO_BACKUP, args, filter, null);


    }


    public void updateIsExportItemCard() {
        Idb = this.getWritableDatabase();
        //SERIAL_NO4 + " = '" + serialNo + "' and " +

        ContentValues args = new ContentValues();
        args.put(IS_EXPORT, "1");

        Idb.update(ITEM_CARD, args, null, null);


    }


   public void deleteAllItem(String tableName)
   {
//             Idb.execSQL("DELETE FROM "+tableName); //delete all rows in a table
       SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("delete from " + tableName);
       db.close();
         }

    public void delete(String Itemcode,String ItemName) {
        Idb.execSQL("DELETE FROM "+ITEM_CARD+" where ITEM_CODE = '"+Itemcode +"' and ITEM_NAME = '"+ItemName+"'"); //delete all rows in a table
    }

    public void deleteItemCardByItemCode(String ItemCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ITEM_CARD+" where ITEM_CODE = '"+ItemCode +"'"); //delete item code rows in a table
        db.close();

    }

    public void deleteItemCardSwitchByItemCode(String ItemCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ITEM_SWITCH+" where ITEM_O_CODE = '"+ItemCode +"'"); //delete item code rows in a table
        db.close();

    }

    public void deleteItemCardUnitByItemCode(String ItemCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ITEM_UNITS+" where ITEM_O_CODE = '"+ItemCode +"'"); //delete item code rows in a table
        db.close();

    }

    public void deleteItemCardQRByItemCode(String ItemCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ITEM_QR_CODE_TABLE+" where ITEM_CODE = '"+ItemCode +"'"); //delete item code rows in a table
        db.close();

    }
    public void deleteItemCardByItemCodetest(String ItemCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ITEM_CARD+" where ITEM_CODE = '"+ItemCode +"'"); //delete item code rows in a table
        db.close();

    }

    public void deleteItemSwitchByItemCode(String ITEM_O_CODEs,String ITEM_N_CODEs) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ITEM_SWITCH+" where ITEM_O_CODE = '"+ITEM_O_CODEs +"' and ITEM_N_CODE = '"+ITEM_N_CODEs+"'"); //delete item code rows in a table
        db.close();

    }

    public void deleteItemQRByItemCode(String ITEM_O_CODEs,String ITEM_QR) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ITEM_QR_CODE_TABLE+" where ITEM_CODE = '"+ITEM_O_CODEs +"' and QR_CODE = '"+ITEM_QR+"'");
        db.close();

    }


    public void deleteItemUniteByItemCode(String ITEM_O_CODEs,String ITEM_BARCODE) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ITEM_UNITS+" where ITEM_O_CODE = '"+ITEM_O_CODEs +"' and ITEM_BARCODE = '"+ITEM_BARCODE+"'"); //delete item code rows in a table
        db.close();

    }

    public void deleteItemFromItemInfo(String ItemCode,String SERIALNo) {
        Idb.execSQL("DELETE FROM "+ITEMS_INFO+" where ITEM_CODE = '"+ItemCode +"' and SERIAL_NO = '"+SERIALNo+"'"); //delete all rows in a table
    }

    public void deleteItemFromItemInfoByLocation(String loc) {
        Idb.execSQL("DELETE FROM "+ITEMS_INFO+" where  ITEM_LOCATION= '"+loc+"'"); //delete all rows in a table by location the same
    }

    public void deleteItemFromItemInfoAssest(String ItemCode,String SERIALNo) {
        Idb.execSQL("DELETE FROM "+ASSEST_TABLE_INFO+" where ASSESST_CODE = '"+ItemCode +"' and ASSESST_SERIAL_INFO = '"+SERIALNo+"'"); //delete all rows in a table
    }
    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫","."));
        return newValue;
    }



}
