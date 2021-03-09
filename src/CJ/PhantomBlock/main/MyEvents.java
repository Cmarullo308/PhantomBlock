package CJ.PhantomBlock.main;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MyEvents implements Listener {
	PhantomBlock main;

	public MyEvents(PhantomBlock main) {
		this.main = main;
	}

	@EventHandler
	public void ballFiring(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if ((!main.PlayersWithPhantomBlock.contains(player.getUniqueId())) && playerHoldingBuildingTool(player)
				&& player.isSneaking() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			// Get location that the phantom block will be placed
			Location phantomBlockLocation = getPhantomBloackLocation(e.getClickedBlock().getLocation(),
					e.getBlockFace());

			if (phantomBlockLocation == null) {
				return;
			}

			if (phantomBlockLocation.getBlock().getType() != Material.AIR
					&& phantomBlockLocation.getBlock().getType() != Material.WATER) {
				return;
			}

			// Adds player to list of players that currently have a phantom block placed
			main.PlayersWithPhantomBlock.add(player.getUniqueId());

			// Turns the block into a phantom block
			main.getServer().getWorld(phantomBlockLocation.getWorld().getName()).getBlockAt(phantomBlockLocation)
					.setType(Material.BEDROCK);

			// Removes the phantom block after an amount of time
			RemoveBlock rb = new RemoveBlock(player.getUniqueId(), phantomBlockLocation);
			rb = new RemoveBlock(player.getUniqueId(), phantomBlockLocation);
			rb.runTaskLater(main, 60);
		}
	}

	private Location getPhantomBloackLocation(Location clickedBlockLocation, BlockFace blockFace) {
		Location phantomBlockLocation = null;

		switch (blockFace) {
		case UP:
			phantomBlockLocation = clickedBlockLocation.add(0, 1, 0);
			break;
		case SOUTH:
			phantomBlockLocation = clickedBlockLocation.add(0, 0, 1);
			break;
		case WEST:
			phantomBlockLocation = clickedBlockLocation.add(-1, 0, 0);
			break;
		case EAST:
			phantomBlockLocation = clickedBlockLocation.add(1, 0, 0);
			break;
		case NORTH:
			phantomBlockLocation = clickedBlockLocation.add(0, 0, -1);
			break;
		case DOWN:
			phantomBlockLocation = clickedBlockLocation.add(0, -1, 0);
			break;
		default:
			break;

		}

		return phantomBlockLocation;
	}

	private boolean playerHoldingBuildingTool(Player player) {
		Material itemInHand = player.getInventory().getItemInMainHand().getType();

		switch (itemInHand) {
		case WOODEN_PICKAXE:
		case STONE_PICKAXE:
		case IRON_PICKAXE:
		case DIAMOND_PICKAXE:
		case NETHERITE_PICKAXE:
		case WOODEN_AXE:
		case STONE_AXE:
		case IRON_AXE:
		case DIAMOND_AXE:
		case NETHERITE_AXE:
			return true;
		default:
			return false;
		}
	}

	public class RemoveBlock extends BukkitRunnable {
		UUID playerID;
		Location phantomBlockLocation;

		public RemoveBlock(UUID playerID, Location phantomBlockLocation) {
			this.playerID = playerID;
			this.phantomBlockLocation = phantomBlockLocation;
		}

		@Override
		public void run() {
			main.getServer().getWorld(phantomBlockLocation.getWorld().getName()).getBlockAt(phantomBlockLocation)
					.setType(Material.AIR);
			main.PlayersWithPhantomBlock.remove(playerID);
		}

	}
}
