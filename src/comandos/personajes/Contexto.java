package comandos.personajes;

import java.util.Collections;
import java.util.LinkedList;

import comandos.estructura.Direccion;

public class Contexto {
	// PROPIEDADES
	private final boolean siVentana;
	private final LinkedList<Direccion> listaDirecciones;

	// MÉTODOS DE CONSULTA
	public boolean isSiVentana() {
		return siVentana;
	}

	public LinkedList<Direccion> getListaDirecciones() {
		return new LinkedList<Direccion>(listaDirecciones);
	}

	// CONSTRUCTOR
	public Contexto(boolean siVentana, Direccion... direcciones) {
		this.siVentana = siVentana;
		listaDirecciones = new LinkedList<Direccion>();
		Collections.addAll(listaDirecciones, direcciones);
	}
}
