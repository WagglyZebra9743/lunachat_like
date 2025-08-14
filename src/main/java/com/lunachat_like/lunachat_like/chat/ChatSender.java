package com.lunachat_like.lunachat_like.chat;

import java.lang.reflect.Field;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ChatSender {

    public static boolean enable = true;

    private static Field inputField;
    static {
        try {
            // GuiChatクラスの "inputField" (srg名: "field_146415_a") を取得
            inputField = ReflectionHelper.findField(GuiChat.class, "inputField", "field_146415_a");
        } catch (ReflectionHelper.UnableToFindFieldException e) {
            e.printStackTrace();
        }
    }

    // 他のMODより先に実行されるように、優先度をHIGHに設定
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onKeyInput(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        // チャットGUIでEnterキーが押された時だけ処理
        if (event.gui instanceof GuiChat && Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
        	if(!enable)return;
            try {
                // リフレクションでテキストフィールドを取得
                GuiTextField textField = (GuiTextField) inputField.get(event.gui);
                String currentMessage = textField.getText();

                // メッセージが空でなければ処理
                if (currentMessage != null && !currentMessage.isEmpty()&&!currentMessage.startsWith("/")) {

                	
                	if(ChatListener.containsJapanese(currentMessage))return;
                	if(currentMessage.startsWith("gg")||currentMessage.startsWith("gf")||currentMessage.startsWith("nc"))return;
                	
                    // ローマ字変換処理
                	String jpmessage = ChatListener.toHiragana(currentMessage);//ローマ字→ひらがな
                	String kanjimessage = DictionaryManager.convertToKanji(jpmessage);// ひらがな→単語変換
                    if(kanjimessage==null||kanjimessage.isEmpty()||kanjimessage==""||kanjimessage.equals(currentMessage))return;
                	String convertedMessage = currentMessage +" (" + kanjimessage+")"; 
                	

                    // テキストフィールドのテキストを直接書き換える
                    textField.setText(convertedMessage);

                    // ここではイベントをキャンセルしない！
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
