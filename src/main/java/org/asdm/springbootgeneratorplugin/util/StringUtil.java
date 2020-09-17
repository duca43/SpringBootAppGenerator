package org.asdm.springbootgeneratorplugin.util;

import org.asdm.springbootgeneratorplugin.model.MetaAppInfo;

import java.io.File;

public class StringUtil {
    public static String modifyName(final String name) {
        final StringBuilder builder = new StringBuilder();
        if (name.contains("_")) {
            final String[] words = name.split("_");
            for (final String word : words) {
                builder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
            }
        } else if (name.contains("-")) {
            final String[] words = name.split("-");
            for (final String word : words) {
                builder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
            }
        } else {
            builder.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
        }
        return builder.toString();
    }

    public static String transformPackageToPath(final String pckg) {
        return pckg.replace(".", File.separator);
    }

    public static String getBasePackage(final MetaAppInfo metaAppInfo) {
        return metaAppInfo.getInfo().getGroupId() + "." + metaAppInfo.getInfo().getArtifactId().replaceAll("-", "").toLowerCase();
    }
}