package Objects;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Daniel Rosillo 
 * 
 *         El Perceptron es uno de los modelos mas famosos de ANS
 *         para trabajar con agentes conexionistas, esta clase modela a un
 *         agente multicapa capaz de resolver problemas linealmente
 *         independientes mediante algoritmos de machine learning.
 * 
 * 
 *         Caracteristicas del Agente
 * 
 *         DETERMINIST AGENT 
 *         MACHINE LEARNING SYSTEM 
 *         MULTILAYER PERCEPTRON
 *         BACKPROPAGATION ALGORITHM
 *         SUPERVISED LEARNING 
 *         FEEDFORWARD
 * 
 * 
 */
public class Perceptron
{
    protected Sys sys;
    protected String name;
    protected Double[] entrys;

    public Perceptron(String name)
    {
	this.name = name;
	sys = new Sys();
    }

    @Override
    public String toString()
    {
	return "Perceptron [" + sys + ", name=" + name + " ]";
    }

    static
    {
	out.println("In God We Trust");
    }

    /**
     * 
     * @return, nombre del agente.
     */
    public String name()
    {
	return name;
    }

    /**
     * Inicia el proceso de modelado de la red.
     * @param model, modelo de la red en forma de matriz.
     */
    public void set(Integer[] model)
    {
	sys.build(model);
    }

    /**
     * Es por este metodo que el agente percibe datos del mundo exterior.
     * @param entrys, percepciones del entorno
     */
    public void sense(Double[] entrys)
    {
	this.entrys = entrys;
    }

    /*
     * Este metodo le permite al agente procesar y cuantificar la informacion percibida.
     */
    public void process()
    {
	var list = sys.layers().get(0);

	for (int i = 0; i < list.neurons.size(); i++)
	    list.neurons.get(i).setOut(entrys[i]);

	sys.converge();
    }

