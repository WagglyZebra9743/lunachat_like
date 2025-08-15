package com.lunachat_like.lunachat_like;

import com.lunachat_like.lunachat_like.chat.ChatListener;
import com.lunachat_like.lunachat_like.chat.ChatSender;
import com.lunachat_like.lunachat_like.chat.DictionaryManager;
import com.lunachat_like.lunachat_like.commands.LunachatLikeCommand;
import com.lunachat_like.lunachat_like.config.LunachatLikeConfig;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "lunachat_like", version = "1.2",guiFactory = "com.lunachat_like.lunachat_like.config.GuiFactory")
public class lunachat_like {
	public static boolean enable = true;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new LunachatLikeCommand());
        MinecraftForge.EVENT_BUS.register(new ChatListener());
        MinecraftForge.EVENT_BUS.register(new ChatSender());
        DictionaryManager.loadDictionary();
        
        

    }
    
    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
    	LunachatLikeConfig.loadConfig(event.getSuggestedConfigurationFile());
    }
}