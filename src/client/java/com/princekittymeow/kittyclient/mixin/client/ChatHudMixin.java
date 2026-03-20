package com.princekittymeow.kittyclient.mixin.client;

import com.princekittymeow.kittyclient.chat.ImgurChatInjector;
import com.princekittymeow.kittyclient.display.HudOverlay;
import com.princekittymeow.kittyclient.display.ImgurHudTexture;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.text.Style;

/**
 * A mixin allowing the chat injection of imgur images.
 */
@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Inject(method = "getTextStyleAt", at = @At("RETURN"))
    private void onGetTextStyleAt(double x, double y, CallbackInfoReturnable<Style> cir) {
        Style style = cir.getReturnValue();

        if (style == null || style.getInsertion() == null) {
            HudOverlay.shouldShow = false;
            return;
        }

        String insertion = style.getInsertion();
        String url = ImgurChatInjector.getUrl(insertion);
        if (url != null) {
            if (!url.equals(ImgurHudTexture.loadedUrl) && !ImgurHudTexture.isLoading) {
                ImgurHudTexture.load(url);
            }
            HudOverlay.shouldShow = true;
        } else {
            HudOverlay.shouldShow = false;
        }


    }
}
