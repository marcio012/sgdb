package website.marcioheleno.model.bloco.pagina;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagina {

	private int fileID;
	private int blocoID;
	private int pos;

}
