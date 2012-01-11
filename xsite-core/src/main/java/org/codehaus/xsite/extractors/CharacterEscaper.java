package org.codehaus.xsite.extractors;

import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * Allows the escaping of characters with HTML entities. The escaped characters
 * are expressed as a Map, linking the the Unicode character to the
 * corresponding HTML entity. By default characters in the ordinal range 192-255
 * are escaped.
 * 
 * @author Mauro Talevi
 */
public class CharacterEscaper {
    private final Map<String, String> characters;

    public CharacterEscaper() {
        this(defaultEscapedCharacters());
    }

    public CharacterEscaper(Map<String, String> characters) {
        this.characters = characters;
    }

    public String escape(String value) {
        String escaped = value;
        for (String key : characters.keySet()) {
            escaped = escaped.replace(key, characters.get(key));
        }
        return escaped;
    }

    /**
     * Returns the escaped characters from ordinal number range 192-255
     * 
     * @return A Map of escaped characters
     */
    public static Map<String, String> defaultEscapedCharacters() {
        Map<String, String> escaped = new HashMap<String, String>();
        escaped.putAll(escapedCharacters(192, 255));
        return escaped;
    }

    /**
     * Returns the escaped characters from ordinal number range provided
     * 
     * @return A Map of escaped characters
     */
    public static Map<String, String> escapedCharacters(int from, int to) {
        Map<String, String> escaped = new HashMap<String, String>();
        String entity = "&#{0};";
        for (int c = from; c <= to; c++) {
            escaped.put(((Character) (char) c).toString(), format(entity, c));
        }
        return escaped;
    }

}
