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

        out: while(true) {
            try {
                System.out.print("> ");
                String line = buffer.readLine();
                if(line == null) {
                    break; // EOF
                }
                line = line.trim();
                List<String> command = Arrays.asList(line.split(" "));

//                StringTokenizer tokenizer = new StringTokenizer(line);
//                if(!tokenizer.hasMoreTokens())
//                    continue;
//                String command = tokenizer.nextToken().toLowerCase();
                Collection<Map<String, String>> queryResult;
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
                        System.out.println("inventory                     - List Inventory");
                        break;
                    case "describe":
                        queryResult = new DescribeCommand(queries).execute(command);
                        System.out.println(ReplRenderer.vauleRenderer(queryResult));
                        break;
                    case "paths":
                        queryResult = new PathsCommand(queries).execute(command);
                        System.out.println(ReplRenderer.renderGetPaths(queryResult));
                        break;
                    case "goto":
                        queryResult = new GotoCommand(queries).execute(command);
                        System.out.println(ReplRenderer.renderGoto(queryResult));
                        break;
                    case "inventory":
                        queryResult = new InventoryCommand(queries).execute(command);
                        System.out.println(ReplRenderer.vauleRenderer(queryResult));
                        break;
                    case "take":
                        queryResult = new TakeCommand(queries).execute(command);
                        System.out.println(ReplRenderer.defaultRenderer(queryResult));
                        break;
                    default:
                        System.out.println("Unknown command, type 'help' for command list.");
                        break;
                }

                history.add(line);

            } catch ( IOException e) {
                e.printStackTrace();
            }
        }
    }

}
