package CJ.PhantomBlock.main;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class PhantomBlock extends JavaPlugin {
	CommandHandler commandHandler = new CommandHandler(this);
	ArrayList<UUID> PlayersWithPhantomBlock;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new MyEvents(this), this);
		PlayersWithPhantomBlock = new ArrayList<UUID>();
	}

	public void consoleMessage(String message) {
		getLogger().info(message);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		commandHandler.onCommand(sender, command, label, args);
		return true;
	}
}
