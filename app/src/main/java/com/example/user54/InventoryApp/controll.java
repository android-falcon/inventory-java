package com.example.user54.InventoryApp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class controll {

    public static boolean isYellow=true;
    public static String URL="http://10.0.0.22:8081/";
    public static String F_D="";
    public static String Item_name="",qty_name;
    public static int dataBaseNo=110;
    public  static int   RoomVersion=110;


    public String readFromFile(Context context) {

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

        return ret.replace("\n","");
    }


    public String readFromAssest(Context context){

        String string = "";
        InputStream inputStream = null;
        try {
            inputStream =  context.getAssets().open("itemC.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;

    }


    public String readFromSwitchAssest(Context context){

        String string = "";
        InputStream inputStream = null;
        try {
            inputStream =  context.getAssets().open("itemS.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;

    }

    public String readFromUnitAssest(Context context){

        String string = "";
        InputStream inputStream = null;
        try {
            inputStream =  context.getAssets().open("itemU.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;

    }

    public String readFromStoreAssest(Context context){

        String string = "";
        InputStream inputStream = null;
        try {
            inputStream =  context.getAssets().open("itemST.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;

    }

    public void WriteBtn(Context context,String DataNo) {


        // add-write text into file
        try {
            FileOutputStream fileout=context.openFileOutput("databaseNo.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(DataNo);
            outputWriter.close();

            //display file saved message
            Toast.makeText(context, "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
