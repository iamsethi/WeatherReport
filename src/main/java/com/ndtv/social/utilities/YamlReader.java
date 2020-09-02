package com.ndtv.social.utilities;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlReader {
    static Yaml yaml = null;

    public static Map<String, Object> loadUserDataYaml(InputStream envDataStream) {
        yaml = new Yaml();
        return (Map)yaml.load(envDataStream);
    }
}
