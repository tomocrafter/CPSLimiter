package net.tomocraft.cpslimiter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiSliderFixed extends GuiButton {

	private float sliderValue;
	private float sliderMinValue;
	private float sliderMaxValue;

	private boolean dragging = false;
	private String label;

	GuiSliderFixed(final int id, final int x, final int y, final int width, final int height, final String label, final float minValue, final float maxValue, final float startingValue) {
		super(id, x, y, width, height, "");
		this.label = label;
		this.sliderMinValue = minValue;
		this.sliderMaxValue = maxValue;
		this.sliderValue = (startingValue - minValue) / (maxValue - minValue);
		if (this.sliderValue < 0.0F) {
			this.sliderValue = 0.0F;
		}
		if (this.sliderValue > 1.0F) {
			this.sliderValue = 1.0F;
		}
	}

	@Override
	protected int getHoverState(boolean par1) {
		return 0;
	}

	int getValueAsInt() {
		return Math.round((this.sliderMaxValue - this.sliderMinValue) * this.sliderValue + this.sliderMinValue);
	}

	@Override
	public void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {
		if (this.enabled && this.visible && this.packedFGColour == 0) {
			if (this.dragging) {
				this.sliderValue = (mouseX - (xPosition + 4f)) / (width - 8f);
				if (this.sliderValue < 0.0F) {
					this.sliderValue = 0.0F;
				}
				if (this.sliderValue > 1.0F) {
					this.sliderValue = 1.0F;
				}
			}

			this.displayString = label + ": " + this.getValueAsInt() + " CPS";

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
			this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
		}
	}

	@Override
	public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			this.sliderValue = (float) (this.xPosition - (this.xPosition + 4)) / (float) (this.width - 8);
			if (this.sliderValue < 0.0f) {
				this.sliderValue = 0.0f;
			}
			if (this.sliderValue > 1.0f) {
				this.sliderValue = 1.0f;
			}
			return this.dragging = true;
		}
		return false;
	}

	@Override
	public void mouseReleased(final int mouseX, final int mouseY) {
		if (this.dragging) {
			this.playPressSound(CPSLimiter.mc.getSoundHandler());
		}
		this.dragging = false;
	}
}