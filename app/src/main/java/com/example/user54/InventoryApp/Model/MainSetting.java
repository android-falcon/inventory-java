package com.example.user54.InventoryApp.Model;

public class MainSetting {

    private String IP;
    private String StorNo;
    private String isAssest;
    private String isQr;
    private String onlinePrice;
    private String companyNo;
    private String printerType;
    private String currencyType;
    private int CoName;
    private String numberType;
    private String rotate;
    private int reSize;
    private int width;
    private int height;
    private int dataBaseNo;
    private int dataBaseNoRoom;
    private int isMinus;
    private String ONlINEshlf;
    private  String isSaveAfterUp;
    private  String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIsSaveAfterUp() {
        return isSaveAfterUp;
    }

    public void setIsSaveAfterUp(String isSaveAfterUp) {
        this.isSaveAfterUp = isSaveAfterUp;
    }

    public String getONlINEshlf() {
        return ONlINEshlf;
    }

    public void setONlINEshlf(String ONlINEshlf) {
        this.ONlINEshlf = ONlINEshlf;
    }

    public MainSetting() {
    }

    public MainSetting(String IP, String storNo, String isAssest, String isQr) {
        this.IP = IP;
        this.StorNo = storNo;
        this.isAssest = isAssest;
        this.isQr = isQr;
    }

    public MainSetting(String IP, String storNo, String isAssest, String isQr, String onlinePrice, String companyNo,String printerType,String currencyType,int CoName,String numberType,String rotate,int dataBaseNo,int dataBaseNoR,int reSize,int width,int height,int isMinus,String onlinShl,String saveUp,String coNo) {
        this.IP = IP;
        this.StorNo = storNo;
        this.isAssest = isAssest;
        this.isQr = isQr;
        this.onlinePrice = onlinePrice;
        this.companyNo = companyNo;
        this.printerType = printerType;
        this.currencyType=currencyType;
        this.CoName=CoName;
        this.numberType=numberType;
        this.rotate=rotate;
        this.reSize=reSize;
        this.width=width;
        this.height=height;
        this.dataBaseNo=dataBaseNo;
        this.dataBaseNoRoom=dataBaseNoR;
        this.isMinus=isMinus;
        this.ONlINEshlf= onlinShl;
        this.isSaveAfterUp=saveUp;
        this.companyName=coNo;
    }

    public String getPrinterType() {
        return printerType;
    }

    public void setPrinterType(String printerType) {
        this.printerType = printerType;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getStorNo() {
        return StorNo;
    }

    public void setStorNo(String storNo) {
        StorNo = storNo;
    }

    public String getIsAssest() {
        return isAssest;
    }

    public void setIsAssest(String isAssest) {
        this.isAssest = isAssest;
    }

    public String getIsQr() {
        return isQr;
    }

    public void setIsQr(String isQr) {
        this.isQr = isQr;
    }

    public String getOnlinePrice() {
        return onlinePrice;
    }

    public void setOnlinePrice(String onlinePrice) {
        this.onlinePrice = onlinePrice;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public int getCoName() {
        return CoName;
    }

    public void setCoName(int coName) {
        CoName = coName;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }


    public int getDataBaseNo() {
        return dataBaseNo;
    }

    public void setDataBaseNo(int dataBaseNo) {
        this.dataBaseNo = dataBaseNo;
    }

    public int getDataBaseNoRoom() {
        return dataBaseNoRoom;
    }

    public int getReSize() {
        return reSize;
    }

    public void setReSize(int reSize) {
        this.reSize = reSize;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDataBaseNoRoom(int dataBaseNoRoom) {
        this.dataBaseNoRoom = dataBaseNoRoom;
    }

    public int getIsMinus() {
        return isMinus;
    }

    public void setIsMinus(int isMinus) {
        this.isMinus = isMinus;
    }
}
