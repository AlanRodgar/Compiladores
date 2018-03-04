
package com.compiladores.practicauno;

import java.util.Objects;

/**
 *
 * @author Alan Rodgar
 */
public class TransicionSi {
    char minSimb;
    char maxSimb;
    EstadoSi estadoDestino;
    
    public TransicionSi(char s, EstadoSi estadoDestino){
        minSimb = s;
        maxSimb = s;
        this.estadoDestino = estadoDestino;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transicion other = (Transicion) obj;
        if (this.minSimb != other.minSimb) {
            return false;
        }
        if (this.maxSimb != other.maxSimb) {
            return false;
        }
        if (!this.estadoDestino.equals(other.estadoDestino)) {
            return false;
        }
        return true;
    }    
    
}
