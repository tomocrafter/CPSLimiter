package net.tomocraft.cpslimiter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

class Settings {

	private static final String SETTINGS_FILE_NAME = "cps_limiter.conf";

	static int cpsCounterPosX = 0;
	static int cpsCounterPosY = 0;

	static int cpsLimit = 0;

	static boolean preventDoubleClicks = true;

	static void loadSettings() {
		final File settings = new File(CPSLimiter.mc.mcDataDir, SETTINGS_FILE_NAME);
		if (!settings.exists()) {
			return;
		}
		try (BufferedReader reader = Files.newBufferedReader(settings.toPath(), StandardCharsets.UTF_8)) {
			final String[] options = reader.readLine().split(":");
			Settings.cpsCounterPosX = Integer.valueOf(options[0]);
			Settings.cpsCounterPosY = Integer.valueOf(options[1]);
			Settings.cpsLimit = Integer.valueOf(options[2]);
			Settings.preventDoubleClicks = Boolean.valueOf(options[3]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void saveSettings() {
		final File settings = new File(CPSLimiter.mc.mcDataDir, SETTINGS_FILE_NAME);
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(settings.toPath()), StandardCharsets.UTF_8.newEncoder()))) {
			writer.write(Settings.cpsCounterPosX + ":" + Settings.cpsCounterPosY + ":" + Settings.cpsLimit + ":" + Settings.preventDoubleClicks);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
