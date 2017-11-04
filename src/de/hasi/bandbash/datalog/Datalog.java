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
import java.util.stream.Collectors;

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
        Collection<Map<String, String>> answers = jatalog.query(Expr.expr("currentArea", "X"));
        return getFirst(answers, "X");
    }
    @Override
    public Collection<Map<String, String>> describeArea() {
        try {
            String currentArea = getCurrentArea();
            Collection<Map<String, String>> answers =
                    jatalog.query(Expr.expr("area", currentArea, "AREA_DESCRIPTION"));
            Collection<Map<String, String>> itemList = jatalog.query(Expr.expr("item","ITEM", "DESCRIPTION", currentArea));

            answers.addAll(itemList);
            return answers;
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error describing area paths");
    }

    @Override
    public Collection<Map<String, String>> getPaths() {
        try {
            return jatalog.query(Expr.expr("path",getCurrentArea(), "PATH"));

        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error listing paths");
    }

    @Override
    public Collection<Map<String, String>>  goTo(String target) {
        try {
            if( getBoolValue("reachable",target)){
                jatalog.delete(Expr.expr("currentArea", getCurrentArea()));
                jatalog.fact(Expr.expr("currentArea", target));
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
            return jatalog.query(Expr.expr("item","ITEM", "DESCRIPTION", "inventory"));
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return newMessage("Error listing inventory!");
    }

    @Override
    public Collection<Map<String, String>> describeItem(String itemname) {
        try {
            if(getBoolValue("visibleItem", itemname)){
                return  jatalog.query(Expr.expr("item",itemname, "DESCRIPTION", "LOCATION"));

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
}
