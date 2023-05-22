package net.starly.privateitem.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.stream.Collectors;

public class InventoryDragListener extends PrivateItemListener {

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack itemStack = event.getOldCursor();

        for (Inventory inventory : event.getRawSlots().stream().map(event.getView()::getInventory).collect(Collectors.toSet())) {
            if (!(inventory instanceof PlayerInventory)) {
                checkNbtTag(itemStack, player, "noMovePrivateItem", (result) -> {
                    if (result) {
                        event.setCancelled(true);
                    }
                });
            }
        }
    }
}
