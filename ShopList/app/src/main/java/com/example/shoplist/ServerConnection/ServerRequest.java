package com.example.shoplist.ServerConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Activiti.SettingActivity;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.Classes.URLSendRequest;

import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.core.content.ContextCompat;

import static com.example.shoplist.Activiti.MainActivity.addRequest;
import static com.example.shoplist.Activiti.MainActivity.clearRequest;

public class ServerRequest {

    private static String SERVER_IP = "http://192.168.56.1:8080/ShopListServer/";
    private static URLSendRequest url = new URLSendRequest(SERVER_IP, 20000);
    private static boolean isInternetConnection;

    /**
     * Добавление заметок в базу на сервере
     * @param note заметка, которую нужно добавить
     */

    public static void noteAddServer(NoteClass note) {
        String request = "note?act=add&idnote="+ note.getId()
                + "&name="+ note.getText()
                + "&type=" + note.getType()
                + "&amount="+ note.getAmount()
                + "&units=" + note.getUnits()
                + "&date=" + note.getData()
                + "&checked=" + note.getChecked().toString()
                + "&iduser=" + MainActivity.getUserId();

        if (getInternetConnection()) {
            int r =  Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "send");

            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
            MainActivity.incMadeCounter();
            updateServerUserInfo();
        } else {
            addRequest(request);
            Logger.getLogger("myLog").log(Level.INFO, "Не отправлено");
        }

    }



    /**
     * Обновление информации о пользователе при изменении его статистики
     */

    public static void updateServerUserInfo() {
        String request = "login?act=update&iduser="+MainActivity.getUserId()
                + "&madecounter=" + MainActivity.getMadeCounter()
                + "&checkcounter=" + MainActivity.getCheckedCounter();

        if (getInternetConnection()) {
            int r = Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
        } else {
            addRequest(request);
            Logger.getLogger("myLog").log(Level.INFO, "Не отправлено");
        }

    }


    /**
     * Удаление заметки с сервера
     * @param note заметка для удаления
     */

    public static void deleteNoteServer(NoteClass note) {
        String request = "note?act=delete&idnote=" + note.getId();

        if (getInternetConnection()) {
            int r = Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
        } else {
            addRequest(request);
            Logger.getLogger("myLog").log(Level.INFO, "Не отправлено");
        }

    }

    /**
     * Обновление заметки на сервере
     * @param note заметка для редактирования
     */

    public static void editNoteServer(NoteClass note) {
        String request = "note?act=edit&name="+ note.getText()
                + "&type=" + note.getType()
                + "&amount="+ note.getAmount()
                + "&units=" + note.getUnits()
                + "&idnote=" + note.getId();
        if (getInternetConnection()) {
            int r = Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);

        } else {
            Logger.getLogger("myLog").log(Level.INFO, "Не отправлено");
            addRequest(request);
        }

    }

    public static void sendRequestFromMemory (String request) {
        if (getInternetConnection()) {
            int r =  Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "send");
            clearRequest(request);
            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
            MainActivity.incMadeCounter();
            updateServerUserInfo();
        }
    }




    public static boolean getInternetConnection() {
        return isInternetConnection;
    }

    public static void setInternetConnection(boolean isInternetConnection) {
        ServerRequest.isInternetConnection = isInternetConnection;
    }
}
