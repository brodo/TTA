package de.hasi.bandbash.commands;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GotoCommand implements Command {

    private final Queries queries;

    public GotoCommand(Queries queries) {
        this.queries = queries;
    }

    @Override
    public Collection<Map<String, String>> execute(List<String> params) {
        if(params.size() < 2){
            return new LinkedList<>();
        } else {
            return queries.goTo(params.get(1));
        }

    }
}
