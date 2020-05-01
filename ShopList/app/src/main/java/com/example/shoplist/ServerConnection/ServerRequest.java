package com.example.shoplist.ServerConnection;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.Classes.URLSendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.shoplist.Activiti.MainActivity.addRequest;
import static com.example.shoplist.Activiti.MainActivity.getCheckedCounter;
import static com.example.shoplist.Activiti.MainActivity.getMadeCounter;
import static com.example.shoplist.Activiti.MainActivity.getShopList;
import static com.example.shoplist.Activiti.MainActivity.getUserId;
import static com.example.shoplist.Activiti.MainActivity.getViewModel;

public class ServerRequest {

    private static String SERVER_IP = "http://192.168.1.194:8080/ShopListServer/";
    private static URLSendRequest url = new URLSendRequest(SERVER_IP, 5000);
    private static boolean isInternetConnection;
    private static List<NoteClass> notesFromServer;

    /**
     * Добавление заметок в базу на сервере
     * @param note заметка, которую нужно добавить
     */

    public static void noteAddServer(NoteClass note) {
        if (getUserId() != 0) {
            String request = "note?act=add&idnote="+ note.getId()
                    + "&name="+ note.getText()
                    + "&type=" + note.getType()
                    + "&amount="+ note.getAmount()
                    + "&units=" + note.getUnits()
                    + "&date=" + note.getData()
                    + "&checked=" + note.getChecked().toString()
                    + "&iduser=" + getUserId();
            try {
                int r =  Integer.parseInt(url.get(request).replaceAll("\n",""));
                Logger.getLogger("mylog").log(Level.INFO, "send");
                Logger.getLogger("mylog").log(Level.INFO, "result: " + r);

                MainActivity.incMadeCounter();
                updateServerUserInfo();
            } catch (NullPointerException ex) {
                addRequest(request);
                Logger.getLogger("myLog").log(Level.INFO, "Добавлено в буфер");
            }
        }



    }

    /**
     * Обновление информации о пользователе при изменении его статистики
     */

    public static void updateServerUserInfo() {
        String request = "login?act=update&iduser="+ getUserId()
                + "&madecounter=" + getMadeCounter()
                + "&checkcounter=" + getCheckedCounter();
        try {
            int r = Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
        } catch (NullPointerException ex) {
            addRequest(request);
            Logger.getLogger("myLog").log(Level.INFO, "Добавлено в буфер");
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
            Logger.getLogger("myLog").log(Level.INFO, "Добавлено в буфер");
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
                + "&idnote=" + note.getId()
                + "&iduser=" + getUserId()
                + "&checked=" + note.getChecked().toString();
        System.out.println(getUserId());
        System.out.println(request);
            try {
                int r = Integer.parseInt(url.get(request).replaceAll("\n",""));
                Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
            } catch (NullPointerException ex) {
                Logger.getLogger("mylog").log(Level.INFO, "result: no server connect " );
                Logger.getLogger("myLog").log(Level.INFO, "Добавлено в буфер");
                addRequest(request);
            }
    }

    /**
     * Отправка запроса из буфера
     * @param request — запрос, который нужно отправить
     */

    public static void sendRequestFromMemory (String request) {
        try {
            int r =  Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "send");
            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
            MainActivity.incMadeCounter();
            updateServerUserInfo();
        } catch (NullPointerException ex) {
            Logger.getLogger("mylog").log(Level.INFO, "result: no server connect " );
        }
    }

    /**
     * Вход/регистрация пользователя
     * @param id пользователя
     * @param name — имя пользователя
     */
    public static void login (int id, String name) {
        try {
            int r = Integer.parseInt(url.get("login?act=reg&iduser=" + id
                    + "&username="+ name
                    + "&madecounter="+ getMadeCounter()
                    + "&checkcounter=" + getCheckedCounter()).replaceAll("\n",""));

            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
        } catch (NullPointerException ex) {
            Logger.getLogger("mylog").log(Level.INFO, "result: no server connection ");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void syncNotes() throws JSONException {
        try {
            notesFromServer = new ArrayList<>();
            String notes = url.get("login?act=sync&iduser=" + getUserId());
            System.out.println(notes);
            JSONArray jsonArray = new JSONArray(notes);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String text = jsonObject.get("name").toString();
                String date = jsonObject.get("date").toString();
                int amount = Integer.parseInt(jsonObject.get("amount").toString());
                boolean checked = Boolean.getBoolean(jsonObject.get("checked").toString());
                String units = jsonObject.get("units").toString();
                String type = jsonObject.get("type").toString();
                String id = jsonObject.get("idnote").toString();
                NoteClass note = new NoteClass(text, type, units, amount, id);
                note.setChecked(checked);
                note.setData(date);
                notesFromServer.add(note);
            }

            for (NoteClass note : notesFromServer) {
                if (getShopList().stream().noneMatch(NoteClass -> NoteClass.getId().equals(note.getId()))) {
                    System.out.println("Заметки нет на устройстве, но есть на сервере ");
                    getViewModel().insert(note);
                }
            }

            for (NoteClass note : getShopList()) {
                if (notesFromServer.stream().noneMatch(NoteClass -> NoteClass.getId().equals(note.getId()))) {
                    System.out.println("Заметка добавлена на сервер");
                    noteAddServer(note);
                }
            }


        } catch (NullPointerException ex) {
            Logger.getLogger("mylog").log(Level.INFO, "result: no server connection ");
        }



    }


    public static boolean getInternetConnection() {
        return isInternetConnection;
    }

    public static void setInternetConnection(boolean isInternetConnection) {
        ServerRequest.isInternetConnection = isInternetConnection;
    }
}
