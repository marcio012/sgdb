package website.marcioheleno.model.bloco.dados;

import lombok.Data;
import website.marcioheleno.utils.ConverterUltils;

import java.nio.ByteBuffer;

@Data
public class Bloco {

    private int tamanho = 10000;
    private byte[] dados;

    /*
        TODO: Separa essa implementação,

        Bloco Container Controller
        Bloco Container Dados
        Criar uma interface Bloco

     */

    //cria bloco normal
    public Bloco(int idBloco, byte idContainer) {
        byte[] aux = new byte[tamanho];

        //id do container
        aux[0] = idContainer;

        //id do bloco
        aux = ConverterUltils.bytePlusbyte(aux, ConverterUltils.intToByte(idBloco), 1);

        //status do container
        aux[4] = 0;

        //tamanho do bloco
        aux = ConverterUltils.bytePlusbyte(aux, ConverterUltils.intToByte(8), 5);

        this.dados = aux;
    }

    //cria bloco controle
    public Bloco(String linha, byte idContainer) {
        byte[] aux = new byte[tamanho];

        //id do container
        aux[0] = idContainer;

        //tamanho do bloco
        aux = ConverterUltils.bytePlusbyte(aux, ConverterUltils.intToByte(tamanho), 1);

        //status do container
        aux[4] = 0;

        //id do proximo bloco livre
        aux = ConverterUltils.bytePlusbyte(aux, ConverterUltils.intToByte(0),5);

        //grava linha de byte
        byte[] linhaByte = linha.getBytes();
        aux = ConverterUltils.bytePlusbyte(aux, ConverterUltils.intTo2Byte(linhaByte.length), 9);
        aux = ConverterUltils.bytePlusbyte(aux, linhaByte, 11);

        this.dados = aux;
    }

    public void adicionarTuplaNoBloco(byte[] tupla) {
        int bytesUsados = ConverterUltils.byteToInt(ConverterUltils.getBytes(this.dados, 5, 3));
        this.dados = ConverterUltils.bytePlusbyte(this.dados, tupla, bytesUsados);
        setBytes(ConverterUltils.intToByte(bytesUsados + tupla.length), 5, 3);
    }

    //retorna conjunto de bytes de um array de bytes
//    public static byte[] getBytes(byte[] dadosRecebidos, int posicaoInicial, int deslocamento){
//        byte[] bytes = new byte[deslocamento];
//
//        for(int i = posicaoInicial, j = 0; i < posicaoInicial + deslocamento; i++, j++){
//            bytes[j] = dadosRecebidos[i];
//        }
//
//        return bytes;
//    }
//
//    //concatena array de bytes
//    public static byte[] bytePlusbyte(byte[] valor1, byte[] valor2, int posicao){
//        for(int i = posicao, j = 0; i < valor2.length + posicao; i++, j++){
//            valor1[i] = valor2[j];
//        }
//        return valor1;
//    }



    //seta bytes em posicoes determinadas de um array de bytes
    public void setBytes(byte[] dadosRecebidos, int posicaoInicial, int deslocamento){
        for(int i = posicaoInicial, j = 0; i < posicaoInicial + deslocamento; i++, j++){
            dados[i] = dadosRecebidos[j];
        }
    }

    public int getTamanhoBloco() {
//        return byteToInt(getBytes(dados, 5, 3));
        return ConverterUltils.byteToInt(ConverterUltils.getBytes(dados, 5, 3));
    }

//    public static byte[] intToByte(int i) {
//        ByteBuffer buf = ByteBuffer.allocate(4);
//        buf.putInt(i);
//
//        byte[] bytes = buf.array();
//
//        byte[] result = new byte[3];
//        result[0] = bytes[1];
//        result[1] = bytes[2];
//        result[2] = bytes[3];
//
//        return result;
//    }
//
//    public static int byteToInt(byte[] bytes) {
//        byte[] result = new byte[4];
//
//        result[0] = 0;
//        result[1] = bytes[0];
//        result[2] = bytes[1];
//        result[3] = bytes[2];
//
//        ByteBuffer buf = ByteBuffer.wrap(result);
//
//        return buf.getInt();
//    }
//
//    public static byte[] intTo2Byte(int i) {
//        ByteBuffer buf = ByteBuffer.allocate(4);
//        buf.putInt(i);
//
//        byte[] bytes = buf.array();
//
//        byte[] result = new byte[2];
//        result[0] = bytes[2];
//        result[1] = bytes[3];
//
//        return result;
//    }
//
//    public static int byte2ToInt(byte[] bytes) {
//        byte[] result = new byte[4];
//
//        result[0] = 0;
//        result[1] = 0;
//        result[2] = bytes[0];
//        result[3] = bytes[1];
//
//        ByteBuffer buf = ByteBuffer.wrap(result);
//
//        return buf.getInt();
//    }
//
//    public static String byteToString(byte[] bytes){
//
//        return new String(bytes);
//
//    }

}
