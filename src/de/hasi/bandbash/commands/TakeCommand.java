package de.hasi.bandbash.commands;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import static de.hasi.bandbash.utils.Utils.*;

public class TakeCommand implements Command {
    private final Queries queries;

    public TakeCommand(Queries queries) {
        this.queries = queries;
    }

    @Override
    public Collection<Map<String, String>> execute(List<String> params) {
        if(params.size() < 2){
            return newMessage("What should I take?");
        }
        return queries.take(params.get(1));
    }
}
