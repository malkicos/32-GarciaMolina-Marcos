package comandos.personajes;

import java.util.ArrayList;
import comandos.estructura.Direccion;
import comandos.estructura.Posicion;

/*
 * Este comando se diferencia de los demás en que cuenta con vidas para renacer. Al inicio cuenta con 3 vidas que se irán 
 * decrementando cada vez que muera, hasta que cuando sean 0 dejará de renacer. Sin embargo, puede aumentar sus vidas si
 * descubre zonas nuevas del edificio. En concreto, cada 5 zonas nuevas suma una vida. Además. va a priorizar aquellas zonas 
 * que no haya visitado aún, y en caso de que no pueda ir a ninguna zona nueva, se movera aleatoriamente.
 */

public class ComandoHalcón extends Comando {
	// PROPIEDADES
	private int vidas;
	private int contadorAumentarVidas;

	// CONSTRUCTOR
	public ComandoHalcón() {
		super();
		vidas = 2;
		contadorAumentarVidas = 0;
	}

	// MÉTODO DE CONSULTA
	public int getVidas() {
		return vidas;
	}

	@Override
	public String getRutaImagen() {
		return "imagenes/comando-halcon.png";
	}

	// FUNCIONALIDAD
	public void decrementarVidas() {
		vidas--;
	}

	@Override
	public void añadirPosicion(Posicion p) {
		if (!this.getHistoricoPosicion().contains(p)) {
			contadorAumentarVidas++;
			if (contadorAumentarVidas == 5) {
				vidas++;
				contadorAumentarVidas = 0;
			}
		}
		super.añadirPosicion(p);
	}

	@Override
	protected Direccion decidirMovimiento(Contexto contexto) {
		ArrayList<Direccion> nuevo = new ArrayList<>();
		for (Direccion dic : contexto.getListaDirecciones()) {
			Posicion p = this.getActual().posicionAdyacente(dic);
			if (!this.getHistoricoPosicion().contains(p)) {
				nuevo.add(dic);
			}
		}
		Direccion[] array = new Direccion[nuevo.size()];
		array = nuevo.toArray(array);
		Contexto contextoNuevo = new Contexto(contexto.isSiVentana(), array);

		if (nuevo.size() > 0) {
			return super.decidirMovimiento(contextoNuevo);
		}
		return super.decidirMovimiento(contexto);
	}

	@Override
	public ComandoHalcón clone() {
		return (ComandoHalcón) super.clone();
	}
}
