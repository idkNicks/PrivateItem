package net.starly.privateitem.listener;

import net.starly.core.jb.version.nms.tank.NmsItemStackUtil;
import net.starly.core.jb.version.nms.wrapper.ItemStackWrapper;
import net.starly.core.jb.version.nms.wrapper.NBTTagCompoundWrapper;
import net.starly.privateitem.context.MessageContent;
import net.starly.privateitem.context.MessageType;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.stream.Collectors;

public class InventoryDragListener implements Listener {

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        MessageContent content = MessageContent.getMessageContent();

        ItemStack itemStack = event.getOldCursor();

        if (itemStack == null) return;

        ItemStackWrapper itemStackWrapper = NmsItemStackUtil.getInstance().asNMSCopy(itemStack);
        NBTTagCompoundWrapper nbtTagCompoundWrapper = itemStackWrapper.getTag();
        if (nbtTagCompoundWrapper == null) return;

        String stringNumber = nbtTagCompoundWrapper.getString("stprivateitem");
        if (stringNumber != null && !stringNumber.isEmpty()) {
            for (Inventory inventory : event.getRawSlots().stream().map(event.getView()::getInventory).collect(Collectors.toSet())) {
                if (!(inventory instanceof PlayerInventory)) {
                    content.getMessageAfterPrefix(MessageType.ERROR, "noMovePrivateItem").ifPresent(message -> player.sendMessage(message));
                    event.setCancelled(true);
                }
            }
        }
    }
}
