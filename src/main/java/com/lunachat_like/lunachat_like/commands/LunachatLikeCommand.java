package com.lunachat_like.lunachat_like.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lunachat_like.lunachat_like.chat.ChatListener;
import com.lunachat_like.lunachat_like.chat.ChatSender;
import com.lunachat_like.lunachat_like.chat.DictionaryManager;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;



public class LunachatLikeCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "jp";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("jp");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/jp <togglereceive/togglesend/reload/help>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText("§c使用方法: /jp <togglereceive/togglesend/reload/help>"));
            return;
        }

        String sub = args[0];

        switch (sub.toLowerCase()) {
            case "togglereceive":
                if(ChatListener.enable){
                    ChatListener.enable = false;
                    sender.addChatMessage(new ChatComponentText("§a[lunachat_like]§7受信チャットのローマ字変換を無効にしました"));
                }else {
                    ChatListener.enable = true;
                    sender.addChatMessage(new ChatComponentText("§a[lunachat_like]§7受信チャットのローマ字変換を有効にしました"));
                }
                break;

            case "togglesend":
                if(ChatSender.enable){
                    ChatSender.enable = false;
                    sender.addChatMessage(new ChatComponentText("§a[lunachat_like]§7送信チャットのローマ字変換を無効にしました"));
                }else {
                    ChatSender.enable = true;
                    sender.addChatMessage(new ChatComponentText("§a[lunachat_like]§7送信チャットのローマ字変換を有効にしました"));
                }
                break;
            case "reload":
            	sender.addChatMessage(new ChatComponentText("§a[lunachat_like]§7変換辞書の再度読み込みを実行します"));
            	DictionaryManager.reloadDictionary();
            	break;
            	

            case "help":
            	
                sender.addChatMessage(new ChatComponentText("§a---- [lunachat_like] コマンド一覧 ----"));
                sender.addChatMessage(new ChatComponentText("§7/jp togglereceive - 受信チャット（橙色）のローマ字変換を切り替えます"));
                sender.addChatMessage(new ChatComponentText("§7/jp togglesend - 送信チャット（自分）のローマ字変換を切り替えます"));
                sender.addChatMessage(new ChatComponentText("§7/jp reload - 変換辞書(config/lunachat_dict.txt)を再度読み込みします"));
                sender.addChatMessage(new ChatComponentText("§7/jp help - この一覧を表示します"));
                break;
           
            default:
                sender.addChatMessage(new ChatComponentText("§c不明なコマンドです。/jp help でヘルプを表示します。"));
                break;
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            List<String> options = new ArrayList<>();
            options.add("togglereceive");
            options.add("togglesend");
            options.add("reload");
            options.add("help");
            return getListOfStringsMatchingLastWord(args, options.toArray(new String[0]));
        }
        return null;
    }
}
