package net.starly.privateitem.util;

import net.starly.core.jb.version.nms.tank.NmsItemStackUtil;
import net.starly.core.jb.version.nms.wrapper.ItemStackWrapper;
import net.starly.core.jb.version.nms.wrapper.NBTTagCompoundWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrivateItemUtil {

    public static void setPrivateItem(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand().clone();
        ItemStackWrapper itemStackWrapper = NmsItemStackUtil.getInstance().asNMSCopy(itemStack);
        NBTTagCompoundWrapper nbtTagCompoundWrapper = itemStackWrapper.getTag();

        if (nbtTagCompoundWrapper == null) nbtTagCompoundWrapper = NmsItemStackUtil.getInstance().getNbtCompoundUtil().newInstance();

        nbtTagCompoundWrapper.setString("stprivateitem", "true");
        itemStackWrapper.setTag(nbtTagCompoundWrapper);

        ItemMeta itemMeta = NmsItemStackUtil.getInstance().asBukkitCopy(itemStackWrapper).getItemMeta();
        itemStack.setItemMeta(itemMeta);

        int heldItemSlot = player.getInventory().getHeldItemSlot();
        player.getInventory().setItem(heldItemSlot, itemStack);
    }
}
