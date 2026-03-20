package com.princekittymeow.kittyclient.mixin.client;

import com.princekittymeow.kittyclient.chat.ChatReader;
import com.princekittymeow.kittyclient.chat.MessageStripper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A mixin to allow the mod to read incoming system messages.
 */
@Mixin(ChatHud.class)
public class ChatHudListenerMixin {

    @Inject(
            method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
            at = @At("TAIL")
    )
    private void onAddMessage(
            Text message,
            @Nullable MessageSignatureData signatureData,
            @Nullable MessageIndicator indicator,
            CallbackInfo ci
            ) {
        if (signatureData != null) {
            ChatReader.readChatMessage(message, signatureData, indicator);
        } else {
            ChatReader.readSystemMessage(message, indicator);
        }
    }
}
