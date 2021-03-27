package me.mckoxu.mcktools.util;

import me.mckoxu.mcktools.enums.TopType;
import me.mckoxu.mcktools.object.OfflineUser;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    public static String color(String s) {
        try {
            return ChatColor.translateAlternateColorCodes('&', s);
        } catch (Exception ex) {
            return "";
        }
    }

    public static ArrayList<String> color(List<String> s) {
        ArrayList<String> al = new ArrayList<String>();
        for (String msg : s) {
            al.add(color(msg));
        }
        return al;
    }

    public static String censore(String msg, List<String> list, String symbol) {
        String m = msg;
        for (String s : list) {
            m = m.replace(s, symbol);
        }
        return m;
    }

    public String replace(String s, TopType type, List<OfflineUser> list) {
        Matcher m = Pattern.compile("\\{TOP\\-[0-9]*\\-FORMAT\\}").matcher(s);
        while (m.find()) {
            String raw = m.group();
            String match = raw.substring(8);
            match = match.subSequence(0, match.indexOf("-")).toString();
            System.out.println(match);
            if (Util.isInt(match)) {
                int index = Integer.valueOf(match) - 1;
                if (list.size() > index) {//lista z nazwami posortowanymi
                    s = s.replaceFirst(Pattern.quote(raw), String.valueOf(list.get(Integer.valueOf(match) - 1)));//lista z nazwami posortowanymi
                    continue;
                }
            }
            s = s.replaceFirst(Pattern.quote(raw), "BRAK");
        }
        return s;
    }


}
