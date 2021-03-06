package me.noodles.gui.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import me.noodles.gui.inv.InvCreator;
import me.noodles.gui.inv.InvNames;
import me.noodles.gui.inv.Items;
import me.noodles.gui.PunishmentGUI;

public class Punish implements Listener, CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getServer().getLogger().warning(PunishmentGUI.getPlugin().getConfig().getString("NoPlayer"));
			return true;
		}

		Player p = (Player) sender;

		if (!cmd.getName().equalsIgnoreCase("punish")) {
			return true;
		}

		if (args.length < 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("NoMessage")));
			return true;
		}

		if (args.length == 1) {
			PunishmentGUI.getPlugin().getBannedManager().add(p.getUniqueId(), args[0]);
		}

		if (!sender.hasPermission("punish.use")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("NoPermission")));
			return true;
		}

		if (args.length > 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("NoMessage")));
			return true;
		}

		if(PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()).length() > 16){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("NameLength")));
			return true;
		}

		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("ChatOffensesLocation"), Items.ChatOffences(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("GeneralOffensesLocation"), Items.GeneralOffences(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("ClientModOffensesLocation"), Items.ClientModOffences(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("AdmitidoOffensesLocation"), Items.AdmitidoOffenses(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity1MuteLocation"), Items.Severity1Mute(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity2MuteLocation"), Items.Severity1Mute(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity3MuteLocation"), Items.Severity1Mute(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity1GeneralBanLocation"), Items.Severity1GeneralBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity2GeneralBanLocation"), Items.Severity2GeneralBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity3GeneralBanLocation"), Items.Severity3GeneralBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity1ClientBanLocation"), Items.Severity1ClientBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity2ClientBanLocation"), Items.Severity2ClientBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity3ClientBanLocation"), Items.Severity3ClientBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity1AdmitidoBanLocation"), Items.Severity1AdmitidoBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity2AdmitidoBanLocation"), Items.Severity2AdmitidoBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("Severity3AdmitidoBanLocation"), Items.Severity3AdmitidoBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("PermanentBanLocation"), Items.PermBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("PermanentMuteLocation"), Items.PermMute(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("IPBanLocation"), Items.IPBan(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("IPMuteLocation"), Items.IPMute(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("StaffIPLocation"), Items.StaffIP(p));
		InvCreator.Main.setItem(PunishmentGUI.getPlugin().getGuiItems().getInt("EvadirBANLocation"), Items.EvadirBAN(p));

		if (PunishmentGUI.getPlugin().getGuiItems().getBoolean("FillerEnabled")) {
			for (int i = 0; i < 54; ++i) {
				if (InvCreator.Main.getItem(i) == null) {
					InvCreator.Main.setItem(i, Items.Glass(p));
				}
			}
		}

		p.openInventory(InvCreator.Main);
		return true;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (!e.getView().getTitle().equals(InvNames.Main)) {
			return;
		}

		if (e.getCurrentItem() == null || (e.getCurrentItem().getType() == Material.AIR)) {
			return;
		}

		if (e.getView().getTitle().equals(InvNames.Main)) {
			e.setCancelled(true);
			if (e.getCurrentItem().equals(Items.PermMute(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("PermMuteCommand").replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("PermMuteReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("PermMuteMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.Severity1Mute(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity1MuteCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity1MuteTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity1MuteReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity1MuteMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.Severity1GeneralBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity1GeneralBanCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity1GeneralBanTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity1GeneralBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity1GeneralBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}
			
			if (e.getCurrentItem().equals(Items.Severity2GeneralBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity2GeneralBanCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity2GeneralBanTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity2GeneralBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity2GeneralBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}
			
			if (e.getCurrentItem().equals(Items.Severity3GeneralBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity3GeneralBanCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity3GeneralBanTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity3GeneralBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity3GeneralBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.Severity1ClientBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity1ClientBanCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity1ClientBanTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity1ClientBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity1ClientBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.Severity2Mute(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity2MuteCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity2MuteTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity2MuteReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity2MuteMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.Severity3Mute(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity3MuteCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity3MuteTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity3MuteReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity3MuteMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.Severity2ClientBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity2ClientBanCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity2ClientBanTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity2ClientBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity2ClientBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.Severity3ClientBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity3ClientBanCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity3ClientBanTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity3ClientBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity3ClientBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}
			
			if (e.getCurrentItem().equals(Items.Severity1AdmitidoBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity1AdmitidoBanCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity1AdmitidoBanTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity1AdmitidoBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity1AdmitidoBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}
			
			if (e.getCurrentItem().equals(Items.Severity2AdmitidoBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity2AdmitidoBanCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity2AdmitidoBanTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity2AdmitidoBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity2AdmitidoBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}
			
			if (e.getCurrentItem().equals(Items.Severity3AdmitidoBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("Severity3AdmitidoBanCommand").replace("%t%", PunishmentGUI.getPlugin().getBanReason().getString("Severity3AdmitidoBanTime")).replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("Severity3AdmitidoBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("Severity3AdmitidoBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.PermBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("PermBanCommand").replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("PermBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("PermBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.IPMute(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("IPMuteCommand").replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("IPMuteReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("IPMuteMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.IPBan(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("IPBanCommand").replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("IPBanReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("IPBanMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}
			
			if (e.getCurrentItem().equals(Items.StaffIP(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("StaffIPCommand").replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("StaffIPReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("StaffIPMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

			if (e.getCurrentItem().equals(Items.EvadirBAN(p))) {
				p.chat(PunishmentGUI.getPlugin().getGuiCommands().getString("EvadirBANCommand").replace("%reason%", PunishmentGUI.getPlugin().getBanReason().getString("EvadirBANReason")).replace("%target%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId())));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', PunishmentGUI.getPlugin().getConfig().getString("Prefix") + PunishmentGUI.getPlugin().getBanReason().getString("EvadirBANMessage").replace("%player%", PunishmentGUI.getPlugin().getBannedManager().get(p.getUniqueId()))));
				p.closeInventory();

				PunishmentGUI.getPlugin().getBannedManager().remove(p.getUniqueId());
			}

		}
	}

}
