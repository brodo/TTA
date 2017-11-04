package de.hasi.bandbash.main;

import de.hasi.bandbash.commands.*;
import de.hasi.bandbash.renderer.ReplRenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class REPL {
    public void start(Queries queries){
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("BandBash REPL; Type 'help' for commands, 'exit' to quit.");

        List<String> history = new LinkedList<>();


        Collection<Map<String, String>> queryResult;

        System.out.println(ReplRenderer.vauleRenderer(queries.getAreaIntro()));

        out: while(true) {
            try {
                System.out.print("> ");
                String line = buffer.readLine();
                if(line == null) {
                    break; // EOF
                }
                line = line.trim();
                List<String> command = Arrays.asList(line.split(" "));


                switch (command.get(0)) {
                    case "exit":
                        break out;
                    case "history":
                        for (String item : history) {
                            System.out.println(item);
                        }
                        break;
                    case "help":
                        System.out.println("Queries:");
                        System.out.println("exit                          - Quit BandBash");
                        System.out.println("history                       - Show past commands");
                        System.out.println("describe <area/item/person>   - Describe something");
                        System.out.println("paths                         - List available paths");
                        System.out.println("goto <area>                   - Go to a specific area");
                        System.out.println("time                          - Get the current time");
                        System.out.println("inventory                     - List Inventory");
                        break;
                }

                CommandParser commandParser = new CommandParser(queries);
                Command commandObj = commandParser.commandFromText(command);
                queryResult = commandObj.execute(command);
                System.out.println(ReplRenderer.keyValueRenderer(queryResult));
                history.add(line);

            } catch ( IOException e) {
                e.printStackTrace();
            }
        }
    }

}