    /**
     * El entrenamiento consiste en ajustar los pesos mediante el algoritmo backpropagation.
     * @param expected, salida esperada.
     */
    public void train(Double[] expected)
    {
	var w = new LinkedList<Double>();
	var u = new LinkedList<Double>();

	// Start BackPropagation

	// Calibrar pesos de la capa 3->4
	for (int i = 0; i < sys.layers.get(2).size(); i++)
	{
	    var ni = sys.layers.get(2).get(i);
	    for (int k = 0; k < sys.layers.get(3).size(); k++)
	    {
		var nk = sys.layers.get(3).get(k);

		double yi = nk.out;
		double dE3 = ni.out * (yi - expected[k]) * yi * (1 - yi);
		w.add(ni.getWeight(k).orElse(1.0) - (sys().alpha * dE3));
	    }
	}

	// Calibrar pesos de la capa 2->3
	for (int j = 0; j < sys.layers.get(1).size(); j++)
	{
	    var nj = sys.layers.get(1).get(j);
	    for (int k = 0; k < sys.layers.get(2).size(); k++)
	    {
		double res = 0.0;
		var nk = sys.layers.get(2).get(k);
		for (int i = 0; i < sys.layers.get(3).size(); i++)
		{
		    var ni = sys.layers.get(3).get(i);
		    double yi = ni.out;
		    res += nk.getWeight(i).orElse(1.0) * (yi - expected[i]) * yi * (1 - yi);
		}
		double dE2 = nj.out * nk.out * (1 - nk.out) * res;
		w.add(nj.getWeight(k).orElse(1.0) - (sys().alpha * dE2));
	    }
	}

	// Calibrar capa 1->2
	for (int j = 0; j < sys.layers.get(0).size(); j++)
	{
	    var nj = sys.layers.get(0).get(j);
	    for (int k = 0; k < sys.layers.get(1).size(); k++)
	    {
		double acumular = 0.0;
		var nk = sys.layers.get(1).get(k);
		for (int p = 0; p < sys.layers.get(2).size(); p++)
		{
		    double res = 0.0;
		    var n = sys.layers.get(2).get(p);
		    for (int i = 0; i < sys().layers.get(3).size(); i++)
		    {
			var ni = sys().layers.get(3).get(i);
			double yi = ni.out;
			res += n.getWeight(i).orElse(1.0) * (yi - expected[i]) * yi * (1 - yi);
		    }
		    acumular += nk.getWeight(p).orElse(1.0) * n.out().orElse(1.0) * (1 - n.out().orElse(1.0)) * res;

		}

		double dE1 = entrys[j] * nk.out * (1 - nk.out) * acumular;
		w.add(nj.getWeight(k).orElse(1.0) - (sys().alpha * dE1));

	    }
	}

	// Calibrar umbrales de la capa 4
	for (int i = 0; i < sys().layers.get(3).size(); i++)
	{
	    var ni = sys().layers.get(3).get(i);

	    double Yi = ni.out;
	    double dE4 = (Yi - expected[i]) * Yi * (1 - Yi);
	    u.add(ni.u - sys().alpha * dE4);
	}

	// Calibrar umbrales capa 3
	for (int k = 0; k < sys().layers.get(2).size(); k++)
	{
	    var nk = sys().layers.get(2).get(k);
	    double acum = 0.0;
	    for (int i = 0; i < sys().layers.get(3).size(); i++)
	    {
		var ni = sys().layers.get(3).get(i);
		double Yi = ni.out;
		acum += nk.getWeight(i).orElse(1.0) * (Yi - expected[i]) * Yi * (1 - Yi);
	    }
	    double dE3 = nk.out * (1 - nk.out) * acum;
	    u.add(nk.u - sys().alpha * dE3);
	}

	// Calibrar umbrales capa 2
	for (int k = 0; k < sys().layers.get(1).size(); k++)
	{
	    double acumular = 0.0;
	    var nk = sys().layers.get(1).get(k);

	    for (int p = 0; p < sys().layers.get(2).size(); p++)
	    {
		var np = sys().layers.get(2).get(p);
		double acum = 0.0;

		for (int i = 0; i < sys().layers.get(3).size(); i++)
		{
		    var ni = sys().layers.get(3).get(i);
		    double Yi = ni.out;
		    acum += np.getWeight(i).orElse(1.0) * (Yi - expected[i]) * Yi * (1 - Yi);
		}
		acumular += nk.getWeight(p).orElse(1.0) * np.out * (1 - np.out) * acum;
	    }
	    double dE2 = nk.out * (1 - nk.out) * acumular;
	    u.add(nk.out - sys().alpha * dE2);
	}

	// list.addAll(w);
	// list.addAll(sys.weights());
	// var namesAlreadySeen = new HashSet<>();
	// list.removeIf(p -> !namesAlreadySeen.add(p));

	int e = 0;
	for (int layer = 2; layer >= 0; layer--)
	{
	    for (int i = 0; i < sys.layers.get(layer).size(); i++)
	    {
		var np = sys().layers.get(layer).get(i);
		int value = sys.layers.get(layer + 1).size();
		var s = new ArrayList<Double>();

		for (int k = 0; k < value; k++)
		{
		    s.add(w.get(e));
		    e++;
		}

		np.setWeights(s);
	    }
	}

	e = 0;
	for (int layer = 2; layer >= 0; layer--)
	{
	    for (int i = 0; i < sys.layers.get(layer).size(); i++)
	    {
		var np = sys().layers.get(layer).get(i);
		np.setU(u.get(e));
		e++;
	    }
	}
    }

    /**
     * Asigna un nuevo valor a lapha.
     * @param alpha, nuevo factor de aprendizaje.
     */
    public void setAlpha(double alpha)
    {
	sys.setAlpha(alpha);
    }

    /**
     * 
     * @return el sistema del agente.
     */
    public Sys sys()
    {
	return sys;
    }

    /**
     * 
     * @return las percepciones del momento.
     */
    public Double[] perceptions()
    {
	return entrys;
    }
    
    /**
     * Muestra la tabla de verdad junto con la respuesta real, la del agente y el peso riginal asociado a la respuesta.
     * @param s, salida esperada.
     */
    public void show(Double[] s)
    {
	for (int cont = 0; cont < 2; cont++)
	    out.print(entrys[cont] + ", -> ");
	for (int cont = 0; cont < 1; cont++)
	    out.print(s[cont] + " <vs> ");
	for (int cont = 0; cont < 1; cont++)
	{
	    var n = sys().layers.get(3).get(cont);

	    if (n.function() == 1) out.print("Agent say: 1.0 | " + n.out);
	    else out.print("Agent say: 0.0 | " + n.out);

	}
	out.println();
    }

}
