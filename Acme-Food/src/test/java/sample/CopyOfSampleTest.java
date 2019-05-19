/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package sample;

import java.util.ArrayList;
import java.util.List;

import utilities.AbstractTest;

public class CopyOfSampleTest extends AbstractTest {

	public static void main(final String[] args) {
		final List<Double> res2 = new ArrayList<Double>();
		res2.add(3.0);
		res2.add(3.5);

		Double res = 0.0;
		for (int i = 0; i < res2.size(); i++)
			res = res + res2.iterator().next();

		System.out.println(res);
	}
}
