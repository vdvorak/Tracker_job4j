package ru.job4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Читаем файл конфигурации.
 * Считать содержимое конфиг-файла.
 * По умолчанию это файл: connection_config.properties.
 *
 * @author Daniils Loputevs
 * @version $Id$
 * @since 04.05.20.
 */
public class ConfigLoader {
    private final String path;
    private final Properties values = new Properties();
    private static final Logger LOG = LoggerFactory.getLogger(ConfigLoader.class);

    public ConfigLoader(final String path) {
        this.path = path;
        load();
    }

    /**
     * Загружает настройки из файла по пути {@code path}.
     * * {@code path} - загружается через конструктор.
     * Фильтруем через stream от комментариев и пустых строк.
     * <p>
     * Собираем в карту:
     * key   - имя настройки.
     * value - значение настройки.
     */
    private void load() {
        try (var in = new BufferedReader(new FileReader(path))) {
            this.values.load(in);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Получить значение настройки по её названию.
     *
     * @param key - ключ.
     * @return value/null
     */
    public String value(String key) {
        return this.values.getProperty(key);
    }

    @Override
    public String toString() {
        return "Config: " + values;
    }

    /* static methods */

    public static String getPsqlConfigPath() {
        String cfgResourcePath = "connection_config.properties";
        return ConfigLoader.class.getClassLoader().getResource(cfgResourcePath).getFile();
    }
}