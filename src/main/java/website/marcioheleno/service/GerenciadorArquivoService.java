package website.marcioheleno.service;

import website.marcioheleno.exceptions.ContainerNoExistent;
import website.marcioheleno.interfaces.IFileManager;
import website.marcioheleno.model.bloco.container.BlocoContainer;
import website.marcioheleno.model.bloco.dados.BlocoDado;

import java.io.IOException;
import java.util.ArrayList;

public class GerenciadorArquivoService {

    private IFileManager ga;
    public GerenciadorArquivoService(IFileManager _ga) {
        ga = _ga;
    }

    public BlocoContainer gerarContainerByInput(String diretorio) throws IOException, ContainerNoExistent {
        ArrayList<String> linhas = GerenciadorDeIO.getStrings(diretorio);

        BlocoContainer container = ga.criarArquivo(linhas.get(0));
        linhas.remove(0);

        linhas.stream().forEach(linha -> {
            try {
                BlocoDado bloco = ga.adicionarLinha(container, linha);
                ga.gravarBloco(container, bloco);
            } catch (IOException e) {
                System.out.println("Não foi possível gravar a linha: " + linha);
            }
        });

        return container;
    }
}
