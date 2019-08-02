package Package;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import Objects.Perceptron;
/**
 * @author Daniel Rosillo
 */
public class Demo
{
    final Double[][] table = new Double[][] { { 0.0, 0.0 }, { 0.0, 1.0 }, { 1.0, 0.0 }, { 1.0, 1.0 } };
    private Perceptron agent;

    public void start(Integer[] model, double alpha)
    {
	agent = new Perceptron("Giru");
	agent.set(model);
	agent.setAlpha(alpha);
	menu();
    }

    public void menu()
    {
	var mode = "XOR";
	var input = new BufferedReader(new InputStreamReader(System.in));
	try
	{
	    while (!false)
	    {
		out.println("-----------------------------------------------------------------------------\n"
			+ " DELYSID - MENU - MODE: " + mode.toUpperCase() + "\n\n" + "t) Train Agent\n"
			+ "s) Chat to Agent\n" + "m) Mode\n" + "a) Auto\n" + "d) About\n" + "0) Exit\n"
			+ "-----------------------------------------------------------------------------\n");
		switch (input.readLine().toLowerCase())
		{
		case "t":

		    var source = memory(mode);
		    for (int cycle = 0; cycle <= 9000; cycle++)
		    {
			for (int i = 0; i < 4; i++)
			{
			    agent.sense(table[i]);
			    agent.process();
			    agent.train(source[i]);
			}
		    }

		    out.println("Train Complete");
		break;

		case "s":
		    double e1, e2;
		    out.println("Ingresa la primera entrada: ");
		    e1 = Double.parseDouble(input.readLine());
		    out.println("Ingresa la segunda entrada: ");
		    e2 = Double.parseDouble(input.readLine());
		    out.println("\nYou: " + e1 + ", " + e2);

		    agent.sense(new Double[] { e1, e2 });
		    agent.process();

		    var n = agent.sys().layers().get(3).get(0);
		    if (n.function() == 1) out.println("\nAgent say: 1.0  ");
		    else out.println("\nAgent say: 0.0  ");

		break;

		case "a":
		    source = memory(mode);
		    for (int cycle = 0; cycle <= 9000; cycle++)
		    {
			if (cycle % 500 == 0) out.println("Iteracion: " + cycle);
			for (int i = 0; i < 4; i++)
			{
			    agent.sense(table[i]);
			    agent.process();
			    if (cycle % 500 == 0) agent.show(source[i]);
			    agent.train(source[i]);
			}
		    }
		break;

		case "d":
		    out.println("\nDelysid 1.1 | Build 02/08/19 | Powered by: JAVA 10 | Developer: Daniel Rosillo");

		break;

		case "m":
		    out.println("\nAviable: AND,OR,XOR,NAND,NOR,XNOR \nMODE: ");
		    mode = input.readLine();
		break;

		case "0":
		    System.exit(0);

		default:
		    out.println("-----------------------------------------------------------------------------\n"
			    + " DELYSID - MENU - MODE: " + mode.toUpperCase() + "\n\n" + "t) Train Agent\n"
			    + "s) Chat to Agent\n" + "m) Mode\n" + "a) Auto\n" + "d) About\n" + "0) Exit\n"
			    + "-----------------------------------------------------------------------------\n");

		}
	    }
	}
	catch (Throwable m)
	{
	    m.printStackTrace();
	}
    }

    public Double[][] memory(String text)
    {
	Double[][] AND = new Double[][] { { 0.0, 0.0 }, { 0.0, 0.0 }, { 0.0, 0.0 }, { 1.0, 0.0 } };
	Double[][] OR = new Double[][] { { 0.0, 0.0 }, { 1.0, 0.0 }, { 1.0, 0.0 }, { 1.0, 0.0 } };
	Double[][] XOR = new Double[][] { { 0.0, 0.0 }, { 1.0, 0.0 }, { 1.0, 0.0 }, { 0.0, 0.0 } };
	Double[][] NAND = new Double[][] { { 1.0, 0.0 }, { 1.0, 0.0 }, { 1.0, 0.0 }, { 0.0, 0.0 } };
	Double[][] NOR = new Double[][] { { 1.0, 0.0 }, { 0.0, 0.0 }, { 0.0, 0.0 }, { 0.0, 0.0 } };
	Double[][] XNOR = new Double[][] { { 1.0, 0.0 }, { 0.0, 0.0 }, { 0.0, 0.0 }, { 1.0, 0.0 } };

	switch (text.toUpperCase())
	{

	case "AND":
	    return AND;

	case "OR":
	    return OR;

	case "XOR":
	    return XOR;

	case "NAND":
	    return NAND;

	case "NOR":
	    return NOR;

	case "XNOR":
	    return XNOR;

	default:
	    return AND;
	}
    }

    static public void main(String... args)
    {
	Demo t = new Demo();
	t.start(new Integer[] { 2, 4, 5, 2 }, .5);
    }

}
