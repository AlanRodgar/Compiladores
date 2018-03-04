
package com.compiladores.practicauno;

import java.util.ArrayList;

/**
 *
 * @author Fernando
 */
public class AFD {
    
    EstadoSi estadoInicial;
    ArrayList<EstadoSi> estados;
    ArrayList<EstadoSi> estadosFinales;
    ArrayList<Character> alfabeto;

    public AFD(EstadoSi estadoInicial, ArrayList<EstadoSi> estados, ArrayList<Character> alfabeto) {
        this.estadoInicial = estadoInicial;
        this.estados = estados;
        this.alfabeto = alfabeto;
    }

    public AFD() {
    }
    
}
