package com.lunachat_like.lunachat_like.chat;

import java.lang.reflect.Field;

import org.lwjgl.input.Keyboard;

import com.lunachat_like.lunachat_like.config.LunachatLikeConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ChatSender {

    public static boolean enable = true;

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static Field inputField;
    static {
        try {
            // GuiChatクラスの "inputField" (srg名: "field_146415_a") を取得
            inputField = ReflectionHelper.findField(GuiChat.class, "inputField", "field_146415_a");
        } catch (ReflectionHelper.UnableToFindFieldException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGuiDraw(GuiScreenEvent.DrawScreenEvent event) {
    	LunachatLikeHUD.enable = false;
    	if(event.gui instanceof GuiChat )LunachatLikeHUD.enable = true;
    }
    // 他のMODより先に実行されるように、優先度をHIGHに設定
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onKeyInput(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        if(event.gui instanceof GuiChat ) {
        	LunachatLikeHUD.enable = true;
        }else LunachatLikeHUD.enable = false;
        if(event.gui instanceof GuiChat && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
        	LunachatLikeHUD.enable = false;
        }
    	// チャットGUIでEnterキーが押された時だけ処理
        if (!(event.gui instanceof GuiChat )|| Keyboard.getEventKey() != Keyboard.KEY_RETURN)return;
        try {
            // リフレクションでテキストフィールドを取得
            if(inputField==null)return;
            GuiTextField textField = (GuiTextField) inputField.get(event.gui);
            if(textField==null)return;
            final String currentMessage = textField.getText();

            // メッセージが空でなければ処理
            if (currentMessage == null || currentMessage.isEmpty())return;
            
            if(!LunachatLikeConfig.enableSend&&!currentMessage.startsWith("/")){
                String cmdpart = "";
                switch(LunachatLikeConfig.channel) {
                    case "p":
                   	case "group":
               		case "clan msg":
               			cmdpart = "/"+LunachatLikeConfig.channel+" ";
               			break;
               		case "all":
                   		cmdpart = "";
                   		break;
                }
               	if(LunachatLikeConfig.channel.startsWith("tell")) {
               		cmdpart = "/"+LunachatLikeConfig.channel+" ";
               	}
               	final String convertedMessage = cmdpart+currentMessage;
                textField.setText(convertedMessage);
               	return;
           	}
                
            if(currentMessage.startsWith("/")) {//チャット系列コマンドだったらテキスト部分だけ変換したい
            	if(!LunachatLikeConfig.enableSend)return;
                switch(currentMessage.trim()) {
                	case"/group":
                		if(LunachatLikeConfig.autesend) {
                			sendchat("§f[§aLCL§f]デフォルトの発言先をグループ(近距離)チャットに変更しました",mc.thePlayer);
                   			LunachatLikeConfig.channel = "group";
                   			LunachatLikeConfig.saveConfig();
                   		}
               			mc.displayGuiScreen(null);//空白でも空白が送信されるからキャンセルする
               			event.setCanceled(true);
               			return;
                	case"/all":
                		if(LunachatLikeConfig.autesend) {
               				sendchat("§f[§aLCL§f]デフォルトの発言先を全体チャットに変更しました",mc.thePlayer);
                   			LunachatLikeConfig.channel = "all";
                   			LunachatLikeConfig.saveConfig();
                   			if(currentMessage.trim().equals("/all")) {
                   				mc.displayGuiScreen(null);
                   				event.setCanceled(true);
                   				return;
                   			}
                   			final String convertedMessage = currentMessage.replaceAll("/all ", "");
                   			textField.setText(convertedMessage);
               			}
                   		return;
                	case"/p":
               			if(LunachatLikeConfig.autesend) {
               				sendchat("§f[§aLCL§f]デフォルトの発言先をパーティーチャットに変更しました",mc.thePlayer);
                   			LunachatLikeConfig.channel = "p";
               				LunachatLikeConfig.saveConfig();
                   		}
                   		return;
               		case "/clanmsg":
               			if(LunachatLikeConfig.autesend) {
               				sendchat("§f[§aLCL§f]デフォルトの発言先をクランチャットに変更しました",mc.thePlayer);
               				LunachatLikeConfig.channel = "clan msg";
                   			LunachatLikeConfig.saveConfig();
               			}
               			return;
               	}
               	
                final String[] parts = currentMessage.split(" ", 3);
                if(parts.length==0) {
               		return;
               	}else {
               		final String commandpart = parts[0].replace("/", "");
               		String chchangeto = "";
               		switch(commandpart){
           				case "all":
               			case "p":
               			case "group":
               			case "r":{
               				if (commandpart.equals("all")) {
               				    chchangeto = "全体";
               				} else if (commandpart.equals("p")) {
               				    chchangeto = "パーティー";
               				} else if (commandpart.equals("group")) {
               				    chchangeto = "グループ(近距離)";
               				}
                			if(LunachatLikeConfig.autesend&&!commandpart.equals(LunachatLikeConfig.channel)&&!commandpart.equals("r")) {
                				LunachatLikeConfig.channel = commandpart;
                				sendchat("§f[§aLCL§f]デフォルトの発言先を"+chchangeto+"チャットに変更しました",mc.thePlayer);
                				LunachatLikeConfig.saveConfig();
                			}
               				if(parts.length>=2) {
               					String messagepart = parts[1];
                   				if(parts.length==3) {//メッセージに空白があったらそれも考慮しよう
                   					messagepart = messagepart + " " + parts[2];
               					}
               					final String kanjimessage = texttoKanji(messagepart);
               					if(kanjimessage==null||kanjimessage.isEmpty())return;
               					String convertedMessage = parts[0] + " " +messagepart + " ("+kanjimessage+")";
                   				if(convertedMessage.length()>=100) {
                   					convertedMessage = parts[0] + " " +"("+kanjimessage+")";
                   				}
                    			if(commandpart.equals("all")) {
                    				convertedMessage = convertedMessage.replaceAll("/all ", "");
                   				}
                   				if(convertedMessage.equals("")) {
                   					event.setCanceled(true);
               						return;
               					}
                   				textField.setText(convertedMessage);
               				}
               			}
               			return;
               			case "clan":
               				if(parts.length>=2&&parts[1].equals("msg")) {
               					if(LunachatLikeConfig.autesend&&!LunachatLikeConfig.channel.equals("clan msg")) {
                   					LunachatLikeConfig.channel = "clan msg";
                   					sendchat("§f[§aLCL§f]デフォルトの発言先をクランチャットに変更しました",mc.thePlayer);
                   					LunachatLikeConfig.saveConfig();
                   				}
               					if(parts.length==3) {
           							final String messagepart = parts[2];
                   					final String kanjimessage = texttoKanji(messagepart);
                       				if(kanjimessage==null||kanjimessage.isEmpty())return;
                       				String convertedMessage = parts[0] + " " +messagepart + " ("+kanjimessage+")";
                       				if(convertedMessage.length()>=100) {
                       					convertedMessage = parts[0] + " " + parts[1] +"("+kanjimessage+")";
                   					}
                       					textField.setText(convertedMessage);
               						}
               				}return;
               			case "tell":
               				if(parts.length>=2) {
               					if(LunachatLikeConfig.autesend&&!LunachatLikeConfig.channel.equals("tell "+parts[1])) {
                    				LunachatLikeConfig.channel = "tell "+parts[1];
                    				sendchat("§f[§aLCL§f]デフォルトの発言先を"+parts[1]+"との個人チャットに変更しました",mc.thePlayer);
                    				LunachatLikeConfig.saveConfig();
                    			}
                				if(parts.length==3) {
                					final String messagepart = parts[2];
                   					final String kanjimessage = texttoKanji(messagepart);
                       				if(kanjimessage==null||kanjimessage.isEmpty())return;
                       				String convertedMessage = parts[0] + " " +parts[1]+" " +messagepart + " ("+kanjimessage+")";
                       				if(convertedMessage.length()>=100) {
                       					convertedMessage = parts[0] + " " + parts[1] +"("+kanjimessage+")";
                       				}
                       				textField.setText(convertedMessage);
               					}
                   			return;
               			}
               		}
               	}
            }else {
            //通常チャットにもコマンド付加があるかも
            String cmdpart = "";
            switch(LunachatLikeConfig.channel) {
               	case "p":
           		case "group":
           		case "clan msg":
           			cmdpart = "/"+LunachatLikeConfig.channel+" ";
               		break;
                case "all":
                	cmdpart = "";
           	}
           	if(LunachatLikeConfig.channel.startsWith("tell")) {
           		cmdpart = "/"+LunachatLikeConfig.channel+" ";
            }
            final String kanjimessage = texttoKanji(currentMessage);
            if(kanjimessage==null||kanjimessage.isEmpty()) {
               	final String cmdtext = cmdpart+currentMessage;
           		textField.setText(cmdtext);
           		return;
           	}
            String convertedMessage =cmdpart + currentMessage +" (" + kanjimessage+")";
           	if(convertedMessage.length()>=100) {
				convertedMessage =cmdpart+"("+kanjimessage+")";
			}
            //長いテキストは変換後だけ表示にした
            //どうやら送信文字数の上限は100文字らしい                	

            // テキストフィールドのテキストを直接書き換える
            textField.setText(convertedMessage);
            }
        
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    
    //ローマ字テキストをStringで入れると漢字変換されて返ってくる(無かったらnull)
    private static String texttoKanji(final String messagepart) {
    	if(messagepart==null||messagepart.isEmpty()||ChatListener.containsJapanese(messagepart))return null;
		final String jpmessage = ChatListener.toHiragana(messagepart);
		final String kanjimessage = DictionaryManager.convertToKanji(jpmessage);
		if(kanjimessage==null||kanjimessage.isEmpty()||kanjimessage.equals("")||kanjimessage.equals(messagepart.toLowerCase()))return null;
		return kanjimessage;
    }
    private static void sendchat(String text,EntityPlayerSP thePlayer) {
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    	return;
    }
}
