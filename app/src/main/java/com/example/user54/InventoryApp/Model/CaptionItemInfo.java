package com.example.user54.InventoryApp.Model;

public class CaptionItemInfo {
//{"AccCode":"0","ItemNCode":"7708800188","ItemOCode":"7708800188","ItemNameA":"SUIT WOLL","ItemNameE":"SUIT WOLL","InDate":"18\/04\/2023","F_D":"200","AVLQTY":"1"},
// "AccCode": "",
//        "ItemNCode": "1045400018",
//        "ItemOCode": "1045400018",
//        "ItemNameA": "BLUES",
//        "ItemNameE": "BLUES",
//        "InDate": "08/04/2023",
//        "F_D": "60",
//        "AVLQTY": "1",
//        "ITEMUNIT": "",
//        "ITEMGROUP": "BLUES",
//        "ITEMCOLOR": "BLUE",
//        "ITEMSIZE": "XL",
//        "ITEMMODEL": "10454",
//        "ITEMGS": "",
//        "ITEMDIV": "",
//        "ITEMSUB1": "",
//        "ITEMSUB2": "",
//        "ITEMSUB3": "",
//        "ITEMSUB4": "",
//        "ITEMSUB5": "",
//        "ITEMSUB6": "",
//        "ITEMSUB7": ""

    private  String AccCode;
    private  String ItemNCode;
    private  String ItemOCode;
    private  String ItemNameA;
    private  String ItemNameE;
    private  String InDate;
    private  String F_D;
    private  String AVLQTY;
    private  String ITEMUNIT;
    private  String ITEMGROUP;
    private  String ITEMCOLOR;
    private  String ITEMSIZE;
    private  String ITEMMODEL;
    private  String ITEMGS;
    private  String ITEMDIV;
    private  String ITEMSUB1;
    private  String ITEMSUB2;
    private  String ITEMSUB3;
    private  String ITEMSUB4;
    private  String ITEMSUB5;
    private  String ITEMSUB6;
    private  String ITEMSUB7;



    public CaptionItemInfo(String accCode, String itemNCode, String itemOCode, String itemNameA, String itemNameE, String inDate, String f_D, String AVLQTY, String ITEMUNIT, String ITEMGROUP, String ITEMCOLOR, String ITEMSIZE, String ITEMMODEL, String ITEMGS, String ITEMDIV, String ITEMSUB1, String ITEMSUB2, String ITEMSUB3, String ITEMSUB4, String ITEMSUB5, String ITEMSUB6, String ITEMSUB7) {
        AccCode = accCode;
        ItemNCode = itemNCode;
        ItemOCode = itemOCode;
        ItemNameA = itemNameA;
        ItemNameE = itemNameE;
        InDate = inDate;
        F_D = f_D;
        this.AVLQTY = AVLQTY;
        this.ITEMUNIT = ITEMUNIT;
        this.ITEMGROUP = ITEMGROUP;
        this.ITEMCOLOR = ITEMCOLOR;
        this.ITEMSIZE = ITEMSIZE;
        this.ITEMMODEL = ITEMMODEL;
        this.ITEMGS = ITEMGS;
        this.ITEMDIV = ITEMDIV;
        this.ITEMSUB1 = ITEMSUB1;
        this.ITEMSUB2 = ITEMSUB2;
        this.ITEMSUB3 = ITEMSUB3;
        this.ITEMSUB4 = ITEMSUB4;
        this.ITEMSUB5 = ITEMSUB5;
        this.ITEMSUB6 = ITEMSUB6;
        this.ITEMSUB7 = ITEMSUB7;
    }

    public CaptionItemInfo() {
    }


    public String getAccCode() {
        return AccCode;
    }

    public void setAccCode(String accCode) {
        AccCode = accCode;
    }

    public String getItemNCode() {
        return ItemNCode;
    }

    public void setItemNCode(String itemNCode) {
        ItemNCode = itemNCode;
    }

    public String getItemOCode() {
        return ItemOCode;
    }

    public void setItemOCode(String itemOCode) {
        ItemOCode = itemOCode;
    }

    public String getItemNameA() {
        return ItemNameA;
    }

    public void setItemNameA(String itemNameA) {
        ItemNameA = itemNameA;
    }

    public String getItemNameE() {
        return ItemNameE;
    }

    public void setItemNameE(String itemNameE) {
        ItemNameE = itemNameE;
    }

    public String getInDate() {
        return InDate;
    }

    public void setInDate(String inDate) {
        InDate = inDate;
    }

    public String getF_D() {
        return F_D;
    }

    public void setF_D(String f_D) {
        F_D = f_D;
    }

    public String getAVLQTY() {
        return AVLQTY;
    }

    public void setAVLQTY(String AVLQTY) {
        this.AVLQTY = AVLQTY;
    }


    public String getITEMUNIT() {
        return ITEMUNIT;
    }

    public void setITEMUNIT(String ITEMUNIT) {
        this.ITEMUNIT = ITEMUNIT;
    }

    public String getITEMGROUP() {
        return ITEMGROUP;
    }

    public void setITEMGROUP(String ITEMGROUP) {
        this.ITEMGROUP = ITEMGROUP;
    }

    public String getITEMCOLOR() {
        return ITEMCOLOR;
    }

    public void setITEMCOLOR(String ITEMCOLOR) {
        this.ITEMCOLOR = ITEMCOLOR;
    }

    public String getITEMSIZE() {
        return ITEMSIZE;
    }

    public void setITEMSIZE(String ITEMSIZE) {
        this.ITEMSIZE = ITEMSIZE;
    }

    public String getITEMMODEL() {
        return ITEMMODEL;
    }

    public void setITEMMODEL(String ITEMMODEL) {
        this.ITEMMODEL = ITEMMODEL;
    }

    public String getITEMGS() {
        return ITEMGS;
    }

    public void setITEMGS(String ITEMGS) {
        this.ITEMGS = ITEMGS;
    }

    public String getITEMDIV() {
        return ITEMDIV;
    }

    public void setITEMDIV(String ITEMDIV) {
        this.ITEMDIV = ITEMDIV;
    }

    public String getITEMSUB1() {
        return ITEMSUB1;
    }

    public void setITEMSUB1(String ITEMSUB1) {
        this.ITEMSUB1 = ITEMSUB1;
    }

    public String getITEMSUB2() {
        return ITEMSUB2;
    }

    public void setITEMSUB2(String ITEMSUB2) {
        this.ITEMSUB2 = ITEMSUB2;
    }

    public String getITEMSUB3() {
        return ITEMSUB3;
    }

    public void setITEMSUB3(String ITEMSUB3) {
        this.ITEMSUB3 = ITEMSUB3;
    }

    public String getITEMSUB4() {
        return ITEMSUB4;
    }

    public void setITEMSUB4(String ITEMSUB4) {
        this.ITEMSUB4 = ITEMSUB4;
    }

    public String getITEMSUB5() {
        return ITEMSUB5;
    }

    public void setITEMSUB5(String ITEMSUB5) {
        this.ITEMSUB5 = ITEMSUB5;
    }

    public String getITEMSUB6() {
        return ITEMSUB6;
    }

    public void setITEMSUB6(String ITEMSUB6) {
        this.ITEMSUB6 = ITEMSUB6;
    }

    public String getITEMSUB7() {
        return ITEMSUB7;
    }

    public void setITEMSUB7(String ITEMSUB7) {
        this.ITEMSUB7 = ITEMSUB7;
    }
}
