package comandos.juego;

import java.awt.Color;
import java.util.LinkedList;

import comandos.estructura.Direccion;
import comandos.estructura.Escenario;
import comandos.estructura.EstadoJuego;
import comandos.vista.Alarma;
import comandos.vista.Pantalla;

public class Programa {
	public final static int ANCHO = 5;
	public final static int ALTO = 9;

	public final static String ARRIBA = "i";
	public final static String ABAJO = "k";
	public final static String DERECHA = "l";
	public final static String IZQUIERDA = "j";
	public final static String DISPARO = "d";
	public final static String FINAL = "x";

	public static void main(String[] args) {
		Escenario escenaJuego = new Escenario(ALTO, ANCHO, "juego");

		Pantalla pantalla = new Pantalla(ANCHO, ALTO, 75, Color.BLACK);
		LinkedList<Imagen> imagenes;
		int i = 0; // Esta variable es para que solo genere 5 comandos en el escenario. Cada vez
					// que introduce uno, aumenta la variable hasta 5.
		boolean fin = false;

		escenaJuego.iniciarPartida();

		while (escenaJuego.getEstado() == EstadoJuego.EN_JUEGO && !fin) {
			pantalla.resetear();

			if (pantalla.hayTecla()) {
				String tecla = pantalla.leerTecla();
				switch (tecla) {
				case ARRIBA:
					escenaJuego.desplazarArma(Direccion.ARRIBA);
					break;
				case ABAJO:
					escenaJuego.desplazarArma(Direccion.ABAJO);
					break;
				case DERECHA:
					escenaJuego.desplazarArma(Direccion.DERECHA);
					break;
				case IZQUIERDA:
					escenaJuego.desplazarArma(Direccion.IZQUIERDA);
					break;
				case DISPARO:
					escenaJuego.disparar();
					break;
				case FINAL:
					fin = true;
					break;
				}
			}
			if (i < 5) {
				escenaJuego.introducirComando();
				i++;
			}
			escenaJuego.actualizar();

			imagenes = escenaJuego.getTodasImagenes();
			for (Imagen imagen : imagenes) {
				pantalla.addImagen(imagen.getX(), imagen.getY(), imagen.getRuta());
			}

			pantalla.setBarraEstado("Tiempo: " + escenaJuego.tiempoTranscurrido());
			pantalla.dibujar();
			Alarma.dormir(150);
		}
		if (escenaJuego.getEstado() == EstadoJuego.EN_JUEGO) {
			pantalla.setBarraEstado("Fin del juego por el jugador.");
		} else {
			pantalla.setBarraEstado("Fin del juego por: " + escenaJuego.getEstado());
		}
	}

}
