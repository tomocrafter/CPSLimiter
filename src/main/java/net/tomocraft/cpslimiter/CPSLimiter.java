package net.tomocraft.cpslimiter;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = "cps_limiter", name = "CPSLimiter", version = "1.0.0", clientSideOnly = true, acceptedMinecraftVersions = "[1.8,)")
public final class CPSLimiter {

	static final Minecraft mc = Minecraft.getMinecraft();
	private static List<Long> clicks = new ArrayList<>();

	static void addCPS() {
		CPSLimiter.clicks.add(System.currentTimeMillis());
	}

	static int getCPS() {
		CPSLimiter.clicks.removeIf(click -> click < System.currentTimeMillis() - 1000L);
		return CPSLimiter.clicks.size();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent e) {
		Settings.loadSettings();
		MinecraftForge.EVENT_BUS.register(new EventListener());
		ClientCommandHandler.instance.registerCommand(new CPSModCommand());
	}
}
