package de.hasi.bandbash.main;

import de.hasi.bandbash.datalog.Datalog;

public class Main {
    public static void main(String[] args) {
        Datalog dl = new Datalog();
        dl.readFile("datalog/facts.dl");
        REPL repl = new REPL();
        repl.start(dl);

    }
}
