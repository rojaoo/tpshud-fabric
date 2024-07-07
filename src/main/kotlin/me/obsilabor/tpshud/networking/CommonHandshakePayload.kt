package me.obsilabor.tpshud.networking

import net.minecraft.network.PacketByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Identifier

class CommonHandshakePayload : CustomPayload {
    companion object {
        val PAYLOAD_ID: CustomPayload.Id<CommonHandshakePayload> = CustomPayload.Id(Identifier.of(Packets.HANDSHAKE))

        val CODEC: PacketCodec<PacketByteBuf, CommonHandshakePayload> = CustomPayload.codecOf(
            { _, _ -> run {} },
            { _ -> CommonHandshakePayload() }
        ) // What is this bullshit and why is it important?
    }

    override fun getId(): CustomPayload.Id<out CustomPayload> {
        return PAYLOAD_ID
    }
}