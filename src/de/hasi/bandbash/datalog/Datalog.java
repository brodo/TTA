package de.hasi.bandbash.datalog;

import de.hasi.bandbash.commands.Queries;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Jatalog;
import za.co.wstoop.jatalog.output.DefaultQueryOutput;
import za.co.wstoop.jatalog.output.QueryOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import static de.hasi.bandbash.utils.Utils.getFirst;
import static de.hasi.bandbash.utils.Utils.newMessage;


public class Datalog implements Queries {

    private final Jatalog jatalog;
    private final QueryOutput queryOutput;

    public Datalog(){
        jatalog = new Jatalog();
        queryOutput = new DefaultQueryOutput();
    }

    public void readFile(String filename) {
        try {
            try (Reader reader = new BufferedReader(new FileReader(filename))) {
                jatalog.executeAll(reader, queryOutput);
            }
        } catch (DatalogException |
                IOException e) {
            e.printStackTrace();
        }
    }

    private boolean getBoolValue(String predicate, String arg) {
        try {
            return jatalog.query(Expr.expr(predicate, arg)).size() > 0;
        } catch (DatalogException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getCurrentArea() throws DatalogException {
        Collection<Map<String, String>> answers = jatalog.query(Expr.expr("personArea", "me", "X"));
        return getFirst(answers, "X");
    }

    @Override
    public Collection<Map<String, String>> getAreaIntro() {
        try {
            return jatalog.query(Expr.expr("areaIntro",getCurrentArea(), "INTRO"));
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Entered new Area!");
    }

    @Override
    public Collection<Map<String, String>> describeArea() {
        try {
            String currentArea = getCurrentArea();

            return jatalog.query(Expr.expr("describeArea", currentArea, "Subject", "Type"));
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error describing area paths");
    }

    @Override
    public Collection<Map<String, String>> getPaths() {
        try {
            return jatalog.query(Expr.expr("path",getCurrentArea(), "Path"));

        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error listing paths");
    }

    @Override
    public Collection<Map<String, String>>  goTo(String target) {
        try {
            if( getBoolValue("reachable",target)){
                jatalog.delete(Expr.expr("personArea", "me", "X"));
                jatalog.fact(Expr.expr("personArea", "me", target));
                if(!getBoolValue("visited", target)){
                    jatalog.fact(Expr.expr("visited", target));
                    return getAreaIntro();
                }
                return newMessage("Changed area");
            } else {
                return newMessage("Target area not reachabele");
            }
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }



    @Override
    public Collection<Map<String, String>> inventory() {
        try {
            return jatalog.query(Expr.expr("item","Item", "Description", "inventory"));
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error listing inventory!");
    }

    @Override
    public Collection<Map<String, String>> describeItem(String itemname) {
        try {
            if(getBoolValue("visibleItem", itemname)){
                return  jatalog.query(Expr.expr("item",itemname, "Description", "Location"));

            } else {
                return newMessage("I can not see that item");
            }

        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error looking at item");
    }

    @Override
    public Collection<Map<String, String>> take(String item) {
        try {
            if(getBoolValue("inInventory", item)){
                return newMessage("Item is already in inventory");
            }

            if(getBoolValue("visibleItem", item)){
                Collection<Map<String, String>> items = jatalog.query(Expr.expr("item",item, "X", "Y"));
                Map<String, String> currentItem = items.stream().findFirst().get();
                jatalog.delete(Expr.expr("item", item, currentItem.get("X"), currentItem.get("Y")));
                jatalog.fact(Expr.expr("item", item, currentItem.get("X"), "inventory"));
                return newMessage("Added " + item + " to inventory");
            } else {
                return newMessage("Item does not exist or is not reachable.");

            }
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error while taking inventory");
    }

    @Override
    public Collection<Map<String, String>> getTime() {
        try {
            return jatalog.query(Expr.expr("currentTime","Time"));
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error getting the current time!");
    }

    @Override
    public Collection<Map<String, String>> setTime(String time) {
        try {
            jatalog.delete(Expr.expr("currentTime", "X"));
            jatalog.fact(Expr.expr("currentTime", time));
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error setting the current time!");

    }

    @Override
    public Collection<Map<String, String>> describePerson(String s) {
        try {
            return jatalog.query(Expr.expr("person",s, "Person"));
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error setting the current time!");
    }
}
