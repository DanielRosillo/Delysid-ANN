package Objects;

import java.util.ArrayList;
import java.util.Optional;

import Interfaces.Context;

/**
 * @author Daniel Rosillo 
 *         Esta clase representa una neurona elemental, segun los
 *         modelos descritos para el perceptron.
 */
public class Neuron implements Context
{
    protected Double out;// La salida asociada a la neurona durante la convergencia.
    protected ArrayList<Double> w;// Los pesos de la neurona.
    protected double u;// Umbral de la neurona.

    public Neuron(ArrayList<Double> w, double u)
    {
	this.u = u;
	this.w = w;
    }

    public Neuron(double u)
    {
	this.u = u;
	w = new ArrayList<>();
    }

    @Override
    public String toString()
    {
	return "Neuron [w=" + w + ", u=" + u + "]";
    }

    @Override
    public ArrayList<Double> weights()
    {
	return w;
    }

    @Override
    public void setWeights(ArrayList<Double> values)
    {
	w = values;
    }

    @Override
    public double u()
    {
	return u;
    }

    @Override
    public void setOut(double value)
    {
	out = value;
    }

    @Override
    public Optional<Double> out()
    {
	return Optional.ofNullable(out);
    }

    @Override
    public void setU(double value)
    {
	u = value;
    }

}
