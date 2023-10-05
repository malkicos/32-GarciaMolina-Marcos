package comandos.personajes;

import java.util.HashSet;
import java.util.Random;

import comandos.estructura.Direccion;
import comandos.estructura.Posicion;
import comandos.juego.Decision;

public class Comando implements Cloneable {
	// PROPIEDADES
	private Posicion actual;
	private final int bombasMax;
	private int bombasDisponibles;
	// private String rutaImagen; propiedad calculada
	private HashSet<Posicion> historicoPosicion;
	private HashSet<Posicion> ventanasConocidas;

	// MÉTODOS DE CONSULTA
	public Posicion getActual() {
		return actual;
	}

	public HashSet<Posicion> getHistoricoPosicion() {
		return new HashSet<Posicion>(historicoPosicion);
	}

	public String getRutaImagen() {
		return "imagenes/comando.png";
	}

	public int getBombasMax() {
		return bombasMax;
	}

	public int getBombasDisponibles() {
		return bombasDisponibles;
	}

	protected void setBombasDisponibles(int b) {
		bombasDisponibles = b;
	}

	public HashSet<Posicion> getVentanasConocidas() {
		return new HashSet<Posicion>(ventanasConocidas);
	}

	// CONSTRUCTOR
	public Comando() {
		actual = null;
		historicoPosicion = new HashSet<Posicion>();
		this.bombasMax = 6;
		this.bombasDisponibles = 6;
		ventanasConocidas = new HashSet<Posicion>();

	}

	// MÉTODOS DE FUNCIONALIDAD
	public void añadirPosicion(Posicion p) {
		actual = p;
		historicoPosicion.add(p);
	}

	public Decision tomarDecision(Contexto contexto) {
		if (contexto.isSiVentana()) {
			ventanasConocidas.add(actual);
		}
		Decision decision = new Decision(decidirTirarBomba(contexto), decidirMovimiento(contexto));
		return decision;
	}

	protected boolean decidirTirarBomba(Contexto contexto) {
		return contexto.isSiVentana() && bombasDisponibles > 0;
	}

	protected Direccion decidirMovimiento(Contexto contexto) {
		int tamañoDireccionesContexto = contexto.getListaDirecciones().size();
		if (tamañoDireccionesContexto == 0) {
			return null;
		}
		Random r = new Random();
		int indice = r.nextInt(tamañoDireccionesContexto);
		return contexto.getListaDirecciones().get(indice);
	}

	public void bombaLiberada() {
		bombasDisponibles = bombasDisponibles - 1;
	}

	private Comando copiaSuperficial() {
		try {
			Comando copiaSuperficial = (Comando) super.clone();
			return copiaSuperficial;
		} catch (CloneNotSupportedException e) {
			System.err.println("La clase no es cloneable");
		}
		return null;
	}

	@Override
	public Comando clone() {
		Comando copia = this.copiaSuperficial();
		copia.bombasDisponibles = copia.bombasMax;
		copia.actual = new Posicion(this.actual.getX(), this.actual.getY());
		copia.historicoPosicion = this.getHistoricoPosicion();
		copia.ventanasConocidas = this.getVentanasConocidas();

		return copia;
	}
}
