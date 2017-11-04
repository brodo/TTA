package de.hasi.bandbash.commands;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Command {
    public Collection<Map<String, String>> execute(List<String> params);
}
