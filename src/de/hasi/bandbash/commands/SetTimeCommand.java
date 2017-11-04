package de.hasi.bandbash.commands;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import static de.hasi.bandbash.utils.Utils.*;

public class SetTimeCommand implements Command {
    private final Queries queries;

    public SetTimeCommand(Queries queries) {
        this.queries = queries;
    }

    @Override
    public Collection<Map<String, String>> execute(List<String> params) {
        if(params.size() < 2){
            return newMessage("Set time to what?");
        }
        return queries.setTime(params.get(1));
    }
}
