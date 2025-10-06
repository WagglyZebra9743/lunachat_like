package com.lunachat_like.lunachat_like.chat;

import com.lunachat_like.lunachat_like.config.LunachatLikeConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LunachatLikeHUD {
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static boolean enable = false ;
	@SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
		if(!enable) {
			return;
		}
		FontRenderer font = mc.fontRendererObj;
    	int x = LunachatLikeConfig.hudX;//HUD表示位置指定
    	int y = LunachatLikeConfig.hudY;//HUD表示位置指定
    	int textWidth = font.getStringWidth("/"+LunachatLikeConfig.channel);
    	int textHeight = font.FONT_HEIGHT;
    	int padding = 2;
    	Gui.drawRect(x - padding, y - padding, x + textWidth + padding, y + textHeight + padding, 0x50000000);
    	
    	
    	GlStateManager.pushMatrix();
    	mc.fontRendererObj.drawStringWithShadow("/"+LunachatLikeConfig.channel , x, y, 0xFFFFFF);
    	GlStateManager.popMatrix();
	}
}