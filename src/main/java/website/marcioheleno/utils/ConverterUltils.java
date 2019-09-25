package website.marcioheleno.utils;

import java.nio.ByteBuffer;

public class ConverterUltils {

    //retorna conjunto de bytes de um array de bytes
    public static byte[] getBytes(byte[] dadosRecebidos, int posicaoInicial, int deslocamento){
        byte[] bytes = new byte[deslocamento];

        for(int i = posicaoInicial, j = 0; i < posicaoInicial + deslocamento; i++, j++){
            bytes[j] = dadosRecebidos[i];
        }

        return bytes;
    }

    //concatena array de bytes
    public static byte[] bytePlusbyte(byte[] valor1, byte[] valor2, int posicao){
        for(int i = posicao, j = 0; i < valor2.length + posicao; i++, j++){
            valor1[i] = valor2[j];
        }
        return valor1;
    }


    public static byte[] intToByte(int i) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt(i);

        byte[] bytes = buf.array();

        byte[] result = new byte[3];
        result[0] = bytes[1];
        result[1] = bytes[2];
        result[2] = bytes[3];

        return result;
    }

    public static int byteToInt(byte[] bytes) {
        byte[] result = new byte[4];

        result[0] = 0;
        result[1] = bytes[0];
        result[2] = bytes[1];
        result[3] = bytes[2];

        ByteBuffer buf = ByteBuffer.wrap(result);

        return buf.getInt();
    }

    public static byte[] intTo2Byte(int i) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt(i);

        byte[] bytes = buf.array();

        byte[] result = new byte[2];
        result[0] = bytes[2];
        result[1] = bytes[3];

        return result;
    }

    public static int byte2ToInt(byte[] bytes) {
        byte[] result = new byte[4];

        result[0] = 0;
        result[1] = 0;
        result[2] = bytes[0];
        result[3] = bytes[1];

        ByteBuffer buf = ByteBuffer.wrap(result);

        return buf.getInt();
    }

    public static String byteToString(byte[] bytes){
        return new String(bytes);
    }
}
