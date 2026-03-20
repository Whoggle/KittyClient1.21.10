package com.princekittymeow.kittyclient;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.princekittymeow.kittyclient.chat.ChatMessageHandler;
import com.princekittymeow.kittyclient.command.CommandSender;
import com.princekittymeow.kittyclient.command.utility.PartyWarper;
import com.princekittymeow.kittyclient.display.HudOverlay;
import com.princekittymeow.kittyclient.dungeon.DungeonCommands;
import com.princekittymeow.kittyclient.gui.PartyCommandScreen;
import com.princekittymeow.kittyclient.notification.PlayerNotifier;
import com.princekittymeow.kittyclient.waypoint.WaypointManager;
import com.princekittymeow.kittyclient.waypoint.WaypointRenderer;
import com.princekittymeow.kittyclient.waypoint.WaypointTriggerRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class KittyClientClient implements ClientModInitializer {

	public static boolean openCommandsScreen = false;

	@Override
	public void onInitializeClient() {
		// Entrypoint for client-specific logic

		// Register stuff
		PartyWarper.register();
		HudOverlay.register();
		WaypointRenderer.register();
		WaypointTriggerRegistry.register();

		// register tick events
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				CommandSender.tick();
			}
			ChatMessageHandler.tick();
			if (openCommandsScreen) {
				openCommandsScreen = false;
				client.setScreen(new PartyCommandScreen());
			}
		});
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			ChatMessageHandler.onJoinServer();
		});

		// commands
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			java.util.function.UnaryOperator<LiteralArgumentBuilder<FabricClientCommandSource>> commands = root -> root
					.executes(ctx -> {
						KittyClientClient.openCommandsScreen = true;
						return 0;
					})
					.then(ClientCommandManager.literal("clearwaypoints")
							.executes(ctx -> {
								WaypointManager.clearAll();
								return 0;
							})
					)
					.then(ClientCommandManager.literal("imgursize")
							.then(ClientCommandManager.argument("size", IntegerArgumentType.integer(32, 1024))
								.executes(ctx -> {
									int size = IntegerArgumentType.getInteger(ctx, "size");
									HudOverlay.changeMaxSize(size);
									return 0;
								})
						)
					)
					.then(ClientCommandManager.literal("partyCheck")  // This command was technically a test command, but it's nice to have
							.executes(ctx -> {
								DungeonCommands.testParty();
								return 0;
							})
					)
					.then(ClientCommandManager.literal("ping")  // This command was technically a test command, but it's nice to have
							.executes(ctx -> {
								PlayerNotifier.ping();
								return 0;
							})
					);


			dispatcher.register(commands.apply(ClientCommandManager.literal("kittyclient")));
			dispatcher.register(commands.apply(ClientCommandManager.literal("kc")));
		});

		// End loading
		System.out.println("KittyClient loaded!");
	}
}