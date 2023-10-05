package comandos.pruebas;

import java.util.LinkedList;

import comandos.estructura.Escenario;

public class PruebaEscenario {

	public static void main(String[] args) {
		Escenario esc1 = new Escenario("escena2", 9, 5);
		Escenario esc2 = new Escenario(9, 5, "escena1");

		LinkedList<Escenario> lista = new LinkedList<Escenario>();

		lista.add(esc1);
		lista.add(esc2);

		for (Escenario esc : lista) {
			System.out.println("Nombre: " + esc.getNombre());
			for (int i = 0; i < esc.getAncho(); i++) {
				for (int j = 0; j < esc.getAlto(); j++) {
					System.out.println("Posicion(" + i + "," + j + "): Cerrada(" + esc.isCerrada(i, j) + "), Ventana("
							+ esc.tieneVentana(i, j) + ")\n");
				}
			}
		}
		System.out.println(esc2.obtenerDireccionesValidas(3, 2));
		System.out.println(esc2.obtenerDireccionesValidas(0, 0));
	}

}
