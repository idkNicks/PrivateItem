package net.starly.privateitem.listener;

import lombok.AllArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@AllArgsConstructor
public class PlayerDeathListener extends PrivateItemListener {

    private final JavaPlugin plugin;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack[] inventory = player.getInventory().getContents();

        for (ItemStack itemStack : inventory) {
            if (itemStack == null) continue;

            checkNbtTag(itemStack, player, "restorePrivateItem", (result) -> {
                if (result) {
                    event.getDrops().remove(itemStack);
                    plugin.getServer().getScheduler().runTaskLater(plugin, () -> player.getInventory().addItem(itemStack), 1L);
                }
            });
        }
    }
}
