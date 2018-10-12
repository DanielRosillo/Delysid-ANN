package Interfaces;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Daniel Rosillo - veni, vidi, veci. 
 *         Esta clase representa el contexto
 *         de trabajo de una neurona simple. Entiendace por contexto de trabajo,
 *         las operaciones matematicas que se llevan acabo en la neurona.
 */
public interface Context
{

    public abstract ArrayList<Double> weights();// @return, Todos los pesos que contiene la neurona.

    public abstract void setWeights(ArrayList<Double> values);// @param values, Los pesos que se desea asignar como
							      // nuevos en la neurona.

    public abstract double u();// @return, El umbral asociado a la neurona

    public abstract void setU(double value);// @param value, El nuevo umbral que se desea asignar.

    public Optional<Double> out();// @return, Un optional que contiene la salida de la neurona si existe.

    public void setOut(double value);// @param value, El valor que sera la nueva salida de la nuerona

    /**
     * Converger una neurona del sistema implica recuperar las entradas y pesos
     * asociados a esa neurona y multiplicarlos para posteriormente aplicar la
     * funcion sigmoide.
     * 
     * @param connections,
     *            Representa los pesos entrantes a la neurona.
     * @param inputs,
     *            Representa las salidas de la capa anterior, que son las entradas
     *            en esta neuron.
     */
    public default void converge(ArrayList<Double> connections, ArrayList<Double> inputs)
    {
	var value = 0.0;
	for (var n = 0; n < connections.size(); n++)// Multiplicamos los pesos por la entradas de la neurona.
	    value += connections.get(n) * inputs.get(n);
	value += u();
	setOut(1 / (1 + Math.exp(-value)));// Aplicamos la funcion Sigmoide para la salida.
    }

    /**
     * 
     * @return La salida binaria de la neurona.
     */
    public default int function()
    {
	return (out().orElse((double) 0.0) > 0.5) ? 1 : 0;
    }

    /**
     * 
     * @param index
     *            es la posicion de la cual se desea recuperar el peso
     * @return un optional que contiene el peso si existe en la neurona.
     */
    public default Optional<Double> getWeight(int index)
    {
	return Optional.ofNullable(weights().get(index));
    }

}
