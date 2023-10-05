package comandos.personajes;

import comandos.estructura.Direccion;
import comandos.estructura.Posicion;

public class ComandoAlfa extends Comando {
	// PROPIEDADES
	private int altura;

	// CONSTRUCTOR
	public ComandoAlfa() {
		super();
		altura = 0;
	}

	// MÉTODOS DE CONSULTA
	public int getAltura() {
		return altura;
	}

	@Override
	public String getRutaImagen() {
		return "imagenes/comando-alfa.png";
	}

	// FUNCIONALIDAD

	@Override
	public void añadirPosicion(Posicion p) {
		super.añadirPosicion(p);
		if (altura == 0) {
			altura = p.getY();
		}
	}

	@Override
	protected boolean decidirTirarBomba(Contexto contexto) {
		if (this.getActual().getY() < altura / 2) {
			return super.decidirTirarBomba(contexto);
		}
		return false;
	}

	@Override
	protected Direccion decidirMovimiento(Contexto contexto) {
		if (contexto.getListaDirecciones().contains(Direccion.ABAJO)) {
			return Direccion.ABAJO;
		}
		return super.decidirMovimiento(contexto);
	}

	@Override
	public ComandoAlfa clone() {
		return (ComandoAlfa) super.clone();
	}
}
