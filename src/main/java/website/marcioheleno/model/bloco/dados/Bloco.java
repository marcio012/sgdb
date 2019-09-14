package website.marcioheleno.model.bloco.dados;

import lombok.Data;
import website.marcioheleno.utils.ConverterUltils;

@Data
public class Bloco {

	private int tamanho = 8192;
	private byte[] dados;

	// cria bloco normal
	public Bloco(int idBloco, byte idContainer) {
		byte[] aux = new byte[tamanho];

		// id do container
		aux[0] = idContainer;

		// id do bloco
		aux = ConverterUltils.bytePlusbyte(aux, ConverterUltils.intToByte(idBloco), 1);

		// status do container
		aux[4] = 0;

		// tamanho do bloco
		aux = ConverterUltils.bytePlusbyte(aux, ConverterUltils.intToByte(8), 5);

		this.dados = aux;
	}

	// cria bloco controle
	public Bloco(String linha, byte idContainer) {
		byte[] aux = new byte[tamanho];

		// id do container
		aux[0] = idContainer;

		// tamanho do bloco
		aux = ConverterUltils.bytePlusbyte(aux, ConverterUltils.intToByte(tamanho), 1);

		// status do container
		aux[4] = 0;

		// id do proximo bloco livre
		aux = ConverterUltils.bytePlusbyte(aux, ConverterUltils.intToByte(0), 5);

		// grava linha de byte
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

	// seta bytes em posicoes determinadas de um array de bytes
	public void setBytes(byte[] dadosRecebidos, int posicaoInicial, int deslocamento) {
		for (int i = posicaoInicial, j = 0; i < posicaoInicial + deslocamento; i++, j++) {
			dados[i] = dadosRecebidos[j];
		}
	}

	public int getTamanhoBloco() {
//        return byteToInt(getBytes(dados, 5, 3));
		return ConverterUltils.byteToInt(ConverterUltils.getBytes(dados, 5, 3));
	}

}
