package de.hasi.bandbash.utils;

import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Utils {
    public static String getFirst(Collection<Map<String, String>> answers, String name){
        return answers.stream().map(stringStringMap -> stringStringMap.get(name)).findFirst().get();
    }

    public static Map<String, String> newMessageMap(String message){
        HashMap<String, String> hm = new HashMap<>();
        hm.put("MESSAGE", message);
        return hm;
    }

    public static Collection<Map<String, String>> newMessage(String message){
        LinkedList<Map<String, String>> ll = new LinkedList<>();
        ll.add(newMessageMap(message));
        return ll;
    }


}
