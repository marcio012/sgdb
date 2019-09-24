package website.marcioheleno.model.bloco.container;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import website.marcioheleno.model.bloco.dados.Bloco;
import website.marcioheleno.utils.ConverterUltils;

@Data
public class Container {

	static int containerId = 0;
	Bloco blocoDeControle;
	List<Bloco> blocosDados = new ArrayList<Bloco>();

	public Container(String linha) {
		containerId++;
		blocoDeControle = new Bloco(linha, (byte) containerId);
	}

	public Bloco getBlocoId(int id) {
		for (int i = 0; i < blocosDados.size(); i++) {
			Bloco b = blocosDados.get(i);
			if (id == ConverterUltils.byteToInt(ConverterUltils.getBytes(b.getDados(), 1, 3))) {
				return b;
			}
		}
		return null;
	}

	public void atualizaIdLivreControle(int id) {
		blocoDeControle.setBytes(ConverterUltils.intToByte(id), 5, 3);
	}

	public int tamanhoDoBloco() {
		return ConverterUltils.byteToInt(ConverterUltils.getBytes(blocoDeControle.getDados(), 1, 3));
	}

	public Bloco getControle() {
		return blocoDeControle;
	}

	public void setControle(Bloco controle) {
		this.blocoDeControle = controle;
	}

	public byte getContainerId() {
		return blocoDeControle.getDados()[0];
	}

}
