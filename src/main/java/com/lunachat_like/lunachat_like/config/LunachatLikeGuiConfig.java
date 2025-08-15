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
        LunachatLikeConfig.syncConfig();
    }
}
