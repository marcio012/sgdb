package website.marcioheleno.interfaces;

import website.marcioheleno.exceptions.ContainerNoExistent;
import website.marcioheleno.model.bloco.container.BlocoContainer;
import website.marcioheleno.model.bloco.dados.BlocoDado;
import website.marcioheleno.model.bloco.dados.RowId;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IFileManager {

    BlocoContainer criarBlocoContainer();

    BlocoDado criarBlocoDeDado(int containerId) throws ContainerNoExistent;
    BlocoDado criarBlocoDeDado(byte[] bytes) throws ContainerNoExistent;

    BlocoContainer lerContainer(int containerId) throws FileNotFoundException;
    void gravarArquivoTexto(BlocoContainer container) throws IOException;

    BlocoContainer criarArquivo(String containerString) throws IOException, ContainerNoExistent;
    BlocoDado lerBloco(RowId rowId) throws IOException;
    void gravarBloco(BlocoContainer container, BlocoDado bloco) throws FileNotFoundException;
    BlocoDado adicionarLinha(BlocoContainer container, String linha) throws IOException;
}
