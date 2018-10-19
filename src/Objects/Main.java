package Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.lang.System.out;

public class Main
{

    public static void main(String[] args)
    {
	var a = new ArrayList<String>();
	a.add("a");
	a.add("b");
	a.add("c");
	a.add("d");
	a.add("e");

	var b = new ArrayList<String>();
	b.add("a");
	b.add("b");
	b.add("c");

	List<String> c = Stream.concat(a.stream(), b.stream()).distinct().collect(Collectors.toList());

	c.forEach(s -> out.println(s));
    }

}
