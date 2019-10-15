package website.marcioheleno.model.bloco.controler;

import website.marcioheleno.interfaces.IBinary;
import website.marcioheleno.interfaces.IPrint;
import website.marcioheleno.model.bloco.dados.Descritor;
import website.marcioheleno.model.bloco.container.BlocoContainerHeader;
import website.marcioheleno.utils.ByteArrayConcater;
import website.marcioheleno.utils.ByteArrayUtils;

import java.util.ArrayList;

public class BlocoControle implements IBinary, IPrint {

    private BlocoContainerHeader blocoHeader;
    private ArrayList<Descritor> descritores;

    public BlocoControle(int containerId) {
        this.descritores = new ArrayList<Descritor>();
        this.blocoHeader = new BlocoContainerHeader(containerId);
    }

    public BlocoContainerHeader getHeader() {
        return blocoHeader;
    }

    public int getContainerId() { return this.blocoHeader.getContainerId(); }
    @Override
    public byte[] toByteArray() {
        ByteArrayConcater bc = new ByteArrayConcater();
        bc.concat(this.blocoHeader.toByteArray())
            .concat(this.bytesDescritores());
        return bc.getFinalByteArray();
    }

    @Override
    public ArrayList<String> print() {
        ArrayList<String> parse = new ArrayList<String>();

        for (Descritor descritor : descritores) {
            parse.addAll(descritor.print());
        }
        return parse;
    }

    @Override
    public BlocoControle fromByteArray(byte[] byteArray) {

        this.blocoHeader.fromByteArray(ByteArrayUtils.subArray(byteArray, 0, 11));
        if (byteArray.length > 11)
            this.descritores.addAll(this.descritoresFromByteArray(ByteArrayUtils.subArray(byteArray, 11, this.blocoHeader.getTamanhoDescritor())));

        return this;
    }

    public void adicionarDescritores(ArrayList<Descritor> descritores) {
        this.descritores.addAll(descritores);
    }

    private byte[] bytesDescritores() {
        ByteArrayConcater bc = new ByteArrayConcater();
        for (Descritor descritor : this.descritores) {
            bc.concat(descritor.toByteArray());
        }
        return bc.getFinalByteArray();
    }

    private ArrayList<Descritor> descritoresFromByteArray(byte[] descritoresByteArray) {
        ArrayList<Descritor> descritores = new ArrayList<Descritor>();
        boolean whileTrue = true;
        int proximoIndex = 0;

        while(whileTrue) {
            int tamanho = ByteArrayUtils.byteArrayToInt(ByteArrayUtils.subArray(descritoresByteArray, proximoIndex, 4));
            descritores.add(new Descritor(ByteArrayUtils.subArray(descritoresByteArray, proximoIndex,  tamanho)));

            proximoIndex += tamanho;
            whileTrue = whileTrue && proximoIndex < descritoresByteArray.length;
        }

        return descritores;
    }


}
