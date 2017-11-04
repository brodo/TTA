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
        String errorStr = "Either say 'describe area', 'describe item x' or 'describe person x'";
        if(params.size() < 2 ){
            return newMessage(errorStr);
        }
        switch (params.get(1)){
            case "area":
                return queries.describeArea();
            case "item":
                if(params.size() < 3){
                    return newMessage("Please specify an item");
                }
                return queries.describeItem(params.get(2));
            case "person":
                if(params.size() < 3){
                    return newMessage("Please specify a person");
                }
                return queries.describePerson(params.get(2));
        }
        return newMessage(errorStr);
    }
}
