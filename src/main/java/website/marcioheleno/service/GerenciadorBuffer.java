package website.marcioheleno.service;

import website.marcioheleno.model.bloco.dados.BlocoDado;
import website.marcioheleno.model.bloco.dados.RowId;
import website.marcioheleno.service.LRU.Lru;

public class GerenciadorBuffer {

    private BlocoDado[] blocks;
    private int countPages;
    private Lru lru;
    private int hit;
    private int miss;

    public GerenciadorBuffer(int sizeBuffer)
    {
        this.blocks = new BlocoDado[sizeBuffer];
        this.lru = new Lru();
        this.countPages = 0;
        this.hit = 0;
        this.miss = 0;
    }

    public BlocoDado addBlock(BlocoDado block) {
        if(checkSize())
        {
            block.setPosicaoLRU((this.countPages));
            this.blocks[this.countPages] = block;
            this.lru.addBlock(block);
            return this.blocks[this.countPages++];
        }
        else
        {
            int positionBlockDelete = this.lru.removeBlock(block);
            if(positionBlockDelete != -1)
            {
                this.blocks[positionBlockDelete] = block;
                return this.blocks[positionBlockDelete];
            }
            else//este else não ocorre, pois a lru só é chama quando o buffer está cheio e neste caso lru já tem elementos incluidos
            {
                System.out.println("LRU vazia !!!");
            }
        }
        return null;
    }

    public BlocoDado existRowId(RowId rowId) {
        BlocoDado result = this.lru.search(rowId);
        if (result == null)
            this.miss++;
        else
            this.hit++;

        return result;
    }

    public void viewBuffer_LRU()
    {
        this.lru.viewLRU();
    }

    private boolean checkSize(){
        return this.countPages < this.blocks.length ? true : false;
    }

    public float taxaAcerto(){
        return 1 - ((float)this.hit / (float)(this.hit+this.miss));
    }

    public int getHit(){
        return this.hit;
    }

    public int getMiss()
    {
        return this.miss;
    }

//    private static void gravarLog() throws IOException {
//        java.util.Date d = new Date();
//        DecimalFormat df = new DecimalFormat("#.##");
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
////        String dStr = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
//        String dStr = dateFormat.format(d);
//
//        // gerando arquivo de log
////        FileWriter arq = new FileWriter("assets/relatorio_de_miss.txt");
//        String[] lines = new String[] {
//            "=================" + dStr + "=====================",
//            "Tamanho da Memória: 	" + lru.getLru().length,
//            "Quantidade de Paginas requisitadas:	" + cacheHitMiss[2],
//            "Cache Hit: 		" + cacheHitMiss[0],
//            "Cache Miss: 		" + cacheHitMiss[1],
//            "Taxa de Hit:		" + df.format(((double) cacheHitMiss[0] / cacheHitMiss[2]) * 100) + "%",
//            "Taxa de Miss:		" + df.format(((double) cacheHitMiss[1] / cacheHitMiss[2]) * 100) + "%",
//            "========================================================="
//        };
//        String path = "assets/relatorio_de_miss.txt";
//        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))){
//
//            for (String line: lines) {
//                bufferedWriter.write(line);
//                bufferedWriter.newLine();
//            }
//        }
//    }

}
