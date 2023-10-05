package comandos.personajes;

//import comandos.estructura.Direccion;
//import comandos.estructura.Escenario;
//import comandos.estructura.Posicion;

/*
 * Este comando tiene la particularidad de que su número de bombas va aumentando cada vez que visita una ventana 
 * nueva distinta. Empieza teniendo todas las bombas, pero el comando que renace de él tendrá tantas bombas extras como ventanas
 * diferentes visitadas, puediendo superar el máximo de bombas del comando. Sin embargo, tarda en verificar una nueva ventana (debido a que las 
 * ventanas conocidas aumenta en actualizar, que se ejecuta cada 2 segundos).
 */

public class ComandoAlacrán extends Comando {
	// PROPIEDADES
	// private int numeroVentanasDistintasRecorridas Propiedad Calculada

	// CONSTRUCTORES
	public ComandoAlacrán() {
		super();
		this.setBombasDisponibles(0);
	}

	// MéTODOS DE CONSULTA
	public int getNumVentanasDistintasRecorridas() {
		return this.getVentanasConocidas().size();
	}

	@Override
	public String getRutaImagen() {
		return "imagenes/comando-alacran.png";
	}

	// FUNCIONALIDAD

	@Override
	public ComandoAlacrán clone() {
		ComandoAlacrán copia = (ComandoAlacrán) super.clone();
		copia.setBombasDisponibles(getNumVentanasDistintasRecorridas() + this.getBombasMax());
		return copia;
	}
}