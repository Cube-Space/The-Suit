package net.cubespace.thesuit.Teleport.PluginMessage;

import com.iKeirNez.PluginMessageApiPlus.PacketWriter;
import com.iKeirNez.PluginMessageApiPlus.StandardPacket;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
@Getter
public class TeleportMessage extends StandardPacket {
    private String world;
    private Double x;
    private Double y;
    private Double z;

    public TeleportMessage(String world, Double x, Double y, Double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    protected void handle(DataInputStream dataInputStream) throws IOException {
        world = dataInputStream.readUTF();
        x = dataInputStream.readDouble();
        y = dataInputStream.readDouble();
        z = dataInputStream.readDouble();
    }

    @Override
    protected PacketWriter write() throws IOException {
        PacketWriter packetWriter = new PacketWriter(this);
        packetWriter.writeUTF(world);
        packetWriter.writeDouble(x);
        packetWriter.writeDouble(y);
        packetWriter.writeDouble(z);

        return packetWriter;
    }
}
