package infinitesimalzeros.common.core.handler;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeybindHandler {
	
	//public static final KeyBinding wandMode = new KeyBinding("infinitesimalzeros.keybind.wandmode", Keyboard.KEY_F, "key.categories.inventory");
	
	public static void init() {
		
		//ClientRegistry.registerKeyBinding(wandMode);
	}
	
	public static boolean isKeyDown(KeyBinding keybind) {
		int key = keybind.getKeyCode();
		if(key < 0) {
			int button = 100 + key;
			return Mouse.isButtonDown(button);
		}
		return Keyboard.isKeyDown(key);
	}

}
