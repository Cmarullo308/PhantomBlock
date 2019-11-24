package CJ.PhantomBlock.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHandler {
	PhantomBlock main;

	public CommandHandler(PhantomBlock main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			switch (args[0].toLowerCase()) {
			case "test":
				TestCommand(sender);
				break;
			default:
				break;
			}
		}
		return true;
	}

	private void TestCommand(CommandSender s) {

	}
}
