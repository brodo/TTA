package de.hasi.bandbash.commands;

import java.util.Collection;
import java.util.Map;

public interface Queries {
    Collection<Map<String, String>> describeArea();

    Collection<Map<String, String>> getPaths();

    Collection<Map<String, String>>  goTo(String target);

    Collection<Map<String, String>> inventory();

    Collection<Map<String, String>> describeItem(String itemname);

    Collection<Map<String, String>> take(String item);
}
