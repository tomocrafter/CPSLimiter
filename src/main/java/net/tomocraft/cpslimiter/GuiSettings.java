package net.tomocraft.cpslimiter;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiSettings extends GuiScreen {

	private int cps;
	private boolean isDragging;
	private int lastX;
	private int lastY;

	private GuiButton preventDoubleClicksButton;
	private GuiSliderFixed cpsLimit;

	GuiSettings() {
		this.cps = 0;
		this.isDragging = false;
		this.lastX = 0;
		this.lastY = 0;
	}

	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Reset Position"));
		this.buttonList.add(preventDoubleClicksButton = new GuiButton(1, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Prevent DoubleClicks: " + Settings.preventDoubleClicks));
		this.buttonList.add(cpsLimit = new GuiSliderFixed(1, this.width / 2 - 75, this.height / 2 + 26, 200, 20, "CPS Limit: ", 0, 20, 0));
	}

	@Override
	public void drawScreen(final int x, final int y, final float partialTicks) {
		super.drawDefaultBackground();
	}

	@Override
	public void updateScreen() {
		this.cps = CPSLimiter.getCPS();
	}

	@Override
	protected void keyTyped(final char c, final int key) {
		if (key == Keyboard.KEY_NONE) {
			this.mc.displayGuiScreen(null);
		}
	}

	@Override
	protected void mouseClicked(final int x, final int y, final int time) throws IOException {
		final int minX = Settings.cpsCounterPosX;
		final int minY = Settings.cpsCounterPosY;
		final int maxX = Settings.cpsCounterPosX + this.fontRendererObj.getStringWidth(this.cps + " CPS") + 4;
		final int maxY = Settings.cpsCounterPosY + 12;
		if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
			this.isDragging = true;
			this.lastX = x;
			this.lastY = y;
		}
		super.mouseClicked(x, y, time);
	}

	@Override
	protected void mouseClickMove(final int x, final int y, final int lastButtonClicked, final long timeSinceClick) {
		if (this.isDragging) {
			Settings.cpsCounterPosX += x - this.lastX;
			Settings.cpsCounterPosY += y - this.lastY;
			this.lastX = x;
			this.lastY = y;
		}
		super.mouseClickMove(x, y, lastButtonClicked, timeSinceClick);
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		switch (button.id) {
			case 0:
				Settings.cpsCounterPosX = 0;
				Settings.cpsCounterPosY = 0;
				break;

			case 1:
				Settings.preventDoubleClicks = !Settings.preventDoubleClicks;
				preventDoubleClicksButton.displayString = "Prevent Double Clicks: " + Settings.preventDoubleClicks;
				break;
		}
	}

	@Override
	public void onGuiClosed() {
		Settings.cpsLimit = cpsLimit.getValueAsInt();
		Settings.saveSettings();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
