package com.example.user54.InventoryApp.Model;

import java.util.List;

public class GetCaptionModel {
//"DESCNAMEA": "المجموعة",
//"DESCNAMEE": "المجموعة",
//"DESCTYPE": "1",
    private  String DESCNAMEA;
    private  String DESCNAMEE;
    private  String DESCTYPE;

    private List<String> DescList;

    public GetCaptionModel() {
    }

    public GetCaptionModel(String DESCNAMEA, String DESCNAMEE, String DESCTYPE, List<String> descList) {
        this.DESCNAMEA = DESCNAMEA;
        this.DESCNAMEE = DESCNAMEE;
        this.DESCTYPE = DESCTYPE;
        DescList = descList;
    }


    public String getDESCNAMEA() {
        return DESCNAMEA;
    }

    public void setDESCNAMEA(String DESCNAMEA) {
        this.DESCNAMEA = DESCNAMEA;
    }

    public String getDESCNAMEE() {
        return DESCNAMEE;
    }

    public void setDESCNAMEE(String DESCNAMEE) {
        this.DESCNAMEE = DESCNAMEE;
    }

    public String getDESCTYPE() {
        return DESCTYPE;
    }

    public void setDESCTYPE(String DESCTYPE) {
        this.DESCTYPE = DESCTYPE;
    }

    public List<String> getDescList() {
        return DescList;
    }

    public void setDescList(List<String> descList) {
        DescList = descList;
    }
}
