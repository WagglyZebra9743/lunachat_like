package com.lunachat_like.lunachat_like.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class LunachatLikeConfig {
	
	
	public static final String CATEGORY_GENERAL = "general";
	private static final String DEFAULT_VERSIONCHECK_TEXT = "バージョン情報を自動で確認するかどうか";
    private static final String DEFAULT_SENDMCID_TEXT = "バージョン確認時にmcidを送信するか";

    public static boolean enableReceive = true;
    public static boolean enableSend = true;
    public static boolean autesend = true;
    public static boolean auteres = true;
    public static String channel = "all";
    public static int hudX = 5;
    public static int hudY = 5;
    public static boolean AutoVersionCheck = true;
    public static boolean SendMCID = false;

    public static Configuration config;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);
        config.load();
        
        enableReceive = config.get(CATEGORY_GENERAL, "enableReceive" , true ,"受信チャットをローマ字変換するか").getBoolean();
        enableSend = config.get(CATEGORY_GENERAL, "enableSend", true,"送信チャットをローマ字変換するか").getBoolean();
        
        autesend = config.get(CATEGORY_GENERAL, "autesend", false,"送信時の自動チャンネル切り替え").getBoolean();
        auteres = config.get(CATEGORY_GENERAL, "auteres", false,"受信時の自動チャンネル切り替え").getBoolean();
        
        channel = config.get(CATEGORY_GENERAL, "channel", "all","デフォルトの発言先").getString();
        hudX = config.get(CATEGORY_GENERAL, "hudX", 5, "発言先表示のX座標").getInt();
        hudY = config.get(CATEGORY_GENERAL, "hudY", 5, "発言先表示のY座標").getInt();
        AutoVersionCheck = config.get(CATEGORY_GENERAL, "AutoVersionCheck", true, DEFAULT_VERSIONCHECK_TEXT).getBoolean();
        SendMCID = config.get(CATEGORY_GENERAL, "SendMCID", false, DEFAULT_SENDMCID_TEXT).getBoolean();

        
        if (config.hasChanged()) {
            config.save();
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
        config.get(Configuration.CATEGORY_GENERAL, "AutoVersionCheck", true,DEFAULT_VERSIONCHECK_TEXT).set(AutoVersionCheck);
        config.get(Configuration.CATEGORY_GENERAL, "SendMCID", false,DEFAULT_SENDMCID_TEXT).set(SendMCID);
        if (config.hasChanged()) {
            config.save();
        }
    }
    public static Configuration getConfig() {
        return config;
    }
}