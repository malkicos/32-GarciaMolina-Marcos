package comandos.estructura;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import comandos.juego.Decision;
import comandos.juego.Imagen;
import comandos.personajes.Comando;
import comandos.personajes.ComandoAlacrán;
import comandos.personajes.ComandoAlfa;
import comandos.personajes.ComandoHalcón;
import comandos.personajes.Contexto;

public class Escenario {
	// PROPIEDADES
	private final String nombre;
	private final int alto;
	private final int ancho;
	private Posicion arma;
	private EstadoJuego estado;
	private LocalDateTime inicio; // propiedad de implementación
	private LocalDateTime ultiActualizacion; // propiedad de implementación
	private final boolean[][] zonas;
	private HashSet<Posicion> ventanas;
	private HashSet<Posicion> bombas;
	private HashSet<Comando> comandos;

	// MÉTODOS DE CONSULTA
	public String getNombre() {
		return nombre;
	}

	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}

	public Posicion getArma() {
		return arma;
	}

	public EstadoJuego getEstado() {
		return estado;
	}

	public HashSet<Posicion> getPosicionesComandos() {
		HashSet<Posicion> posicionComandos = new HashSet<Posicion>();
		for (Comando c : comandos) {
			posicionComandos.add(c.getActual());
		}
		return posicionComandos;
	}

	public Comando getComando(Posicion p) {
		for (Comando comando : comandos) {
			if (comando.getActual().equals(p)) {
				return comando;
			}
		}
		return null;
	}

	// CONSTRUCTORES
	public Escenario(String nombre, int alto, int ancho, Posicion... ventanas) {
		this.nombre = nombre;
		this.alto = alto;
		this.ancho = ancho;
		this.arma = new Posicion(0, 0);
		estado = null;
		inicio = null;
		ultiActualizacion = null;
		bombas = new HashSet<Posicion>();
		comandos = new HashSet<Comando>();
		this.zonas = new boolean[ancho][alto];
		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				this.zonas[i][j] = true;
			}
		}
		this.ventanas = new HashSet<Posicion>();
		Collections.addAll(this.ventanas, ventanas);
	}

	public Escenario(int alto, int ancho, String nombre) {
		this(nombre, alto, ancho);
		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				// Zonas
				if (i == 0 || j == alto - 1) {
					this.zonas[i][j] = true;
				} else {
					if (j % 2 != 0) {
						this.zonas[i][j] = true;
					} else {
						this.zonas[i][j] = false;
					}
				}
				// Ventanas
				if (i % 2 == 0 && j % 2 != 0) {
					this.ventanas.add(new Posicion(i, j));
				}
			}
		}
	}

	// MÉTODOS DE FUNCIONAMIENTO
	public boolean isCerrada(int i, int j) {
		return !this.zonas[i][j];
	}

	public boolean isCerrada(Posicion p) {
		return !this.zonas[p.getX()][p.getY()];
	}

	public boolean tieneVentana(int i, int j) {
		if (ventanas.contains(new Posicion(i, j))) {
			return true;
		}
		return false;
	}

	private boolean isDentroEscenario(Posicion p) {
		return p.getX() >= 0 && p.getX() < ancho && p.getY() >= 0 && p.getY() < alto;
	}

	public LinkedList<Direccion> obtenerDireccionesValidas(int i, int j) {
		LinkedList<Direccion> resultado = new LinkedList<Direccion>();
		Posicion p = new Posicion(i, j);
		for (Direccion dic : Direccion.values()) {
			if (isDentroEscenario(p.posicionAdyacente(dic)) && !isCerrada(p.posicionAdyacente(dic))) {
				resultado.add(dic);
			}
		}
		return resultado;
	}

	public boolean desplazarArma(Direccion dic) {
		Posicion nuevaArma = this.arma.posicionAdyacente(dic);
		if (isDentroEscenario(nuevaArma)) {
			this.arma = nuevaArma;
			return true;
		}
		return false;
	}

	public void iniciarPartida() {
		inicio = LocalDateTime.now();
		ultiActualizacion = LocalDateTime.now();
		estado = EstadoJuego.EN_JUEGO;
	}

	public long tiempoTranscurrido() {
		return ChronoUnit.SECONDS.between(inicio, LocalDateTime.now());
	}

	public LinkedList<Imagen> getTodasImagenes() {
		LinkedList<Imagen> imagenes = new LinkedList<Imagen>();

		for (Comando comando : comandos) {
			Imagen imagenComando = new Imagen(comando.getActual().getX(), comando.getActual().getY(),
					comando.getRutaImagen());
			imagenes.add(imagenComando);
		}

		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				if (tieneVentana(i, j)) {
					Imagen ventana = new Imagen(i, j, "imagenes/ventana.png");
					imagenes.add(ventana);
				} else {
					Imagen ladrillo = new Imagen(i, j, "imagenes/ladrillos.png");
					imagenes.add(ladrillo);
				}
			}
		}

		for (Posicion b : bombas) {
			Imagen imagenComando = new Imagen(b.getX(), b.getY(), "imagenes/bomba.png");
			imagenes.add(imagenComando);
		}

		Imagen objetivo = new Imagen(arma.getX(), arma.getY(), "imagenes/objetivo.png");
		imagenes.add(objetivo);
		return imagenes;
	}

	public LinkedList<Imagen> getTodasImagenesDebug() {
		LinkedList<Imagen> imagenes = new LinkedList<Imagen>();
		for (Comando comando : comandos) {
			Imagen imagenComando = new Imagen(comando.getActual().getX(), comando.getActual().getY(),
					comando.getRutaImagen());
			imagenes.add(imagenComando);
		}

		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				if (tieneVentana(i, j)) {
					Imagen ventana = new Imagen(i, j, "imagenes/ventana.png");
					imagenes.add(ventana);
				} else if (isCerrada(i, j)) {
					Imagen cerrado = new Imagen(i, j, "imagenes/zona-cerrada.png");
					imagenes.add(cerrado);
				}
			}
		}

		for (Posicion b : bombas) {
			Imagen imagenComando = new Imagen(b.getX(), b.getY(), "imagenes/bomba.png");
			imagenes.add(imagenComando);
		}

		Imagen objetivo = new Imagen(arma.getX(), arma.getY(), "imagenes/objetivo.png");
		imagenes.add(objetivo);
		return imagenes;
	}

	private Posicion zonaDisponibleIntroducirComando() {
		LinkedList<Posicion> posicionesRecientes = new LinkedList<Posicion>();

		Random r = new Random();
		int i = r.nextInt(ancho);
		Posicion pRandom = new Posicion(i, alto - 1);

		boolean fin = true;
		boolean rep = false;
		while (fin) {
			rep = false;

			if (posicionesRecientes.size() == ancho) {
				return null;
			}

			if (posicionesRecientes.contains(pRandom)) {
				i = r.nextInt(ancho);
				pRandom = new Posicion(i, alto - 1);
			} else {
				if (this.getPosicionesComandos().contains(pRandom)) {
					rep = true;
				}

				if (rep == true) {
					posicionesRecientes.add(pRandom);
					i = r.nextInt(ancho);
					pRandom = new Posicion(i, alto - 1);
				} else {
					return pRandom;
				}
			}
		}
		return null;

	}

	public boolean introducirComando() {
		Posicion p = zonaDisponibleIntroducirComando();

		if (p == null) {
			return false;
		}
		Random com = new Random();
		Comando nuevo;
		int r = com.nextInt(10);
		;
		if (r < 5) {
			nuevo = new Comando();
			nuevo.añadirPosicion(p);
		} else if (r < 7) {
			nuevo = new ComandoAlacrán();
			nuevo.añadirPosicion(p);
		} else if (r < 8) {
			nuevo = new ComandoAlfa();
			nuevo.añadirPosicion(p);
		} else {
			nuevo = new ComandoHalcón();
			nuevo.añadirPosicion(p);
		}
		comandos.add(nuevo);
		return true;
	}

	public void disparar() {
		Comando borrar = null;
		Posicion pR;
		Comando renacido = null;
		for (Comando comando : comandos) {
			if (this.tieneVentana(arma.getX(), arma.getY()) && comando.getActual().equals(arma)) {
				borrar = comando;
			}
		}
		if (borrar != null) {
			if (borrar instanceof ComandoHalcón) {
				((ComandoHalcón) borrar).decrementarVidas();
				comandos.remove(borrar);
				if (((ComandoHalcón) borrar).getVidas() != 0) {
					pR = zonaDisponibleIntroducirComando();
					if (pR != null) {
						renacido = borrar.clone();
						renacido.añadirPosicion(pR);
						comandos.add(renacido);
					}
				}
			} else {
				comandos.remove(borrar);

				pR = zonaDisponibleIntroducirComando();
				if (pR != null) {
					renacido = borrar.clone();
					renacido.añadirPosicion(pR);
					comandos.add(renacido);
				}
			}
		}

		Posicion br = null;
		for (Posicion p : bombas) {
			if (p.equals(arma)) {
				br = p;
			}
		}
		bombas.remove(br);
	}

	public void actualizar() {

		if (ChronoUnit.SECONDS.between(ultiActualizacion, LocalDateTime.now()) >= 1) {

			for (Comando comando : comandos) {
				int x = comando.getActual().getX();
				int y = comando.getActual().getY();
				boolean dicValida = true;
				ArrayList<Direccion> direccionesCorrectas = new ArrayList<>();

				for (Direccion dic : obtenerDireccionesValidas(x, y)) {
					dicValida = true;

					if (this.getPosicionesComandos().contains(comando.getActual().posicionAdyacente(dic))) {
						dicValida = false;
					}
					if (dicValida) {
						direccionesCorrectas.add(dic);
					}
				}

				Direccion[] array = new Direccion[direccionesCorrectas.size()];
				array = direccionesCorrectas.toArray(array);
				Contexto contexto = new Contexto(tieneVentana(x, y), array);
				Decision decision = comando.tomarDecision(contexto);

				if (decision.isSiBomba()) {
					bombas.add(comando.getActual());
					comando.bombaLiberada();
				}
				if (decision.getMovimiento() != null) {
					comando.añadirPosicion(comando.getActual().posicionAdyacente(decision.getMovimiento()));
				}

				// Actualizar el estado del juego por los comandos.
				if (comando.getActual().getY() == 0) {
					estado = EstadoJuego.FIN_POR_CONTROL;
				}
			}

			// Descender bombas y actualizar el estado del juego por las bombas.

			HashSet<Posicion> nuevasBombas = new HashSet<>();
			for (Posicion bomba : bombas) {
				Posicion bomba1 = bomba.posicionAdyacente(Direccion.ABAJO);
				nuevasBombas.add(bomba1);
				if (bomba1.getY() == 0) {
					estado = EstadoJuego.FIN_POR_BOMBA;
				}
			}
			bombas = nuevasBombas;
			ultiActualizacion = LocalDateTime.now();
		}
	}

}