package comandos.estructura;

import java.util.Objects;

public class Posicion {
	// PROPIEDADES
	private final int x;
	private final int y;

	// MÉTODOS DE CONSULTA Y MODIFICACION
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// CONSTRUCTORES
	public Posicion(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// FUNCIONALIDAD
	public Posicion posicionAdyacente(Direccion dic) {
		if (dic == Direccion.ARRIBA) {
			return new Posicion(this.x, this.y + 1);
		} else if (dic == Direccion.ABAJO) {
			return new Posicion(this.x, this.y - 1);
		} else if (dic == Direccion.DERECHA) {
			return new Posicion(this.x + 1, this.y);
		} else {
			return new Posicion(this.x - 1, this.y);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
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
		Posicion other = (Posicion) obj;
		return x == other.x && y == other.y;
	}
}
