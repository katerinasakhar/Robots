package locale;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class RobotsLocale {
    public static volatile ResourceBundle INSTANCE;
    private static final String CONFIG = "config";
    private static final String CONFIG_LANG = "lang";

    public static ResourceBundle getINSTANCE()
    {
        if (INSTANCE == null)
        {
            synchronized (RobotsLocale.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = ResourceBundle.getBundle("text", getLocale());
                }
            }
        }

        return INSTANCE;
    }

    private static Locale getLocale()
    {
        synchronized (CONFIG) {
            try {
                ObjectInputStream configFile = new ObjectInputStream(new FileInputStream(new File(CONFIG)));
                if (configFile.readObject() instanceof HashMap<?, ?> config) {
                    Object obj = config.get(CONFIG_LANG);
                    if (obj instanceof Locale locale) {
                        return locale;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Locale("ru", "RU");
        }
    }

    public static void setLang(Locale locale)
    {
        synchronized (CONFIG) {
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
}
