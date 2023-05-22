package net.starly.privateitem.command;

import net.starly.privateitem.PrivateItem;
import net.starly.privateitem.context.MessageContent;
import net.starly.privateitem.context.MessageType;
import net.starly.privateitem.util.PrivateItemUtil;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrivateItemCommand implements TabExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        MessageContent content = MessageContent.getMessageContent();

        if (!sender.isOp()) {
            content.getMessageAfterPrefix(MessageType.ERROR, "notAnOperator").ifPresent(sender::sendMessage);
            return false;
        }

        if (args.length == 0) {
            content.getMessageAfterPrefix(MessageType.ERROR, "wrongCommand").ifPresent(sender::sendMessage);
            return false;
        }

        switch (args[0]) {
            case "리로드":
                PrivateItem.getInstance().reloadConfig();
                content.initializing(PrivateItem.getInstance().getConfig());
                content.getMessageAfterPrefix(MessageType.NORMAL, "reloadComplete").ifPresent(sender::sendMessage);
                break;

            case "설정":
                if (sender instanceof Player player) {
                    PrivateItemUtil.setPrivateItem(player);
                    content.getMessageAfterPrefix(MessageType.NORMAL, "setPrivateItem").ifPresent(sender::sendMessage);
                } else {
                    content.getMessageAfterPrefix(MessageType.ERROR, "noConsoleMessage").ifPresent(sender::sendMessage);
                }
                break;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player || sender instanceof ConsoleCommandSender)) {
            return Collections.emptyList();
        }

        return StringUtil.copyPartialMatches(args[0], List.of("리로드", "설정"), new ArrayList<>());
    }
}
