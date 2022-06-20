package com.fdmgroup.boiler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdmgroup.boiler.model.Attribute;
import com.fdmgroup.boiler.model.Method;
import com.fdmgroup.boiler.model.User;
import com.fdmgroup.boiler.repository.MethodRepository;
import com.fdmgroup.boiler.repository.UserRepository;

/** 
 * This class loads data into the repositories
 * @author Zane Havemann
 */
@Component
public class DataLoader implements ApplicationRunner {

	MethodRepository mr;
	UserRepository ur;
	
	@Autowired
	public DataLoader(MethodRepository mr, UserRepository ur) {
		this.mr = mr;
		this.ur = ur;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user = new User("Zane", "$2a$10$hcUpdZx6wonxuan3U99mP.cor4m9zNYlDYwpBYYfmvTtxEPgpHWAK");
		this.ur.save(user);
		List<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("base", "double"));
		attributes.add(new Attribute("exponent", "double"));
		String code = "public double calculatePower(double base, double exponent) {\n"
				+ "\n"
				+ "\tif (exponent == 0)\n"
				+ "\t\treturn 1;\n"
				+ "\n"
				+ "\tdouble temp = calculatePower(base, exponent / 2);\n"
				+ "\n"
				+ "\tif (exponent % 2 == 0)\n"
				+ "\t\treturn temp * temp;\n"
				+ "\n"
				+ "\telse {\n"
				+ "\t\tif (exponent > 0.0)\n"
				+ "\t\t\treturn base * temp * temp;\n"
				+ "\n"
				+ "\t\telse\n"
				+ "\t\t\treturn (temp * temp) / base;\n"
				+ "\t}\n"
				+ "}";
		this.mr.save(new Method("Calculate Power", "This method takes two integers and returns the result of the first integer to the power of the second integer.", code, false, attributes, user));
		attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("n", "double"));
		attributes.add(new Attribute("A", "double"));
		attributes.add(new Attribute("x", "double"));
		code = "public double nthRoot(double n, double A, double x) \n"
				+ "\n"
				+ "\tdouble result = (n-1) / n * x + A / n * 1 / exponent(x, n-1);\n"
				+ "\n"
				+ "\tif (result != x)\n"
				+ "\t\tresult = nthRoot(n, A, result);"
				+ "\n"
				+ "\treturn result;\n"
				+ "}";
		this.mr.save(new Method("Calculate Nth Root", "This method return the result of a fractional exponent using using newtons method.", code, true, attributes, user));
		user = new User("John", "$2a$10$hcUpdZx6wonxuan3U99mP.cor4m9zNYlDYwpBYYfmvTtxEPgpHWAK");
		this.ur.save(user);
		attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("decimalNumber", "double"));
		code = "public ArrayList<Integer> convertToFraction(double decimalNumber) {\n"
				+ "\n"
				+ "\tString numToConvert = Double.toString(num);\n"
				+ "\tint decimalLength = numToConvert.substring(numToConvert.indexOf(\".\") + 1).length();\n"
				+ "\n"
				+ "\tint numerator = (int) (decimalNumber * powerInt(10, decimalLength));\n"
				+ "\tint denominator = (int) powerInt(10, decimalLength);\n"
				+ "\tint gcd = getGCD(numerator, denominator);\n"
				+ "\n"
				+ "\tArrayList<Integer> fraction = new ArrayList<>();\n"
				+ "\tfraction.add(numerator/gcd);\n"
				+ "\tfraction.add(denominator/gcd);\n"
				+ "\n"
				+ "\treturn fraction;\n"
				+ "}";

		this.mr.save(new Method("Convert to Fraction", "This method converts a decimal into a fraction.", code, true, attributes, user));
	}

}
