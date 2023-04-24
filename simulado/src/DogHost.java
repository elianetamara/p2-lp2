package src;

public class DogHost {

    private String nome;

    private int qtdMaxRacao;

    private int qtdMaxCachorros;

    private Dog[] cachorros;

    DogHost(String nome, int qtdMaxRacao, int qtdMaxCachorros){
        this.nome = nome;
        this.qtdMaxRacao = qtdMaxRacao;
        this.qtdMaxCachorros = qtdMaxCachorros;
        this.cachorros = new Dog[qtdMaxCachorros];
    }

    private int cont = 0;
    public boolean adicionaDog(String nome, String tutor, int qtdRacao){
        if(qtdRacao > qtdMaxRacao){
            throw new IllegalArgumentException("Excede quantidade máxima de ração");
        }if (cont >= qtdMaxCachorros){
            throw new IllegalArgumentException("Excede quantidade maxima de cachorros");
        }
        for (Dog d: cachorros) {
            if(d.equals(nome, tutor)){
                throw new IllegalArgumentException("Cachorro já cadastrado");
            }
        }
        cachorros[cont] = new Dog(nome, tutor, qtdRacao);
        cont++;
        return true;
    }

    public boolean adicionaDog(String nome, String tutor, int qtdRacao, String tipoRestricao){
        boolean adicionou = adicionaDog(nome, tutor, qtdRacao);
        if(adicionou){
            cachorros[cont].setRestricao(tipoRestricao);
            cachorros[cont].setQtdRacao(qtdRacao*2);
            return true;
        }
        return false;
    }

    public String listaDogs(){
        String lista = nome + ":\n";
        for (int i = 0; i < cachorros.length; i++) {
            if(i == cont){
                lista += cachorros[i].toString();
            }else{
                lista += cachorros[i].toString() + "\n";
            }
        }
        return lista;
    }

    public int pegaRacaoTotal(){
        int total = 0;
        for (Dog d: cachorros) {
            total += d.getQtdRacao();
        }
        return total;
    }

    public double pegaValorTotalHospedagem(){
        double total = 0;
        for (Dog d: cachorros) {
            total += consultaValorHospedagem(d, 1);
        }
        return total;
    }

    public double consultaValorHospedagem(Dog toto, int dias){
        boolean temCachorro = procuraCachorro(toto);
        if(!temCachorro){
            throw new IllegalArgumentException("Esse cachorro não existe");
        }
        return toto.getQtdRacao() * 0.4 * dias;
    }

    private boolean procuraCachorro(Dog toto){
        boolean temCachorro = false;
        for (Dog d: cachorros) {
            if(d.equals(toto.getNome(), toto.getTutor())){
                temCachorro = true;
                break;
            }
        }
        return temCachorro;
    }
}
