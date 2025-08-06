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
            
            //スキル関連
            out.println("# スキル関連");
            out.println("いずみ=泉");
            out.println("ぱりぃ=パリィ");
            out.println("かくせい=覚醒");
            out.println("ぼるけ=ボルケ");
            out.println("びょう=秒");
            
            //募集関連
            out.println("# 募集関連");
            out.println("ぼしゅう=募集");
            out.println("ぼすまえ=ボス前");
            out.println("きてないひと=来てない人");
            out.println("せいげん=制限");
            out.println("みす=ミス");
            out.println("ほうこく=報告");
            out.println("なぐり=殴り");
            out.println("ぷり=プリ");
            out.println("ばたし=バタシ");
            
            //ダンジョン名関連
            out.println("# ダンジョン名関連");
            out.println("だんじょん=ダンジョン");
            out.println("でざてん=デザテン ");
            out.println("どらたに=ドラ谷");
            out.println("そうてん=蒼天");
            out.println("じゅんぱく=純白");
            out.println("やくさい=厄災");
            out.println("にゃる=ニャル");
            out.println("とわ=トワ");
            out.println("としょかん=図書館");
            out.println("ばじ=バジ");
            out.println("さんく=サンク");
            out.println("しれん=試練");
            out.println("かぜ=風");
            out.println("みず=水");
            out.println("だいち=大地");
            out.println("とこやみ=常闇");
            out.println("えいどり=エイドリ");
            out.println("ふういん=封印");
            out.println("めいかい=冥界");
            out.println("ついおく=追憶");
            out.println("ここり=ココリ");
            
            //武器関連
            out.println("# 武器関連");
            out.println("はた=旗");
            out.println("ぶりてん=ブリテン");
            out.println("あむる=アムル");
            out.println("えたぺん=エタペン");
            out.println("さたん=サタン");
            out.println("しゃるる=シャルル");
            out.println("めいよけん=名誉剣");
            out.println("ろあ=RoA");
            out.println("ほね=骨");
            out.println("せいけん=聖剣");
            out.println("めいけん=冥剣");
            out.println("きのこけん=キノコ剣");
            
            //乗り物関連
            out.println("# 乗り物関連");
            out.println("ほねうま=骨馬");
            out.println("ばしゃ=馬車");
            out.println("ふね=船");
            out.println("ひくうてい=飛空艇");
            out.println("ひこうせん=飛行船");
            
            //地名関連
            out.println("# 地名関連");
            out.println("えるどーる=エルドール");
            out.println("えるど=エルド");
            out.println("たりばんず=タリバンズ");
            out.println("しゅれりっつ=シュレリッツ");
            out.println("りぐへるむ=リグヘルム");
            out.println("らじゃすたん=ラジャスタン");
            out.println("いろえる=イロエル");
            out.println("びるにす=ビルニス");
            out.println("めりある=メリアル");
            out.println("じょうさい=城塞");
            out.println("ぱんぷにー=パンプニー");
            out.println("はるしおん=ハルシオン");
            out.println("ゔぇねみあ=ヴェネミア");
            out.println("めるとりあ=メルトリア");
            out.println("おうこく=王国");
            out.println("ふぇるとん=フェルトン");
            out.println("すのーりぃ=スノーリィ");
            out.println("あるのーす=アルノース");
            out.println("べるふぉーと=ベルフォート");
            out.println("くらんはうす=クランハウス");
            
            //その他
            out.println("# その他");
            out.println("てんせい=転生");
            out.println("くえすと=クエスト");
            out.println("くらくえ=クラクエ");
            out.println("けいけんち=経験値");
            out.println("まこん=魔混");
            out.println("ばぐ=バグ");
            out.println("ぼてん=補填");
            out.println("どうが=動画");
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
