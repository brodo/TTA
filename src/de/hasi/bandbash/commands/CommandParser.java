package de.hasi.bandbash.commands;

import de.hasi.bandbash.renderer.ReplRenderer;

import java.util.List;

public class CommandParser {
    private final Queries queries;

    public CommandParser(Queries queries) {
        this.queries = queries;
    }

    public Command commandFromText(List<String> command){
        switch (command.get(0)) {
            case "describe":
                return new DescribeCommand(queries);
            case "paths":
                return new PathsCommand(queries);
            case "goto":
                return new GotoCommand(queries);
            case "inventory":
                return new InventoryCommand(queries);
            case "take":
                return new TakeCommand(queries);
            case "time":
                return new GetTimeCommand(queries);
        }
        return new NilCommand();
    }
}
