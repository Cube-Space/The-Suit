package net.cubespace.thesuit.Teleport.PluginMessage;

import com.iKeirNez.PluginMessageApiPlus.PacketWriter;
import com.iKeirNez.PluginMessageApiPlus.StandardPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class LocationRequest extends StandardPacket {
    private UUID deferredUUID;

    @Override
    protected void handle(DataInputStream dataInputStream) throws IOException {
        deferredUUID = UUID.fromString(dataInputStream.readUTF());
    }

    @Override
    protected PacketWriter write() throws IOException {
        PacketWriter packetWriter = new PacketWriter(this);
        packetWriter.writeUTF(deferredUUID.toString());

        return packetWriter;
    }
}
