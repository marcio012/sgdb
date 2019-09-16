package website.marcioheleno.model.bloco.tupla;

import website.marcioheleno.model.bloco.dados.Bloco;
import website.marcioheleno.utils.ConverterUltils;

public class Tupla {

    public static byte[] montaTuplaByte(String[] linha) {
        byte[] tupla = new byte[4];

        for (int i = 0; i < linha.length; i++) {
            byte[] linhaByte = linha[i].getBytes();
            byte[] coluna = new byte[2 + linhaByte.length];
            byte[] tamanhoColuna = ConverterUltils.intTo2Byte(linhaByte.length);
            //monta coluna
            coluna = ConverterUltils.bytePlusbyte(coluna, tamanhoColuna, 0);
            coluna = ConverterUltils.bytePlusbyte(coluna, linhaByte, 2);
            //adiciona coluna na tupla
            byte[] novaTupla = new byte[tupla.length + coluna.length];
            novaTupla = ConverterUltils.bytePlusbyte(novaTupla, ConverterUltils.intToByte(somaTotalBytes(tupla, coluna)), 0);
            novaTupla = ConverterUltils.bytePlusbyte(novaTupla, ConverterUltils.getBytes(tupla, 4, tupla.length - 4), 4);
            tupla = ConverterUltils.bytePlusbyte(novaTupla, coluna, tupla.length);

            //System.out.println("eae");
			/*novaTupla = Bloco.bytePlusbyte(novaTupla, Bloco.intToByte(somaTotalBytes(Bloco.getBytes(tupla, 0, 4), coluna)), 0);
			novaTupla = Bloco.bytePlusbyte(novaTupla, Bloco.getBytes(tupla, 4, tupla.length - 4), 4);
            aux = Bloco.bytePlusbyte(novaTupla, coluna, tupla.length - 4);*/
        }

        return tupla;

    }

    public static int somaTotalBytes(byte[] tupla, byte[] coluna) {
        int totalTupla = ConverterUltils.byteToInt(ConverterUltils.getBytes(tupla, 0, 4));
        int totalColuna = ConverterUltils.byte2ToInt(ConverterUltils.getBytes(coluna, 0, 2));

        return totalTupla + totalColuna;
    }

}
