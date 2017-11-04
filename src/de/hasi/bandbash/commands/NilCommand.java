package de.hasi.bandbash.commands;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import static de.hasi.bandbash.utils.Utils.*;

public class NilCommand implements Command {
    @Override
    public Collection<Map<String, String>> execute(List<String> params) {
        return newMessage("NULL Command");
    }
}
