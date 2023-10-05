package comandos.juego;

public class Imagen {
	// PROPIEDADES
	private final String ruta;
	private final int x;
	private final int y;

	// MÉTODOS DE CONSULTA
	public String getRuta() {
		return ruta;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// CONSTRUCTURES
	public Imagen(int x, int y, String ruta) {
		this.x = x;
		this.y = y;
		this.ruta = ruta;
	}
}
