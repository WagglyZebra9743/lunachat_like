package com.lunachat_like.lunachat_like.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.lunachat_like.lunachat_like.config.LunachatLikeConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;



public class LunachatLikeChannels extends CommandBase {

    @Override
    public String getCommandName() {
        return "lunachatlike";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("ch");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ch <autosend/autores/all/clan/party/tell/group/help>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sendmsg("§c使用方法: /ch <autorend/autores/all/clan/party/tell/group/help>",sender);
            return;
        }

        final String sub = args[0];

        switch (sub.toLowerCase()) {
            case "autosend":
                if(LunachatLikeConfig.autesend){
                	LunachatLikeConfig.autesend = false;
                	LunachatLikeConfig.saveConfig();
                    sendmsg("§f[§aLCL§f]送信時の自動チャンネル切り替えを無効にしました",sender);
                }else {
                	LunachatLikeConfig.autesend = true;
                	LunachatLikeConfig.saveConfig();
                    sendmsg("§f[§aLCL§f]送信時の自動チャンネル切り替えを有効にしました",sender);
                }
                break;

            case "autores":
                if(LunachatLikeConfig.auteres){
                	LunachatLikeConfig.auteres = false;
                	LunachatLikeConfig.saveConfig();
                    sendmsg("§f[§aLCL§f]受信時の自動チャンネル切り替えを無効にしました",sender);
                }else {
                	LunachatLikeConfig.auteres = true;
                	LunachatLikeConfig.saveConfig();
                    sendmsg("§f[§aLCL§f]受信時の自動チャンネル切り替えを有効にしました",sender);
                }
                break;
            case "hud":
            	if(args.length<3) {
            		sendmsg("§c[lunachat_like]使用方法:/ch hud <x> <y>",sender);
            		return;
            	}
            	try {
                    int x = Integer.parseInt(args[1]);
                    int y = Integer.parseInt(args[2]);

                    sendmsg("§a[lunachat_like]§7表示位置を("+LunachatLikeConfig.hudX+","+LunachatLikeConfig.hudY+")から§e(" + x + ", " + y + ") §7に変更しました",sender);
                    LunachatLikeConfig.hudX = x;
                    LunachatLikeConfig.hudY = y;
                    LunachatLikeConfig.saveConfig();
                } catch (Exception  e) {
                    sendmsg("§c[lunachat_like]使用方法:/ch hud <x> <y>",sender);
                    sendmsg("§7現在の表示位置は§e("+LunachatLikeConfig.hudX+","+LunachatLikeConfig.hudY+")§7です",sender);
                }
            	break;
            case"info":
            	sendmsg("§f[§aLCL§f]現在デフォルトの発言先は"+LunachatLikeConfig.channel+"になっています",sender);
            	break;
            case "all":
            	sendmsg("§f[§aLCL§f]デフォルトの発言先を全体チャットに変更しました",sender);
            	LunachatLikeConfig.channel = "all";
            	LunachatLikeConfig.saveConfig();
            	break;
            case "clan":
            	sendmsg("§f[§aLCL§f]デフォルトの発言先をクランチャットに変更しました",sender);
            	LunachatLikeConfig.channel = "clan msg";
            	LunachatLikeConfig.saveConfig();
            	break;
            case "party":
            case "p":
            	sendmsg("§f[§aLCL§f]デフォルトの発言先をパーティーチャットに変更しました",sender);
            	LunachatLikeConfig.channel = "p";
            	LunachatLikeConfig.saveConfig();
            	break;
            case "group":
            	sendmsg("§f[§aLCL§f]デフォルトの発言先をグループ(近距離)チャットに変更しました",sender);
            	LunachatLikeConfig.channel = "group";
            	LunachatLikeConfig.saveConfig();
            	break;
            case "tell":
            	if(args.length>=2) {
            		final String mcid = args[1];
            		sendmsg("§f[§aLCL§f]デフォルトの発言先を"+mcid+"との個人チャットに変更しました",sender);
                	LunachatLikeConfig.channel = "tell "+mcid;
                	LunachatLikeConfig.saveConfig();
                	break;
            	}else {
            		sendmsg("§c使用方法: /ch tell <mcid>",sender);
            		break;
            	}
            	
            case "help":
                sendmsg("§6---- [lunachat_like] コマンド一覧 ----",sender);
                sendmsg("§6/ch autosend§7 - チャット送信時に自動でチャンネルが切り替わる機能のon/offを切り替えます",sender);
                sendmsg("§6/ch autores§7 - チャット受信時に自動でチャンネルが切り替わる機能のon/offを切り替えます",sender);
                sendmsg("§6/ch hud <x> <y>§7 - チャンネル表示座標を設定します",sender);
                sendmsg("§6/ch all§7 - 発言先を全体チャットにします",sender);
                sendmsg("§6/ch clan§7 - 発言先をクランチャットにします",sender);
                sendmsg("§6/ch party§7 - 発言先をパーティチャットにします",sender);
                sendmsg("§6/ch tell <mcid>§7 - 発言先をmcidとの個人チャットにします",sender);
                sendmsg("§6/ch group§7 - 発言先をグループ(近距離)チャットにします",sender);
                sendmsg("§6/ch help§7 - この一覧を表示します",sender);
                sendmsg("§6--------------------------------------",sender);
                break;
            default:
                sendmsg("§c不明なコマンドです。/ch help でヘルプを表示します。",sender);
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
            options.add("autosend");
            options.add("autores");
            options.add("hud");
            options.add("info");
            options.add("all");
            options.add("clan");
            options.add("party");
            options.add("tell");
            options.add("group");
            options.add("help");
            return getListOfStringsMatchingLastWord(args, options.toArray(new String[0]));
        }else if (args.length == 2) {
            // 1番目の引数が "tell" の場合
            if (args[0].equalsIgnoreCase("tell")) {
                
                
                // クライアントのネットワークハンドラを取得
                NetHandlerPlayClient netHandler = Minecraft.getMinecraft().getNetHandler();
                if (netHandler == null) {
                    return null; // 念のためnullチェック
                }

                // クライアントが認識しているプレイヤー情報のリストを取得
                Collection<NetworkPlayerInfo> playerInfoMap = netHandler.getPlayerInfoMap();
                List<String> playerNames = new ArrayList<>();

                // プレイヤー情報から名前を抽出してリストに追加
                for (NetworkPlayerInfo playerInfo : playerInfoMap) {
                    playerNames.add(playerInfo.getGameProfile().getName());
                }
                
                // 作成したプレイヤー名のリストを候補として返す
                return getListOfStringsMatchingLastWord(args, playerNames.toArray(new String[0]));
                
            }
        }
        return null;
    }
    
    private static void sendmsg(String msg , ICommandSender sender) {
    	sender.addChatMessage(new ChatComponentText(msg));
    }
}
