package me.obsilabor.tpshud.networking

import me.obsilabor.tpshud.TpsTracker
import me.obsilabor.tpshud.config.ConfigManager
import me.obsilabor.tpshud.minecraft
import me.obsilabor.tpshud.screen.CompatibleServerScreen
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.client.toast.SystemToast
import net.minecraft.text.Text

object NetworkingListener {
    init {
        PayloadTypeRegistry.playS2C().register(CommonHandshakePayload.PAYLOAD_ID, CommonHandshakePayload.CODEC)
        PayloadTypeRegistry.playS2C().register(CommonTickRatePayload.PAYLOAD_ID, CommonTickRatePayload.CODEC)
        ClientPlayNetworking.registerGlobalReceiver(CommonHandshakePayload.PAYLOAD_ID) { _, context ->
            if (ConfigManager.config?.askedForServerProvidedData != true) {
                minecraft.execute {
                    context.client().setScreen(CompatibleServerScreen())
                }
            } else {
                minecraft.execute {
                    context.client().toastManager.add(SystemToast(
                        SystemToast.Type.NARRATOR_TOGGLE,
                        Text.translatable("screen.useServerProvidedData.title"),
                        Text.translatable("toast.useServerProvidedData.message")
                    ))
                }
            }
        }
        ClientPlayNetworking.registerGlobalReceiver(CommonTickRatePayload.PAYLOAD_ID) { payload, _ ->
            if (ConfigManager.config?.askedForServerProvidedData != true) {
                return@registerGlobalReceiver
            }
            TpsTracker.INSTANCE.serverProvidedTps = payload.tickRate.toFloat()
        }
    }
}