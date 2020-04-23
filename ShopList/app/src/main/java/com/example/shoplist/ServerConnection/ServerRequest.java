package com.example.shoplist.ServerConnection;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.Classes.URLSendRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.shoplist.Activiti.MainActivity.addRequest;
import static com.example.shoplist.Activiti.MainActivity.getCheckedCounter;
import static com.example.shoplist.Activiti.MainActivity.getMadeCounter;
import static com.example.shoplist.Activiti.MainActivity.getUserId;

public class ServerRequest {

    private static String SERVER_IP = "http://192.168.1.194:8080/ShopListServer/";
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
                + "&iduser=" + getUserId();

        if (getInternetConnection()) {
            int r =  Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "send");

            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
            MainActivity.incMadeCounter();
            updateServerUserInfo();
        } else {
            addRequest(request);
            Logger.getLogger("myLog").log(Level.INFO, "Добавлено в буфер");
        }

    }

    /**
     * Обновление информации о пользователе при изменении его статистики
     */

    public static void updateServerUserInfo() {
        String request = "login?act=update&iduser="+ getUserId()
                + "&madecounter=" + getMadeCounter()
                + "&checkcounter=" + getCheckedCounter();

        if (getInternetConnection()) {
            int r = Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
        } else {
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
                + "&iduser=" + getUserId();
        System.out.println(getUserId());
        System.out.println(request);
        if (getInternetConnection()) {
            int r = Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);

        } else {
            Logger.getLogger("myLog").log(Level.INFO, "Добавлено в буфер");
            addRequest(request);
        }

    }

    /**
     * Отправка запроса из буфера
     * @param request — запрос, который нужно отправить
     */

    public static void sendRequestFromMemory (String request) {
        if (getInternetConnection()) {
            int r =  Integer.parseInt(url.get(request).replaceAll("\n",""));
            Logger.getLogger("mylog").log(Level.INFO, "send");
            Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
            MainActivity.incMadeCounter();
            updateServerUserInfo();
        }
    }

    /**
     * Вход/регистрация пользователя
     * @param id пользователя
     * @param name — имя пользователя
     */
    public static void login (int id, String name) {
        int r = Integer.parseInt(url.get("login?act=reg&iduser=" + id
                + "&username="+ name
                + "&madecounter="+ getMadeCounter()
                + "&checkcounter=" + getCheckedCounter()).replaceAll("\n",""));

        Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
    }

    public static void syncNotes() throws JSONException {
        String notes = url.get("login?act=sync&iduser=" + getUserId());
        System.out.println(notes);
        JSONArray jsonArray = new JSONArray(notes);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.get("idnote").toString() + " i:" + i);
        }

    }


    public static boolean getInternetConnection() {
        return isInternetConnection;
    }

    public static void setInternetConnection(boolean isInternetConnection) {
        ServerRequest.isInternetConnection = isInternetConnection;
    }
}
