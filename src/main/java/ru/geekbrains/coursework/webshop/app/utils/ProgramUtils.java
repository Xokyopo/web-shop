package ru.geekbrains.coursework.webshop.app.utils;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

public class ProgramUtils {

    public static Optional<String> getRequestMappingValue(Object object) {
        String result = "";
        if (object.getClass().isAnnotationPresent(RequestMapping.class)) {
            result = object.getClass().getAnnotation(RequestMapping.class).value()[0];
        }
        return (result.isEmpty()) ? Optional.empty() : Optional.of(result);
    }

    public static String addSlashOnStartAndRemoveOnEnd(String left) {
        return (left.startsWith("/") ? "" : "/") + left.substring(0, left.endsWith("/") ? left.length() - 1 : left.length());
    }

    public String convertToHumanFileLength(float fileLength) {
        float baseLength = 1024;
        String[] prefixes = new String[]{"B", "KB", "MB", "GB", "TB", "PB"};
        int count;
        for (count = 0; fileLength >= baseLength; count++) {
            fileLength = fileLength / baseLength;
        }
        return String.format("%.2f %s", fileLength, prefixes[count]);
    }

    public static <T> T exceptionReplacer(ExReturnedPod<T> function, RuntimeException replacedException) {
        try {
            return function.run();
        } catch (Exception e) {
            replacedException.addSuppressed(e);
            throw replacedException;
        }
    }

    public static void exceptionReplacer(ExCallbackPod function, RuntimeException replacedException) {
        try {
            function.run();
        } catch (Exception e) {
            replacedException.addSuppressed(e);
            throw replacedException;
        }
    }
}
