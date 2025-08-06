package com.lunachat_like.lunachat_like.chat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;

public class DictionaryManager {

    private static List<Map.Entry<String, String>> KANJI_ENTRIES; 
    private static final String FILE_NAME = "lunachat_dict.txt";

    public static void loadDictionary() {
        File configDir = new File(Minecraft.getMinecraft().mcDataDir, "config");
        File dictFile = new File(configDir, FILE_NAME);

        try {
            if (!dictFile.exists()) {
                configDir.mkdirs();
                createDefaultDictionary(dictFile);
            }
            readDictionary(dictFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultDictionary(File file) throws IOException {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            out.println("# Lunachat-like MOD 辞書ファイル");
            out.println("# 左側がひらがな、右側が変換後の漢字や表記");
            out.println("いずみ=泉");
            out.println("えたぺん=エタペン");
            out.println("さたん=サタン");
            out.println("くえすと=クエスト");
            out.println("せいげん=制限");
            out.println("みす=ミス");
            out.println("ほうこく=報告");
            out.println("てんせい=転生");
            out.println("でざてん=デザテン ");
            out.println("どらたに=ドラ谷");
            out.println("そうてん=蒼天");
            out.println("ぱりぃ=パリィ");
            out.println("ぷり=プリ");
            out.println("はた=旗");
            out.println("ばじ=バジ");
            out.println("ぶりてん=ブリテン");
            out.println("あむる=アムル");
        }
    }

    private static void readDictionary(File file) throws IOException {
        Map<String, String> tempMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || !line.contains("=")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    tempMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        }

        // 🔽 キーの長さ順（降順）でソート
        KANJI_ENTRIES = tempMap.entrySet()
            .stream()
            .sorted((a, b) -> Integer.compare(b.getKey().length(), a.getKey().length()))
            .collect(Collectors.toList());
    }

    public static String convertToKanji(String hiragana) {
        String result = hiragana;
        for (Map.Entry<String, String> entry : KANJI_ENTRIES) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
