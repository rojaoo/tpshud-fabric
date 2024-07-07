package me.obsilabor.tpshud.networking

import net.minecraft.network.PacketByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Identifier

class CommonTickRatePayload(val tickRate: Double) : CustomPayload {
    companion object {
        val PAYLOAD_ID: CustomPayload.Id<CommonTickRatePayload> = CustomPayload.Id(Identifier.of(Packets.TPS))

        val CODEC: PacketCodec<PacketByteBuf, CommonTickRatePayload> = CustomPayload.codecOf(
            { obj: CommonTickRatePayload, buf -> obj.write(buf) },
            { buf -> CommonTickRatePayload(buf) }
        ) // What is this bullshit and why is it important?
    }

    constructor(buf: PacketByteBuf) : this(buf.readDouble())

    fun write(buf: PacketByteBuf) {
        buf.writeDouble(tickRate)
    }

    override fun getId(): CustomPayload.Id<out CustomPayload> {
        return PAYLOAD_ID
    }
}