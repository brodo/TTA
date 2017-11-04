package de.hasi.bandbash.renderer;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class ReplRenderer {

    public static String keyValueRenderer(Collection<Map<String, String>> queryResult){
        return queryResult.stream().map(ReplRenderer::renderMap).collect(Collectors.joining("\n"));
    }

    public static String vauleRenderer(Collection<Map<String, String>> queryResult){
        return queryResult.stream().map(ReplRenderer::renderValue).collect(Collectors.joining("\n"));
    }

    private static String renderMap(Map<String, String> entry){
        return entry.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining(", "));
    }

    private static String renderValue(Map<String, String> entry){
        return entry.values().stream().collect(Collectors.joining(" - "));
    }

}
