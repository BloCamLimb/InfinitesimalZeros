package infinitesimalzeros.common.config;

import java.io.File;

import infinitesimalzeros.InfinitesimalZeros;
import net.minecraftforge.common.config.Configuration;

public class Config {
	
	public static Configuration cfg;
	
	public static String General = "general";
	
	public static boolean doAntiCheat = true;
	
	public static void init(File file) {
		
		cfg = new Configuration(new File(file.getPath(), InfinitesimalZeros.MODID + ".cfg"));
		
		cfg.load();
		
		read();
		
		cfg.save();
	}
	
	
	
	public static void read() {
		
		doAntiCheat = cfg.getBoolean("antiAcceleration", General, true, "If true will automatically detect if a machine is being accelerated by certain mod like Torcherino or NotEnoughWands.");

	}
	
}
