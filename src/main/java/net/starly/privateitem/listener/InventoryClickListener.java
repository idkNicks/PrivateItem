package net.starly.privateitem.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryClickListener extends PrivateItemListener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        ItemStack itemStack = event.isShiftClick() ? event.getCurrentItem() : event.getCursor();

        if (!(event.getClickedInventory() instanceof PlayerInventory) || event.isShiftClick()) {
            checkNbtTag(itemStack, player, "noMovePrivateItem", event::setCancelled);
        }
    }
}

