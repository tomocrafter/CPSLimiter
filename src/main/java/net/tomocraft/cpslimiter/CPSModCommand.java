package net.tomocraft.cpslimiter;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CPSModCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "cpsmod";
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) {
		CPSLimiter.mc.displayGuiScreen(new GuiSettings());
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}
}
