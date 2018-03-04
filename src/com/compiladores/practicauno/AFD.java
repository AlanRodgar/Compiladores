
package com.compiladores.practicauno;

import java.util.ArrayList;

/**
 *
 * @author Alan Rodgar
 */
public class AFD {
    public static int id;
    private int idAutomata;
    EstadoSi estadoInicial;
    ArrayList<EstadoSi> estados;
    ArrayList<EstadoSi> estadosAceptacion;
    ArrayList<Character> alfabeto;

    public AFD(EstadoSi estadoInicial, ArrayList<EstadoSi> estados, ArrayList<Character> alfabeto) {
        this.idAutomata = ++id;
        this.estadoInicial = estadoInicial;
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadosAceptacion = new ArrayList<>();
    }

    public AFD() {
        this(new EstadoSi(), new ArrayList<EstadoSi>(), new ArrayList<Character>());
    }
    
    public int validarCadena(String s){
        ArrayList<EstadoSi> C = new ArrayList();
        int len = s.length(); 
        for(int i=0;i<len;i++){
            C=irA(this.estados,s.charAt(i));
            if(C.isEmpty()){
                return -1;
            }
        }
        for(EstadoSi e:estadosAceptacion){
            if(e.isIncluded(C)){
                return e.getToken();
            }
        }
        return -1;
    }
    
    /**
     * Esta operacion significa obtener todos los estados que son accesibles al
     * introducir un determinado simbolo.
     * @param estadosSi Lista de posibles estados destino.
     * @param c Simbolo de transicion.
     * @return Conjunto de estados accesibles R.
     */
    public ArrayList<EstadoSi> irA(ArrayList<EstadoSi> estadosSi, char c){
        ArrayList<EstadoSi> R = new ArrayList();
            for(EstadoSi e:estadosSi){
               R.addAll(moverA(e,c));
            }
        return R;
    }    
    
    /**
     * Retorna una lista con todos los estados a los que se puede llegar con el
     * simbolo c.
     * @param e Estado del AFD
     * @param c Simbolo de la transicion
     * @return Lista de estados R del AFD
     */
    public ArrayList<EstadoSi> moverA(EstadoSi e, char c){
        ArrayList<EstadoSi> R = new ArrayList();
            for(TransicionSi t:e.getTransiciones()){/////////////// Checar todo el rango de simbolos
               if(t.minSimb==c || t.maxSimb==c){
                    R.add(t.estadoDestino);
                } //if
            }//for
        return R;
    }

    public int getIdAutomata() {
        return idAutomata;
    }

    public void setIdAutomata(int idAutomata) {
        this.idAutomata = idAutomata;
    }
    
}
