package Objects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author Daniel Rosillo 
 *         Esta clase representa un sistema neuronal elemental
 *         para un agente.
 */
public class Sys
{
    protected Random random;// Provedor de numeros aleatorios, cabe destacar que debe ser unico por cada
			    // sistema.
    protected LinkedList<Layer> layers;// Capas de del sistema
    protected int size;// numero de neuronas en el sistema
    protected int skins;// numero de capas
    protected double alpha = 0.9;// Factor de aprendizaje

    public Sys()
    {
	random = new Random();
	layers = new LinkedList<>();
    }

    @Override
    public String toString()
    {
	return "System [size=" + size + ", skins=" + skins + "]";
    }

    /**
     * Crea el sistema (nueronas,capas y sus enlaces) basandoce en un modelado
     * 
     * @param model
     *            Representa el modelo de la red en forma de matriz ejemplo...
     * 
     *            model ={4,3,3,2} representaria una red cuya primera capa contiene
     *            4 neuronas la segunda 3 y asi sucesivamente...
     */
    public void build(Integer[] model)
    {
	LinkedList<Neuron> source = new LinkedList<>();// Lista auxiliar
	skins = model.length;// El largo del arreglo es asu vez el numero de capas del sistema

	// Por cada valor en modelo "capa"
	for (var i = 0; i < model.length; i++)
	{
	    LinkedList<Neuron> list = new LinkedList<Neuron>();// Esta Lista es para almacenar la ultima capa creada y
							       // se toma en cuenta apartir de la 2a iteracion cuando
							       // comienza el enlace entre neuronas
	    list.addAll(source);

	    source = new LinkedList<>();// Inicializamos la nueva capa
	    for (var c = 1; c <= model[i]; c++)// Entramos a un ciclo que regira sus iteraciones deacuerdo al valor
					       // leido en el arreglo "modelo" ya que por ejemplo "2" simboliza una capa
					       // de dos neuronas.
	    {
		// En cada iteracion crea una neurona y la agrega a la capa, si no se encuentra
		// en la iteracion 0 comienza a almacenar las neuronas que seran conexiones
		// entrantes dentro de proximas iteraciones
		var data = new ArrayList<Double>();
		Neuron n;
		if ((i + 1) < model.length)
		{
		    for (var e = 1; e <= model[i + 1]; e++)
			data.add(random.nextDouble());
		    n = new Neuron(data, random.nextDouble());
		}

		else n = new Neuron(random.nextDouble());

		source.add(n);
	    }

	    Layer layer = new Layer(source, list, i);
	    layers.add(layer);// Agrega la capa creada a la lista de capas
	    size += model[i];// Suma el valor del arreglo modelo a un contador que al final sera el numero de
			     // neuronas
	}
    }

    /**
     * 
     * @return, Las capas del sistema en forma de lista doblemente enlazada.
     */
    public LinkedList<Layer> layers()
    {
	return layers;
    }

    /**
     * Inicia el proceso de convergencia del sistema, capas y neuronas.
     */
    public void converge()
    {
	layers().forEach(layer ->
	{
	    if (layer.value != 0) for (int i = 0; i < layer.neurons().size(); i++)
		layer.converge();
	});
    }

    /**
     * 
     * @return, todos los pesos del sistema.
     */
    public LinkedList<Double> weights()
    {
	var list = new LinkedList<Double>();
	for (Layer layer : layers)
	{
	    list.addAll(layer.weights());
	}
	return list;
    }

    /**
     * Actualiza el valor del factor de aprendizaje.
     * 
     * @param alpha,
     *            nuevo factor de aprendizaje.
     */
    public void setAlpha(double alpha)
    {
	this.alpha = alpha;
    }
}
