package net.cubespace.thesuit.Teleport.PluginMessage;

import com.iKeirNez.PluginMessageApiPlus.PacketWriter;
import com.iKeirNez.PluginMessageApiPlus.StandardPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TeleportRequest extends StandardPacket {
    private UUID deferredUUID;
    private String world;
    private Double x;
    private Double y;
    private Double z;

    @Override
    protected void handle(DataInputStream dataInputStream) throws IOException {
        deferredUUID = UUID.fromString(dataInputStream.readUTF());
        world = dataInputStream.readUTF();
        x = dataInputStream.readDouble();
        y = dataInputStream.readDouble();
        z = dataInputStream.readDouble();
    }

    @Override
    protected PacketWriter write() throws IOException {
        PacketWriter packetWriter = new PacketWriter(this);
        packetWriter.writeUTF(deferredUUID.toString());
        packetWriter.writeUTF(world);
        packetWriter.writeDouble(x);
        packetWriter.writeDouble(y);
        packetWriter.writeDouble(z);

        return packetWriter;
    }
}
