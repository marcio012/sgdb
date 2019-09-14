package website.marcioheleno.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import website.marcioheleno.model.bloco.dados.Bloco;

@Data
@AllArgsConstructor
public class Buffer {

	Bloco[] buffer;

	public Buffer(int tam) {
		this.buffer = new Bloco[tam];
	}
}
