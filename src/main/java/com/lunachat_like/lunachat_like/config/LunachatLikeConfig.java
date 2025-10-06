package com.lunachat_like.lunachat_like.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class LunachatLikeConfig {

    public static boolean enableReceive = true;
    public static boolean enableSend = true;
    public static boolean autesend = true;
    public static boolean auteres = true;
    public static String channel = "all";
    public static int hudX = 5;
    public static int hudY = 5;

    public static Configuration config;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);

        try {
            config.load();

            enableReceive = config.getBoolean("enableReceive",Configuration.CATEGORY_GENERAL,true,"受信チャットをローマ字変換するか");

            enableSend = config.getBoolean("enableSend",Configuration.CATEGORY_GENERAL,true,"送信チャットをローマ字変換するか");
            
            autesend = config.getBoolean("autesend",Configuration.CATEGORY_GENERAL,false,"送信時の自動チャンネル切り替え");
            auteres = config.getBoolean("auteres",Configuration.CATEGORY_GENERAL,false,"受信時の自動チャンネル切り替え");
            channel = config.getString("channel",Configuration.CATEGORY_GENERAL,"all","デフォルトの発言先");
            hudX = config.getInt(Configuration.CATEGORY_GENERAL, "hudX", 5, Integer.MIN_VALUE, Integer.MAX_VALUE, "発言先表示のX座標");
            hudY = config.getInt(Configuration.CATEGORY_GENERAL, "hudY", 5, Integer.MIN_VALUE, Integer.MAX_VALUE, "発言先表示のY座標");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    public static void saveConfig() {
        config.get(Configuration.CATEGORY_GENERAL, "enableReceive", true,"受信チャットをローマ字変換するか").set(enableReceive);
        config.get(Configuration.CATEGORY_GENERAL, "enableSend", true,"送信チャットをローマ字変換するか").set(enableSend);
        config.get(Configuration.CATEGORY_GENERAL, "autesend", false,"送信時の自動チェンネル切り替え").set(autesend);
        config.get(Configuration.CATEGORY_GENERAL, "auteres", false,"受信の自動チェンネル切り替え").set(auteres);
        config.get(Configuration.CATEGORY_GENERAL, "channel", "all","デフォルトの発言先").set(channel);
        config.get(Configuration.CATEGORY_GENERAL, "hudX", 5, "発言先表示のX座標").set(hudX);
        config.get(Configuration.CATEGORY_GENERAL, "hudY", 5, "発言先表示のY座標").set(hudY);
        if (config.hasChanged()) {
            config.save();
        }
    }
    public static void syncConfig() {
        config.setCategoryComment(Configuration.CATEGORY_GENERAL, "Lunachat Like の設定");

        enableReceive = config.getBoolean("enableReceive",Configuration.CATEGORY_GENERAL,true,"受信チャットを変換する機能を切り替える");

        enableSend = config.getBoolean("enableSend",Configuration.CATEGORY_GENERAL,true,"送信チャットを変換する機能を切り替える");
        
        autesend = config.getBoolean("autesend",Configuration.CATEGORY_GENERAL,false,"チャット送信時に自動でチャンネルを切り替える");
        auteres = config.getBoolean("auteres",Configuration.CATEGORY_GENERAL,false,"受信時に自動でチャンネルを切り替える(全体を除く)");
        channel = config.getString("channel",Configuration.CATEGORY_GENERAL,"all","チャットを送信するチャンネル");
        
        hudX = config.getInt(Configuration.CATEGORY_GENERAL, "hudX", 5, Integer.MIN_VALUE, Integer.MAX_VALUE, "発言先表示のX座標");
        hudY = config.getInt(Configuration.CATEGORY_GENERAL, "hudY", 5, Integer.MIN_VALUE, Integer.MAX_VALUE, "発言先表示のY座標");

        if (config.hasChanged()) {
            config.save();
        }
    }

}