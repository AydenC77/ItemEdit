package emanondev.itemedit.command.itemedit;

import emanondev.itemedit.Util;
import emanondev.itemedit.command.ItemEditCommand;
import emanondev.itemedit.command.SubCmd;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MaxStackSize extends SubCmd {

    public MaxStackSize(ItemEditCommand cmd) {
        super("maxstacksize", cmd, true, true);
    }

    //ie MaxStackSize <1-99/default>
    @Override
    public void onCommand(CommandSender sender, String alias, String[] args) {
        Player p = (Player) sender;
        ItemStack item = this.getItemInHand(p);
        try {
            if (args.length > 2)
                throw new IllegalArgumentException("Wrong param number");
            ItemMeta meta = item.getItemMeta();
            Integer value = args.length == 1 ? (meta.hasMaxStackSize() ? Integer.valueOf(item.getType().getMaxStackSize()) : Integer.valueOf(99)) :
                    (args[1].toLowerCase(Locale.ENGLISH).equalsIgnoreCase("default") ? null : Integer.valueOf(args[1]));
            meta.setMaxStackSize(value);
            item.setItemMeta(meta);
        } catch (Exception e) {
            onFail(p, alias);
        }
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            List<String> list = Util.complete(args[1], "1", "32", "64", "99");
            if ("default".startsWith(args[1].toLowerCase(Locale.ENGLISH)))
                list.add("default");
            return list;
        }
        return Collections.emptyList();
    }
}