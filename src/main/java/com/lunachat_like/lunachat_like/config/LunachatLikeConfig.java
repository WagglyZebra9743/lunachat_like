package com.lunachat_like.lunachat_like.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class LunachatLikeConfig {

    public static boolean enableReceive = true;
    public static boolean enableSend = true;

    public static Configuration config;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);

        try {
            config.load();

            enableReceive = config.getBoolean(
                    "enableReceive",
                    Configuration.CATEGORY_GENERAL,
                    true,
                    "受信チャットをローマ字変換するか"
            );

            enableSend = config.getBoolean(
                    "enableSend",
                    Configuration.CATEGORY_GENERAL,
                    true,
                    "送信チャットをローマ字変換するか"
            );

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    public static void saveConfig() {
        config.get(Configuration.CATEGORY_GENERAL, "enableReceive", enableReceive).set(enableReceive);
        config.get(Configuration.CATEGORY_GENERAL, "enableSend", enableSend).set(enableSend);
        if (config.hasChanged()) {
            config.save();
        }
    }
    public static void syncConfig() {
        config.setCategoryComment(Configuration.CATEGORY_GENERAL, "Lunachat Like の設定");

        enableReceive = config.getBoolean(
            "enableReceive",
            Configuration.CATEGORY_GENERAL,
            enableReceive,
            "受信チャットを変換する機能を切り替える"
        );

        enableSend = config.getBoolean(
            "enableSend",
            Configuration.CATEGORY_GENERAL,
            enableSend,
            "送信チャットを変換する機能を切り替える"
        );

        if (config.hasChanged()) {
            config.save();
        }
    }

}