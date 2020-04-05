package com.example.shoplist.Classes;

import com.example.shoplist.Activiti.MainActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRequest {

    private static String SERVER_IP = "http://192.168.56.1:8080/ShopListServer/";
    private static URLSendRequest url = new URLSendRequest(SERVER_IP, 20000);

    /**
     * Добавление заметок в базу на сервере
     * @param note заметка, которую нужно добавить
     */

    public static void noteAddServer(NoteClass note) {
        Logger.getLogger("mylog").log(Level.INFO, "send");
        int r = Integer.parseInt(url.get("note?act=add&idnote="+ note.getId()
                + "&name="+ note.getText()
                + "&type=" + note.getType()
                + "&amount="+ note.getAmount()
                + "&units=" + note.getUnits()
                + "&date=" + note.getData()
                + "&checked=" + note.getChecked().toString()
                + "&iduser=" + MainActivity.getUserId()).replaceAll("\n",""));
        Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
        MainActivity.incMadeCounter();
        updateServerUserInfo();
    }

    /**
     * Обновление информации о пользователе при изменении его статистики
     */

    public static void updateServerUserInfo() {
        int r = Integer.parseInt(url.get("login?act=update&iduser="+MainActivity.getUserId()
                + "&madecounter=" + MainActivity.getMadeCounter()
                + "&checkcounter=" + MainActivity.getCheckedCounter()).replaceAll("\n",""));
        Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
    }

    /**
     * Удаление заметки с сервера
     * @param note заметка для удаления
     */

    public static void deleteNoteServer(NoteClass note) {
        Logger.getLogger("mylog").log(Level.INFO, "send");
        int r = Integer.parseInt(url.get("note?act=delete&idnote=" + note.getId()).replaceAll("\n",""));
        Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
    }

    /**
     * Обновление заметки на сервере
     * @param note заметка для редактирования
     */

    public static void editNoteServer(NoteClass note) {
        Logger.getLogger("mylog").log(Level.INFO, "send");
        int r = Integer.parseInt(url.get("note?act=edit&name="+ note.getText()
                + "&type=" + note.getType()
                + "&amount="+ note.getAmount()
                + "&units=" + note.getUnits()
                + "&idnote=" + note.getId()).replaceAll("\n",""));
        Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
    }


}
