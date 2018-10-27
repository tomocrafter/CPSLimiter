package net.tomocraft.cpslimiter;

import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class EventListener {

	private boolean hasClickedThisTick = false;

	@SubscribeEvent
	public void onMouse(final MouseEvent e) {
		if (e.button != 0 || e.buttonstate) return;
		if (CPSLimiter.getCPS() > Settings.cpsLimit && Settings.preventDoubleClicks && hasClickedThisTick) {
			e.setCanceled(true);
			return;
		}

		hasClickedThisTick = true;
		CPSLimiter.addCPS();
	}

	@SubscribeEvent
	public void onRenderGameOverlay(final RenderGameOverlayEvent event) {
		if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE || event.isCancelable() || !(CPSLimiter.mc.currentScreen instanceof GuiSettings) || CPSLimiter.mc.gameSettings.showDebugInfo)
			return;

		final boolean blendEnabled = GL11.glIsEnabled(3042);
		GL11.glDisable(3042);
		CPSLimiter.mc.fontRendererObj.drawString(CPSLimiter.getCPS() + " CPS", Settings.cpsCounterPosX + 2, Settings.cpsCounterPosY + 2, 0xFFFFFF);
		if (blendEnabled) {
			GL11.glEnable(3042);
		}
	}
}
