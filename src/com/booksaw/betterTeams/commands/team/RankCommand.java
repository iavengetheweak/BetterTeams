package com.booksaw.betterTeams.commands.team;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.booksaw.betterTeams.CommandResponse;
import com.booksaw.betterTeams.Main;
import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.commands.SubCommand;
import com.booksaw.betterTeams.message.ReferencedFormatMessage;

public class RankCommand extends SubCommand {

	@Override
	public CommandResponse onCommand(CommandSender sender, String label, String[] args) {
		Team team = null;

		if (args.length == 0 && sender instanceof Player) {
			team = Team.getTeam((Player) sender);
		} else if (args.length >= 1) {
			team = Team.getTeam(args[0]);
		}

		if (team == null) {
			return new CommandResponse("rank.noTeam");
		}

		String priceStr = Main.plugin.getConfig().getString("levels.l" + (team.getLevel() + 1) + ".price");
		boolean score = priceStr.contains("s");

		return new CommandResponse(true, new ReferencedFormatMessage("rank.info" + ((score) ? "s" : "m"),
				team.getLevel() + "", priceStr.substring(0, priceStr.length() - 1)));
	}

	@Override
	public String getCommand() {
		return "rank";
	}

	@Override
	public String getNode() {
		return "rank";
	}

	@Override
	public String getHelp() {
		return "View the rank of a team";
	}

	@Override
	public String getArguments() {
		return "[team]";
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

	@Override
	public int getMaximumArguments() {
		return 1;
	}

	@Override
	public void onTabComplete(List<String> options, CommandSender sender, String label, String[] args) {

		for (Entry<UUID, Team> team : Team.getTeamList().entrySet()) {
			options.add(team.getValue().getName());
		}

	}

}
