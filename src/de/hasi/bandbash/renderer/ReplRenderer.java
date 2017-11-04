package de.hasi.bandbash.renderer;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class ReplRenderer {

    public static String defaultRenderer(Collection<Map<String, String>> queryResult){
        return queryResult.stream().map(ReplRenderer::renderMap).collect(Collectors.joining("\n"));
    }

    public static String vauleRenderer(Collection<Map<String, String>> queryResult){
        return queryResult.stream().map(ReplRenderer::renderValue).collect(Collectors.joining("\n"));
    }

    public static String renderMap(Map<String, String> entry){
        return entry.entrySet().stream().map(e -> e.getKey() + " - " + e.getValue()).collect(Collectors.joining("\n"));
    }

    public static String renderValue(Map<String, String> entry){
        return entry.values().stream().collect(Collectors.joining(" - in"));
    }


    public static String renderGetPaths(Collection<Map<String, String>> queryResult) {
        StringBuilder sb = new StringBuilder();
        sb.append("Available Paths:");
        sb.append("\n");
        sb.append(vauleRenderer(queryResult));
        return sb.toString();
    }

    public static String renderGoto(Collection<Map<String, String>> queryResult){
        StringBuilder sb = new StringBuilder();
        sb.append(defaultRenderer(queryResult));
        return sb.toString();
    }
}
