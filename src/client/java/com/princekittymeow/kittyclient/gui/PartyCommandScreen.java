package com.princekittymeow.kittyclient.gui;

import com.princekittymeow.kittyclient.command.CommandSender;
import com.princekittymeow.kittyclient.config.PartyCommandConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * A screen to toggle party commands in the mod.
 */
public class PartyCommandScreen extends Screen {

    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 26;
    private static final int COLUMN_GAP = 20;
    private static final int HEADER_HEIGHT = 20;

    /**
     * Create's the screen object.
     */
    public PartyCommandScreen() {
        super(Text.literal("Party Commands"));
    }

    /**
     * Overrides the init method to show our custom screen.
     */
    @Override
    protected void init() {
        int totalWidth = BUTTON_WIDTH * 3 + COLUMN_GAP * 2;

        int startX = (this.width - totalWidth) / 2;
        int startY = 30;

        int col1 = startX;
        int col2 = startX + BUTTON_WIDTH + COLUMN_GAP;
        int col3 = startX + (BUTTON_WIDTH + COLUMN_GAP) * 2;
        int btnY = startY + HEADER_HEIGHT;

        // Fun
        addToggle(col1, btnY, "!8ball",
                PartyCommandConfig.eightBallEnabled,
                b -> { PartyCommandConfig.eightBallEnabled = !PartyCommandConfig.eightBallEnabled; refreshButtons(); });
        addToggle(col1, btnY + BUTTON_SPACING, "!ref",
                PartyCommandConfig.refEnabled,
                b -> { PartyCommandConfig.refEnabled = !PartyCommandConfig.refEnabled; refreshButtons(); });
        addToggle(col1, btnY + BUTTON_SPACING * 2, "!meow",
                PartyCommandConfig.meowEnabled,
                b -> { PartyCommandConfig.meowEnabled = !PartyCommandConfig.meowEnabled; refreshButtons(); });
        addToggle(col1, btnY + BUTTON_SPACING * 3, "!ping",
                PartyCommandConfig.pingEnabled,
                b -> { PartyCommandConfig.pingEnabled = !PartyCommandConfig.pingEnabled; refreshButtons(); });

        // Utility
        addToggle(col2, btnY, "!coords",
                PartyCommandConfig.coordsEnabled,
                b -> { PartyCommandConfig.coordsEnabled = !PartyCommandConfig.coordsEnabled; refreshButtons(); });
        addToggle(col2, btnY + BUTTON_SPACING, "!w (warp)",
                PartyCommandConfig.warpEnabled,
                b -> { PartyCommandConfig.warpEnabled = !PartyCommandConfig.warpEnabled; refreshButtons(); });
        addToggle(col2, btnY + BUTTON_SPACING * 2, "!c (cancel)",
                PartyCommandConfig.cancelEnabled,
                b -> { PartyCommandConfig.cancelEnabled = !PartyCommandConfig.cancelEnabled; refreshButtons(); });
        addToggle(col2, btnY + BUTTON_SPACING * 3, "!fw (force warp)",
                PartyCommandConfig.forceWarpEnabled,
                b -> { PartyCommandConfig.forceWarpEnabled = !PartyCommandConfig.forceWarpEnabled; refreshButtons(); });

        // Dungeon
        addToggle(col3, btnY, "!rq (requeue)",
                PartyCommandConfig.requeueEnabled,
                b -> { PartyCommandConfig.requeueEnabled = !PartyCommandConfig.requeueEnabled; refreshButtons(); });
        addToggle(col3, btnY + BUTTON_SPACING, "!dt (downtime)",
                PartyCommandConfig.downtimeEnabled,
                b -> { PartyCommandConfig.downtimeEnabled = !PartyCommandConfig.downtimeEnabled; refreshButtons(); });

        // Close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), b -> this.close())
                .dimensions(this.width / 2 - 40, btnY + BUTTON_SPACING * 4 + 5, 80, BUTTON_HEIGHT)
                .build());
    }

    private void addToggle(int x, int y, String label, boolean state, ButtonWidget.PressAction action) {
        String prefix = state ? "§a✔ " : "§c✘ ";
        this.addDrawableChild(ButtonWidget.builder(Text.literal(prefix + label), action)
                .dimensions(x, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
    }

    private void refreshButtons() {
        this.clearChildren();
        this.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x80000000);
        super.render(context, mouseX, mouseY, delta);

        int totalWidth = BUTTON_WIDTH * 3 + COLUMN_GAP * 2;
        int startX = (this.width - totalWidth) / 2;
        int startY = 30;

        int col1 = startX;
        int col2 = startX + BUTTON_WIDTH + COLUMN_GAP;
        int col3 = startX + (BUTTON_WIDTH + COLUMN_GAP) * 2;

        drawScaledCenteredText(context, "§lParty Commands", this.width / 2, startY - 14, 0xFFFFFF, 1.0f);
        drawScaledText(context, "§e✦ Fun", col1, startY + 2, 0xFFFFFF, 1.0f);
        drawScaledText(context, "§b✦ Utility", col2, startY + 2, 0xFFFFFF, 1.0f);
        drawScaledText(context, "§c✦ Dungeon", col3, startY + 2, 0xFFFFFF, 1.0f);
    }

    private void drawScaledText(DrawContext context, String text, int x, int y, int color, float scale) {
        context.drawTextWithShadow(this.textRenderer, Text.literal(text), x + 1, y + 1, 0xFF000000);
        context.drawTextWithShadow(this.textRenderer, Text.literal(text), x, y, color);
        if (scale > 1.2f) {
            context.drawTextWithShadow(this.textRenderer, Text.literal(text), x + 1, y, color);
        }
    }

    private void drawScaledCenteredText(DrawContext context, String text, int x, int y, int color, float scale) {
        int textWidth = this.textRenderer.getWidth(Text.literal(text));
        drawScaledText(context, text, x - textWidth / 2, y, color, scale);
    }

    /**
     * Overrides the Screen shouldPause() message so that it will never try to pause.
     * @return false
     */
    @Override
    public boolean shouldPause() {
        return false;
    }
}