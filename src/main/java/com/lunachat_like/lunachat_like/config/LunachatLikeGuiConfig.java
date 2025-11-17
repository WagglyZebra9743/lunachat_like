package com.lunachat_like.lunachat_like.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class LunachatLikeGuiConfig extends GuiConfig {
    public LunachatLikeGuiConfig(GuiScreen parent) {
        super(parent,
                new ConfigElement(LunachatLikeConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                "lunachat_like",
                false,
                false,
                "Lunachat Like 設定"
        );
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        // GUIでの変更を反映
        LunachatLikeConfig.enableReceive = LunachatLikeConfig.getConfig().get(LunachatLikeConfig.CATEGORY_GENERAL,"enableReceive",true).getBoolean();
        LunachatLikeConfig.enableSend = LunachatLikeConfig.getConfig().get(LunachatLikeConfig.CATEGORY_GENERAL,"enebleSend",true).getBoolean();
        
        LunachatLikeConfig.autesend = LunachatLikeConfig.getConfig().get(LunachatLikeConfig.CATEGORY_GENERAL,"autesend",false).getBoolean();
        LunachatLikeConfig.auteres = LunachatLikeConfig.getConfig().get(LunachatLikeConfig.CATEGORY_GENERAL,"auteres",false).getBoolean();
        LunachatLikeConfig.channel = LunachatLikeConfig.getConfig().get(LunachatLikeConfig.CATEGORY_GENERAL,"channel","all").getString();
        
        LunachatLikeConfig.hudX = LunachatLikeConfig.getConfig().get(LunachatLikeConfig.CATEGORY_GENERAL, "hudX", 5).getInt();
        LunachatLikeConfig.hudY = LunachatLikeConfig.getConfig().get(LunachatLikeConfig.CATEGORY_GENERAL, "hudY", 5).getInt();
        
        LunachatLikeConfig.AutoVersionCheck = LunachatLikeConfig.getConfig().get(LunachatLikeConfig.CATEGORY_GENERAL, "AutoVersionCheck", true).getBoolean();
        LunachatLikeConfig.SendMCID = LunachatLikeConfig.getConfig().get(LunachatLikeConfig.CATEGORY_GENERAL, "SendMCID", false).getBoolean();
        
        LunachatLikeConfig.saveConfig();
    }
}
