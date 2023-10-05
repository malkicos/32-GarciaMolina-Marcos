package comandos.juego;

import comandos.estructura.Direccion;

public class Decision {
	// PROPIEDADES
	private final boolean siBomba;
	private final Direccion movimiento;

	// MÉTODOS DE CONSULTA
	public boolean isSiBomba() {
		return siBomba;
	}

	public Direccion getMovimiento() {
		return movimiento;
	}

	// CONSTRUCTOR
	public Decision(boolean siBomba, Direccion movimiento) {
		this.siBomba = siBomba;
		this.movimiento = movimiento;
	}
}