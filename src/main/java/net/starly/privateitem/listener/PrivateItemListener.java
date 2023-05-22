package net.starly.privateitem.listener;

import net.starly.core.jb.version.nms.tank.NmsItemStackUtil;
import net.starly.core.jb.version.nms.wrapper.ItemStackWrapper;
import net.starly.core.jb.version.nms.wrapper.NBTTagCompoundWrapper;
import net.starly.privateitem.context.MessageContent;
import net.starly.privateitem.context.MessageType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public abstract class PrivateItemListener implements Listener {

    protected void checkNbtTag(ItemStack itemStack, Player player, MessageType messageType, String errorMessageKey, Consumer<Boolean> callback) {
        if (itemStack == null) return;

        ItemStackWrapper itemStackWrapper = NmsItemStackUtil.getInstance().asNMSCopy(itemStack);
        NBTTagCompoundWrapper nbtTagCompoundWrapper = itemStackWrapper.getTag();
        if (nbtTagCompoundWrapper == null) return;

        String stringNumber = nbtTagCompoundWrapper.getString("stprivateitem");
        if (stringNumber != null && !stringNumber.isEmpty()) {
            MessageContent content = MessageContent.getMessageContent();
            content.getMessageAfterPrefix(messageType, errorMessageKey).ifPresent(player::sendMessage);
            callback.accept(true);
        }
    }
}
