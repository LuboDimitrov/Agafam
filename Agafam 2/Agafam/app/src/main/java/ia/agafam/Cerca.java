package ia.agafam;

import java.util.ArrayList;
import java.util.Collections;

public class Cerca {

    private ArrayList<Node> llistaOberts;
    private ArrayList<Node> llistaTancats;
    private Joc graella;

    Cerca(Joc graella) {
        this.graella = graella;
        llistaOberts = new ArrayList<Node>();
        llistaTancats = new ArrayList<Node>();
    }

    public Node calculaCasella(Punt inici) {
        Node nodeInicial = new Node(inici.x, inici.y);
        Node aux = null;
        llistaOberts.add(nodeInicial);
        while (!llistaOberts.isEmpty()) {
            aux = llistaOberts.get(0);
            Punt p = new Punt(aux.getX(), aux.getY());
            llistaOberts.remove(0); //eliminar x de obert
            if (!graella.casellaDinsGraella(p)) { // si x es meta
                //cami fins a x
                aux = llistaTancats.get(llistaTancats.size() - 1);
                Node solucio = new Node(0, 0);
                while (aux.getNodePrevi() != null) {
                    solucio = aux;
                    p = new Punt(aux.getX(), aux.getY());
                    if (!graella.hiHaObstacle(p)) {
                        graella.afegirNodeSolucio(p);
                    }
                    aux = aux.getNodePrevi();
                }
                return solucio;
            } else {
                //generar successors
                for (Direccio d : Direccio.values()) {
                    if (d != Direccio.C) {
                        if (!graella.hiHaObstacle(p)) {
                            Punt p1 = d.coordenadesVeinat(p);
                            Node n = new Node(p1.x, p1.y);
                            n.setNodePrevi(aux);
                            llistaOberts.add(n);
                        }
                    }
                }
                llistaTancats.add(aux); // posar x a tancat
            }
        }
        return null; //no ha trobat solució.
    }
}
