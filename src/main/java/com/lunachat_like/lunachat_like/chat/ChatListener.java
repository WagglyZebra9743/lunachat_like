package com.lunachat_like.lunachat_like.chat;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lunachat_like.lunachat_like.config.LunachatLikeConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatListener {
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static boolean debugmode = false;
	private static final Map<String, String> ROMAJI_MAP = new LinkedHashMap<>();

    static {
    	//4文字(っが前に入る3文字)
    	ROMAJI_MAP.put("kkya", "っきゃ"); ROMAJI_MAP.put("kkyu", "っきゅ"); ROMAJI_MAP.put("kkyo", "っきょ");
        ROMAJI_MAP.put("ggya", "っぎゃ"); ROMAJI_MAP.put("ggyu", "っぎゅ"); ROMAJI_MAP.put("ggyo", "っぎょ");
        ROMAJI_MAP.put("sshi", "っし");
        ROMAJI_MAP.put("ssya", "っしゃ"); ROMAJI_MAP.put("ssyu", "っしゅ"); ROMAJI_MAP.put("ssyo", "っしょ");
        ROMAJI_MAP.put("ssye", "っしぇ");
        ROMAJI_MAP.put("zzya", "っじゃ"); ROMAJI_MAP.put("zzyu", "っじゅ"); ROMAJI_MAP.put("zzyo", "っじょ");
        ROMAJI_MAP.put("jjya", "っじゃ"); ROMAJI_MAP.put("jjyu", "っじゅ"); ROMAJI_MAP.put("jjyo", "っじょ");
        ROMAJI_MAP.put("cchi", "っち");   ROMAJI_MAP.put("ttsu", "っつ");
        ROMAJI_MAP.put("ccha", "っちゃ"); ROMAJI_MAP.put("cchu", "っちゅ"); ROMAJI_MAP.put("ccho", "っちょ");
        ROMAJI_MAP.put("ttya", "っちゃ"); ROMAJI_MAP.put("ttyu", "っちゅ"); ROMAJI_MAP.put("ttyo", "っちょ");
        ROMAJI_MAP.put("ddya", "っぢゃ"); ROMAJI_MAP.put("ddyu", "っぢゅ"); ROMAJI_MAP.put("ddyo", "っぢょ");
        ROMAJI_MAP.put("ttsa", "っつぁ"); ROMAJI_MAP.put("ttsi", "っつぃ"); ROMAJI_MAP.put("ttse", "っつぇ"); ROMAJI_MAP.put("ttso", "っつぉ");
        ROMAJI_MAP.put("nnya", "っにゃ"); ROMAJI_MAP.put("nnyu", "っにゅ"); ROMAJI_MAP.put("nnyo", "っにょ");
        ROMAJI_MAP.put("hhya", "っひゃ"); ROMAJI_MAP.put("hhyu", "っひゅ"); ROMAJI_MAP.put("hhyo", "っひょ");
        ROMAJI_MAP.put("bbya", "っびゃ"); ROMAJI_MAP.put("bbyu", "っびゅ"); ROMAJI_MAP.put("bbyo", "っびょ");
        ROMAJI_MAP.put("ppya", "っぴゃ"); ROMAJI_MAP.put("ppyu", "っぴゅ"); ROMAJI_MAP.put("ppyo", "っぴょ");
        ROMAJI_MAP.put("mmya", "っみゃ"); ROMAJI_MAP.put("mmyu", "っみゅ"); ROMAJI_MAP.put("mmyo", "っみょ");
        ROMAJI_MAP.put("rrya", "っりゃ"); ROMAJI_MAP.put("rryu", "っりゅ"); ROMAJI_MAP.put("rryo", "っりょ");
        
        //一部の特殊単語
        ROMAJI_MAP.put("boss", "ボス"); ROMAJI_MAP.put("wave", "ウェーブ"); ROMAJI_MAP.put("rush", "ラッシュ");
        
        // 3文字
        ROMAJI_MAP.put("kya", "きゃ"); ROMAJI_MAP.put("kyu", "きゅ"); ROMAJI_MAP.put("kyo", "きょ");
        ROMAJI_MAP.put("gya", "ぎゃ"); ROMAJI_MAP.put("gyu", "ぎゅ"); ROMAJI_MAP.put("gyo", "ぎょ");
        ROMAJI_MAP.put("shi", "し");
        ROMAJI_MAP.put("sya", "しゃ"); ROMAJI_MAP.put("syu", "しゅ"); ROMAJI_MAP.put("syo", "しょ");
        ROMAJI_MAP.put("sha", "しゃ"); ROMAJI_MAP.put("shu", "しゅ"); ROMAJI_MAP.put("sho", "しょ");
        ROMAJI_MAP.put("sye", "しぇ");
        ROMAJI_MAP.put("zya", "じゃ"); ROMAJI_MAP.put("zyu", "じゅ"); ROMAJI_MAP.put("zyo", "じょ");
        ROMAJI_MAP.put("jya", "じゃ"); ROMAJI_MAP.put("jyu", "じゅ"); ROMAJI_MAP.put("jyo", "じょ");
        ROMAJI_MAP.put("chi", "ち");   ROMAJI_MAP.put("tsu", "つ");
        ROMAJI_MAP.put("cha", "ちゃ"); ROMAJI_MAP.put("chu", "ちゅ"); ROMAJI_MAP.put("cho", "ちょ");
        ROMAJI_MAP.put("tya", "ちゃ"); ROMAJI_MAP.put("tyu", "ちゅ"); ROMAJI_MAP.put("tyo", "ちょ");
        ROMAJI_MAP.put("dya", "ぢゃ"); ROMAJI_MAP.put("dyu", "ぢゅ"); ROMAJI_MAP.put("dyo", "ぢょ");
        ROMAJI_MAP.put("tsa", "つぁ"); ROMAJI_MAP.put("tsi", "つぃ"); ROMAJI_MAP.put("tse", "つぇ"); ROMAJI_MAP.put("tso", "つぉ");
        ROMAJI_MAP.put("nya", "にゃ"); ROMAJI_MAP.put("nyu", "にゅ"); ROMAJI_MAP.put("nyo", "にょ");
        ROMAJI_MAP.put("hya", "ひゃ"); ROMAJI_MAP.put("hyu", "ひゅ"); ROMAJI_MAP.put("hyo", "ひょ");
        ROMAJI_MAP.put("bya", "びゃ"); ROMAJI_MAP.put("byu", "びゅ"); ROMAJI_MAP.put("byo", "びょ");
        ROMAJI_MAP.put("pya", "ぴゃ"); ROMAJI_MAP.put("pyu", "ぴゅ"); ROMAJI_MAP.put("pyo", "ぴょ");
        ROMAJI_MAP.put("mya", "みゃ"); ROMAJI_MAP.put("myu", "みゅ"); ROMAJI_MAP.put("myo", "みょ");
        ROMAJI_MAP.put("rya", "りゃ"); ROMAJI_MAP.put("ryu", "りゅ"); ROMAJI_MAP.put("ryo", "りょ");
        
        //3文字(っが前に入る2文字)
        ROMAJI_MAP.put("kka", "っか"); ROMAJI_MAP.put("kki", "っき"); ROMAJI_MAP.put("kku", "っく"); ROMAJI_MAP.put("kke", "っけ"); ROMAJI_MAP.put("kko", "っこ");
        ROMAJI_MAP.put("gga", "っが"); ROMAJI_MAP.put("ggi", "っぎ"); ROMAJI_MAP.put("ggu", "っぐ"); ROMAJI_MAP.put("gge", "っげ"); ROMAJI_MAP.put("ggo", "っご");
        ROMAJI_MAP.put("ssa", "っさ"); ROMAJI_MAP.put("ssi", "っし"); ROMAJI_MAP.put("ssu", "っす"); ROMAJI_MAP.put("sse", "っせ"); ROMAJI_MAP.put("sso", "っそ");
        ROMAJI_MAP.put("zza", "っざ"); ROMAJI_MAP.put("zzi", "っじ"); ROMAJI_MAP.put("zzu", "っず"); ROMAJI_MAP.put("zze", "っぜ"); ROMAJI_MAP.put("zzo", "っぞ");
        ROMAJI_MAP.put("jja","っじゃ");ROMAJI_MAP.put("jju","っじゅ");ROMAJI_MAP.put("jjo", "っじょ");
        ROMAJI_MAP.put("jje", "っじぇ");
        ROMAJI_MAP.put("tta", "った"); ROMAJI_MAP.put("tti", "っち"); ROMAJI_MAP.put("ttu", "っつ"); ROMAJI_MAP.put("tte", "って"); ROMAJI_MAP.put("tto", "っと");
        ROMAJI_MAP.put("dda", "っだ"); ROMAJI_MAP.put("ddi", "っぢ"); ROMAJI_MAP.put("ddu", "っづ"); ROMAJI_MAP.put("dde", "っで"); ROMAJI_MAP.put("ddo", "っど");
        							 	ROMAJI_MAP.put("jji", "っじ");
        ROMAJI_MAP.put("ppa", "っぱ"); ROMAJI_MAP.put("ppi", "っぴ"); ROMAJI_MAP.put("ppu", "っぷ"); ROMAJI_MAP.put("ppe", "っぺ"); ROMAJI_MAP.put("ppo", "っぽ");
        ROMAJI_MAP.put("bba", "っば"); ROMAJI_MAP.put("bbi", "っび"); ROMAJI_MAP.put("bbu", "っぶ"); ROMAJI_MAP.put("bbe", "っべ"); ROMAJI_MAP.put("bbo", "っぼ");
        ROMAJI_MAP.put("hha", "っは"); ROMAJI_MAP.put("hhi", "っひ"); ROMAJI_MAP.put("hhu", "っふ"); ROMAJI_MAP.put("hhe", "っへ"); ROMAJI_MAP.put("hho", "っほ");
        														  		ROMAJI_MAP.put("ffu", "っふ");
        ROMAJI_MAP.put("ffa","っふぁ");ROMAJI_MAP.put("ffi","っふぃ");ROMAJI_MAP.put("ffe","っふぇ");ROMAJI_MAP.put("ffo", "っふぉ");
        ROMAJI_MAP.put("mma", "っま"); ROMAJI_MAP.put("mmi", "っみ"); ROMAJI_MAP.put("mmu", "っむ"); ROMAJI_MAP.put("mme", "っめ"); ROMAJI_MAP.put("mmo", "っも");
        ROMAJI_MAP.put("yya", "っや"); ROMAJI_MAP.put("yyu", "っゆ"); ROMAJI_MAP.put("yyo", "っよ");
        ROMAJI_MAP.put("rra", "っら"); ROMAJI_MAP.put("rri", "っり"); ROMAJI_MAP.put("rru", "っる"); ROMAJI_MAP.put("rre", "っれ"); ROMAJI_MAP.put("rro", "っろ");
        ROMAJI_MAP.put("wwa", "っわ"); ROMAJI_MAP.put("wwo", "っを"); 
        
        ROMAJI_MAP.put("qqa", "っくぁ"); ROMAJI_MAP.put("qqi", "っくぃ"); ROMAJI_MAP.put("qqe", "っくぇ"); ROMAJI_MAP.put("qqo", "っくぉ");
        ROMAJI_MAP.put("wwi", "っうぃ"); ROMAJI_MAP.put("wwe", "っうぇ"); 
        ROMAJI_MAP.put("vva", "っゔぁ");
        ROMAJI_MAP.put("vvi", "っゔぃ");
        ROMAJI_MAP.put("vvu", "っゔ");
        ROMAJI_MAP.put("vve", "っゔぇ");
        ROMAJI_MAP.put("vvo", "っゔぉ");
        
        //3文字(小さくなるやつ)
        ROMAJI_MAP.put("xtu","っ"); ROMAJI_MAP.put("ltu","っ");
        ROMAJI_MAP.put("xya","ゃ"); ROMAJI_MAP.put("xyu","ゅ"); ROMAJI_MAP.put("xyo","ょ"); 
        ROMAJI_MAP.put("lya","ゃ"); ROMAJI_MAP.put("lyu","ゅ"); ROMAJI_MAP.put("lyo","ょ"); 
        
      //一部特殊単語
        ROMAJI_MAP.put("dia", "ダイヤ"); 

        //2文字(小さくなるやつ)
        ROMAJI_MAP.put("xa","ぁ"); ROMAJI_MAP.put("xi","ぃ"); ROMAJI_MAP.put("xu","ぅ"); ROMAJI_MAP.put("xe","ぇ"); ROMAJI_MAP.put("xo","ぉ");
        ROMAJI_MAP.put("la","ぁ"); ROMAJI_MAP.put("li","ぃ"); ROMAJI_MAP.put("lu","ぅ"); ROMAJI_MAP.put("le","ぇ"); ROMAJI_MAP.put("lo","ぉ");
        
        
        
     // 2文字
        ROMAJI_MAP.put("ka", "か"); ROMAJI_MAP.put("ki", "き"); ROMAJI_MAP.put("ku", "く"); ROMAJI_MAP.put("ke", "け"); ROMAJI_MAP.put("ko", "こ");
        ROMAJI_MAP.put("ga", "が"); ROMAJI_MAP.put("gi", "ぎ"); ROMAJI_MAP.put("gu", "ぐ"); ROMAJI_MAP.put("ge", "げ"); ROMAJI_MAP.put("go", "ご");
        ROMAJI_MAP.put("sa", "さ"); ROMAJI_MAP.put("si", "し"); ROMAJI_MAP.put("su", "す"); ROMAJI_MAP.put("se", "せ"); ROMAJI_MAP.put("so", "そ");
        ROMAJI_MAP.put("za", "ざ"); ROMAJI_MAP.put("zi", "じ"); ROMAJI_MAP.put("zu", "ず"); ROMAJI_MAP.put("ze", "ぜ"); ROMAJI_MAP.put("zo", "ぞ");
        ROMAJI_MAP.put("ja","じゃ");ROMAJI_MAP.put("ju","じゅ");ROMAJI_MAP.put("jo", "じょ");
        ROMAJI_MAP.put("je", "じぇ");
        ROMAJI_MAP.put("ta", "た"); ROMAJI_MAP.put("ti", "ち"); ROMAJI_MAP.put("tu", "つ"); ROMAJI_MAP.put("te", "て"); ROMAJI_MAP.put("to", "と");
        ROMAJI_MAP.put("da", "だ"); ROMAJI_MAP.put("di", "ぢ"); ROMAJI_MAP.put("du", "づ"); ROMAJI_MAP.put("de", "で"); ROMAJI_MAP.put("do", "ど");
        							 ROMAJI_MAP.put("ji", "じ");
        ROMAJI_MAP.put("na", "な"); ROMAJI_MAP.put("ni", "に"); ROMAJI_MAP.put("nu", "ぬ"); ROMAJI_MAP.put("ne", "ね"); ROMAJI_MAP.put("no", "の");
        ROMAJI_MAP.put("pa", "ぱ"); ROMAJI_MAP.put("pi", "ぴ"); ROMAJI_MAP.put("pu", "ぷ"); ROMAJI_MAP.put("pe", "ぺ"); ROMAJI_MAP.put("po", "ぽ");
        ROMAJI_MAP.put("ba", "ば"); ROMAJI_MAP.put("bi", "び"); ROMAJI_MAP.put("bu", "ぶ"); ROMAJI_MAP.put("be", "べ"); ROMAJI_MAP.put("bo", "ぼ");
        ROMAJI_MAP.put("ha", "は"); ROMAJI_MAP.put("hi", "ひ"); ROMAJI_MAP.put("hu", "ふ"); ROMAJI_MAP.put("he", "へ"); ROMAJI_MAP.put("ho", "ほ");
        														  ROMAJI_MAP.put("fu", "ふ");
        ROMAJI_MAP.put("fa","ふぁ");ROMAJI_MAP.put("fi","ふぃ");ROMAJI_MAP.put("fe","ふぇ");ROMAJI_MAP.put("fo", "ふぉ");
        ROMAJI_MAP.put("ma", "ま"); ROMAJI_MAP.put("mi", "み"); ROMAJI_MAP.put("mu", "む"); ROMAJI_MAP.put("me", "め"); ROMAJI_MAP.put("mo", "も");
        ROMAJI_MAP.put("ya", "や"); ROMAJI_MAP.put("yu", "ゆ"); ROMAJI_MAP.put("yo", "よ");
        ROMAJI_MAP.put("ra", "ら"); ROMAJI_MAP.put("ri", "り"); ROMAJI_MAP.put("ru", "る"); ROMAJI_MAP.put("re", "れ"); ROMAJI_MAP.put("ro", "ろ");
        ROMAJI_MAP.put("wa", "わ"); ROMAJI_MAP.put("wo", "を");
        ROMAJI_MAP.put("nn", "ん"); 
        
        ROMAJI_MAP.put("qa", "くぁ"); ROMAJI_MAP.put("qi", "くぃ"); ROMAJI_MAP.put("qe", "くぇ"); ROMAJI_MAP.put("qo", "くぉ");
        ROMAJI_MAP.put("wi", "うぃ"); ROMAJI_MAP.put("we", "うぇ"); 
        ROMAJI_MAP.put("va", "ゔぁ");
        ROMAJI_MAP.put("vi", "ゔぃ");
        ROMAJI_MAP.put("vu", "ゔ");
        ROMAJI_MAP.put("ve", "ゔぇ");
        ROMAJI_MAP.put("vo", "ゔぉ");
        
        
        // 1文字
        
        ROMAJI_MAP.put("n", "ん");
        ROMAJI_MAP.put("a", "あ");
        ROMAJI_MAP.put("i", "い");
        ROMAJI_MAP.put("u", "う");
        ROMAJI_MAP.put("e", "え");
        ROMAJI_MAP.put("o", "お");
        ROMAJI_MAP.put("-", "ー");
        
    }

    public static String toHiragana(String input) {
        String lower = input.toLowerCase();
        String result = lower;

        for (Map.Entry<String, String> entry : ROMAJI_MAP.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }

        return result;
    }
	public static boolean enable = true;
 // 日本語判定（ひらがな、カタカナ、漢字が含まれているか）
    public static boolean containsJapanese(String text) {
        return text.matches(".*[\\u3040-\\u30FF\\u4E00-\\u9FFF\\uFF65-\\uFF9F].*");
    }

    // カラーコード判定（Minecraftカラーコード§が含まれるか）
    private boolean containsColorCode(String text) {
        return text.contains("§");
    }
    
    public static String message = "";
    private static String messagePart = "";
    @SubscribeEvent
    public void ChatReceived(ClientChatReceivedEvent event) {
    	String colormessage = event.message.getFormattedText();
    	if(LunachatLikeConfig.auteres) {
    		if(colormessage.startsWith("§r§a[P]")&&!LunachatLikeConfig.channel.equals("p")) {
        		LunachatLikeConfig.channel = "p";
        		sendchat("§f[§aLCL§f]デフォルトの発言先をパーティーチャットに変更しました",mc.thePlayer);
        		LunachatLikeConfig.saveConfig();
        	}
    		if(colormessage.startsWith("§r§b[ClanChat]")&&!LunachatLikeConfig.channel.equals("clan msg")) {
    			LunachatLikeConfig.channel = "clan msg";
    			sendchat("§f[§aLCL§f]デフォルトの発言先をクランチャットに変更しました",mc.thePlayer);
    			LunachatLikeConfig.saveConfig();
    		}
    		if(colormessage.startsWith("§r§a[GroupChat]")&&!LunachatLikeConfig.channel.equals("group")) {
    			LunachatLikeConfig.channel = "group";
    			sendchat("§f[§aLCL§f]デフォルトの発言先をグループ(近距離)チャットに変更しました",mc.thePlayer);
    			LunachatLikeConfig.saveConfig();
    		}
    		if(colormessage.contains("からささやかれました")&&colormessage.endsWith("§r")&&colormessage.startsWith("§r§o§7")) {
    			String regex = "§r§o§7(.*?)からささやかれました";
    			Pattern pattern = Pattern.compile(regex);
    			Matcher matcher = pattern.matcher(colormessage);
    			if (matcher.find()) {
    	            // 一致した部分の中から、1番目のグループ（(.*?)の部分）を取得
    	            String mcid = matcher.group(1);
    	            if(!LunachatLikeConfig.channel.equals("tell "+mcid)) {
    	            	LunachatLikeConfig.channel = "tell "+mcid;
        	            sendchat("§f[§aLCL§f]デフォルトの発言先を"+mcid+"との個人チャットに変更しました",mc.thePlayer);
        	            LunachatLikeConfig.saveConfig();
    	            }
    	        }else {
    	        	System.out.println("mcid not found");
    	        }
    		}
    	}
    	if(!LunachatLikeConfig.enableReceive)return;
    	message = event.message.getUnformattedText(); // 色コードや装飾を除去したテキスト
    	if(debugmode)System.out.println("[LCL]チャットを取得:"+event.message.getFormattedText());
    	
    	//現状見つけたエラーメッセージを除外しようとしている
    	if((colormessage.startsWith("§r§b")&&!colormessage.startsWith("§r§b[ClanChat]"))||colormessage.startsWith("§c"))return;
    	//通常チャット パーティーチャット 個人チャット クランチャット以外なら終わり
    	if(!colormessage.contains("§r§f : ")&&!colormessage.contains("§r§f: ")&&!colormessage.contains("§r§o§7")&&!colormessage.contains("§r§7")&&!colormessage.contains("た: ")&&!colormessage.contains("§r§b[ClanChat]")&&!colormessage.startsWith("§a[GroupChat]")) {
    		return;
    	}
		
    	//変数初期化
    	String kanjimessage = "";
    	String jpmessage = "";
    	
    	//全てのチャットに共通して: の後がチャットメッセージである
    	int colonIndex = message.indexOf(": ");//まずそれが何文字目にあるかを取得する
        if (colonIndex != -1 && colonIndex + 2 < message.length()) {//その後ろを格納する
            messagePart = message.substring(colonIndex + 2);
        }else return;//: がないなら終わり
        
        //null,空,日本語あり,色コードあり(rをのぞく)なら終わり
        if(messagePart==null||messagePart.isEmpty()||containsJapanese(messagePart)||(containsColorCode(messagePart)&&!colormessage.endsWith("§r"))) {
        	return;
        }
		
		//ローマ字→ひらがな→漢字変換
    	jpmessage = toHiragana(messagePart);
    	kanjimessage = DictionaryManager.convertToKanji(jpmessage);
    	
    	//漢字変換したものと元メッセージを小文字化したものが同じなら終わり
    	if(kanjimessage==null||kanjimessage.isEmpty()||kanjimessage.equals("")||kanjimessage.equals(messagePart.toLowerCase()))return;
    	
    	//チャットの後ろに付け加える
    	event.message.appendSibling(new ChatComponentText(" §6(" + kanjimessage + ")"));

        }
    private static void sendchat(String text,EntityPlayerSP thePlayer) {
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    	return;
    }
}
