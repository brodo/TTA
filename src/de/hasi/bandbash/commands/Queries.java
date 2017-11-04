package de.hasi.bandbash.commands;

import java.util.Collection;
import java.util.Map;

public interface Queries {
    Collection<Map<String, String>> getAreaIntro();

    Collection<Map<String, String>> describeArea();

    Collection<Map<String, String>> getPaths();

    Collection<Map<String, String>>  goTo(String target);

    Collection<Map<String, String>> inventory();

    Collection<Map<String, String>> describeItem(String itemname);

    Collection<Map<String, String>> take(String item);

    Collection<Map<String, String>> getTime();

    Collection<Map<String, String>> setTime(String time);

    Collection<Map<String,String>> describePerson(String s);
}
