package website.marcioheleno.factories;

import website.marcioheleno.interfaces.IBinary;
import website.marcioheleno.utils.ByteArrayUtils;

public class ContainerId implements IBinary {

    private int id;

    public  ContainerId(){ }

    private ContainerId(int id) {
        this.id = id;
    }

    public static  ContainerId create() {
        return new ContainerId();
    }

    public static ContainerId create(int id) {
        return new ContainerId(id);
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[1];
        bytes[0] = ByteArrayUtils.intTo1Bytes(this.id);
        return bytes;
    }

    @Override
    public ContainerId fromByteArray(byte[] byteArray) {
        int _containerId = ByteArrayUtils.byteArrayToInt(byteArray);
        return this.create(_containerId);
    }

    public int getValue() {
        return this.id;
    }
}
