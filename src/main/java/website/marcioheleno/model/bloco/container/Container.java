package website.marcioheleno.model.bloco.container;

import lombok.Data;
import website.marcioheleno.model.bloco.dados.Bloco;

import java.util.ArrayList;
import java.util.List;

@Data
public class Container {

    static int containerId = 0;
    Bloco controle;
    List<Bloco> blocos = new ArrayList<Bloco>();


    public Container(String linha) {
        containerId++;
        controle = new Bloco(linha, (byte) containerId);
    }

    public Bloco getBlocoId(int id) {
        for (int i = 0; i < blocos.size(); i++) {
            Bloco b = blocos.get(i);
            if (id == Bloco.byteToInt(Bloco.getBytes(b.getDados(), 1, 3))) {
                return b;
            }
        }
        return null;
    }

    public void atualizaIdLivreControle(int id) {
        controle.setBytes(Bloco.intToByte(id), 5, 3);
    }

    public int tamanhoDoBloco() {
        return Bloco.byteToInt(Bloco.getBytes(controle.getDados(), 1, 3));
    }

    public Bloco getControle() {
        return controle;
    }

    public void setControle(Bloco controle) {
        this.controle = controle;
    }

    public byte getContainerId() {
        return controle.getDados()[0];
    }

}
