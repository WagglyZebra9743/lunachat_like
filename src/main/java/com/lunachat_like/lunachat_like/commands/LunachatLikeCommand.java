package com.lunachat_like.lunachat_like.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lunachat_like.lunachat_like.chat.DictionaryManager;
import com.lunachat_like.lunachat_like.config.LunachatLikeConfig;

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
            sendmsg("§c使用方法: /jp <togglereceive/togglesend/reload/help>",sender);
            return;
        }

        String sub = args[0];

        switch (sub.toLowerCase()) {
            case "togglereceive":
                if(LunachatLikeConfig.enableReceive){
                	LunachatLikeConfig.enableReceive = false;
                	LunachatLikeConfig.saveConfig();
                    sendmsg("§f[§aLCL§f]受信チャットのローマ字変換を無効にしました",sender);
                }else {
                	LunachatLikeConfig.enableReceive = true;
                	LunachatLikeConfig.saveConfig();
                    sendmsg("§f[§aLCL§f]受信チャットのローマ字変換を有効にしました",sender);
                }
                break;

            case "togglesend":
                if(LunachatLikeConfig.enableSend){
                	LunachatLikeConfig.enableSend = false;
                	LunachatLikeConfig.saveConfig();
                    sendmsg("§f[§aLCL§f]送信チャットのローマ字変換を無効にしました",sender);
                }else {
                	LunachatLikeConfig.enableSend = true;
                	LunachatLikeConfig.saveConfig();
                    sendmsg("§f[§aLCL§f]送信チャットのローマ字変換を有効にしました",sender);
                }
                break;
            case "reload":
            	sendmsg("§f[§aLCL§f]変換辞書の再度読み込みを実行します",sender);
            	DictionaryManager.reloadDictionary();
            	break;
            	

            case "help":
            	
                sendmsg("§6---- [lunachat_like] コマンド一覧 ----",sender);
                sendmsg("§6/jp togglereceive§7 - 受信チャット（橙色）のローマ字変換を切り替えます",sender);
                sendmsg("§6/jp togglesend§7 - 送信チャット（自分）のローマ字変換を切り替えます",sender);
                sendmsg("§6/jp reload§7 - 変換辞書(config/lunachat_dict.txt)を再度読み込みします",sender);
                sendmsg("§6/jp help§7 - この一覧を表示します",sender);
                break;
           
            default:
                sendmsg("§c不明なコマンドです。/jp help でヘルプを表示します。",sender);
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
    private static void sendmsg(String msg , ICommandSender sender) {
    	sender.addChatMessage(new ChatComponentText(msg));
    }
}
