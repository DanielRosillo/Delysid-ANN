package Objects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @author Daniel Rosillo 
 *         Layer es la envoltura que representa una capa de
 *         neuronas, contiene todas las conexiones asociadas a las nueronas.
 *         Layer tambien proporciona la interfaz de interaccion entre el sistema
 *         y las neuronas, proveyendo recursos e infromacion generalizada entre
 *         las neuronas.
 */
public class Layer
{
    protected int value;// Numero de capa dentro del sistema
    protected LinkedList<Neuron> neurons = new LinkedList<>();// Neuronas de la capa.
    protected LinkedList<Neuron> connections = new LinkedList<>();// Conexiones asociadas a sus neuronas.

    public Layer(LinkedList<Neuron> neurons, LinkedList<Neuron> connections, int value)
    {
	this.neurons = neurons;
	this.connections = connections;
	this.value = value;
    }

    @Override
    public String toString()
    {
	return " layer: " + value + "\n" + " connection size: " + connections.size() + "\n" + " neurons of layer: "
		+ neurons.size() + "\n";
    }

    public LinkedList<Neuron> neurons()
    {
	return neurons;
    }

    public LinkedList<Neuron> connections()
    {
	return connections;
    }

    public int value()
    {
	return value;
    }

    /**
     * 
     * @param output,
     *            Indice de la neurona asociada. @return, Todos los pesos asociados
     *            a esa neurona.
     */
    public ArrayList<Double> weightsTo(int output)
    {
	return connections.stream().map(n -> n.getWeight(output).orElse(1.0))
		.collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @return, Todos los pesos de la capa.
     */
    public LinkedList<Double> weights()
    {
	var list = new LinkedList<Double>();
	for (Neuron neuron : neurons)
	    list.addAll(neuron.weights());

	return list;
    }

    /**
     * @return, Como las entradas de las neuronas de la capa son las mismas para
     * todas, este metodo las recupera de sus conexiones.
     */
    public ArrayList<Double> getInputs()
    {
	return connections.stream().map((neuron -> neuron.out().orElse(1.0)))
		.collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Converge las neuronas de la capa, recorriendo y recuperando sus pesos y
     * salidas asociados.
     */
    public void converge()
    {
	for (var i = 0; i < neurons.size(); i++)
	    neurons.get(i).converge(weightsTo(i), getInputs());
    }

    /**
     * @return, el numero de neuronas en la capa.
     */
    public int size()
    {
	return neurons.size();
    }

    /**
     * 
     * @param n,
     *            la posicion de la neurona que se desea recuperar. @return, la
     *            neurona posicionada en el indice dentro de la capa.
     */
    public Neuron get(int n)
    {
	return neurons.get(n);
    }

}
