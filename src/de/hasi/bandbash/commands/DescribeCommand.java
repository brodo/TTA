package de.hasi.bandbash.commands;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import static de.hasi.bandbash.utils.Utils.*;

public class DescribeCommand implements Command {
    private final Queries queries;

    public DescribeCommand(Queries queries) {
        this.queries = queries;
    }

    @Override
    public Collection<Map<String, String>> execute(List<String> params) {
        if(params.size() < 2 ){
            return newMessage("Either say 'describe area' or 'describe item x'");
        }
        switch (params.get(1)){
            case "area":
                return queries.describeArea();
            case "item":
                if(params.size() < 3){
                    return newMessage("Please specify an item");
                }
                return queries.describeItem(params.get(2));
        }

        return null;
    }
}
