package de.hasi.bandbash.commands;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class InventoryCommand implements Command {
    private final Queries queries;

    public InventoryCommand(Queries queries) {
        this.queries = queries;
    }

    @Override
    public Collection<Map<String, String>> execute(List<String> params) {
        return queries.inventory();
    }
}
