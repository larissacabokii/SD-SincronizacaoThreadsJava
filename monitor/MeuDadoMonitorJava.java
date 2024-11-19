class MeuDadoMonitor {
    private int Dado;
    private boolean Pronto;
    private boolean Ocupado;
    public MeuDadoMonitor() {
        Pronto=false;
        Ocupado=true;
    }
    public void armazenar(int Dado) {
        while(!Ocupado);
        synchronized(this) {
            this.Dado=Dado;
            Ocupado=false;
            Pronto=true;
        }
    }

    public int carregar() {
        while(!Pronto);
        synchronized(this) {
            Pronto=false;
            Ocupado=true;
            return this.Dado;
        }
    }
}

class ProdutorMonitor implements Runnable {
    MeuDadoMonitor dado;
    public ProdutorMonitor(MeuDadoMonitor dado) {
        this.dado=dado;
    }
    public void run() {
        int i;
        for(i=0;i<30;i++) {
            dado.armazenar(i);
            System.out.println ("Produtor usando Monitor: "+i);
            try {
                // cochila por um tempo randômico (0 to 0.5 sec)
                Thread.sleep((int)(Math.random()*500));
            } catch (InterruptedException e) { }
        }
    }
}

class ConsumidorMonitor implements Runnable {
    MeuDadoMonitor dado;
    public ConsumidorMonitor(MeuDadoMonitor dado) {
        this.dado=dado;
    }
    public void run() {
        for(int i=0;i<30;i++) {
            System.out.println("Consumidor usando Monitor: "+dado.carregar());
            try {
                // cochila por um tempo randômico (0 to 0.5 sec)
                Thread.sleep((int)(Math.random()*500));
            } catch (InterruptedException e) { }
        }
    }
}


class MeuDadoMonitorJava {
    public static void main(String argv[]) {
        MeuDadoMonitor dado = new MeuDadoMonitor();
        new Thread(new ProdutorMonitor(dado)).start();
        new Thread(new ConsumidorMonitor(dado)).start();
    }
}
