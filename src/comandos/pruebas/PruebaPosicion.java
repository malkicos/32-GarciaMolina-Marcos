package comandos.pruebas;

import comandos.estructura.Direccion;
import comandos.estructura.Posicion;

public class PruebaPosicion {

	public static void main(String[] args) {
		Posicion posicion1 = new Posicion(0, 0);
		for (Direccion dic : Direccion.values()) {
			System.out.println(dic + " = (" + posicion1.posicionAdyacente(dic).getX() + ","
					+ posicion1.posicionAdyacente(dic).getY() + ")\n");
		}

	}
}
