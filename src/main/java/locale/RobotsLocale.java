package locale;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class RobotsLocale {
    private static final ResourceBundle INSTANCE = init();
    private static final String CONFIG = "config";
    private static final String CONFIG_LANG = "lang";

    private static ResourceBundle init()
    {
        return ResourceBundle.getBundle("text", getLocale());
    }

    public static ResourceBundle getINSTANCE()
    {
        return INSTANCE;
    }

    private static Locale getLocale()
    {
        try {
            ObjectInputStream configFile = new ObjectInputStream(new FileInputStream(new File(CONFIG)));
            if (configFile.readObject() instanceof HashMap<?, ?> config)
            {
                Object obj = config.get(CONFIG_LANG);
                if (obj instanceof Locale locale)
                {
                    return locale;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Locale("ru", "RU");
    }

    public static void setLang(Locale locale)
    {
        try {
            ObjectOutputStream configFile = new ObjectOutputStream(new FileOutputStream(new File(CONFIG)));
            HashMap<String, Locale> config = new HashMap<>();
            config.put(CONFIG_LANG, locale);
            configFile.writeObject(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
