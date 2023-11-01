package com.example.user54.InventoryApp.Model;

import android.util.Log;

import androidx.room.Ignore;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemInfo  {

    private String itemCode ;
    private String itemName ;
    private float itemQty;
    private float rowIndex;
    private String itemLocation ;
    private int serialNo ;
    private String ExpDate ;
    private float salePrice;
    private String TrnDate;
    private String isExport;
    private String Location;
    private String QRCode;
    private String LotNo;
    @Ignore
    private String IsDelete;
    @Ignore
    private double Summation;

    public ItemInfo() {

    }

    @Ignore
    public ItemInfo(String itemCode, String itemName, float itemQty, float rowIndex, String itemLocation, int serialNo, String expDate, float salePrice, String trnDate, String isExport, String location, String QRCode, String lotNo) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.rowIndex = rowIndex;
        this.itemLocation = itemLocation;
        this.serialNo = serialNo;
        this.ExpDate = expDate;
        this.salePrice = salePrice;
        this.TrnDate = trnDate;
        this.isExport = isExport;
        this.Location = location;
        this.QRCode = QRCode;
        this.LotNo = lotNo;
    }


    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemQty(float itemQty) {
        this.itemQty = itemQty;
    }

    public double getSummation() {
        return Summation;
    }

    public void setSummation(double summation) {
        Summation = summation;
    }

    public void setRowIndex(float rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public void setExpDate(String expDate) {
        ExpDate = expDate;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public float getItemQty() {
        return itemQty;
    }

    public float getRowIndex() {
        return rowIndex;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public String getTrnDate() {
        return TrnDate;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public void setTrnDate(String trnDate) {
        TrnDate = trnDate;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getLotNo() {
        return LotNo;
    }

    public void setLotNo(String lotNo) {
        LotNo = lotNo;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(String isDelete) {
        IsDelete = isDelete;
    }

    //{"JRD":[{"ITEMCODE":"10115","ITEMNAME":"مادة1","STORENO":"1","ITEMQTY":"10","ROWINDEX":"1","EXPIRYDATE":"18/10/2025","TRDATE":"20/02/2020","SALESPRICE":"1.10"},{"ITEMCODE":"10115","ITEMNAME":"مادة1","STORENO":"1","ITEMQTY":"10","ROWINDEX":"1","EXPIRYDATE":"18/10/2025","TRDATE":"20/02/2020","SALESPRICE":"1.11"}]}
    public JSONObject getJSONObject2() { // for server
        JSONObject obj = new JSONObject();
        try {

            obj.put("ITEMCODE", itemCode);
            obj.put("ITEMNAME", itemName);
            obj.put("STORENO", itemLocation);
            obj.put("ITEMQTY", itemQty);
            obj.put("ROWINDEX", rowIndex);
            obj.put("EXPIRYDATE",ExpDate );
            obj.put("TRDATE", TrnDate);
            obj.put("SALESPRICE", salePrice);
            obj.put("LOCATION", Location);
            obj.put("QRCODE", QRCode);
            obj.put("LOTNUMBER", LotNo);


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }


    public JSONObject getJSONObjectBacup() { // for server
        JSONObject obj = new JSONObject();
        try {

            obj.put("ITEMCODE", itemCode);
            obj.put("ITEMNAME", itemName);
            obj.put("STORENO", itemLocation);
            obj.put("ITEMQTY", itemQty);
            obj.put("ROWINDEX", rowIndex);
            obj.put("EXPIRYDATE",ExpDate );
            obj.put("TRDATE", TrnDate);
            obj.put("SALESPRICE", salePrice);
            obj.put("LOCATION", Location);
            obj.put("QRCODE", QRCode);
            obj.put("LOTNUMBER", LotNo);
            obj.put("DELETED", IsDelete);


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
